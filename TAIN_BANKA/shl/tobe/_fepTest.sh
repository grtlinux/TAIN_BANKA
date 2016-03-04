#!/bin/ksh

#------------------------------------------------------

MAIN_CLASS=ic.vela.ibridge.base.xml.sendrecv.XmlSendRecvTestMain

#------------------------------------------------------

DATEPATH=`date "+%Y%m%d"`

JOB_PATH=${HOME_PATH}/banca
JAVA_EXE=${JAVA_HOME}/bin/java

#------------------------------------------------------

CLASSPATH=${CLASSPATH}:${JOB_PATH}/lib/IB_Vela.1.130616.jar

#------------------------------------------------------

OPTION="-Xss256K"
OPTION="${OPTION} -Dfile.encoding=euc-kr"
# OPTION="${OPTION} -Dfile.encoding=utf-8"
OPTION="${OPTION} -Ddev.author=°­¼®"
OPTION="${OPTION} -Ddev.version=1.6.0_45"

#------------------------------------------------------

OPTION="${OPTION} -Dsystem.ib.param.host.ip=210.216.158.157"
OPTION="${OPTION} -Dsystem.ib.param.host.port=7960"

#------------------------------------------------------

${JAVA_EXE} -cp ${CLASSPATH} ${OPTION} ${MAIN_CLASS}
