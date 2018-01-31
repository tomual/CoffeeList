package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class TaskList {

    private User user;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXListView<String> listView;

    @FXML
    private JFXButton addButton;

    @FXML
    void addButtonClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addTask.fxml"));
            AnchorPane form = loader.load();

            AddTask addTask = loader.getController();
            addTask.initialize();
            addTask.setUser(user);

            anchorPane.getChildren().setAll(form);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        ObservableList<String> observableList = FXCollections.observableArrayList("Test thing", "Another Test thing", "Most Test thing");
        listView.setItems(observableList);
        assert listView != null : "fx:id=\"listView\" was not injected: check your FXML file 'taskList.fxml'.";
        assert addButton != null : "fx:id=\"addButton\" was not injected: check your FXML file 'taskList.fxml'.";

    }

    public void setUser(User user) {
        this.user = user;
        System.out.println("Hello, " + user.getUsername() + "!");
    }
}
