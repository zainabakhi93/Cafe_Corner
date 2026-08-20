package Backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAO {

    public static int placeOrder(int cashierId, ArrayList<CartItem> cart) throws SQLException {

        if(cart.isEmpty()){
            throw new IllegalArgumentException("Cannot place an order with an empty cart");
        }

        double total = 0;
        for(CartItem ci : cart){
            total += ci.getSubtotal();
        }

        Connection conn = null;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); // start transaction

            String orderSql = "INSERT INTO orders (cashier_id, total) VALUES (?, ?)";
            PreparedStatement orderPs = conn.prepareStatement(orderSql, PreparedStatement.RETURN_GENERATED_KEYS);
            orderPs.setInt(1, cashierId);
            orderPs.setDouble(2, total);
            orderPs.executeUpdate();

            ResultSet keys = orderPs.getGeneratedKeys();
            int orderId = 0;
            if(keys.next()){
                orderId = keys.getInt(1);
            }

            String detailSql = "INSERT INTO order_details (order_id, item_id, quantity, subtotal) VALUES (?, ?, ?, ?)";
            PreparedStatement detailPs = conn.prepareStatement(detailSql);

            for(CartItem ci : cart){
                detailPs.setInt(1, orderId);
                detailPs.setInt(2, ci.getItem().getId());
                detailPs.setInt(3, ci.getQuantity());
                detailPs.setDouble(4, ci.getSubtotal());
                detailPs.addBatch();
            }

            detailPs.executeBatch();

            conn.commit(); // only save everything if all inserts succeeded

            return orderId;

        } catch (SQLException e) {

            if(conn != null){
                try {
                    conn.rollback(); // undo the order if details failed
                } catch (SQLException rollbackEx) {
                    System.out.println("Rollback failed: " + rollbackEx.getMessage());
                }
            }

            throw e;

        } finally {

            if(conn != null){
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException closeEx) {
                    System.out.println("Failed to close connection: " + closeEx.getMessage());
                }
            }

        }

    }
    public static ArrayList<String> getTodaysOrders(int cashierId) throws SQLException {

        ArrayList<String> results = new ArrayList<>();

        String sql = "SELECT order_id, order_time, total FROM orders " +
                     "WHERE cashier_id = ? AND DATE(order_time) = CURDATE() " +
                     "ORDER BY order_time DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, cashierId);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                String line = "Order #" + rs.getInt("order_id")
                        + "   " + rs.getTimestamp("order_time")
                        + "   Total: " + rs.getDouble("total");
                results.add(line);
            }

        }

        return results;

    }
    public static ArrayList<String> getAllOrders() throws SQLException {

        ArrayList<String> results = new ArrayList<>();

        String sql = "SELECT o.order_id, o.order_time, o.total, u.full_name " +
                     "FROM orders o JOIN users u ON o.cashier_id = u.user_id " +
                     "ORDER BY o.order_time DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while(rs.next()){
                String line = "Order #" + rs.getInt("order_id")
                        + "   " + rs.getTimestamp("order_time")
                        + "   Cashier: " + rs.getString("full_name")
                        + "   Total: " + rs.getDouble("total");
                results.add(line);
            }

        }

        return results;

    }

    public static double getTodaysSalesTotal() throws SQLException {

        String sql = "SELECT SUM(total) AS daily_total FROM orders WHERE DATE(order_time) = CURDATE()";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if(rs.next()){
                return rs.getDouble("daily_total");
            }

        }

        return 0;

    }

    public static int getTodaysOrderCount() throws SQLException {

        String sql = "SELECT COUNT(*) AS cnt FROM orders WHERE DATE(order_time) = CURDATE()";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if(rs.next()){
                return rs.getInt("cnt");
            }

        }

        return 0;

    }

}