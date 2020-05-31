---
layout: post
title : 正则表达式基础
categories : algorithms
description : 
keywords : regexp
---

正则表达式就是那种，写的时候看文档，写完之后能用，过段时间看不懂，下一次写的时候又要看文档...这里总结复习一下基础语法及常用例子，也方便快速查阅

---

## 正则表达式 基础语法

| \		|转义字符	|eg: \\ 匹配 \ , \n 匹配 n|

### 定位符

| ^		|匹配字符串开始位置，[]内除外||
| $		|匹配字符串结束位置||
| \b	|匹配单词边界		|eg: er\b 匹配 never	中的 er|
| \B	|非单词边界匹配		|eg: er\B 匹配 verb	中的 er|

### 非打印字符

| \cx	|匹配由x的控制字符		|eg: \cM 匹配 Ctrl-M \| Enter|
| \d	|匹配一个数字字符|等价于 [0-9]|
| \D	|匹配一个非数字字符|等价于 [^0-9]|
| \u4e00-\u9fa5	|匹配中文字符	|eg: [^\u4e00-\u9fa5] 匹配非中文字符


| \f	|匹配换页符|等价于 \x0c|
| \n	|匹配换行符|等价于 \x0a|
| \r	|匹配回车符|等价于 \x0d|
| \t	|匹配制表符|等价于 \x09|
| \v	|匹配垂直制表符|等价于 \x0b|


| \s	|匹配任意空白字符,包括空格、制表符、换页符等等|等价于[\f\n\r\t\v]|
| \S	|匹配任何非空白字符|等价于[^\f\n\r\t\v]|

### 限定符

| *		|匹配前面的字表达式0次或多次|等价于：{0,}	|eg: zo* 匹配 z \| zo \| zoo ... |
| +		|匹配前面的字表达式1次或多次|等价于：{1,}	|eg: zo* 匹配 zo \| zoo ... |
| ?		|匹配前面的字表达式0次或1次|等价于：{0,1}	|eg: zo* 匹配 z \| zo , do(es)* 匹配 do \| does|


| {n}	|表示匹配n次						|eg: o{2}  匹配 aoob|
| {n,}	|表示至少匹配n次					|eg: o{2} 匹配 aoob \| aooob ...|
| {n,m}	|表示至少匹配n次，至多匹配m次	|eg: o{1,3} 匹配 aob \| aoob \| aooob|


- ？ 当该字符紧跟在任何一个其他限制符（*,+,?，{n}，{n,}，{n,m}）后面时，匹配模式是非贪婪的



PS： 默认的匹配为贪婪模式，即尽可能多的匹配所搜索的字符串

 eg: 对于 oooo， o+ 将匹配所有 o ,而非贪婪模式  o+? 将匹配单个 o 



### 特殊字符

| .				|匹配除换行符 \n 以外所有的单个字符|

| ()			|标记一个子表达式的开始结束位置			|为了提取匹配的字符串,表达式中有几个()就有几个相应的匹配字符串|
| []			|标记一个中括号表达式的开始结束位置		|定义匹配的字符范围,比如 [0-9] 表示相应位置的字符要匹配数字字符|
| {}			|标记限定符表达式的开始结束位置			|用来表示匹配的长度，比如 \s{1,3} 表示匹配一到三个空格|

| (pattern)			|匹配pattern并且获取匹配结果||
| (?:pattern)		|匹配但不获取结果	|eg: dut(?:y\|ies) 等价于 duty \| duties , duty duties 匹配 duty duties 中的 dut dut|


- 正向预查(不获取结果)


    | (?=pattern)	|正向匹配			|eg: dut(?=y) , duty duties duty-free 匹配 duty 中的 dut|
    | (?!pattern)	|正向不匹配			|eg: dut(?!y) , duty duties duty-free 匹配 duties 中的 dut|


- 反向预查(不获取结果)


    | (?<=pattern)	|反向匹配			|eg: dut(?<=y) , duty duties duty-free 匹配 duty-free 中的 dut |		eg2:	(?<=J)a , Java 匹配 第一个a|
    | (?<!pattern)	|反向不匹配			|eg: dut(?<!y) , duty duties duty-free 匹配 duties 中的 dut |		eg2:	(?<!J)a , Java 匹配 第二个a|


| x\|y		|匹配x或y		|eg: (z\|f)ood, 匹配 zood \| food|
| [xyz]		|匹配x或y或z		|eg: [abc] 匹配 plain 中的 a|
| [^xyz]	|不匹配x或y或z	|eg: [^abc] 匹配 plain 中的 p l i n|
| [a-z]		|匹配a-z的任一字符||
| [^a-z]	|不匹配a-z的任一字符||
| \w		|匹配包括下划线的任何单词字符|等价于[A-Za-z0-9_]|
| \W		|匹配任何非单词字符|等价于[^A-Za-z0-9_]|

## 正则表达式 常用例子

| 多数字字符串		|([0-9]+)|
| 非负整数			|^\d+|
| 浮点数			|^(-?\d+)(\.\d+)?$ 或  [-]?[0-9]+\.?[0-9]+|


| 用户名			|/^[a-z0-9_-]{3,16}$/|
| 邮箱				|/^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/|
| URL				|/^(https?:\/\/)?([\da-z\.-]+)\.([a-z\.]{2,6})([\/\w \.-]*)*\/?$/|

### 正则表达式 python 应用

#### re.match(pattern, string, flags=0)

re.match 尝试从字符串的起始位置匹配一个模式，如果不是起始位置匹配成功的话，match()就返回none

- pattern	匹配的正则表达式
- string	要匹配的字符串
- flags		标志位，用于控制正则表达式的匹配方式
    - re.I		使匹配对大小写不敏感
    - re.L		做本地化识别（locale-aware）匹配
    - re.M		多行匹配，影响 ^ 和 $
    - re.S		使 . 匹配包括换行在内的所有字符
    - re.U		根据Unicode字符集解析字符。这个标志影响 \w, \W, \b, \B.
    - re.X		该标志通过给予你更灵活的格式以便你将正则表达式写得更易于理解

eg:  

```python
line = "Cats are smarter than dogs"
matchObj = re.match( r'(.*) are (.*?) .*', line, re.M|re.I)
```

#### re.search(pattern, string, flags=0)

re.search 扫描整个字符串并返回**第一个**成功的匹配

#### re.match与re.search的区别

re.match只匹配字符串的开始，如果字符串开始不符合正则表达式，则匹配失败，函数返回None

而re.search匹配整个字符串，直到找到一个匹配。

#### re.sub(pattern, repl, string, count=0)

re.sub用于替换字符串中的匹配项

- pattern	正则中的模式字符串。
- repl		替换的字符串，也可为一个函数。
- string	要被查找替换的原始字符串。
- count		模式匹配后替换的最大次数，默认 0 表示替换所有的匹配。

```python
phone = "2004-959-559 # 这是一个电话号码"
 
# 删除注释
num = re.sub(r'#.*$', "", phone)
print ("电话号码 : ", num)
 
# 移除非数字的内容
num = re.sub(r'\D', "", phone)
print ("电话号码 : ", num)
```

#### re.compile(pattern[, flags])

compile 函数用于编译正则表达式，生成一个正则表达式（ Pattern ）对象，供 match() 和 search() 这两个函数使用

- pattern	 一个字符串形式的正则表达式
- flags		可选，表示匹配模式，比如忽略大小写，多行模式等，具体参数为：
    - re.I		忽略大小写
	- re.L		表示特殊字符集 \w, \W, \b, \B, \s, \S 依赖于当前环境
	- re.M		多行模式
	- re.S		即为' . '并且包括换行符在内的任意字符（' . '不包括换行符）
	- re.U		表示特殊字符集 \w, \W, \b, \B, \d, \D, \s, \S 依赖于 Unicode 字符属性数据库
	- re.X		为了增加可读性，忽略空格和' # '后面的注释


```python
>>>import re
>>> pattern = re.compile(r'\d+')                    # 用于匹配至少一个数字
>>> m = pattern.match('one12twothree34four')        # 查找头部，没有匹配
>>> print m
```

#### findall(string[, pos[, endpos]])

在字符串中找到正则表达式所匹配的所有子串，并返回一个列表，如果没有找到匹配的，则返回空列表。  
注意： match 和 search 是匹配一次 findall 匹配所有。

- string	待匹配的字符串。
- pos		可选参数，指定字符串的起始位置，默认为 0。
- endpos	可选参数，指定字符串的结束位置，默认为字符串的长度。

```python
import re
 
pattern = re.compile(r'\d+')   # 查找数字
result1 = pattern.findall('runoob 123 google 456')
result2 = pattern.findall('run88oob123google456', 0, 10)
 
print(result1)
print(result2)

```
输出结果：
```
['123', '456']
['88', '12']

```

#### re.split(pattern, string[, maxsplit=0, flags=0])

split 方法按照能够匹配的子串将字符串分割后返回列表

- pattern	匹配的正则表达式
- string	要匹配的字符串。
- maxsplit	分隔次数，maxsplit=1 分隔一次，默认为 0，不限制次数。
- flags		标志位，用于控制正则表达式的匹配方式，如：是否区分大小写，多行匹配等等。

```python
>>>import re
>>> re.split('\W+', 'runoob, runoob, runoob.')
['runoob', 'runoob', 'runoob', '']
>>> re.split('(\W+)', ' runoob, runoob, runoob.') 
['', ' ', 'runoob', ', ', 'runoob', ', ', 'runoob', '.', '']
>>> re.split('\W+', ' runoob, runoob, runoob.', 1) 
['', 'runoob, runoob, runoob.']
 
>>> re.split('a*', 'hello world')   # 对于一个找不到匹配的字符串而言，split 不会对其作出分割
['hello world']
```

### 正则表达式 Java 应用

#### 关于Java中的转义字符 '\\'

- 在匹配 `.` 或 `{` 或 `[` 或 `(` 或 `?` 或 `$` 或 `^` 或 `*` 这些特殊字符时，需要在前面加上 `\\`，比如匹配 `.` 时，Java 中要写为 `\\.`，但对于正则表达式来说就是 `\.`。
- 在匹配 `\` 时，Java 中要写为 `\\\\`，但对于正则表达式来说就是 `\\`。

PS：Java 中的正则表达式字符串有两层含义，**首先 Java 字符串转义出符合正则表达式语法的字符串**，然后再由转义后的正则表达式进行模式匹配。

#### java.util.regex包

java.util.regex 包主要包括以下三个类：

- Pattern 类

- Matcher 类

- PatternSyntaxException类

  PatternSyntaxException 是一个非强制异常类，它表示一个正则表达式模式中的语法错误。

#### Pattern类

pattern 对象是一个正则表达式的编译表示。Pattern 类没有公共构造方法。要创建一个 Pattern 对象，必须首先调用其公共静态编译方法，它返回一个 Pattern 对象。该方法接受一个正则表达式作为它的第一个参数。

##### Pattern.complie(String regex)

通过Pattern.complie(String regex)简单工厂方法创建一个正则表达式

```java
Pattern p=Pattern.compile("\\w+"); 
```

##### Pattern.pattern()

pattern() 返回正则表达式的字符串形式,其实就是返回Pattern.complile(String regex)的regex参数

```java
Pattern p=Pattern.compile("\\w+"); 
//返回  "\w+"  参数
p.pattern();
```

##### Pattern.split(CharSequence input)

用于分隔字符串

```java
Pattern p=Pattern.compile("\\w+"); 
String[] str=p.split(str); 
```

##### Pattern.matcher(String regex,CharSequence input)

一个静态方法,用于快速匹配字符串,该方法适合用于只匹配一次,且匹配全部字符串.

```java
Pattern p=Pattern.compile("\\d+"); 
// 返回一个Matcher对象
Matcher m=p.matcher("22bb23"); 
```

#### Matcher类

##### Matcher.matches()

当匹配到时返回true,没匹配到则返回false。

matches()对整个字符串进行匹配,只有整个字符串都匹配了才返回true

```java
//返回true 
Pattern.matches("\\d+","2223");
//返回false,需要匹配到所有字符串才能返回true,这里aa不能匹配到 
Pattern.matches("\\d+","2223aa");
//返回false,需要匹配到所有字符串才能返回true,这里bb不能匹配到 
Pattern.matches("\\d+","22bb23");
```

##### Matcher.lookingAt()

lookingAt()对前面的字符串进行匹配,只有匹配到的字符串在最前面才返回true

```java
Pattern p=Pattern.compile("\\d+");
Matcher m=p.matcher("22bb23");
//返回true,因为\d+匹配到了前面的22
m.lookingAt();
```

##### find()

find()对字符串进行匹配,匹配到的字符串可以在任何位置

```java
Pattern p=Pattern.compile("\\d+");
Matcher m=p.matcher("bb23");
//返回true，匹配到23
m.find();
```



当使用以上matches()/lookingAt()/find(),匹配到字符串返回true，就可以使用以下方法对匹配的字符串进行操作

##### start()

返回匹配到的子字符串在字符串中的索引位置

##### end()

返回匹配到的子字符串的最后一个字符在字符串中的索引位置

##### group()

返回匹配到的子字符串

##### replaceAll()/replaceFirst()

替换

##### appendReplacement(StringBuffer sb, String replacement)

将当前匹配子串替换为指定字符串，并且将替换后的子串以及其之前到上次匹配子串之后的字符串段添加到一个StringBuffer对象里

eg：

有字符串fatcatfatcatfat,假设既有正则表达式模式为"cat"，第一次匹配后调用appendReplacement(sb,"dog"),那么这时StringBuffer sb的内容为fatdog，也就是fatcat中的cat被替换为dog并且与匹配子串前的内容加到sb里

```java
Pattern p=Pattern.compile("cat");
Matcher m=p.matcher("fatcatfatcatfat");
// 遍例所有匹配的序列
while (matcher.find()) {
    StringBuffer sb;
	// 某一个fat
    m.appendReplacement(sb, "dog");
    // "fatdog"
    System.out.println(sb.toString());
}
```

##### appendTail(StringBuffer sb)

将最后一次匹配工作后剩余的字符串添加到一个StringBuffer对象里

eg:

如上之后，第二次匹配后调用appendReplacement(sb,"dog")，那么sb的内容就变为fatdogfatdog，如果最后再调用一次appendTail（sb）,那么sb最终的内容将是fatdogfatdogfat。



#### 匹配实现步骤

1. 通过正则表达式创建模式对象 `Pattern`
2. 通过模式对象 `Pattern`，根据指定字符串创建匹配对象 `Matcher`
3. 通过匹配对象 `Matcher`，根据正则表达操作字符串

eg:

```java
// 1
Pattern pattern = Pattern.compile("\\d+");
// 2
Matcher matcher = pattern.matcher(str);
// 3
// 遍例所有匹配的序列
while (matcher.find()) {
	System.out.println(matcher.group());
}
```







## 其他链接

- [正则表达式手册](http://tool.oschina.net/uploads/apidocs/jquery/regexp.html)
- [正则表达式-菜鸟教程](https://www.runoob.com/regexp/regexp-tutorial.html)