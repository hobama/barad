import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class Logger {
	
	private File logFile;
	private PrintWriter writer;
	
	/**
	 * Create a new logger
	 */
	public Logger() {
		logFile = new File(TesterProperties.LOG_PATH, TesterProperties.LOG_FILE);
		try{
			writer = new PrintWriter(new FileOutputStream(logFile));}
		catch(FileNotFoundException fnfe){
			System.out.println(fnfe);
		}
	}
	
	/**
	 * Create a new logger
	 * @param path The log path
	 * @param fileName The log file name
	 */
	public Logger(String path, String fileName) {
		logFile = new File(path, fileName);
		try{
			writer = new PrintWriter(new FileOutputStream(logFile));}
		catch(FileNotFoundException fnfe){
			System.out.println(fnfe);
		}
	}
	
	/**
	 * Logs a message
	 * @param client The client class
	 * @param message The message
	 */
	public void info(Object client, String message) {
		String clientName = "DEFAULT";
		if (client instanceof String) {
			clientName = (String)client;
		} else {
			clientName = client.getClass().getName();
		}
		writer.append("[" + clientName + "]" + " [INFO] " + message +  TesterProperties.NEW_LINE);
		writer.flush();
	}
	
	/**
	 * Logs a warning message
	 * @param client The client class
	 * @param message The message
	 */
	public void warn(Object client, String message) {
		String clientName = "DEFAULT";
		if (client instanceof String) {
			clientName = (String)client;
		} else {
			clientName = client.getClass().getName();
		}
		writer.append("[" + clientName + "]" + " [WARN] " + message +  TesterProperties.NEW_LINE);
		writer.flush();
	}
	
	/**
	 * Logger a debug message
	 * @param message Debug message
	 */
	public void debug(Object client, String message) {
		if (TesterProperties.DEBUG) {
			String clientName = "DEFAULT";
			if (client instanceof String) {
				clientName = (String)client;
			} else {
				clientName = client.getClass().getName();
			}
			writer.append("[" + clientName + "]" + " [DEBUG] " + message +  TesterProperties.NEW_LINE);
			writer.flush();
		}
	}
	
	/**
	 * Logger an error message
	 * @param message Error message
	 */
	public void error(Object client, String message) {
		String clientName = "DEFAULT";
		if (client instanceof String) {
			clientName = (String)client;
		} else {
			clientName = client.getClass().getName();
		}
		writer.append("[" + clientName + "]" + " [ERROR] " + message +  TesterProperties.NEW_LINE);
		writer.flush();
	}
	
	/**
	 * Closes the output stream
	 */
	@Override
	public void finalize()  {
		writer.close();
	}
}
