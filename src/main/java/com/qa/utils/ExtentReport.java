package com.qa.utils;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import java.io.File;
import java.nio.file.Paths;

public class ExtentReport implements ITestListener{
    public ExtentSparkReporter sparkReporter;
    public static ExtentReports extent;
    public ExtentTest test;

    public void initReports() {
        // Specify the folder path where the report should be stored
        String folderPath = "reports"; // Update this path as needed
        File directory = new File(folderPath);
        if (!directory.exists()) {
            directory.mkdirs(); // Create the directory if it doesn't exist
        }
        String reportPath = Paths.get(folderPath, "ExtentReport.html").toString();
        sparkReporter=new ExtentSparkReporter(reportPath);
        sparkReporter.config().setDocumentTitle("RestLoginAPIAutomation"); // Title of report
        sparkReporter.config().setReportName("Rest Login API"); // name of the report
        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports(); // Fix: use the class-level variable
        extent.attachReporter(sparkReporter);
    }
    public void onTestSuccess(ITestResult result)
    {
        test=extent.createTest(result.getName());
        //test.assignCategory(result.getMethod().getGroups());
        test.createNode(result.getName());
        test.log(Status.PASS, "Test Passed");
    }

    public void onTestFailure(ITestResult result)
    {
        test=extent.createTest(result.getName());
        test.createNode(result.getName());
        //test.assignCategory(result.getMethod().getGroups());
        test.log(Status.FAIL, "Test Failed");
        test.log(Status.FAIL, result.getThrowable().getMessage());
    }

    public void onTestSkipped(ITestResult result)
    {
        test=extent.createTest(result.getName());
        test.createNode(result.getName());
       // test.assignCategory(result.getMethod().getGroups());
        test.log(Status.SKIP, "Test Skipped");
        test.log(Status.SKIP, result.getThrowable().getMessage());
    }

    // Method to flush reports
    public static void flushReports() {
        if (extent != null) {
            extent.flush();
        } else {
            System.err.println("ExtentReports is not initialized. Call initReports() first.");
        }
    }

    // Method to be called at the end of tests
    public void onFinish(ITestContext testContext) {
        flushReports();
    }
}
