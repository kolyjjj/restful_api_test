package com.thoughtworks.apitest;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import org.junit.Before;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class DemoTest {
    @Before
    public void setUp() throws Exception {
        RestAssured.port = 80;
    }
    
    @Test
    public void should_assert_json_field_from_response_from_an_api_server() {
        RestAssured.baseURI = "http://ip.jsontest.com";
        get("/").then().assertThat().body("ip", is("171.216.170.10"));
    }

    @Test
    public void should_get_response_from_api(){
        RestAssured.baseURI = "http://echo.jsontest.com/key/value/one/two";
        Response response = get("/").then().extract().response();
        ResponseBody body = response.getBody();
        String result = body.asString();
        assertThat(result, is("{\n   \"one\": \"two\",\n   \"key\": \"value\"\n}\n"));
    }

    @Test
    public void should_compare_with_an_external_json_file(){
        RestAssured.baseURI = "http://date.jsontest.com";
        get("/").then().assertThat().body(matchesJsonSchemaInClasspath("date_jsontest_com_get.json"));
    }
}
