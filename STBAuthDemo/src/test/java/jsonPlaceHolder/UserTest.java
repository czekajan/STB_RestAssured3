package jsonPlaceHolder;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import jsonplaceholder.AddressLombok;
import jsonplaceholder.CompanyLombok;
import jsonplaceholder.GeoLombok;
import jsonplaceholder.UserLombok;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class UserTest {

    @Test
    public void createNewUser(){

        UserLombok user = new UserLombok();
        user.setName("Anna Wait");
        user.setUsername("Ania");
        user.setEmail("ania@test.pl");
        user.setPhone("123456789");
        user.setWebsite("www.ankawait.pl");

        GeoLombok geo = new GeoLombok();
        geo.setLat("-37.3259");
        geo.setLng("81.1450");

        AddressLombok address = new AddressLombok();
        address.setStreet("Zielona");
        address.setSuite("Nr 3");
        address.setCity("Nibylandia");
        address.setZipcode("88555");
        address.setGeolombok(geo);

        user.setAddress(address);

        CompanyLombok company = new CompanyLombok();
        company.setName("Anka Company");
        company.setCatchPhrase("Multi-layered client-server neural-net");
        company.setBs("harness real-time e-markets");

        user.setCompany(company);


        Response response = given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("https://jsonplaceholder.typicode.com/users")
                .then()
                .statusCode(201)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        Assertions.assertThat(json.getString("name")).isEqualTo(user.getName());

    }

}
