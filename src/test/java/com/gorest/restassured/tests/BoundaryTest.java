package com.gorest.restassured.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import com.gorest.restassured.base.BaseTest;
import com.gorest.restassured.pojo.User;

/**
 * Test Suite: Boundary Edge Case Testing
 * 			
 * API: POST /public/v2/users
 *  
 * Verifies GoRest correctly handles Name field for empty string, with 200 characters, with 201 characters, with apostrophe
 */
public class BoundaryTest extends BaseTest{
	
	
	/**
     * TC_USER_N019
     * Scenario: Attempt to create user with empty string fields.
     * Expected: API returns 422 Unprocessable Entity with an error indicating
     * 			name can't be blank.
     * Business rule reference: Name is required field for user.
     */
	@Test
	public void testCreateUserWithEmptyStringFields() {
		given()
			.spec(authenticatedRequestSpec)
			.body(new User("", "ganguteli@gmailcom", "male", "active"))
		.when()
			.post("/users")
		.then()
			.statusCode(422)
			.body("[0].field", equalTo("name"))
			.body("[0].message", equalTo("can't be blank"));
	}
	/**
     * TC_USER_N020
     * Scenario: Scenario: Attempt to create user containing name with 201 characters.
     * Expected: API returns 422 Unprocessable Entity with an error indicating
     * 			name can't be more than 200 characters.
     * Business rule reference: Maximum allowed characters for name are 200.
     */
	@Test
	public void testCreateUserWithVeryLongName() {
		given()
			.spec(authenticatedRequestSpec)
			.body(new User("123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901", "ganguteli@gmailcom", "male", "active"))
		.when()
			.post("/users")
		.then()
			.statusCode(422)
			.body("[0].field", equalTo("name"))
			.body("[0].message", equalTo("is too long (maximum is 200 characters)"));
	}
	/**
     * TC_USER_N021
     * Scenario: Scenario: Attempt to create user having name with 200 characters.
     * Expected: API returns 201 Created indicating 
     * 				user having 200 characters name is allowed.
     * Business rule reference: Maximum allowed characters for name are 200.
     */
	@Test
	public void testCreateUserWithMaximumName() {
		given()
			.spec(authenticatedRequestSpec)
			.body(new User("12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890", "ganguteli2@gmailcom", "male", "active"))
		.when()
			.post("/users")
		.then()
			.statusCode(201)
			.body("email", equalTo("ganguteli2@gmailcom"));
	}
	
	/**
     * TC_USER_N022
     * Scenario: Scenario: Attempt to create user having special character apostrophe(')  in name.
     * Expected: API returns 201 Created indicating 
     * 				user having apostrophe in name is allowed.
     * Business rule reference: Apostrophe is allowed in name.
     */
	@Test
	public void testCreateUserWithApostrophe() {
		given()
			.spec(authenticatedRequestSpec)
			.body(new User("Dan O'Brien", "danobrien@gmailcom", "male", "active"))
		.when()
			.post("/users")
		.then()
			.statusCode(201)
			.body("email", equalTo("danobrien@gmailcom"));
	}
}
