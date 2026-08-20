package Frontend;

import Backend.CartItem;
import Backend.MenuDAO;
import Backend.MenuItem;
import Backend.OrderDAO;
import Backend.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class NewOrderFrame extends JFrame {

    User currentUser;
    ArrayList<MenuItem> menuItems;
    ArrayList<CartItem> cart = new ArrayList<>();

    JComboBox<String> itemDropdown;
    JSpinner qtySpinner;
    DefaultTableModel cartModel;
    JTable cartTable;
    JLabel totalLabel;

    public NewOrderFrame(User user){

        this.currentUser = user;

        setTitle("New Order");

        // Warm Cream Background
        getContentPane().setBackground(new java.awt.Color(245, 242, 236));

        setSize(650,500);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        menuItems = MenuDAO.getAllItems();

        String[] names = new String[menuItems.size()];
        for(int i = 0; i < menuItems.size(); i++){
            names[i] = menuItems.get(i).getName() + " - " + menuItems.get(i).getPrice();
        }

        JLabel selectLabel = new JLabel("Item:");
        selectLabel.setBounds(20,20,50,30);
        add(selectLabel);

        itemDropdown = new JComboBox<>(names);
        itemDropdown.setBounds(70,20,220,30);
        add(itemDropdown);

        JLabel qtyLabel = new JLabel("Qty:");
        qtyLabel.setBounds(300,20,40,30);
        add(qtyLabel);

        qtySpinner = new JSpinner(new SpinnerNumberModel(1,1,50,1));
        qtySpinner.setBounds(340,20,60,30);
        add(qtySpinner);

        JButton addBtn = new JButton("Add to Cart");
        styleOrangeButton(addBtn);
        addBtn.setBounds(420,20,150,30);
        add(addBtn);
        addBtn.addActionListener(e -> addToCart());

        String[] cartColumns = {"Item","Qty","Subtotal"};
        cartModel = new DefaultTableModel(cartColumns, 0);
        cartTable = new JTable(cartModel);
        JScrollPane scrollPane = new JScrollPane(cartTable);
        scrollPane.setBounds(20,70,590,250);
        add(scrollPane);

        JButton removeBtn = new JButton("Remove Selected");
        styleOrangeButton(removeBtn);
        removeBtn.setBounds(20,330,180,35);
        add(removeBtn);
        removeBtn.addActionListener(e -> removeSelected());

        totalLabel = new JLabel("Total: 0.00");
        totalLabel.setFont(new Font("Arial",Font.BOLD,16));
        totalLabel.setBounds(220,330,200,35);
        add(totalLabel);

        JButton checkoutBtn = new JButton("Checkout");
        styleOrangeButton(checkoutBtn);
        checkoutBtn.setBounds(450,330,150,35);
        add(checkoutBtn);
        checkoutBtn.addActionListener(e -> checkout());

        setVisible(true);
    }

    private void addToCart(){
        try {
            int index = itemDropdown.getSelectedIndex();
            if(index < 0){
                throw new IllegalStateException("No menu item selected");
            }

            int qty = (int) qtySpinner.getValue();
            if(qty <= 0){
                throw new IllegalArgumentException("Quantity must be at least 1");
            }

            MenuItem selected = menuItems.get(index);
            cart.add(new CartItem(selected, qty));
            refreshCartTable();

        } catch (Exception e) {
            customMessageDialog("Could not add item: " + e.getMessage());
        }
    }

    private void removeSelected(){
        int row = cartTable.getSelectedRow();
        if(row == -1){
            customMessageDialog("Select a row in the cart first");
            return;
        }

        cart.remove(row);
        refreshCartTable();
    }

    private void refreshCartTable(){
        cartModel.setRowCount(0);
        double total = 0;

        for(CartItem ci : cart){
            cartModel.addRow(new Object[]{
                    ci.getItem().getName(),
                    ci.getQuantity(),
                    ci.getSubtotal()
            });
            total += ci.getSubtotal();
        }

        totalLabel.setText("Total: " + total);
    }

    private void checkout(){
        try {
            if(cart.isEmpty()){
                throw new IllegalStateException("Cart is empty, add items before checkout");
            }

            int orderId = OrderDAO.placeOrder(currentUser.getId(), cart);

            // তারিখ ও সময় পাওয়ার জন্য
            java.time.LocalDateTime now = java.time.LocalDateTime.now();
            java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy - hh:mm a");
            String dateTimeStr = now.format(formatter);

            // 👈 সুন্দর ম্যানুয়াল রসিদ লেআউট তৈরি
            StringBuilder receipt = new StringBuilder();
            receipt.append("          CAFE MANAGEMENT          \n");
            receipt.append("-----------------------------------\n");
            receipt.append("          *** RECEIPT ***          \n\n");

            receipt.append(String.format("ORDER #%-12d %s\n", orderId, dateTimeStr));
            receipt.append(String.format("CASHIER: %s\n", currentUser.getFullName()));
            receipt.append("-----------------------------------\n");

            double total = 0;
            for(CartItem ci : cart){
                String itemName = ci.getItem().getName();
                int qty = ci.getQuantity();
                double pricePerItem = ci.getItem().getPrice();
                double subtotal = ci.getSubtotal();

                receipt.append(String.format("%-25s $%.2f\n", itemName, subtotal));
                if(qty > 1) {
                    receipt.append(String.format("  x%d @ $%.2f\n", qty, pricePerItem));
                }
                total += subtotal;
            }

            receipt.append("-----------------------------------\n");
            receipt.append(String.format("%-25s $%.2f\n", "SUBTOTAL", total));
            receipt.append("-----------------------------------\n");
            receipt.append(String.format("%-25s $%.2f\n\n", "TOTAL AMOUNT", total));
            receipt.append("-----------------------------------\n");
            receipt.append("       THANK YOU FOR VISITING!     \n");

            // JTextArea দিয়ে সুন্দরভাবে রসিদ ডিসপ্লে করা
            JTextArea textArea = new JTextArea(receipt.toString());
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
            textArea.setEditable(false);
            textArea.setOpaque(false);

            customMessageDialog("Receipt", textArea);

            cart.clear();
            refreshCartTable();
            dispose();

        } catch (Exception e) {
            customMessageDialog("Checkout failed: " + e.getMessage());
        }
    }

    // বাটন অরেঞ্জ ও রাউন্ডেড করার স্টাইল
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
                g2.setColor(new java.awt.Color(230, 120, 23));
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 15, 15);
                g2.dispose();
                super.paint(g, c);
            }
        });
    }

    // কাস্টম অরেঞ্জ OK ডায়ালগ মেথড (ডিফল্ট)
    private void customMessageDialog(Object message) {
        customMessageDialog("Message", message);
    }

    // কাস্টম অরেঞ্জ OK ডায়ালগ মেথড (টাইটেল সহ)
    private void customMessageDialog(String titleText, Object message) {
        JButton okBtn = new JButton("OK");
        styleOrangeButton(okBtn);

        okBtn.addActionListener(e -> {
            java.awt.Window win = javax.swing.SwingUtilities.getWindowAncestor(okBtn);
            if (win != null) win.dispose();
        });

        JOptionPane.showOptionDialog(
                this,
                message,
                titleText,
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new Object[]{okBtn},
                okBtn
        );
    }
}