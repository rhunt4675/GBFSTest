package application.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.data.Station;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

public class GraphPanel extends BorderPane implements Initializable {
	@FXML private TableView<Station> tableView;

	public GraphPanel() {
		FXMLLoader fxml = new FXMLLoader();
		fxml.setLocation(getClass().getResource("GraphPanel.fxml"));
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
		// TODO Auto-generated method stub
	}
}
