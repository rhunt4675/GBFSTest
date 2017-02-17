package application.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

public class Source {
	public static enum FILE {
		GBFS("gbfs.json"),
		SYSTEM_INFORMATION("system_information.json"),
		STATION_INFORMATION("station_information.json"),
		STATION_STATUS("station_status.json"),
		FREE_BIKE_STATUS("free_bike_status.json"),
		SYSTEM_HOURS("system_hours.json"),
		SYSTEM_CALENDAR("system_calendar.json"),
		SYSTEM_REGIONS("system_regions.json"),
		SYSTEM_PRICING_PLANS("system_pricing_plans.json"),
		SYSTEM_ALERTS("system_alerts.json");
		
		private String _uri;
		private FILE(String uri) {_uri = uri;}
		public String getUri() {return _uri;}
	};
	
	public static JSONObject get(String baseUrl, FILE type) throws JSONException, IOException {
		if (type == null)
			throw new IllegalArgumentException("FILE parameter must not be null.");
		if (baseUrl == null)
			throw new IllegalArgumentException("Base URL string must not be null.");
		
		JSONObject result;
		URL obj = new URL(baseUrl + type.getUri());
		
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		
		if (con.getResponseCode() == HttpURLConnection.HTTP_OK) { 
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			StringBuffer response = new StringBuffer();
			String inputLine;

			while ((inputLine = in.readLine()) != null)
				response.append(inputLine);
			in.close();

			String strResponse = response.toString();
			result = new JSONObject(strResponse);
		} else {
			throw new IOException("Server returned status " + con.getResponseCode());
		}
		
		return result;
	}
}
