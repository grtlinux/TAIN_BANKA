#!/bin/ksh

DATEPATH=`date "+%Y%m%d"`

JOB_PATH=${HOME_PATH}/webCash

JAVA_EXE=${JAVA_HOME}/bin/java
MAIN_CLASS=ic.vela.ibridge.test.TestServer

CLASSPATH=${CLASSPATH}:./IB_Vela.0.130528.jar

OPTION="-Xss256K"
OPTION="${OPTION} -Dfile.encoding=euc-kr"
# OPTION="${OPTION} -Dfile.encoding=utf-8"
OPTION="${OPTION} -Ddev.author=����"
OPTION="${OPTION} -Ddev.version=1.6.0_45"

OPTION="${OPTION} -Dsystem.property.host=127.0.0.1"
OPTION="${OPTION} -Dsystem.property.waitsecs=5"

# OPTION="${OPTION} -Dsystem.property.port=16501"      # local Test

# OPTION="${OPTION} -Dsystem.property.port=44013"      # ��ȭ���� ��ġ����
# OPTION="${OPTION} -Dsystem.property.port=44023"      # ���ѻ��� ��ġ����
# OPTION="${OPTION} -Dsystem.property.port=44033"      # �ϳ�HSBC���� ��ġ����
# OPTION="${OPTION} -Dsystem.property.port=44043"      # �ﱹ���� ��ġ����     (5.29)
# OPTION="${OPTION} -Dsystem.property.port=44053"      # ī�������� ��ġ����
# OPTION="${OPTION} -Dsystem.property.port=44063"      # �˸��������� ��ġ����
# OPTION="${OPTION} -Dsystem.property.port=44073"      # �Ｚ���� ��ġ����

${JAVA_EXE} -cp ${CLASSPATH} ${OPTION} ${MAIN_CLASS}
