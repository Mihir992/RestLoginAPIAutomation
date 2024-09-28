package com.qa.utils;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelUtils {

    private static final String EXCEL_FILE_PATH = "src/test/resources/LoginData.xlsx"; // Update with your Excel file path

    public static String[][] getDataFromExcel() throws IOException {
        try (FileInputStream fis = new FileInputStream(EXCEL_FILE_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            int rowCount = sheet.getPhysicalNumberOfRows() - 1; // Exclude header
            int colCount = sheet.getRow(0).getPhysicalNumberOfCells(); // Get number of columns from the header row

            String[][] data = new String[rowCount][colCount]; // Adjusted to support multiple columns

            for (int i = 1; i <= rowCount; i++) { // Start from 1 to skip header
                Row row = sheet.getRow(i);
                if (row != null) {
                    for (int j = 0; j < colCount; j++) { // Iterate through each column
                        data[i - 1][j] = row.getCell(j).getStringCellValue(); // Get cell value
                    }
                }
            }
            return data;
        }
    }
}
