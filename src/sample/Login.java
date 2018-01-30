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
        String hash = "";
        String salt = "";
        Database database = new Database();
        ResultSet resultSet = database.getUser(username);
        try {
            hash = resultSet.getString("password");
            salt = resultSet.getString("salt");
            User user = new User(resultSet.getInt("userid"), resultSet.getString("username"), resultSet.getString("email"));
        } catch (SQLException e) {
            System.err.println("User not found: " + e.getMessage());
        }
        MyHash myHash = new MyHash();
        if (myHash.check(password, hash, salt)) {
        }

    }

    @FXML
    void initialize() {
        assert loginButton != null : "fx:id=\"loginButton\" was not injected: check your FXML file 'login.fxml'.";
        assert signupButton != null : "fx:id=\"signupButton\" was not injected: check your FXML file 'login.fxml'.";
        assert usernameInput != null : "fx:id=\"usernameInput\" was not injected: check your FXML file 'login.fxml'.";
        assert passwordInput != null : "fx:id=\"passwordInput\" was not injected: check your FXML file 'login.fxml'.";

    }
}
