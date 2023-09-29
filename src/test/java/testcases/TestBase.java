package testcases;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeSuite;

public class TestBase {

    @BeforeSuite
    public void setup(){
        // define base url
        RestAssured.baseURI = "https://pay2.foodics.dev/cp_internal";

        //ByPass SSL Certificate
        RestAssured.useRelaxedHTTPSValidation();
    }

}
