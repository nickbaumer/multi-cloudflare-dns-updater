package main.java.events;

import java.util.Date;

import main.java.events.CloudFlare;
import main.java.events.Utils;

public class Main {
	
	static String XAuthEmail = "";
	static String XAuthKey = "";
	static String[] domains = {""};
	
	public static void main(String[] args) throws Exception {
		
		try {
			while (true) {
				for (String domain:domains){
					String currentIP = CloudFlare.getCurrentIP(domain, XAuthEmail, XAuthKey);
					String externalIP = Utils.getExternalIP();
					
					if (currentIP.equals(externalIP) == false) {
						System.out.println(domain+": IP does not match, updating to "+externalIP);
						CloudFlare.updateIP(domain, externalIP, XAuthEmail, XAuthKey);
					} else {
						System.out.println(domain+": IP address does not need updating.");
					}
				}
				System.out.println(new Date());
	            Thread.sleep(60 * 1000);
			}
		}	catch (InterruptedException e) {
			        e.printStackTrace();
		}
	}
}