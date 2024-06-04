---
layout: post
title : 网易云音乐API分析
categories : python
---



网易云音乐官方API有好几个，而且请求参数都用到了AES加密，这里是一些简单总结，包括怎么去分析不同的API请求参数的加密过程，也汇集了一些解析贴

---

## 简要分析

### 关于weapi

weapi就是网易云音乐网页版用的API

![](https://raw.githubusercontent.com/humingk/resource/master/image/2018/weapi.png)

如图所示，关于某首歌的热门评论：

请求链接：

https://music.163.com/weapi/v1/resource/comments/R_SO_4_27759600?csrf_token=

请求方式为POST，重点在请求参数Form Data：

```
params: ijv7HcA5CRxI/lWUPv5o2jvLmv6JPYcA1HLDDS7PZwaIUzuybQTrpCSuPF8+o18gIadNpC4NA4zzTbAlUYTZ9lBGEo3JhkiDGwELaPrt52KjXm0j62P80s389dYCDxQD/sRGAzWS0rHueCg99+PreKnUVH5Jc3BRPBohqVjxWCB04SFqOOqzstoQaFanUIal
encSecKey: dfaac2123ed10285b18d876c3355933aea8f0cc74865f6f7e98f996dbb4aacb90c0c84821eada73a84c8da2ef20ca184da4b4d26ac5c92ec657f0eef9794038e28e9419710c65a151855426f0bdef5318cafc2c500e0e6dfbe901d2ccb8f9f002640cf7023d455716727026c3a09aaa7b8b1d3f69608c767466eb18d0bd25aac
```

两个请求参数都被加密了，我们要想获得这个请求链接返回的数据，就需要在本地模拟这个加密过程，要想分析其加密过程，可以用Fiddler等软件进行抓包，如文末帖子里面的操作，大致可以知道，weapi的请求参数params进行了两次的AES加密

### 关于eapi

eapi就是目前网易云音乐PC版本、Android版本用到的API

eapi分析过程同上，eapi的请求参数params进行了一次的AES加密

### 关于weapi请求参数

比如请求评论的类型，评论页数，等等请求参数，都可以通过fiddler抓包分析来获取，每一种请求链接一般都有不同的请求参数，可以参考NeteaseCloudMusicAPI项目里的[module模块](https://github.com/Binaryify/NeteaseCloudMusicApi/tree/master/module)

在我的[netease_music_cat](https://github.com/humingk/netease_music_cat)项目的netease模块 [first_param.py](https://github.com/humingk/netease_music_cat/blob/master/netease/first_param.py)，也实现了一小部分请求参数

### weapi和eapi的python模拟加密

在我的[netease_music_cat](https://github.com/humingk/netease_music_cat)项目的netease模块，实现了weapi和eapi的加密过程

```python
# !/usr/bin/env python
# -*- coding: utf-8 -*-

# author: humingk
# ----------------------
import hashlib
from binascii import b2a_hex
import config
import base64
from Crypto.Cipher import AES
from my_tools.logger_tool import loggler_tool

logger = loggler_tool()


class form_data:
    """
    AES加密请求参数类
        利用fiddler分析网易云音乐中的core.js对请求表单参数的加密过程
        此模块模拟对请求表单参数的两次AES加密

    """

    def __get_weapi_params(self, first_param):
        """
        获取weapi Form Data中的parms（通过两次AES加密）

        :param first_param: 第一个参数字典（由first_param获取）
        :return: 两次加密后的params
        """
        params = self.__AES_encrypt(mode=AES.MODE_CBC, param=first_param, key=config.first_key, iv=config.iv)
        params = str(base64.b64encode(params))[2:-1]
        params = self.__AES_encrypt(mode=AES.MODE_CBC, param=params, key=config.second_key, iv=config.iv)
        return str(base64.b64encode(params))[2:-1]

    def __get_eapi_params(self, eapi_url, first_param):
        """
        获取eapi Form Data中的params(通过一次AES加密)

        :param eapi_url: 请求url 字符串
        :param first_param:  第一个参数字典 字符串
        :return: 一次加密后的params
        """
        first_param = first_param.encode()
        eapi_url = eapi_url.encode()
        params = b'nobody' + eapi_url + b'use' + first_param + b'md5forencrypt'
        md5 = hashlib.md5()
        md5.update(params)
        params = eapi_url + b'-36cd479b6b5-' + first_param + b'-36cd479b6b5-' + md5.hexdigest().encode()
        pad = 16 - len(params) % 16
        params = params + bytearray([pad] * pad)
        crypt = AES.new(config.eapikey, AES.MODE_ECB)
        params = crypt.encrypt(params)
        return b2a_hex(params).upper()

    def __get_encSecKey(self):
        """
        获取Form Data中的encSeckey（已配合param加密）

        :return: encSecKey
        """
        return config.encSecKey

    def __AES_encrypt(self, mode, param, key, iv):
        """
        AES加密过程

        :param mode: AES模式
        :param param: AES加密对象
        :param key: AES加密秘钥
        :param iv: AES秘钥偏移量
        :return: AES加密结果
        """
        # 这里不能使用len(param),因为此处长度指标不能用中文，应该先转化为unicode
        pad = 16 - len(param.encode()) % 16
        param = param + pad * chr(pad)
        encryptor = AES.new(key, mode, iv)
        encrypt_text = encryptor.encrypt(param)
        return encrypt_text

    def get_form_data(self, first_param, api_type=config.api_weapi, eapi_url=None):
        """
        获取请求参数

        :param first_param: 第一个请求字典
        :param api_type: api类型
        :param eapi_url: eapi的请求链接
        :return:
        """
        if api_type == config.api_weapi:
            return {
                "params": self.__get_weapi_params(first_param),
                "encSecKey": self.__get_encSecKey()
            }
        elif api_type == config.api_eapi:
            return {
                "params": self.__get_eapi_params(eapi_url=eapi_url, first_param=first_param)
            }
        else:
            return None
```



### 其他的还有LinuxApi等等



## 相关帖子汇总

### API服务提供

- [NeteaseCloudMusicApi](https://github.com/Binaryify/NeteaseCloudMusicApi)

  提供现成的、且非常全面的API服务，依赖于nodejs

  应该是目前最全面的，目前有很多人在维护，我的[douban_movie网站](https://github.com/humingk/douban_movie)上关于网易云音乐也用的是这个

- [网易云音乐命令行版本](https://github.com/darknessomi/musicbox)

  也有关于API

### 关于weapi

- [NeteaseCloudMusicApi文档](https://binaryify.github.io/NeteaseCloudMusicApi/#/)

  上面[NeteaseCloudMusicApi](https://github.com/Binaryify/NeteaseCloudMusicApi)项目的文档

  其实只需这一个就够了，很全面

- [【爬虫】爬取网易云音乐评论](https://zhuanlan.zhihu.com/p/32069543)

- [【爬虫】爬取网易云音乐评论2](https://zhuanlan.zhihu.com/p/32100823)

- [【爬虫】网易云音乐评论爬取3(完善数据库）](https://zhuanlan.zhihu.com/p/32235424)

- [【爬虫】爬取网易云音乐评论4（完结）](https://zhuanlan.zhihu.com/p/32340005)

  包括抓包分析weapi的加密过程，Python

### 关于eapi

目前只有eapi能够获取到所有的热门评论，weapi只能获取第一页的热门评论

- [新增eapi算法](https://github.com/Binaryify/NeteaseCloudMusicApi/commit/e92e8029e1b8721f5e16d1579aaf72028774fd41)

  上面[NeteaseCloudMusicApi](https://github.com/Binaryify/NeteaseCloudMusicApi)项目中新增的Js

- [网易云音乐API](https://github.com/picone/MusicUnionSearch/wiki/%E7%BD%91%E6%98%93%E4%BA%91%E9%9F%B3%E4%B9%90API)

  eapi加密过程 Python

- [网易云音乐网盘上传工具](https://github.com/picone/CloudMusicUploader/blob/master/cloud_music.py)

  eapi加密过程 Python

- [获取网易云音乐精彩评论API](http://silvercodingcat.com/python/2017/09/15/Netease-Music-Hot-Comment/)

  eapi的抓包简要分析

- [网易云音乐PC客户端加密API逆向解析](https://www.freebuf.com/articles/web/164636.html)

  逆向分析eapi加密方式，这个过程我是真的没有看懂orz...

- [Android逆向——网易云音乐排行榜api(上)](https://juejin.im/post/5ac10c51f265da23a229408d)

- [Android逆向——网易云音乐排行榜api(下)](https://juejin.im/post/5b1b6e4b6fb9a01e87569e96)

  同上

  

  