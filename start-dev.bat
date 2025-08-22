@echo off
echo Starting IDMS Backend in development mode...

REM Build the application
echo Building application...
call mvn clean package -DskipTests

REM Start with default profile for HTTP
echo Starting server with HTTP configuration...
java -jar target/storemanagementbackend-0.0.1-SNAPSHOT.jar

pause