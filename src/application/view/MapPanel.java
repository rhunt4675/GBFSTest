package application.view;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventHandler;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.InfoWindow;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;

import application.data.Station;
import application.data.StationList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import netscape.javascript.JSObject;

public class MapPanel extends BorderPane implements Initializable, MapComponentInitializedListener {
	@FXML private GoogleMapView mapView;
	private GoogleMap map;
	
	private Map<String, Marker> _mapMarkers = new HashMap<>();
	private Map<JSObject, String> _latLongMap = new HashMap<>();
	private InfoWindow _activeInfoWindow;

	public MapPanel() {
		FXMLLoader fxml = new FXMLLoader();
		fxml.setLocation(getClass().getResource("MapPanel.fxml"));
		fxml.setRoot(this);
		fxml.setController(this);
		
		try {
			fxml.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		mapView.addMapInializedListener(this);
	}
	
	@Override
	public void mapInitialized() {
        // Set the initial properties of the map.
        MapOptions mapOptions = (new MapOptions())
    		.center(new LatLong(43.0731, -89.4012)) // Madison, WI
            .mapType(MapTypeIdEnum.ROADMAP)
            .overviewMapControl(false)
            .panControl(false)
            .rotateControl(false)
            .scaleControl(false)
            .streetViewControl(false)
            .zoomControl(true)
            .zoom(14);
        
		map = mapView.createMap(mapOptions);
	}
	
	public void addBikeStations(StationList stationList) {
		Collection<Station> stations = stationList.getMapOfStations().values();
		for (Station station : stations) {
			// Create a Marker (if necessary)
			if (!_mapMarkers.containsKey(station.get_id())) {
				LatLong latLong = new LatLong(station.get_lattitude(), station.get_longitude());
				MarkerOptions markerOptions = new MarkerOptions();
				markerOptions.position(latLong);
				Marker marker = new Marker(markerOptions);

				map.addMarker(marker);
				map.addUIEventHandler(marker, UIEventType.click, new UIEventHandler() {
					@Override public void handle(JSObject mark) {
						
						// Close Existing InfoWindow
						if (_activeInfoWindow != null)
							_activeInfoWindow.close();
						
						// Create New InfoWindow
						String id = (String) _latLongMap.get(mark.getMember("latLng"));
						_activeInfoWindow = new InfoWindow();
						_activeInfoWindow.setContent(
							"<h2>" + station.get_id() 
							+ "<br>(" + station.get_name() + ")</h2>"
							+ station.get_address() + "<br>"
							+ "Bikes Available: " + station.get_numBikesAvailable() + station.get_capacity() + "<br>"
							+ "Docks Available: " + station.get_numDocksAvailable() + "<br>"
							+ "Last Reported: " + station.get_lastReported()
						);
						_activeInfoWindow.open(map, _mapMarkers.get(id));
					}
				});
				
				// Create Lookup Tables for Event Handler
				_mapMarkers.put(station.get_id(), marker);
				_latLongMap.put(latLong.getJSObject(), station.get_id());
			}
		}
	}
}