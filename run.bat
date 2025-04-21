@echo off
echo Compiling Java files...
javac -cp "lib/*" -d out src/*.java

if %ERRORLEVEL% NEQ 0 (
    echo Compilation failed!
    pause
    exit /b %ERRORLEVEL%
)

echo Running Quiz Management System...
java -cp "out;lib/*" Welcome_page

pause 