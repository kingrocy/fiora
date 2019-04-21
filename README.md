# fiora

#### 介绍
一个基于数据库实现的高性能分布式发号器。

#### 实现原理

通过数据库发号,是基于db的主键自动递增和数据库的行级锁，来保证每一个号只会被一个线程拿到且号自动递增。

为了提高性能，数据库发号一次发多个号，存储在内存中，应用来自动分发给单独的线程。

具体实现原理参考如下文章：

https://mp.weixin.qq.com/s?__biz=MjM5MDI3MjA5MQ==&mid=2697266651&idx=2&sn=77a5b0d4cabcbb00fafeb6a409b93cd7&scene=21#wechat_redirect 
