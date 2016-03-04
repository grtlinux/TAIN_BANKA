#!/bin/ksh

#------------------------------------------------------

MAIN_CLASS=ic.vela.ibridge.server.ap.apmanager.ApManager

#------------------------------------------------------

DATEPATH=`date "+%Y%m%d"`

HOME_PATH=/hw01/domains/hwbanca
JOB_PATH=${HOME_PATH}/interface/fep
# JAVA_EXE=${JAVA_HOME}/bin/java
JAVA_EXE=/usr/bin/java

#------------------------------------------------------

CLASSPATH=${CLASSPATH}:${JOB_PATH}/lib/IB_Vela.2.130624.jar

#------------------------------------------------------

OPTION="-Xss256K"
OPTION="${OPTION} -Dfile.encoding=euc-kr"
# OPTION="${OPTION} -Dfile.encoding=utf-8"
OPTION="${OPTION} -Ddev.author=����"
OPTION="${OPTION} -Ddev.version=1.6.0_45"

#------------------------------------------------------
# basefolder �� ������ ����ϰ� �⺻�� �Ǵ� ȯ��������
# WAS�� ������ �� �߰��� ����.
# WAS���� ���� : -Dsystem.ib.param.basefolder=/hw01/domains/hwbanca/interface/fep/server/

OPTION="${OPTION} -Dsystem.ib.param.server=ap"
OPTION="${OPTION} -Dsystem.ib.param.basefolder=${JOB_PATH}/server/"

#------------------------------------------------------

${JAVA_EXE} -cp ${CLASSPATH} ${OPTION} ${MAIN_CLASS}
