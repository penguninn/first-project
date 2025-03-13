-- Drop and recreate the database with UTF-8 support for Vietnamese characters
DROP DATABASE IF EXISTS DuAn1_Final;
CREATE DATABASE DuAn1_Final CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE DuAn1_Final;

-- Create table SanPham
CREATE TABLE SanPham (
                         ID INT PRIMARY KEY AUTO_INCREMENT,
                         MaSP VARCHAR(255),
                         TenSP VARCHAR(255),
                         MoTa TEXT,
                         HienThi VARCHAR(50) DEFAULT 'Hien'
);

-- Create triggers for SanPham (MySQL requires separate triggers for INSERT and UPDATE)
DELIMITER //

CREATE TRIGGER trg_Unique_MaSP_Insert BEFORE INSERT ON SanPham
    FOR EACH ROW
BEGIN
    IF EXISTS (SELECT 1 FROM SanPham WHERE MaSP = NEW.MaSP) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'DaTonTai';
    END IF;
    IF EXISTS (SELECT 1 FROM SanPham WHERE TenSP = NEW.TenSP) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'DaTonTai';
    END IF;
END;
//

CREATE TRIGGER trg_Unique_MaSP_Update BEFORE UPDATE ON SanPham
    FOR EACH ROW
BEGIN
    IF EXISTS (SELECT 1 FROM SanPham WHERE MaSP = NEW.MaSP AND ID <> NEW.ID) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'DaTonTai';
    END IF;
    IF EXISTS (SELECT 1 FROM SanPham WHERE TenSP = NEW.TenSP AND ID <> NEW.ID) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'DaTonTai';
    END IF;
END;
//

DELIMITER ;

-- Create table NhaCungCap
CREATE TABLE NhaCungCap (
                            ID INT PRIMARY KEY AUTO_INCREMENT,
                            MaNhaCungCap VARCHAR(255),
                            TenNhaCungCap VARCHAR(255),
                            LienHe VARCHAR(13),
                            DiaChi VARCHAR(255)
);

-- Create table MauSac
CREATE TABLE MauSac (
                        ID INT PRIMARY KEY AUTO_INCREMENT,
                        MaMauSac VARCHAR(255),
                        TenMauSac VARCHAR(255),
                        TrangThai VARCHAR(50)
);

-- Create table Size
CREATE TABLE Size (
                      ID INT PRIMARY KEY AUTO_INCREMENT,
                      MaSize VARCHAR(255),
                      TenSize VARCHAR(255),
                      TrangThai VARCHAR(50)
);

-- Create table ChatLieu
CREATE TABLE ChatLieu (
                          ID INT PRIMARY KEY AUTO_INCREMENT,
                          MaChatLieu VARCHAR(255),
                          TenChatLieu VARCHAR(255),
                          TrangThai VARCHAR(50)
);

-- Create table DoDay
CREATE TABLE DoDay (
                       ID INT PRIMARY KEY AUTO_INCREMENT,
                       MaDoDay VARCHAR(255),
                       TenDoDay VARCHAR(255),
                       TrangThai VARCHAR(50)
);

-- Create table SanPhamChiTiet
CREATE TABLE SanPhamChiTiet (
                                ID INT PRIMARY KEY AUTO_INCREMENT,
                                MaSPCT VARCHAR(255),
                                TenSPCT VARCHAR(255),
                                SoLuong INT,
                                IdSanPham INT,
                                IdMauSac INT,
                                IdSize INT,
                                IdChatLieu INT,
                                IdDoDay INT,
                                IdNhaCungCap INT,
                                NgayTao DATE,
                                GiaNhap DECIMAL(10, 2),
                                GiaBan DECIMAL(10, 2),
                                TrangThai VARCHAR(50),
                                HienThi VARCHAR(50) DEFAULT 'Hien',
                                FOREIGN KEY (IdSanPham) REFERENCES SanPham(ID),
                                FOREIGN KEY (IdMauSac) REFERENCES MauSac(ID),
                                FOREIGN KEY (IdSize) REFERENCES Size(ID),
                                FOREIGN KEY (IdChatLieu) REFERENCES ChatLieu(ID),
                                FOREIGN KEY (IdDoDay) REFERENCES DoDay(ID),
                                FOREIGN KEY (IdNhaCungCap) REFERENCES NhaCungCap(ID)
);

-- Create table NhanVien
CREATE TABLE NhanVien (
                          ID INT PRIMARY KEY AUTO_INCREMENT,
                          MaNhanVien VARCHAR(255),
                          HoTen VARCHAR(255),
                          SoDT VARCHAR(10),
                          CCCD VARCHAR(12),
                          NgaySinh DATE,
                          ChucVu VARCHAR(50),
                          GioiTinh BOOLEAN,
                          DiaChi VARCHAR(255),
                          TaiKhoan VARCHAR(50),
                          MatKhau VARCHAR(10),
                          NgayTao DATE,
                          TrangThai BOOLEAN DEFAULT 1
);

-- Create table KhachHang
CREATE TABLE KhachHang (
                           ID INT PRIMARY KEY AUTO_INCREMENT,
                           MaKhachHang VARCHAR(255),
                           HoTen VARCHAR(255),
                           GioiTinh BOOLEAN,
                           SoDT VARCHAR(15) UNIQUE,
                           DiaChi VARCHAR(255),
                           NgayTao DATE,
                           NguoiTao VARCHAR(255),
                           TrangThai BOOLEAN DEFAULT 1
);

-- Create table Voucher
CREATE TABLE Voucher (
                         ID INT PRIMARY KEY AUTO_INCREMENT,
                         MaVoucher VARCHAR(255),
                         GiaTriVoucher DECIMAL(10, 2),
                         NgayBatDau DATE,
                         NgayKetThuc DATE,
                         SoLuong INT,
                         MoTa TEXT,
                         TrangThai INT
);

-- Create table PhuongThucThanhToan
CREATE TABLE PhuongThucThanhToan (
                                     ID INT PRIMARY KEY AUTO_INCREMENT,
                                     TenPhuongThucTT VARCHAR(255),
                                     TrangThai BOOLEAN DEFAULT 1
);

-- Create table HoaDon
CREATE TABLE HoaDon (
                        ID INT PRIMARY KEY AUTO_INCREMENT,
                        MaHD VARCHAR(255),
                        IDKhachHang INT,
                        IDNhanVien INT,
                        IDVoucher INT,
                        TongGiaTriHoaDon DECIMAL(10, 2),
                        ThanhToan DECIMAL(10, 2),
                        IDPhuongThucTT INT,
                        NgayTao DATE,
                        TrangThai INT,
                        GhiChu TEXT,
                        FOREIGN KEY (IDKhachHang) REFERENCES KhachHang(ID),
                        FOREIGN KEY (IDNhanVien) REFERENCES NhanVien(ID),
                        FOREIGN KEY (IDVoucher) REFERENCES Voucher(ID),
                        FOREIGN KEY (IDPhuongThucTT) REFERENCES PhuongThucThanhToan(ID)
);

-- Create table HoaDonCT
CREATE TABLE HoaDonCT (
                          ID INT PRIMARY KEY AUTO_INCREMENT,
                          IDHoaDon INT,
                          IDCTSP INT,
                          DonGia DECIMAL(10, 2),
                          TrangThai BOOLEAN,
                          SoLuong INT,
                          FOREIGN KEY (IDHoaDon) REFERENCES HoaDon(ID),
                          FOREIGN KEY (IDCTSP) REFERENCES SanPhamChiTiet(ID)
);

-- Insert data into SanPham
INSERT INTO SanPham (MaSP, TenSP, MoTa, HienThi)
VALUES
    ('SP001', 'Quần dài Adidas Originals', 'Quần dài thể thao nam Adidas Originals', 'Hien'),
    ('SP002', 'Quần dài Adidas Sportswear', 'Quần dài thể thao nam Adidas Sportswear', 'Hien'),
    ('SP003', 'Quần dài Adidas Terrex', 'Quần dài outdoor nam Adidas Terrex', 'Hien'),
    ('SP004', 'Quần dài Adidas Performance', 'Quần dài thể thao nam Adidas Performance', 'Hien'),
    ('SP005', 'Quần dài Adidas Essentials', 'Quần dài cơ bản nam Adidas Essentials', 'Hien'),
    ('SP006', 'Quần dài Adidas Adicolor', 'Quần dài thời trang nam Adidas Adicolor', 'Hien'),
    ('SP007', 'Quần dài Adidas Z.N.E.', 'Quần dài thể thao nam Adidas Z.N.E.', 'Hien'),
    ('SP008', 'Quần dài Adidas 4DFWD', 'Quần dài chạy bộ nam Adidas 4DFWD', 'Hien'),
    ('SP009', 'Quần dài Adidas Primeblue', 'Quần dài nam Adidas Primeblue từ nhựa tái chế', 'Hien'),
    ('SP010', 'Quần dài Adidas Tiro', 'Quần dài bóng đá nam Adidas Tiro', 'Hien');

-- Insert data into NhaCungCap
INSERT INTO NhaCungCap (MaNhaCungCap, TenNhaCungCap, LienHe, DiaChi)
VALUES
    ('NCC001', 'Công ty TNHH Adidas Việt Nam', '0123456789', '123 Đường Lê Lợi, Quận 1, TP. HCM'),
    ('NCC002', 'Công ty CP Thể Thao Sài Gòn', '0987654321', '456 Đường Nguyễn Huệ, Quận 1, TP. HCM'),
    ('NCC003', 'Tổng công ty CP Phong Phú', '0909123456', '789 Đường Nguyễn Trãi, Quận 5, TP. HCM'),
    ('NCC004', 'Công ty TNHH May Việt Tiến', '0918765432', '159 Đường Hai Bà Trưng, Quận 3, TP. HCM'),
    ('NCC005', 'Công ty CP Dệt May Thành Công', '0965432198', '357 Đường Sư Vạn Hạnh, Quận 10, TP. HCM'),
    ('NCC006', 'Công ty TNHH Thương mại Quốc tế Hoàng Phúc', '0932165478', '753 Đường 3/2, Quận 10, TP. HCM'),
    ('NCC007', 'Công ty TNHH Thể Thao Động Lực', '0945678912', '951 Đường Cách Mạng Tháng 8, Quận 3, TP. HCM'),
    ('NCC008', 'Công ty CP XNK Tổng hợp I Việt Nam', '0978912345', '258 Đường Võ Văn Tần, Quận 3, TP. HCM'),
    ('NCC009', 'Công ty TNHH MTV Thương mại Thái Bình', '0991234567', '456 Đường Lý Thường Kiệt, Quận Tân Bình, TP. HCM'),
    ('NCC010', 'Công ty TNHH Thương mại Dịch vụ Tổng hợp Vina', '0912345678', '789 Đường Trường Chinh, Quận Tân Bình, TP. HCM');

-- Insert data into MauSac
INSERT INTO MauSac (MaMauSac, TenMauSac, TrangThai)
VALUES
    ('MS001', 'Đen', 'ConHang'),
    ('MS002', 'Xanh navy', 'ConHang'),
    ('MS003', 'Xám', 'ConHang'),
    ('MS004', 'Trắng', 'ConHang'),
    ('MS005', 'Xanh dương', 'ConHang'),
    ('MS006', 'Đỏ', 'ConHang'),
    ('MS007', 'Xanh lá', 'ConHang'),
    ('MS008', 'Cam', 'ConHang'),
    ('MS009', 'Vàng', 'ConHang'),
    ('MS010', 'Tím', 'ConHang');

-- Insert data into Size
INSERT INTO Size (MaSize, TenSize, TrangThai)
VALUES
    ('SZ001', 'S', 'ConHang'),
    ('SZ002', 'M', 'ConHang'),
    ('SZ003', 'L', 'ConHang'),
    ('SZ004', 'XL', 'ConHang'),
    ('SZ005', 'XXL', 'ConHang'),
    ('SZ006', '28', 'ConHang'),
    ('SZ007', '30', 'ConHang'),
    ('SZ008', '32', 'ConHang'),
    ('SZ009', '34', 'ConHang'),
    ('SZ010', '36', 'ConHang');

-- Insert data into ChatLieu
INSERT INTO ChatLieu (MaChatLieu, TenChatLieu, TrangThai)
VALUES
    ('CL001', 'Polyester', 'ConHang'),
    ('CL002', 'Cotton', 'ConHang'),
    ('CL003', 'Nylon', 'ConHang'),
    ('CL004', 'Spandex', 'ConHang'),
    ('CL005', 'Fleece', 'ConHang'),
    ('CL006', 'Vải dệt kim', 'ConHang'),
    ('CL007', 'Vải dù', 'ConHang'),
    ('CL008', 'Vải thun', 'ConHang'),
    ('CL009', 'Vải len', 'ConHang'),
    ('CL010', 'Vải kaki', 'ConHang');

-- Insert data into DoDay
INSERT INTO DoDay (MaDoDay, TenDoDay, TrangThai)
VALUES
    ('DD001', 'Mỏng', 'ConHang'),
    ('DD002', 'Vừa', 'ConHang'),
    ('DD003', 'Dày', 'ConHang'),
    ('DD004', 'Rất mỏng', 'ConHang'),
    ('DD005', 'Rất dày', 'ConHang'),
    ('DD006', 'Nhẹ', 'ConHang'),
    ('DD007', 'Trung bình', 'ConHang'),
    ('DD008', 'Nặng', 'ConHang'),
    ('DD009', 'Siêu nhẹ', 'ConHang'),
    ('DD010', 'Siêu dày', 'ConHang');

-- Insert data into SanPhamChiTiet
INSERT INTO SanPhamChiTiet (
    MaSPCT, TenSPCT, SoLuong, IdSanPham, IdMauSac, IdSize, IdChatLieu, IdDoDay, IdNhaCungCap,
    NgayTao, GiaNhap, GiaBan, TrangThai, HienThi
) VALUES
      ('SPCT001', 'Quần dài Adidas Originals đen size S', 50, 1, 1, 1, 1, 2, 1, '2024-07-01', 300000, 500000, 'Còn hàng', 'Hien'),
      ('SPCT002', 'Quần dài Adidas Sportswear xanh navy size M', 50, 2, 2, 2, 2, 2, 1, '2024-07-01', 320000, 550000, 'Còn hàng', 'Hien'),
      ('SPCT003', 'Quần dài Adidas Terrex xám size L', 50, 3, 3, 3, 3, 3, 2, '2024-07-01', 350000, 600000, 'Còn hàng', 'Hien'),
      ('SPCT004', 'Quần dài Adidas Performance trắng size XL', 50, 4, 4, 4, 4, 2, 2, '2024-07-01', 330000, 580000, 'Còn hàng', 'Hien'),
      ('SPCT005', 'Quần dài Adidas Essentials xanh dương size XXL', 50, 5, 5, 5, 5, 2, 3, '2024-07-01', 280000, 480000, 'Còn hàng', 'Hien'),
      ('SPCT006', 'Quần dài Adidas Adicolor đỏ size 30', 50, 6, 6, 7, 6, 1, 3, '2024-07-01', 310000, 520000, 'Còn hàng', 'Hien'),
      ('SPCT007', 'Quần dài Adidas Z.N.E. xanh lá size 32', 50, 7, 7, 8, 7, 3, 4, '2024-07-01', 370000, 620000, 'Còn hàng', 'Hien'),
      ('SPCT008', 'Quần dài Adidas 4DFWD cam size 34', 50, 8, 8, 9, 8, 1, 4, '2024-07-01', 400000, 650000, 'Còn hàng', 'Hien'),
      ('SPCT009', 'Quần dài Adidas Primeblue vàng size 36', 50, 9, 9, 10, 9, 2, 5, '2024-07-01', 380000, 630000, 'Còn hàng', 'Hien'),
      ('SPCT010', 'Quần dài Adidas Tiro tím size M', 50, 10, 10, 2, 10, 2, 5, '2024-07-01', 340000, 570000, 'Còn hàng', 'Hien');

-- Insert data into NhanVien
INSERT INTO NhanVien (
    MaNhanVien, HoTen, SoDT, CCCD, NgaySinh, ChucVu, GioiTinh, DiaChi, TaiKhoan, MatKhau, NgayTao, TrangThai
) VALUES
      ('NV001', 'Nguyễn Văn An', '0901234567', '001234567890', '1990-01-01', 'nv', 1, '456 Đường Nguyễn Trãi, Quận 5, TP.HCM', 'nva', '123', '2023-01-01', 1),
      ('NV002', 'Trần Thị Bình', '0912345678', '001234567891', '1992-05-15', 'nv', 0, '789 Đường Lý Thường Kiệt, Quận 10, TP.HCM', 'ttb', '123', '2023-01-01', 1),
      ('NV003', 'Lê Văn Cường', '0923456789', '001234567892', '1988-09-20', 'nv', 1, '101 Đường Cách Mạng Tháng 8, Quận 3, TP.HCM', 'lvc', '123', '2023-01-01', 1),
      ('NV004', 'Phạm Thị Dung', '0934567890', '001234567893', '1995-03-10', 'nv', 0, '202 Đường Nguyễn Văn Cừ, Quận 5, TP.HCM', 'ptd', '123', '2023-01-01', 1),
      ('NV005', 'Hoàng Văn Em', '0945678901', '001234567894', '1993-07-25', 'nv', 1, '303 Đường Võ Văn Tần, Quận 3, TP.HCM', 'hve', '123', '2023-01-01', 1),
      ('NV006', 'Ngô Thị Phương', '0956789012', '001234567895', '1991-11-30', 'nv', 0, '404 Đường Điện Biên Phủ, Quận Bình Thạnh, TP.HCM', 'ntp', '123', '2023-01-01', 1),
      ('NV007', 'Đặng Văn Quang', '0967890123', '001234567896', '1994-02-14', 'nv', 1, '505 Đường Nguyễn Thị Minh Khai, Quận 1, TP.HCM', 'dvq', '123', '2023-01-01', 1),
      ('NV008', 'Mai Thị Hoa', '0978901234', '001234567897', '1989-06-05', 'nv', 0, '606 Đường Trần Hưng Đạo, Quận 1, TP.HCM', 'mth', '123', '2023-01-01', 1),
      ('NV009', 'Trương Văn Khoa', '0989012345', '001234567898', '1996-10-18', 'nv', 1, '707 Đường Lê Hồng Phong, Quận 5, TP.HCM', 'tvk', '123', '2023-01-01', 1),
      ('NV010', 'Lý Thị Lan', '0990123456', '001234567899', '1987-12-22', 'nv', 0, '808 Đường Hai Bà Trưng, Quận 1, TP.HCM', 'ltl', '123', '2023-01-01', 1);

-- Insert data into KhachHang
INSERT INTO KhachHang (
    MaKhachHang, HoTen, GioiTinh, SoDT, DiaChi, NgayTao, NguoiTao
) VALUES
      ('KH000', 'Khách Bán Lẻ', 1, '0969477050', 'Dia CHi Admin', '2024-07-01', 'admin'),
      ('KH001', 'Lê Văn Cường', 1, '0923456789', '101 Đường Cách Mạng Tháng 8, Quận 3, TP. HCM', '2024-07-01', 'admin'),
      ('KH002', 'Phạm Thị Dung', 0, '0934567890', '202 Đường Nguyễn Văn Cừ, Quận 5, TP. HCM', '2024-07-01', 'admin'),
      ('KH003', 'Trần Minh Đức', 1, '0945678901', '303 Đường Lê Đại Hành, Quận 11, TP. HCM', '2024-07-02', 'admin'),
      ('KH004', 'Nguyễn Thị Em', 0, '0956789012', '404 Đường Nguyễn Thị Minh Khai, Quận 1, TP. HCM', '2024-07-02', 'admin'),
      ('KH005', 'Hoàng Văn Phúc', 1, '0967890123', '505 Đường Võ Thị Sáu, Quận 3, TP. HCM', '2024-07-03', 'admin'),
      ('KH006', 'Đặng Thị Giang', 0, '0978901234', '606 Đường Trần Phú, Quận 5, TP. HCM', '2024-07-03', 'admin'),
      ('KH007', 'Bùi Văn Hùng', 1, '0989012345', '707 Đường Nguyễn Đình Chiểu, Quận 3, TP. HCM', '2024-07-04', 'admin'),
      ('KH008', 'Mai Thị Lan', 0, '0990123456', '808 Đường Lý Tự Trọng, Quận 1, TP. HCM', '2024-07-04', 'admin'),
      ('KH009', 'Trương Văn Khánh', 1, '0901234567', '909 Đường Nguyễn Trãi, Quận 5, TP. HCM', '2024-07-05', 'admin'),
      ('KH010', 'Lý Thị Mai', 0, '0912345678', '1010 Đường Điện Biên Phủ, Quận Bình Thạnh, TP. HCM', '2024-07-05', 'admin');

-- Insert data into Voucher
INSERT INTO Voucher (
    MaVoucher, GiaTriVoucher, NgayBatDau, NgayKetThuc, SoLuong, MoTa, TrangThai
) VALUES
      ('VC001', 50000, '2024-07-01', '2024-12-31', 100, 'Giảm giá 50,000đ cho quần dài Adidas', 1),
      ('VC002', 100000, '2024-07-01', '2024-12-31', 50, 'Giảm giá 100,000đ cho đơn hàng từ 1,000,000đ', 2),
      ('VC003', 150000, '2024-07-01', '2024-12-31', 30, 'Giảm giá 150,000đ cho đơn hàng từ 1,500,000đ', 3),
      ('VC004', 200000, '2024-07-01', '2024-12-31', 20, 'Giảm giá 200,000đ cho đơn hàng từ 2,000,000đ', 2),
      ('VC005', 250000, '2024-07-01', '2024-12-31', 10, 'Giảm giá 250,000đ cho đơn hàng từ 2,500,000đ', 2),
      ('VC006', 75000, '2024-07-01', '2024-12-31', 80, 'Giảm giá 75,000đ cho quần dài Adidas Originals', 2),
      ('VC007', 125000, '2024-07-01', '2024-12-31', 40, 'Giảm giá 125,000đ cho đơn hàng từ 1,250,000đ', 2),
      ('VC008', 175000, '2024-07-01', '2024-12-31', 25, 'Giảm giá 175,000đ cho đơn hàng từ 1,750,000đ', 2),
      ('VC009', 225000, '2024-07-01', '2024-12-31', 15, 'Giảm giá 225,000đ cho đơn hàng từ 2,250,000đ', 2),
      ('VC010', 300000, '2024-07-01', '2024-12-31', 5, 'Giảm giá 300,000đ cho đơn hàng từ 3,000,000đ', 2);

-- Insert data into PhuongThucThanhToan
INSERT INTO PhuongThucThanhToan (TenPhuongThucTT, TrangThai)
VALUES
    ('Tiền mặt', 1),
    ('Chuyển khoản', 1);

-- Insert data into HoaDon
INSERT INTO HoaDon (
    MaHD, IDKhachHang, IDNhanVien, IDVoucher, TongGiaTriHoaDon, ThanhToan, IDPhuongThucTT, NgayTao, TrangThai, GhiChu
) VALUES
      ('HD001', 1, 1, 1, 950000, 950000, 1, '2024-07-01', 0, 'Ghi chú cho HD001'),
      ('HD002', 2, 2, NULL, 500000, 500000, 2, '2024-07-02', 0, 'Ghi chú cho HD002'),
      ('HD003', 3, 3, 2, 1450000, 1450000, 1, '2024-07-03', 0, 'Ghi chú cho HD003'),
      ('HD004', 4, 4, NULL, 1100000, 1100000, 2, '2024-07-04', 1, 'Ghi chú cho HD004'),
      ('HD005', 5, 5, 3, 1800000, 1800000, 1, '2024-07-05', 1, 'Ghi chú cho HD005'),
      ('HD006', 6, 6, NULL, 570000, 570000, 2, '2024-07-06', 0, 'Ghi chú cho HD006'),
      ('HD007', 7, 7, 4, 2250000, 2250000, 1, '2024-07-07', 1, 'Ghi chú cho HD007'),
      ('HD008', 8, 8, NULL, 1300000, 1300000, 2, '2024-07-08', 0, 'Ghi chú cho HD008'),
      ('HD009', 9, 9, 5, 2700000, 2700000, 1, '2024-07-09', 1, 'Ghi chú cho HD009'),
      ('HD010', 10, 10, NULL, 630000, 630000, 2, '2024-07-10', 0, 'Ghi chú cho HD010');

-- Insert data into HoaDonCT
INSERT INTO HoaDonCT (IDHoaDon, IDCTSP, DonGia, TrangThai, SoLuong)
VALUES
    (1, 1, 500000, 1, 1),
    (1, 2, 550000, 1, 1),
    (2, 3, 600000, 1, 1),
    (3, 4, 580000, 1, 1),
    (3, 5, 480000, 1, 2),
    (4, 6, 520000, 1, 1),
    (4, 7, 620000, 1, 1),
    (5, 8, 650000, 1, 2),
    (5, 9, 630000, 1, 1),
    (6, 10, 570000, 1, 1),
    (7, 1, 500000, 1, 2),
    (7, 2, 550000, 1, 2),
    (7, 3, 600000, 1, 1),
    (8, 4, 580000, 1, 1),
    (8, 5, 480000, 1, 1),
    (8, 6, 520000, 1, 1),
    (9, 7, 620000, 1, 2),
    (9, 8, 650000, 1, 2),
    (9, 9, 630000, 1, 1),
    (10, 10, 570000, 1, 1);
