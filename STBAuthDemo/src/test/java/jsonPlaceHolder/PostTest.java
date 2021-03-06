package jsonPlaceHolder;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import jsonplaceholder.Post;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.*;

public class PostTest {

    // serializacja
    @Test
    public void createPost(){

        // POJO
        Post post = new Post();
        post.setUserId(1);
        post.setTitle("This is tile");
        post.setBody("This is body");

        // POJO -> JSON
        Response response = given()
                .contentType(ContentType.JSON)
                .body(post)
                .when()
                .post("https://jsonplaceholder.typicode.com/posts")
                .then()
                .statusCode(201)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertThat(json.getString("title")).isEqualTo(post.getTitle());
    }


    // deserializacja
    @Test
    public void readPost(){

        // JSON -> POJO
        Post post = given()
                .when()
                .get("https://jsonplaceholder.typicode.com/posts/1")
                .as(Post.class);

        assertThat(post.getTitle()).isEqualTo("sunt aut facere repellat provident occaecati excepturi optio reprehenderit");
    }
}
