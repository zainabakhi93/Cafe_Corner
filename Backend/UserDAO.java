package Backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO {

    public static User checkLogin(String username, String password) {
        password = PasswordUtil.hash(password);

        String sql = "SELECT user_id, username, role, full_name FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("role"),
                        rs.getString("full_name")
                );
            }

        } catch (SQLException e) {
            System.out.println("Login check failed: " + e.getMessage());
        }

        return null; // no match found, or an error occurred
    }
    public static ArrayList<User> getAllUsers() throws SQLException {

        ArrayList<User> list = new ArrayList<>();

        String sql = "SELECT user_id, username, role, full_name FROM users";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while(rs.next()){
                list.add(new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("role"),
                        rs.getString("full_name")
                ));
            }

        }

        return list;

    }

    public static void addUser(String username, String password, String role, String fullName) throws SQLException {

        if(username == null || username.trim().isEmpty()){
            throw new IllegalArgumentException("Username cannot be empty");
        }

        if(password == null || password.trim().isEmpty()){
            throw new IllegalArgumentException("Password cannot be empty");
        }

        if(!role.equals("ADMIN") && !role.equals("CASHIER")){
            throw new IllegalArgumentException("Role must be ADMIN or CASHIER");
        }

        String sql = "INSERT INTO users (username, password, role, full_name) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, PasswordUtil.hash(password));
            ps.setString(3, role);
            ps.setString(4, fullName);

            ps.executeUpdate();

        }

    }

    public static void deleteUser(int userId) throws SQLException {

        String sql = "DELETE FROM users WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);

            int rows = ps.executeUpdate();

            if(rows == 0){
                throw new IllegalArgumentException("No user found with id " + userId);
            }

        }

    }

}