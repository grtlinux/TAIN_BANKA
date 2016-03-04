#!/bin/ksh

DATEPATH=`date "+%Y%m%d"`

JOB_PATH=${HOME_PATH}/webCash

JAVA_EXE=${JAVA_HOME}/bin/java
MAIN_CLASS=ic.vela.ibridge.test.TestClient

CLASSPATH=${CLASSPATH}:./IB_Vela.0.130528.jar

OPTION="-Xss256K"
OPTION="${OPTION} -Dfile.encoding=euc-kr"
# OPTION="${OPTION} -Dfile.encoding=utf-8"
OPTION="${OPTION} -Ddev.author=강석"
OPTION="${OPTION} -Ddev.version=1.6.0_45"

# OPTION="${OPTION} -Dsystem.property.host=127.0.0.1"
# OPTION="${OPTION} -Dsystem.property.port=16501"

OPTION="${OPTION} -Dsystem.property.waitsecs=5"

OPTION="${OPTION} -Dsystem.property.host=10.60.101.35"      # 흥국생명 개발 : 온라인
OPTION="${OPTION} -Dsystem.property.port=8010"

${JAVA_EXE} -cp ${CLASSPATH} ${OPTION} ${MAIN_CLASS}
