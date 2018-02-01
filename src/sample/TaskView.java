package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class TaskView {

    private User user;
    private Task task;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private JFXButton deleteButton;

    @FXML
    private JFXButton backButton;

    @FXML
    private Text description;

    @FXML
    private JFXCheckBox label;

    @FXML
    void backButtonClick(ActionEvent event) {

    }

    @FXML
    void checkTask(ActionEvent event) {

    }

    @FXML
    void deleteButtonClick(ActionEvent event) {

    }

    @FXML
    void initialize() {
        label.setText(task.getLabel());
        description.setText(task.getDescripion());
    }

    public void setUser(User user) {
        this.user = user;
        System.out.println("Hello, " + user.getUsername() + "!");
    }

    public void setTask(Task task) {
        this.task = task;
        System.out.println("Opened task: " + task.getLabel());
    }
}
