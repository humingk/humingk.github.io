#..................................................
# File Name     : save.py
# Author        : HuMingJun
# Mail          : humingk@qq.com
# QQ            : 764824480
# Created Time  : Fri 12 Otc 2018 09:27:23 AM DST
# Last Modified : Fri 21 Dec 2018 05:47:01 PM DST
#..................................................


from .base import ThreadPoolEnum,MyThread

"""
继承了base.py中的Thread类
"""
class SaveThread(MyThread):
    """
    只需要fetchThread中初始化MyThread即可

    """

    """
    覆盖了MyThread中的working方法
    
    """
    def working(self):
        # 从线程池中获取一个save任务
        url,keys,item=self._pool.get_task(ThreadPoolEnum.ITEM_SAVE)
        # save任务开始执行
        save_result=self._worker.working(url,keys,item)

        # save 执行成功
        if save_result > 0:
            self._pool.update_number_dict(ThreadPoolEnum.ITEM_SAVE_SUCC,+1)

        # save 执行失败
        else:
            self._pool.update_number_dict(ThreadPoolEnum.ITEM_SAVE_FAIL,+1)

        self._pool.finish_task(ThreadPoolEnum.ITEM_SAVE)
        return True
