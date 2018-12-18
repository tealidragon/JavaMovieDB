import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class Collections {

    @After
    public void
    restassured_reset_after_each_test() {RestAssured.reset();}

    @Before
    public void
    given_rest_assured_is_configured() {
        RestAssured.baseURI = "https://api.themoviedb.org/3/collection/";
    }

    //Save the status_code and status_messages to enums

    /*Get Details*/
    @Test
    public void
    get_details_200_success_default_lang () {

        Map<String, Object> parameters = new HashMap<String, Object>();

        parameters.put("api_key", Authentication.api_key);

        ResponseBody responseBody = given().params(parameters).get("10");

        assertEquals(200, ((Response) responseBody).statusCode());

    }

    @Test
    public void
    get_details_200_success_language_check_spanish () {

        Map<String, Object> parameters = new HashMap<String, Object>();

        parameters.put("language", "es-ME");
        parameters.put("api_key", Authentication.api_key);

        ResponseBody responseBody = given().params(parameters).get("10");

        assertEquals(200, ((Response) responseBody).statusCode());

    }

    @Test
    public void
    get_details_404_failed_invalid_param_type () {

        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("api_key",Authentication.api_key);

        ResponseBody responseBody = given().param("collection_id", 7).param("api_key", Authentication.api_key).get("seven");
        assertEquals(404, ((Response) responseBody).statusCode());

    }

    @Test
    public void
    get_details_404_not_found () {

        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("collection_id", String.valueOf(7));
        parameters.put("api_key",Authentication.api_key);

        ResponseBody responseBody = given().param("collection_id", 7).param("api_key", Authentication.api_key).get();
        assertEquals(404, ((Response) responseBody).statusCode());
        assertEquals(34, ((Response) responseBody).getBody().jsonPath().get("status_code"));
        assertEquals("The resource you requested could not be found.", ((Response) responseBody).getBody().jsonPath().get("status_message"));
    }


    @Test
    public void
    get_details_401_not_authorized_no_api_key () {

        ResponseBody responseBody = given().get();
        assertEquals(401, ((Response) responseBody).statusCode());
        assertEquals(7, ((Response) responseBody).getBody().jsonPath().get("status_code"));
        assertEquals("Invalid API key: You must be granted a valid key.", ((Response) responseBody).getBody().jsonPath().get("status_message"));

    }

    @Test
    public void
    get_details_401_not_authorized_invalid_api_key () {

        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("api_key", "abcd123");


        ResponseBody responseBody = given().params(parameters).get();
        assertEquals(401, ((Response) responseBody).statusCode());
        assertEquals(7, ((Response) responseBody).getBody().jsonPath().get("status_code"));
        assertEquals("Invalid API key: You must be granted a valid key.", ((Response) responseBody).getBody().jsonPath().get("status_message"));

    }

    /*Get Images*/
    @Test
    public void
    get_images_200_success () {
        Map<String, Object> parameters = new HashMap<String, Object>();

        parameters.put("api_key", Authentication.api_key);

        ResponseBody responseBody = given().params(parameters).get("10/images");

        assertEquals(200, ((Response) responseBody).statusCode());
    }

    @Test
    public void
    get_images_404_resource_not_found () {
        Map<String, Object> parameters = new HashMap<String, Object>();

        parameters.put("api_key", Authentication.api_key);

        ResponseBody responseBody = given().params(parameters).get("11/images");

        assertEquals(404, ((Response) responseBody).statusCode());

    }




}
