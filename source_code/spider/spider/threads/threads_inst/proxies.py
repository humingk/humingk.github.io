#..................................................
# File Name     : proxies.py
# Author        : HuMingJun
# Mail          : humingk@qq.com
# QQ            : 764824480
# Created Time  : Sun 14 Otc 2018 10:18:26 AM DST
# Last Modified : Fri 21 Dec 2018 05:01:59 PM DST
#..................................................


from .base import ThreadPoolEnum,MyThread

"""
继承了base.py中的Thread类
"""
class ProxiesThread(MyThread):

    def __init__(self,name,worker,pool):
        MyThread.__init__(self,name,worker,pool)
        return
    """
    覆盖了MyThread中的working方法
    
    """
    def working(self):

        """
        proxies任务需要频繁地执行

        """

        # proxies 任务开始执行
        proxies_result,proxies_list=self._worker.working()

        # proxies 获取到的代理IP列表加到线程池中的proxies
        for proxies in proxies_list:
            self._pool.add_task(ThreadPoolEnum.PROXIES,proxies)

        return True
