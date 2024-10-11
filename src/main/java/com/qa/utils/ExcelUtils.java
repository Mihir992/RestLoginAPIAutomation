package com.qa.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelUtils {

    private static final String EXCEL_FILE_PATH = "src/test/resources/LoginData.xlsx"; // Update with your Excel file path

    //Get Login Data From Excel File
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

    //Get User Data From Excel File
    public static String[][] getUserDataFromExcel() throws IOException {
        try (FileInputStream fis = new FileInputStream(EXCEL_FILE_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(1);
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

    //Write updated user data into excel file
    public static void updateUserDataInExcel(int rowIndex, String name, String job) throws IOException {
        try (FileInputStream fis = new FileInputStream(EXCEL_FILE_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(1); // Get the appropriate sheet
            int rowCount = sheet.getPhysicalNumberOfRows();

            Row row = sheet.getRow(rowIndex); // Get the specific row

            if (row != null) {
                // Update the name and job title in the appropriate columns
                Cell nameCell = row.getCell(0); // Assuming name is in the first column
                if (nameCell == null) {
                    nameCell = row.createCell(0); // Create the cell if it doesn't exist
                }
                nameCell.setCellValue(name); // Update name

                Cell jobCell = row.getCell(1); // Assuming job title is in the second column
                if (jobCell == null) {
                    jobCell = row.createCell(1); // Create the cell if it doesn't exist
                }
                jobCell.setCellValue(job); // Update job
            }    // Write changes back to the Excel file
            try (FileOutputStream fos = new FileOutputStream(EXCEL_FILE_PATH)) {
                    workbook.write(fos);
            }
        }
    }
}
