package com.gorest.restassured.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasItems;

import org.testng.annotations.Test;

import com.gorest.restassured.base.BaseTest;
import com.gorest.restassured.pojo.User;

/**
 * Test Suite: User Operations - Positive Scenarios
 * API: GET /public/v2/users
 * 		GET /public/v2/users/id
 * 		POST /public/v2/users
 * 		PUT /public/v2/users/id
 * 		DELETE /public/v2/users/id
 * 
 * Verifies GoRest correctly accepts valid user operations
 * and returns appropriate 2XX status codes with correct details.
 */
public class UsersTest extends BaseTest{
	

	@Test
	/**
     * TC_USER_N04
     * Scenario: Attempt to get the users.
     * Expected: API returns 200 Ok with 
     * 				a list of user details.
     * Business rule reference: Read operation (Users) can be executed anonymously.
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

	@Test
	/**
     * TC_USER_N05
     * Scenario: Attempt to create a new user with a valid token.
     * Expected: API returns 201 Created indicating 
     * 					a new user has been created successfully.
     * Business rule reference: A new user, adhering to strict schema constraints,  can be created with a proper authentication.
     */
	public void testCreateUser() {
		given()
			.spec(authenticatedRequestSpec)
			.body(new User("Tenali2 Ramakrishna", "tenali10@example.com", "male", "active"))
		.when()
			.post("/users")
		.then()
			.statusCode(201)
			.extract()
			.path("id");
	}

	@Test
	/**
     * TC_USER_N06
     * Scenario: Attempt to get a user with given id.
     * Expected: API returns 200 Ok with the user details.
     * Business rule reference: Read operation (Users) can be executed anonymously.
     */
	public void testGetUser() {
		given()
			.spec(unauthenticatedRequestSpec)
		.when()
			.get("/users/8599255")
		.then()
			.statusCode(200)
			.body("name", equalTo("Tenali3 Ramakrishna"));
	}

	@Test
	/**
     * TC_USER_N07
     * Scenario: Attempt to create a new user, get the details of this user with given id, update name and status, delete the user.
     * Expected: API returns 
     * 					201 Created indicating a new user has been created successfully
     * 					200 Ok with the user details.
     * 					200 Ok with updated user details.
     * 					204 No Content with no user details as payload.
     * 					404 Not Found indicating user record is not found
     * Business rule reference: User Lifecycle Scenario - the business rules ensure data integrity, security, and system compliance.
     */
	public void testCreateGetUpdateDeleteUser() {
		String userId = given()
							.spec(authenticatedRequestSpec)
							.body(new User("Raja Bhoj5", "rajabhoj5@example.com", "male", "active"))
						.when()
							.post("/users")
						.then()
							.statusCode(201)
							.extract()
							.path("id").toString();
		System.out.println("id ---"+userId);
		given()
			.spec(authenticatedRequestSpec)
		
		.when()
			.get("/users/" + userId)
		.then()
			.statusCode(200)
			.body("name", equalTo("Raja Bhoj5"));
		System.out.println("get  ");
		
		given()
			.spec(authenticatedRequestSpec)
			.body("{ \"name\": \"Gangu Teli\", \"status\": \"inactive\" }")
		.when()
			.put("/users/" + userId)
		.then()
			.statusCode(200)
			.body("name", equalTo("Gangu Teli"));
		System.out.println("update  ");
		
		given()
			.spec(authenticatedRequestSpec)
		.when()
			.delete("/users/" + userId)
		.then()
			.statusCode(204);
		System.out.println("delete  ");
		
		
		given()
			.spec(authenticatedRequestSpec)
		.when()
			.get("/users/" + userId)
		.then()
			.statusCode(404);
	}
}
