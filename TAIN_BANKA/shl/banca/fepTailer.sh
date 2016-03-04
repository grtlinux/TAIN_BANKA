#!/bin/ksh

#------------------------------------------------------

DATEPATH=`date "+%Y%m%d"`

JOB_PATH=/hw01/domains/hwbanca
JAVA_EXE=${JAVA_HOME}/bin/java

#------------------------------------------------------

tail -f ${JOB_PATH}/interface/fep/server/fep/log/${DATEPATH}.log
