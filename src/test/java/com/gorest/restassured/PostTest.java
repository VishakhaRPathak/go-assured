package com.gorest.restassured;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

import org.testng.annotations.Test;

public class PostTest {
	@Test
	public void testGetPost() {
		given()
			.baseUri("https://gorest.co.in/public/v2")
		.when()
			.get("/posts")
		.then()
			.statusCode(200)
			.body("size()", greaterThanOrEqualTo(1));
		
	}

}
