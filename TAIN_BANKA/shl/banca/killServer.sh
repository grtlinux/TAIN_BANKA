#!/bin/ksh

#------------------------------------------------------

okkill_ApManager()
{
	pid=`ps -ef | grep hwbanca | grep ApManager | grep -v tail | grep -v grep | awk '{print $2}'`

	if [ "$pid" = "" ];
	then
		echo " ### ApManager is not running "
	else
		kill -9 $pid;
		echo " ### ApManager is killed [PID=$pid]"
	fi
}


#------------------------------------------------------

okkill_FepManager()
{
	pid=`ps -ef | grep hwbanca | grep FepManager | grep -v tail | grep -v grep | awk '{print $2}'`

	if [ "$pid" = "" ];
	then
		echo " ### FepManager is not running "
	else
		kill -9 $pid;
		echo " ### FepManager is killed [PID=$pid]"
	fi
}


#------------------------------------------------------

okkill_ExtManager()
{
	pid=`ps -ef | grep hwbanca | grep ExtManager | grep -v tail | grep -v grep | awk '{print $2}'`

	if [ "$pid" = "" ];
	then
		echo " ### ExtManager is not running "
	else
		kill -9 $pid;
		echo " ### ExtManager is killed [PID=$pid]"
	fi
}


#------------------------------------------------------

okkill_ApManager;
okkill_FepManager;
okkill_ExtManager;

