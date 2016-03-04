#!/bin/ksh

#------------------------------------------------------

DATEPATH=`date "+%Y%m%d"`

JOB_PATH=/hw01/ibridge/banca
JAVA_EXE=${JAVA_HOME}/bin/java

#------------------------------------------------------

tail -f ${JOB_PATH}/server/ext/log/${DATEPATH}.log
