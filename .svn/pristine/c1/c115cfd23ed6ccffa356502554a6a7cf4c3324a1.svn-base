#!/bin/bash
PID=$(ps -ef | grep qrpush*.jar | grep -v grep | awk '{ print $2 }')
if [ -z "$PID" ]
then
    echo 开始启动程序 .......
    nohup java -jar qrpush*.jar --server.port=8081  >>  ./console.log  2>&1 &
    echo 程序启动成功
else
    echo 程序已经运行,kill $PID
    kill $PID
    echo 开始启动程序 .......
    nohup java -jar qrpush*.jar --server.port=8081 >> ./console.log  2>&1 &
    echo 程序启动成功
fi