package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

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
    void loginButtonClick(ActionEvent event) {

    }

    @FXML
    void signupButtonClick(ActionEvent event) {

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
