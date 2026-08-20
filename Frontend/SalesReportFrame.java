package Frontend;

import Backend.OrderDAO;

import javax.swing.*;
import java.awt.*;

public class SalesReportFrame extends JFrame {

    public SalesReportFrame(){

        setTitle("Sales Report");

        setSize(400,250);

        setLayout(null);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel titleLabel = new JLabel("Today's Sales Report");

        titleLabel.setFont(new Font("Arial",Font.BOLD,18));

        titleLabel.setBounds(70,20,300,30);

        add(titleLabel);

        JLabel countLabel = new JLabel("Orders today: ...");

        countLabel.setBounds(50,80,300,30);

        add(countLabel);

        JLabel totalLabel = new JLabel("Total sales: ...");

        totalLabel.setBounds(50,120,300,30);

        add(totalLabel);

        try {

            int count = OrderDAO.getTodaysOrderCount();

            double total = OrderDAO.getTodaysSalesTotal();

            countLabel.setText("Orders today: " + count);

            totalLabel.setText("Total sales: " + total);

        } catch (Exception e) {

            countLabel.setText("Failed to load report");

            totalLabel.setText(e.getMessage());

        }

        setVisible(true);

    }

}