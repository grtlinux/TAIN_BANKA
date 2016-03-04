#!/bin/ksh

DATEPATH=`date "+%Y%m%d"`

JOB_PATH=${HOME_PATH}/webCash

JAVA_EXE=${JAVA_HOME}/bin/java
MAIN_CLASS=ic.vela.ibridge.test.ConnectionCheck

CLASSPATH=${CLASSPATH}:./IB_Vela.0.130528.jar

OPTION="-Xss256K"
OPTION="${OPTION} -Dfile.encoding=euc-kr"
# OPTION="${OPTION} -Dfile.encoding=utf-8"
OPTION="${OPTION} -Ddev.author=����"
OPTION="${OPTION} -Ddev.version=1.6.0_45"

# OPTION="${OPTION} -Dsystem.property.host=127.0.0.1"         # local Test
# OPTION="${OPTION} -Dsystem.property.port=16501"
OPTION="${OPTION} -Dsystem.property.waitsecs=30"

########################################################################################
#------------ ��ȭ���� -------------------------------
# OPTION="${OPTION} -Dsystem.property.host=10.60.101.35"      # ��ȭ���� ���� : �¶���      (5.30)
# OPTION="${OPTION} -Dsystem.property.port=8010"

# OPTION="${OPTION} -Dsystem.property.host=10.60.101.35"      # ��ȭ���� ���� : ��ġ�۽�  (5.30)
# OPTION="${OPTION} -Dsystem.property.port=8011"

# OPTION="${OPTION} -Dsystem.property.host=10.60.101.35"      # ��ȭ���� ���� : ��ǰ����
# OPTION="${OPTION} -Dsystem.property.port=7040"

# OPTION="${OPTION} -Dsystem.property.host=210.121.184.126"   # ��ȭ���� � : �¶���
# OPTION="${OPTION} -Dsystem.property.port=8010"

# OPTION="${OPTION} -Dsystem.property.host=20.20.20.35"       # ��ȭ���� � : ��ġ�۽�
# OPTION="${OPTION} -Dsystem.property.port=8011"

# OPTION="${OPTION} -Dsystem.property.host=210.121.184.125"   # ��ȭ���� � : ��ǰ����
# OPTION="${OPTION} -Dsystem.property.port=7040"

########################################################################################
#------------ ���ѻ��� -------------------------------
# OPTION="${OPTION} -Dsystem.property.host=10.60.101.35"      # ���ѻ��� ���� : �¶���
# OPTION="${OPTION} -Dsystem.property.port=8010"

# OPTION="${OPTION} -Dsystem.property.host=10.60.101.35"      # ���ѻ��� ���� : ��ġ�۽�
# OPTION="${OPTION} -Dsystem.property.port=8011"

# OPTION="${OPTION} -Dsystem.property.host=10.60.101.35"      # ���ѻ��� ���� : ��ǰ����
# OPTION="${OPTION} -Dsystem.property.port=7040"

# OPTION="${OPTION} -Dsystem.property.host=210.121.184.126"   # ���ѻ��� � : �¶���
# OPTION="${OPTION} -Dsystem.property.port=8010"

# OPTION="${OPTION} -Dsystem.property.host=20.20.20.35"       # ���ѻ��� � : ��ġ�۽�
# OPTION="${OPTION} -Dsystem.property.port=8011"

# OPTION="${OPTION} -Dsystem.property.host=210.121.184.125"   # ���ѻ��� � : ��ǰ����
# OPTION="${OPTION} -Dsystem.property.port=7040"

########################################################################################
#------------ �ϳ�HSBC���� -------------------------------
# OPTION="${OPTION} -Dsystem.property.host=10.60.101.35"      # �ϳ�HSBC���� ���� : �¶���
# OPTION="${OPTION} -Dsystem.property.port=8010"

# OPTION="${OPTION} -Dsystem.property.host=10.60.101.35"      # �ϳ�HSBC���� ���� : ��ġ�۽�
# OPTION="${OPTION} -Dsystem.property.port=8011"

# OPTION="${OPTION} -Dsystem.property.host=10.60.101.35"      # �ϳ�HSBC���� ���� : ��ǰ����
# OPTION="${OPTION} -Dsystem.property.port=7040"

# OPTION="${OPTION} -Dsystem.property.host=210.121.184.126"   # �ϳ�HSBC���� � : �¶���
# OPTION="${OPTION} -Dsystem.property.port=8010"

# OPTION="${OPTION} -Dsystem.property.host=20.20.20.35"       # �ϳ�HSBC���� � : ��ġ�۽�
# OPTION="${OPTION} -Dsystem.property.port=8011"

# OPTION="${OPTION} -Dsystem.property.host=210.121.184.125"   # �ϳ�HSBC���� � : ��ǰ����
# OPTION="${OPTION} -Dsystem.property.port=7040"

########################################################################################
#------------ �ﱹ���� -------------------------------
# OPTION="${OPTION} -Dsystem.property.host=10.60.101.35"      # �ﱹ���� ���� : �¶���      (5.29)
# OPTION="${OPTION} -Dsystem.property.port=8010"

# OPTION="${OPTION} -Dsystem.property.host=10.60.101.35"      # �ﱹ���� ���� : ��ġ�۽�    (5.29)
# OPTION="${OPTION} -Dsystem.property.port=8011"

# OPTION="${OPTION} -Dsystem.property.host=10.60.101.35"      # �ﱹ���� ���� : ��ǰ����
# OPTION="${OPTION} -Dsystem.property.port=7040"

# OPTION="${OPTION} -Dsystem.property.host=210.121.184.126"   # �ﱹ���� � : �¶���
# OPTION="${OPTION} -Dsystem.property.port=8010"

# OPTION="${OPTION} -Dsystem.property.host=20.20.20.35"       # �ﱹ���� � : ��ġ�۽�
# OPTION="${OPTION} -Dsystem.property.port=8011"

# OPTION="${OPTION} -Dsystem.property.host=210.121.184.125"   # �ﱹ���� � : ��ǰ����
# OPTION="${OPTION} -Dsystem.property.port=7040"

########################################################################################
#------------ ī�������� -------------------------------
# OPTION="${OPTION} -Dsystem.property.host=10.60.101.35"      # ī�������� ���� : �¶���
# OPTION="${OPTION} -Dsystem.property.port=8010"

# OPTION="${OPTION} -Dsystem.property.host=10.60.101.35"      # ī�������� ���� : ��ġ�۽�
# OPTION="${OPTION} -Dsystem.property.port=8011"

# OPTION="${OPTION} -Dsystem.property.host=10.60.101.35"      # ī�������� ���� : ��ǰ����
# OPTION="${OPTION} -Dsystem.property.port=7040"

# OPTION="${OPTION} -Dsystem.property.host=210.121.184.126"   # ī�������� � : �¶���
# OPTION="${OPTION} -Dsystem.property.port=8010"

# OPTION="${OPTION} -Dsystem.property.host=20.20.20.35"       # ī�������� � : ��ġ�۽�
# OPTION="${OPTION} -Dsystem.property.port=8011"

# OPTION="${OPTION} -Dsystem.property.host=210.121.184.125"   # ī�������� � : ��ǰ����
# OPTION="${OPTION} -Dsystem.property.port=7040"

########################################################################################
#------------ �˸��������� -------------------------------
# OPTION="${OPTION} -Dsystem.property.host=10.60.101.35"      # �˸��������� ���� : �¶���
# OPTION="${OPTION} -Dsystem.property.port=8010"

# OPTION="${OPTION} -Dsystem.property.host=10.60.101.35"      # �˸��������� ���� : ��ġ�۽�
# OPTION="${OPTION} -Dsystem.property.port=8011"

# OPTION="${OPTION} -Dsystem.property.host=10.60.101.35"      # �˸��������� ���� : ��ǰ����
# OPTION="${OPTION} -Dsystem.property.port=7040"

# OPTION="${OPTION} -Dsystem.property.host=210.121.184.126"   # �˸��������� � : �¶���
# OPTION="${OPTION} -Dsystem.property.port=8010"

# OPTION="${OPTION} -Dsystem.property.host=20.20.20.35"       # �˸��������� � : ��ġ�۽�
# OPTION="${OPTION} -Dsystem.property.port=8011"

# OPTION="${OPTION} -Dsystem.property.host=210.121.184.125"   # �˸��������� � : ��ǰ����
# OPTION="${OPTION} -Dsystem.property.port=7040"

########################################################################################
#------------ �Ｚ���� -------------------------------
# OPTION="${OPTION} -Dsystem.property.host=10.60.101.35"      # �Ｚ���� ���� : �¶���
# OPTION="${OPTION} -Dsystem.property.port=8010"

# OPTION="${OPTION} -Dsystem.property.host=10.60.101.35"      # �Ｚ���� ���� : ��ġ�۽�
# OPTION="${OPTION} -Dsystem.property.port=8011"

# OPTION="${OPTION} -Dsystem.property.host=10.60.101.35"      # �Ｚ���� ���� : ��ǰ����
# OPTION="${OPTION} -Dsystem.property.port=7040"

# OPTION="${OPTION} -Dsystem.property.host=210.121.184.126"   # �Ｚ���� � : �¶���
# OPTION="${OPTION} -Dsystem.property.port=8010"

# OPTION="${OPTION} -Dsystem.property.host=20.20.20.35"       # �Ｚ���� � : ��ġ�۽�
# OPTION="${OPTION} -Dsystem.property.port=8011"

# OPTION="${OPTION} -Dsystem.property.host=210.121.184.125"   # �Ｚ���� � : ��ǰ����
# OPTION="${OPTION} -Dsystem.property.port=7040"

########################################################################################
########################################################################################

${JAVA_EXE} -cp ${CLASSPATH} ${OPTION} ${MAIN_CLASS}
