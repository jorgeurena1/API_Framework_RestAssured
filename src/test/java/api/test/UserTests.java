package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import api.endpoints.UserEndpoints;
import api.endpoints.UserEndpoints2;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests {
	
	User userPayload;
	public Logger logger;
	
	@BeforeClass
	public void setup() {
		
		userPayload = new User();
		
		userPayload.setId(1);
		userPayload.setUsername("username1");
		userPayload.setFirstName("fistname1");
		userPayload.setLastName("lastname1");
		userPayload.setEmail("email@abcd.com");
		userPayload.setPassword("pass123");
		userPayload.setPhone("555555555");
		
		//logs
		logger = LogManager.getLogger(this.getClass());
	}
	
	@Test(priority=1)
	public void testPostUser() {
		
		logger.info("------------ Creating User ------------");
		
		Response response = UserEndpoints2.createUser(userPayload);
		
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("------------ User Created -------------");
	}
	
	@Test(priority=2)
	public void testGetUserByName() {
		
		Response response = UserEndpoints2.readUser(this.userPayload.getUsername());
		
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	
	@Test(priority=3)
	public void testUpdateUserByName() {
		
		//update data using data
		userPayload.setFirstName("fistname2");
		userPayload.setLastName("lastname2");
		userPayload.setEmail("email2@abcd.com");
		
		Response response = UserEndpoints2.updateUser(this.userPayload.getUsername(), userPayload);
		
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		//Checking data after update
		Response responseAfterUpdate = UserEndpoints2.readUser(this.userPayload.getUsername());
		responseAfterUpdate.then().log().all();
		Assert.assertEquals(responseAfterUpdate.getStatusCode(), 200);
	}
	
	@Test(priority=4)
	public void testDeleteUserByName() {
		
		Response response = UserEndpoints2.deleteUser(this.userPayload.getUsername());
		Assert.assertEquals(response.getStatusCode(), 200);
		
	}
	
}
