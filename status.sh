#!/bin/bash

APP_MAIN=webase.event.client.Application
CURRENT_DIR=`pwd`
CONF_DIR=${CURRENT_DIR}/conf

processPid=0
checkProcess(){
    server_pid=`ps aux | grep java | grep $CURRENT_DIR | grep $APP_MAIN | awk '{print $2}'`
    if [ -n "$server_pid" ] && [ -n "$(echo $server_pid| sed -n "/^[0-9]\+$/p")" ]; then
        processPid=$server_pid
    else
        processPid=0
    fi
}

status(){
    checkProcess
    echo "==============================================================================================="
    if [ $processPid -ne 0 ]; then
        echo "Server $APP_MAIN is running PID($processPid)"
        echo "==============================================================================================="
    else
        echo "Server $APP_MAIN is not running"
        echo "==============================================================================================="
    fi
}

status
