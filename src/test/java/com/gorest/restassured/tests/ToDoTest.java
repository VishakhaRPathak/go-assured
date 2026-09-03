package com.gorest.restassured.tests;

import static io.restassured.RestAssured.given;

import java.util.List;

import org.testng.annotations.Test;

import com.gorest.restassured.base.BaseTest;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ToDoTest extends BaseTest{
	@Test
	/**
     * TC_USER_N02
     * Scenario: Attempt to get the todo list.
     * Expected: API returns 200 Ok.
     * Business rule reference: Read operation (Todos) can be executed anonymously.
     */
	public void testGetToDo() {
		Response response = given()
				.spec(unauthenticatedRequestSpec)
				
		.when()
			.get("/todos");
		
		JsonPath jsonPath = response.jsonPath();
		List<String> todoList = jsonPath.getList("title"); 
		System.out.println("First Item: " + todoList.get(0));
		
	}
}
