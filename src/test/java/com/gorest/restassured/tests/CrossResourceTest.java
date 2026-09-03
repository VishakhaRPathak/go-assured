package com.gorest.restassured.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import com.gorest.restassured.base.BaseTest;

/**
 * Test Suite: User-Posts, User-ToDo relationship
 * API: GET /public/v2/users/id
 * 		GET /public/v2/posts?user_id=id
 * 		GET /public/v2/todos?user_id=id
 * 		DELETE /public/v2/users/id
 * 
 * Verifies GoRest correctly handles posts and todo after deletion of a user.
 */
public class CrossResourceTest extends BaseTest{
	@Test
	/**
     * TC_USER_N024
     * Scenario: Attempt to get post for the deleted user.
     * Expected: API returns 200 Ok with 
     * 				a list of posts for user.
     * Business rule reference: Post of deleted user are deleted.
     */
	public void testUserPostRelation() {
		String userId = "8596196";
		given()
			.spec(authenticatedRequestSpec)
		.when()
			.get("/posts?user_id=" + userId )
		.then()
			.statusCode(200)
			.body("size()", greaterThanOrEqualTo(1));
	System.out.println("GET");
		given()
			.spec(authenticatedRequestSpec)
		.when()
			.delete("/users/" + userId)
		.then()
			.statusCode(204);
		System.out.println("DELETE");	
		given()
			.spec(authenticatedRequestSpec)
		.when()
			.get("/posts?user_id=" + userId )
		.then()
		.statusCode(200)
		.body("size()", equalTo(0));


	}
	@Test
	/**
     * TC_USER_N024
     * Scenario: Attempt to get todo for the deleted user.
     * Expected: API returns 200 Ok with 
     * 				a list of todos.
     * Business rule reference: Todo of deleted user are retained.
     */
	public void testUserTodoRelation() {
		String userId = "8599722";
		given()
			.spec(authenticatedRequestSpec)
		.when()
			.get("/todos?user_id=" + userId )
		.then()
			.statusCode(200)
			.body("size()", greaterThanOrEqualTo(1));
	
		given()
			.spec(authenticatedRequestSpec)
		.when()
			.delete("/users/" + userId)
		.then()
			.statusCode(204);
		
		given()
			.spec(authenticatedRequestSpec)
		.when()
			.get("/todos?user_id=" + userId )
		.then()
			.statusCode(200)
			.body("size()", equalTo(0));


	}

}
