package Frontend;

import Backend.User;
import Backend.UserDAO;

import javax.swing.*;
import java.awt.*;


public class LoginFrame extends JFrame {

    JTextField usernameField;
    JPasswordField passwordField;
    JButton loginButton;


    public LoginFrame(){

        setTitle("Cafe Monitoring System");

        setSize(500,400);

        //setLayout(null);

        //background.setLayout(null);
        //this.setContentPane(background);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

       // ImageIcon bgIcon = new ImageIcon("cafe_bg.jpg");
        //Image img = bgIcon.getImage().getScaledInstance(500, 400, Image.SCALE_SMOOTH);

        //JLabel background = new JLabel(new ImageIcon(img));
        //background.setLayout(null);
        //this.setContentPane(background);

        //setLayout(null);
        JPanel background = new JPanel() {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                ImageIcon bgIcon = new ImageIcon("cafe_bg.jpg");
                g.drawImage(bgIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };




       // JLabel background = new JLabel(new ImageIcon(img));
        background.setLayout(null); // আগের setBounds যেন ঠিকভাবে কাজ করে
        this.setContentPane(background);

       // Color creamColor = new Color(255, 248, 220);

        JLabel title = new JLabel("Cafe Corner");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(new java.awt.Color(40, 40, 40));




       // title.setForeground(creamColor);
        //title.setFont(new Font("Arial", Font.BOLD, 22));

        title.setBounds(180,30,200,40);
        background.add(title);



        JLabel user = new JLabel("Username");

        user.setBounds(80,100,100,30);

        background.add(user);



        usernameField = new JTextField();

        usernameField.setBounds(180,100,180,30);

        background.add(usernameField);



        JLabel pass = new JLabel("Password");

        pass.setBounds(80,150,100,30);

        background.add(pass);



        passwordField = new JPasswordField();

        passwordField.setBounds(180,150,180,30);

        background.add(passwordField);



        loginButton = UITheme.primaryButton("Login");

        loginButton.setBounds(180,220,120,35);

        background.add(loginButton);



        loginButton.addActionListener(e -> login());



        setVisible(true);

    }



    private void login(){

        String username = usernameField.getText();

        String password = new String(passwordField.getPassword());

        if(username.isEmpty() || password.isEmpty()){

            JOptionPane.showMessageDialog(this,"Username and password cannot be empty");

            return;

        }

        User user = UserDAO.checkLogin(username, password);

        if(user != null){


            // ১. অরেঞ্জ রাউন্ডেড OK বাটন তৈরি
            JButton okButton = new JButton("OK");
            okButton.setBackground(new java.awt.Color(230, 120, 23));
            okButton.setForeground(java.awt.Color.WHITE);
            okButton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
            okButton.setFocusPainted(false);
            okButton.setFocusable(false);

            // ২. নীল বর্ডার বাদ দিয়ে কোণা গোল (Rounded) করা
            okButton.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                    new javax.swing.border.LineBorder(new java.awt.Color(230, 120, 23), 1, true),
               javax.swing.BorderFactory.createEmptyBorder(6, 18, 6, 18)


            ));

            // ৩. ওকে বাটনে ক্লিক করলে ডায়ালগ বন্ধ হওয়া
            okButton.addActionListener(e -> {
                java.awt.Window win = javax.swing.SwingUtilities.getWindowAncestor(okButton);
                if (win != null) win.dispose();
            });

            // ৪. পপ-আপ মেসেজ দেখানো
            JOptionPane.showOptionDialog(
                    this,
                    "Login Successful",
                    "Message",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new Object[]{okButton},
                    okButton
            );






           // UIManager.put("Button.background", new java.awt.Color(230, 120, 23)); // কফি/কমলা কালার
           // UIManager.put("Button.foreground", java.awt.Color.WHITE);            // টেক্সট কালার
          //  UIManager.put("Button.focus", new java.awt.Color(0, 0, 0, 0));        // বর্ডার ফোকাস সরাতে

          //  UIManager.put("Button.focusPainted", Boolean.FALSE);
           // UIManager.put("Button.borderPainted", Boolean.FALSE);


          //  UIManager.put("Button.select", new java.awt.Color(210, 100, 15));



           // JOptionPane.showMessageDialog(this,"Login Successful");

            dispose();

            if(user.getRole().equals("ADMIN")){

                new AdminDashboard(user);

            } else {

                new CashierDashboard(user);

            }

        }

        else{

            JOptionPane.showMessageDialog(this,"Wrong Username or Password");

        }

    }


}