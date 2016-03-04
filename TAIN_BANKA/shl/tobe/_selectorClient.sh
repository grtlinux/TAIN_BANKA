#!/bin/ksh

#------------------------------------------------------

MAIN_CLASS=ic.vela.ibridge.test.selector.sessiontest.SocketChannelSessionTestMain

#------------------------------------------------------

DATEPATH=`date "+%Y%m%d"`

JOB_PATH=${HOME_PATH}/banca
JAVA_EXE=${JAVA_HOME}/bin/java

#------------------------------------------------------

CLASSPATH=${CLASSPATH}:${JOB_PATH}/lib/IB_Vela.1.130614.jar

#------------------------------------------------------

OPTION="-Xss256K"
OPTION="${OPTION} -Dfile.encoding=euc-kr"
# OPTION="${OPTION} -Dfile.encoding=utf-8"
OPTION="${OPTION} -Ddev.author=°­¼®"
OPTION="${OPTION} -Ddev.version=1.6.0_45"

#------------------------------------------------------

OPTION="${OPTION} -Dsystem.ib.param.basefolder=${JOB_PATH}/server/"

#------------------------------------------------------

${JAVA_EXE} -cp ${CLASSPATH} ${OPTION} ${MAIN_CLASS}
