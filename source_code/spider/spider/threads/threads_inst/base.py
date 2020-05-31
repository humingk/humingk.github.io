#..................................................
# File Name     : base.py
# Author        : HuMingJun
# Mail          : humingk@qq.com
# QQ            : 764824480
# Created Time  : Sun 14 Otc 2018 11:52:10 AM DST
# Last Modified : Fri 21 Dec 2018 01:22:24 PM DST
#..................................................

import enum
import queue
import threading

"""
继承Threading模块的Thread类
重写Thread中的：
    init构造方法
    run方法

"""
class MyThread(threading.Thread):

    def __init__(self,name,worker,pool):
        threading.Thread.__init__(self,name=name)
        self._worker=worker
        self._pool=pool
        return
    def working(self):
        raise NotImplementedError
    def run(self):
        while True:
            try:
                if not self.working():
                    break
            except(queue.Empty,TypeError):
                pass
            except Exception as e:
                break

        return

"""
线程池中的线程Flag类
用于确认线程的运行状态

"""
class ThreadPoolEnum(enum.Enum):
    TASKS_RUNNING = "tasks_running"

    """
    fetcher的状态
    not:    还没有被获取
    succ:   获取成功
    fail:   获取失败
    count:  权重队列中的权重flag
    
    """
    URL_FETCH = "url_fetch"
    URL_FETCH_NOT = "url_fetch_not"
    URL_FETCH_SUCC = "url_fetch_succ"
    URL_FETCH_FAIL = "url_fetch_fail"
    URL_FETCH_COUNT = "url_fetch_count"

    """
    parser的状态
    not:    还没有被解析
    succ:   解析成功
    fail:   解析失败
    
    """
    HTM_PARSE = "htm_parse"
    HTM_PARSE_NOT = "htm_parse_not"
    HTM_PARSE_SUCC = "htm_parse_succ"
    HTM_PARSE_FAIL = "htm_parse_fail"

    """
    saver的状态
    not:    还没有被存储
    succ:   存储成功
    fail:   存储失败
    
    """
    ITEM_SAVE = "item_save"
    ITEM_SAVE_NOT = "item_save_not"
    ITEM_SAVE_SUCC = "item_save_succ"
    ITEM_SAVE_FAIL = "item_save_fail"

    """
    proxieser的状态
    left:   剩下还没有用的代理IP
    fail:   失效了的IP
    """
    PROXIES = "proxies"
    PROXIES_LEFT = "proxies_left"
    PROXIES_FAIL = "proxies_fail"

