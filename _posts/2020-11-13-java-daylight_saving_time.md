---
layout: post
title : 消失了的那些天——Joda中的夏令时
categories : java
---

出生于1989年4月16日的用户，竟然无法在我们的系统里下单，这是怎么一回事呢？

记一次排查Joda时区问题的记录

---

# 背景

事情是这样子的，前段时间我们的系统日志里出现了这样的异常栈：

![image-20220412193733827](https://raw.githubusercontent.com/humingk/resource/master/image/2020/JucBWY4iEph6wGQ.png)

在代码里是这样用的：

![image-20220412193554490](https://raw.githubusercontent.com/humingk/resource/master/image/2020/5gkN8o7tuD4lFqh.png)

其中，`DateUtil.dateFormatter`是这样的：

![image-20220412193656061](https://raw.githubusercontent.com/humingk/resource/master/image/2020/Or3tzym16wSDYCi.png)

看到这里就困惑了，`1989-04-16`怎么就不符合`YYYY-MM-dd`格式了呢？

（注：此处`YYYY-MM-dd`属于格式使用错误，正确使用方式应是`yyyy-MM-dd`，详见另文——[Java中的周年](https://humingk.github.io/java-week_day)）



# 问题排查

### 例子测试

问题比较诡异，难以定位，我们分别用`java.time` 和 `org.joda.time`测试一下



1. 使用`java.time.LocalDate`解析`1989-04-16`，正常解析

```java
        java.time.format.DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        java.time.LocalDate date = LocalDate.parse("1989-04-16", formatter);
        System.out.println(date);
        // 1989-04-16
```

2. 使用`java.time.LocalDateTime`解析`1989-04-16 00:00:00`，正常解析

```java
        java.time.format.DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        java.time.LocalDateTime dateTime = LocalDateTime.parse("1989-04-16 00:00:00", formatter);
        System.out.println(dateTime);
        // 1989-04-16T00:00
```

3. 使用`org.joda.time.LocalDate`通过`parse`方法解析`1989-04-16`，正常解析

```java
        org.joda.time.format.DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        org.joda.time.LocalDate date = LocalDate.parse("1989-04-16", formatter);
        System.out.println(date);
        // 1989-04-16
```

4. 使用`org.joda.time.LocalDate`通过`parseLocalDateTime`方法解析`1989-04-16 00:00:00`，正常解析

```java
        org.joda.time.format.DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = formatter.parseLocalDateTime("1989-04-16 00:00:00");
        System.out.println(dateTime);
        // 1989-04-16T00:00:00.000
```

5. 使用`org.joda.time.LocalDateTime`通过`parseDateTime`方法解析`1989-04-16 00:00:00`，**报错**`IllegalInstantException`

```java
        org.joda.time.format.DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        org.joda.time.DateTime dateTime = formatter.parseDateTime("1989-04-16 00:00:00");
        System.out.println(dateTime);

//        Exception in thread "main" org.joda.time.IllegalInstantException: Cannot parse "1989-04-16 00:00:00": Illegal instant due to time zone offset transition (Asia/Shanghai)
//        at org.joda.time.format.DateTimeParserBucket.computeMillis(DateTimeParserBucket.java:471)
//        at org.joda.time.format.DateTimeParserBucket.computeMillis(DateTimeParserBucket.java:411)
//        at org.joda.time.format.DateTimeFormatter.parseDateTime(DateTimeFormatter.java:928)
```

我们换一个日期

6. 使用`org.joda.time.LocalDateTime`通过`parseDateTime`方法解析`1989-04-15 00:00:00`，正常解析

```java
        org.joda.time.format.DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        org.joda.time.DateTime dateTime = formatter.parseDateTime("1989-04-15 00:00:00");
        System.out.println(dateTime);
        // 1989-04-15T00:00:00.000+08:00
```



# 分析思路

看报错信息：`Illegal instant due to time zone offset transition (Asia/Shanghai)`

竟然报的是时区偏移错误！

整理了一下上面的几个测试例子，主要有以下三个问题待解决：

1. 可以理解非闰年的2月份没有29号，但`1989-04-16`，怎么可能没有这一天呢？
2. `java.time`的`parse`方法能够正常解析，`joda`的`parseLocalDateTime`能正常解析，但`parseDateTime`却不能正常解析？
3. 为什么`1989-04-16`不能解析，但前一天`1989-04-15`就可以正常解析？

来debug一下源代码



---

### 异常解析的debug

在计算Unix时间戳的时候，有一段对`iZone.getOffsetFromLocal`方法和`iZone.getOffset`方法算出来的偏移量进行比较的逻辑

异常就是从这里抛出来的

`org.joda.time.format.DateTimeParserBucket#computeMillis(boolean, java.lang.CharSequence)`

![image-20220505170236704](https://raw.githubusercontent.com/humingk/resource/master/image/2020/UGY9ytHuCaxDOXi.png)

`unix时间戳=608688000000ms`

通过`iZone.getOffset`方法，返回的是`iOffset=32400000`

`getOffset`方法看代码意思是返回一个偏移量，我们的UTC时间加上这个偏移量就是本地时间

即：`UTC时间 + getOffset偏移量 = 本地时间`

`org.joda.time.tz.CachedDateTimeZone#getOffset`

![image-20220505181926122](https://raw.githubusercontent.com/humingk/resource/master/image/2020/uGNQFdkIPn279wD.png)

通过`iZone.getOffsetFromLocal`方法，返回的是`offsetAdjusted=28800000`

`getOffsetFromLocal`方法看代码意思是返回一个偏移量，我们用本地时间减去这个偏移量就是UTC时间

即：`本地时间 - getOffsetFromLocal偏移量 = UTC时间`

`org.joda.time.DateTimeZone#getOffsetFromLocal`

![image-20220505181434379](https://raw.githubusercontent.com/humingk/resource/master/image/2020/9lY8r5hXoAnRH6m.png)

以上是解析`1989-94-16`的时候报错的逻辑



### 为什么joda要校验两个偏移量？



`iOffset=32400000`和`offsetAdjusted=28800000`这两个不一致，导致抛出时区异常

这里为什么会做一个偏移量的校验？其实很好理解，根据上面的代码逻辑分析：

1. `joda`用本地时间减去`getOffsetFromLocal`方法根据本地时间（时区）计算出的偏移量，得到UTC时间
2. `joda`用`getOffet`方法根据上面得到的UTC时间计算出一个新的偏移量
3. 这两者计算出来的偏移量，按理说应该是一致的，不一致的话说明是有问题的，故抛出异常！

根据：

`UTC时间 + getOffset偏移量 = 本地时间`

`本地时间 - getOffsetFromLocal偏移量 = UTC时间`

得出应该满足：

`getOffset偏移量 = getOffsetFromLocal偏移量`



---

### 正常解析的debug

我们再看一下正常解析前一天`1989-04-15`的时候此处是什么样的

`org.joda.time.format.DateTimeParserBucket#computeMillis(boolean, java.lang.CharSequence)`

![image-20220505182627459](https://raw.githubusercontent.com/humingk/resource/master/image/2020/TnDjXZqPhfbgcIQ.png)

通过`getOffset`方法计算出的偏移量是`iOffset=28800000`

`org.joda.time.tz.CachedDateTimeZone.Info#getOffset`

![image-20220505182933271](https://raw.githubusercontent.com/humingk/resource/master/image/2020/e2sYObkp49DWjoX.png)

`iOffset=28800000`和`offsetAdjusted=28800000`这两个一致，校验成功，正常返回时间戳



---

### 正常解析和异常解析中的偏移量解析来源

以下是错误解析`1989-04-16 00:00:00`的`iOffset=32400000`来源

![image-20220506205034563](https://raw.githubusercontent.com/humingk/resource/master/image/2020/3opSLTDEIQ9CKRB.png)

以下是正确解析前一天`1989-04-15 00:00:00`的`iOffset=28800000`来源

![image-20220506205552360](https://raw.githubusercontent.com/humingk/resource/master/image/2020/CznkR72wZcQE89W.png)



---



### 为什么偏移量相差一个小时？



那么问题来了：

错误解析`1989-04-16 00:00:00`，`getOffset`方法计算的偏移量是`iOffset=32400000`，即**9个小时**

正常解析前一天`1989-04-15 00:00:00`，`getOffset`方法计算的偏移量是`iOffset=28800000`，即**8个小时**



算一下这两个偏移量的差值，**两者竟相差一个小时！**

上海时区是东八区，偏移量按理说就应该是8个小时，难道4月16号这一天的偏移量，算错啦？？？算成东九区啦？？？



查询资料发现，**夏令时**机制会导致这样的情况发生

也就是在4月16号那一天，如果把时间拨快一个小时，那一天的时区偏移量就应该是**9个小时**



### 夏令时机制



> 什么是夏令时？
>
> 
>
> 夏令时，表示为了节约能源，人为规定时间的意思。也叫夏时制，夏令时（Daylight Saving Time：DST），又称“[日光节约时制](https://baike.baidu.com/item/日光节约时制/10349737)”和“夏令时间”，在这一制度实行期间所采用的统一时间称为“夏令时间”。一般在天亮早的夏季人为将时间调快一小时，可以使人早起早睡，减少照明量，以充分利用光照资源，从而节约照明用电。各个采纳夏时制的国家具体规定不同。全世界有近110个国家每年要实行夏令时。
>
> 
>
> ————[百度百科-夏令时](https://baike.baidu.com/item/夏令时)



而刚好在1986-1991年，我国短暂实行过夏令时机制



> 我国大陆实行过夏令时？
>
> 
>
> 1986年4月，中国中央有关部门发出“在全国范围内实行夏时制的通知”，具体做法是：每年从**四月中旬第一个星期日的凌晨2时整**（北京时间），将时钟拨快一小时，即将表针由2时拨至3时，夏令时开始；到九月中旬第一个星期日的凌晨2时整（[北京夏令时](https://baike.baidu.com/item/北京夏令时/1882131)），再将时钟拨回一小时，即将表针由2时拨至1时，夏令时结束。从1986年到1991年的六个年度，除1986年因是实行夏时制的第一年，从5月4日开始到9月14日结束外，其它年份均按规定的时段施行。在夏令时开始和结束前几天，新闻媒体均刊登有关部门的通告。1992年起，夏令时暂停实行。
>
> 
>
> ————[百度百科-夏令时](https://baike.baidu.com/item/夏令时)



`四月中旬第一个星期日的凌晨2时整`在1989年刚好是`1989-04-16`这一天！



![image-20220507171604788](https://raw.githubusercontent.com/humingk/resource/master/image/2020/XpRdunbWmAoxi3r.png)



在这一天的凌晨2点整，我们将时间拨到了3点整，当天的时区偏移量是9个小时，没毛病。。。



# 解决方案



### 使用parseLocalDateTime方法

根据上文的测试例子，我们可以发现，如果我们用`joda`工具包的话，用`parseLocalDateTime`方法即可正常解析

而且joda的官方建议也是这样：

> ![image-20220507181906777](https://raw.githubusercontent.com/humingk/resource/master/image/2020/RmfOye4dJib6rct.png)
>
> ————[What does 'Illegal instant due to time zone offset transition' mean?](http://joda-time.sourceforge.net/faq.html#illegalinstant)



### 使用UTC时区

上面是通过解析本地时间的方式，我们也可以指定一个标准UTC时区，使用`parseDateTime`方法

```java
        org.joda.time.format.DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        org.joda.time.DateTime dateTime = formatter.withZoneUTC().parseDateTime("1989-04-16 00:00:00");
        System.out.println(dateTime);
        // 1989-04-16T00:00:00.000Z
```



# 拓展

### Java的时区维护



那么Java是怎么知道中国在`1986~1991`年之前实行过夏令时呢？它存储在哪里的？

这么多国家时区和夏令时的变化，它是通过什么样的方式来维护的呢？



经过查阅资料，JDK也一直在维护一个时区数据的版本更新记录：[Timezone Data Versions in the JRE Software](https://www.oracle.com/java/technologies/tzdata-versions.html)



而JDK的时区数据，原来是存放在`jdk1.8.0_271\jre\lib\tzdb.dat`目录

`java.time.zone.TzdbZoneRulesProvider#TzdbZoneRulesProvider`

![image-20220506211811346](https://raw.githubusercontent.com/humingk/resource/master/image/2020/OfgNybMGp8Za6tu.png)



### joda的时区维护

joda用的是自己维护的一套时区数据

![image-20220507173053791](https://raw.githubusercontent.com/humingk/resource/master/image/2020/xLtAkerDZBXhg28.png)



# 总结

1. 在使用`org.joda.time`工具包的时候：
   - 尽量使用`parseLocalDateTime`方法而不是`parseDateTime`方法
   - 使用`parseDateTime`方法需要注意当前时区的问题（指定时区）

2. 不只要知其然，还要知其所以然，我们经常忽视常用的工具方法中的细节，很多问题的答案都可以直接在源代码里找到
3. 细节很重要，在巨量下，小细节问题造成的影响会成倍放大，比如此文的问题，我国在1986-1991的6年间，共计调整过12次时间，也就是说在我们系统中，只要用户的生日在这12天之中，就无法成功生单，根据部分异常日志统计，就有好几千条失败记录



# 附加问题

另外，示例代码中的`YYYY-MM-dd`格式是经典的格式错误用法，具体分析可见——[Java中的周年](https://humingk.github.io/java-week_day)