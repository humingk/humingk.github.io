---
layout: post
title : MySQL 修改默认存储路径
categories : mysql
description : 
keywords :
---





mysql8.0 在ubuntu18上默认的存储路径为 /var/lib/mysql ，这里将其更换为 /home/data

网上各种帖子不是少了权限就是少了AppArmor服务或socket属性，这里都包括了

---

# linux ubuntu MySQL 修改默认存储路径

暂停mysql服务:

```shell
sudo service mysql stop
```

将 mysql文件夹移到home目录下，并更名为data:

```shell
sudo mv /var/lib/mysql /home/data
```

查看mysql默认配置选项：

```shell
mysqld --verbose --help | grep -A 1 'Default options

# 配置文件顺序如下：
Default options are read from the following files in the given order:
/etc/my.cnf /etc/mysql/my.cnf ~/.my.cnf 
```

按此顺序依次找到socket和datadir属性所在的文件，将其修改如下：

```shell
# 我这里找到在mysqld.cnf中
sudo vim /etc/mysql/mysql.conf.d/mysqld.cnf

# 修改文件内容（client中也要修改socket,没有的话添加一个）

[client]
port = 3306
#socket = /var/run/mysqld/mysqld.sock
socket		= /home/data/mysqld.sock

[mysqld]
pid-file	= /var/run/mysqld/mysqld.pid
#socket		= /var/run/mysqld/mysqld.sock
socket		= /home/data/mysqld.sock
#datadir		= /var/lib/mysql
datadir		= /home/data
log-error	= /var/log/mysql/error.log
```

在AppArmor中赋予mysql程序能够访问的socket和datadir权限，如下：

```shell
# 有的在user.bin.mysqld中
sudo vim /etc/apparmor.d/usr.sbin.mysqld   

# 修改文件内容如下：

# Allow pid, socket, socket lock file access

# 此栏目中添加一个socket目录
  /home/data/mysql.sock rw,
  ...
  
  # Allow data dir access

  # 此栏目中修改datadir目录
    #/var/lib/mysql/ r,
  /home/data/ r,
    #/var/lib/mysql/** rwk,
  /home/data/** rwk,
```

修改data目录权限：

```shell
# 修改mysql属性
sudo  chown -R mysql:mysql /home/data/mysql
# 修改data属性
sudo chown mysql:mysql -R /home/data/
```

重启服务:

```shell
# AppArmor服务
sudo service apparmor restart 
# mysql服务
sudo service mysql start 
```

