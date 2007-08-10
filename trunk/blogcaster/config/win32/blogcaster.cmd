@echo off
:: $RCSfile$    $Revision$  $Date$ - $Author$
:: 
:: Copyright (c) 2005 - Michael King, Kevin McAllister
:: 
:: This library is free software; you can redistribute it and/or modify
:: it under the terms of the GNU Lesser General Public License as
:: published by the Free Software Foundation; either version 2.1 of the
:: License, or (at your option) any later version. See the file
:: LICENSE.txt included with this library for more information.

:: This file is a simple batch script used to launch Blogcaster on windows
:: Systems.  If you already have a java environment set up it will
:: attempt to use it by searching the registry for where it is.
:: based on example batch scripts from 
:: http://www.ericphelps.com/batch/samples/samples.htm

:: Blogcaster probably will only work with Java 1.4 or better

::First test to see if we are on NT or similar OS by seeing 
::if the ampersand is interpreted as a command separator
> reg1.txt echo 1234&rem
type reg1.txt | find "rem"
if not errorlevel 1 goto WIN9X

::Find the current (most recent) Java version
start /w regedit /e reg1.txt "HKEY_LOCAL_MACHINE\SOFTWARE\JavaSoft\Java Runtime Environment"
type reg1.txt | find "CurrentVersion" > reg2.txt
if errorlevel 1 goto ERROR
for /f "tokens=2 delims==" %%x in (reg2.txt) do set JavaTemp=%%~x
if errorlevel 1 goto ERROR
del reg1.txt
del reg2.txt

::Get the home directory of the most recent Java
start /w regedit /e reg1.txt "HKEY_LOCAL_MACHINE\SOFTWARE\JavaSoft\Java Runtime Environment\%JavaTemp%"
type reg1.txt | find "JavaHome" > reg2.txt
if errorlevel 1 goto ERROR
for /f "tokens=2 delims==" %%x in (reg2.txt) do set JavaTemp=%%~x
if errorlevel 1 goto ERROR
del reg1.txt
del reg2.txt

::Convert double backslashes to single backslashes
set JavaHome=
:WHILE
  if "%JavaTemp%"=="" goto WEND
  if not "%JavaHome%"=="" set JavaHome=%JavaHome%\
  for /f "delims=\" %%x in ("%JavaTemp%") do set JavaHome=%JavaHome%%%x
  for /f "tokens=1,* delims=\" %%x in ("%JavaTemp%") do set JavaTemp=%%y
  goto WHILE
:WEND
set JavaTemp=

::Convert long path (with spaces) into a short path
for %%x in ("%JavaHome%") do set JavaHome=%%~fsx

::Test the java path to see if there really is a javaw.exe
if not exist %JavaHome%\bin\javaw.exe goto ERROR
::Get the current batch file's short path
for %%x in (%0) do set BatchPath=%%~fsx
for %%x in (%BatchPath%) do set BatchPath=%%~dpsx
if not exist %BatchPath%Blogcaster.jar goto ERROR
if not exist %BatchPath%native goto ERROR
:: Figure out how to let this go in the background and get rid of the cmd window
start /MIN /B %JavaHome%\bin\javaw.exe -Dblogcaster.settingspath=%BatchPath% -Djava.library.path=%BatchPath%native -jar %BatchPath%Blogcaster.jar
goto DONE

:WIN9X
echo It seems you are running an old windows
echo blogcaster will probably still work, but you should
echo will need to set JavaHome in this cmd file or add the java.exe
echo  to your path and uncomment the relevant code under the WIN9X label
:: set JavaHome="C:\PROGRA~1\JAVA\JRE15~1.0_0"
:: %JavaHome%\bin\java.exe -Djava.library.path=%BatchPath% -jar %BatchPath%\Blogcaster.jar

exit

:ERROR
echo Something failed, and I am pretty bad at windows scripting.
echo If you can figure it out or give me good feedback, let me know.
echo blogcaster@webcodefocus.com

exit

:DONE
@echo off
cls
exit