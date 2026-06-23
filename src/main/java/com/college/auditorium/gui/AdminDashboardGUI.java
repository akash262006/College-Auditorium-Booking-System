package com.college.auditorium.gui;

import javax.swing.*;
import java.awt.*;

public class AdminDashboardGUI extends JFrame {

    public AdminDashboardGUI() {

        setTitle("Faculty Dashboard");
        setSize(800, 600); // Expanded for dashboard feel
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Sidebar Navigation
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(ModernTheme.PRIMARY_COLOR);
        sidebar.setPreferredSize(new Dimension(220, 600));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel menuTitle = new JLabel("MENU");
        menuTitle.setFont(ModernTheme.FONT_TITLE);
        menuTitle.setForeground(new Color(148, 163, 184)); // Muted text for menu header
        menuTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        sidebar.add(menuTitle);
        sidebar.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton addEvent = createSidebarButton("Add Event & Slot");
        JButton removeEvent = createSidebarButton("Remove Event");
        JButton viewBookings = createSidebarButton("View Bookings");

        // New Feature Button
        JButton exportBookings = createSidebarButton("Export Data");

        JButton logout = createSidebarButton("Logout");
        logout.setBackground(ModernTheme.DANGER_COLOR); // Red hint

        sidebar.add(addEvent);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(removeEvent);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(viewBookings);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(exportBookings);

        sidebar.add(Box.createVerticalGlue()); // Push logout to bottom
        sidebar.add(logout);

        // Main Content Area
        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setBackground(ModernTheme.BG_COLOR);
        mainContent.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel welcomeTags = new JLabel("Dashboard Overview");
        welcomeTags.setFont(ModernTheme.FONT_HEADER);
        mainContent.add(welcomeTags, BorderLayout.NORTH);

        // Stats Panel (Grid of Cards)
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        statsPanel.setOpaque(false);
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        statsPanel.add(createStatCard("Total Events", "12", new Color(59, 130, 246)));
        statsPanel.add(createStatCard("Active Bookings", "45", new Color(16, 185, 129)));
        statsPanel.add(createStatCard("Today's Shows", "3", new Color(245, 158, 11)));

        // Center content wrapper
        JPanel centerWrapper = new JPanel(new BorderLayout());
        centerWrapper.setOpaque(false);
        centerWrapper.add(statsPanel, BorderLayout.NORTH);

        // Placeholder for charts or lists
        JLabel placeholder = new JLabel("Select an option from the menu to manage auditoriums.");
        placeholder.setFont(ModernTheme.FONT_BODY);
        placeholder.setForeground(Color.GRAY);
        placeholder.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));
        centerWrapper.add(placeholder, BorderLayout.CENTER);

        mainContent.add(centerWrapper, BorderLayout.CENTER);

        // Action Listeners
        addEvent.addActionListener(e -> new AddEventGUI());
        removeEvent.addActionListener(e -> new RemoveEventGUI());
        viewBookings.addActionListener(e -> new ViewBookingsGUI());

        exportBookings.addActionListener(e -> {
            try {
                java.sql.Connection con = com.college.auditorium.util.DBConnection.getConnection();
                com.college.auditorium.util.ExportUtil.exportBookingsToCSV(con);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        logout.addActionListener(e -> {
            new LoginGUI();
            dispose();
        });

        // Split Pane (Sidebar + Main) -> No, standard BorderLayout is cleaner here
        add(sidebar, BorderLayout.WEST);
        add(mainContent, BorderLayout.CENTER);

        setVisible(true);
    }

    private JButton createSidebarButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(ModernTheme.FONT_BUTTON);
        btn.setBackground(ModernTheme.PRIMARY_COLOR);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setMaximumSize(new Dimension(200, 40));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        return btn;
    }

    private JPanel createStatCard(String title, String value, Color accentInfo) {
        JPanel card = new JPanel(new BorderLayout());
        ModernTheme.applyCardStyle(card);

        JLabel titleLbl = new JLabel(title);
        titleLbl.setFont(ModernTheme.FONT_BODY);
        titleLbl.setForeground(Color.GRAY);

        JLabel valueLbl = new JLabel(value);
        valueLbl.setFont(ModernTheme.FONT_HEADER);
        valueLbl.setForeground(accentInfo);

        card.add(titleLbl, BorderLayout.NORTH);
        card.add(valueLbl, BorderLayout.CENTER);
        return card;
    }
}
