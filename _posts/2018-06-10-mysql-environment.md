---
layout: post
title: mysql 环境配置与密码修改
categories: mysql
---
mysql
===

1.安装
---

[下载](https://dev.mysql.com/downloads/mysql/)mysql comunity server
解压文件后，新建my.ini,添加以下内容：
	
	[mysql]
	# 设置mysql客户端默认字符集
	default-character-set=utf8 
	[mysqld]
	#设置3306端口
	port = 3306 
	# 设置mysql的安装目录
	basedir=C:\Programs\mysql-5.7.21-winx64-debug-test\mysql-5.7.21-winx64
	# 设置mysql数据库的数据的存放目录
	datadir=C:\Programs\mysql-5.7.21-winx64-debug-test\mysql-5.7.21-winx64\date
	# 允许最大连接数
	max_connections=200
	# 服务端使用的字符集默认为8比特编码的latin1字符集
	character-set-server=utf8
	# 创建新表时将使用的默认存储引擎
	default-storage-engine=INNODB


2.环境变量
---
>eg D:/mysql/mysql-5.7.17-winx64/bin；

```
安装 MySQL 服务: mysqld –install
启动: net start MySQL
停止: net stop MySQL
卸载: sc delete MySQL，mysqld -remove
//命令行窗口用管理员模式打开
```
（可能遇到问题，MySQL 服务正在启动。MSQL服务无法启动。服务没有报告任何错误。请键入NET HELPMSG 3534 以获得更多的帮助。
把data文件夹删掉，用mysqld –initialize 初始化data目录即可。

当成功进入开启服务以后
>mysql -u root -p 

输入密码（data文件夹下，后缀为.err的文件里）
注意： 旧版本初始是没有密码的，直接回车就可以登陆成功，新版本为了加强安全性，会产生随机密码。

3.修改密码
---
可以有三种方法：
1. 用SET PASSWORD命令：首先登录MySQL。


格式：
>mysql> set password for 用户名@localhost = password(‘新密码');

例子：
>mysql> set password for root@localhost = password(‘123');

或者:
>mysql> set password = password(‘123');


2. 用mysqladmin（未登录MySQL的情况下使用）

格式：
>mysqladmin -u用户名 -p旧密码 password 新密码

例子：
>mysqladmin -uroot -p123456 password 123

3. 用UPDATE直接编辑user表

首先登录MySQL。
```
mysql> use mysql;
mysql> update user set password=password(‘123') where user='root' and host='localhost';
mysql> flush privileges;
```

4. 禁止mysql自启
---
	Win R  
	service.msc  
	mysqld 改为手动启动