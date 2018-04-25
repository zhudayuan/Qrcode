#!/bin/bash
PID=$(ps -ef | grep qrpush*.jar | grep -v grep | awk '{ print $2 }')
if [ -z "$PID" ]
then
    echo 程序已经停止了
else
    echo kill $PID
    kill $PID
     echo 程序停止成功
fi
