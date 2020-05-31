#..................................................
# File Name     : parse.py
# Author        : HuMingJun
# Mail          : humingk@qq.com
# QQ            : 764824480
# Created Time  : Sun 14 Otc 2018 10:17:33 AM DST
# Last Modified : Fri 21 Dec 2018 05:20:19 PM DST
#..................................................

import time

from .base import ThreadPoolEnum,MyThread

"""
继承了base.py中的Thread类
"""
class ParseThread(MyThread):
    """
    只需要fetchThread中初始化MyThread即可

    """

    """
    覆盖了MyThread中的working方法
    
    """
    def working(self):

        # 从线程池中获取一个parse任务
        priority,count,url,keys,content=self._pool.get_task(ThreadPoolEnum.HTM_PARSE)
        # parse任务开始执行
        parse_result,url_list,save_list=self._worker.working(priority,url,keys,content)
        #print("parseThread======================")
        #print(parse_result)
        #print(url_list)
        #print(save_list)

        # parse 执行成功
        if parse_result > 0:
            self._pool.update_number_dict(ThreadPoolEnum.HTM_PARSE_SUCC,+1)
            # url_list 中的fetch任务添加到线程池中
            for _url,_keys,_priority in url_list:
                self._pool.add_task(ThreadPoolEnum.URL_FETCH,(_priority,self._pool.get_number_dict(ThreadPoolEnum.URL_FETCH_COUNT),_url,_keys,0))


            # save_list 中的save任务添加到线程池中
            for item in save_list:
                self._pool.add_task(ThreadPoolEnum.ITEM_SAVE,(url,keys,item))

        # parse 执行失败
        else:
            self._pool.update_number_dict(ThreadPoolEnum.HTM_PARSE_FAIL,+1)

        self._pool.finish_task(ThreadPoolEnum.HTM_PARSE)
        return True
