@echo off
rem ------------------------------------------------------------------

set MAIN_CLASS=ic.vela.ibridge.server.ib.servant.ServantTestMain

rem ------------------------------------------------------------------

set HOME_PATH=K:\WORK\workspace\IB_Vela.2

set JAVA_HOME=K:\PROG\jdk1.6.0_45
set JRE_HOME=%JAVA_HOME%

rem set JAVA_EXE=%JAVA_HOME%\bin\javaw.exe
set JAVA_EXE=%JAVA_HOME%\bin\java.exe

set MAIN_PATH=%HOME_PATH%\bin

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
set IB_PACKAGE=%HOME_PATH%/lib/IB_Vela.2.151010.jar
echo "IB_PACKAGE = [%IB_PACKAGE%]"

set CP=%CP%;%IB_PACKAGE%

rem ------------------------------------------------------------------

set OPTION=-Xms128m -Xmx128m
set OPTION=%OPTION% -cp %CP%
set OPTION=%OPTION% -Ddev.author=강석
set OPTION=%OPTION% -Ddev.version=1.6.0_45

rem ------------------------------------------------------------------

set OPTION=%OPTION% -Dsystem.ib.param.server=ib
set OPTION=%OPTION% -Dsystem.ib.param.basefolder=%HOME_PATH%/server/

rem
rem switchno
rem  *1. 전문단건 송수신
rem   2. 전문 송수신(동기)을 loopcount만큼 반복
rem   3. 전문단건 송수신. 일반전문
rem   4. 
rem  *5. 전문 송수신(비동기).  loopcount 만큼 반복
rem  *6. 전문 송수신(동기).  loopcount건 만큼 반복
rem  *7. 멀티IBridgeServant 별 멀티송신 멀티수신
rem   8. 멀티기관 Thread 비동기 송수신
rem

set OPTION=%OPTION% -Dsystem.ib.param.switchno=1
set OPTION=%OPTION% -Dsystem.ib.param.waittime=1
set OPTION=%OPTION% -Dsystem.ib.param.loopcount=20

@echo on

%JAVA_EXE% %OPTION%  %MAIN_CLASS%
