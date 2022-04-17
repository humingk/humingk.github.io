---
layout: post
title : 消失了的那些天——Java中的夏令时
categories : java
description : 坑
keywords : java,datetime
---

出生于1989年4月16日的用户，竟然无法在我们的系统里下单，这是怎么一回事呢？

---

# 背景

事情是这样子的，前段时间我们的系统error日志里出现了这样的异常栈：

![image-20220412193733827](https://s2.loli.net/2022/04/12/JucBWY4iEph6wGQ.png)

在代码里是这样用的：

![image-20220412193554490](https://s2.loli.net/2022/04/12/5gkN8o7tuD4lFqh.png)

其中，`DateUtil.dateFormatter`是这样的：

![image-20220412193656061](https://s2.loli.net/2022/04/12/Or3tzym16wSDYCi.png)

看到这里就困惑了，`1989-04-16`怎么就不符合`YYYY-MM-dd`格式了呢？

看报错信息：`Illegal instant due to time zone offset transition (Asia/Shanghai)`



# 解决方案



### 使用UTC时区

![image-20220417135326354](https://s2.loli.net/2022/04/17/ugnLzApec9Paxd7.png)





### 使用LocalDate





# 附加问题

另外，示例代码中的`YYYY-MM-dd`格式是错误用法——[Java中的周年](https://humingk.github.io/java-week_day)