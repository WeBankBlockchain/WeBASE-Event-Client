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

stop(){
	checkProcess
	echo "==============================================================================================="
	if [ $processPid -ne 0 ]; then
	    echo -n "Stopping Server $APP_MAIN PID($processPid)..."
	    kill -9 $processPid
	    if [ $? -eq 0 ]; then
	        echo "[Success]"
	        echo "==============================================================================================="
	    else
	        echo "[Failed]"
	        echo "==============================================================================================="
	    fi
	else
	    echo "Server $APP_MAIN is not running"
	    echo "==============================================================================================="
	fi
}

stop