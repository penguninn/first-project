/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daipc.IO;

import com.daipc.model.SanPham;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 *
 * @author DaiPc
 */
public class Export {

    public void exportExcel(DefaultTableModel modeltb, String filePath) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Danh sách sản phẩm");
        int rowNum = 0;

        int columnCount = modeltb.getColumnCount();

        Row headerRow = sheet.createRow(rowNum++);
        for (int col = 0; col < columnCount; col++) {
            headerRow.createCell(col).setCellValue(modeltb.getColumnName(col));
        }

        int rowCount = modeltb.getRowCount();
        for (int row = 0; row < rowCount; row++) {
            Row dataRow = sheet.createRow(rowNum++);
            for (int col = 0; col < columnCount; col++) {
                Object cellValue = modeltb.getValueAt(row, col);
                dataRow.createCell(col).setCellValue(String.valueOf(cellValue));
            }
        }
        
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            workbook.write(outputStream);
            System.out.println("File đã được lưu vào desktop.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void exportPdf(DefaultTableModel modeltb, String filePath) {
        // Tạo một document PDF mới
        try (PdfWriter writer = new PdfWriter(filePath)) {
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Tạo bảng trong PDF
            int columnCount = modeltb.getColumnCount();
            Table table = new Table(columnCount);

            // Thêm tiêu đề cột
            for (int col = 0; col < columnCount; col++) {
                table.addHeaderCell(new Paragraph(modeltb.getColumnName(col)));
            }

            // Thêm dữ liệu từ DefaultTableModel
            int rowCount = modeltb.getRowCount();
            for (int row = 0; row < rowCount; row++) {
                for (int col = 0; col < columnCount; col++) {
                    Object cellValue = modeltb.getValueAt(row, col);
                    table.addCell(new Paragraph(String.valueOf(cellValue)));
                }
            }

            // Thêm bảng vào document
            document.add(table);

            // Đóng document
            document.close();
            System.out.println("File đã được lưu vào desktop.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
