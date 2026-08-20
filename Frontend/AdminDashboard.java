package Frontend;

import Backend.ClockThread;
import Backend.User;

import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {

    User currentUser;

    public AdminDashboard(User user) {

        this.currentUser = user;

        setTitle("Admin Dashboard - " + user.getFullName());
        setSize(640, 520);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(UITheme.BACKGROUND);

        // ---- Consistent top bar: no "Back" here (this is the top-level dashboard), only Logout ----
        JPanel topBar = UITheme.createTopBar("Admin Dashboard", null, e -> {
            dispose();
            new LoginFrame();
        });
        add(topBar, BorderLayout.NORTH);

        // ---- Body ----
        JPanel body = new JPanel(null);
        UITheme.styleRootPanel(body);
        body.setBackground(UITheme.BACKGROUND);
        add(body, BorderLayout.CENTER);

        JLabel welcome = new JLabel("Welcome, " + user.getFullName());
        welcome.setFont(UITheme.FONT_HEADING);
        welcome.setForeground(UITheme.TEXT_DARK);
        welcome.setBounds(24, 10, 350, 30);
        body.add(welcome);

        JLabel clockLabel = new JLabel("Time");
        clockLabel.setFont(UITheme.FONT_LABEL);
        clockLabel.setForeground(UITheme.TEXT_LIGHT);
        clockLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        clockLabel.setBounds(380, 10, 190, 30);
        body.add(clockLabel);

        ClockThread clock = new ClockThread(clockLabel);
        clock.start();

        // ---- Menu grid (consistent spacing: 24px gap, 2 columns) ----
        int colW = 270, rowH = 56, gap = UITheme.GAP_LG;
        int x1 = 24, x2 = x1 + colW + gap;
        int y1 = 70, y2 = y1 + rowH + gap;

        UITheme.RoundedButton manageMenu = UITheme.primaryButton("Manage Menu");
        manageMenu.setBounds(x1, y1, colW, rowH);
        body.add(manageMenu);
        manageMenu.addActionListener(e -> new ManageMenuFrame(user));

        UITheme.RoundedButton allOrders = UITheme.primaryButton("All Orders");
        allOrders.setBounds(x2, y1, colW, rowH);
        body.add(allOrders);
        allOrders.addActionListener(e -> new AllOrdersFrame());

        UITheme.RoundedButton salesReport = UITheme.primaryButton("Sales Report");
        salesReport.setBounds(x1, y2, colW, rowH);
        body.add(salesReport);
        salesReport.addActionListener(e -> new SalesReportFrame());

        UITheme.RoundedButton manageStaff = UITheme.primaryButton("Manage Staff");
        manageStaff.setBounds(x2, y2, colW, rowH);
        body.add(manageStaff);
        manageStaff.addActionListener(e -> new ManageStaffFrame());

        setVisible(true);
    }
}