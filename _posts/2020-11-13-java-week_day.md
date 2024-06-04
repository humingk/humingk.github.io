---
layout: post
title : 大小写造成的悲剧——Java中的周年
categories : java
---

`2020年`变成了`2021年`，`yyyy-MM-dd`和`YYYY-MM-dd`的背后原来是这么回事

---

# 背景

上次说到 [Java中的夏令时](https://humingk.github.io/java-daylight_saving_time)，其中的示例代码还有一个经典的格式错误用法，那就是小写的`y`和大写的`Y`



### 语义区别

`YYYY-MM-dd`和`yyyy-MM-dd`是不一样的，我们通常用的是`yyyy-MM-dd`

> `y`和`Y`的语义区别：
>
> - `y`表示的是普通的`year`
> - `Y`表示的是`week year`，周年，表示当天所在的周所在的年份，**只要本周跨年，那么这周就属于下一年**
>
> | Letter | Date or Time Component | Presentation                                                 | Examples     |
> | ------ | ---------------------- | ------------------------------------------------------------ | ------------ |
> | `y`    | Year                   | [Year](https://docs.oracle.com/javase/8/docs/api/java/text/SimpleDateFormat.html#year) | `1996`; `96` |
> | `Y`    | Week year              | [Year](https://docs.oracle.com/javase/8/docs/api/java/text/SimpleDateFormat.html#year) | `2009`; `09` |
>
> 用周年通常使用格式`Y-w`，`Y`表示周年，`w`表示当前周年的第几个周
>
> 
>
> 参考：[JDK1.8的SimpleDateFormat文档](https://docs.oracle.com/javase/8/docs/api/java/text/SimpleDateFormat.html)



### 什么是周年？

某些场景下，我们为一年中的每个星期分配一个数字，这样就能把年**按照周的方式划分**而不是按月的方式划分

但在不同的标准中有不同的定义

> 在跨年周中，周年的第一周有这样等效、兼容的定义：
>
> - 第一个工作日所在的周
> - 第一个星期四所在的周
> - 1月4号所在的周
> - 大部分在1月份的周，四天及其以上
> - 从12月29号——1月4号期间星期一所在的周
>
> 因此，如果 1 月 1 日在星期一、星期二、星期三或星期四，则在第 01 周。如果 1 月 1 日在星期五、星期六或星期日，则在上一年的第 52 或 53 周（有没有第 00 周）。12 月 28 日总是一年中的最后一周。
>
> 
>
> 在ISO-8601的标准中，ISO周从第一周的星期一开始
>
> 周年有52或53个整周，每个整周是7天，所以一个周年有364或371天
>
> 
>
> 参考：[ISO-8601#week_dates](https://en.wikipedia.org/wiki/ISO_8601#Week_dates)



### 例子

举个例子，`2020-12-31`2020年的最后一天是周四，属于跨年的周

使用`yyyy-MM-dd`格式，输出`2020-12-31`

```java
        // 2020-12-31
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, Calendar.DECEMBER, 31);
        Date date = calendar.getTime();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = format.format(date);
        System.out.println(dateStr);
        // 2020-12-31
```

使用`YYYY-MM-dd`格式，却输出`2021-12-31`

```java
        // 2020-12-31
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, Calendar.DECEMBER, 31);
        Date date = calendar.getTime();

        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
        String dateStr = format.format(date);
        System.out.println(dateStr);
        // 2021-12-31
```

当然，使用`java.time.LocalDate`也是如此，使用`YYYY-MM-dd`格式，输出`2021-12-31`

```java
        // 2020-12-31
        LocalDate date = LocalDate.of(2020, 12, 31);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd");
        String dateStr = date.format(formatter);
        System.out.println(dateStr);
        // 2021-12-31
```

他们都使用了相同的标准：`ISO-8601`



但此时发现了一个问题，如果使用`YYYY-MM-dd`格式来解析`2020-12-27`（周日），却输出`2021-12-27`

```java
        // 2020-12-27
        LocalDate date = LocalDate.of(2020, 12, 27);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd");
        String dateStr = date.format(formatter);
        System.out.println(dateStr);
        // 2021-12-27
```

从上文按照`ISO-8601`标准：一周应该是从周一开始的

周日这一天应该属于2020周年的最后一周，而不属于跨年周呀？不应该输出`2020-12-27`吗？



原来，这和`java.util.Locale`也有关：

- 如果在美洲——美国，一周是从**周日**开始的，这时解析`2020-12-27`（周日）会输出`2021-12-27`

  ```java
          // 2020-12-27
          LocalDate date = LocalDate.of(2020, 12, 27);
          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd", Locale.US);
          String dateStr = date.format(formatter);
          System.out.println(dateStr);
          // 2021-12-27
  ```

- 如果在欧洲——英国，一周是从**周一**开始的，这时解析`2020-12-27`（周日）会输出`2020-12-27`

  ```java
          // 2020-12-27
          LocalDate date = LocalDate.of(2020, 12, 27);
          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd", Locale.UK);
          String dateStr = date.format(formatter);
          System.out.println(dateStr);
          // 2020-12-27
  ```
  
- 如果是阿拉伯国家——阿富汗，一周是从**周六**开始的，这时解析`2020-12-26`（周六）会输出`2021-12-26`

  ```java
          // 2020-12-26
          LocalDate date = LocalDate.of(2020, 12, 26);
          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd", Locale.forLanguageTag("ar-AF"));
          String dateStr = date.format(formatter);
          System.out.println(dateStr);
          // 2021-12-26
  ```

甚至在不同的JDK版本中，由于默认值的设定，周年表现的也不一样（主要是一周到底是从哪一天开始）

可参考stackoverflow：[在JVM不同版本中WeekFields的不同表现](https://stackoverflow.com/questions/55139324/different-behavior-of-weekfields-on-jvm-8-and-jvm-10)



### 源代码分析

我们可以通过源代码分析`2020`年是怎么变成`2021`年的



正常情况下对`yyyy-MM-dd`的解析，在解析`y`的时候，取到的`TemporalField`是`YearOfEra`，表示常规年份

`java.time.format.DateTimeFormatterBuilder#parseField`

![image-20220417144917612](https://raw.githubusercontent.com/humingk/resource/master/image/2020/Lt3oBIzvUFbeNDT.png)

这是Java8文档中关于`yearOfEra`的官方定义：

```java
    /**
     * The year within the era.
     * <p>
     * This represents the concept of the year within the era.
     * This field is typically used with {@link #ERA}.
     * <p>
     * The standard mental model for a date is based on three concepts - year, month and day.
     * These map onto the {@code YEAR}, {@code MONTH_OF_YEAR} and {@code DAY_OF_MONTH} fields.
     * Note that there is no reference to eras.
     * The full model for a date requires four concepts - era, year, month and day. These map onto
     * the {@code ERA}, {@code YEAR_OF_ERA}, {@code MONTH_OF_YEAR} and {@code DAY_OF_MONTH} fields.
     * Whether this field or {@code YEAR} is used depends on which mental model is being used.
     * See {@link ChronoLocalDate} for more discussion on this topic.
     * <p>
     * In the default ISO calendar system, there are two eras defined, 'BCE' and 'CE'.
     * The era 'CE' is the one currently in use and year-of-era runs from 1 to the maximum value.
     * The era 'BCE' is the previous era, and the year-of-era runs backwards.
     * <p>
     * For example, subtracting a year each time yield the following:<br>
     * - year-proleptic 2  = 'CE' year-of-era 2<br>
     * - year-proleptic 1  = 'CE' year-of-era 1<br>
     * - year-proleptic 0  = 'BCE' year-of-era 1<br>
     * - year-proleptic -1 = 'BCE' year-of-era 2<br>
     * <p>
     * Note that the ISO-8601 standard does not actually define eras.
     * Note also that the ISO eras do not align with the well-known AD/BC eras due to the
     * change between the Julian and Gregorian calendar systems.
     * <p>
     * Non-ISO calendar systems should implement this field using the most recognized
     * year-of-era value for users of the calendar system.
     * Since most calendar systems have only two eras, the year-of-era numbering approach
     * will typically be the same as that used by the ISO calendar system.
     * The year-of-era value should typically always be positive, however this is not required.
     */
    YEAR_OF_ERA("YearOfEra", YEARS, FOREVER, ValueRange.of(1, Year.MAX_VALUE, Year.MAX_VALUE + 1)),
```



对`YYYY-MM-dd`的解析，在解析`Y`的时候，新建了一个基于周年的属性解析器`WeekBasedFieldPrinterParser`

`java.time.format.DateTimeFormatterBuilder#parsePattern`

![image-20220417145919501](https://raw.githubusercontent.com/humingk/resource/master/image/2020/MIPq2rDCwlJdUbf.png)

这是Java8文档中关于`WeekBasedFieldPrinterParser`类的定义，可以看到**确实依赖了当前的语言环境**locale：

```java
    /**
     * Prints or parses a localized pattern from a localized field.
     * The specific formatter and parameters is not selected until the
     * the field is to be printed or parsed.
     * The locale is needed to select the proper WeekFields from which
     * the field for day-of-week, week-of-month, or week-of-year is selected.
     */
    static final class WeekBasedFieldPrinterParser implements DateTimePrinterParser {
```



在解析了时间格式之后，计算年份的时候

关键代码定位：`java.time.temporal.WeekFields.ComputedDayOfField#localizedWeekBasedYear`

![image-20220412212320193](https://raw.githubusercontent.com/humingk/resource/master/image/2020/DdCZ6ifmrjKaTSH.png)

可以看到，在计算年份的时候，首先计算当天在当前年的周数：

- 如果是`0`，表示当天是在去年最后一周的，则`year-1`
- 如果不是`0`，则看当前周是不是属于`跨越周`，如果属于跨越周，则`year+1`



在这段代码里，有一段关键逻辑，根据参考日计算当前年的周年数

`int newYearWeek = computeWeek(offset, yearLen + weekDef.getMinimalDaysInFirstWeek());`

定位`java.time.temporal.WeekFields.ComputedDayOfField#computeWeek`

![image-20220427125643148](https://raw.githubusercontent.com/humingk/resource/master/image/2020/ATSIXQRHYmV6M8O.png)

其中的offset表示当前day与第一个完整的周开始的那天的偏移量

offset计算方式定位`java.time.temporal.WeekFields.ComputedDayOfField#startOfWeekOffset`

![image-20220427130053617](https://raw.githubusercontent.com/humingk/resource/master/image/2020/5e14uKkp6AXtlB7.png)