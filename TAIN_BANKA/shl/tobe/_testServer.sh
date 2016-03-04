#!/bin/ksh

DATEPATH=`date "+%Y%m%d"`

JOB_PATH=${HOME_PATH}/webCash

JAVA_EXE=${JAVA_HOME}/bin/java
MAIN_CLASS=ic.vela.ibridge.test.TestServer

CLASSPATH=${CLASSPATH}:./IB_Vela.0.130528.jar

OPTION="-Xss256K"
OPTION="${OPTION} -Dfile.encoding=euc-kr"
# OPTION="${OPTION} -Dfile.encoding=utf-8"
OPTION="${OPTION} -Ddev.author=강석"
OPTION="${OPTION} -Ddev.version=1.6.0_45"

OPTION="${OPTION} -Dsystem.property.host=127.0.0.1"
OPTION="${OPTION} -Dsystem.property.waitsecs=5"

# OPTION="${OPTION} -Dsystem.property.port=16501"      # local Test

# OPTION="${OPTION} -Dsystem.property.port=44013"      # 한화생명 배치수신
# OPTION="${OPTION} -Dsystem.property.port=44023"      # 신한생명 배치수신
# OPTION="${OPTION} -Dsystem.property.port=44033"      # 하나HSBC생명 배치수신
# OPTION="${OPTION} -Dsystem.property.port=44043"      # 흥국생명 배치수신     (5.29)
# OPTION="${OPTION} -Dsystem.property.port=44053"      # 카디프생명 배치수신
# OPTION="${OPTION} -Dsystem.property.port=44063"      # 알리안츠생명 배치수신
# OPTION="${OPTION} -Dsystem.property.port=44073"      # 삼성생명 배치수신

${JAVA_EXE} -cp ${CLASSPATH} ${OPTION} ${MAIN_CLASS}
