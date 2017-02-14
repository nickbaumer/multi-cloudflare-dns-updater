package main.java.events;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MyLogger {
        static private FileHandler fileTxt;
        static private SimpleFormatter formatterTxt;

        static public void setup() throws IOException {

                // get the global logger to configure it
                //Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        		Logger logger = Logger.getLogger("dave");

                // suppress the logging output to the console
                Logger rootLogger = Logger.getLogger("");
                Handler[] handlers = rootLogger.getHandlers();
                if (handlers[0] instanceof ConsoleHandler) {
                        rootLogger.removeHandler(handlers[0]);
                }

                logger.setLevel(Level.INFO);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmmss"); 
                fileTxt = new FileHandler("Logging_"+LocalDateTime.now().format(formatter)+".txt");

                // create a TXT formatter
                formatterTxt = new SimpleFormatter();
                fileTxt.setFormatter(formatterTxt);
                logger.addHandler(fileTxt);
        }
}