@echo off
echo ========================================
echo Spring Boot JMeter Test Application
echo ========================================
echo.

echo Starting Spring Boot application...
start "Spring Boot App" cmd /k "mvn spring-boot:run"

echo.
echo Waiting for application to start...
timeout /t 10 /nobreak > nul

echo.
echo Application should be running at: http://localhost:8080
echo H2 Console: http://localhost:8080/h2-console
echo.

echo ========================================
echo JMeter Test Plans Available:
echo ========================================
echo 1. Basic Load Test (jmeter-tests/Basic-Load-Test.jmx)
echo 2. Stress Test (jmeter-tests/Stress-Test.jmx)
echo 3. API Test Suite (jmeter-tests/API-Test-Suite.jmx)
echo.

echo To run JMeter tests:
echo 1. Open JMeter GUI
echo 2. File -> Open -> Select .jmx file
echo 3. Click Start button
echo.

echo Or run from command line:
echo jmeter -n -t jmeter-tests/Basic-Load-Test.jmx -l results.jtl
echo.

pause
