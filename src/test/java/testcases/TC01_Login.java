package testcases;

import io.qameta.allure.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
@Epic("Login API Regression Testing using TestNG")
@Feature("Verify Login Scenarios with Valid and Invalid data")
public class TC01_Login extends TestBase{
    @Test
    @Story("POST Request")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description : Verify Login with Valid email and password")
    public void loginWithValidEmailPassword_P() {

        JSONObject data = new JSONObject();

        data.put("email", "merchant@foodics.com");
        data.put("password", "123456");
        data.put("token", "Lyz22cfYKMetFhKQybx5HAmVimF1i0xO");

        // GIVEN
        Response response=given()
                .log().all()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .body(data.toString())

                // WHEN
                .when()
                .post("/login")

                // THEN
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();
        Assert.assertFalse(response.jsonPath().get("token").toString().equals(null));
    }
    @Test
    @Story("POST Request")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description : Verify Login with Invalid email and Valid password")
    public void loginWithInvalidEmailValidPassword_N() {

        JSONObject data = new JSONObject();

        data.put("email", "test@foodics.com");
        data.put("password", "123456");
        data.put("token", "Lyz22cfYKMetFhKQybx5HAmVimF1i0xO");

        // GIVEN
        given()
                .log().all()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .body(data.toString())

                // WHEN
                .when()
                .post("/login")

                // THEN
                .then()
                .log().all()
                .statusCode(500);
    }
    @Test
    @Story("POST Request")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description : Verify Login with Valid email and password and Invalid token")
    public void loginWithValidEmailPasswordInvalidToken_N() {

        JSONObject data = new JSONObject();

        data.put("email", "merchant@foodics.com");
        data.put("password", "123456");
        data.put("token", "Lyz22cfYKMetFhKQybx5HAmVimF1i1x1");

        // GIVEN
        given()
                .log().all()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .body(data.toString())

                // WHEN
                .when()
                .post("/login")

                // THEN
                .then()
                .log().all()
                .statusCode(302)
                .assertThat().extract().response().getBody().toString().contains("Redirecting to https://pay2.foodics.dev");
            }
}