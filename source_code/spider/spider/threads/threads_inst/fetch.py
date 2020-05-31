#..................................................
# File Name     : fetch.py
# Author        : HuMingJun
# Mail          : humingk@qq.com
# QQ            : 764824480
# Created Time  : Sun 14 Otc 2018 11:02:06 AM DST
# Last Modified : Fri 21 Dec 2018 05:10:17 PM DST
#..................................................


from .base import ThreadPoolEnum,MyThread

"""
继承了base.py中的Thread类
"""
class FetchThread(MyThread):

    def __init__(self,name,worker,pool):
        MyThread.__init__(self,name,worker,pool)
        self._proxies=None
        return

    """
    覆盖了MyThread中的working方法
    
    """
    def working(self):

        # 从proxies列表获取一个代理IP
        if self._pool.get_proxies_flag() and (not self._proxies):
            self._proxies=self._pool.get_task(ThreadPoolEnum.PROXIES)
        # 从线程池中获取一个FETCH任务
        priority,count,url,keys,repeat=self._pool.get_task(ThreadPoolEnum.URL_FETCH)
        # fetch任务开始执行
        fetch_result,proxies_state,content=self._worker.working(priority,url,keys,repeat,proxies=self._proxies)

        # fetch 执行成功
        if fetch_result > 0:
            self._pool.update_number_dict(ThreadPoolEnum.URL_FETCH_SUCC,+1)
            # fetch到内容
            if content is not None:
                # 添加一个parse任务
                self._pool.add_task(task_name=ThreadPoolEnum.HTM_PARSE,task_content=(priority,count,url,keys,content))

        # fetch 失败 并且需要重复fetch
        elif fetch_result==0:
            # 重新添加该Fetch任务到线程池中
            self._pool.add_task(ThreadPoolEnum.URL_FETCH,(priority,count,url,keys,repeat+1))

        # fetch 失败 并且不需要重复fetch
        else:
            self._pool.update_number_dict(ThreadPoolEnum.URL_FETCH_FAIL,+1)

        # 代理IP失效
        if self._proxies and (not proxies_state):
            self._pool.update_number_dict(ThreadPoolEnum.PROXIES_FAIL,+1)
            # 结束该proxies线程，并设置为None以便重新获取，repeat也还是要+1
            self._pool.finish_task(ThreadPoolEnum.PROXIES)
            self._proxies=None

        self._pool.finish_task(ThreadPoolEnum.URL_FETCH)
        return True