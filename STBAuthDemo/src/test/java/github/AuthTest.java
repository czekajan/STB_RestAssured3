package github;

import org.junit.Test;

import static io.restassured.RestAssured.given;

public class AuthTest {

    private static final String TOKEN = "TOKEN";

    @Test
    public void basicAuth(){

        given()
                .auth()
                .preemptive()
                .basic("nazwa_githuba", "haslo")
                .when()
                .get("https://api.github.com/users")
                .then()
                .statusCode(200);
    }

    @Test
    public void bearerToken(){

        given()
                .headers("Authorizations", "Bearer " + TOKEN)
                .get("https://api.github.com/users")
                .then()
                .statusCode(200);
    }

    @Test
    public void oAuth2(){

        given()
                .auth()
                .oauth2(TOKEN)
                .get("https://api.github.com/users")
                .then()
                .statusCode(200);
    }

}
