package com.api.client;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.json.XML;

public class RestClient {
	public int statuscode;
	public String responsestring;
	public String response;
	public JSONObject jsonresponse;
	public Header[] hadderArray;

	public void get(String url) throws ClientProtocolException, IOException {

		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(url);
		CloseableHttpResponse closeablehttpresponse = httpClient.execute(httpget);

		statuscode = closeablehttpresponse.getStatusLine().getStatusCode();

//		responsestring = EntityUtils.toString(closeablehttpresponse.getEntity(), "UTF-8");
//		if (responsestring.startsWith("{")) {
//			jsonresponse = new JSONObject(responsestring);
//		} else if (responsestring.startsWith("<")) {
//			jsonresponse = XML.toJSONObject(responsestring);
//		}

		responsestring = EntityUtils.toString(closeablehttpresponse.getEntity(), "UTF-8");
		if (responsestring.startsWith("{")) {
			
		} else if (responsestring.startsWith("<")) {
			jsonresponse = XML.toJSONObject(responsestring);
			responsestring = jsonresponse.toString();

		}

		hadderArray = closeablehttpresponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();
		for (Header header : hadderArray) {
			allHeaders.put(header.getName(), header.getValue());
		}

	}

}
