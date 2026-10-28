@echo off
setlocal
cd /d "%~dp0"
echo Starting Vayal - Smart Fertilizer Advisor...

if not defined JAVA_HOME (
    if exist "%USERPROFILE%\.jdks\jdk-17.0.12+7" (
        set "JAVA_HOME=%USERPROFILE%\.jdks\jdk-17.0.12+7"
    )
)

if defined JAVA_HOME (
    set "PATH=%JAVA_HOME%\bin;%PATH%"
)

set "MVN_CMD=mvn"
if exist "%USERPROFILE%\.maven\apache-maven-3.9.6\bin\mvn.cmd" (
    set "MVN_CMD=%USERPROFILE%\.maven\apache-maven-3.9.6\bin\mvn.cmd"
)

echo Using Java: %JAVA_HOME%
"%MVN_CMD%" spring-boot:run
endlocal
