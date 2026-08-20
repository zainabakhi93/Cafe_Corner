package Frontend;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

/**
 * ============================================================
 *  UITheme.java  -  Central design system for Cafe Monitoring System
 * ============================================================
 *  Ei ekta file e shob "look & feel" jinis rakha ache:
 *   - Color theme
 *   - Fonts
 *   - Rounded Button (RoundedButton class)
 *   - Rounded Panel  (RoundedPanel class)
 *   - Simple vector icons (no image file lagbe na)
 *   - Modern confirm / success / error dialog
 *   - Consistent Back & Logout bar
 *
 *  Kono ekta .java file er business logic (DAO, DB, calculation)
 *  ekhane touch kora hoyni. শুধু UI styling.
 * ============================================================
 */
public class UITheme {

    // ---------- 1) COLOR THEME ----------
    public static final Color PRIMARY        = new Color(0x4E342E); // deep coffee brown
    public static final Color PRIMARY_DARK   = new Color(0x3B2723);
    public static final Color ACCENT         = new Color(0xD98324); // warm amber
    public static final Color ACCENT_DARK    = new Color(0xB8690F);
    public static final Color BACKGROUND     = new Color(0xF5F0E6); // cream
    public static final Color PANEL_BG       = Color.WHITE;
    public static final Color TEXT_DARK      = new Color(0x2E2422);
    public static final Color TEXT_LIGHT     = new Color(0x8A7B76);
    public static final Color BORDER_COLOR   = new Color(0xE0D6CC);
    public static final Color SUCCESS        = new Color(0x2E7D32);
    public static final Color DANGER         = new Color(0xC62828);
    public static final Color WHITE          = Color.WHITE;

    // ---------- 2) FONTS ----------
    public static final Font FONT_TITLE   = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font FONT_HEADING = new Font("Segoe UI", Font.BOLD, 17);
    public static final Font FONT_LABEL   = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_BODY    = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_BUTTON  = new Font("Segoe UI", Font.BOLD, 14);

    // ---------- 3) SPACING ----------
    public static final int GAP_SM = 8;
    public static final int GAP_MD = 16;
    public static final int GAP_LG = 24;
    public static final int RADIUS = 14; // border round-koron

    // ============================================================
    //  4) ROUNDED BUTTON  (button style + size + round corner)
    // ============================================================
    public static class RoundedButton extends JButton {
        private Color bg;
        private Color hoverBg;
        private final int radius;

        public RoundedButton(String text, Color bgColor, Color hoverColor) {
            this(text, bgColor, hoverColor, RADIUS);
        }

        public RoundedButton(String text, Color bgColor, Color hoverColor, int radius) {
            super(text);
            this.bg = bgColor;
            this.hoverBg = hoverColor;
            this.radius = radius;
            setFont(FONT_BUTTON);
            setForeground(WHITE);
            setFocusPainted(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setOpaque(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setPreferredSize(new Dimension(150, 40));
            setMargin(new Insets(8, 18, 8, 18));

            addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent e) { setBackground(hoverBg); repaint(); }
                public void mouseExited(java.awt.event.MouseEvent e)  { setBackground(bg); repaint(); }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getModel().isRollover() ? hoverBg : bg);
            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, radius, radius));
            g2.dispose();
            super.paintComponent(g);
        }
    }

    /** Primary (accent) button — mostly ব্যবহারের জন্য: Save, Login, Add, Confirm */
    public static RoundedButton primaryButton(String text) {
        return new RoundedButton(text, ACCENT, ACCENT_DARK);
    }

    /** Secondary/neutral button — Cancel, Back */
    public static RoundedButton secondaryButton(String text) {
        return new RoundedButton(text, PRIMARY, PRIMARY_DARK);
    }

    /** Danger button — Delete, Logout */
    public static RoundedButton dangerButton(String text) {
        return new RoundedButton(text, DANGER, new Color(0x8E1E1E));
    }

    // ============================================================
    //  5) ROUNDED PANEL (panel design + border round shape)
    // ============================================================
    public static class RoundedPanel extends JPanel {
        private final int radius;
        public RoundedPanel(int radius) {
            this.radius = radius;
            setOpaque(false);
            setBackground(PANEL_BG);
        }
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, radius, radius));
            g2.setColor(BORDER_COLOR);
            g2.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 2, getHeight() - 2, radius, radius));
            g2.dispose();
            super.paintComponent(g);
        }
    }

    // ============================================================
    //  6) SIMPLE VECTOR ICONS (image file lagbe na)
    // ============================================================
    public static class VectorIcon implements Icon {
        public enum Type { BACK, LOGOUT, ADD, EDIT, DELETE, SEARCH, USER, SAVE }
        private final Type type;
        private final Color color;
        private final int size;

        public VectorIcon(Type type, Color color, int size) {
            this.type = type; this.color = color; this.size = size;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.translate(x, y);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            int s = size;
            switch (type) {
                case BACK -> {
                    g2.drawLine(s, s / 2, 2, s / 2);
                    g2.drawLine(2, s / 2, s / 2, 2);
                    g2.drawLine(2, s / 2, s / 2, s - 2);
                }
                case LOGOUT -> {
                    g2.drawRoundRect(2, 2, s / 2, s - 4, 3, 3);
                    g2.drawLine(s / 2, s / 2, s - 2, s / 2);
                    g2.drawLine(s - 6, s / 2 - 4, s - 2, s / 2);
                    g2.drawLine(s - 6, s / 2 + 4, s - 2, s / 2);
                }
                case ADD -> {
                    g2.drawLine(s / 2, 2, s / 2, s - 2);
                    g2.drawLine(2, s / 2, s - 2, s / 2);
                }
                case EDIT -> g2.drawRect(3, s / 3, s - 6, s / 3);
                case DELETE -> {
                    g2.drawLine(3, 3, s - 3, s - 3);
                    g2.drawLine(s - 3, 3, 3, s - 3);
                }
                case SEARCH -> {
                    g2.drawOval(2, 2, s - 8, s - 8);
                    g2.drawLine(s - 7, s - 7, s - 2, s - 2);
                }
                case USER -> {
                    g2.drawOval(s / 2 - 4, 2, 8, 8);
                    g2.drawArc(2, s / 2, s - 4, s / 2, 0, 180);
                }
                case SAVE -> g2.drawRect(3, 3, s - 6, s - 6);
            }
            g2.dispose();
        }
        @Override public int getIconWidth()  { return size; }
        @Override public int getIconHeight() { return size; }
    }

    // ============================================================
    //  7) CONSISTENT BACK & LOGOUT BAR (item #10)
    // ============================================================
    /**
     * Sob dashboard/frame e ek-i style-r ekta top bar dey: Back (left) + Logout (right).
     * backAction NULL dile Back button hide thakbe (jemon main dashboard e Back lagbe na).
     */
    public static JPanel createTopBar(String titleText, ActionListener backAction, ActionListener logoutAction) {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(PRIMARY);
        bar.setBorder(new EmptyBorder(10, 16, 10, 16));

        JLabel title = new JLabel(titleText);
        title.setFont(FONT_HEADING);
        title.setForeground(WHITE);

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        left.setOpaque(false);
        if (backAction != null) {
            RoundedButton back = secondaryButton("Back");
            back.setIcon(new VectorIcon(VectorIcon.Type.BACK, WHITE, 16));
            back.setPreferredSize(new Dimension(100, 34));
            back.addActionListener(backAction);
            left.add(back);
        }
        left.add(title);

        RoundedButton logout = dangerButton("Logout");
        logout.setIcon(new VectorIcon(VectorIcon.Type.LOGOUT, WHITE, 16));
        logout.setPreferredSize(new Dimension(110, 34));
        if (logoutAction != null) logout.addActionListener(logoutAction);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        right.setOpaque(false);
        right.add(logout);

        bar.add(left, BorderLayout.WEST);
        bar.add(right, BorderLayout.EAST);
        return bar;
    }

    // ============================================================
    //  8) MODERN CONFIRMATION / SUCCESS / ERROR DIALOG (item #9)
    // ============================================================
    public static void showSuccess(Component parent, String message) {
        showStyledDialog(parent, message, "Success", SUCCESS);
    }

    public static void showError(Component parent, String message) {
        showStyledDialog(parent, message, "Error", DANGER);
    }

    public static boolean showConfirm(Component parent, String message) {
        UIManager.put("OptionPane.background", PANEL_BG);
        UIManager.put("Panel.background", PANEL_BG);
        UIManager.put("OptionPane.messageFont", FONT_BODY);
        int result = JOptionPane.showConfirmDialog(
                parent, message, "Please Confirm",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return result == JOptionPane.YES_OPTION;
    }

    private static void showStyledDialog(Component parent, String message, String title, Color accent) {
        UIManager.put("OptionPane.background", PANEL_BG);
        UIManager.put("Panel.background", PANEL_BG);
        UIManager.put("OptionPane.messageFont", FONT_BODY);
        JOptionPane pane = new JOptionPane(message,
                accent == DANGER ? JOptionPane.ERROR_MESSAGE : JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = pane.createDialog(parent, title);
        dialog.setVisible(true);
    }

    // ============================================================
    //  9) INPUT FIELD STYLE HELPER (spacing/alignment/border round)
    // ============================================================
    public static void styleTextField(JTextField field) {
        field.setFont(FONT_BODY);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(6, 10, 6, 10)));
    }

    public static void styleLabel(JLabel label) {
        label.setFont(FONT_LABEL);
        label.setForeground(TEXT_DARK);
    }

    /** Root panel-e ek-laine background + padding boshate. */
    public static void styleRootPanel(JPanel panel) {
        panel.setBackground(BACKGROUND);
        panel.setBorder(new EmptyBorder(GAP_LG, GAP_LG, GAP_LG, GAP_LG));
    }
}