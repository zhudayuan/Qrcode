# qrpush
推送系统
1.初始化部署
在./qrpush目录下,执行命令 
mvn clean package -P zip -P pub  #正式机
mvn clean package -P zip -P dev  #测试机
将会生成qrpush\target\qrpush-release-0.1.0.tar.gz文件
上传到服务器目录,解压
tar xf qrpush-release-0.1.0.tar.gz
cd qrpush/bin
chmod 755 *.sh
启动服务
./qr.sh start
tail -f ../logs/console.out 查看是否启动成功
支持命令 ./qr.sh {start|start-foreground|stop|restart|status|print-cmd}

2.升级版本(修改代码,不添加包)
mvn clean package  -P pub  #正式机
mvn clean package  -P dev  #测试机
将会生成qrpush\target\bootstrap.jar(目前大小几十K)文件,将其上传到服务器qrpush/bin目录下,覆盖原文件
./qr.sh restart
tail -f ../logs/console.out 查看是否重新启动成功
3.日志
日志目录在qrpush/logs
启动日志:  console.out  
二维码日志:  qr_code.log  
日志格式:   2018-04-25 12:32:20.747 - {"backwp":1075,"workhours":60000,"qrurl":"https://www.juzijumi.com/1?qrid=515339","code":1,"aftertime":0,"backurl":"https://www.juzijumi.com/tvImage/185.png","countdown":true,"qrid":515339,"qrtype":"11","starttime":1525157520000,"qrsize":112,"deliverid":"123","backhp":515,"qrwp":1112,"backsize":185,"cardid":"8270104048478636","qrhp":566,"serviceid":6205,"aid":"101","qrtime":"20180431 14:52:00","timestamp":"20180425123220"}
推送日志:   qr_push_result.log  
日志格式:   2018-04-25 12:31:55.463 - send msg offline,userId=8270104048469031
请求日志:   qr_request.log
日志格式:   2018-04-25 12:29:42.759 - REQUEST_INFO:IP=27.19.38.2,GET,URL=http://120.79.229.253:8081/que/des3,ARGS=[{"param":["8270104243895428"]}]
            2018-04-25 12:29:42.779 - RESPONSE_INFO:{"code":0,"data":{"Des3.encode加密:8270104243895428":"h+2zfAQSFbmf1/sj9oXqSLrYMg+1Aee+","Des3.hexdecrypt加密:8270104243895428":"87EDB37C041215B99FD7FB23F685EA48BAD8320FB501E7BE"},"msg":"success"}




