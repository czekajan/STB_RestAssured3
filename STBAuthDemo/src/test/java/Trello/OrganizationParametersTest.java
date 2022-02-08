package Trello;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.*;

public class OrganizationParametersTest {

    private final String KEY =  "KEY";
    private final String TOKEN = "TOKEN";

    private static Stream<Arguments> createOrganizationData() {
        return Stream.of(
                Arguments.of("This is display name", "This is description", "this is name", "https://website.pl"),
                Arguments.of("This is display name", "This is description", "this is name", "http://website.pl"),
                Arguments.of("This is display name", "This is description", "thi", "http://website.pl"),
                Arguments.of("This is display name", "This is description", "this_is_name", "http://website.pl"),
                Arguments.of("This is display name", "This is description", "thisisname123", "http://website.pl"),
                Arguments.of("This is display name", "This is description", "thisisname123", "123"));
    }

    private static Stream<Arguments> createOrganizationWithInvalidData() {
        return Stream.of(
                Arguments.of("", "This is description", "this is name", "http.website.pl"),
                Arguments.of(" This is display name", "This is description", "this is name", "website.pl"),
                Arguments.of("This is display name", "This is description", "thi", "www.website.pl"),
                Arguments.of("This is display name", "This is description", "this is name", "http://website.pl"),
                Arguments.of("This is display name", "This is description", "th?", "http://website.pl"),
                Arguments.of("This is display name", "This is description", "THISISNAME", "123"));
    }

    @DisplayName("Create organization with invalid data")
    @ParameterizedTest(name = "Display name: {0}, desc: {1}, name: {2}, website: {3}")
    @MethodSource("createOrganizationWithInvalidData")
    public void createOrganizationWithInvalidData(String displayName, String desc, String name, String website){

        given()
                .spec(reqSpec)
                .queryParam("displayName", displayName)
                .queryParam("desc", desc)
                .queryParam("name", name)
                .queryParam("website", website)
                .when()
                .post("https://api.trello.com/1/organizations")
                .then()
                .statusCode(400);

    }

    @DisplayName("Create organization with valid data")  // opis testu
    @ParameterizedTest(name = "Display name: {0}, desc: {1}, name: {2}, website: {3}")    // test z parametrami
    @MethodSource("createOrganizationData")  // metoda z ktorej beda brane parametry
    public void createOrganization(String displayName, String desc, String name, String website) {


        Response response = given()
                .contentType(ContentType.JSON)
                .queryParam("KEY", KEY)
                .queryParam("TOKEN", TOKEN)
                .queryParam("displayName", displayName)
                .queryParam("desc", desc)
                .queryParam("name", name)
                .queryParam("website", website)
                .when()
                .post("https://api.trello.com/1/organizations")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertThat(json.getString("displayName")).isEqualTo(displayName);

        final String organizationId = json.getString("id");

        given()
                .contentType(ContentType.JSON)
                .queryParam("key", key)
                .queryParam("token", token)
                .when()
                .delete("https://api.trello.com/1/organizations" + "/" + organizationId)
                .then()
                .statusCode(200);
    }

}
