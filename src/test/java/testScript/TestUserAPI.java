package testScript;

import com.qa.endpoints.LoginEndpoint;
import com.qa.payload.UserPayload;
import com.qa.utils.ExcelUtils;
import com.qa.utils.ExtentReport;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.qa.endpoints.UserEndpoint.createUser;
import static com.qa.endpoints.UserEndpoint.updateUser;
import static com.qa.utils.ExcelUtils.updateUserDataInExcel;
import static com.qa.utils.ExtentReport.flushReports;

public class TestUserAPI {

    private int lastCreatedUserId;
    UserPayload userPayload = new UserPayload();
    ExtentReport report = new ExtentReport();

    @BeforeClass
    public void setUp() {
        report.initReports();
    }

    @DataProvider(name = "UserData")
    public Object[][] getUserDataProvider() throws IOException {
        // Call the modified method to get data as String[][]
        return ExcelUtils.getUserDataFromExcel();
    }

    @DataProvider(name = "UpdateUserData")
    public Object[][] updateJobDataProvider() throws IOException {
        // Call the modified method to get data as String[][]
        return new Object[][]{
                {1, "zion resident"} // rowIndex, Job
        };
    }

    @Test(dataProvider = "UserData")
    public void testPostUser(String Name, String Job) throws IOException {
        try {
            userPayload.setName(Name);
            userPayload.setJob(Job);
            // Create the user and store the user ID
            lastCreatedUserId = Integer.parseInt(createUser(userPayload));
            System.out.println("Created user with ID: " + lastCreatedUserId);
           } catch (AssertionError e) {
            System.err.println("Data not created: " + e.getMessage());
        }
    }

    @Test(dataProvider = "UpdateUserData",dependsOnMethods = "testPostUser")
    public void testUpdateUser(int rowIndex,String Job) throws IOException {
        try {
            //userPayload.setName(Name);
            userPayload.setJob("zion resident");
            // Update the user using the ID from the previous test
            Response updateResponse = updateUser(lastCreatedUserId, userPayload);
            System.out.println("Update Response: " + updateResponse.asString());
            // Optionally, assert the update response
            assert updateResponse.getStatusCode() == 200;
            // Update the Excel file with new user data
            // Update the Excel file with new user data at the specified row index
            updateUserDataInExcel(0, userPayload.getName(), Job);
        } catch (AssertionError e) {
            System.err.println("Data not updated for userId "+lastCreatedUserId+" + e.getMessage()");
        }
    }

    @AfterClass
    public void tearDown() {
        flushReports(); // Call flushReports to ensure the report is saved
    }

}
