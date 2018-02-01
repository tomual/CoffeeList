package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;

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
        ObservableList<String> observableList = FXCollections.observableArrayList();;
        Database database = new Database();
        if (user != null) {
            observableList = database.getTasks(user.getUserId());
        }
        listView.setItems(observableList);
        listView.setCellFactory(param -> new JFXCell());
        assert listView != null : "fx:id=\"listView\" was not injected: check your FXML file 'taskList.fxml'.";
        assert addButton != null : "fx:id=\"addButton\" was not injected: check your FXML file 'taskList.fxml'.";

    }

    public void setUser(User user) {
        this.user = user;
        System.out.println("Hello, " + user.getUsername() + "!");
    }

    static class JFXCell extends JFXListCell<String> {
        HBox hBox = new HBox();
        JFXCheckBox checkBox = new JFXCheckBox();
        Label labelLabel = new Label();

        Pane pane = new Pane();

        public JFXCell() {
            super();

            hBox.getChildren().addAll(checkBox, labelLabel);
            hBox.setHgrow(pane, Priority.ALWAYS);
            hBox.setPadding(new Insets(5, 2.5, 5, 2.5));
            checkBox.setCheckedColor(Paint.valueOf("#29434e"));
        }

        public void updateItem(String label, boolean checked) {
            super.updateItem(label, checked);
            setText(null);

            if(label != null && !checked) {
                labelLabel.setText(label);
                setGraphic(hBox);
            }
        }
    }
}
