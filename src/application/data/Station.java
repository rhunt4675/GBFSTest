package application.data;

import org.json.JSONException;
import org.json.JSONObject;

public class Station {	
	public Station(JSONObject station) throws JSONException {
		// Required Fields
		_id = station.getString("station_id");
		_name = station.getString("name");
		_lattitude = station.getDouble("lat");
		_longitude = station.getDouble("lon");
		
		// Optional Fields
		if (station.has("short_name")) _shortName = station.getString("short_name");
		if (station.has("address")) _address = station.getString("address");
		if (station.has("cross_street")) _crossStreet = station.getString("cross_street");
		if (station.has("region_id")) _regionId = station.getString("region_id");
		if (station.has("post_code")) _postCode = station.getString("post_code");
		if (station.has("rental_methods")) _paymentMethod = PAYMENT_METHOD.fromString(station.getString("rental_methods"));
		if (station.has("capacity")) _capacity = station.getInt("capacity");
	}
	
	public void update(JSONObject status) throws JSONException {
		// Required Fields
		_numBikesAvailable = status.getInt("num_bikes_available");
		_numDocksAvailable = status.getInt("num_docks_available");
		_isInstalled = status.getInt("is_installed") == 1;
		_isRenting = status.getInt("is_renting") == 1;
		_isReturning = status.getInt("is_returning") == 1;
		_lastReported = status.getInt("last_reported");
		
		// Optional Fields
		if (status.has("num_bikes_disabled")) _numBikesDisabled = status.getInt("num_bikes_disabled");
		if (status.has("num_docks_disabled")) _numDocksDisabled = status.getInt("num_docks_disabled");
	}
	
	public enum PAYMENT_METHOD {
		KEY("KEY"),
		CREDIT_CARD("CREDITCARD"),
		PAY_PASS("PAYPASS"),
		APPLE_PAY("APPLEPAY"),
		ANDROID_PAY("ANDROIDPAY"),
		TRANSIT_CARD("TRANSITCARD"),
		ACCOUNT_NUMBER("ACCOUNTNUMBER"),
		PHONE("PHONE");
		
		private String _method;
		private PAYMENT_METHOD(String method) {_method = method;}
		
		public static PAYMENT_METHOD fromString(String s) {
			for (PAYMENT_METHOD method : PAYMENT_METHOD.values()) {
				if (method._method.equals(s))
					return method;
			}
			return null;
		}
	}
	
	private String _id;
	private String _name;
	private double _lattitude;
	private double _longitude;
	private String _shortName;
	private String _address;
	private String _crossStreet;
	private String _regionId;
	private String _postCode;
	private PAYMENT_METHOD _paymentMethod;
	private int _capacity;
	
	private int _numBikesAvailable;
	private int _numBikesDisabled;
	private int _numDocksAvailable;
	private int _numDocksDisabled;
	private boolean _isInstalled;
	private boolean _isRenting;
	private boolean _isReturning;
	private int _lastReported;
	
	public String get_id() {
		return _id;
	}

	public String get_name() {
		return _name;
	}

	public double get_lattitude() {
		return _lattitude;
	}

	public double get_longitude() {
		return _longitude;
	}

	public String get_shortName() {
		return _shortName;
	}

	public String get_address() {
		return _address;
	}

	public String get_crossStreet() {
		return _crossStreet;
	}

	public String get_regionId() {
		return _regionId;
	}

	public String get_postCode() {
		return _postCode;
	}

	public PAYMENT_METHOD get_paymentMethod() {
		return _paymentMethod;
	}

	public int get_capacity() {
		return _capacity;
	}

	public int get_numBikesAvailable() {
		return _numBikesAvailable;
	}

	public int get_numBikesDisabled() {
		return _numBikesDisabled;
	}

	public int get_numDocksAvailable() {
		return _numDocksAvailable;
	}

	public int get_numDocksDisabled() {
		return _numDocksDisabled;
	}

	public boolean isInstalled() {
		return _isInstalled;
	}

	public boolean isRenting() {
		return _isRenting;
	}

	public boolean isReturning() {
		return _isReturning;
	}

	public int get_lastReported() {
		return _lastReported;
	}
}
