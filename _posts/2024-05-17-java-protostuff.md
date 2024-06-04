---
layout: post
title : 线上bug：当deprecated注解遇到protostuff序列化
categories : java 
---

有时候会用deprecated注解表示该字段已经废弃或即将废弃，protostuff序列化会直接忽略这个字段。

---

[TOC]

# 背景

我们的业务数据在搜索阶段和填单阶段出现了不一致， 少了`drawbackMapping`字段，导致用户看到的退改规则不一致。

数据DIFF如下：

![image-20240517153053809](https://raw.githubusercontent.com/humingk/resource/master/image/2024/202405171537824.png)

代码如下：

![image-20240517154637929](https://raw.githubusercontent.com/humingk/resource/master/image/2024/202405171546998.png)

# 排查思路

### 范围缩小

首先，`drawbackMapping`字段存在已久，排除最有可能原因：Jar包版本不一致导致字段缺失。

然后依次检查上游系统链路日志，定位到是在我们的上游交易系统中，丢失了`drawbackMapping`字段。

上游交易系统链路比较长，排查日志和代码也比较麻烦。

此时发现这个字段与其它字段唯一的区别就是有一个`@Deprecated`注解，开始猜测最有可能的原因：序列化和反序列化。

### 猜测1：Json序列化

交易系统用的Jackson工具，梳理一下Jackson序列化丢失字段的常见原因：

- `getter`方法和`setter`方法问题
  - 字段属性为`private`且`getter`方法和`setter`缺失
  - 使用lombok注解自动生成`getter`和`setter`方法，属性命名首字母小写第二个字母大写。例如`aBcd`，lombok生成`getABcd`方法，jackson截取`ABcd`后会将连续大写字母转成小写最终变成`abcd`而不是`aBcd`
  - 使用`@JsonIgnore`注解忽略字段

### 猜测2：Protostuff序列化

继续排查其它序列化方式，发现交易系统部分流程使用了protostuff序列化

protostuff竟然会忽略`@Deprecated`注解字段！

> 来自 [Protostuff官方文档](https://protostuff.github.io/docs/protostuff-runtime/)：
>
> ![image-20240517203809817](https://raw.githubusercontent.com/humingk/resource/master/image/2024/202405172038943.png)

### Java中的deprecated注解官方解释

> 来自 [Java8官方文档](https://docs.oracle.com/javase/8/docs/api/java/lang/Deprecated.html)：
>
> A program element annotated @Deprecated is one that programmers are discouraged from using, typically because it is dangerous, or because a better alternative exists. Compilers warn when a deprecated program element is used or overridden in non-deprecated code.

后续版本的解释都差不多。

# 总结

用`@Deprecated`的初衷是表明该字段不建议被使用，如果还没有完全废弃的话不推荐使用。

以前就踩过`protostuff`的一个坑，在类里面新增属性没有加在最末尾，结果上游系统用了protostuff，导致反序列化失败。

在使用`protostuff`的时候，应该对下游系统负责。

