package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class Login {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXButton loginButton;

    @FXML
    private JFXButton signupButton;

    @FXML
    private JFXTextField usernameInput;

    @FXML
    private JFXPasswordField passwordInput;

    @FXML
    private Label loginError;

    @FXML
    private Label usernameError;

    @FXML
    private Label passwordError;

    @FXML
    void loginButtonClick(ActionEvent event) {
        String username = usernameInput.getText().trim();
        String password = passwordInput.getText().trim();
        login(username, password);
    }

    @FXML
    void signupButtonClick(ActionEvent event) {
        signupButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("signup.fxml"));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

    private void login(String username, String password) {
        if (!validate(username, password)) {
            return;
        }
        User user = null;
        String hash = "";
        String salt = "";
        Database database = new Database();
        ResultSet resultSet = database.getUser(username);

        try {
            hash = resultSet.getString("password");
            salt = resultSet.getString("salt");
            user = new User(resultSet.getInt("userid"), resultSet.getString("username"), resultSet.getString("email"));
        } catch (SQLException e) {
            System.err.println("User not found: " + e.getMessage());
        }

        MyHash myHash = new MyHash();
        if (myHash.check(password, hash, salt)) {
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
            loginButton.getScene().getWindow().hide();
            pageStage.show();
        } else {
            loginError.setVisible(true);
        }
    }

    private boolean validate(String username, String password) {

        boolean valid = true;
        loginError.setVisible(false);
        usernameError.setVisible(false);
        passwordError.setVisible(false);
        usernameInput.setUnFocusColor(Paint.valueOf("#4d4d4d"));
        passwordInput.setUnFocusColor(Paint.valueOf("#4d4d4d"));

        if(username.isEmpty()) {
            usernameError.setVisible(true);
            usernameInput.setUnFocusColor(Paint.valueOf("#b91400"));
            valid = false;
        }
        if(password.isEmpty()) {
            passwordError.setVisible(true);
            passwordInput.setUnFocusColor(Paint.valueOf("#b91400"));
            valid = false;
        }
        return valid;
    }

    @FXML
    void initialize() {
        assert loginButton != null : "fx:id=\"loginButton\" was not injected: check your FXML file 'login.fxml'.";
        assert signupButton != null : "fx:id=\"signupButton\" was not injected: check your FXML file 'login.fxml'.";
        assert usernameInput != null : "fx:id=\"usernameInput\" was not injected: check your FXML file 'login.fxml'.";
        assert passwordInput != null : "fx:id=\"passwordInput\" was not injected: check your FXML file 'login.fxml'.";

    }
}
