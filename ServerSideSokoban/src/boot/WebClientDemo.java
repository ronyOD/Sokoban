package boot;

import SokobanServer.WebClient;

public class WebClientDemo {

	public static void main(String[] args) {
		WebClient client = new WebClient();
		String sol = client.get("level6");
		System.out.println(sol);
		
/*		WebClient client = new WebClient();
		String response = client.post("level3", "blo,blo");
		System.out.println(response);*/
	}

}
