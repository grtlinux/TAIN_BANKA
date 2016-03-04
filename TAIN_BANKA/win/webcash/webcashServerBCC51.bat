@echo off
rem ------------------------------------------------------------------

set MAIN_CLASS=ic.vela.ibridge.server.webcash.server.WebCashBatchServerMain

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
set OPTION=%OPTION% -Ddev.author=°­¼®
set OPTION=%OPTION% -Ddev.version=1.6.0_45

rem ------------------------------------------------------------------

set OPTION=%OPTION% -Dsystem.ib.param.fepid=BCC51
set OPTION=%OPTION% -Dsystem.ib.param.basefolder=K:/WORK/workspace/IB_Vela.2/server/

@echo on

%JAVA_EXE% %OPTION%  %MAIN_CLASS%
