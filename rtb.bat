@echo off

rem #####################################################################
rem
rem Script that starts the launching pad for RTB Software Suites.
rem
rem     Script name:  rtb.bat
rem     Purpose:      This shell script starts a launcher
rem     Arguments:    None
rem
rem Copyright (C) 2021-2022 RTB
rem All Rights Reserved.
rem
rem #####################################################################

SET LAUNCH_HOME=%~dp0
cd %LAUNCH_HOME% 
rem echo %LAUNCH_HOME% 

SET RTB=%LAUNCH_HOME%jars\rtb.jar
rem echo "%RTB%"

SET CLASSPATH=%RTB%;%LAUNCH_HOME%jars\jtk.jar;%LAUNCH_HOME%jars\commons-io-2.4.jar;%LAUNCH_HOME%jars\NhashCLT.jar;^
%LAUNCH_HOME%jars\jackson2.11.3\jackson-core-2.11.3.jar;%LAUNCH_HOME%jars\jackson2.11.3\jackson-databind-2.11.3.jar;%LAUNCH_HOME%jars\jackson2.11.3\jackson-annotations-2.11.3.jar

SET LIBRARYPATH=%LAUNCH_HOME%lib

SET RESOURCESPATH=%LAUNCH_HOME%resources
SET HISTORYPATH=%LAUNCH_HOME%history

rem echo "%CLASSPATH%"


IF /I %0 EQU "%~dpnx0" (
	%LAUNCH_HOME%jre\bin\java -d64 -Xms512m -Xmx4096m -showversion -Drtb.dir=%LAUNCH_HOME% ^
	-Djava.library.path=%LIBRARYPATH% -Djava.resources.path=%RESOURCESPATH% -Djava.history.path=%HISTORYPATH% ^
	-cp %CLASSPATH% edu.uth.app.launcher.LauncherApp
	PAUSE
) 
IF "%1" == "1" (
	SET /A MODE=%1
	SET INPUTFILE=%~f2
	echo I am here %MODE% %INPUTFILE%

    %LAUNCH_HOME%jre\bin\java -d64 -Xms512m -Xmx4096m -showversion -Drtb.dir=%LAUNCH_HOME% ^
	-Djava.library.path=%LIBRARYPATH% -Djava.resources.path=%RESOURCESPATH% -Djava.history.path=%HISTORYPATH% ^
	-cp %CLASSPATH% edu.uth.app.qac.QacApp %MODE% %INPUTFILE%
	
	PAUSE	
)



