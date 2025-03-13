/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daipc.IO;

import com.daipc.model.SanPham;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author DaiPc
 */
public class Import {

    public List<SanPham> importData(String filePath) {
        try {

            List<SanPham> sanPhamList = new ArrayList<>();
            FileInputStream fis = new FileInputStream(filePath);
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }
                SanPham sanPham = new SanPham();

                sanPham.setMaSP(row.getCell(0).getStringCellValue());
                sanPham.setTenSP(row.getCell(1).getStringCellValue());
                sanPham.setMoTa(row.getCell(2).getStringCellValue());
                sanPhamList.add(sanPham);
            }
            workbook.close();
            fis.close();

            return sanPhamList;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
