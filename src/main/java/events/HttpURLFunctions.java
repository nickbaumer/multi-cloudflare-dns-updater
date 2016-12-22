package main.java.events;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;

public class HttpURLFunctions {

	@SuppressWarnings("unused")
	private final String USER_AGENT = "Mozilla/5.0";

	public static void main(String[] args) throws Exception {

	}

	// HTTP GET request
	String sendGet(String url, HashMap<String, String> properties) throws Exception {

		if (url.isEmpty()) {
			url = "https://api.cloudflare.com/client/v4/zones/";
		}
		

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		
		addHeaders(properties, con);
		
		int responseCode = con.getResponseCode();

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		//System.out.println(response.toString());
		return response.toString();

	}
	
	String sendGet(String url) throws Exception {

		if (url.isEmpty()) {
			url = "https://api.cloudflare.com/client/v4/zones/";
		}
		

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		int responseCode = con.getResponseCode();
		

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		//System.out.println(response.toString());
		return response.toString();

	}

	private void addHeaders(HashMap<String, String> properties, HttpURLConnection con) {
		Set<Entry<String, String>> set = properties.entrySet();
		Iterator<Entry<String, String>> iterator = set.iterator();
		while (iterator.hasNext()) {
			Map.Entry mentry = (Map.Entry)iterator.next();
			con.setRequestProperty(mentry.getKey().toString(), mentry.getValue().toString());
		}
	}

	// HTTP POST request
	public void sendPost(String url, String urlParameters, String method, HashMap<String, String> properties) throws Exception {

		if (url.isEmpty()) {
			url = "https://api.cloudflare.com/client/v4/zones/";
		}
		
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		// post / put has to be added
		con.setRequestMethod(method);
		addHeaders(properties, con);

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());

	}
	
}