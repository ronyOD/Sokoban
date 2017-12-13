package SokobanServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class WebClient {

	URL url;
	HttpURLConnection connection;
	public static final String address = "http://localhost:8080/WS/service";
	
	public WebClient() {
	}
	
	public String get(String levelName){
		StringBuilder sb = null;
		try {
			String output;
			sb = new StringBuilder();
			url = new URL(address + "/" + levelName);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "text/plain");
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			
			while((output = reader.readLine()) != null){
				sb.append(output);
			}
			
			connection.disconnect();
		
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
			

		return sb.toString();
	}
	
	public String post(String levelName, String solution){
		String output;
		StringBuilder sb = new StringBuilder();
		try {
			url = new URL(address + "/" + levelName + "/" + solution);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "text/plain");
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			
			while((output = reader.readLine()) != null){
				sb.append(output);
			}
			
			connection.disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

}
