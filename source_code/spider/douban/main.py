#..................................................
# File Name     : main.py
# Author        : HuMingJun
# Mail          : humingk@qq.com
# QQ            : 764824480
# Created Time  : Fri 19 Otc 2018 04:01:20 PM DST
# Last Modified : Fri 21 Dec 2018 11:42:10 PM DST
#..................................................
import logging
from douban_movie.douban.fetcher import fetcher
from douban_movie.douban.parser import parser
from douban_movie.douban.proxy import proxy
from douban_movie.douban.saver import saver
import douban_movie.spider as spider
import douban_movie.douban.config as config
import pymysql
import warnings
warnings.filterwarnings("ignore")


def get_all(movies):
    _proxy=proxy(sleep_time=0)
    _fetcher=fetcher(max_repeat=50,sleep_time=0)
    _parser=parser(max_deep=3)
    _saver=saver()
    id_spider=spider.WebSpider(proxieser=_proxy,fetcher=_fetcher,parser=_parser,saver=_saver,url_filter=None,max_count_in_parse=500,max_count_in_proxies=100)
    for key,url in movies:
        id_spider.set_start_url(url,keys={"type":key},priority=0,deep=0)
    id_spider.start_working(fetcher_num=2)
    id_spider.wait_for_finished()
    return

if __name__ == "__main__":
    logging.basicConfig(level=logging.INFO, format="%(asctime)s\t%(levelname)s\t%(message)s")
    movies=set()
    movies.update([("new_tag","https://movie.douban.com/j/search_subjects?type=movie&tag=%E6%9C%80%E6%96%B0&page_limit=10&page_start=0")])
    #movies.update([("all_tag","https://movie.douban.com/tag/爱情")])
    #for tag in config.movie_all_tags:
     #   movies.update([("all_tag","https://movie.douban.com/tag/"+tag)])
    #print(movies)
    #movies.update([("movie_detail","https://movie.douban.com/subject/26336252/")])
    #movies.update([("actor_detail","https://movie.douban.com/celebrity/1403275/")])
    #movies.update([("movie_detail","https://movie.douban.com/subject/26258068/")])
    #movies.update([("movie_detail","https://movie.douban.com/subject/26336256/")])

    #movies.update([("movie_detail","https://movie.douban.com/subject/26416062/")])
    """
    try:
        db = pymysql.connect("localhost", config.user_name, config.user_pwd, "douban_movie",charset=config.database_charset)
    except pymysql.err as e:
        logging("open your mysql please...")
        logging(e)
    cursor = db.cursor()
    sql="select %s from %s where douban_rate is null" % ("movie_id","movie")
    try:
        cursor.execute(sql)
        movies.update([("movie_detail","https://movie.douban.com/subject/"+str(id[0])) for id in cursor.fetchall()])
    except pymysql.err as e:
        logging("open your mysql please...")
        logging(e)
    """
    """
    try:
        db = pymysql.connect("localhost", config.user_name, config.user_pwd, "douban_movie",charset=config.database_charset)
    except pymysql.err as e:
        logging("open your mysql please...")
        logging(e)
    cursor = db.cursor()
    sql="select %s from %s where sex is null and actor_id < 100000000" % ("actor_id","actor")
    try:
        cursor.execute(sql)
        movies.update([("actor_detail","https://movie.douban.com/celebrity/"+str(id[0])) for id in cursor.fetchall()])
    except pymysql.err as e:
        logging("open your mysql please...")
        logging(e)
    """
    get_all(movies)
    exit()