package main.java.events;

import java.io.IOException;
import java.util.Date;

import main.java.events.CloudFlare;
import main.java.events.Utils;
import main.java.events.MyLogger;
import main.java.utils.SendGmail;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
	
	static String XAuthEmail = "";
	static String XAuthKey = "";
	static String[] domains = {""};
	
	private final static Logger LOGGER = Logger.getLogger("dave");
	
	
	public static void main(String[] args) throws Exception {
		LOGGER.setLevel(Level.INFO);
		if (XAuthEmail == "" || XAuthKey == "" || domains.length == 0) {
			throw new RuntimeException("User info for Cloudflare is blank. Not attempting to check IP.");
		}
        try {
                MyLogger.setup();
        } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Problems with creating the log files");
        }
        Boolean shouldContinue = new Boolean(true);
        int retryCount = 0;
        while (shouldContinue && retryCount < 5) {
			try {
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
				retryCount = 0; // set to 0 as we had a successful update
				Thread.sleep(60 * 1000);
			}	catch (Exception e) {
					LOGGER.log(Level.SEVERE, "an exception was thrown", e);
					System.out.println(e.getMessage());
					SendGmail.generateAndSendEmail("nick.baumer@gmail.com", e.getMessage());
					retryCount++;
					Thread.sleep(300 * 1000); // if something went wrong, sleep for 5 minutes and try again
			}
        }
        SendGmail.generateAndSendEmail("nick.baumer@gmail.com", "Cloudflare DNS updater has stopped running."); // this should be sent if we fall outside the while loop
	}
}