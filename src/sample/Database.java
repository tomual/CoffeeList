package sample;

import com.mysql.jdbc.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class Database {

    private Connection connection;
    private static String database;
    private static String dbuser;
    private static String dbpassword;

    public Database() {
        loadConfig();
        connect();
    }

    public static void loadConfig() {
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream("config.properties");
            prop.load(input);
            database = prop.getProperty("database");
            dbuser = prop.getProperty("dbuser");
            dbpassword = prop.getProperty("dbpassword");
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Connection connect() {
        loadConfig();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(database, dbuser, dbpassword);
            System.out.println(connection.getCatalog());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public Task createTask(User user, String label, String description) {
        String query = "INSERT INTO tasks (userid, label, description) values (?, ?, ?)";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, user.getUserId());
            preparedStatement.setString(2, label);
            preparedStatement.setString(3, description);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating task failed");
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Task task = new Task(generatedKeys.getInt(1), user.getUserId(), label, description, new java.util.Date());
                    return task;
                }
                else {
                    throw new SQLException("Creating task failed");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User createUser(String username, String email, String password) {
        MyHash hash = new MyHash(password);
        String query = "INSERT INTO users (username, email, password, salt) values (?, ?, ?, ?)";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, hash.getHash());
            preparedStatement.setString(4, hash.getSalt());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed");
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    User user = new User(generatedKeys.getInt(1), username, email);
                    return user;
                }
                else {
                    throw new SQLException("Creating user failed");
                }
            }
        } catch (SQLException e) {
            return null;
        }
    }

    public ResultSet getUser(String username) {
        String query = "SELECT * FROM users WHERE username = ?";

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet;
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ObservableList<Task> getTasks(int userId) {
        ObservableList<Task> observableList = FXCollections.observableArrayList();;
        System.out.println("getTasks");
        String query = "SELECT * FROM tasks WHERE userid = ?";

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                System.out.println("Adding: " + resultSet.getString("label"));
                Task task = new Task(resultSet.getInt("taskid"), resultSet.getInt("userid"), resultSet.getString("label"), resultSet.getString("description"), resultSet.getDate("created"));
                observableList.add(task);
            }
            preparedStatement.close();
            return observableList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteTask(Task task) {
        String query = "DELETE FROM tasks WHERE taskid = ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, task.getTaskid());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
