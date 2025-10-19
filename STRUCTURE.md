# 📁 Project Structure

## 📂 Tổng quan cấu trúc

```
jmeter/
│
├── 📄 README.md                          # Tài liệu chính - ĐỌC ĐẦU TIÊN
├── 📄 QUICK-START.md                     # Hướng dẫn nhanh 5 phút
├── 📄 MYSQL_SETUP.md                     # Setup MySQL database
├── 📄 STRUCTURE.md                       # File này
│
├── 📁 src/main/java/                     # Source code
│   └── com/example/jmetertestapp/
│       ├── config/                       # Configuration classes
│       ├── controller/                   # REST Controllers
│       ├── dto/                          # Data Transfer Objects
│       ├── model/                        # Entity classes
│       ├── repository/                   # JPA Repositories
│       ├── service/                      # Business logic
│       └── JmeterTestAppApplication.java # Main class
│
├── 📁 src/main/resources/
│   └── application.yml                   # Application configuration
│
├── 📁 jmeter-tests/                      # JMeter Test Plans
│   ├── 01-Smoke-Test.jmx                # ⚡ Quick check (30s)
│   ├── 02-Load-Test.jmx                 # 📊 Normal load (5 min)
│   ├── 03-Spike-Test.jmx                # 🚀 Flash sale (1 min)
│   ├── 04-Stress-Test.jmx               # 💪 Breaking point (5 min)
│   ├── 05-Functional-API-Test.jmx       # ✅ CRUD testing (3 min)
│   └── README-TESTS.md                  # Chi tiết các tests
│
├── 📁 database/
│   └── setup.sql                         # MySQL setup script
│
└── 📄 pom.xml                            # Maven dependencies

```

---

## 📚 Hướng dẫn đọc tài liệu

### **1. Mới bắt đầu? ĐỌC THEO THỨ TỰ:**

```
1️⃣ README.md              (5 phút)  → Tổng quan project
2️⃣ MYSQL_SETUP.md         (10 phút) → Setup database  
3️⃣ QUICK-START.md         (5 phút)  → Chạy test đầu tiên
4️⃣ jmeter-tests/          (...)     → Tìm hiểu các tests
   README-TESTS.md
```

### **2. Đã quen? QUICK REFERENCE:**

```bash
# Start app
mvn spring-boot:run

# Run test
jmeter -n -t jmeter-tests/01-Smoke-Test.jmx -l results/smoke.jtl

# Check docs
README.md → API endpoints
QUICK-START.md → Commands
```

---

## 🎯 File nào cho mục đích gì?

### **📄 README.md** - File chính
- ✅ Tổng quan project
- ✅ Danh sách API endpoints
- ✅ Danh sách test plans
- ✅ Configuration examples
- ✅ Troubleshooting

**Khi nào đọc:** Đầu tiên, reference cho API

---

### **📄 QUICK-START.md** - Bắt đầu nhanh
- ✅ 3 bước để bắt đầu
- ✅ Commands để chạy tests
- ✅ Test scenarios
- ✅ Success criteria
- ✅ Quick troubleshooting

**Khi nào đọc:** Muốn chạy test ngay, cần commands nhanh

---

### **📄 MYSQL_SETUP.md** - Database setup
- ✅ Cài đặt MySQL
- ✅ Cấu hình database
- ✅ Performance tuning
- ✅ Monitoring queries
- ✅ Troubleshooting MySQL

**Khi nào đọc:** Setup database lần đầu, gặp lỗi database

---

### **📄 jmeter-tests/README-TESTS.md** - Chi tiết tests
- ✅ Chi tiết từng test plan
- ✅ Configuration options
- ✅ Success criteria
- ✅ Use cases
- ✅ Best practices

**Khi nào đọc:** Muốn hiểu sâu về tests, customize tests

---

## 🔍 Tìm thông tin nhanh

### **"Tôi muốn biết..."**

| Muốn biết gì | Xem file nào | Section |
|--------------|--------------|---------|
| API có gì | README.md | API Endpoints |
| Chạy test như thế nào | QUICK-START.md | Quick Start |
| Setup MySQL | MYSQL_SETUP.md | Cài đặt MySQL |
| Test nào cho gì | README.md | JMeter Test Plans |
| Chi tiết test | README-TESTS.md | Test Plans Overview |
| Commands | QUICK-START.md | Execution Commands |
| Troubleshooting | README.md hoặc QUICK-START.md | Troubleshooting |

---

## 📊 Test Plans

```
01-Smoke-Test.jmx          ⚡ 30s   → After deploy
02-Load-Test.jmx           📊 5min  → Daily baseline
03-Spike-Test.jmx          🚀 1min  → Flash sale prep
04-Stress-Test.jmx         💪 5min  → Capacity planning
05-Functional-API-Test.jmx ✅ 3min  → CI/CD pipeline
```

**Chi tiết:** Xem [jmeter-tests/README-TESTS.md](jmeter-tests/README-TESTS.md)

---

## 🎯 Quick Actions

### **Chạy test nhanh:**
```bash
# Start app
mvn spring-boot:run

# Smoke test
jmeter -n -t jmeter-tests/01-Smoke-Test.jmx -l results/smoke.jtl
```

### **Xem API:**
```bash
# Health check
curl http://localhost:8080/api/test/health

# Get users
curl http://localhost:8080/api/users
```

### **Database:**
```bash
mysql -u root -p
use jmeter;
show tables;
```

---

## 📝 Notes

- **3 files chính:** README, QUICK-START, MYSQL_SETUP
- **1 file test details:** jmeter-tests/README-TESTS.md
- **5 test plans:** .jmx files trong jmeter-tests/
- **Tất cả là Markdown** - dễ đọc, dễ edit

---

**🎉 Đơn giản, rõ ràng, đủ dùng!**
