# fiora

#### 介绍
一个基于数据库实现的高性能分布式发号器


#### 实现原理

根据ip+port(端口)表示一个取号对象。（为什么用ip+port?因为一台机器上可以部署同一个服务的集群，ip+port可以标识唯一的一个服务）


数据库设计如下：


将ip+port作为唯一索引，通过replace into语句，实现如下功能：

若已存在某条数据（ip+port），则将这条数据删除，再插入一条新纪录。

若不存在该条数据，则直接新增。

不管是先删除、再插入，还是直接插入，生成的（ip+port）数据 主键都是自动递增过的。


所以每次获取取号时，先replace into,再select 即可获取最新的号段。


这样有一个问题？

每次取号段时，都会很慢。因为会将对应的行锁住，只有一个线程会更新，拿到号段。其余的都被阻塞了。


解决方案：

针对于每次取号都需要从数据库查询，所以一次取号，然后放大n倍（100倍、1000倍之类），形成[号段*n,(号段+1)*n]这个数值区间

，存储到内存中，之后每次取号从内存中取号，减少了访问数据库的频次

还有一个问题？

当内存中号段用完，需要从数据库取号时，多个线程竞争取号，该怎么解决？？？ 不能让每个线程都去数据库中取最新的号段更新。。

解决方案：

如果到达了最后一个号码，那么阻塞住其他请求线程，最早的那个线程去数据库取个号段，再更新一下内存中的号段，就可以了。

将内存中的号段对象，作为锁，拿到锁的线程去数据库更新，获取最新的号段，然后再更新缓存。

至于其他的线程就阻塞在从缓存中取号的阶段，，，直到取完号。

新问题来了？怎么让一个线程执行，其他线程都被阻塞。

使用synchronize


参考文章：

https://mp.weixin.qq.com/s?__biz=MjM5MDI3MjA5MQ==&mid=2697266651&idx=2&sn=77a5b0d4cabcbb00fafeb6a409b93cd7&scene=21#wechat_redirect 
