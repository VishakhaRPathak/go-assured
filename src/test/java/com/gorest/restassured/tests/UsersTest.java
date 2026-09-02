package com.gorest.restassured;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.gorest.restassured.pojo.User;
import com.gorest.restassured.utility.ConfigReader;

import io.restassured.http.ContentType;

public class UsersTest {
	private String token;

	
	@BeforeClass
	public void loadToken() {
		ConfigReader.loadProperties();
		token = ConfigReader.getProperty("TOKEN");
	}

	@Test
	public void testGetUsers() {
		given().baseUri("https://gorest.co.in/public/v2").when().get("/users").then().statusCode(200)
				.body("size()", greaterThanOrEqualTo(1)).body("status", hasItems("active", "inactive"))
				.body("[0].name", equalTo("Dharitri Varman"));
	}

	@Test
	public void testCreateUser() {
		String userId = given().header("Authorization", "Bearer " + token).baseUri("https://gorest.co.in/public/v2")
				.contentType("application/json")
				.body(new User("Tenali2 Ramakrishna", "tenali2@example.com", "male", "active")).when().post("/users")
				.then().statusCode(201).extract().path("id");
	}

	@Test
	public void testGetUser() {
		given().header("Authorization", "Bearer " + token).baseUri("https://gorest.co.in/public/v2").when()
				.get("/users/8599255").then().statusCode(200).body("name", equalTo("Tenali3 Ramakrishna"));
	}

	@Test
	public void testCreateGetUpdateDeleteUser() {
		String userId = given()
							.header("Authorization", "Bearer " + token)
							.baseUri("https://gorest.co.in/public/v2")
							.contentType(ContentType.JSON)
							.body(new User("Raja Bhoj5", "rajabhoj5@example.com", "male", "active"))
						.when()
							.post("/users")
						.then()
							.statusCode(201)
							.extract()
							.path("id").toString();
		System.out.println("id ---"+userId);
		given()
			.header("Authorization", "Bearer " + token)
			.baseUri("https://gorest.co.in/public/v2")
		.when()
			.get("/users/" + userId)
		.then()
			.statusCode(200)
			.body("name", equalTo("Raja Bhoj5"));
		System.out.println("get  ");
		given()
			.log().all()
			.header("Authorization", "Bearer " + token)
			.baseUri("https://gorest.co.in/public/v2")
			.contentType(ContentType.JSON)
			.body("{ \"name\": \"Gangu Teli\", \"status\": \"inactive\" }")
		.when()
			.put("/users/" + userId)
		.then()
			.statusCode(200)
			.body("name", equalTo("Gangu Teli"));
		System.out.println("update  ");
		
		given()
			.header("Authorization", "Bearer " + token)
			.baseUri("https://gorest.co.in/public/v2")
		.when()
			.delete("/users/" + userId)
		.then()
			.statusCode(204);
		System.out.println("delete  ");
		
		
		given()
			.header("Authorization", "Bearer " + token)
			.baseUri("https://gorest.co.in/public/v2")
		.when()
			.get("/users/" + userId)
		.then()
			.statusCode(404);
	}
}
