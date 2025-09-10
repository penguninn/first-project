# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Java desktop application for managing retail sales operations built with Swing and Maven. The application includes a MySQL database backend and provides functionality for product management, sales processing, customer management, inventory tracking, and business analytics.

Main entry point: `com.daipc.UI.Login` (configured in pom.xml)

## Common Development Commands

### Build and Run
```bash
# Compile the project
mvn compile

# Run the application
mvn exec:java

# Clean and build
mvn clean compile

# Package into JAR
mvn package
```

### Database Setup
- MySQL database: `DuAn1_Final`
- Connection details in `JDBCHelper.java`: localhost:3306, user: root, password: 1234
- Database schema available in `DuAn_1.sql`
- Run the SQL file to set up the database schema and initial data

## Architecture Overview

### Package Structure
- **UI Package** (`com.daipc.UI`): Main application frames and headers
  - `Login.java`: Authentication entry point
  - `MainFrame.java`: Main application window with navigation
  - `Header.java`: Application header component
  - `PanelChart.java`: Chart display component

- **Form Package** (`com.daipc.form`): Business feature forms
  - Sales: `Form_Sell.java`, `Form_Bill.java`, `Form_Refund.java`
  - Management: `Form_Products.java`, `Form_Customer.java`, `Form_Staffs.java`
  - Analytics: `Form_Home.java` (dashboard), `Form_Promotion.java`
  - User: `Form_Profile.java`

- **Model Package** (`com.daipc.model`): Data entities
  - Core: `SanPham`, `KhachHang`, `NhanVien`, `HoaDon`
  - Product attributes: `ChatLieu`, `MauSac`, `Size`, `DoDay`
  - Business: `Voucher`, `GioHang`, `SPCT` (product details)
  - Analytics: `ThongKe*` classes for various statistics

- **Repository Package** (`com.daipc.repo`): Data access layer
  - `JDBCHelper.java`: Database connection management
  - Entity repos: `*Repo.java` classes for CRUD operations
  - Business logic: `QuanLi*.java` classes for complex operations

- **Component Packages**: Custom UI components
  - `swing/`: Custom Swing components (`Button`, `MenuItem`, etc.)
  - `table/`: Custom table renderers and editors
  - `textfield/`: Custom input fields
  - `chart/`: Chart components for analytics
  - `customTable/`: Advanced table functionality with action buttons

### Key Technologies
- **UI Framework**: Swing with FlatLaf look and feel
- **Database**: MySQL with JDBC
- **Layout**: MiG Layout for complex layouts
- **Charts**: JFreeChart for analytics visualization
- **File Operations**: Apache POI for Excel export, iText for PDF generation
- **Additional UI**: SwingX components, JCalendar for date selection

### Application Flow
1. **Authentication**: Users log in through `Login.java`
2. **Main Navigation**: `MainFrame.java` provides menu-based navigation
3. **Feature Forms**: Each business function has dedicated forms in the `form` package
4. **Data Management**: Repository classes handle database operations
5. **UI Components**: Custom components provide consistent user experience

### Database Connection
- Connection managed by `JDBCHelper.java`
- Uses connection pooling pattern
- Database credentials: localhost:3306/DuAn1_Final (root/1234)
- Ensure MySQL server is running before application startup

### Resource Management
- Icons and images in `src/main/resources/com/daipc/icon/`
- External JAR libraries in `src/main/resources/com/daipc/Library/`
- Form files (.form) are NetBeans GUI builder files paired with Java classes