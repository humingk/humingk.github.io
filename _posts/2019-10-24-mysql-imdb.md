---
layout: post
title : IMDB官方接口数据库转化为满足三范式的关系型数据库
categories : mysql
description : 
keywords :
---





IMDB官方接口数据库是几个tsv文件合计几个G大小，导入MySQL中大概有十几个G，其中还存在冗余，这里将其转化为至少满足三范式的关系型数据库

---

# IMDB官方接口数据库转化为满足三范式的关系型数据库

## IMDB数据来源

[https://www.imdb.com/interfaces/](https://www.imdb.com/interfaces/)

## 导入MySQL

这里可以借助 [IMDBpy](https://github.com/alberanid/imdbpy) 项目中的 [s32imdbpy.py](https://github.com/alberanid/imdbpy/blob/master/bin/s32imdbpy.py) 脚本文件将tsv文件批量导入MySQL中，大概需要一个小时

[s32imdbpy.py 使用文档](https://imdbpy.readthedocs.io/en/latest/usage/s3.html)

使用方法(这里采用pymysql)：

```shell
s32imdbpy.py [tsv压缩文件目录] [mysql+pymysql://[mysql账号]:[mysql密码]@localhost:3306/[mysql数据库名] 
```

## 转化过程

大概需要半个多小时,由imdb数据库转化到movie数据库

### 人物表

IMDB中人物表结构为：

```mysql
CREATE TABLE `name_basics` (
  `deathYear` int(11) DEFAULT NULL,
  `s_soundex` varchar(5) DEFAULT NULL,
  `birthYear` int(11) DEFAULT NULL,
  `nconst` int(11) DEFAULT NULL,
  `sn_soundex` varchar(5) DEFAULT NULL,
  `knownForTitles` text,
  `primaryName` text,
  `primaryProfession` text,
  `ns_soundex` varchar(5) DEFAULT NULL,
  KEY `ix_name_basics_nconst` (`nconst`),
  KEY `ix_name_basics_deathYear` (`deathYear`),
  KEY `ix_name_basics_birthYear` (`birthYear`),
  KEY `ix_name_basics_ns_soundex` (`ns_soundex`),
  KEY `ix_name_basics_s_soundex` (`s_soundex`),
  KEY `ix_name_basics_sn_soundex` (`sn_soundex`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
```

要将其转化为：

```mysql
create table celebrity_imdb
(
    # nm+id,id至少7个数字（不够7个在id前面添0）
    id                bigint unsigned      not null primary key,
    # 英文名
    name_en           varchar(255)         not null default '',
    # 出生年份
    birth_year        smallint(4) unsigned not null default 0,
    # 是否更新豆瓣影人 0:未更新 1:已更新
    is_douban_updated tinyint(1)           not null default 0,

    index (name_en)
) ENGINE = InnoDB
  default charset = utf8mb4;
```

执行sql：

```mysql
insert ignore into movie.celebrity_imdb(id,name_en,birth_year) 
select nconst,primaryName,ifnull(birthYear,0)
from imdb.name_basics;
```



### 电影表

IMDB中电影表为:

```mysql
CREATE TABLE `title_basics` (
  `startYear` int(11) DEFAULT NULL,
  `endYear` int(11) DEFAULT NULL,
  `tconst` int(11) DEFAULT NULL,
  `primaryTitle` text,
  `originalTitle` text,
  `runtimeMinutes` int(11) DEFAULT NULL,
  `titleType` varchar(16) DEFAULT NULL,
  `isAdult` tinyint(1) DEFAULT NULL,
  `genres` text,
  `t_soundex` varchar(5) DEFAULT NULL,
  KEY `ix_title_basics_startYear` (`startYear`),
  KEY `ix_title_basics_titleType` (`titleType`),
  KEY `ix_title_basics_runtimeMinutes` (`runtimeMinutes`),
  KEY `ix_title_basics_isAdult` (`isAdult`),
  KEY `ix_title_basics_tconst` (`tconst`),
  KEY `ix_title_basics_t_soundex` (`t_soundex`),
  CONSTRAINT `title_basics_chk_1` CHECK ((`isAdult` in (0,1)))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
```

要将其转化为：

```mysql
create table movie_imdb
(
    id                bigint unsigned      not null primary key,
    # 影片种类/类型  (电影、电视剧、电视剧的单集...)
    id_type_video     tinyint unsigned     not null default 1,
    # IMDB电影英文名
    name_en           varchar(255)         not null default '',
    # IMDB电影发行年份 、 电视剧首集播放年份
    start_year        smallint(4) unsigned not null default 0,
    # 是否是成人电影 0-不是 1-是
    is_adult          tinyint(1)           not null default 0,
    # IMDB电影原始名
    name_origin       varchar(255)         not null default '',
    # IMDB电影片长 分钟
    runtime           smallint unsigned    not null default 0,
    # imdb海报 https://m.media-amazon.com/images/M/ + url
    url_poster        varchar(1000)        not null default '',
    # 简介
    summary           text,
    # 是否更新豆瓣电影 0:未更新 1:已更新
    is_douban_updated tinyint(1)           not null default 0,

    index (id_type_video),
    index (name_en),
    index (start_year desc),
    index (name_origin)
) ENGINE = InnoDB
  default charset = utf8mb4;
```

查看titleType的分布有以下,设置默认值

episode为剧集ID，我不需要精确到电视剧的每一集，而且这也占用太多，故此处不考虑剧集。

type_video表:

```mysql
select titleType,count(*) from title_basics group by titleType;

+----------------+------------+
| titleType      |   count(*) |
|----------------+------------|
| episode        |    4378738 |
| movie          |     533141 |
| short          |     706991 |
| tv mini series |      28044 |
| tv series      |     172957 |
| tv short       |      11242 |
| tv special     |      25351 |
| tvMovie        |     120804 |
| video          |     244718 |
| video game     |      24289 |
+----------------+------------+

create table type_video
(
    id      tinyint unsigned not null primary key,
    # 影片类型中文名
    name_zh varchar(255)     not null default '',
    # 影片类型英文名
    name_en varchar(255)     not null default '',

    index (name_zh),
    index (name_en)
) ENGINE = InnoDB
  default charset = utf8mb4;

  # 默认值
insert ignore into type_video
values (1, '未知', 'unknown'),
       (2, '电影', 'movie'),
       (3, '电视剧', 'tv series'),
       (4, '短片', 'short'),
       # 来自IMDB
       (5, '', 'tv mini series'),
       (6, '', 'tv short'),
       (7, '', 'tv special'),
       (8, '', 'tvMovie'),
       (9, '', 'video'),
       (10, '', 'video game');
```

执行sql为：

```mysql
insert into movie.movie_imdb(id,id_type_video,start_year,is_adult,name_en,name_origin,runtime)
select tconst,
(select id from type_video where name_en=titleType),
ifnull(startYear,0),isAdult,
if(isnull(primaryTitle)=0 and char_length(primaryTitle)<255,primaryTitle,''),
if(isnull(originalTitle)=0 and char_length(originalTitle)<255,originalTitle,''),
if(isnull(runtimeMinutes)=0 and runtimeMinutes<60000,runtimeMinutes,0)
from imdb.title_basics where titleType!='episode';
```

### IMDB电影评分表

IMDB评分表：

```mysql
CREATE TABLE `title_ratings` (
  `averageRating` float DEFAULT NULL,
  `tconst` int(11) DEFAULT NULL,
  `numVotes` int(11) DEFAULT NULL,
  KEY `ix_title_ratings_averageRating` (`averageRating`),
  KEY `ix_title_ratings_tconst` (`tconst`),
  KEY `ix_title_ratings_numVotes` (`numVotes`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
```

需要转化为：

```mysql
create table rate_imdb
(
    # tt+id,id至少7个数字（不够7个在id前面添0）
    id           bigint unsigned not null primary key,
    # IMDB评分
    imdb_score   decimal(3, 1)   not null default 0.0,
    # IMDB评分人数
    imdb_vote    int unsigned    not null default 0,
    # 烂番茄新鲜度
    tomato_score decimal(3, 1)   not null default 0.0,
    # MTC评分
    mtc_score    decimal(3, 1)   not null default 0.0,

    index (imdb_score desc),
    index (imdb_vote desc),
    index (tomato_score desc),
    index (mtc_score desc)
) ENGINE = InnoDB
  default charset = utf8mb4;
```

此处评分表不需要包括剧集的评分

执行sql：

```mysql
insert into movie.rate_imdb(id,imdb_score,imdb_vote)
select tconst,averageRating,numVotes
from imdb.title_ratings where imdb.title_ratings.tconst in (select id from movie.movie_imdb);
```

### 人物与电影的关系

IMDB表为：

```mysql
CREATE TABLE `title_principals` (
  `category` varchar(64) DEFAULT NULL,
  `tconst` int(11) DEFAULT NULL,
  `nconst` int(11) DEFAULT NULL,
  `job` varchar(1024) DEFAULT NULL,
  `characters` varchar(1024) DEFAULT NULL,
  `ordering` int(11) DEFAULT NULL,
  KEY `ix_title_principals_tconst` (`tconst`),
  KEY `ix_title_principals_nconst` (`nconst`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `title_crew` (
  `directors` text,
  `tconst` int(11) DEFAULT NULL,
  `writers` text,
  KEY `ix_title_crew_tconst` (`tconst`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
```

要将其转化为：

```mysql
create table movie_imdb_to_celebrity_imdb
(
    id_movie_imdb     bigint unsigned  not null,
    id_celebrity_imdb bigint unsigned  not null,
    # 该IMDB名人在该IMDB电影中的职位
    id_profession     tinyint unsigned not null,

    primary key (id_movie_imdb, id_celebrity_imdb, id_profession)
) ENGINE = InnoDB
  default charset = utf8mb4;
```

先提取出所有的职位信息：

```mysql
select category,count(*) from title_principals group by category;

+---------------------+------------+
| category            |   count(*) |
|---------------------+------------|
| self                |    6091154 |
| director            |    4110906 |
| cinematographer     |    1281510 |
| composer            |    1294864 |
| producer            |    2158407 |
| editor              |    1182529 |
| actor               |    8387366 |
| actress             |    6227601 |
| writer              |    4755449 |
| production_designer |     283898 |
| archive_footage     |     206564 |
| archive_sound       |       2131 |
+---------------------+------------+

# 名人职业
create table profession
(
    id      tinyint unsigned not null primary key,
    # 职业中文名
    name_zh varchar(255)     not null default '',
    # 职业英文名
    name_en varchar(255)     not null default '',

    index (name_zh),
    index (name_en)
) ENGINE = InnoDB
  default charset = utf8mb4;
  
  insert into profession
values (1, '未知',
        'unknown'),
       (2, '导演', 'director'),
       (3, '编剧', 'writer'),
       (4, '主演', 'starring'),
       # 来自IMDB
       (5, '男演员', 'actor'),
       (6, '女演员', 'actress'),
       (7, '', 'archive_footage'),
       (8, '', 'archive_sound'),
       (9, '', 'cinematographer'),
       (10, '', 'composer'),
       (11, '', 'editor'),
       (12, '', 'producer'),
       (13, '', 'production_designer'),
       (14, '', 'self');
```

执行sql：(记得不需要加上剧集)

```mysql
insert into movie.movie_imdb_to_celebrity_imdb(id_movie_imdb,id_celebrity_imdb,id_profession)
select tconst,nconst,
(select id from movie.profession where name_en=category)
from imdb.title_principals inner join movie_imdb on id=tconst
on duplicate key update id_movie_imdb=tconst;
```

查看数据库占用情况

```mysql
use information_schema;

select TABLE_NAME,TABLE_ROWS as count,
concat(round((DATA_LENGTH+INDEX_LENGTH)/1024/1024,2), 'MB') as data 
from TABLES where TABLE_SCHEMA='movie' 
order by data desc;

+-------------------------------------+---------+-----------+
| TABLE_NAME                          |   count | data      |
|-------------------------------------+---------+-----------|
| celebrity_imdb                      | 9488012 | 857.00MB  |
| movie_imdb                          | 1871162 | 403.38MB  |
| rate_imdb                           |  525516 | 116.78MB  |
| movie_imdb_to_celebrity_imdb        | 8669081 | 1039.98MB |
```

经过这一系列操作，数据库最终收录IMDB电影共计180万条，IMDB影人共计940万条，总占用2个多G。

