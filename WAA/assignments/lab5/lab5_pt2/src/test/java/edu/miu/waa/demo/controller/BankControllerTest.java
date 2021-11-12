package edu.miu.waa.demo.controller;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.restassured.http.ContentType;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BankControllerTest {

    JSONObject request;

    @BeforeEach
    void setUp() {
        RestAssured.port = Integer.valueOf(8080);
        RestAssured.baseURI = "http://localhost";
        RestAssured.basePath = "/banks/";

        request = new JSONObject();
        request.put("accountNumber",1);
        request.put("accountHolder","John");
    }

    @Test
    void createBank() {
        //add
        given()
                .contentType(ContentType.JSON)
                .when()
                .body(request.toJSONString())
                .post();

        //verify
        given()
                .when()
                .get("/"+request.get("accountNumber"))
                .then()
                .statusCode(200);

        //delete
        given()
                .when()
                .delete("/"+request.get("accountNumber"))
                .then()
                .statusCode(200);
    }

    @Test
    void add_get_delete_bank() {
        //add data
        given()
                .contentType(ContentType.JSON)
        .when()
                .body(request.toJSONString())
                .post();

        //delete
        given()
            .when()
            .delete("/"+request.get("accountNumber"))
                .then()
                .statusCode(200);
    }

    @Test
    void getAllBanks() {
        given()
            .relaxedHTTPSValidation("TLSv1.2")
        .when()
            .get()
        .then()
            .contentType(ContentType.JSON)
                .statusCode(200);
    }
}