# 🧪 JMeter Test Plans - Complete Guide

## 📋 **Danh sách Test Plans**

### ✅ **01-Smoke-Test.jmx** 
**Mục đích:** Quick sanity check sau khi deploy  
**Cấu hình:**
- **Users:** 1
- **Duration:** ~30 giây
- **Loops:** 1

**Use Cases:**
- ✅ Health check
- ✅ Verify basic APIs work
- ✅ Quick validation after deployment
- ✅ CI/CD pipeline check

**Khi nào chạy:**
- Sau mỗi deployment
- Trước các test phức tạp
- Daily sanity check

---

### ✅ **02-Load-Test.jmx**
**Mục đích:** Test với normal expected load  
**Cấu hình:**
- **Users:** 10
- **Ramp-up:** 30 giây
- **Loops:** 10
- **Duration:** ~5 phút

**Test Cases:**
- Health check
- Get Users
- Get Products
- Create User
- Create Product

**Metrics:**
- **Response Time:** < 1000ms
- **Throughput:** > 10 req/sec
- **Error Rate:** < 1%

**Use Cases:**
- ✅ Normal daily traffic
- ✅ Baseline performance
- ✅ Capacity planning

---

### ✅ **03-Spike-Test.jmx**
**Mục đích:** Test sudden load increase (Flash Sale scenario)  
**Cấu hình:**
- **Users:** 100
- **Ramp-up:** 10 giây (Very fast!)
- **Duration:** 60 giây
- **Loops:** 5

**Scenario:**
```
0s    ────────▶  10s   ────────▶  60s
0 users      100 users      100 users
(Normal)      (SPIKE!)     (Sustained)
```

**Test Cases:**
- Random selection of endpoints
- Uniform random timer (500-1000ms)

**Metrics:**
- **System Recovery:** < 10 giây
- **Error Rate:** < 5%
- **Response Time:** Acceptable degradation

**Use Cases:**
- ✅ Flash sales
- ✅ Marketing campaigns
- ✅ Viral content
- ✅ Breaking news

---

### ✅ **04-Stress-Test.jmx**
**Mục đích:** Find breaking point  
**Cấu hình:**
- **Users:** 200 ⚠️ (INCREASED - Will break!)
- **Ramp-up:** 20 giây ⚠️ (Fast ramp-up!)
- **Duration:** 5 phút (300 seconds)
- **Loops:** Infinite (-1)

**Test Cases:**
- Health Check
- Get Users
- Get Products
- Search Products
- Load Test endpoint
- Memory Test endpoint

**Metrics:**
- **Find:** Maximum capacity
- **Monitor:** CPU, Memory, Database
- **Identify:** Bottlenecks

**Use Cases:**
- ✅ Peak hours
- ✅ Black Friday
- ✅ System limits
- ✅ Capacity planning

---

### ✅ **05-Functional-API-Test.jmx**
**Mục đích:** Comprehensive CRUD testing  
**Cấu hình:**
- **Thread Group 1:** User API Tests (3 users, 3 loops)
- **Thread Group 2:** Product API Tests (3 users, 3 loops)

**User API Tests:**
1. ✅ Create User (with data extraction)
2. ✅ Get User by ID
3. ✅ Update User
4. ✅ Delete User

**Product API Tests:**
1. ✅ Create Product (with data extraction)
2. ✅ Get Product by ID
3. ✅ Search Products
4. ✅ Get Products by Category
5. ✅ Delete Product

**Features:**
- Data correlation (JSONPostProcessor)
- Dynamic data generation
- Think time simulation

**Use Cases:**
- ✅ Regression testing
- ✅ API validation
- ✅ Integration testing
- ✅ CI/CD automation

---

## 🎯 **Các Test còn thiếu (Recommend thêm)**

### **06-Endurance-Test.jmx** (NÊN THÊM)
**Mục đích:** Long-duration stability testing  
**Cấu hình khuyến nghị:**
```
Users: 30
Ramp-up: 30 seconds
Duration: 8-24 hours
Loops: Infinite
```

**Mục tiêu:**
- ✅ Find memory leaks
- ✅ Resource exhaustion
- ✅ Performance degradation over time
- ✅ Database connection leaks

---

### **07-Concurrency-Test.jmx** (NÊN THÊM)
**Mục đích:** Test race conditions  
**Cấu hình khuyến nghị:**
```
Users: 100
Ramp-up: 1 second (All at once!)
Loops: 10
Target: Same resource
```

**Scenarios:**
- ✅ 100 users update same product
- ✅ Multiple bookings same seat
- ✅ Inventory deduction
- ✅ Payment processing

---

### **08-Scalability-Test.jmx** (OPTIONAL)
**Mục đích:** Growth planning  
**Cấu hình khuyến nghị:**
```
Users: 10 → 500 (increment)
Step: +10 every minute
Duration: 50 minutes
```

**Mục tiêu:**
- Find optimal capacity
- Test auto-scaling
- Horizontal scaling validation

---

## 📊 **Test Execution Order**

### **1. Development/Testing:**
```
01-Smoke-Test.jmx          → Quick check (30s)
05-Functional-API-Test.jmx → CRUD validation (2 min)
02-Load-Test.jmx           → Normal load (5 min)
```

### **2. Pre-Production:**
```
01-Smoke-Test.jmx          → Sanity (30s)
02-Load-Test.jmx           → Baseline (5 min)
03-Spike-Test.jmx          → Flash sale sim (1 min)
04-Stress-Test.jmx         → Breaking point (5 min)
```

### **3. Production Readiness:**
```
All tests above +
06-Endurance-Test.jmx      → Stability (8-24 hours)
07-Concurrency-Test.jmx    → Race conditions (2 min)
```

---

## 🚀 **Cách chạy Tests**

### **GUI Mode (Development):**
```bash
# Windows
jmeter.bat

# Linux/Mac
jmeter.sh

# Sau đó: File → Open → Chọn .jmx file
```

### **Non-GUI Mode (Production):**
```bash
# Smoke Test
jmeter -n -t jmeter-tests/01-Smoke-Test.jmx -l results/smoke-test.jtl

# Load Test
jmeter -n -t jmeter-tests/02-Load-Test.jmx -l results/load-test.jtl

# Spike Test
jmeter -n -t jmeter-tests/03-Spike-Test.jmx -l results/spike-test.jtl

# Stress Test
jmeter -n -t jmeter-tests/04-Stress-Test.jmx -l results/stress-test.jtl

# Functional Test
jmeter -n -t jmeter-tests/05-Functional-API-Test.jmx -l results/functional-test.jtl
```

### **Generate HTML Report:**
```bash
jmeter -g results/load-test.jtl -o reports/load-test/
```

---

## 📈 **Success Criteria**

### **01-Smoke-Test:**
- ✅ 100% Success Rate
- ✅ Response Time < 500ms
- ✅ All assertions pass

### **02-Load-Test:**
- ✅ 99% Success Rate
- ✅ Average Response Time < 1000ms
- ✅ Throughput > 10 req/sec
- ✅ CPU < 80%

### **03-Spike-Test:**
- ✅ 95% Success Rate
- ✅ System recovers within 10s
- ✅ No crashes
- ✅ Response time degradation acceptable

### **04-Stress-Test:**
- ✅ Find breaking point
- ✅ Identify bottlenecks
- ✅ Document maximum capacity
- ✅ Plan for scaling

### **05-Functional-API-Test:**
- ✅ 100% Success Rate
- ✅ All CRUD operations work
- ✅ Data correlation successful
- ✅ No data corruption

---

## 🎯 **Test Coverage Matrix**

| Test Type | Smoke | Load | Spike | Stress | Functional |
|-----------|-------|------|-------|--------|------------|
| **Health Check** | ✅ | ✅ | ✅ | ✅ | ❌ |
| **GET Users** | ✅ | ✅ | ✅ | ✅ | ❌ |
| **GET Products** | ✅ | ✅ | ✅ | ✅ | ❌ |
| **POST User** | ✅ | ✅ | ❌ | ❌ | ✅ |
| **POST Product** | ✅ | ✅ | ❌ | ❌ | ✅ |
| **PUT User** | ❌ | ❌ | ❌ | ❌ | ✅ |
| **DELETE User** | ❌ | ❌ | ❌ | ❌ | ✅ |
| **Search** | ❌ | ❌ | ✅ | ✅ | ✅ |
| **Load Test EP** | ❌ | ❌ | ✅ | ✅ | ❌ |
| **Memory Test EP** | ❌ | ❌ | ❌ | ✅ | ❌ |

---

## 📝 **Notes**

### **Important:**
- ⚠️ **Smoke Test** should run < 1 minute
- ⚠️ **Load Test** represents normal traffic
- ⚠️ **Spike Test** simulates sudden traffic surge
- ⚠️ **Stress Test** finds system limits
- ⚠️ **Functional Test** validates API correctness

### **Best Practices:**
1. Always run **Smoke Test** first
2. Run tests in **non-GUI mode** for accuracy
3. Monitor system resources during tests
4. Save results for comparison
5. Document findings and bottlenecks

### **Monitoring:**
```bash
# During tests, monitor:
- CPU usage
- Memory usage  
- Database connections
- Response times
- Error rates
- Throughput
```

---

**Happy Testing! 🚀**

