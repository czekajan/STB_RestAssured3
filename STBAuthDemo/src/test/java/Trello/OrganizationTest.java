package Trello;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.*;

public class OrganizationTest {

    private final String KEY =  "KEY";
    private final String TOKEN = "TOKEN";


    @Test
    public void createOrganization(){

        Organization organization = new Organization();
        organization.setDisplayName("This is display name");
        organization.setDesc("This is description");
        organization.setName("This is name");
        organization.setWebsite("https://website.pl");


        Response response = given()
                .contentType(ContentType.JSON)
                .queryParam("KEY", KEY)
                .queryParam("TOKEN", TOKEN)
                .queryParam("displayName", organization.getDisplayName())
                .queryParam("desc", organization.getDesc())
                .queryParam("name", organization.getName())
                .queryParam("website", organization.getWebsite())
                .when()
                .post("https://api.trello.com/1/organizations")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertThat(json.getString("displayName")).isEqualTo(organization.getDisplayName());

        final String organizationId = json.getString("id");

        given()
                .contentType(ContentType.JSON)
                .queryParam("KEY", KEY)
                .queryParam("TOKEN", TOKEN)
                .when()
                .delete("https://api.trello.com/1/organizations" + "/" + organizationId)
                .then()
                .statusCode(200);
    }

}
