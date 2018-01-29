package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class AddItem {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXListView<?> listView;

    @FXML
    private JFXButton addButton;

    @FXML
    void addButtonClick(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert listView != null : "fx:id=\"listView\" was not injected: check your FXML file 'addItem.fxml'.";
        assert addButton != null : "fx:id=\"addButton\" was not injected: check your FXML file 'addItem.fxml'.";

    }
}
