---
layout: post
title : MySQL学习笔记
categories : mysql
---

总结一些关于数据库表设计相关的注意事项，特别是一些强制性的规约，我们为什么要那样做呢？

---

允许适当冗余

对于一些需要经常关联查询的表，可以在对应的关联表中冗余储存一些相同的字段，以减少关联查询，但冗余字段避免采用：

- 需要频繁修改的字段（比如投票数，评分等等）
- 占用空间较大的字段，如varchar，text
- 非唯一索引的字段



## 关于命名

- 表示与否概念的，用 is_xxx tinyint(1) unsigned

- 全部使用小写字母和下划线

- 全部使用单数名称，禁止用复数
- 表中必须至少要有一个id字段

- 索引命名：（Innodb中无须在意）
  - 主键索引: pk\_主键名
  - 唯一索引: uk\_索引名
  - 普通索引: idx\_索引名



## 关于外键

- 禁止使用外键

  使用外键的好处：

  - 能最大限度保证数据库的完整性和一致性，减少部分冗余
  - 设计数据库的时候方便查看ER关系图

  使用外键的坏处：

  - 使用外键的话，插入、更新、删除等操作性能更差

    当存在外键约束的时候，每次插入都要去扫描一下此外键字段是否在其他表中存在

  - 对数据项的约束要求太高，如果出现外键约束错误，整个事务都会回滚，导致其他数据项操作失败

  - 对与外键有关的数据项操作时，Innodb会锁住其外键对应的数据行，可能会造成死锁

  - 重构数据库的时候很麻烦，包括删除表、添加表等等

  - 数据库迁移受限，比如MyISAM等引擎暂不支持外键

  总结：

  在项目构建初期，可以使用外键，这样方便查看ER关系图进行数据库设计，数据库设计完成后，可以取消外键，数据项的约束关系可在操作数据库的时候用代码逻辑约束。

  对于用户量大、并发度高的项目，性能的关键点在于数据库服务器，把数据库一致性的事务操作放在应用事务中而不是数据库中，能减少数据库服务器的压力，强烈推荐不用外键。

  如果项目小，用户量少，并发度低，而且对数据库的完整性、一致性要求很高，可以考虑保留外键。



## 关于索引

SQL查询：

- SQL查询 order by 尽量为组合索引的最后一个字段，且order by 前面的字段一般不用范围比较

- SQL查询语句中尽量不要在非索引字段中使用or

  or会导致索引失效，如果必须要用or，需要将or条件中的每个字段都加上索引，即在索引字段中使用or

- SQL查询尽量使用覆盖索引（即联合索引的最优情况，根据索引字段查找索引字段），不用回表

表设计：

- 尽量建立唯一索引，即使是多个字段的组合

  唯一索引虽然有时候会降低一点insert效率，但能大大提高select效率

  表中没有唯一索引，必然会有脏数据产生

- 主键要尽可能短，Innodb数据表中，普通索引都会保存主键的键值

  能有效减少磁盘占用

索引并不是越多越好，下面为不需要使用索引的情况：

- 永远也不会在where后面出现的字段

  比如select xxx，xxx完全没必要建立索引

- 出现在where后的字段，并且包含 is null、is not null、like '%xxx%'、like '%xxx'等

  null判断，模糊匹配，等均会导致索引失效

- 数据唯一性差的字段

  比如is_xxx，只有0和1两种，则索引的二叉树高度低，不管搜索哪个值都对得出大约一半的数据行，没有必要建立索引

- 频繁更新的字段

  更新数据会频繁更新索引，降低效率



## 关于数据类型

- 小数类型采用decimal，禁止使用float，double

  decimal(10,8) 8表示小数位数，10表示整数位数+小数位数

- 使用Innode引擎的表，字符串尽量使用varchar(x)，x不能超过5000

  PS：varchar最大长度为65535(Innodb中varchar最大字符数为1000）

  首先，对于Innode数据表，所有的数据行都使用**指向数据列值的头指针**，即内部的行存储格式并没有区分是否为固定长度，因此，使用char和varchar在Innodb数据表中并无太大的性能差别，相反，主要性能因素为**数据行的大小**，一般不定长的varchar比定长的char占用更少的空间，故推荐使用varchar。

  （参考自《深入浅出MySQL》-char和varchar）

  其次，建议varchar不要超过5000，超过的建议用text，并且将包含text数据项独立出来(垂直分表)，以避免其影响其他字段的索引效率。

  eg:

  ```mysql
  # 豆瓣电影影评
  create table review_douban
  (
      id           bigint unsigned not null primary key,
      # 赞同数
      agree_vote   int unsigned default 0,
      # 反对数
      against_vote int unsigned default 0,
  
      index (agree_vote desc),
      index (against_vote desc)
  ) ENGINE = InnoDB
    default charset = utf8mb4;
  
  # 豆瓣电影影评详情
  create table review_douban_detail
  (
      id              bigint unsigned not null primary key,
      # 影评内容
      content         text,
      # 影评日期时间
      create_datetime datetime,
  
      index (content(20))
  ) ENGINE = InnoDB
    default charset = utf8mb4;
  ```