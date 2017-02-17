package application.data;

import java.io.IOException;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import application.data.Source.FILE;

public class BikeSystem {
	private String _id;
	private String _name;
	private Locale _language;
	private String _shortName;
	private String _operator;
	private String _url;
	private String _purchaseUrl;
	private String _startDate;
	private String _phoneNumber;
	private String _email;
	private String _timezone;
	private String _licenseUrl;
	
	private StationList _stationList;
	
	private BikeSystem(JSONObject system, StationList stationList) throws JSONException {
		_stationList = stationList;
		
		// Required Fields
		_id = system.getString("system_id");
		_name = system.getString("name");
		_language = Locale.forLanguageTag(system.getString("language"));
		
		// Optional Fields
		if (system.has("short_name")) _shortName = system.getString("short_name");
		if (system.has("operator")) _operator = system.getString("operator");
		if (system.has("url")) _url = system.getString("url");
		if (system.has("purchase_url")) _purchaseUrl = system.getString("purchase_url");
		if (system.has("start_date")) _startDate = system.getString("start_date");
		if (system.has("phone_number")) _phoneNumber = system.getString("phone_number");
		if (system.has("email")) _email = system.getString("email");
		if (system.has("timezone")) _timezone = system.getString("timezone");
		if (system.has("license_url")) _licenseUrl = system.getString("license_url");
	}
	
	public static BikeSystem getSystem(String baseUrl) {
		BikeSystem result = null;
		
		try {
			JSONObject system = Source.get(baseUrl, FILE.SYSTEM_INFORMATION);
			JSONObject data = system.getJSONObject("data");
			StationList stationList = new StationList(baseUrl);
			result = new BikeSystem(data, stationList);
			
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public String get_id() {
		return _id;
	}

	public String get_name() {
		return _name;
	}

	public Locale get_language() {
		return _language;
	}

	public String get_shortName() {
		return _shortName;
	}

	public String get_operator() {
		return _operator;
	}

	public String get_url() {
		return _url;
	}

	public String get_purchaseUrl() {
		return _purchaseUrl;
	}

	public String get_startDate() {
		return _startDate;
	}

	public String get_phoneNumber() {
		return _phoneNumber;
	}

	public String get_email() {
		return _email;
	}

	public String get_timezone() {
		return _timezone;
	}

	public String get_licenseUrl() {
		return _licenseUrl;
	}

	public StationList get_stationList() {
		return _stationList;
	}
}
