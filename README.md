# 🏪 Retail Management System

<div align="center">

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=for-the-badge&logo=mysql)
![Swing](https://img.shields.io/badge/Swing-GUI-green?style=for-the-badge&logo=java)
![Maven](https://img.shields.io/badge/Maven-Build-red?style=for-the-badge&logo=apache-maven)

*A Java desktop application for managing retail store operations.*

</div>

---

## 🔍 Overview

Retail Management System is a desktop application built with **Java Swing and MySQL** for managing common retail operations such as sales, products, customers, invoices, staff, promotions, returns, and business statistics.

The project focuses on applying Java desktop development, JDBC, relational database design, and layered application architecture.

## ✨ Features

- 📊 Dashboard and business statistics
- 💰 Sales and order processing
- 📦 Product and inventory management
- 👥 Customer management
- 🧾 Invoice management
- 👤 Staff and role management
- 🎁 Voucher and promotion management
- 🔄 Product return processing
- 📈 Charts and reporting
- 📄 Excel and PDF export

## 🛠️ Technology Stack

### Core

- **Java 21**
- **Java Swing**
- **MySQL 8.0**
- **JDBC**
- **Maven**

### Libraries

- **FlatLaf** — modern Swing look and feel
- **MigLayout** — UI layout management
- **JFreeChart** — charts and statistics
- **Apache POI** — Excel export
- **iText** — PDF generation
- **JCalendar** — date picker components

## 📸 Screenshots

> Application screenshots will be added here.

## 🚀 Getting Started

### Prerequisites

- JDK 21+
- MySQL 8.0+
- Maven 3.6+

### 1. Clone the repository

```bash
git clone <repository-url>
cd first-project
```

### 2. Create the database

```bash
mysql -u root -p < DuAn_1.sql
```

### 3. Configure database connection

Update:

```text
src/main/java/com/daipc/repo/JDBCHelper.java
```

with your MySQL credentials.

Example:

```java
private final String URL = "jdbc:mysql://localhost:3306/DuAn1_Final";
private final String USER = "your_username";
private final String PASSWORD = "your_password";
```

### 4. Run the application

```bash
mvn compile
mvn exec:java
```

### Default Account

```text
Username: nva
Password: 123
```

## 📁 Project Structure

```text
src/main/java/com/daipc/
├── UI/        # Main application windows
├── form/      # Feature screens
├── model/     # Domain models
├── repo/      # JDBC and data access
├── swing/     # Custom Swing components
├── table/     # Table components
└── chart/     # Chart components
```

## 🗃️ Main Database Entities

```text
SanPham       Product
KhachHang     Customer
NhanVien      Employee
HoaDon        Invoice
HoaDonCT      Invoice Item
Voucher       Promotion
NhaCungCap    Supplier
```

Main relationships:

```text
Customer
   │
   └── HoaDon
         │
         └── HoaDonCT
               │
               └── SanPham

SanPham ─── NhaCungCap

HoaDon ─── NhanVien
```

---

<div align="center">

Made with Java, Swing and MySQL.

</div>
