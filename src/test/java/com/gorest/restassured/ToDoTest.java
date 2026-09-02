package com.gorest.restassured;

import static io.restassured.RestAssured.given;

import java.util.List;

import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ToDoTest {
	@Test
	public void testGetToDo() {
		Response response = given()
			.baseUri("https://gorest.co.in/public/v2")
		.when()
			.get("/todos");
		
		JsonPath jsonPath = response.jsonPath();
		List<String> todoList = jsonPath.getList("title"); 
		System.out.println("First Item: " + todoList.get(0));
		
	}
}
