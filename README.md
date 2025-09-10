# 🏪 Retail Management System

<div align="center">

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=for-the-badge&logo=mysql)
![Swing](https://img.shields.io/badge/Swing-GUI-green?style=for-the-badge&logo=java)
![Maven](https://img.shields.io/badge/Maven-Build-red?style=for-the-badge&logo=apache-maven)

*A comprehensive Java desktop application for retail store management with modern UI and complete business functionality*

</div>

---

## 📋 Table of Contents

- [Overview](#-overview)
- [Features](#-features)
- [Screenshots](#-screenshots)
- [Technology Stack](#-technology-stack)
- [Installation](#-installation)
- [Usage](#-usage)
- [Project Structure](#-project-structure)
- [Database Schema](#-database-schema)
- [Contributing](#-contributing)

## 🔍 Overview

The **Retail Management System** is a full-featured desktop application designed for managing retail operations. Built with Java Swing and MySQL, it provides a modern, user-friendly interface for handling sales, inventory, customer management, and business analytics.

### Key Highlights
- 🎯 **Complete Business Solution**: End-to-end retail management from inventory to analytics
- 🎨 **Modern UI**: Professional interface with FlatLaf theme and responsive design
- 📊 **Real-time Analytics**: Dashboard with charts, KPIs, and business intelligence
- 🔐 **Role-based Access**: Manager and employee roles with appropriate permissions
- 💾 **Robust Database**: MySQL backend with comprehensive data relationships
- 📈 **Scalable Architecture**: Well-organized codebase with separation of concerns

## ✨ Features

### 🏢 Core Business Modules

#### 📊 **Dashboard & Analytics**
- Real-time KPI monitoring (revenue, orders, profit margins)
- Interactive charts and statistical visualizations
- Multi-dimensional analytics (products, staff, customers)
- Time-based filtering and reporting

#### 💰 **Sales Management**
- Multi-order processing with shopping cart functionality
- Real-time inventory validation
- Customer lookup and creation
- Voucher/discount system integration
- Multiple payment methods (Cash, Bank Transfer)
- Order workflow automation

#### 📦 **Product Management**
- Master product catalog with variants
- Product attributes (size, color, material, thickness)
- Supplier relationship management
- Inventory tracking and stock alerts
- Advanced search and filtering

#### 👥 **Customer Management**
- Comprehensive customer database
- Contact information and demographics
- Customer status tracking
- Phone-based lookup system
- Purchase history integration

#### 🧾 **Invoice Management**
- Complete transaction history
- Detailed invoice breakdown
- Customer and payment tracking
- Search and filtering capabilities

#### 👤 **Staff Management**
- Employee database with role assignments
- Login credential management
- Performance tracking
- Active/inactive status control

#### 🎁 **Promotion Management**
- Voucher creation and management
- Time-based promotional campaigns
- Usage limits and restrictions
- Discount type configuration

#### 🔄 **Return Processing**
- Product return handling
- Automatic refund calculations
- Inventory adjustment automation

### 🛠️ Technical Features

#### 🎨 **User Interface**
- Modern FlatLaf look and feel
- Responsive MigLayout system
- Custom Swing components
- Interactive data tables with cell editors
- Real-time form validation
- Keyboard shortcuts and mouse interactions

#### 📈 **Data Visualization**
- JFreeChart integration for analytics
- Dynamic chart updates
- Color-coded performance metrics
- Statistical trend analysis

#### 📄 **Export Capabilities**
- Excel export using Apache POI
- PDF generation with iText
- Customizable report formats

## 📸 Screenshots

*Coming soon - Application screenshots showcasing the modern UI and key features*

## 🛠️ Technology Stack

### **Core Technologies**
- **Java 21** - Latest LTS version with modern language features
- **Swing** - Desktop GUI framework with custom components
- **MySQL 8.0** - Relational database for data persistence
- **Maven** - Build automation and dependency management

### **UI & Design**
- **FlatLaf** - Modern look and feel for Swing applications
- **MigLayout** - Flexible and powerful layout manager
- **SwingX** - Enhanced Swing components
- **Custom Components** - Purpose-built UI elements

### **Data & Analytics**
- **JDBC** - Database connectivity and operations
- **JFreeChart** - Chart and graph generation
- **Apache POI** - Excel file manipulation
- **iText** - PDF document generation

### **Additional Libraries**
- **JCalendar** - Date picker components
- **TimingFramework** - Animation support
- **JNA** - Native system integration

## 🚀 Installation

### Prerequisites
- **Java Development Kit (JDK) 21** or higher
- **MySQL Server 8.0** or higher
- **Maven 3.6** or higher

### Setup Instructions

1. **Clone the Repository**
   ```bash
   git clone <repository-url>
   cd first-project
   ```

2. **Database Setup**
   ```bash
   # Start MySQL service
   sudo systemctl start mysql  # Linux
   # or
   brew services start mysql   # macOS

   # Import database schema
   mysql -u root -p < DuAn_1.sql
   ```

3. **Configure Database Connection**
   
   Update database credentials in `src/main/java/com/daipc/repo/JDBCHelper.java`:
   ```java
   private final String URL = "jdbc:mysql://localhost:3306/DuAn1_Final";
   private final String USER = "your_username";
   private final String PASSWORD = "your_password";
   ```

4. **Build and Run**
   ```bash
   # Compile the project
   mvn compile

   # Run the application
   mvn exec:java
   ```

### Default Login Credentials
- **Username**: `nva`
- **Password**: `123`

## 📖 Usage

### Getting Started

1. **Login**: Use the default credentials or create new employee accounts
2. **Dashboard**: View real-time business metrics and analytics
3. **Sales**: Process orders by selecting products and customers
4. **Inventory**: Manage products, suppliers, and stock levels
5. **Customers**: Maintain customer database and relationships
6. **Reports**: Generate business reports and analytics

### Common Workflows

#### **Processing a Sale**
1. Navigate to Sales module
2. Select or create customer
3. Add products to cart (double-click or search)
4. Apply vouchers/discounts if applicable
5. Choose payment method
6. Complete transaction

#### **Managing Inventory**
1. Go to Products module
2. Add new products with variants
3. Set pricing and supplier information
4. Monitor stock levels
5. Update quantities as needed

#### **Generating Reports**
1. Access Dashboard or respective modules
2. Select date ranges and filters
3. View real-time analytics
4. Export data to Excel/PDF

## 📁 Project Structure

```
src/main/java/com/daipc/
├── UI/                     # Main application windows
│   ├── Login.java         # Authentication entry point
│   ├── MainFrame.java     # Main application window
│   └── Header.java        # Application header
├── form/                   # Business feature forms
│   ├── Form_Sell.java     # Sales processing
│   ├── Form_Products.java # Product management
│   ├── Form_Customer.java # Customer management
│   ├── Form_Bill.java     # Invoice management
│   ├── Form_Staffs.java   # Staff management
│   └── Form_Home.java     # Dashboard
├── model/                  # Data entities
│   ├── SanPham.java       # Product model
│   ├── KhachHang.java     # Customer model
│   └── HoaDon.java        # Invoice model
├── repo/                   # Data access layer
│   ├── JDBCHelper.java    # Database connection
│   └── QuanLi*.java       # Business logic classes
├── swing/                  # Custom UI components
├── table/                  # Custom table components
└── chart/                  # Chart components
```

## 🗃️ Database Schema

### Core Tables
- **SanPham** - Products and catalog
- **KhachHang** - Customer information
- **NhanVien** - Employee data
- **HoaDon** - Invoice transactions
- **HoaDonCT** - Invoice line items
- **Voucher** - Promotional codes
- **NhaCungCap** - Supplier management

### Key Relationships
- Products have multiple variants (size, color, material)
- Invoices contain multiple product line items
- Customers are linked to their purchase history
- Employees are associated with their sales transactions

### Development Guidelines

- Follow Java coding conventions
- Write meaningful commit messages
- Add comments for complex business logic
- Test thoroughly before submitting
- Update documentation as needed



---

<div align="center">

**📧 For questions or support, please open an issue on GitHub**

Made with ❤️ using Java and Swing

</div>