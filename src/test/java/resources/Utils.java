package resources;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Utils {

	public static RequestSpecification requestSpecification;

	public RequestSpecification requestSpecification() throws IOException {

		if (requestSpecification == null) {

			PrintStream log = new PrintStream(new FileOutputStream("logs.txt"));

			requestSpecification = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseUrl"))
					.addQueryParam("key", "qaclick123").addFilter(RequestLoggingFilter.logRequestTo(log))
					.addFilter(ResponseLoggingFilter.logResponseTo(log)).setContentType(ContentType.JSON).build();

			return requestSpecification;
		}
		return requestSpecification;
	}

	public String getGlobalValue(String key) throws IOException {

		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(
				"/Users/sarbani.paulthoughtworks.com/eclipse-workspace/APIFramework/src/test/java/resources/global.properties");
		prop.load(fis);
		return prop.getProperty(key);

	}
	
	public String getJsonPath(Response response, String key) {
		 
		String responseInString = response.asString();
		JsonPath jsonPath = new JsonPath(responseInString);
		return jsonPath.get(key).toString();
	}

}
