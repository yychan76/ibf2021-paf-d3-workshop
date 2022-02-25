@echo off
echo Creating Angular Project with Material and Flex Layout
set /p PROJECT="Create Angular Project Name: "

choice /c YN /m "Add Material "
if errorlevel 2 (set material=false)
if errorlevel 1 (set material=true)

choice /c YN /m "Add Flex Layout "
if errorlevel 2 (set flex=false)
if errorlevel 1 (set flex=true)

echo material=%material% flex=%flex%
pause
call ng new %PROJECT%
cd "%PROJECT%"
if %material%==true (
    echo Adding Angular Material
    call ng add @angular/material
)
if %flex%==true (
    echo Adding Angular Flex Layout
    call npm i -s @angular/flex-layout @angular/cdk
)
pause
EXIT /B %ERRORLEVEL%
