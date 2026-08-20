package Frontend;

import Backend.MenuDAO;
import Backend.MenuItem;
import Backend.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
// import java.awt.BorderLayout;

public class ManageMenuFrame extends JFrame {

    User currentUser;

    DefaultTableModel model;
    JTable table;
    ArrayList<MenuItem> items;

    public ManageMenuFrame(User currentUser){
        this.currentUser = currentUser;
        setTitle("Manage Menu");
        setSize(600,450);

        setLocationRelativeTo(null);
        // ১. Layout নিশ্চিত করা
        this.setLayout(new java.awt.BorderLayout());

        // ২. TopBar যোগ করা
        this.add(UITheme.createTopBar("Manage Menu",
                e -> {
                    new AdminDashboard(currentUser).setVisible(true);
                    this.dispose();
                },
                e -> {
                    new LoginFrame().setVisible(true);
                    this.dispose();
                }
        ), java.awt.BorderLayout.NORTH);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columns = {"ID","Name","Price"};

        model = new DefaultTableModel(columns, 0);

        table = new JTable(model);

        add(new JScrollPane(table), java.awt.BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        JButton addBtn = new JButton("Add Item");
        JButton editBtn = new JButton("Edit Selected");
        JButton deleteBtn = new JButton("Delete Selected");
        JButton refreshBtn = new JButton("Refresh");

        // ৪টি বাটনে অরেঞ্জ কালার ও সাইড গোল স্টাইল অ্যাপ্লাই করা
        styleOrangeButton(addBtn);
        styleOrangeButton(editBtn);
        styleOrangeButton(deleteBtn);
        styleOrangeButton(refreshBtn);

        buttonPanel.add(addBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(refreshBtn);

        add(buttonPanel, java.awt.BorderLayout.SOUTH);

        addBtn.addActionListener(e -> addItem());

        editBtn.addActionListener(e -> editItem());

        deleteBtn.addActionListener(e -> deleteItem());

        refreshBtn.addActionListener(e -> loadItems());

        loadItems();

        setVisible(true);

    }

    private void loadItems(){

        items = MenuDAO.getAllItems();

        model.setRowCount(0);

        for(MenuItem item : items){
            model.addRow(new Object[]{item.getId(), item.getName(), item.getPrice()});
        }

    }

    private void addItem(){

        try {

            // 👈 কাস্টম অরেঞ্জ ইনপুট ডায়ালগ
            String name = customInputDialog("Item name:");

            if(name == null || name.trim().isEmpty()) return;

            String priceStr = customInputDialog("Price:");

            if(priceStr == null || priceStr.trim().isEmpty()) return;

            double price = Double.parseDouble(priceStr);

            String category = customInputDialog("Category (e.g. Drink/Food):");

            MenuDAO.addItem(name, price, category);

           // JOptionPane.showMessageDialog(this, "Item added successfully");

            customMessageDialog("Item added successfully");
            loadItems();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Price must be a valid number");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to add item: " + e.getMessage());
        }

    }

    private void editItem(){

        int row = table.getSelectedRow();

        if(row == -1){
           // JOptionPane.showMessageDialog(this, "Select an item first");
            customMessageDialog("Select an item first");
            return;
        }

        try {

            MenuItem selected = items.get(row);

            // 👈 কাস্টম অরেঞ্জ ইনপুট ডায়ালগ
            String name = customInputDialog("New name:");

            if(name == null || name.trim().isEmpty()) return;

            String priceStr = customInputDialog("New price:");

            if(priceStr == null || priceStr.trim().isEmpty()) return;

            double price = Double.parseDouble(priceStr);

            MenuDAO.updateItem(selected.getId(), name, price);

           // JOptionPane.showMessageDialog(this, "Item updated successfully");
            customMessageDialog("Item updated successfully");

            loadItems();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Price must be a valid number");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to update item: " + e.getMessage());
        }

    }

    private void deleteItem(){

        int row = table.getSelectedRow();

        if(row == -1){
           // JOptionPane.showMessageDialog(this, "Select an item first");
            customMessageDialog("Select an item first");
            return;
        }

        try {

            MenuItem selected = items.get(row);

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Delete " + selected.getName() + "?", "Confirm",
                    JOptionPane.YES_NO_OPTION);

            if(confirm != JOptionPane.YES_OPTION) return;

            MenuDAO.deleteItem(selected.getId());

            //JOptionPane.showMessageDialog(this, "Item deleted");
            customMessageDialog("Item deleted");

            loadItems();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to delete item: " + e.getMessage());
        }

    }

    // বাটনগুলোকে অরেঞ্জ, রাউন্ডেড এবং বর্ডারলেস করার নিখুঁত মেথড
    private void styleOrangeButton(JButton btn) {
        btn.setForeground(java.awt.Color.WHITE);
        btn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));

        // --- নীল বর্ডার ও ফোকাস রিমুভ করার প্রপার্টি ---
        btn.setFocusPainted(false);
        btn.setFocusable(false);          // 👈 ফোকাস নীল দাগ বন্ধ করবে
        btn.setBorderPainted(false);        // 👈 ডিফল্ট আউটলাইন বর্ডার বন্ধ করবে
        btn.setContentAreaFilled(false);

        // সাইড গোল কাস্টম ব্যাকগ্রাউন্ড পেইন্ট
        btn.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(java.awt.Graphics g, javax.swing.JComponent c) {
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new java.awt.Color(230, 120, 23)); // সুন্দর কফি-অরেঞ্জ কালার
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 15, 15); // সাইড রাউন্ডেড
                g2.dispose();
                super.paint(g, c);
            }
        });

    }

    // কাস্টম অরেঞ্জ বাটনের ইনপুট ডায়ালগ মেথড
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


    // কাস্টম অরেঞ্জ OK বাটনের মেসেজ ডায়ালগ মেথড
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

    


}
