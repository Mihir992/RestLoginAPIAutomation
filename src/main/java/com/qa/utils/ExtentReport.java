package com.qa.utils;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import java.io.File;
import java.nio.file.Paths;
import org.testng.ITestListener;

public class ExtentReport implements ITestListener{
    private static ExtentReports extent;
    private static ExtentTest test;

    public static void initReports() {
        // Specify the folder path where the report should be stored

        String folderPath = "reports"; // Update this path as needed
        File directory = new File(folderPath);
        if (!directory.exists()) {
            directory.mkdirs(); // Create the directory if it doesn't exist
        }
        String reportPath = Paths.get(folderPath, "ExtentReport.html").toString();

        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        extent = new ExtentReports();
        extent.attachReporter(spark);
    }

    public static void startTest(String testName) {
        test = extent.createTest(testName);
    }

    public static void log(String message) {
        test.info(message);
    }

    public static void flushReports() {
        extent.flush();
    }
}
