import by.examle.rest.dto.Login;
import by.examle.rest.dto.Register;
import by.examle.rest.dto.User;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

public class ReqresRestTest {

    //LIST USERS
    @Test
    public void getListUsers() {
        given().
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                log().ifValidationFails().
        when().
                get("https://reqres.in/api/users?page=2").
        then().
                statusCode(200).
                body("data", not(empty()),
                        "page", equalTo(2));
    }

    // GET SINGLE USER
    @Test
    public void getSingleUser() {
        given().
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                log().ifValidationFails().
        when().
                get("https://reqres.in/api/users/2").
        then().
                statusCode(200).
                body("data.id", not(empty()),
                        "data.id", equalTo(2));
    }

    //GET SINGLE USER NOT FOUND
    @Test
    public void getSingleUserNotFound() {
        given().
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                log().ifValidationFails().
        when().
                get("https://reqres.in/api/users/23").
        then().
                statusCode(404).
                body(equalTo("{}"));
    }

    //GET  /LIST <RESOURCE>
    @Test
    public void getListResource() {
        given().
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                log().ifValidationFails().
        when().
                get("https://reqres.in/api/unknown").
        then().
                statusCode(200).
                body("data", not(empty()),
                        "page", equalTo(1));
    }

    // GET SINGLE <RESOURCE>
    @Test
    public void getSingleResource() {
        given().
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                log().ifValidationFails().
        when().
                get("https://reqres.in/api/unknown/2").
        then().
                statusCode(200).
                body("data.id", not(empty()),
                        "data.id", equalTo(2));
    }

    //GET  SINGLE <RESOURCE> NOT FOUND
    @Test
    public void getSingleResourceNotFound() {
        given().
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                log().ifValidationFails().
        when().
                get("https://reqres.in/api/unknown/23").
        then().
                statusCode(404).
                body(equalTo("{}"));
    }

    // POST /create user
    @Test
    public void createUser() {
        User expectedUser = User.builder()
                .name("morpheus")
                .job("leader")
                .build();

        User postActualUser = given().
                                    contentType(ContentType.JSON).
                                    accept(ContentType.JSON).
                                    body(expectedUser).
                                    log().ifValidationFails().
                            when().
                                    post("https://reqres.in/api/users").
                            then().
                                    statusCode(201).
                                    extract().
                                    body().as(User.class);
        assertCreateUser(expectedUser, postActualUser);
    }

    // PUT/Update user
    @Test
    public void updateUserPut() {
        User expectedUser = User.builder()
                .name("morpheus")
                .job("zion resident")
                .build();

        User putActualUser = given().
                                    contentType(ContentType.JSON).
                                    accept(ContentType.JSON).
                                    body(expectedUser).
                                    log().ifValidationFails().
                            when().
                                    put("https://reqres.in/api/users/2").
                            then().
                                    statusCode(200).
                                    extract().
                                    body().as(User.class);
        assertUpdateUser(expectedUser, putActualUser);
    }

    // PATCH/Update
    @Test
    public void updateUserPatch() {
        User expectedUser = User.builder()
                .name("morpheus")
                .job("zion resident")
                .build();

        User putActualUser = given().
                                    contentType(ContentType.JSON).
                                    accept(ContentType.JSON).
                                    body(expectedUser).
                                    log().ifValidationFails().
                            when().
                                    patch("https://reqres.in/api/users/2").
                            then().
                                    statusCode(200).
                                    extract().
                                    body().as(User.class);
        assertUpdateUser(expectedUser, putActualUser);
    }

    // Delete user
    @Test
    public void DeleteUser() {
        given().
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                log().ifValidationFails().
        when().
                delete("https://reqres.in/api/users/2").
        then().
                statusCode(204);
    }

    //POST REGISTER - SUCCESSFUL
    @Test
    public void registerWithValidData() {
        Register expectedRegister = Register.builder()
                .email("eve.holt@reqres.in")
                .password("pistol")
                .build();

        Register postActualRegister = given().
                                            contentType(ContentType.JSON).
                                            accept(ContentType.JSON).
                                            body(expectedRegister).
                                            log().ifValidationFails().
                                    when().
                                            post("https://reqres.in/api/register").
                                    then().
                                            statusCode(200).
                                            extract().
                                            body().as(Register.class);
        assertRegister(postActualRegister);
    }

    //POST REGISTER - UNSUCCESSFUL
    @Test
    public void registerWithInvalidData() {
        Register expectedRegister = Register.builder()
                .email("eve.holt@reqres.in")
                .password("")
                .build();
        given().
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                body(expectedRegister).
                log().ifValidationFails().
        when().
                post("https://reqres.in/api/register").
        then().
                statusCode(400).
                body("error", equalTo("Missing password")).
                log().all();
    }

    //POST LOGIN - SUCCESSFUL
    @Test
    public void loginWithValidData() {
        Login expectedLogin = Login.builder()
                .email("eve.holt@reqres.in")
                .password("cityslicka")
                .build();

        Login postActualLogin = given().
                                        contentType(ContentType.JSON).
                                        accept(ContentType.JSON).
                                        body(expectedLogin).
                                        log().ifValidationFails().
                                when().
                                        post("https://reqres.in/api/login").
                                then().
                                        statusCode(200).
                                        extract().
                                        body().as(Login.class);
        assertLogin(postActualLogin);
    }

    //POST LOGIN - UNSUCCESSFUL
    @Test
    public void loginWithInvalidData() {
        Login expectedLogin = Login.builder()
                .email("peter@klaven")
                .password("")
                .build();

        given().
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                body(expectedLogin).
                log().ifValidationFails().
        when().
                post("https://reqres.in/api/login").
        then().
                statusCode(400).
                body("error", equalTo("Missing password")).
                log().all();
    }

    //DELAYED RESPONSE
    @Test
    public void getDelayedResponse() {
        given().
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                log().ifValidationFails().
        when().
                get("https://reqres.in/api/users?delay=3").
        then().
                statusCode(200).
                body("data", not(empty()),
                        "page", equalTo(1));
    }

    private void assertCreateUser(User expectedUser, User postActualUser) {
        assertThat(postActualUser).as("The user in response doesn't match expected user")
                .usingRecursiveComparison()
                .ignoringFields("id", "createdAt")
                .isEqualTo(expectedUser);
        assertThat(postActualUser.getId()).as("The \"id\" is not generated")
                .isNotNull()
                .isNotEqualTo(0);
    }
    private void assertUpdateUser(User expectedUser, User putActualUser) {
        assertThat(putActualUser).as("The user in response doesn't match expected user")
                .usingRecursiveComparison()
                .ignoringFields("updatedAt")
                .isEqualTo(expectedUser);
    }

    private void assertRegister(Register postActualRegister) {
        assertThat(postActualRegister.getId()).as("The \"id\" is not generated")
                .isNotNull()
                .isNotEqualTo(0);
        assertThat(postActualRegister.getToken()).as("The \"token\" is not generated")
                .isNotNull()
                .isNotEqualTo(0);
    }

    private void assertLogin(Login postActualLogin) {
        assertThat(postActualLogin.getToken()).as("The \"token\" is not generated")
                .isNotNull()
                .isNotEqualTo(0);
    }
}