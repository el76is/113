package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final String TABLE_NAME = "usersTable";

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

        String sqlCommand = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(255), " +
                "lastName VARCHAR(255), " +
                "age SMALLINT)";

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sqlCommand);
//            System.out.println("createUsersTable():\t OK");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void dropUsersTable() {

        String sqlCommand = "DROP TABLE IF EXISTS " + TABLE_NAME;
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sqlCommand);
//            System.out.println("dropUsersTable():\t OK");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        String sqlCommand = "INSERT INTO " + TABLE_NAME + " (name, lastName, age) VALUES (?,?,?)";
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlCommand)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
//            System.out.println("saveUser(" + name + "; " + lastName + "; " + age + "):\t OK");
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {

        String sqlCommand = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlCommand)) {
            statement.setLong(1, id);
            statement.executeUpdate();
//            System.out.println("removeUserById(" + id + "):\t OK");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {

        List<User> userList = new LinkedList<>();
        String sqlCommand = "SELECT * FROM " + TABLE_NAME;

        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlCommand);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
            }
//            System.out.println("getAllUsers():\t OK");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {

        String sqlCommand = "TRUNCATE TABLE " + TABLE_NAME;
        try(Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlCommand);
//            System.out.println("cleanUsersTable():\t OK");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
