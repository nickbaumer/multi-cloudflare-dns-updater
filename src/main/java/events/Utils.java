package main.java.events;

public class Utils {
	
	public static String getExternalIP() throws Exception {
		
		HttpURLFunctions http = new HttpURLFunctions();
		String url = "https://api.ipify.org/";
		String response = http.sendGet(url);
		return response;
	}
	
}
