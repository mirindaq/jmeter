#!/bin/bash

echo "========================================"
echo "Spring Boot JMeter Test Application"
echo "========================================"
echo

echo "Starting Spring Boot application..."
mvn spring-boot:run &
SPRING_PID=$!

echo
echo "Waiting for application to start..."
sleep 10

echo
echo "Application should be running at: http://localhost:8080"
echo "H2 Console: http://localhost:8080/h2-console"
echo

echo "========================================"
echo "JMeter Test Plans Available:"
echo "========================================"
echo "1. Basic Load Test (jmeter-tests/Basic-Load-Test.jmx)"
echo "2. Stress Test (jmeter-tests/Stress-Test.jmx)"
echo "3. API Test Suite (jmeter-tests/API-Test-Suite.jmx)"
echo

echo "To run JMeter tests:"
echo "1. Open JMeter GUI: jmeter.sh"
echo "2. File -> Open -> Select .jmx file"
echo "3. Click Start button"
echo

echo "Or run from command line:"
echo "jmeter -n -t jmeter-tests/Basic-Load-Test.jmx -l results.jtl"
echo

echo "Press Ctrl+C to stop the application"
echo

# Function to cleanup on exit
cleanup() {
    echo
    echo "Stopping Spring Boot application..."
    kill $SPRING_PID 2>/dev/null
    exit 0
}

# Set trap to cleanup on script exit
trap cleanup SIGINT SIGTERM

# Wait for user to press Ctrl+C
wait
