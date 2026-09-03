package com.gorest.restassured.tests;

import org.testng.annotations.Test;

import com.gorest.restassured.base.BaseTest;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static io.restassured.RestAssured.given;

/**
 * Test Suite: Schema Validation Testing
 * 			
 * API: GET /public/v2/users/id
 * 		GET /public/v2/posts/id
 * 		GET /public/v2/todos/id
 *  
 * Verifies GoRest correctly get User/posts/todos details as per the respective schemas.
 */

public class SchemaValidationTest extends BaseTest{

	@Test
	/**
     * TC_USER_NO21
     * Scenario: Attempt to get the user.
     * Expected: API returns 200 Ok with 
     * 				a list of user details.
     * Business rule reference: User details will be returned as per the given specifications.
     */
	public void validateUserJSONFormat() {
		given()
			.spec(unauthenticatedRequestSpec)
		.when()
			.get("/users/8599730")
		.then()
			.statusCode(200)
			.body(matchesJsonSchemaInClasspath("com/gorest/restassured/schema/user-schema.json"));
	}
	@Test
	/**
     * TC_USER_NO22
     * Scenario: Attempt to get the post.
     * Expected: API returns 200 Ok with 
     * 				a list of post details.
     * Business rule reference: Post details will be returned as per the given specifications.
     */
	public void validatePostJSONFormat() {
		given()
			.spec(unauthenticatedRequestSpec)
		.when()
			.get("/posts/290191")
		.then()
			.statusCode(200)
			.body(matchesJsonSchemaInClasspath("com/gorest/restassured/schema/post-schema.json"));
	}
	@Test
	/**
     * TC_USER_NO23
     * Scenario: Attempt to get the todo.
     * Expected: API returns 200 Ok with 
     * 				a list of todo details.
     * Business rule reference: Todo details will be returned as per the given specifications.
     */
	public void validateTodoJSONFormat() {
		given()
			.spec(unauthenticatedRequestSpec)
		.when()
			.get("/todos/109312")
		.then()
			.statusCode(200)
			.body(matchesJsonSchemaInClasspath("com/gorest/restassured/schema/todo-schema.json"));
	}
}
