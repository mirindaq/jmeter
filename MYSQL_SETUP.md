# Hướng dẫn Setup MySQL cho JMeter Test Application

## 📋 Yêu cầu
- MySQL Server 8.0+
- MySQL Workbench (optional)
- Java 17+
- Maven 3.6+

## 🚀 Cài đặt MySQL

### 1. Download và cài đặt MySQL
```bash
# Windows - Download từ MySQL website
# https://dev.mysql.com/downloads/mysql/

# Linux (Ubuntu/Debian)
sudo apt update
sudo apt install mysql-server

# macOS với Homebrew
brew install mysql
```

### 2. Khởi động MySQL
```bash
# Windows
net start mysql

# Linux
sudo systemctl start mysql
sudo systemctl enable mysql

# macOS
brew services start mysql
```

### 3. Cấu hình MySQL
```bash
# Đăng nhập MySQL
mysql -u root -p

# Tạo database
CREATE DATABASE jmeter CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

# Tạo user (optional)
CREATE USER 'jmeter_user'@'localhost' IDENTIFIED BY 'jmeter_password';
GRANT ALL PRIVILEGES ON jmeter.* TO 'jmeter_user'@'localhost';
FLUSH PRIVILEGES;
```

## 🔧 Cấu hình Application

### 1. Kiểm tra application.yml
```yaml
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
```

### 2. Kiểm tra pom.xml
```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>
```

## 🗄️ Database Schema

### Tables sẽ được tạo tự động:
- **users**: Thông tin người dùng
- **products**: Thông tin sản phẩm
- **orders**: Thông tin đơn hàng
- **order_items**: Chi tiết items trong đơn hàng

### Indexes được tạo tự động:
- **users**: username, email, is_active
- **products**: category, is_active, status, sku
- **orders**: user_id, status, order_number, created_at
- **order_items**: order_id, product_id

## 🚀 Chạy Application

### 1. Build project
```bash
mvn clean install
```

### 2. Chạy application
```bash
mvn spring-boot:run
```

### 3. Kiểm tra kết nối
- Application sẽ tự động tạo tables
- Sample data sẽ được insert tự động
- Kiểm tra logs để xem quá trình tạo database

## 🔍 Troubleshooting

### 1. Connection Refused
```
Error: java.net.ConnectException: Connection refused
```
**Solutions:**
- Kiểm tra MySQL server đang chạy
- Kiểm tra port 3306 có bị block không
- Kiểm tra firewall settings

### 2. Access Denied
```
Error: Access denied for user 'root'@'localhost'
```
**Solutions:**
- Kiểm tra password trong application.yml
- Reset MySQL password nếu cần
- Kiểm tra user permissions

### 3. Database Not Found
```
Error: Unknown database 'jmeter'
```
**Solutions:**
- Tạo database manually: `CREATE DATABASE jmeter;`
- Kiểm tra database name trong application.yml
- Kiểm tra user có quyền tạo database

### 4. Table Creation Issues
```
Error: Table doesn't exist
```
**Solutions:**
- Kiểm tra `ddl-auto: update` trong application.yml
- Kiểm tra Hibernate dialect
- Kiểm tra logs để xem lỗi chi tiết

## 📊 Monitoring

### 1. MySQL Performance
```sql
-- Kiểm tra connections
SHOW PROCESSLIST;

-- Kiểm tra database size
SELECT 
    table_schema AS 'Database',
    ROUND(SUM(data_length + index_length) / 1024 / 1024, 2) AS 'Size (MB)'
FROM information_schema.tables 
WHERE table_schema = 'jmeter'
GROUP BY table_schema;

-- Kiểm tra table sizes
SELECT 
    table_name AS 'Table',
    ROUND(((data_length + index_length) / 1024 / 1024), 2) AS 'Size (MB)'
FROM information_schema.tables 
WHERE table_schema = 'jmeter'
ORDER BY (data_length + index_length) DESC;
```

### 2. Application Monitoring
- **Actuator endpoints**: `/actuator/health`
- **Database metrics**: `/actuator/metrics`
- **Logs**: Kiểm tra application logs

## 🎯 Performance Optimization

### 1. MySQL Configuration
```ini
# my.cnf
[mysqld]
innodb_buffer_pool_size = 1G
innodb_log_file_size = 256M
max_connections = 200
query_cache_size = 64M
```

### 2. Connection Pool
```yaml
spring:
  datasource:
    hikari:
      maximum-pool-size: 20
      minimum-idle: 5
      connection-timeout: 30000
      idle-timeout: 600000
      max-lifetime: 1800000
```

### 3. JPA Optimization
```yaml
spring:
  jpa:
    properties:
      hibernate:
        jdbc:
          batch_size: 20
        order_inserts: true
        order_updates: true
        batch_versioned_data: true
```

## 📝 Best Practices

### 1. Database Design
- Sử dụng indexes cho các columns thường query
- Sử dụng appropriate data types
- Sử dụng foreign key constraints
- Regular backup database

### 2. Application Design
- Sử dụng connection pooling
- Optimize queries
- Sử dụng pagination cho large datasets
- Monitor database performance

### 3. Security
- Sử dụng strong passwords
- Limit database user permissions
- Enable SSL connections
- Regular security updates

---

**Happy Testing! 🚀**
