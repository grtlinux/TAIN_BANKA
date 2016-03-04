#!/bin/ksh

DATEPATH=`date "+%Y%m%d"`

JOB_PATH=${HOME_PATH}/webCash

JAVA_EXE=${JAVA_HOME}/bin/java
MAIN_CLASS=ic.vela.ibridge.test.ConnectionCheck

CLASSPATH=${CLASSPATH}:./IB_Vela.0.130528.jar

OPTION="-Xss256K"
OPTION="${OPTION} -Dfile.encoding=euc-kr"
# OPTION="${OPTION} -Dfile.encoding=utf-8"
OPTION="${OPTION} -Ddev.author=강석"
OPTION="${OPTION} -Ddev.version=1.6.0_45"

# OPTION="${OPTION} -Dsystem.property.host=127.0.0.1"         # local Test
# OPTION="${OPTION} -Dsystem.property.port=16501"
OPTION="${OPTION} -Dsystem.property.waitsecs=30"

########################################################################################
#------------ 한화생명 -------------------------------
# OPTION="${OPTION} -Dsystem.property.host=10.60.101.35"      # 한화생명 개발 : 온라인      (5.30)
# OPTION="${OPTION} -Dsystem.property.port=8010"

# OPTION="${OPTION} -Dsystem.property.host=10.60.101.35"      # 한화생명 개발 : 배치송신  (5.30)
# OPTION="${OPTION} -Dsystem.property.port=8011"

# OPTION="${OPTION} -Dsystem.property.host=10.60.101.35"      # 한화생명 개발 : 상품설명
# OPTION="${OPTION} -Dsystem.property.port=7040"

# OPTION="${OPTION} -Dsystem.property.host=210.121.184.126"   # 한화생명 운영 : 온라인
# OPTION="${OPTION} -Dsystem.property.port=8010"

# OPTION="${OPTION} -Dsystem.property.host=20.20.20.35"       # 한화생명 운영 : 배치송신
# OPTION="${OPTION} -Dsystem.property.port=8011"

# OPTION="${OPTION} -Dsystem.property.host=210.121.184.125"   # 한화생명 운영 : 상품설명
# OPTION="${OPTION} -Dsystem.property.port=7040"

########################################################################################
#------------ 신한생명 -------------------------------
# OPTION="${OPTION} -Dsystem.property.host=10.60.101.35"      # 신한생명 개발 : 온라인
# OPTION="${OPTION} -Dsystem.property.port=8010"

# OPTION="${OPTION} -Dsystem.property.host=10.60.101.35"      # 신한생명 개발 : 배치송신
# OPTION="${OPTION} -Dsystem.property.port=8011"

# OPTION="${OPTION} -Dsystem.property.host=10.60.101.35"      # 신한생명 개발 : 상품설명
# OPTION="${OPTION} -Dsystem.property.port=7040"

# OPTION="${OPTION} -Dsystem.property.host=210.121.184.126"   # 신한생명 운영 : 온라인
# OPTION="${OPTION} -Dsystem.property.port=8010"

# OPTION="${OPTION} -Dsystem.property.host=20.20.20.35"       # 신한생명 운영 : 배치송신
# OPTION="${OPTION} -Dsystem.property.port=8011"

# OPTION="${OPTION} -Dsystem.property.host=210.121.184.125"   # 신한생명 운영 : 상품설명
# OPTION="${OPTION} -Dsystem.property.port=7040"

########################################################################################
#------------ 하나HSBC생명 -------------------------------
# OPTION="${OPTION} -Dsystem.property.host=10.60.101.35"      # 하나HSBC생명 개발 : 온라인
# OPTION="${OPTION} -Dsystem.property.port=8010"

# OPTION="${OPTION} -Dsystem.property.host=10.60.101.35"      # 하나HSBC생명 개발 : 배치송신
# OPTION="${OPTION} -Dsystem.property.port=8011"

# OPTION="${OPTION} -Dsystem.property.host=10.60.101.35"      # 하나HSBC생명 개발 : 상품설명
# OPTION="${OPTION} -Dsystem.property.port=7040"

# OPTION="${OPTION} -Dsystem.property.host=210.121.184.126"   # 하나HSBC생명 운영 : 온라인
# OPTION="${OPTION} -Dsystem.property.port=8010"

# OPTION="${OPTION} -Dsystem.property.host=20.20.20.35"       # 하나HSBC생명 운영 : 배치송신
# OPTION="${OPTION} -Dsystem.property.port=8011"

# OPTION="${OPTION} -Dsystem.property.host=210.121.184.125"   # 하나HSBC생명 운영 : 상품설명
# OPTION="${OPTION} -Dsystem.property.port=7040"

########################################################################################
#------------ 흥국생명 -------------------------------
# OPTION="${OPTION} -Dsystem.property.host=10.60.101.35"      # 흥국생명 개발 : 온라인      (5.29)
# OPTION="${OPTION} -Dsystem.property.port=8010"

# OPTION="${OPTION} -Dsystem.property.host=10.60.101.35"      # 흥국생명 개발 : 배치송신    (5.29)
# OPTION="${OPTION} -Dsystem.property.port=8011"

# OPTION="${OPTION} -Dsystem.property.host=10.60.101.35"      # 흥국생명 개발 : 상품설명
# OPTION="${OPTION} -Dsystem.property.port=7040"

# OPTION="${OPTION} -Dsystem.property.host=210.121.184.126"   # 흥국생명 운영 : 온라인
# OPTION="${OPTION} -Dsystem.property.port=8010"

# OPTION="${OPTION} -Dsystem.property.host=20.20.20.35"       # 흥국생명 운영 : 배치송신
# OPTION="${OPTION} -Dsystem.property.port=8011"

# OPTION="${OPTION} -Dsystem.property.host=210.121.184.125"   # 흥국생명 운영 : 상품설명
# OPTION="${OPTION} -Dsystem.property.port=7040"

########################################################################################
#------------ 카디프생명 -------------------------------
# OPTION="${OPTION} -Dsystem.property.host=10.60.101.35"      # 카디프생명 개발 : 온라인
# OPTION="${OPTION} -Dsystem.property.port=8010"

# OPTION="${OPTION} -Dsystem.property.host=10.60.101.35"      # 카디프생명 개발 : 배치송신
# OPTION="${OPTION} -Dsystem.property.port=8011"

# OPTION="${OPTION} -Dsystem.property.host=10.60.101.35"      # 카디프생명 개발 : 상품설명
# OPTION="${OPTION} -Dsystem.property.port=7040"

# OPTION="${OPTION} -Dsystem.property.host=210.121.184.126"   # 카디프생명 운영 : 온라인
# OPTION="${OPTION} -Dsystem.property.port=8010"

# OPTION="${OPTION} -Dsystem.property.host=20.20.20.35"       # 카디프생명 운영 : 배치송신
# OPTION="${OPTION} -Dsystem.property.port=8011"

# OPTION="${OPTION} -Dsystem.property.host=210.121.184.125"   # 카디프생명 운영 : 상품설명
# OPTION="${OPTION} -Dsystem.property.port=7040"

########################################################################################
#------------ 알리안츠생명 -------------------------------
# OPTION="${OPTION} -Dsystem.property.host=10.60.101.35"      # 알리안츠생명 개발 : 온라인
# OPTION="${OPTION} -Dsystem.property.port=8010"

# OPTION="${OPTION} -Dsystem.property.host=10.60.101.35"      # 알리안츠생명 개발 : 배치송신
# OPTION="${OPTION} -Dsystem.property.port=8011"

# OPTION="${OPTION} -Dsystem.property.host=10.60.101.35"      # 알리안츠생명 개발 : 상품설명
# OPTION="${OPTION} -Dsystem.property.port=7040"

# OPTION="${OPTION} -Dsystem.property.host=210.121.184.126"   # 알리안츠생명 운영 : 온라인
# OPTION="${OPTION} -Dsystem.property.port=8010"

# OPTION="${OPTION} -Dsystem.property.host=20.20.20.35"       # 알리안츠생명 운영 : 배치송신
# OPTION="${OPTION} -Dsystem.property.port=8011"

# OPTION="${OPTION} -Dsystem.property.host=210.121.184.125"   # 알리안츠생명 운영 : 상품설명
# OPTION="${OPTION} -Dsystem.property.port=7040"

########################################################################################
#------------ 삼성생명 -------------------------------
# OPTION="${OPTION} -Dsystem.property.host=10.60.101.35"      # 삼성생명 개발 : 온라인
# OPTION="${OPTION} -Dsystem.property.port=8010"

# OPTION="${OPTION} -Dsystem.property.host=10.60.101.35"      # 삼성생명 개발 : 배치송신
# OPTION="${OPTION} -Dsystem.property.port=8011"

# OPTION="${OPTION} -Dsystem.property.host=10.60.101.35"      # 삼성생명 개발 : 상품설명
# OPTION="${OPTION} -Dsystem.property.port=7040"

# OPTION="${OPTION} -Dsystem.property.host=210.121.184.126"   # 삼성생명 운영 : 온라인
# OPTION="${OPTION} -Dsystem.property.port=8010"

# OPTION="${OPTION} -Dsystem.property.host=20.20.20.35"       # 삼성생명 운영 : 배치송신
# OPTION="${OPTION} -Dsystem.property.port=8011"

# OPTION="${OPTION} -Dsystem.property.host=210.121.184.125"   # 삼성생명 운영 : 상품설명
# OPTION="${OPTION} -Dsystem.property.port=7040"

########################################################################################
########################################################################################

${JAVA_EXE} -cp ${CLASSPATH} ${OPTION} ${MAIN_CLASS}
