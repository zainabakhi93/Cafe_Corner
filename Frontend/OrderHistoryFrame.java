package Frontend;

import Backend.OrderDAO;
import Backend.User;

import javax.swing.*;
import java.util.ArrayList;

public class OrderHistoryFrame extends JFrame {

    public OrderHistoryFrame(User user){

        setTitle("Today's Order History");

        setSize(500,400);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        DefaultListModelWrapper wrapper = new DefaultListModelWrapper();

        try {

            ArrayList<String> orders = OrderDAO.getTodaysOrders(user.getId());

            if(orders.isEmpty()){
                wrapper.model.addElement("No orders placed today yet.");
            } else {
                for(String line : orders){
                    wrapper.model.addElement(line);
                }
            }

        } catch (Exception e) {
            wrapper.model.addElement("Failed to load order history: " + e.getMessage());
        }

        JList<String> list = new JList<>(wrapper.model);

        add(new JScrollPane(list));

        setVisible(true);

    }

    // small helper class just to hold the list model cleanly
    private static class DefaultListModelWrapper {
        javax.swing.DefaultListModel<String> model = new javax.swing.DefaultListModel<>();
    }

}