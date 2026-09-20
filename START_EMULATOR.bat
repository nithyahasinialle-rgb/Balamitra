@echo off
title BALAMITRA Android Emulator
echo ========================================================
echo   Starting BALAMITRA Android Phone Window...
echo ========================================================
cd /d "%LOCALAPPDATA%\Android\Sdk\emulator"
emulator.exe -avd Medium_Phone_API_35 -no-snapshot-load
pause
