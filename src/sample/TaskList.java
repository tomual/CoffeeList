package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
    private JFXListView<Task> listView;

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

    public void viewTask(Task task) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("taskView.fxml"));
            AnchorPane form = loader.load();

            TaskView taskView = loader.getController();
            taskView.setTask(task);
            taskView.setUser(user);
            taskView.initialize();
            anchorPane.getChildren().setAll(form);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        ObservableList<Task> observableList = FXCollections.observableArrayList();;
        Database database = new Database();
        if (user != null) {
            observableList = database.getTasks(user.getUserId());
        }
        listView.setItems(observableList);
        listView.setCellFactory(param -> new JFXCell());
    }

    void updateList() {
        ObservableList<Task> observableList = FXCollections.observableArrayList();;
        Database database = new Database();
        if (user != null) {
            observableList = database.getTasks(user.getUserId());
        }
        listView.setItems(observableList);
    }

    public void setUser(User user) {
        this.user = user;
        System.out.println("Hello, " + user.getUsername() + "!");
    }

    public void checkboxClick(JFXCheckBox checkBox, Task task) {
        Database database = new Database();
        if(checkBox.isSelected()) {
            database.setTaskComplete(task);
        } else {
            database.setTaskIncomplete(task);
        }
        updateList();
    }

    class JFXCell extends JFXListCell<Task> {
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

        @Override
        protected void updateItem(Task task, boolean empty) {
            super.updateItem(task, empty);
            setText(null);

            if (empty || task == null || task.getLabel() == null) {
                setText(null);
            } else {
                labelLabel.setText(task.getLabel());
                setGraphic(hBox);
                setOnMouseClicked(click -> {
                    if (click.getClickCount() == 2) {
                        viewTask(task);
                    }
                });
                checkBox.setSelected(task.isComplete());
                checkBox.setOnMouseClicked(event -> checkboxClick(checkBox, task));
            }
        }
    }

    @FXML
    void menuAddTaskClick(ActionEvent event) {
        addButtonClick(event);
    }

    @FXML
    void menuDeleteAllClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.NONE, "Delete " + listView.getItems().size() + " tasks?", ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            deleteAll();
            updateList();
        }
    }

    @FXML
    void menuDeleteCompleteClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.NONE, "Delete " + getCountComplete() + " complete tasks?", ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            deleteComplete();
            updateList();
        }
    }

    @FXML
    void menuMarkAllCompleteClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.NONE, "Mark " + listView.getItems().size() + " tasks as complete?", ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            markAllComplete();
            updateList();
        }
    }

    @FXML
    void menuMarkAllIncompleteClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.NONE, "Mark " + listView.getItems().size() + " tasks as incomplete?", ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            markAllIncomplete();
            updateList();
        }
    }
    
    int getCountComplete() {
        int count = 0;
        ObservableList<Task> tasks = listView.getItems();
        for (Task task: tasks) {
            if(task.isComplete()) {
                count++;
            }
        }
        return count;
    }

    void deleteAll() {
        Database database = new Database();
        ObservableList<Task> tasks = listView.getItems();
        for (Task task: tasks) {
            database.deleteTask(task);
        }
    }

    void deleteComplete() {
        Database database = new Database();
        ObservableList<Task> tasks = listView.getItems();
        for (Task task: tasks) {
            if(task.isComplete()) {
                database.deleteTask(task);
            }
        }
    }

    void markAllComplete() {
        Database database = new Database();
        ObservableList<Task> tasks = listView.getItems();
        for (Task task: tasks) {
            if(!task.isComplete()) {
                database.setTaskComplete(task);
            }
        }
    }

    void markAllIncomplete() {
        Database database = new Database();
        ObservableList<Task> tasks = listView.getItems();
        for (Task task: tasks) {
            if(task.isComplete()) {
                database.setTaskIncomplete(task);
            }
        }
    }



}
