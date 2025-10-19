# 🎛️ Resource Limits Configuration

## 🎯 **Tại sao cần limit resources?**

Để dễ dàng test **breaking point** với Stress Test, chúng ta cần giới hạn resources:
- ✅ Tìm breaking point nhanh hơn
- ✅ Test với ít users hơn (tiết kiệm tài nguyên)
- ✅ Realistic testing (production thường có limits)
- ✅ Test recovery behavior

---

## ⚙️ **Current Configuration**

### **Server Limits (Tomcat):**
```yaml
server:
  tomcat:
    threads:
      max: 20              # Max concurrent threads (default: 200)
      min-spare: 5         # Minimum idle threads
    max-connections: 100   # Max connections (default: 8192)
    accept-count: 10       # Queue size when full (default: 100)
    connection-timeout: 20000  # 20 seconds
```

### **Database Limits (HikariCP):**
```yaml
spring:
  datasource:
    hikari:
      maximum-pool-size: 10           # Max DB connections
      minimum-idle: 2                 # Min idle connections
      connection-timeout: 30000       # 30 seconds wait
      idle-timeout: 600000            # 10 minutes idle
      max-lifetime: 1800000           # 30 minutes max
      leak-detection-threshold: 60000 # Detect leaks after 60s
```

---

## 📊 **Breaking Point Prediction**

### **With Current Limits:**

**Tomcat Threads:**
- Max: 20 threads
- Each request needs 1 thread
- **Breaking point**: ~20-25 concurrent users

**Database Connections:**
- Max: 10 connections
- Heavy queries may use 1-2 connections
- **Breaking point**: ~10-15 concurrent DB operations

**Expected Breaking Point:**
```
✅ Smoke Test (1 user):        Pass ✓
✅ Load Test (10 users):       Pass ✓
⚠️ Spike Test (100 users):    Will fail/slow
⚠️ Stress Test (50 users):    Will break around 20-30 users
```

---

## 🎚️ **Cách điều chỉnh Limits**

### **Scenario 1: Muốn dễ gãy hơn** (Testing)
```yaml
server:
  tomcat:
    threads:
      max: 10              # Giảm còn 10 threads
    max-connections: 50    # Giảm connections
    
spring:
  datasource:
    hikari:
      maximum-pool-size: 5 # Chỉ 5 DB connections
```
**Result**: Gãy ở ~10 users

---

### **Scenario 2: Muốn khó gãy hơn** (Production-like)
```yaml
server:
  tomcat:
    threads:
      max: 100             # Tăng lên 100 threads
    max-connections: 500   # Tăng connections
    
spring:
  datasource:
    hikari:
      maximum-pool-size: 30 # 30 DB connections
```
**Result**: Gãy ở ~100+ users

---

### **Scenario 3: Default (Khó gãy)** 
```yaml
server:
  tomcat:
    threads:
      max: 200             # Default Spring Boot
    max-connections: 8192  # Default Tomcat
    
spring:
  datasource:
    hikari:
      maximum-pool-size: 10 # Default HikariCP
```
**Result**: Cần 500+ users mới gãy

---

## 🎯 **Recommended Settings**

### **Development/Testing:**
```yaml
# File: src/main/resources/application-testing.yml
server:
  tomcat:
    threads:
      max: 20
    max-connections: 100

spring:
  datasource:
    hikari:
      maximum-pool-size: 10
```
✅ **Current setting** - Tốt cho testing

---

### **Staging:**
```yaml
# File: src/main/resources/application-staging.yml
server:
  tomcat:
    threads:
      max: 50
    max-connections: 300

spring:
  datasource:
    hikari:
      maximum-pool-size: 20
```

---

### **Production:**
```yaml
# File: src/main/resources/application-production.yml
server:
  tomcat:
    threads:
      max: 200
    max-connections: 1000

spring:
  datasource:
    hikari:
      maximum-pool-size: 50
```

---

## 🔍 **How to Monitor Limits**

### **During Stress Test, watch for:**

#### **1. Thread Pool Exhaustion**
```
Symptoms:
- Response time tăng đột ngột
- Errors: "Pool exhausted"
- Requests queued

Logs:
WARN: Tomcat thread pool exhausted
```

#### **2. Database Connection Pool Exhaustion**
```
Symptoms:
- SQLTransientConnectionException
- "Connection is not available"
- Timeout errors

Logs:
ERROR: HikariPool - Connection is not available
```

#### **3. Memory Issues**
```
Symptoms:
- OutOfMemoryError
- Garbage Collection pauses
- Application slow/freeze

Logs:
ERROR: java.lang.OutOfMemoryError: Java heap space
```

---

## 📈 **Test Results với Current Limits**

### **Expected Results:**

| Test | Users | Expected Result |
|------|-------|----------------|
| Smoke | 1 | ✅ Pass |
| Load | 10 | ✅ Pass |
| Spike | 100 | ⚠️ Errors when spike hits |
| Stress | 50 | 🔴 Break at ~20-30 users |

### **Breaking Point Indicators:**
```
Users: 1-10     → ✅ Normal (< 500ms)
Users: 11-20    → ⚠️ Degrading (500-1000ms)
Users: 21-30    → 🔴 Breaking (> 2000ms, errors)
Users: 30+      → 💥 Broken (timeouts, 503 errors)
```

---

## 🎪 **Quick Adjust Commands**

### **Để test nhanh, sửa trực tiếp trong application.yml:**

```bash
# Giảm threads để dễ gãy
max: 20 → max: 10

# Giảm DB connections
maximum-pool-size: 10 → maximum-pool-size: 5

# Restart app
mvn spring-boot:run

# Run stress test
jmeter -n -t jmeter-tests/04-Stress-Test.jmx -l results/stress.jtl
```

---

## 💡 **Tips**

### **1. Test Breaking Point hiệu quả:**
```
✅ Start với limits thấp (current config)
✅ Run stress test
✅ Note breaking point
✅ Tăng limits dần dần
✅ Re-test để so sánh
```

### **2. Monitor trong khi test:**
```bash
# Terminal 1: Application logs
tail -f logs/application.log

# Terminal 2: JMeter test
jmeter -n -t test.jmx -l results.jtl

# Terminal 3: System resources
# Windows: Task Manager
# Linux: htop
```

### **3. Analyze khi gãy:**
```
- Check logs để xem lỗi gì
- Note số users khi gãy
- Check response time graph
- Identify bottleneck (threads/DB/memory)
```

---

## 🎯 **Current Config Summary**

```yaml
✅ Tomcat Max Threads:        20
✅ Tomcat Max Connections:    100
✅ DB Connection Pool:        10
✅ Connection Timeout:        30s

Expected Breaking Point:     ~20-30 concurrent users
Recommended Test Users:      200 (trong Stress Test) ⚠️ WILL BREAK!
```

---

## 🚀 **Ready to Test!**

Với config hiện tại:
- ✅ Smoke Test (1 user) → Pass
- ✅ Load Test (10 users) → Pass
- ⚠️ Spike Test (100 users) → Sẽ có errors
- 🔴 Stress Test (200 users) → CHẮC CHẮN GÃY ở ~20-30 users!

**➡️ RUN STRESS TEST để xem breaking point!**

```bash
mvn spring-boot:run
jmeter -n -t jmeter-tests/04-Stress-Test.jmx -l results/stress.jtl
```

---

**📝 Note:** Nếu vẫn không gãy, giảm `max: 20` xuống `max: 10`!
