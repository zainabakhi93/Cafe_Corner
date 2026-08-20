package Frontend;

import Backend.ClockThread;
import Backend.User;

import javax.swing.*;
import java.awt.*;

public class CashierDashboard extends JFrame {

    JLabel clockLabel;
    User currentUser;

    public CashierDashboard(User user) {

        this.currentUser = user;

        setTitle("Cashier Dashboard - " + user.getFullName());
        setSize(640, 500);
        setLayout(new BorderLayout());
        setLayout(new BorderLayout());

        this.add(UITheme.createTopBar("Cashier Dashboard",
                null, // মূল ড্যাশবোর্ড তাই Back বাটন লাগবে না
                e -> {
                    new LoginFrame().setVisible(true);
                    this.dispose();
                }
        ), BorderLayout.NORTH);


        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(UITheme.BACKGROUND);

        // ---- Consistent top bar (no Back — this is the top-level dashboard) ----
        JPanel topBar = UITheme.createTopBar("Cashier Dashboard", null, e -> {
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

        clockLabel = new JLabel("Time");
        clockLabel.setFont(UITheme.FONT_LABEL);
        clockLabel.setForeground(UITheme.TEXT_LIGHT);
        clockLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        clockLabel.setBounds(380, 10, 190, 30);
        body.add(clockLabel);

        ClockThread clock = new ClockThread(clockLabel);
        clock.start();

        // ---- Menu grid (same spacing pattern as AdminDashboard) ----
        int colW = 270, rowH = 56, gap = UITheme.GAP_LG;
        int x1 = 24, x2 = x1 + colW + gap;
        int y1 = 70, y2 = y1 + rowH + gap;

        UITheme.RoundedButton newOrder = UITheme.primaryButton("New Order");
        newOrder.setBounds(x1, y1, colW, rowH);
        body.add(newOrder);
        newOrder.addActionListener(e -> new NewOrderFrame(currentUser));

        UITheme.RoundedButton viewMenu = UITheme.primaryButton("View Menu");
        viewMenu.setBounds(x2, y1, colW, rowH);
        body.add(viewMenu);
        viewMenu.addActionListener(e -> new MenuFrame());

        UITheme.RoundedButton history = UITheme.primaryButton("Order History");
        history.setBounds(x1, y2, colW, rowH);
        body.add(history);
        history.addActionListener(e -> new OrderHistoryFrame(currentUser));

        setVisible(true);
    }
}