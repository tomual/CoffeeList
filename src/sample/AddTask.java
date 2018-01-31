package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

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
    private Label labelError;

    @FXML
    void initialize() {
        assert labelInput != null : "fx:id=\"labelInput\" was not injected: check your FXML file 'addTask.fxml'.";
        assert descriptionInput != null : "fx:id=\"descriptionInput\" was not injected: check your FXML file 'addTask.fxml'.";
        assert addButton != null : "fx:id=\"addButton\" was not injected: check your FXML file 'addTask.fxml'.";

    }

    public void addButtonClick(ActionEvent actionEvent) {
        String label = labelInput.getText();
        String description = descriptionInput.getText();

        if (validate()) {
            addTask(label, description);


            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("taskList.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Scene scene = new Scene(root);
            Stage pageStage = new Stage();
            TaskList taskList = loader.getController();
            pageStage.setScene(scene);
            taskList.initialize();
            taskList.setUser(user);
            addButton.getScene().getWindow().hide();
            pageStage.show();
        }
    }

    private void addTask(String label, String description) {
        System.out.println(label + ":" + description);
        Database database = new Database();
        Task task = database.createTask(user, label, description);
        System.out.println(task.getTaskid());
    }

    public void setUser(User user) {
        this.user = user;
        System.out.println(user.getUsername() + " is going to add a task!");
    }

    private boolean validate() {
        boolean valid = true;
        labelError.setVisible(false);
        labelInput.setUnFocusColor(Paint.valueOf("#4d4d4d"));

        if(labelInput.getText().isEmpty()) {
            labelError.setVisible(true);
            labelInput.setUnFocusColor(Paint.valueOf("#b91400"));
            valid = false;
        }
        return valid;
    }
}
