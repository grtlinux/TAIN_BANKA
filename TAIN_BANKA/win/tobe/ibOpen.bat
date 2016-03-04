@echo off
rem ------------------------------------------------------------------

set MAIN_CLASS=ic.vela.ibridge.server.ib.servant.ServantTestMain

rem ------------------------------------------------------------------

set HOME_PATH=D:/KANG/WORK/workspace/IB_Vela.2

set JAVA_HOME=D:/KANG/WORK/PROG/jre6.32
set JRE_HOME=%JAVA_HOME%

rem set JAVA_EXE=%JAVA_HOME%\bin\javaw.exe
set JAVA_EXE=%JAVA_HOME%\bin\java.exe

set MAIN_PATH=D:\KANG\WORK\workspace\IB_Vela.2\bin

rem ------------------------------------------------------------------

set CP=%MAIN_PATH%
rem set CP=
set CP=%CP%;%JAVA_HOME%\lib\tools.jar
set CP=%CP%;%JRE_HOME%\lib\rt.jar
set CP=%CP%;%JRE_HOME%\lib\resources.jar
set CP=%CP%;%JRE_HOME%\lib\jsse.jar
set CP=%CP%;%JRE_HOME%\lib\jce.jar
set CP=%CP%;%JRE_HOME%\lib\charsets.jar
set CP=%CP%;%JRE_HOME%\lib\ext\dnsns.jar
set CP=%CP%;%JRE_HOME%\lib\ext\localedata.jar
set CP=%CP%;%JRE_HOME%\lib\ext\sunjce_provider.jar
set CP=%CP%;%JRE_HOME%\lib\ext\sunmscapi.jar
set CP=%CP%;%JRE_HOME%\lib\ext\sunpkcs11.jar

rem ------------------------------------------------------------------

set IB_PACKAGE=
rem setEnvIBPackage
set IB_PACKAGE=%HOME_PATH%/lib/IB_Vela.2.130703.jar
echo "IB_PACKAGE = [%IB_PACKAGE%]"

set CP=%CP%;%IB_PACKAGE%

rem ------------------------------------------------------------------

set OPTION=-Xms128m -Xmx128m
set OPTION=%OPTION% -cp %CP%
set OPTION=%OPTION% -Ddev.author=����
set OPTION=%OPTION% -Ddev.version=1.6.0_45

rem ------------------------------------------------------------------

set OPTION=%OPTION% -Dsystem.ib.param.server=ib
set OPTION=%OPTION% -Dsystem.ib.param.basefolder=D:/KANG/WORK/workspace/IB_Vela.2/server/

rem
rem switchno
rem  *1. �����ܰ� �ۼ���
rem   2. ���� �ۼ���(����)�� loopcount��ŭ �ݺ�
rem   3. �����ܰ� �ۼ���. �Ϲ�����
rem   4.
rem  *5. ���� �ۼ���(�񵿱�).  loopcount ��ŭ �ݺ�
rem  *6. ���� �ۼ���(����).  loopcount�� ��ŭ �ݺ�
rem  *7. ��ƼIBridgeServant �� ��Ƽ�۽� ��Ƽ����
rem   8. ��Ƽ��� Thread �񵿱� �ۼ���
rem  10. ����� �������� �߻�
rem          ��ȭ����     HWI01 L01
rem          ���ѻ���     SHI01 L11
rem          �ϳ�HSBC���� HNI01    
rem          �ﱹ����     HKI01 L04
rem          ī��������   KFI01 L79
rem          �˸��������� ALI01 L02
rem          �Ｚ����     SSI01 L03
rem

set OPTION=%OPTION% -Dsystem.ib.param.switchno=10
set OPTION=%OPTION% -Dsystem.ib.param.fepid=SHI01
set OPTION=%OPTION% -Dsystem.ib.param.insid=L11


@echo on

%JAVA_EXE% %OPTION%  %MAIN_CLASS%
