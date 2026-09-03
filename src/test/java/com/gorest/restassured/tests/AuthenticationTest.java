package com.gorest.restassured.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasItems;

import org.testng.annotations.Test;

import com.gorest.restassured.base.BaseTest;


/**
 * Test Suite: Authentication Testing
 * 			
 * API: a) POST /public/v2/users
 * 		b) GET /public/v2/users/id
 * 
 * Verifies GoRest correctly 
 * 	a) rejects invalid user creation requests with missing/invalid token
 * 		and returns appropriate 401 status codes with descriptive error messages.
 * 	b) allows querying the users without token
 */

public class AuthenticationTest extends BaseTest{
	
	/**
     * TC_USER_N013
     * Scenario: Attempt to create a user without token.
     * Expected: API returns 401 Unauthorized.
     * Business rule reference: A new user, adhering to strict schema constraints,  can be created with a proper authentication..
     */
	@Test
	public void testMissingToken() {
		given()
			.spec(unauthenticatedRequestSpec)
			.body("{ \"name\": \"Tenali Ramakrishna\",  \"email\": \"tenali9@example.com\", \"gender\": \"male\", \"status\": \"active\" }")
		.when()
			.post("/users")
		.then()
			.statusCode(401)
			.body("message", equalTo("Authentication failed"));
			
	}
	/**
     * TC_USER_N014
     * Scenario: Attempt to create a user with invalid token.
     * Expected: API returns 401 Unauthorized.
     * Business rule reference: A new user, adhering to strict schema constraints,  can be created with a proper authentication.
     */
	@Test
	public void testInvalidToken() {
		given()
			.spec(unauthenticatedRequestSpec)
			.header("Authorization", "Bearer " + "abcdedfghijklmnopqrstuvwxyz")
			.body("{ \"name\": \"Tenali Ramakrishna\",  \"email\": \"tenali9@example.com\", \"gender\": \"male\", \"status\": \"active\" }")
		.when()
			.post("/users")
		.then()
			.statusCode(401)
			.body("message", equalTo("Invalid token"));
			
	}
	/**
     * TC_USER_N015
     * Scenario: Attempt to get the user details with no token.
     * Expected: API returns 200 Ok.
     * Business rule reference: User details can be queried without any authentication in GoRest api.
     */
	public void testGetUsers() {
		given()
			.spec(unauthenticatedRequestSpec)
		.when()
			.get("/users")
		.then()
			.statusCode(200)
			.body("size()", greaterThanOrEqualTo(1)).body("status", hasItems("active", "inactive"))
			.body("[0].name", equalTo("Dharitri Varman"));
	}

}
