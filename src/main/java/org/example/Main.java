package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // Load properties from the database.properties file
        Properties properties = loadProperties();
        // Get properties values
        String url = properties.getProperty("url");
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        // JDBC variables for opening, closing, and managing connection
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            // SQL query for insertion
            String insertQuery = "INSERT INTO public.contact (id, name) VALUES (?, ?)";

            // Creating a PreparedStatement
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                // Setting values for placeholders
                preparedStatement.setString(1, "1");
                preparedStatement.setString(2, "çetin");

                // Executing the insertion
                int affectedRows = preparedStatement.executeUpdate();

                System.out.println(affectedRows + " row(s) affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("database.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find database.properties");
                return properties;
            }
            // load a properties file from class path, inside static method
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return properties;
    }
}