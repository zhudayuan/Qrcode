# alloc
mpush allocator 
### 服务用途
> * alloc 是针对client提供的一个轻量级的负载均衡服务
> * 每次客户端在链接MPUSH server之前都要调用下该服务
> * 以获取可用的MPUSH server列表,然后按顺序去尝试建立TCP链接,直到链接建立成功

1.初始化部署
在./alloc-mater目录下,执行命令 
mvn clean package -P zip -P pub  #正式机
mvn clean package -P zip -P dev  #测试机
将会生成alloc-master\target\alloc-release-0.8.0.tar.gz文件
上传到服务器目录,解压
tar xf alloc-release-0.8.0.tar.gz
cd alloc/bin
chmod 755 *.sh
启动服务
./mp.sh start
tail -f ../logs/mpush.out 查看是否启动成功
支持命令 ./mp.sh {start|start-foreground|stop|restart|status|print-cmd}

2.升级版本(修改代码,不添加包)
mvn clean package  -P pub  #正式机
mvn clean package  -P dev  #测试机
将会生成alloc-master\target\bootstrap.jar(目前大小几十K)文件,将其上传到服务器alloc/bin目录下,覆盖原文件
./mp.sh restart
tail -f ../logs/mpush.out 查看是否重新启动成功
3.日志
日志目录在alloc/logs
启动日志:  mpush.out  



