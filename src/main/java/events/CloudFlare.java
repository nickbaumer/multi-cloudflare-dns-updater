package main.java.events;

import java.util.HashMap;
import main.java.events.HttpURLFunctions;
import org.json.*;

public class CloudFlare {
	
	public static String getCurrentIP(String domain, String XAuthEmail, String XAuthKey) throws Exception {
		
		HttpURLFunctions http = new HttpURLFunctions();
		
		HashMap<String, String> properties = new HashMap<String, String>();
		properties.put("X-Auth-Email", XAuthEmail);
		properties.put("X-Auth-Key", XAuthKey);
		
		String zoneID = getZoneId(domain, XAuthEmail, XAuthKey);
		
		String url = "https://api.cloudflare.com/client/v4/zones/"+zoneID+"/dns_records";
		String response = http.sendGet(url, properties);
		JSONObject obj = new JSONObject(response);
		JSONArray arr = obj.getJSONArray("result");
		String currentIP = arr.getJSONObject(0).getString("content");
		return currentIP;
	}
	
	public static String getZoneId(String domain, String XAuthEmail, String XAuthKey) throws Exception{
		
		HttpURLFunctions http = new HttpURLFunctions();
		
		HashMap<String, String> properties = new HashMap<String, String>();
		properties.put("X-Auth-Email", XAuthEmail);
		properties.put("X-Auth-Key", XAuthKey);
		
		String url = "https://api.cloudflare.com/client/v4/zones/";
		
		String response = http.sendGet(url, properties);
		JSONObject obj = new JSONObject(response);
		JSONArray arr = obj.getJSONArray("result");
		String strId = null;
		for (int i = 0; i < arr.length(); i++) {
			String currDomain = arr.getJSONObject(i).getString("name");
			if (currDomain.equals(domain)) {
				strId = arr.getJSONObject(i).getString("id");				
			}
		}
		
		return strId;
	}
	
	public static String listDNSRecords(String domain, String XAuthEmail, String XAuthKey) throws Exception{
		// this returns the entire JSON string
		HttpURLFunctions http = new HttpURLFunctions();
		
		HashMap<String, String> properties = new HashMap<String, String>();
		properties.put("X-Auth-Email", XAuthEmail);
		properties.put("X-Auth-Key", XAuthKey);
		
		String zoneId = getZoneId(domain, XAuthEmail, XAuthKey);
		
		String url = "https://api.cloudflare.com/client/v4/zones/"+zoneId+"/dns_records";
		
		String response = http.sendGet(url, properties);
		
		return response;
	}
	
	public static String getARecordID(String domain, String XAuthEmail, String XAuthKey) throws Exception{
		// this only returns the ID
		HttpURLFunctions http = new HttpURLFunctions();
		
		HashMap<String, String> properties = new HashMap<String, String>();
		properties.put("X-Auth-Email", XAuthEmail);
		properties.put("X-Auth-Key", XAuthKey);
		
		String zoneId = getZoneId(domain, XAuthEmail, XAuthKey);
		
		String url = "https://api.cloudflare.com/client/v4/zones/"+zoneId+"/dns_records";
		
		String response = http.sendGet(url, properties);
		
		JSONObject obj = new JSONObject(response);
		JSONArray arr = obj.getJSONArray("result");
		String recordId = arr.getJSONObject(0).getString("id");
		
		return recordId;
	}
	
	public static void updateIP(String domain, String newIP, String XAuthEmail, String XAuthKey) throws Exception{
		
		HttpURLFunctions http = new HttpURLFunctions();
		
		HashMap<String, String> properties = new HashMap<String, String>();
		properties.put("X-Auth-Email", XAuthEmail);
		properties.put("X-Auth-Key", XAuthKey);
		
		String aRecordDomain = getARecordDomain(domain, XAuthEmail, XAuthKey);
		String zoneId = getZoneId(domain, XAuthEmail, XAuthKey);
		String recordId = getARecordID(domain, XAuthEmail, XAuthKey);
		
		String url = "https://api.cloudflare.com/client/v4/zones/"+zoneId+"/dns_records/"+recordId;
		
		
		
		String urlParameters = "{"
				+ "\"type\":\"A\","
				+ "\"name\":\""+aRecordDomain+"\","
				+ "\"content\":\""+newIP+"\""
				+ "}"
				+ "";
		
		http.sendPost(url, urlParameters, "PUT", properties);
		
	}
	
	public static String getARecordDomain(String domain, String XAuthEmail, String XAuthKey) throws Exception {
		String dnsRecord = listDNSRecords(domain, XAuthEmail, XAuthKey);
		JSONObject obj = new JSONObject(dnsRecord);
		JSONArray arr = obj.getJSONArray("result");
		String aRecordDomain = arr.getJSONObject(0).getString("name");
		return aRecordDomain;
	}
	

}
