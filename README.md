# Spring Boot JMeter Test Application

Đây là một ứng dụng Spring Boot hoàn chỉnh được thiết kế đặc biệt để test với JMeter. Ứng dụng bao gồm tất cả các trường hợp kiểm thử cần thiết cho việc load testing, stress testing và performance testing.

## 🚀 Tính năng chính

### REST API Endpoints
- **User Management**: CRUD operations cho users
- **Product Management**: CRUD operations cho products  
- **Order Management**: CRUD operations cho orders
- **Authentication**: Login, register, logout, token validation
- **Test Endpoints**: Health check, load testing, memory monitoring

### Database
- **MySQL Database**: Production-ready database
- **JPA/Hibernate**: ORM mapping với MySQL dialect
- **Data Initialization**: Tự động tạo sample data
- **Indexes**: Optimized indexes cho performance

### Security
- **Spring Security**: Disabled cho performance testing
- **CORS**: Enabled cho cross-origin requests
- **No Authentication**: Focus vào performance testing

## 📋 Yêu cầu hệ thống

- Java 17+
- Maven 3.6+
- JMeter 5.6+
- IDE (IntelliJ IDEA, Eclipse, VS Code)

## 🛠️ Cài đặt và chạy

### 1. Clone repository
```bash
git clone <repository-url>
cd jmeter-test-app
```

### 2. Build project
```bash
mvn clean install
```

### 3. Chạy ứng dụng
```bash
mvn spring-boot:run
```

Ứng dụng sẽ chạy tại: `http://localhost:8080`

### 4. Database Setup
- **MySQL**: Đảm bảo MySQL server đang chạy
- **Database**: `jmeter` (sẽ được tạo tự động)
- **Username**: `root`
- **Password**: `viethoang123` (theo config trong application.yml)

## 📊 API Endpoints

### User APIs
```
GET    /api/users                    - Lấy danh sách users
GET    /api/users/{id}               - Lấy user theo ID
GET    /api/users/username/{username} - Lấy user theo username
GET    /api/users/email/{email}      - Lấy user theo email
POST   /api/users                    - Tạo user mới
PUT    /api/users/{id}               - Cập nhật user
DELETE /api/users/{id}               - Xóa user
GET    /api/users/active             - Lấy danh sách users đang hoạt động
GET    /api/users/role/{role}        - Lấy users theo role
GET    /api/users/search?name=...    - Tìm kiếm users theo tên
```

### Product APIs
```
GET    /api/products                 - Lấy danh sách products
GET    /api/products/{id}             - Lấy product theo ID
POST   /api/products                 - Tạo product mới
PUT    /api/products/{id}            - Cập nhật product
DELETE /api/products/{id}            - Xóa product
GET    /api/products/category/{category} - Lấy products theo category
GET    /api/products/active          - Lấy products đang hoạt động
GET    /api/products/search?searchTerm=... - Tìm kiếm products
GET    /api/products/in-stock        - Lấy products còn hàng
GET    /api/products/low-stock?threshold=... - Lấy products sắp hết hàng
```

### Order APIs
```
GET    /api/orders                   - Lấy danh sách orders
GET    /api/orders/{id}              - Lấy order theo ID
POST   /api/orders                   - Tạo order mới
PUT    /api/orders/{id}/status       - Cập nhật status order
DELETE /api/orders/{id}              - Xóa order
GET    /api/orders/user/{userId}     - Lấy orders theo user
GET    /api/orders/status/{status}   - Lấy orders theo status
```

### Authentication APIs (Simple - No JWT)
```
POST   /api/auth/login               - Đăng nhập (trả về user info)
POST   /api/auth/register            - Đăng ký user mới
POST   /api/auth/logout              - Đăng xuất
```

### Test APIs
```
GET    /api/test/health              - Health check
GET    /api/test/ping                - Ping test
GET    /api/test/delay/{seconds}     - Delay test
GET    /api/test/random-delay        - Random delay test
GET    /api/test/error/{statusCode}  - Generate error
GET    /api/test/random-error        - Random error
POST   /api/test/echo                - Echo POST
PUT    /api/test/echo                - Echo PUT
DELETE /api/test/echo                - Echo DELETE
GET    /api/test/load-test           - Load test
GET    /api/test/memory-test         - Memory test
```

## 🧪 JMeter Test Plans (5 Test Types)

### **01-Smoke-Test.jmx** ⚡ (Quick Sanity Check)
- **Users:** 1 | **Duration:** ~30s | **Use:** After deployment
- **Purpose:** Verify basic functionality works
- **Tests:** Health check, Get users/products, Create product, Login

### **02-Load-Test.jmx** 📊 (Normal Load)
- **Users:** 10 | **Ramp-up:** 30s | **Loops:** 10 | **Use:** Daily baseline
- **Purpose:** Test with expected normal load
- **Tests:** Health check, CRUD operations with think time

### **03-Spike-Test.jmx** 🚀 (Sudden Load Spike)
- **Users:** 100 | **Ramp-up:** 10s | **Duration:** 60s | **Use:** Flash sales
- **Purpose:** Simulate sudden traffic surge
- **Tests:** Random endpoints with uniform timer

### **04-Stress-Test.jmx** 💪 (Find Breaking Point)
- **Users:** 50 | **Ramp-up:** 60s | **Duration:** 5 min | **Use:** Capacity planning
- **Purpose:** Find system limits and bottlenecks
- **Tests:** Continuous load with random endpoints

### **05-Functional-API-Test.jmx** ✅ (CRUD Testing)
- **Users:** 3x2 Thread Groups | **Loops:** 3 | **Use:** CI/CD automation
- **Purpose:** Validate API correctness
- **Tests:** Full CRUD operations with data correlation

### **📖 Chi tiết:** 
- **Quick Start:** [QUICK-START.md](QUICK-START.md) - Bắt đầu nhanh trong 5 phút
- **Test Details:** [jmeter-tests/README-TESTS.md](jmeter-tests/README-TESTS.md) - Chi tiết từng test
- **MySQL Setup:** [MYSQL_SETUP.md](MYSQL_SETUP.md) - Cấu hình database

## 🚀 Chạy JMeter Tests

### 1. Mở JMeter
```bash
# Windows
jmeter.bat

# Linux/Mac
jmeter.sh
```

### 2. Load test plan
- File → Open → Chọn file `.jmx` trong thư mục `jmeter-tests/`

### 3. Chạy test
- Click nút "Start" (▶️)
- Xem kết quả trong "Summary Report" và "Graph Results"

### 4. Chạy từ command line (Non-GUI Mode - Recommended)
```bash
# Smoke Test (Quick check)
jmeter -n -t jmeter-tests/01-Smoke-Test.jmx -l results/smoke.jtl

# Load Test (Normal load)
jmeter -n -t jmeter-tests/02-Load-Test.jmx -l results/load.jtl

# Spike Test (Flash sale)
jmeter -n -t jmeter-tests/03-Spike-Test.jmx -l results/spike.jtl

# Stress Test (Breaking point)
jmeter -n -t jmeter-tests/04-Stress-Test.jmx -l results/stress.jtl

# Functional Test (CRUD)
jmeter -n -t jmeter-tests/05-Functional-API-Test.jmx -l results/functional.jtl

# Generate HTML Report
jmeter -g results/load.jtl -o reports/load-test-report/
```

## 📈 Monitoring và Metrics

### Actuator Endpoints
```
GET /actuator/health          - Health status
GET /actuator/info            - Application info
GET /actuator/metrics         - Application metrics
GET /actuator/prometheus      - Prometheus metrics
```

### Sample Data
Ứng dụng tự động tạo sample data:
- **5 Users**: admin, user1, user2, user3, testuser
- **10 Products**: Laptop, Smartphone, Headphones, etc.
- **Roles**: ADMIN, USER, MODERATOR

## 🔧 Configuration

### Application Properties
```yaml
server:
  port: 8080
  servlet:
    context-path: /api

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/jmeter
    username: root
    password: viethoang123
    driver-class-name: com.mysql.cj.jdbc.Driver
  
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQL8Dialect

logging:
  level:
    com.example: DEBUG
```

### JMeter Configuration
- **Base URL**: `http://localhost:8080/api`
- **Thread Groups**: Có thể điều chỉnh số threads và loops
- **Timers**: Random timers để simulate real user behavior
- **Assertions**: Response code và content validation

## 🐛 Troubleshooting

### Common Issues

1. **Port 8080 đã được sử dụng**
   ```bash
   # Thay đổi port trong application.yml
   server:
     port: 8081
   ```

2. **JMeter không kết nối được**
   - Kiểm tra ứng dụng đã chạy chưa
   - Kiểm tra URL trong test plan
   - Kiểm tra firewall settings

3. **Database connection issues**
   - Kiểm tra MySQL server đang chạy
   - Kiểm tra database `jmeter` đã được tạo
   - Kiểm tra username/password trong application.yml
   - Kiểm tra logs để xem lỗi

### Performance Tips

1. **Tăng JVM heap size**
   ```bash
   export JAVA_OPTS="-Xmx2g -Xms1g"
   mvn spring-boot:run
   ```

2. **Database optimization**
   - Sử dụng connection pooling
   - Tối ưu hóa queries
   - Index optimization

3. **JMeter optimization**
   - Sử dụng CSV Data Set Config cho test data
   - Tối ưu hóa timers
   - Sử dụng non-GUI mode cho production tests

## 📝 Test Scenarios

### 1. Load Testing
- **Scenario**: Normal load với expected user count
- **Duration**: 10-30 phút
- **Metrics**: Response time, throughput, error rate

### 2. Stress Testing
- **Scenario**: Tăng load đến breaking point
- **Duration**: 5-15 phút
- **Metrics**: System behavior under stress

### 3. Spike Testing
- **Scenario**: Sudden load spikes
- **Duration**: Short bursts
- **Metrics**: System recovery time

### 4. Volume Testing
- **Scenario**: Large amount of data
- **Duration**: Extended period
- **Metrics**: Memory usage, database performance

## 🤝 Contributing

1. Fork repository
2. Tạo feature branch
3. Commit changes
4. Push to branch
5. Tạo Pull Request

## 📄 License

MIT License - xem file LICENSE để biết thêm chi tiết.

## 📞 Support

Nếu có vấn đề gì, vui lòng tạo issue trên GitHub hoặc liên hệ qua email.

---

**Happy Testing! 🚀**