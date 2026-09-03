package com.gorest.restassured.tests;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import com.gorest.restassured.base.BaseTest;

/**
 * Test Suite: Validation Testing
 * 			a) User Creation - Negative Scenarios
 * 			b) Operating Non existing user
 * API: a) POST /public/v2/users
 * 		b) PUT /public/v2/users/invalid_id
 * 		   DELETE /public/v2/users/invalid_id
 * 		   GET /public/v2/users/invalid_id
 * 
 * Verifies GoRest correctly rejects invalid user creation requests and non existing user operations
 * and returns appropriate 422/404 status codes with descriptive error messages.
 */
public class NegativeTest extends BaseTest{
	
	
	/**
     * TC_USER_N08
     * Scenario: Attempt to create a user without providing the required 'email' field.
     * Expected: API returns 422 Unprocessable Entity with an error indicating 
     *           'email' can't be blank.
     * Business rule reference: Email is a mandatory field per GoRest API docs.
     */
	@Test
	public void testCreateUserWithEmailMissing() {
		given()
			.spec(authenticatedRequestSpec)
			.body("{ \"name\": \"Tenali Ramakrishna\", \"gender\": \"male\", \"status\": \"active\" }")
		.when()
			.post("/users")
		.then()
			.statusCode(422)
			.body("[0].field", equalTo("email"))
			.body("[0].message", equalTo("can't be blank"));
			
	}
	/**
     * TC_USER_N09
     * Scenario: Attempt to create a user with invalid gender format.
     * Expected: API returns 422 Unprocessable Entity with an error indicating 
     *           'gender' can't be blank, can be male or female.
     * Business rule reference: Gender can contain only 'male' or 'female' as value per GoRest API docs.
     */
	@Test
	public void testCreateUserWithInvalidGender() {
		given()
			.spec(authenticatedRequestSpec)
			.body("{ \"name\": \"Tenali Ramakrishna\",  \"email\": \"tenali8@example.com\", \"gender\": 1234, \"status\": \"active\" }")
		.when()
			.post("/users")
		.then()
			.statusCode(422)
			.body("[0].field", equalTo("gender"))
			.body("[0].message", equalTo("can't be blank, can be male of female"));
			
	}
	
	/**
     * TC_USER_N010
     * Scenario: Attempt to create a duplicate user.
     * Expected: API returns 422 Unprocessable Entity with an error indicating 
     *           'gender' can't be blank, can be male or female.
     * Business rule reference: Email address for a user should be unique.
     */
	@Test
	public void testCreateUserWithDuplicateEmail() {
		given()
			.spec(authenticatedRequestSpec)
			.body("{ \"name\": \"Tenali Ramakrishna\",  \"email\": \"tenali@example.com\", \"gender\": \"male\", \"status\": \"active\" }")
		.when()
			.post("/users")
		.then()
			.statusCode(422)
			.body("[0].field", equalTo("email"))
			.body("[0].message", equalTo("has already been taken"));
			
	}
	/**
     * TC_USER_N011
     * Scenario: Attempt to update non existing user.
     * Expected: API returns 404 Not Found with an error indicating 
     *           user record does not exist.
     * Business rule reference: Operations on non existing user should be handled gracefully with proper message - Resource not found.
     */
	@Test
	public void testUpdateNonExistingUser() {
		given()
			.spec(authenticatedRequestSpec)
			.body("{ \"name\": \"Gangu Teli\", \"status\": \"inactive\" }")
		.when()
			.put("/users/1")
		.then()
			.statusCode(404)
			.body("message", equalTo("Resource not found"));
	}
	/**
     * TC_USER_N012
     * Scenario: Attempt to delete non existing user.
     * Expected: API returns 404 Not Found with an error indicating 
     *           user record does not exist.
     * Business rule reference: Operations on non existing user should be handled gracefully with proper message - Resource not found.
     */
	@Test
	public void testDeleteNonExistingUser() {
		given()
			.spec(authenticatedRequestSpec)
		
		.when()
			.delete("/users/1")
		.then()
			.statusCode(404)
			.body("message", equalTo("Resource not found"));
	}
}
