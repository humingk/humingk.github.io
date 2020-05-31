#..................................................
# File Name     : fetch.py
# Author        : HuMingJun
# Mail          : humingk@qq.com
# QQ            : 764824480
# Created Time  : Fri 12 Otc 2018 09:13:11 AM DST
# Last Modified : Fri 21 Dec 2018 11:21:19 AM DST
#..................................................

import requests
import time
import random

class Fetcher(object):
    def __init__(self,max_repeat=3,sleep_time=0):
        self._sleep_time=sleep_time
        self._max_repeat=max_repeat
        return

    def working(self,priority:int,url:str,keys:dict,repeat:int,proxies=None)->(int,bool,object):

        time.sleep(random.randint(0,self._sleep_time))
        try:
            fetch_result,proxies_state,content=self.url_fetch(priority=priority,url=url,keys=keys,repeat=repeat,proxies=proxies)
        except Exception as e:
            if repeat >= self._max_repeat:
                fetch_result,proxies_state,content=-1,False,None
            else:
                # 没有超过最大重复次数限制 返回0 继续抓取
                fetch_result,proxies_state,content=0,False,None

        return fetch_result,proxies_state,content

    """
    重写方法 url_fetch
    传入：
        priority:   当前网页的权重值
        url:        str,要fetch的网页链接
        keys:       字典,当前网页链接的一些配置参数
        repeat:     重复抓取当前页面的次数
        proxies:    元组,代理IP池，在proxy中配置，默认为None
    返回：
        1：          -1(获取失败),0(需要重复获取,比如代理IP失效),1(获取成功)
        True：       当前代理IP有没有失效，便于在IP池更换IP，默认为True
        content:     字典，元组，列表... 当前获取到的网页信息

    """
    def url_fetch(self,priority:int,url:str,keys:dict,repeat:int,proxies=None) -> (int,bool,object):

        response=requests.get(url,params=None,data=None,headers={},proxies=proxies,timeout=(3.05,10))
        content=(response.status_code,response.url,response.text)

        return 1,True,content
