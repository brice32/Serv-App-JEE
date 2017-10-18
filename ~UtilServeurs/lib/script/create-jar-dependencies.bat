@ECHO OFF
CHCP 1252 >NUL
PROMPT $G
CD /D "%~dp0"

SET JAVA_HOME=C:\dev\java\jdk\jdk8-x86
SET ANT_HOME=C:\dev\java\utils\ant

SET FULLPATH_FILE_BUILD=%~dp0\build-%~n0.xml
CALL "%ANT_HOME%\bin\ant.bat" -f "%FULLPATH_FILE_BUILD%"

PAUSE
