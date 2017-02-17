package application.data;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import application.data.Source.FILE;

public class StationList {
	private final Map<String /* ID */, Station> _stations = new HashMap<>();
	private String _baseUrl;
	
	public StationList(String baseUrl) {
		_baseUrl = baseUrl;
		
		try {
			JSONObject stationList = Source.get(baseUrl, FILE.STATION_INFORMATION);
			JSONObject data = stationList.getJSONObject("data");
			JSONArray stations = data.getJSONArray("stations");
			
			for (int i = 0; i < stations.length(); i++) {
				JSONObject stationObject = stations.getJSONObject(i);
				Station station = new Station(stationObject);
				_stations.put(station.get_id(), station);
			}
			
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public void refresh() {
		try {
			JSONObject stationStatus = Source.get(_baseUrl, FILE.STATION_STATUS);
			JSONObject data = stationStatus.getJSONObject("data");
			JSONArray stations = data.getJSONArray("stations");
			
			for (int i = 0; i < stations.length(); i++) {
				JSONObject statusMessage = stations.getJSONObject(i);
				String id = statusMessage.getString("station_id");

				if (!_stations.containsKey(id))
					System.err.println("Unknown station found (id=" + id + "). Skipping.");
				else {
					Station toBeUpdated = _stations.get(id);
					toBeUpdated.update(statusMessage);
				}
			}
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public Map<String, Station> getMapOfStations() {
		return Collections.unmodifiableMap(_stations);
	}
}
