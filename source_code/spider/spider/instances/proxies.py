#..................................................
# File Name     : proxies.py
# Author        : HuMingJun
# Mail          : humingk@qq.com
# QQ            : 764824480
# Created Time  : Fri 12 Otc 2018 09:14:06 AM DST
# Last Modified : Fri 21 Dec 2018 02:31:16 PM DST
#..................................................
import time

class Proxieser(object):

    def __init__(self,sleep_time=3):
        self._sleep_time=sleep_time

        return

    def working(self)->(int,list):

        time.sleep(self._sleep_time)
        try:
            proxies_result,proxies_list=self.proxies_get()
        except Exception as e:
            proxies_result,proxies_list=-1,[]

        return proxies_result,proxies_list


    """
    重写 proxies_get
    传入：
        
    返回：
        proxies_result:     -1(获取失败)，1(获取成功)
        proxies_list:       代理IP列表
    
    """
    def proxies_get(self)->(int,list):
        raise NotImplementedError
