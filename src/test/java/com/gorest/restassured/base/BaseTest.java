package com.gorest.restassured.base;

import java.lang.reflect.Method;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.gorest.restassured.utility.ConfigReader;
import com.gorest.restassured.utility.ReportUtils;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;


public class BaseTest {
	public static RequestSpecification authenticatedRequestSpec;
	public static RequestSpecification unauthenticatedRequestSpec;
	
	@BeforeSuite
	public void initiateExtentReport() {
		ReportUtils.initialize();
	}
	@BeforeSuite
	public void loadProperties() {
		ConfigReader.loadProperties();
	}
	@BeforeSuite
    public void setup() {
//		Setting config to ensure logging of request and response when the validation fails
		RestAssured.config = RestAssuredConfig.config()
				.logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails());
        authenticatedRequestSpec = new RequestSpecBuilder()
            .setBaseUri("https://gorest.co.in/public/v2")
            .addHeader("Authorization", "Bearer " + ConfigReader.getProperty("TOKEN"))
            .build();
        unauthenticatedRequestSpec = new RequestSpecBuilder()
        		.setBaseUri("https://gorest.co.in/public/v2")
        		.build();
    }
	@BeforeMethod
	public void createTest(Method method) {
		ReportUtils.createTest(method.getName());
	}
	@AfterMethod
	public void endTest(ITestResult result) {
		if(result.getStatus()==ITestResult.FAILURE) {
			ReportUtils.fail(result.getThrowable());
			
		}else if(result.getStatus() == ITestResult.SUCCESS) {
			ReportUtils.pass("Testcase passed");
		}else if(result.getStatus() == ITestResult.SKIP) {
			ReportUtils.skip("Testcase skipped");
		}
	}
	
	@AfterSuite
	public void stopExtentReport() {
		ReportUtils.close();
	}
}
