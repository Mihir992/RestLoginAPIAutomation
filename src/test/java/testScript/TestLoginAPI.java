package testScript;

import com.qa.endpoints.LoginEndpoint;
import com.qa.utils.ExcelUtils;
import com.qa.utils.FileUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.io.IOException;

import static com.qa.utils.FileUtils.saveTokenToFile;

public class TestLoginAPI {
    @DataProvider(name = "loginData")
    public Object[][] loginDataProvider() throws IOException {
        // Call the modified method to get data as String[][]
        return ExcelUtils.getDataFromExcel();
    }

    @Test(dataProvider = "loginData")
    public void testLogin(String username, String password) throws IOException {
        LoginEndpoint loginEndpoint = new LoginEndpoint();
        String token = loginEndpoint.performLogin(username, password);
       // Assert.assertNotNull(token, "Token should not be null");
        // Save the token to a file
        saveTokenToFile(token);
    }
}


