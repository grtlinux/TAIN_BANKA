#!/bin/ksh

#------------------------------------------------------

MAIN_CLASS=ic.vela.ibridge.server.fep.fepmanager.FepManager

#------------------------------------------------------

DATEPATH=`date "+%Y%m%d"`

JOB_PATH=/hw01/ibridge/banca
JAVA_EXE=${JAVA_HOME}/bin/java

#------------------------------------------------------

CLASSPATH=${CLASSPATH}:${JOB_PATH}/lib/IB_Vela.2.130624.jar

#------------------------------------------------------

OPTION="-Xss256K"
OPTION="${OPTION} -Dfile.encoding=euc-kr"
# OPTION="${OPTION} -Dfile.encoding=utf-8"
OPTION="${OPTION} -Ddev.author=����"
OPTION="${OPTION} -Ddev.version=1.6.0_45"

#------------------------------------------------------

OPTION="${OPTION} -Dsystem.ib.param.server=fep"
OPTION="${OPTION} -Dsystem.ib.param.basefolder=${JOB_PATH}/server/"

#------------------------------------------------------

okkill()
{
	pid=`ps -ef | grep ibridge | grep FepManager | grep -v tail | grep -v grep | awk '{print $2}'`

	if [ "$pid" = "" ];
	then
		echo " ### FepManager is not running "
	else
		kill -9 $pid;
		echo " ### FepManager is killed [PID=$pid]"
	fi
}


#------------------------------------------------------

okkill;

nohup ${JAVA_EXE} -cp ${CLASSPATH} ${OPTION} ${MAIN_CLASS} > /dev/null 2>&1 &

echo " ### FepManager is running "
ps -ef | grep ibridge | grep FepManager | grep -v tail | grep -v grep

