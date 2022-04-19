---
layout: post
title : 排查一次回滚事务不生效问题——动态数据源中的事务机制
categories : java
---

多数据源与事务

---

todo

# 背景



# 排查思路



### 动态数据源

去掉动态数据源配置，采用单一数据源配置，排除动态数据源的干扰



事务回滚生效，果然是动态数据源配置的问题



### debug日志比对



动态数据源的回滚日志

```
2022-04-19 10:54:11,189 DEBUG [http-nio-8080-exec-1] o.s.j.d.DataSourceTransactionManager[370] ####QTraceId[f_qbd_admin_server_220419.105410.192.168.137.1.5080.11_1]-QSpanId[1] Creating new transaction with name [com.qunar.flight.qbd.admin.sal.cte.wrapper.impl.WrapperServiceImpl.add]: PROPAGATION_REQUIRED,ISOLATION_DEFAULT; 'releaseTransactionManager',-java.lang.Exception
2022-04-19 10:54:11,193 DEBUG [http-nio-8080-exec-1] o.s.j.d.DataSourceTransactionManager[267] ####QTraceId[f_qbd_admin_server_220419.105410.192.168.137.1.5080.11_1]-QSpanId[1] Acquired Connection [com.qunar.db.resource.isolate.IsolateConnection@66356ac3] for JDBC transaction
2022-04-19 10:54:11,195 INFO [chaos-config-init] qunar.log.ChaosRemoteLogger[88] #### on load chaos_record_invocation.properties, on: true
2022-04-19 10:54:11,206 DEBUG [http-nio-8080-exec-1] o.s.j.d.DataSourceTransactionManager[285] ####QTraceId[f_qbd_admin_server_220419.105410.192.168.137.1.5080.11_1]-QSpanId[1] Switching JDBC Connection [com.qunar.db.resource.isolate.IsolateConnection@66356ac3] to manual commit
2022-04-19 10:54:11,280 DEBUG [http-nio-8080-exec-1] org.mybatis.spring.SqlSessionUtils[49] ####QTraceId[f_qbd_admin_server_220419.105410.192.168.137.1.5080.11_1]-QSpanId[1] Creating a new SqlSession
2022-04-19 10:54:11,313 DEBUG [http-nio-8080-exec-1] org.mybatis.spring.SqlSessionUtils[49] ####QTraceId[f_qbd_admin_server_220419.105410.192.168.137.1.5080.11_1]-QSpanId[1] Registering transaction synchronization for SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5fe71729]
2022-04-19 10:54:11,496 DEBUG [http-nio-8080-exec-1] o.s.jdbc.datasource.DataSourceUtils[116] ####QTraceId[f_qbd_admin_server_220419.105410.192.168.137.1.5080.11_1]-QSpanId[1] Fetching JDBC Connection from DataSource
Tue Apr 19 10:54:11 CST 2022 WARN: Establishing SSL connection without server's identity verification is not recommended. According to MySQL 5.5.45+, 5.6.26+ and 5.7.6+ requirements SSL connection must be established by default if explicit option isn't set. For compliance with existing applications not using SSL the verifyServerCertificate property is set to 'false'. You need either to explicitly disable SSL by setting useSSL=false, or set useSSL=true and provide truststore for server certificate verification.
2022-04-19 10:54:11,519 DEBUG [http-nio-8080-exec-1] o.m.s.t.SpringManagedTransaction[49] ####QTraceId[f_qbd_admin_server_220419.105410.192.168.137.1.5080.11_1]-QSpanId[1] JDBC Connection [com.qunar.db.resource.isolate.IsolateConnection@2aa08a3f] will be managed by Spring
2022-04-19 10:54:11,528 DEBUG [http-nio-8080-exec-1] c.q.f.q.a.d.w.m.W.selectOne[137] ####QTraceId[f_qbd_admin_server_220419.105410.192.168.137.1.5080.11_1]-QSpanId[1] ==>  Preparing: SELECT id,wrapper_id,wrapper_name,domain,wrapper_type,trip_type,biz_type,city_flag,agent_flag,status_ref,operator,create_time,update_time FROM wrapper_info WHERE (wrapper_id = ?)
2022-04-19 10:54:11,550 DEBUG [http-nio-8080-exec-1] c.q.f.q.a.d.w.m.W.selectOne[137] ####QTraceId[f_qbd_admin_server_220419.105410.192.168.137.1.5080.11_1]-QSpanId[1] ==> Parameters: gnqssagag(String)
2022-04-19 10:54:11,631 DEBUG [http-nio-8080-exec-1] c.q.f.q.a.d.w.m.W.selectOne[137] ####QTraceId[f_qbd_admin_server_220419.105410.192.168.137.1.5080.11_1]-QSpanId[1] <==      Total: 0
2022-04-19 10:54:11,632 DEBUG [http-nio-8080-exec-1] org.mybatis.spring.SqlSessionUtils[49] ####QTraceId[f_qbd_admin_server_220419.105410.192.168.137.1.5080.11_1]-QSpanId[1] Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5fe71729]
2022-04-19 10:54:11,635 DEBUG [http-nio-8080-exec-1] org.mybatis.spring.SqlSessionUtils[49] ####QTraceId[f_qbd_admin_server_220419.105410.192.168.137.1.5080.11_1]-QSpanId[1] Fetched SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5fe71729] from current transaction
2022-04-19 10:54:11,648 DEBUG [http-nio-8080-exec-1] c.q.f.q.a.d.w.m.W.insert[137] ####QTraceId[f_qbd_admin_server_220419.105410.192.168.137.1.5080.11_1]-QSpanId[1] ==>  Preparing: INSERT INTO wrapper_info ( wrapper_id, wrapper_name, domain, wrapper_type, trip_type, biz_type, city_flag, agent_flag ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ? )
2022-04-19 10:54:11,649 DEBUG [http-nio-8080-exec-1] c.q.f.q.a.d.w.m.W.insert[137] ####QTraceId[f_qbd_admin_server_220419.105410.192.168.137.1.5080.11_1]-QSpanId[1] ==> Parameters: gnqssagag(String), wrapper名称(String), ww.dasbad.dfag(String), 2(Integer), 1(Integer), 2(Integer), false(Boolean), false(Boolean)
2022-04-19 10:54:11,654 DEBUG [http-nio-8080-exec-1] c.q.f.q.a.d.w.m.W.insert[137] ####QTraceId[f_qbd_admin_server_220419.105410.192.168.137.1.5080.11_1]-QSpanId[1] <==    Updates: 1
2022-04-19 10:54:11,661 DEBUG [http-nio-8080-exec-1] org.mybatis.spring.SqlSessionUtils[49] ####QTraceId[f_qbd_admin_server_220419.105410.192.168.137.1.5080.11_1]-QSpanId[1] Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5fe71729]
2022-04-19 10:54:11,699 INFO [http-nio-8080-exec-1] c.q.f.q.a.s.c.w.i.WrapperInfoServiceImpl[44] ####QTraceId[f_qbd_admin_server_220419.105410.192.168.137.1.5080.11_1]-QSpanId[1] 新增wrapperInfo成功,infoPO={"id":40,"wrapperId":"gnqssagag","wrapperName":"wrapper名称","domain":"ww.dasbad.dfag","wrapperType":"cpa_one_trip","tripType":"round_trip","bizType":"x_round_trip_domestic","cityFlag":false,"agentFlag":false}
2022-04-19 10:54:11,705 DEBUG [http-nio-8080-exec-1] org.mybatis.spring.SqlSessionUtils[49] ####QTraceId[f_qbd_admin_server_220419.105410.192.168.137.1.5080.11_1]-QSpanId[1] Fetched SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5fe71729] from current transaction
2022-04-19 10:54:11,708 DEBUG [http-nio-8080-exec-1] c.q.f.q.a.d.w.m.W.selectOne[137] ####QTraceId[f_qbd_admin_server_220419.105410.192.168.137.1.5080.11_1]-QSpanId[1] ==>  Preparing: SELECT id,wrapper_id,status_flag,source,reason,operator,create_time,update_time FROM wrapper_status WHERE (wrapper_id = ?)
2022-04-19 10:54:11,709 DEBUG [http-nio-8080-exec-1] c.q.f.q.a.d.w.m.W.selectOne[137] ####QTraceId[f_qbd_admin_server_220419.105410.192.168.137.1.5080.11_1]-QSpanId[1] ==> Parameters: gnqssagag(String)
2022-04-19 10:54:11,712 DEBUG [http-nio-8080-exec-1] c.q.f.q.a.d.w.m.W.selectOne[137] ####QTraceId[f_qbd_admin_server_220419.105410.192.168.137.1.5080.11_1]-QSpanId[1] <==      Total: 0
2022-04-19 10:54:11,712 DEBUG [http-nio-8080-exec-1] org.mybatis.spring.SqlSessionUtils[49] ####QTraceId[f_qbd_admin_server_220419.105410.192.168.137.1.5080.11_1]-QSpanId[1] Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5fe71729]
2022-04-19 10:54:11,713 DEBUG [http-nio-8080-exec-1] org.mybatis.spring.SqlSessionUtils[49] ####QTraceId[f_qbd_admin_server_220419.105410.192.168.137.1.5080.11_1]-QSpanId[1] Fetched SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5fe71729] from current transaction
2022-04-19 10:54:11,717 DEBUG [http-nio-8080-exec-1] c.q.f.q.a.d.w.m.W.insert[137] ####QTraceId[f_qbd_admin_server_220419.105410.192.168.137.1.5080.11_1]-QSpanId[1] ==>  Preparing: INSERT INTO wrapper_status ( wrapper_id, status_flag, source, reason ) VALUES ( ?, ?, ?, ? )
2022-04-19 10:54:11,718 DEBUG [http-nio-8080-exec-1] c.q.f.q.a.d.w.m.W.insert[137] ####QTraceId[f_qbd_admin_server_220419.105410.192.168.137.1.5080.11_1]-QSpanId[1] ==> Parameters: gnqssagag(String), false(Boolean), 0(Integer), 新增测试(String)
2022-04-19 10:54:11,720 DEBUG [http-nio-8080-exec-1] c.q.f.q.a.d.w.m.W.insert[137] ####QTraceId[f_qbd_admin_server_220419.105410.192.168.137.1.5080.11_1]-QSpanId[1] <==    Updates: 1
2022-04-19 10:54:11,721 DEBUG [http-nio-8080-exec-1] org.mybatis.spring.SqlSessionUtils[49] ####QTraceId[f_qbd_admin_server_220419.105410.192.168.137.1.5080.11_1]-QSpanId[1] Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5fe71729]
2022-04-19 10:54:11,727 DEBUG [http-nio-8080-exec-1] org.mybatis.spring.SqlSessionUtils[49] ####QTraceId[f_qbd_admin_server_220419.105410.192.168.137.1.5080.11_1]-QSpanId[1] Fetched SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5fe71729] from current transaction
2022-04-19 10:54:11,728 DEBUG [http-nio-8080-exec-1] c.q.f.q.a.d.w.m.W.insert[137] ####QTraceId[f_qbd_admin_server_220419.105410.192.168.137.1.5080.11_1]-QSpanId[1] ==>  Preparing: INSERT INTO wrapper_log ( wrapper_id, status_flag, source, reason ) VALUES ( ?, ?, ?, ? )
2022-04-19 10:54:11,729 DEBUG [http-nio-8080-exec-1] c.q.f.q.a.d.w.m.W.insert[137] ####QTraceId[f_qbd_admin_server_220419.105410.192.168.137.1.5080.11_1]-QSpanId[1] ==> Parameters: gnqssagag(String), false(Boolean), 0(Integer), 新增测试(String)
2022-04-19 10:54:11,731 DEBUG [http-nio-8080-exec-1] c.q.f.q.a.d.w.m.W.insert[137] ####QTraceId[f_qbd_admin_server_220419.105410.192.168.137.1.5080.11_1]-QSpanId[1] <==    Updates: 1
2022-04-19 10:54:11,732 DEBUG [http-nio-8080-exec-1] org.mybatis.spring.SqlSessionUtils[49] ####QTraceId[f_qbd_admin_server_220419.105410.192.168.137.1.5080.11_1]-QSpanId[1] Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5fe71729]
2022-04-19 10:54:11,741 INFO [http-nio-8080-exec-1] c.q.f.q.a.s.c.w.i.WrapperLogServiceImpl[43] ####QTraceId[f_qbd_admin_server_220419.105410.192.168.137.1.5080.11_1]-QSpanId[1] 新增wrapperLog成功,logPO={"id":40,"wrapperId":"gnqssagag","statusFlag":false,"source":"MANUAL","reason":"新增测试"}
2022-04-19 10:54:11,741 INFO [http-nio-8080-exec-1] c.q.f.q.a.s.c.w.i.WrapperStatusServiceImpl[52] ####QTraceId[f_qbd_admin_server_220419.105410.192.168.137.1.5080.11_1]-QSpanId[1] 新增wrapperStatus成功,statusPO={"id":40,"wrapperId":"gnqssagag","statusFlag":false,"source":"MANUAL","reason":"新增测试"}
2022-04-19 10:54:11,755 DEBUG [http-nio-8080-exec-1] org.mybatis.spring.SqlSessionUtils[49] ####QTraceId[f_qbd_admin_server_220419.105410.192.168.137.1.5080.11_1]-QSpanId[1] Transaction synchronization deregistering SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5fe71729]
2022-04-19 10:54:11,756 DEBUG [http-nio-8080-exec-1] org.mybatis.spring.SqlSessionUtils[49] ####QTraceId[f_qbd_admin_server_220419.105410.192.168.137.1.5080.11_1]-QSpanId[1] Transaction synchronization closing SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5fe71729]
2022-04-19 10:54:11,759 DEBUG [http-nio-8080-exec-1] o.s.j.d.DataSourceTransactionManager[833] ####QTraceId[f_qbd_admin_server_220419.105410.192.168.137.1.5080.11_1]-QSpanId[1] Initiating transaction rollback
2022-04-19 10:54:11,760 DEBUG [http-nio-8080-exec-1] o.s.j.d.DataSourceTransactionManager[345] ####QTraceId[f_qbd_admin_server_220419.105410.192.168.137.1.5080.11_1]-QSpanId[1] Rolling back JDBC transaction on Connection [com.qunar.db.resource.isolate.IsolateConnection@66356ac3]
```



单一数据源的回滚日志

```
2022-04-18 21:37:10,819 DEBUG [http-nio-8080-exec-5] o.s.j.d.DataSourceTransactionManager[370] ####QTraceId[f_qbd_admin_server_220418.213710.192.168.137.1.25716.10_1]-QSpanId[1] Creating new transaction with name [com.qunar.flight.qbd.admin.sal.cte.wrapper.impl.WrapperServiceImpl.add]: PROPAGATION_REQUIRED,ISOLATION_DEFAULT; 'releaseTransactionManager',-java.lang.Exception
2022-04-18 21:37:10,823 DEBUG [http-nio-8080-exec-5] o.s.j.d.DataSourceTransactionManager[267] ####QTraceId[f_qbd_admin_server_220418.213710.192.168.137.1.25716.10_1]-QSpanId[1] Acquired Connection [com.qunar.db.resource.isolate.IsolateConnection@15f98a88] for JDBC transaction
2022-04-18 21:37:10,838 DEBUG [http-nio-8080-exec-5] o.s.j.d.DataSourceTransactionManager[285] ####QTraceId[f_qbd_admin_server_220418.213710.192.168.137.1.25716.10_1]-QSpanId[1] Switching JDBC Connection [com.qunar.db.resource.isolate.IsolateConnection@15f98a88] to manual commit
2022-04-18 21:37:10,914 DEBUG [http-nio-8080-exec-5] org.mybatis.spring.SqlSessionUtils[49] ####QTraceId[f_qbd_admin_server_220418.213710.192.168.137.1.25716.10_1]-QSpanId[1] Creating a new SqlSession
2022-04-18 21:37:10,946 DEBUG [http-nio-8080-exec-5] org.mybatis.spring.SqlSessionUtils[49] ####QTraceId[f_qbd_admin_server_220418.213710.192.168.137.1.25716.10_1]-QSpanId[1] Registering transaction synchronization for SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@7c58f6e1]
2022-04-18 21:37:11,146 DEBUG [http-nio-8080-exec-5] o.m.s.t.SpringManagedTransaction[49] ####QTraceId[f_qbd_admin_server_220418.213710.192.168.137.1.25716.10_1]-QSpanId[1] JDBC Connection [com.qunar.db.resource.isolate.IsolateConnection@15f98a88] will be managed by Spring
2022-04-18 21:37:11,154 DEBUG [http-nio-8080-exec-5] c.q.f.q.a.d.w.m.W.selectOne[137] ####QTraceId[f_qbd_admin_server_220418.213710.192.168.137.1.25716.10_1]-QSpanId[1] ==>  Preparing: SELECT id,wrapper_id,wrapper_name,domain,wrapper_type,trip_type,biz_type,city_flag,agent_flag,status_ref,operator,create_time,update_time FROM wrapper_info WHERE (wrapper_id = ?)
2022-04-18 21:37:11,173 DEBUG [http-nio-8080-exec-5] c.q.f.q.a.d.w.m.W.selectOne[137] ####QTraceId[f_qbd_admin_server_220418.213710.192.168.137.1.25716.10_1]-QSpanId[1] ==> Parameters: gnqssagag(String)
2022-04-18 21:37:11,245 DEBUG [http-nio-8080-exec-5] c.q.f.q.a.d.w.m.W.selectOne[137] ####QTraceId[f_qbd_admin_server_220418.213710.192.168.137.1.25716.10_1]-QSpanId[1] <==      Total: 0
2022-04-18 21:37:11,246 DEBUG [http-nio-8080-exec-5] org.mybatis.spring.SqlSessionUtils[49] ####QTraceId[f_qbd_admin_server_220418.213710.192.168.137.1.25716.10_1]-QSpanId[1] Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@7c58f6e1]
2022-04-18 21:37:11,251 DEBUG [http-nio-8080-exec-5] org.mybatis.spring.SqlSessionUtils[49] ####QTraceId[f_qbd_admin_server_220418.213710.192.168.137.1.25716.10_1]-QSpanId[1] Fetched SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@7c58f6e1] from current transaction
2022-04-18 21:37:11,259 DEBUG [http-nio-8080-exec-5] c.q.f.q.a.d.w.m.W.insert[137] ####QTraceId[f_qbd_admin_server_220418.213710.192.168.137.1.25716.10_1]-QSpanId[1] ==>  Preparing: INSERT INTO wrapper_info ( wrapper_id, wrapper_name, domain, wrapper_type, trip_type, biz_type, city_flag, agent_flag ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ? )
2022-04-18 21:37:11,260 DEBUG [http-nio-8080-exec-5] c.q.f.q.a.d.w.m.W.insert[137] ####QTraceId[f_qbd_admin_server_220418.213710.192.168.137.1.25716.10_1]-QSpanId[1] ==> Parameters: gnqssagag(String), wrapper名称(String), ww.dasbad.dfag(String), 2(Integer), 1(Integer), 2(Integer), false(Boolean), false(Boolean)
2022-04-18 21:37:11,265 DEBUG [http-nio-8080-exec-5] c.q.f.q.a.d.w.m.W.insert[137] ####QTraceId[f_qbd_admin_server_220418.213710.192.168.137.1.25716.10_1]-QSpanId[1] <==    Updates: 1
2022-04-18 21:37:11,270 DEBUG [http-nio-8080-exec-5] org.mybatis.spring.SqlSessionUtils[49] ####QTraceId[f_qbd_admin_server_220418.213710.192.168.137.1.25716.10_1]-QSpanId[1] Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@7c58f6e1]
2022-04-18 21:37:11,306 INFO [http-nio-8080-exec-5] c.q.f.q.a.s.c.w.i.WrapperInfoServiceImpl[44] ####QTraceId[f_qbd_admin_server_220418.213710.192.168.137.1.25716.10_1]-QSpanId[1] 新增wrapperInfo成功,infoPO={"id":39,"wrapperId":"gnqssagag","wrapperName":"wrapper名称","domain":"ww.dasbad.dfag","wrapperType":"cpa_one_trip","tripType":"round_trip","bizType":"x_round_trip_domestic","cityFlag":false,"agentFlag":false}
2022-04-18 21:37:11,312 DEBUG [http-nio-8080-exec-5] org.mybatis.spring.SqlSessionUtils[49] ####QTraceId[f_qbd_admin_server_220418.213710.192.168.137.1.25716.10_1]-QSpanId[1] Fetched SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@7c58f6e1] from current transaction
2022-04-18 21:37:11,316 DEBUG [http-nio-8080-exec-5] c.q.f.q.a.d.w.m.W.selectOne[137] ####QTraceId[f_qbd_admin_server_220418.213710.192.168.137.1.25716.10_1]-QSpanId[1] ==>  Preparing: SELECT id,wrapper_id,status_flag,source,reason,operator,create_time,update_time FROM wrapper_status WHERE (wrapper_id = ?)
2022-04-18 21:37:11,316 DEBUG [http-nio-8080-exec-5] c.q.f.q.a.d.w.m.W.selectOne[137] ####QTraceId[f_qbd_admin_server_220418.213710.192.168.137.1.25716.10_1]-QSpanId[1] ==> Parameters: gnqssagag(String)
2022-04-18 21:37:11,319 DEBUG [http-nio-8080-exec-5] c.q.f.q.a.d.w.m.W.selectOne[137] ####QTraceId[f_qbd_admin_server_220418.213710.192.168.137.1.25716.10_1]-QSpanId[1] <==      Total: 0
2022-04-18 21:37:11,319 DEBUG [http-nio-8080-exec-5] org.mybatis.spring.SqlSessionUtils[49] ####QTraceId[f_qbd_admin_server_220418.213710.192.168.137.1.25716.10_1]-QSpanId[1] Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@7c58f6e1]
2022-04-18 21:37:11,320 DEBUG [http-nio-8080-exec-5] org.mybatis.spring.SqlSessionUtils[49] ####QTraceId[f_qbd_admin_server_220418.213710.192.168.137.1.25716.10_1]-QSpanId[1] Fetched SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@7c58f6e1] from current transaction
2022-04-18 21:37:11,322 DEBUG [http-nio-8080-exec-5] c.q.f.q.a.d.w.m.W.insert[137] ####QTraceId[f_qbd_admin_server_220418.213710.192.168.137.1.25716.10_1]-QSpanId[1] ==>  Preparing: INSERT INTO wrapper_status ( wrapper_id, status_flag, source, reason ) VALUES ( ?, ?, ?, ? )
2022-04-18 21:37:11,323 DEBUG [http-nio-8080-exec-5] c.q.f.q.a.d.w.m.W.insert[137] ####QTraceId[f_qbd_admin_server_220418.213710.192.168.137.1.25716.10_1]-QSpanId[1] ==> Parameters: gnqssagag(String), false(Boolean), 0(Integer), 新增测试(String)
2022-04-18 21:37:11,333 DEBUG [http-nio-8080-exec-5] c.q.f.q.a.d.w.m.W.insert[137] ####QTraceId[f_qbd_admin_server_220418.213710.192.168.137.1.25716.10_1]-QSpanId[1] <==    Updates: 1
2022-04-18 21:37:11,334 DEBUG [http-nio-8080-exec-5] org.mybatis.spring.SqlSessionUtils[49] ####QTraceId[f_qbd_admin_server_220418.213710.192.168.137.1.25716.10_1]-QSpanId[1] Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@7c58f6e1]
2022-04-18 21:37:11,339 DEBUG [http-nio-8080-exec-5] org.mybatis.spring.SqlSessionUtils[49] ####QTraceId[f_qbd_admin_server_220418.213710.192.168.137.1.25716.10_1]-QSpanId[1] Fetched SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@7c58f6e1] from current transaction
2022-04-18 21:37:11,339 DEBUG [http-nio-8080-exec-5] c.q.f.q.a.d.w.m.W.insert[137] ####QTraceId[f_qbd_admin_server_220418.213710.192.168.137.1.25716.10_1]-QSpanId[1] ==>  Preparing: INSERT INTO wrapper_log ( wrapper_id, status_flag, source, reason ) VALUES ( ?, ?, ?, ? )
2022-04-18 21:37:11,340 DEBUG [http-nio-8080-exec-5] c.q.f.q.a.d.w.m.W.insert[137] ####QTraceId[f_qbd_admin_server_220418.213710.192.168.137.1.25716.10_1]-QSpanId[1] ==> Parameters: gnqssagag(String), false(Boolean), 0(Integer), 新增测试(String)
2022-04-18 21:37:11,342 DEBUG [http-nio-8080-exec-5] c.q.f.q.a.d.w.m.W.insert[137] ####QTraceId[f_qbd_admin_server_220418.213710.192.168.137.1.25716.10_1]-QSpanId[1] <==    Updates: 1
2022-04-18 21:37:11,343 DEBUG [http-nio-8080-exec-5] org.mybatis.spring.SqlSessionUtils[49] ####QTraceId[f_qbd_admin_server_220418.213710.192.168.137.1.25716.10_1]-QSpanId[1] Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@7c58f6e1]
2022-04-18 21:37:11,350 INFO [http-nio-8080-exec-5] c.q.f.q.a.s.c.w.i.WrapperLogServiceImpl[43] ####QTraceId[f_qbd_admin_server_220418.213710.192.168.137.1.25716.10_1]-QSpanId[1] 新增wrapperLog成功,logPO={"id":39,"wrapperId":"gnqssagag","statusFlag":false,"source":"MANUAL","reason":"新增测试"}
2022-04-18 21:37:11,351 INFO [http-nio-8080-exec-5] c.q.f.q.a.s.c.w.i.WrapperStatusServiceImpl[52] ####QTraceId[f_qbd_admin_server_220418.213710.192.168.137.1.25716.10_1]-QSpanId[1] 新增wrapperStatus成功,statusPO={"id":39,"wrapperId":"gnqssagag","statusFlag":false,"source":"MANUAL","reason":"新增测试"}
2022-04-18 21:37:11,361 DEBUG [http-nio-8080-exec-5] org.mybatis.spring.SqlSessionUtils[49] ####QTraceId[f_qbd_admin_server_220418.213710.192.168.137.1.25716.10_1]-QSpanId[1] Transaction synchronization deregistering SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@7c58f6e1]
2022-04-18 21:37:11,361 DEBUG [http-nio-8080-exec-5] org.mybatis.spring.SqlSessionUtils[49] ####QTraceId[f_qbd_admin_server_220418.213710.192.168.137.1.25716.10_1]-QSpanId[1] Transaction synchronization closing SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@7c58f6e1]
2022-04-18 21:37:11,362 DEBUG [http-nio-8080-exec-5] o.s.j.d.DataSourceTransactionManager[833] ####QTraceId[f_qbd_admin_server_220418.213710.192.168.137.1.25716.10_1]-QSpanId[1] Initiating transaction rollback
2022-04-18 21:37:11,362 DEBUG [http-nio-8080-exec-5] o.s.j.d.DataSourceTransactionManager[345] ####QTraceId[f_qbd_admin_server_220418.213710.192.168.137.1.25716.10_1]-QSpanId[1] Rolling back JDBC transaction on Connection [com.qunar.db.resource.isolate.IsolateConnection@15f98a88]
2022-04-18 21:37:11,365 DEBUG [http-nio-8080-exec-5] o.s.j.d.DataSourceTransactionManager[389] ####QTraceId[f_qbd_admin_server_220418.213710.192.168.137.1.25716.10_1]-QSpanId[1] Releasing JDBC Connection [com.qunar.db.resource.isolate.IsolateConnection@15f98a88] after transactio
```





# 源代码分析



单一数据源配置的情况下，触发事务回滚



