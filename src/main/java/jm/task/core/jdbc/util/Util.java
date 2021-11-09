package jm.task.core.jdbc.util;

import java.io.*;
import java.nio.file.*;
import java.sql.*;
import java.util.*;

public class Util {

    public static Connection getConnection() {

        Properties props = new Properties();
        try (InputStream in = Files.newInputStream(Paths.get("resources/database.properties"))) {
            props.load(in);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        String url = props.getProperty("url");
        String username = props.getProperty("username");
        String password = props.getProperty("password");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver")
                    .getDeclaredConstructor()
                    .newInstance();

            Connection connection = DriverManager.getConnection(url, username, password);
//            System.out.println("Connection to DB successfully!");
            return connection;

        } catch (Exception ex) {
            System.out.println("Connection failed... :" + ex);
            ex.printStackTrace();
        }
        return null;
    }
}

