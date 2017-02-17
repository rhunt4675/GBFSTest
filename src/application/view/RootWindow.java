package application.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.BorderPane;

public class RootWindow extends BorderPane implements Initializable {
	@FXML private MenuBar menu;
	@FXML private MenuItem menuItemExit;
	private TabPane tabpane = new TabPane();
		
	public RootWindow() {
		FXMLLoader fxml = new FXMLLoader();
		fxml.setLocation(getClass().getResource("RootWindow.fxml"));
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
		// Add Button Listeners
		menuItemExit.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				Platform.exit();
				System.exit(0);
			}
		});
		
		// Setup TabPane
		setCenter(tabpane);
		tabpane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
	}
	
	public TabPane getTabPane() {
		return tabpane;
	}
}