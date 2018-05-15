package logger;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Log {
	
	public static Logger logger;
	public FileHandler fileHandler;
	public static Log log = new Log("C:\\Users\\rpaezbas\\Desktop\\logs\\log.txt");

	public Log(String file_name) {
		File file = new File(file_name);

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			fileHandler = new FileHandler(file_name, true);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		logger = Logger.getLogger("logger");
		logger.addHandler(fileHandler);
		SimpleFormatter formatter = new SimpleFormatter();
		fileHandler.setFormatter(formatter);
		logger.setLevel(Level.INFO);

	}
}
