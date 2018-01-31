package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
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

public class Signup {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXButton signupButton;

    @FXML
    private JFXButton loginButton;

    @FXML
    private JFXTextField usernameInput;

    @FXML
    private JFXPasswordField passwordInput;

    @FXML
    private JFXTextField emailInput;

    @FXML
    private JFXPasswordField confirmPasswordInput;

    @FXML
    private Label usernameError;

    @FXML
    private Label emailError;

    @FXML
    private Label passwordError;

    @FXML
    private Label confirmPasswordError;

    @FXML
    private Label signupError;

    @FXML
    void loginButtonClick(ActionEvent event) {
        signupButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("login.fxml"));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void signupButtonClick(ActionEvent event) {
        String username = usernameInput.getText().trim();
        String email = emailInput.getText().trim();
        String password = passwordInput.getText().trim();
        String confirmPassword = confirmPasswordInput.getText().trim();
        if(validate()) {
            signup(username, email, password);
        }
    }

    private void signup(String username, String email, String password) {
        Database database = new Database();
        User user = database.createUser(username, email, password);

        if (user == null) {
            signupError.setText("Username already exists");
            signupError.setVisible(true);
            usernameInput.setUnFocusColor(Paint.valueOf("#b91400"));
            return;
        }

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
    }

    private boolean validate() {
        boolean valid = true;

        signupError.setVisible(false);
        usernameError.setVisible(false);
        emailError.setVisible(false);
        passwordError.setVisible(false);
        confirmPasswordError.setVisible(false);
        usernameInput.setUnFocusColor(Paint.valueOf("#4d4d4d"));
        emailInput.setUnFocusColor(Paint.valueOf("#4d4d4d"));
        passwordInput.setUnFocusColor(Paint.valueOf("#4d4d4d"));
        confirmPasswordInput.setUnFocusColor(Paint.valueOf("#4d4d4d"));

        if(usernameInput.getText().isEmpty()) {
            usernameError.setVisible(true);
            usernameInput.setUnFocusColor(Paint.valueOf("#b91400"));
            valid = false;
        }
        if(emailInput.getText().isEmpty()) {
            emailError.setVisible(true);
            emailInput.setUnFocusColor(Paint.valueOf("#b91400"));
            valid = false;
        }
        if(passwordInput.getText().isEmpty()) {
            passwordError.setVisible(true);
            passwordInput.setUnFocusColor(Paint.valueOf("#b91400"));
            valid = false;
        }
        if(confirmPasswordInput.getText().isEmpty()) {
            confirmPasswordError.setVisible(true);
            confirmPasswordInput.setUnFocusColor(Paint.valueOf("#b91400"));
            valid = false;
        }
        if(valid && !passwordInput.getText().equals(confirmPasswordInput.getText())) {
            signupError.setText("Passwords do not match");
            signupError.setVisible(true);
            passwordInput.setUnFocusColor(Paint.valueOf("#b91400"));
            confirmPasswordInput.setUnFocusColor(Paint.valueOf("#b91400"));
            valid = false;
        }
        return valid;
    }

    @FXML
    void initialize() {
        assert signupButton != null : "fx:id=\"signupButton\" was not injected: check your FXML file 'signup.fxml'.";
        assert loginButton != null : "fx:id=\"loginButton\" was not injected: check your FXML file 'signup.fxml'.";
        assert usernameInput != null : "fx:id=\"usernameInput\" was not injected: check your FXML file 'signup.fxml'.";
        assert passwordInput != null : "fx:id=\"passwordInput\" was not injected: check your FXML file 'signup.fxml'.";
        assert emailInput != null : "fx:id=\"emailInput\" was not injected: check your FXML file 'signup.fxml'.";
        assert confirmPasswordInput != null : "fx:id=\"confirmPasswordInput\" was not injected: check your FXML file 'signup.fxml'.";

    }
}
