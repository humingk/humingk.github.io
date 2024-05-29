---
layout: post
title : 类加载失败排查记录
categories : java
---

类加载失败排查记录

---

[TOC]

# 背景

### InfoCenter简述

> InfoCenter 是一个依赖 qmq、qconfig，基于本地缓存实现公共配置数据查询服务的Jar包，由一个**配置加载模块**与多个**配置插件**组成，能让万量级配置数据的变更于多个业务系统间秒级生效。
>
> 以汇率数据配置为例，当汇率数据配置发生变更或业务系统启动时，**配置加载模块**通过HTTP请求从服务端加载全量汇率数据配置，然后由**汇率配置插件**解析并保存在本地内存中。业务系统通过调用**汇率配置插件**的静态方法来快速获取汇率配置。
>
> InfoCenter 有一个懒加载机制，即只有业务系统用到汇率数据配置时才会加载**汇率配置插件**类。或在业务系统启动时通过白名单配置、历史访问记录等方式提前加载该类。
>
> **配置插件**的默认实现类通过自定义的SPI集成于 META-INF/spi 目录下，以实现解耦：业务系统可**自定义配置插件**。
>
> InfoCenter 的SPI实现 参考了 Dubbo 的SPI实现，不同于Java的 ServiceLoader 会遍历SPI配置并实例化所有实现类，InfoCenter和Dubbo的ServiceLoader都可以”按需加载“，从而实现懒加载。
>
> Java的SPI实现参考：[Introduction to the Service Provider Interfaces](https://docs.oracle.com/javase/tutorial/sound/SPI-intro.html)
>
> Dubbo的SPI实现参考：[Dubbo SPI](https://dubbo.apache.org/zh/docsv2.7/dev/source/dubbo-spi/)

### 问题描述

某业务系统通过 InfoCenter 查询汇率失败，报错为Jar包中的**汇率配置插件**类加载失败，异常栈如下：

```
java.lang.IllegalStateException: No such service com.qunar.flight.qbd.client.rate.logic.IRateLogic by name default
        at com.qunar.flight.qbd.framework.kernel.extension.ServiceLoader.findServiceClassLoadException(ServiceLoader.java:368)
        at com.qunar.flight.qbd.framework.kernel.extension.ServiceLoader.getServiceClass(ServiceLoader.java:340)
        at com.qunar.flight.qbd.framework.kernel.extension.ServiceLoader.get(ServiceLoader.java:464)
        at com.qunar.flight.qbd.framework.kernel.extension.ServiceLoader.getDefault(ServiceLoader.java:298)
        at com.qunar.flight.qbd.client.rate.RateBeanFactory.getRateLogic(RateBeanFactory.java:79)
        at com.qunar.flight.qbd.client.unified.facade.QbdRateFacades.getGdsRate(QbdRateFacades.java:99)
        at com.qunar.flight.userproduct.athena.house.core.i18n.service.impl.PriceTranslateServiceImpl.translate(PriceTranslateServiceImpl.java:43)
        at com.qunar.flight.userproduct.athena.house.core.i18n.service.impl.replace.strategy.AbstractReplaceStrategy.doReplace(AbstractReplaceStrategy.java:37)
        at com.qunar.flight.userproduct.athena.house.core.i18n.service.impl.replace.strategy.DynamicJoinReplaceStrategy.replace(DynamicJoinReplaceStrategy.java:38)
        at com.qunar.flight.userproduct.athena.house.core.i18n.service.impl.I18nTranslateServiceImpl.getReplacedVal(I18nTranslateServiceImpl.java:292)
        at com.qunar.flight.userproduct.athena.house.core.i18n.service.impl.I18nTranslateServiceImpl.replace(I18nTranslateServiceImpl.java:280)
        at com.qunar.flight.userproduct.athena.house.core.i18n.service.impl.I18nTranslateServiceImpl.lambda$parseJsonPath$0(I18nTranslateServiceImpl.java:145)
        at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
        at java.base/java.util.ArrayList$ArrayListSpliterator.forEachRemaining(ArrayList.java:1625)
        at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:509)
        at java.base/java.util.stream.ForEachOps$ForEachTask.compute(ForEachOps.java:290)
        at java.base/java.util.concurrent.CountedCompleter.exec(CountedCompleter.java:754)
        at java.base/java.util.concurrent.ForkJoinTask.doExec(ForkJoinTask.java:373)
        at java.base/java.util.concurrent.ForkJoinPool$WorkQueue.topLevelExec(ForkJoinPool.java:1182)
        at java.base/java.util.concurrent.ForkJoinPool.scan(ForkJoinPool.java:1655)
        at java.base/java.util.concurrent.ForkJoinPool.runWorker(ForkJoinPool.java:1622)
        at java.base/java.util.concurrent.ForkJoinWorkerThread.run(ForkJoinWorkerThread.java:165)
```

### 疑问点

多次验证该问题后，我们整理了以下两个疑问点：



#### 疑问1：其它业务系统、配置插件无法复现此问题

InfoCenter 由一个**配置加载模块**与多个**配置插件**组成，所有的**配置插件**都共用一个**配置加载模块**。

多次测试其它业务系统、其它配置插件，均无法复现此问题。



#### 疑问2：该业务系统该问题不稳定复现

在该业务系统上进行debug测试：

- 断点阻塞时间较短的情况下，该问题大概率复现。
- 断点阻塞时间较长的情况下，该问题大概率无法复现。



# 代码分析

业务系统通过下列静态方法获取汇率配置。

此方法先加载汇率配置类，再从该类中获取汇率配置。

```java
	public static ExchangeRateVO getGdsRate(String sourceCurrency, String targetCurrency) {
		try {
			return RateBeanFactory.getRateLogic().getGdsRate(sourceCurrency, targetCurrency);
		} catch (Exception e) {
			QMonitor.recordOne("qbd_QbdRateFacades_getGdsRate_error");
			throw e;
		}
	}
```

业务系统通过下列方法获取汇率配置类。

如果该类为空，通过自定义 ServiceLoader 实现加载配置接口的实现类。


```java
	private static IRateLogic rateLogic;

	public static IRateLogic getRateLogic() {
		if (rateLogic == null) {
			rateLogic = ServiceLoader.load(IRateLogic.class).getDefault();
		}
		return rateLogic;
	}
```

业务系统通过下列`load()`工厂方法加载 ServiceLoader 实例。

自定义 ServiceLoader 中维护了一个 `SERVICE_LOADERS` map，每个配置接口都有对应的 ServiceLoader 实例。

当某个接口没有 ServiceLoader 实例时，会在map中新增一个 `new ServiceLoader<>(type)`。

```java
	private static final ConcurrentMap<Class<?>, ServiceLoader<?>> SERVICE_LOADERS = new ConcurrentHashMap<>();
	
	/**
	 * {@link ServiceLoader}的工厂方法。
	 *
	 * @param type 扩展点接口类型
	 * @param <T>  扩展点类型
	 * @return {@link ServiceLoader}实例
	 * @throws IllegalArgumentException 参数为<code>null</code>；或是扩展点接口上没有{@link SPI}注解。
	 */
	public static <T> ServiceLoader<T> load(Class<T> type) {
		if (type == null) {
			throw new IllegalArgumentException("Service type == null");
		}
		if (!type.isInterface()) {
			throw new IllegalArgumentException("Service type(" + type + ") is not interface!");
		}
		if (!withSPIAnnotation(type)) {
			throw new IllegalArgumentException("Service type(" + type + ") is not service, because WITHOUT @" +
					SPI.class.getSimpleName() + " Annotation!");
		}
		ServiceLoader<T> loader = (ServiceLoader<T>) SERVICE_LOADERS.get(type);
		if (loader == null) {
			SERVICE_LOADERS.putIfAbsent(type, new ServiceLoader<>(type));
			loader = (ServiceLoader<T>) SERVICE_LOADERS.get(type);
		}
		return loader;
	}
```

ServiceLoader 的构造方法如下。

先解析接口SPI注解的`default`作为该接口默认实现名，再通过`getAdaptive()`方法加载该接口的工厂类。

```java
	private final IServiceFactory objectFactory;
	
	private ServiceLoader(Class<T> type) {
		this.type = type;

		String defaultName = null;
		final SPI annotation = type.getAnnotation(SPI.class);
		if (annotation != null) {
			String value = annotation.value();
			if ((value = value.trim()).length() > 0) {
				String[] names = NAME_SEPARATOR.split(value);
				if (names.length > 1) {
					throw new IllegalStateException("more than 1 default service name on service " + type.getName() +
							": " + Arrays.toString(names));
				}
				if (names.length == 1 && names[0].trim().length() > 0) {
					defaultName = names[0].trim();
				}
				if (!isValidServiceName(defaultName)) {
					throw new IllegalStateException("default name(" + defaultName + ") of service " + type.getName() +
							" is invalid!");
				}
			}
		}
		this.defaultName = defaultName;
		objectFactory = (type == IServiceFactory.class ? null : ServiceLoader.load(IServiceFactory.class).getAdaptive());
	}
```

`getAdaptive()`方法如下所示。

通过`getServiceClasses()`方法加载该接口配置的所有实现类。

```java
	/**
	 * 取得Adaptive实例。
	 */
	public T getAdaptive() {
		getServiceClasses(); // 加载扩展点，保证会发现手写的AdaptiveClass
		
		...
	}
```

`getServiceClasses()`方法如下所示，`getServiceClass(String name) `方法可按name获取对应实现类。

通过`loadServiceClasses0()`方法加载该接口配置的所有实现类，并维护在`nameMapping`中。

```java
	private final AtomicReference<Map<String, Class<?>>> nameMapping = new AtomicReference<>();

	private Map<String, Class<?>> getServiceClasses() {
		Map<String, Class<?>> classes = nameMapping.get();
		if (classes == null) {
			synchronized (nameMapping) {
				classes = nameMapping.get();
				if (classes == null) { // double check
					loadServiceClasses0();
					classes = nameMapping.get();
				}
			}
		}
		return classes;
	}

	private Class<?> getServiceClass(String name) {
		if (name == null) {
			throw new IllegalArgumentException("Service name == null");
		}
		Class<?> clazz = getServiceClasses().get(name);
		if (clazz == null) {
			throw findServiceClassLoadException(name);
		}
		return clazz;
	}
```

`loadServiceClasses0()`方法如下所示。

通过`getClassLoader()`方法获取类加载器，通过类加载器的`getResources()`方法和接口全路径名获取SPI配置。

```java
	private void loadServiceClasses0() {
		Map<String, Class<?>> serviceName2Class = new LinkedHashMap<>();
		Map<String, Map<String, String>> name2Attributes = new LinkedHashMap<>();
		String fileName = null;
		try {
			ClassLoader classLoader = getClassLoader();
			fileName = SERVICE_CONF_DIRECTORY + type.getName();
			Enumeration<URL> urls;
			if (classLoader != null) {
				urls = classLoader.getResources(fileName);
			} else {
				urls = ClassLoader.getSystemResources(fileName);
			}

			if (urls != null) { // 找到的urls为null，或是没有找到文件，即认为是没有找到扩展点
				while (urls.hasMoreElements()) {
					URL url = urls.nextElement();
					readService0(serviceName2Class, name2Attributes, classLoader, url);
				}
			}
		} catch (Throwable t) {
			logger.error("Exception when load service point(interface: " + type.getName() + ", description file: " +
					fileName + ").", t);
		}

		nameMapping.set(serviceName2Class);
		this.nameAttributesMapping = name2Attributes;
	}
```

`getClassLoader()`方法如下所示。

先获取当前线程的上下文类加载器，如果为空则返回 `ServiceLoader`类的类加载器。

```java
	private static ClassLoader getClassLoader() {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader != null) {
			return classLoader;
		}
		classLoader = ServiceLoader.class.getClassLoader();
		if (classLoader != null) {
			return classLoader;
		}
		return classLoader;
	}
```



# debug复现

第一次加载汇率配置类时，判断汇率配置接口的 ServersLoader 实例中`nameMapping`为空，初始化加载所有实现类。

![image-20230214105502634](https://gitee.com/humingk/image/raw/master/image/2023/02/202302141055789.png)

在获取汇率配置接口的默认实现类时，发现`getServiceClasses()`方法并没有加载该配置接口的所有实现类，包括默认实现类。

导致抛出类加载失败异常。

![image-20230112212905585](https://gitee.com/humingk/image/raw/master/image/2023/01/202301122129721.png)

如下图所示，当前线程的上下文类加载器为`null`。

![image-20230214194829493](https://gitee.com/humingk/image/raw/master/image/2023/02/202302141948718.png)

如下图所示，当前线程的线程池为`ForkJoinPool`，其中的`contextClassLoader`为`null`。

![image-20230214195159434](https://gitee.com/humingk/image/raw/master/image/2023/02/202302141951536.png)



# 本地复现

暂无



# 解决方案 





# 附录

