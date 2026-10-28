@echo off
cd /d "%~dp0"
echo Pushing updates to GitHub repository...
"%USERPROFILE%\.mingit\cmd\git.exe" push origin main
pause
