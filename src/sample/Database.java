package sample;

import com.mysql.jdbc.Statement;

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

    public int createUser(String username, String email, String password) {
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
                    return generatedKeys.getInt(1);
                }
                else {
                    throw new SQLException("Creating user failed");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
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
}
