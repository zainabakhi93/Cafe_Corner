package Frontend;

import Backend.User;
import Backend.UserDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class ManageStaffFrame extends JFrame {

    DefaultTableModel model;
    JTable table;
    ArrayList<User> users;

    public ManageStaffFrame(){

        setTitle("Manage Staff");

        setSize(550,400);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columns = {"ID","Username","Role","Full Name"};

        model = new DefaultTableModel(columns, 0);

        table = new JTable(model);

        add(new JScrollPane(table), java.awt.BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        JButton addBtn = new JButton("Add Staff");
        JButton deleteBtn = new JButton("Delete Selected");
        JButton refreshBtn = new JButton("Refresh");

        styleOrangeButton(addBtn);
        styleOrangeButton(deleteBtn);
        styleOrangeButton(refreshBtn);

        buttonPanel.add(addBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(refreshBtn);

        add(buttonPanel, java.awt.BorderLayout.SOUTH);

        addBtn.addActionListener(e -> addStaff());

        deleteBtn.addActionListener(e -> deleteStaff());

        refreshBtn.addActionListener(e -> loadUsers());

        loadUsers();

        setVisible(true);

    }

    private void loadUsers(){

        try {

            users = UserDAO.getAllUsers();

            model.setRowCount(0);

            for(User u : users){
                model.addRow(new Object[]{u.getId(), u.getUsername(), u.getRole(), u.getFullName()});
            }

        } catch (Exception e) {
            customMessageDialog("Failed to load staff: " + e.getMessage());
        }

    }

    private void addStaff(){

        try {

            // 👈 কাস্টম অরেঞ্জ ইনপুট ডায়ালগ ব্যবহার করা হলো
            String username = customInputDialog("Username:");
            if(username == null || username.trim().isEmpty()) return;

            String password = customInputDialog("Password:");
            if(password == null || password.trim().isEmpty()) return;

            String[] roles = {"CASHIER","ADMIN"};

           // String role = (String) JOptionPane.showInputDialog(this, "Role:",
                   // "Select Role", JOptionPane.QUESTION_MESSAGE, null, roles, roles[0]);

            String role = customComboBoxDialog("Role:", roles);

            if(role == null) return;

            String fullName = customInputDialog("Full name:");
            if(fullName == null || fullName.trim().isEmpty()) return;

            UserDAO.addUser(username, password, role, fullName);

            customMessageDialog("Staff member added"); // 👈 কাস্টম অরেঞ্জ মেসেজ

            loadUsers();

        } catch (Exception e) {
            customMessageDialog("Failed to add staff: " + e.getMessage());
        }

    }

    private void deleteStaff(){

        int row = table.getSelectedRow();

        if(row == -1){
            customMessageDialog("Select a staff member first");
            return;
        }

        try {

            User selected = users.get(row);

           // int confirm = JOptionPane.showConfirmDialog(this,
                   // "Delete " + selected.getUsername() + "?", "Confirm",
                   // JOptionPane.YES_NO_OPTION);

           // if(confirm != JOptionPane.YES_OPTION) return;

            boolean confirm = customConfirmDialog("Delete " + selected.getUsername() + "?");

            if(!confirm) return;

            UserDAO.deleteUser(selected.getId());

            customMessageDialog("Staff member deleted"); // 👈 কাস্টম অরেঞ্জ মেসেজ

            loadUsers();

        } catch (Exception e) {
            customMessageDialog("Failed to delete: " + e.getMessage()
                    + "\n(This usually means they already have orders on record.)");
        }

    }

    // বাটনগুলোকে অরেঞ্জ, রাউন্ডেড এবং বর্ডারলেস করার মেথড
    private void styleOrangeButton(JButton btn) {
        btn.setForeground(java.awt.Color.WHITE);
        btn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));

        btn.setFocusPainted(false);
        btn.setFocusable(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);

        btn.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(java.awt.Graphics g, javax.swing.JComponent c) {
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new java.awt.Color(230, 120, 23)); // কফি-অরেঞ্জ কালার
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 15, 15); // রাউন্ডেড
                g2.dispose();
                super.paint(g, c);
            }
        });
    }

    // কাস্টম অরেঞ্জ OK বাটনের মেসেজ ডায়ালগ মেথড
    private void customMessageDialog(String messageText) {
        JButton okBtn = new JButton("OK");
        styleOrangeButton(okBtn);

        okBtn.addActionListener(e -> {
            java.awt.Window win = javax.swing.SwingUtilities.getWindowAncestor(okBtn);
            if (win != null) win.dispose();
        });

        JOptionPane.showOptionDialog(
                this,
                messageText,
                "Message",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new Object[]{okBtn},
                okBtn
        );
    }

    // কাস্টম অরেঞ্জ OK ও Cancel বাটনের ইনপুট ডায়ালগ মেথড
    private String customInputDialog(String messageText) {
        javax.swing.JTextField inputField = new javax.swing.JTextField(15);
        JButton okBtn = new JButton("OK");
        JButton cancelBtn = new JButton("Cancel");
        styleOrangeButton(okBtn);
        styleOrangeButton(cancelBtn);

        final String[] result = {null};

        okBtn.addActionListener(e -> {
            result[0] = inputField.getText();
            java.awt.Window win = javax.swing.SwingUtilities.getWindowAncestor(okBtn);
            if (win != null) win.dispose();
        });

        cancelBtn.addActionListener(e -> {
            java.awt.Window win = javax.swing.SwingUtilities.getWindowAncestor(cancelBtn);
            if (win != null) win.dispose();
        });

        Object[] msg = { messageText, inputField };

        JOptionPane.showOptionDialog(
                this, msg, "Input",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, new Object[]{okBtn, cancelBtn}, okBtn
        );

        return result[0];
    }


    // ড্রপডাউন (Role Selection) এর জন্য কাস্টম অরেঞ্জ ডায়ালগ মেথড
    private String customComboBoxDialog(String messageText, String[] options) {
        JComboBox<String> comboBox = new JComboBox<>(options);
        JButton okBtn = new JButton("OK");
        JButton cancelBtn = new JButton("Cancel");

        styleOrangeButton(okBtn);
        styleOrangeButton(cancelBtn);

        final String[] result = {null};

        okBtn.addActionListener(e -> {
            result[0] = (String) comboBox.getSelectedItem();
            java.awt.Window win = javax.swing.SwingUtilities.getWindowAncestor(okBtn);
            if (win != null) win.dispose();
        });

        cancelBtn.addActionListener(e -> {
            java.awt.Window win = javax.swing.SwingUtilities.getWindowAncestor(cancelBtn);
            if (win != null) win.dispose();
        });

        Object[] msg = { messageText, comboBox };

        JOptionPane.showOptionDialog(
                this, msg, "Select Role",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, new Object[]{okBtn, cancelBtn}, okBtn
        );

        return result[0];
    }


    // কাস্টম অরেঞ্জ Yes ও No বাটনের কনফার্মেশন ডায়ালগ মেথড
    private boolean customConfirmDialog(String messageText) {
        JButton yesBtn = new JButton("Yes");
        JButton noBtn = new JButton("No");

        // 👈 বাটন দুটিকে অরেঞ্জ ও সাইডে রাউন্ডেড করা হচ্ছে
        styleOrangeButton(yesBtn);
        styleOrangeButton(noBtn);

        final boolean[] userChoice = {false};

        yesBtn.addActionListener(e -> {
            userChoice[0] = true;
            java.awt.Window win = javax.swing.SwingUtilities.getWindowAncestor(yesBtn);
            if (win != null) win.dispose();
        });

        noBtn.addActionListener(e -> {
            userChoice[0] = false;
            java.awt.Window win = javax.swing.SwingUtilities.getWindowAncestor(noBtn);
            if (win != null) win.dispose();
        });

        JOptionPane.showOptionDialog(
                this,
                messageText,
                "Confirm",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new Object[]{yesBtn, noBtn},
                yesBtn
        );

        return userChoice[0];
    }




}