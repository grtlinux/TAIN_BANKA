#!/bin/ksh

#------------------------------------------------------

MAIN_CLASS=ic.vela.ibridge.common.version.Version

#------------------------------------------------------

DATEPATH=`date "+%Y%m%d"`

JOB_PATH=/hw01/ibridge/banca
JAVA_EXE=${JAVA_HOME}/bin/java  # 나중에 수정필요

#------------------------------------------------------

CLASSPATH=${CLASSPATH}:${JOB_PATH}/lib/IB_Vela.2.130624.jar

#------------------------------------------------------

OPTION="-Xss256K"
OPTION="${OPTION} -Dfile.encoding=euc-kr"
# OPTION="${OPTION} -Dfile.encoding=utf-8"
OPTION="${OPTION} -Ddev.author=강석"
OPTION="${OPTION} -Ddev.version=1.6.0_45"

#------------------------------------------------------

OPTION="${OPTION} -Dsystem.ib.param.basefolder=${JOB_PATH}/server/"

#------------------------------------------------------

${JAVA_EXE} -cp ${CLASSPATH} ${OPTION} ${MAIN_CLASS}
