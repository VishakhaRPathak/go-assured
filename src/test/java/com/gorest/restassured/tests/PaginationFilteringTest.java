package com.gorest.restassured.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

import org.testng.annotations.Test;

import com.gorest.restassured.base.BaseTest;

/**
 * Test Suite: Pagination/Filtering Testing
 * 			
 * API: GET /public/v2/users?page=number
 * 		GET /public/v2/users?status=active
 * 		GET /public/v2/users?gender=female
 * 
 * Verifies GoRest correctly allow pagination and filtering
 */
public class PaginationFilteringTest extends BaseTest {
	/**
     * TC_USER_N016
     * Scenario: Get the user details on page 2 with no token.
     * Expected: API returns 200 Ok.
     * Business rule reference: Paginated user details can be queried without any authentication in GoRest api.
     */
	@Test
	public void testGetUsersWithPagination() {
		given()
			.spec(unauthenticatedRequestSpec)
		
		.when()
			.get("/users?page=2")
		.then()
			.statusCode(200)
			.body("size()", greaterThanOrEqualTo(1))
			.body("[0].name", equalTo("Aanjaneya Nehru"));
	}
	/**
     * TC_USER_N017
     * Scenario: Get the user details filtered by gender as female.
     * Expected: API returns 200 Ok.
     * Business rule reference: Filtered user details can be queried in GoRest api.
     */
	@Test
	public void testFilterUsersByGender() {
		given()
			.spec(unauthenticatedRequestSpec)
		
		.when()
			.get("/users?gender=female")
		.then()
			.statusCode(200)
			.body("gender", everyItem(equalTo("female")));
	}
	/**
     * TC_USER_N018
     * Scenario: Get the user details filtered by status as active.
     * Expected: API returns 200 Ok.
     * Business rule reference: Filtered user details can be queried in GoRest api.
     */
	@Test
	public void testFilterUsersByStatus() {
		given()
			.spec(unauthenticatedRequestSpec)
		.when()
			.get("/users?status=active")
		.then()
			.statusCode(200)
			.body("status", everyItem(equalTo("active")));
	}

}
