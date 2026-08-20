package Backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MenuDAO {

    public static ArrayList<MenuItem> getAllItems() {

        ArrayList<MenuItem> list = new ArrayList<>();

        String sql = "SELECT item_id, name, price FROM menu_items WHERE available = TRUE";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("item_id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                list.add(new MenuItem(id, name, price));
            }

        } catch (SQLException e) {
            System.out.println("Failed to load menu: " + e.getMessage());
        }

        return list;
    }
    public static void addItem(String name, double price, String category) throws SQLException {

        if(name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("Item name cannot be empty");
        }

        if(price <= 0){
            throw new IllegalArgumentException("Price must be greater than 0");
        }

        String sql = "INSERT INTO menu_items (name, price, category) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setString(3, category);

            ps.executeUpdate();

        }

    }

    public static void updateItem(int id, String name, double price) throws SQLException {

        if(name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("Item name cannot be empty");
        }

        if(price <= 0){
            throw new IllegalArgumentException("Price must be greater than 0");
        }

        String sql = "UPDATE menu_items SET name = ?, price = ? WHERE item_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setInt(3, id);

            int rows = ps.executeUpdate();

            if(rows == 0){
                throw new IllegalArgumentException("No menu item found with id " + id);
            }

        }

    }

    public static void deleteItem(int id) throws SQLException {

        String sql = "UPDATE menu_items SET available = FALSE WHERE item_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if(rows == 0){
                throw new IllegalArgumentException("No menu item found with id " + id);
            }

        }

    }

}