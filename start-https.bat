@echo off
echo Starting IDMS Backend with HTTPS support...

REM Build the application
echo Building application...
call mvn clean package -DskipTests

REM Start with production profile for HTTPS
echo Starting server with HTTPS configuration...
java -jar target/storemanagementbackend-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod

pause