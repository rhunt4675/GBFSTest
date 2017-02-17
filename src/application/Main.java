package application;

import application.data.BikeSystem;
import application.view.GraphPanel;
import application.view.MapPanel;
import application.view.RootWindow;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class Main extends Application {
	private RootWindow _rootWindow = new RootWindow();
	private MapPanel _mapPanel = new MapPanel();
	private GraphPanel _graphPanel = new GraphPanel();
	private Stage _primaryStage;
	
	@Override
	public void start(Stage primaryStage) {
		// Setup Root Window
		_primaryStage = primaryStage;
		_primaryStage.setTitle("GBFS Test");
		_primaryStage.setScene(new Scene(_rootWindow));
		_primaryStage.show();
		
		// Setup Map Content
		_rootWindow.getTabPane().getTabs().add(new Tab("Map", _mapPanel));
		_rootWindow.getTabPane().getTabs().add(new Tab("Graph", _graphPanel));
		
		// Close Window Handler
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override public void handle(WindowEvent e) {
				Platform.exit();
				System.exit(0);
			}
		});
		
		// Start Periodic Updater Service
		startUpdateService();
	}
	
	private void startUpdateService() {
		final BikeSystem system = BikeSystem.getSystem("https://gbfs.bcycle.com/bcycle_madison/");

		// Schedule a 10-second Periodic Updater Service
		Timeline updater = new Timeline(new KeyFrame(Duration.seconds(10), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				system.get_stationList().refresh();
				_mapPanel.addBikeStations(system.get_stationList());
			}
			
		}));
		
		updater.setCycleCount(Timeline.INDEFINITE);
		updater.play();
	}

	public static void main(String[] args) {		
		/* Start the Application */
		launch(args);
	}
}