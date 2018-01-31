package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class AddTask {

    private User user;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXTextField labelInput;

    @FXML
    private JFXTextArea descriptionInput;

    @FXML
    private JFXButton addButton;

    @FXML
    void initialize() {
        assert labelInput != null : "fx:id=\"labelInput\" was not injected: check your FXML file 'addTask.fxml'.";
        assert descriptionInput != null : "fx:id=\"descriptionInput\" was not injected: check your FXML file 'addTask.fxml'.";
        assert addButton != null : "fx:id=\"addButton\" was not injected: check your FXML file 'addTask.fxml'.";

    }

    public void addButtonClick(ActionEvent actionEvent) {
        String label = labelInput.getText();
        String description = descriptionInput.getText();
        System.out.println(label + ":" + description);
        Database database = new Database();
        Task task = database.createTask(user, label, description);
        System.out.println(task.getTaskid());
    }

    public void setUser(User user) {
        this.user = user;
        System.out.println(user.getUsername() + " is going to add a task!");
    }
}
