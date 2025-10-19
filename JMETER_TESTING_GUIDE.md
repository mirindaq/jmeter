# Hướng dẫn Testing với JMeter

## 📋 Mục lục
1. [Cài đặt JMeter](#cài-đặt-jmeter)
2. [Chuẩn bị Test Environment](#chuẩn-bị-test-environment)
3. [Chạy Test Plans](#chạy-test-plans)
4. [Phân tích kết quả](#phân-tích-kết-quả)
5. [Best Practices](#best-practices)
6. [Troubleshooting](#troubleshooting)

## 🛠️ Cài đặt JMeter

### 1. Download JMeter
```bash
# Download từ Apache JMeter website
wget https://dlcdn.apache.org//jmeter/binaries/apache-jmeter-5.6.2.tgz
tar -xzf apache-jmeter-5.6.2.tgz
cd apache-jmeter-5.6.2
```

### 2. Chạy JMeter
```bash
# Windows
bin/jmeter.bat

# Linux/Mac
bin/jmeter.sh
```

### 3. Cài đặt Plugins (Optional)
- Download JMeter Plugins Manager
- Cài đặt các plugins: Custom Thread Groups, Response Times Over Time, etc.

## 🚀 Chuẩn bị Test Environment

### 1. Khởi động Spring Boot Application
```bash
# Terminal 1
mvn spring-boot:run
```

### 2. Kiểm tra Application
```bash
# Health check
curl http://localhost:8080/api/test/health

# Get users
curl http://localhost:8080/api/users

# Get products
curl http://localhost:8080/api/products
```

### 3. Chuẩn bị Test Data
- Ứng dụng tự động tạo sample data
- Có thể thêm data qua H2 Console: `http://localhost:8080/h2-console`

## 🧪 Chạy Test Plans

### 1. Basic Load Test

#### Mở Test Plan
1. Mở JMeter GUI
2. File → Open → Chọn `jmeter-tests/Basic-Load-Test.jmx`
3. Kiểm tra Thread Group settings:
   - Number of Threads: 10
   - Ramp-up Period: 30 seconds
   - Loop Count: 10

#### Chạy Test
1. Click nút "Start" (▶️)
2. Xem kết quả trong "Summary Report"
3. Dừng test khi hoàn thành

#### Kết quả mong đợi
- **Response Time**: < 1000ms
- **Throughput**: > 10 requests/second
- **Error Rate**: < 1%

### 2. Stress Test

#### Mở Test Plan
1. File → Open → Chọn `jmeter-tests/Stress-Test.jmx`
2. Kiểm tra Thread Group settings:
   - Number of Threads: 50
   - Ramp-up Period: 60 seconds
   - Duration: 300 seconds (5 phút)

#### Chạy Test
1. Click "Start" và để chạy trong 5 phút
2. Monitor system resources
3. Xem kết quả trong "Graph Results"

#### Kết quả mong đợi
- **Response Time**: Có thể tăng dần
- **Throughput**: Peak performance
- **Error Rate**: Có thể tăng khi đạt limit

### 3. API Test Suite

#### Mở Test Plan
1. File → Open → Chọn `jmeter-tests/API-Test-Suite.jmx`
2. Kiểm tra 2 Thread Groups:
   - User API Tests: 5 threads, 5 loops
   - Product API Tests: 3 threads, 3 loops

#### Chạy Test
1. Click "Start"
2. Xem kết quả cho từng Thread Group
3. Kiểm tra data correlation

## 📊 Phân tích kết quả

### 1. Summary Report
- **Samples**: Tổng số requests
- **Average**: Thời gian response trung bình
- **Min/Max**: Thời gian response min/max
- **Error %**: Tỷ lệ lỗi
- **Throughput**: Requests per second

### 2. Graph Results
- **Response Time Over Time**: Biểu đồ thời gian response
- **Throughput Over Time**: Biểu đồ throughput
- **Error Rate Over Time**: Biểu đồ tỷ lệ lỗi

### 3. Key Metrics

#### Response Time
- **Excellent**: < 100ms
- **Good**: 100-500ms
- **Acceptable**: 500-1000ms
- **Poor**: > 1000ms

#### Throughput
- **Target**: > 100 requests/second
- **Good**: 50-100 requests/second
- **Poor**: < 50 requests/second

#### Error Rate
- **Excellent**: 0%
- **Good**: < 1%
- **Acceptable**: 1-5%
- **Poor**: > 5%

## 🎯 Best Practices

### 1. Test Planning
- **Start Small**: Bắt đầu với ít threads
- **Gradual Increase**: Tăng dần load
- **Monitor Resources**: Theo dõi CPU, Memory, Database
- **Set Realistic Goals**: Đặt mục tiêu thực tế

### 2. Test Data Management
- **Use CSV Data**: Sử dụng CSV Data Set Config
- **Unique Data**: Đảm bảo data unique
- **Clean Up**: Xóa test data sau test

### 3. Test Execution
- **Non-GUI Mode**: Sử dụng command line cho production
- **Remote Testing**: Sử dụng distributed testing
- **Resource Monitoring**: Monitor system resources

### 4. Result Analysis
- **Baseline**: Tạo baseline performance
- **Trend Analysis**: Phân tích xu hướng
- **Bottleneck Identification**: Xác định bottleneck
- **Optimization**: Tối ưu hóa dựa trên kết quả

## 🔧 Advanced Configuration

### 1. Custom Thread Groups
```xml
<ThreadGroup>
  <stringProp name="ThreadGroup.num_threads">100</stringProp>
  <stringProp name="ThreadGroup.ramp_time">60</stringProp>
  <boolProp name="ThreadGroup.scheduler">true</boolProp>
  <stringProp name="ThreadGroup.duration">300</stringProp>
</ThreadGroup>
```

### 2. Data Correlation
```xml
<JSONPostProcessor>
  <stringProp name="JSONPostProcessor.referenceNames">user_id</stringProp>
  <stringProp name="JSONPostProcessor.jsonPathExprs">$.id</stringProp>
</JSONPostProcessor>
```

### 3. Assertions
```xml
<ResponseAssertion>
  <stringProp name="Assertion.test_field">Assertion.response_code</stringProp>
  <stringProp name="Assertion.test_type">1</stringProp>
  <collectionProp name="Asserion.test_strings">
    <stringProp>200</stringProp>
  </collectionProp>
</ResponseAssertion>
```

## 🐛 Troubleshooting

### 1. Common Issues

#### Connection Refused
```
Error: java.net.ConnectException: Connection refused
```
**Solution**: Kiểm tra Spring Boot app đã chạy chưa

#### Out of Memory
```
Error: java.lang.OutOfMemoryError: Java heap space
```
**Solution**: Tăng heap size
```bash
export HEAP="-Xms1g -Xmx2g -XX:MaxMetaspaceSize=256m"
jmeter $HEAP -n -t test.jmx
```

#### Slow Response Times
**Possible Causes**:
- Database connection issues
- Insufficient resources
- Network latency
- Application bottlenecks

**Solutions**:
- Check database connections
- Monitor system resources
- Optimize queries
- Scale application

### 2. Performance Tuning

#### JMeter Tuning
```bash
# Increase heap size
export HEAP="-Xms2g -Xmx4g"

# Use non-GUI mode
jmeter -n -t test.jmx -l results.jtl

# Use distributed testing
jmeter -n -t test.jmx -r -l results.jtl
```

#### Spring Boot Tuning
```yaml
server:
  tomcat:
    threads:
      max: 200
      min-spare: 10
    max-connections: 8192
    accept-count: 100
```

## 📈 Monitoring và Alerting

### 1. System Monitoring
- **CPU Usage**: < 80%
- **Memory Usage**: < 80%
- **Disk I/O**: Monitor disk usage
- **Network I/O**: Monitor network traffic

### 2. Application Monitoring
- **Response Time**: Monitor average response time
- **Error Rate**: Monitor error percentage
- **Throughput**: Monitor requests per second
- **Database Connections**: Monitor connection pool

### 3. JMeter Monitoring
- **Active Threads**: Monitor active threads
- **Response Time**: Monitor response time trends
- **Error Rate**: Monitor error rate trends
- **Throughput**: Monitor throughput trends

## 🎯 Test Scenarios

### 1. Load Testing Scenarios
- **Normal Load**: Expected user count
- **Peak Load**: Maximum expected load
- **Sustained Load**: Long duration testing

### 2. Stress Testing Scenarios
- **Breaking Point**: Find system limits
- **Recovery Testing**: Test system recovery
- **Spike Testing**: Sudden load increases

### 3. Performance Testing Scenarios
- **Response Time**: Measure response times
- **Throughput**: Measure throughput
- **Resource Usage**: Monitor resource consumption
- **Scalability**: Test system scalability

## 📝 Reporting

### 1. Test Reports
- **Summary Report**: Basic statistics
- **Graph Results**: Visual representation
- **HTML Report**: Detailed HTML report
- **Custom Reports**: Custom report generation

### 2. Report Generation
```bash
# Generate HTML report
jmeter -g results.jtl -o report/

# Generate custom report
jmeter -g results.jtl -f -o report/
```

### 3. Report Analysis
- **Trend Analysis**: Analyze performance trends
- **Comparison**: Compare different test runs
- **Recommendations**: Provide optimization recommendations
- **Documentation**: Document findings and recommendations

---

**Happy Testing! 🚀**
