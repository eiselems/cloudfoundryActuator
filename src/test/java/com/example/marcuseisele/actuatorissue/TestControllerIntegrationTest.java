package com.example.marcuseisele.actuatorissue;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import org.apache.http.protocol.HTTP;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestControllerIntegrationTest {

    @LocalServerPort
    int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void testEntity() {
        RestAssured
                .given()
                    .header(new Header("Authorization", BasicAuthEncoder.createBasicAuthHeaderValue("user", "password")))
                    .header(HTTP.USER_AGENT, "Custom User-Agent (android)")
                .when()
                    .get("api/v1/param1/path/param2/entity")
                .then()
                    .statusCode(200)
                    .contentType(MediaType.IMAGE_PNG_VALUE)
                    .header("Cache-Control", Matchers.is("max-age=600"))
                    .header("Pragma", Matchers.isEmptyOrNullString());
    }
}
