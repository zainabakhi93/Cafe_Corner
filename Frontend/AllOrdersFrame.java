package Frontend;

import Backend.OrderDAO;

import javax.swing.*;
import java.util.ArrayList;

public class AllOrdersFrame extends JFrame {

    public AllOrdersFrame(){

        setTitle("All Orders");

        setSize(550,400);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        DefaultListModel<String> model = new DefaultListModel<>();

        try {

            ArrayList<String> orders = OrderDAO.getAllOrders();

            if(orders.isEmpty()){
                model.addElement("No orders placed yet.");
            } else {
                for(String line : orders){
                    model.addElement(line);
                }
            }

        } catch (Exception e) {
            model.addElement("Failed to load orders: " + e.getMessage());
        }

        JList<String> list = new JList<>(model);

        add(new JScrollPane(list));

        setVisible(true);

    }

}