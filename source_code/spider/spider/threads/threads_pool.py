#..................................................
# File Name     : threads_pool.py
# Author        : HuMingJun
# Mail          : humingk@qq.com
# QQ            : 764824480
# Created Time  : Wed 10 Otc 2018 09:30:51 PM DST
# Last Modified : Fri 21 Dec 2018 05:26:49 PM DST
#..................................................

import queue
import threading
import copy

from 软件工程课程设计.spider.threads.threads_inst import ThreadPoolEnum
from 软件工程课程设计.spider.threads.threads_inst.fetch import FetchThread
from 软件工程课程设计.spider.threads.threads_inst.parse import ParseThread
from 软件工程课程设计.spider.threads.threads_inst.proxies import ProxiesThread
from 软件工程课程设计.spider.threads.threads_inst.save import SaveThread

class ThreadPool(object):

    def __init__(self,fetcher,parser=None,saver=None,proxieser=None):

        """
        调用类初始化

        """
        self._fetcher=fetcher
        self._parser=parser
        self._saver=saver
        self._proxieser=proxieser

        """
        队列初始化
        
        """
        self._queue_fetch=queue.PriorityQueue()
        self._queue_parse=queue.PriorityQueue()
        self._queue_save=queue.Queue()
        self._queue_proxies=queue.Queue()

        """
        对应线程类初始化
        
        """
        self._thread_fetcher_list=[]
        self._thread_parser=None
        self._thread_saver=None
        self._thread_proxieser=None

        """
        线程停止Flag初始化为False
        
        """
        self._thread_stop_flag=False

        """
        获得threading中的lock锁
        以便实现线程同步
        
        """
        self._lock=threading.Lock()

        """
        ThreadPoolEnum中的元素初始化为0
        
        """
        self._number_dict = {
            ThreadPoolEnum.TASKS_RUNNING: 0,

            ThreadPoolEnum.URL_FETCH_NOT: 0,
            ThreadPoolEnum.URL_FETCH_SUCC: 0,
            ThreadPoolEnum.URL_FETCH_FAIL: 0,
            ThreadPoolEnum.URL_FETCH_COUNT: 0,

            ThreadPoolEnum.HTM_PARSE_NOT: 0,
            ThreadPoolEnum.HTM_PARSE_SUCC: 0,
            ThreadPoolEnum.HTM_PARSE_FAIL: 0,

            ThreadPoolEnum.ITEM_SAVE_NOT: 0,
            ThreadPoolEnum.ITEM_SAVE_SUCC: 0,
            ThreadPoolEnum.ITEM_SAVE_FAIL: 0,

            ThreadPoolEnum.PROXIES_LEFT: 0,
            ThreadPoolEnum.PROXIES_FAIL: 0,
        }


        return

    """
    通过key值获取ThreaadPoolEnum中的所有状态值

    """
    def get_number_dict(self, key=None):
        if key:
            return self._number_dict[key]
        else:
            return self._number_dict

    """
    通过key值更新ThreaadPoolEnum中的所有状态值
    
    """
    def update_number_dict(self, key, value):
        self._lock.acquire()
        self._number_dict[key] += value
        self._lock.release()
        return
    """
    获取线程池的代理IP的flag
    
    """
    def get_proxies_flag(self):
        if self._proxieser:
            return True
        else:
            return False
    """
    获取线程池的stop的flag
    
    """
    def get_thread_stop_flag(self):
        return self._thread_stop_flag

    """
    开始抓取的第一个网页配置
    
    """
    def set_start_url(self,url,priority=0,keys=None):
        self.add_task(task_name=ThreadPoolEnum.URL_FETCH,task_content=(priority,self.get_number_dict(ThreadPoolEnum.URL_FETCH_COUNT),url,keys,0))

        return


    """
    多线程开始运行
    
    """
    def start(self,fetch_num=10):
        self._thread_stop_flag=False

        # 关联到自定义类
        self._thread_fetcher_list=[FetchThread(name="fetcher-%d" % i,worker=copy.deepcopy(self._fetcher),pool=self) for i in range(fetch_num)]
        self._thread_parser=ParseThread(name="parser",worker=self._parser,pool=self) if self._parser else None
        self._thread_saver=SaveThread(name="saver",worker=self._saver,pool=self) if self._saver else None
        self._thread_proxieser=ProxiesThread(name="proxies",worker=self._proxieser,pool=self) if self._proxieser else None

        # 开始线程
        for thread in self._thread_fetcher_list:
            thread.setDaemon(True)
            thread.start()
        if self._thread_parser:
            self._thread_parser.setDaemon(True)
            self._thread_parser.start()
        if self._thread_saver:
            self._thread_saver.setDaemon(True)
            self._thread_saver.start()
        if self._thread_proxieser:
            self._thread_proxieser.setDaemon(True)
            self._thread_proxieser.start()
        return


        return
    """
    等待线程池结束
    
    """
    def wait_finish(self):
        self._thread_stop_flag=True
        for thread in self._thread_fetcher_list:
            if thread.is_alive():
                thread.join()
        if self._thread_parser and self._thread_parser.is_alive():
            self._thread_parser.join()
        if self._thread_saver and self._thread_saver.is_alive():
            self._thread_saver.join()
        if self._thread_proxieser and self._thread_proxieser.is_alive():
            self._thread_proxieser.join()

        return self._number_dict




    """
    向线程池中添加一个任务
    
    """
    def add_task(self,task_name,task_content):

        # fetch线程任务+1
        if task_name == ThreadPoolEnum.URL_FETCH:
            self._queue_fetch.put_nowait(task_content)
            self.update_number_dict(ThreadPoolEnum.URL_FETCH_NOT,+1)
            self.update_number_dict(ThreadPoolEnum.URL_FETCH_COUNT,+1)
        # parse线程任务+1
        elif task_name==ThreadPoolEnum.HTM_PARSE and self._thread_parser:
            self._queue_parse.put_nowait(task_content)
            self.update_number_dict(ThreadPoolEnum.HTM_PARSE_NOT,+1)
        # save线程任务+1
        elif task_name==ThreadPoolEnum.ITEM_SAVE and self._thread_saver:
            self._queue_save.put_nowait(task_content)
            self.update_number_dict(ThreadPoolEnum.ITEM_SAVE_NOT,+1)
        # proxies线程任务+1
        elif task_name==ThreadPoolEnum.PROXIES and self._thread_proxieser:
            self._queue_proxies.put_nowait(task_content)
            self.update_number_dict(ThreadPoolEnum.PROXIES_LEFT,+1)
        return

    """
    从线程池中获取一个任务
    
    """
    def get_task(self,task_name):
        task_content=None
        # proxies
        if task_name == ThreadPoolEnum.PROXIES:
            task_content = self._queue_proxies.get(block=True, timeout=5)
            self.update_number_dict(ThreadPoolEnum.PROXIES_LEFT, -1)
        # fetch
        elif task_name == ThreadPoolEnum.URL_FETCH:
            task_content = self._queue_fetch.get(block=True, timeout=5)
            self.update_number_dict(ThreadPoolEnum.URL_FETCH_NOT, -1)
        # parse
        elif task_name == ThreadPoolEnum.HTM_PARSE:
            task_content = self._queue_parse.get(block=True, timeout=5)
            self.update_number_dict(ThreadPoolEnum.HTM_PARSE_NOT, -1)
        # save
        elif task_name == ThreadPoolEnum.ITEM_SAVE:
            task_content = self._queue_save.get(block=True, timeout=5)
            self.update_number_dict(ThreadPoolEnum.ITEM_SAVE_NOT, -1)

        self.update_number_dict(ThreadPoolEnum.TASKS_RUNNING, +1)
        return task_content

    """
    结束线程池中的一个任务
    
    """
    def finish_task(self,task_name):
        # proxies
        if task_name == ThreadPoolEnum.PROXIES:
            self._queue_proxies.task_done()
        # fetch
        elif task_name == ThreadPoolEnum.URL_FETCH:
            self._queue_fetch.task_done()
        # parse
        elif task_name == ThreadPoolEnum.HTM_PARSE:
            self._queue_parse.task_done()
        # save
        elif task_name == ThreadPoolEnum.ITEM_SAVE:
            self._queue_save.task_done()

        self.update_number_dict(ThreadPoolEnum.TASKS_RUNNING, -1)
        return
