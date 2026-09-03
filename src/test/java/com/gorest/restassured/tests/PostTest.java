package com.gorest.restassured.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

import org.testng.annotations.Test;

import com.gorest.restassured.base.BaseTest;

public class PostTest extends BaseTest{
	@Test
	/**
     * TC_USER_N01
     * Scenario: Attempt to get the posts.
     * Expected: API returns 200 Ok.
     * Business rule reference: Read operation (Posts) can be executed anonymously.
     */
	public void testGetPost() {
		given()
			.spec(unauthenticatedRequestSpec)
		
		.when()
			.get("/posts")
		.then()
			.statusCode(200)
			.body("size()", greaterThanOrEqualTo(1));
		
	}

}
