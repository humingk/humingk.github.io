#..................................................
# File Name     : test.py
# Author        : HuMingJun
# Mail          : humingk@qq.com
# QQ            : 764824480
# Created Time  : Fri 21 Dec 2018 10:12:59 AM DST
# Last Modified : Fri 21 Dec 2018 05:41:39 PM DST
#..................................................
import re
import urllib
import spider
import datetime
import requests


class MyFetcher(spider.Fetcher):
    def url_fetch(self, priority: int, url: str, keys: dict,  repeat: int, proxies=None):
        response = requests.get(url, params=None, headers={}, data=None, proxies=proxies, timeout=(3.05, 10))
        content = (response.status_code, response.url, response.text)
        return 1, True, content


class MyParser(spider.Parser):
    def htm_parse(self, priority: int, url: str, keys: dict,  content: object):
        status_code, url_now, html_text = content

        url_list = []
        tmp_list = re.findall(r"<a.+?href=\"(?P<url>.{5,}?)\".*?>", html_text, flags=re.IGNORECASE)
        url_list=[(_url,keys,priority+1) for _url in tmp_list]
        title = re.search(r"<title>(?P<title>.+?)</title>", html_text, flags=re.IGNORECASE)
        save_list = [(url, title.group("title").strip(), datetime.datetime.now()), ] if title else []
        return 1, url_list, save_list

class MySaver(spider.Saver):
    def item_save(self, url: str, keys: dict, item: (list, tuple)):
        for it in item:
            print(it)
        return 1

def test_spider():
    fetcher = MyFetcher(max_repeat=3, sleep_time=1)
    parser = MyParser()
    saver =MySaver()
    _spider = spider.ThreadPool(fetcher, parser, saver, proxieser=None)

    _spider.set_start_url("http://zhushou.360.cn/", priority=0, keys={"type": "360"})

    _spider.start(fetch_num=2)
    _spider.wait_finish()

    return


if __name__ == "__main__":
    test_spider()
    exit()