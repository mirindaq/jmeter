# 🚀 Quick Start Guide

## ⚡ 3 Bước để bắt đầu testing

### **Bước 1: Khởi động ứng dụng**
```bash
mvn spring-boot:run
```
Chờ application start → `http://localhost:8080`

---

### **Bước 2: Chạy Smoke Test (30 giây)**
```bash
jmeter -n -t jmeter-tests/01-Smoke-Test.jmx -l results/smoke.jtl
```

✅ **Kết quả mong đợi:**
```
summary = 5 in 00:00:05 = 1.0/s Avg: 250 Min: 50 Max: 500 Err: 0 (0.00%)
```

---

### **Bước 3: Chạy Load Test (5 phút)**
```bash
jmeter -n -t jmeter-tests/02-Load-Test.jmx -l results/load.jtl
```

✅ **Kết quả mong đợi:**
```
summary = 500 in 00:05:00 = 1.7/s Avg: 500 Min: 100 Max: 2000 Err: 0 (0.00%)
```

---

## 📊 Các Test khác

### **🚀 Spike Test** (Flash Sale Simulation)
```bash
jmeter -n -t jmeter-tests/03-Spike-Test.jmx -l results/spike.jtl
```
**Mục đích:** Test hệ thống với 100 users đột ngột trong 10 giây

---

### **💪 Stress Test** (Find Breaking Point)
```bash
jmeter -n -t jmeter-tests/04-Stress-Test.jmx -l results/stress.jtl
```
**Mục đích:** Tìm giới hạn của hệ thống với 50 users trong 5 phút

---

### **✅ Functional Test** (API Testing)
```bash
jmeter -n -t jmeter-tests/05-Functional-API-Test.jmx -l results/functional.jtl
```
**Mục đích:** Test CRUD operations với data correlation

---

## 📈 Xem kết quả

### **Tạo HTML Report:**
```bash
jmeter -g results/load.jtl -o reports/load-test/
```

Mở file: `reports/load-test/index.html` trong browser

---

## 🎯 Recommended Testing Sequence

### **Development:**
```bash
# 1. Quick check after code changes
jmeter -n -t jmeter-tests/01-Smoke-Test.jmx -l results/smoke.jtl

# 2. If smoke test passes, run functional tests
jmeter -n -t jmeter-tests/05-Functional-API-Test.jmx -l results/functional.jtl
```

### **Before Deployment:**
```bash
# 1. Smoke Test
jmeter -n -t jmeter-tests/01-Smoke-Test.jmx -l results/smoke.jtl

# 2. Load Test
jmeter -n -t jmeter-tests/02-Load-Test.jmx -l results/load.jtl

# 3. Spike Test
jmeter -n -t jmeter-tests/03-Spike-Test.jmx -l results/spike.jtl

# 4. Stress Test
jmeter -n -t jmeter-tests/04-Stress-Test.jmx -l results/stress.jtl
```

---

## 🎪 Test Scenarios

| Scenario | Test | Duration | When |
|----------|------|----------|------|
| **After Deployment** | 01-Smoke | 30s | Every deploy |
| **Daily Check** | 02-Load | 5 min | Daily |
| **Flash Sale Prep** | 03-Spike | 1 min | Before campaigns |
| **Capacity Planning** | 04-Stress | 5 min | Monthly |
| **CI/CD Pipeline** | 05-Functional | 3 min | Every commit |

---

## 📖 Tài liệu chi tiết

- **[README.md](README.md)** - Project overview & API documentation
- **[MYSQL_SETUP.md](MYSQL_SETUP.md)** - Database setup & configuration
- **[jmeter-tests/README-TESTS.md](jmeter-tests/README-TESTS.md)** - Detailed test documentation

---

## 🆘 Troubleshooting

### **Problem: Connection refused**
```bash
# Check if application is running
curl http://localhost:8080/api/test/health

# If not, start it
mvn spring-boot:run
```

### **Problem: Tests fail**
```bash
# Check logs
tail -f logs/application.log

# Check database
mysql -u root -p
use jmeter;
show tables;
```

### **Problem: High error rate**
```bash
# Check system resources
# Windows: Task Manager
# Linux: htop, vmstat

# Check database connections
SHOW PROCESSLIST;
```

---

## 🎯 Success Criteria

### **✅ Smoke Test:**
- Success Rate: 100%
- Response Time: < 500ms
- Duration: < 30s

### **✅ Load Test:**
- Success Rate: > 99%
- Response Time: < 1000ms
- Throughput: > 10 req/sec

### **✅ Spike Test:**
- Success Rate: > 95%
- System recovers: < 10s
- No crashes

### **✅ Stress Test:**
- Find breaking point
- Document limits
- Plan scaling

### **✅ Functional Test:**
- Success Rate: 100%
- All CRUD works
- Data correlation OK

---

**🎉 You're ready to test! Start with Smoke Test first!**

