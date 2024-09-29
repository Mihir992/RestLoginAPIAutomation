package testScript;

import com.qa.endpoints.LoginEndpoint;
import com.qa.utils.ExcelUtils;
import com.qa.utils.ExtentReport;
import com.qa.utils.FileUtils;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.io.IOException;

import static com.qa.utils.FileUtils.saveTokenToFile;

public class TestLoginAPI {

    @BeforeClass
    public void setUp(){
        ExtentReport.initReports();
    }

    @DataProvider(name = "loginData")
    public Object[][] loginDataProvider() throws IOException {
        // Call the modified method to get data as String[][]
        return ExcelUtils.getDataFromExcel();
    }

    @Test(dataProvider = "loginData")
    public void testLogin(String email, String password) throws IOException {
        try
        {
            LoginEndpoint loginEndpoint = new LoginEndpoint();
            String token = loginEndpoint.performLogin(email, password);
            System.out.println("Login successful, token: " + token);
            saveTokenToFile(token);
        } catch (AssertionError e) {
            System.err.println("Login failed: " + e.getMessage());
        }
    }

    @AfterClass
    public void tearDown(){
        ExtentReport.flushReports();
    }
}


