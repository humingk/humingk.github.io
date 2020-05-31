#..................................................
# File Name     : save.py
# Author        : HuMingJun
# Mail          : humingk@qq.com
# QQ            : 764824480
# Created Time  : Fri 12 Otc 2018 09:14:51 AM DST
# Last Modified : Fri 21 Dec 2018 04:21:34 PM DST
#..................................................


import sys

class Saver(object):

    def __init__(self):

        return

    def working(self, url: str, keys: dict, item: (list,tuple)) -> int:

        try:
            save_result = self.item_save(url, keys, item)
        except Exception as excep:
            save_result = -1

        return save_result

    """
    重写 item_save
    传入：
        url:        str,存储内容的原始网页链接
        keys:       字典,原始网页链接的一些配置参数
        item：      从parser中解析出来返回的需要存储的信息
    """
    def item_save(self, url: str, keys: dict, item: (list,tuple)) -> int:

        return 1