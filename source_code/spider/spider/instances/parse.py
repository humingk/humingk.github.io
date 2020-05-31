#..................................................
# File Name     : parse.py
# Author        : HuMingJun
# Mail          : humingk@qq.com
# QQ            : 764824480
# Created Time  : Fri 12 Otc 2018 09:13:46 AM DST
# Last Modified : Fri 21 Dec 2018 10:51:35 AM DST
#..................................................

class Parser(object):
    def __init__(self):

        return

    def working(self,priority:int,url:str,keys:dict,content:object)->(int,list,list):
        try:
            parse_result,url_list,save_list=self.htm_parse(priority=priority,url=url,keys=keys,content=content)
        except Exception as e:
            parse_result,url_list,save_list=-1,[],[]


        return parse_result,url_list,save_list

    """
    重写方法 htm_parse
    传入：
        url:        str,要fetch的网页链接
        keys:       字典,当前网页链接的一些配置参数
        content:    从fetcher那边返回过来的获取的网页信息
    返回：
        1：          -1(解析失败),1(解析成功)
        url_list:    通过当前的网页解析到其他带爬取的网页链接
        save_list:   从当前页面解析出来的将要存入数据库的信息

    """
    def htm_parse(self,priority:int,url:str,keys:dict,content:object) -> (int,list,list):

        status_code,url,text=content
        url_list=[]
        save_list=[]
        
        return 1,url_list,save_list
