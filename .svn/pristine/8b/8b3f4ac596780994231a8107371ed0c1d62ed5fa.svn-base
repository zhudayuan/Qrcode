#!/usr/bin/env bash

#指定JDK版本
JAVA_HOME=""
JAR_NAME="bootstrap.jar"
PRO_BIN="$(dirname "${BASH_SOURCE-$0}")"
PRO_BIN_DIR="$(cd "${PRO_BIN}"; pwd)"
PRO_HOME="$(dirname "$PRO_BIN_DIR")"
PRO_CFG_DIR="${PRO_HOME}/conf"
PRO_DATA_DIR="${PRO_HOME}/tmp"
PRO_LOG_DIR="${PRO_HOME}/logs"
mkdir -p "$PRO_DATA_DIR"  "$PRO_LOG_DIR"
PRO_PID_FILE="$PRO_DATA_DIR/program_run.pid"
_PRO_DAEMON_OUT="$PRO_LOG_DIR/console.out"

#组装java -d 参数
PRO_PARAM="-Dqr.home=${PRO_HOME}   "
PRO_PARAM="${PRO_PARAM} -Dmp.conf=$PRO_CFG_DIR/mpush.conf    "
PRO_PARAM="${PRO_PARAM} -Dqr.conf=$PRO_CFG_DIR/qr.conf    "
#PRO_PARAM="${PRO_PARAM} -Dlogging.path=$PRO_LOG_DIR "
PRO_PARAM="${PRO_PARAM} -Dlogging.config=$PRO_CFG_DIR/logback.xml   "


#add the conf dir to classpath
CLASSPATH="$PRO_CFG_DIR:$PRO_BIN_DIR/${JAR_NAME}:$CLASSPATH"
#make it work in the binary package
for i in "${PRO_HOME}"/lib/*.jar
do
    CLASSPATH="$i:$CLASSPATH"
done

PRO_MAIN="${PRO_BIN_DIR}/${JAR_NAME}"
echo "Using config: $PRO_PARAM" >&2


if [ "$JAVA_HOME" != "" ]; then
  JAVA="$JAVA_HOME/bin/java"
else
  JAVA=java
fi

case $1 in
start)
    echo  -n "Starting Program ... "
    if [ -f "$PRO_PID_FILE" ]; then
      if kill -0 `cat "$PRO_PID_FILE"` > /dev/null 2>&1; then
         echo $command already running as process `cat "$PRO_PID_FILE"`.
         exit 0
      fi
    fi
    nohup "$JAVA" "-Dqr.home=${PRO_HOME}"  "-Dmp.conf=$PRO_CFG_DIR/mpush.conf" "-Dqr.conf=$PRO_CFG_DIR/qr.conf" "-Dlogging.config=$PRO_CFG_DIR/logback.xml"  -jar $PRO_MAIN > "$_PRO_DAEMON_OUT" 2>&1 < /dev/null &

    if [ $? -eq 0 ]
    then
      case "$OSTYPE" in
      *solaris*)
        /bin/echo "${!}\\c" > "$PRO_PID_FILE"
        ;;
      *)
        /bin/echo -n $! > "$PRO_PID_FILE"
        ;;
      esac
      if [ $? -eq 0 ];
      then
        sleep 1
        echo STARTED
      else
        echo FAILED TO WRITE PID
        exit 1
      fi
    else
      echo SERVER DID NOT START
      exit 1
    fi
    ;;
start-foreground)
    #"$JAVA" -jar "${PRO_PARAM}" -cp "$CLASSPATH" $PRO_MAIN
    "$JAVA" -jar "${PRO_PARAM}"  $PRO_MAIN
    ;;
print-cmd)
    echo "\"$JAVA\" -jar $PRO_MAIN "
    echo " \"${PRO_PARAM}\" "
    echo "$JVM_FLAGS "
    #echo "-cp \"$CLASSPATH\" "
    echo "> \"$_PRO_DAEMON_OUT\" 2>&1 < /dev/null"
    ;;
stop)
    echo "Stopping Program ... "
    if [ ! -f "$PRO_PID_FILE" ]
    then
      echo "no Program to stop (could not find file $PRO_PID_FILE)"
    else
      kill -15 $(cat "$PRO_PID_FILE")
      SLEEP=30
      SLEEP_COUNT=1
      while [ $SLEEP -ge 0 ]; do
        kill -0 $(cat "$PRO_PID_FILE") >/dev/null 2>&1
        if [ $? -gt 0 ]; then
          rm -f "$PRO_PID_FILE" >/dev/null 2>&1
          if [ $? != 0 ]; then
            if [ -w "$PRO_PID_FILE" ]; then
              cat /dev/null > "$PRO_PID_FILE"
            else
              echo "The PID file could not be removed or cleared."
            fi
          fi
          echo STOPPED
          break
        fi
        if [ $SLEEP -gt 0 ]; then
          echo "stopping ... $SLEEP_COUNT"
          sleep 1
        fi
        if [ $SLEEP -eq 0 ]; then
          echo "Program did not stop in time."
          echo "To aid diagnostics a thread dump has been written to standard out."
          kill -3 `cat "$PRO_PID_FILE"`
          echo "force stop Program."
          kill -9 `cat "$PRO_PID_FILE"`
          echo STOPPED
        fi
        SLEEP=`expr $SLEEP - 1`
        SLEEP_COUNT=`expr $SLEEP_COUNT + 1`
      done
    fi
    exit 0
    ;;
restart)
    shift
    "$0" stop ${@}
    sleep 1
    "$0" start ${@}
    ;;
status)
    # -q is necessary on some versions of linux where nc returns too quickly, and no stat result is output
    shell= "ps  -ef|grep "$PRO_MAIN" |grep -v grep"
    echo shell
    echo `shell`
    ;;
*)
    echo "Usage: $0 {start|start-foreground|stop|restart|status|print-cmd}" >&2

esac