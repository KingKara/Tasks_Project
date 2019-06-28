call runcrud.bat
if "%ERRORLEVEL%" == "0" goto runbrowser
echo Cannot execute runcrud.bat file
goto fail

:runbrowser
timeout 30
start chrome http://localhost:8080/crud/v1/task/getTasks
goto end

:fail
echo.
echo There were errors

:end
echo.
echo Work in showtasks is finished.