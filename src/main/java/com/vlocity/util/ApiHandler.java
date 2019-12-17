package com.vlocity.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
/**
 * This Is A Utility Class For Handling Api Http Request. Extendable For GET, POST, PUT, PATCH, DELETE Requests.
 *
 * @author Krishna
 * @since 18 Dec 2019
 */
public class ApiHandler {

	private static Logger log = LoggerFactory.getLogger(ApiHandler.class);

	/**
	 * This Method Handles Get Request.
	 * @param hrefLink {@link String} Representing endpoint
	 * @return {@link HttpResponse} Instance
	 */
	public static HttpResponse handleGetRequest(String hrefLink){

		HttpClient client = HttpClientBuilder.create().disableCookieManagement().build();

		HttpGet getRequest = new HttpGet(hrefLink);
		HttpResponse response = null;

		try {
			log.info("Executing GET Request For - "+hrefLink);
			response = client.execute(getRequest);
		} catch (IOException e) {
			log.error("Error While Executing Http Get Request " + e.getMessage());
		}

		return response;
	}

	/**
	 * This Method Handles Post Request.
	 * @param hrefLink {@link String} Representing endpoint
	 * @param jsonBody {@link String} Body In JSON format
	 * @return {@link HttpResponse} Instance
	 */
	public static HttpResponse handlePostRequest(String hrefLink, String jsonBody){

		HttpClient client = HttpClientBuilder.create().disableCookieManagement().build();

		HttpPost postRequest = new HttpPost(hrefLink);
		HttpResponse response = null;

		postRequest.setEntity(new StringEntity(jsonBody, ContentType.APPLICATION_JSON));

		try {
			log.info("Executing GET Request For - "+hrefLink);
			response = client.execute(postRequest);
		} catch (IOException e) {
			log.error("Error While Executing Http Post Request " + e.getMessage());
		}

		return response;
	}
}
