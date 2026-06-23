package com.college.auditorium.gui;

import javax.swing.*;
import java.awt.*;

public class StudentDashboardGUI extends JFrame {

    int userId;

    public StudentDashboardGUI(int userId) {
        this.userId = userId;

        setTitle("Student Dashboard");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Main Layout
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(ModernTheme.BG_COLOR);
        main.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(ModernTheme.BG_COLOR);

        JLabel title = new JLabel("Welcome, Student");
        title.setFont(ModernTheme.FONT_HEADER);
        title.setForeground(ModernTheme.PRIMARY_COLOR);

        JButton logout = new JButton("Logout");
        ModernTheme.applymodernButton(logout, new Color(148, 163, 184)); // Muted gray
        logout.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        header.add(title, BorderLayout.WEST);
        header.add(logout, BorderLayout.EAST);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Content Area - Grid of Action Cards
        JPanel content = new JPanel(new GridLayout(1, 2, 30, 0)); // 2 columns
        content.setBackground(ModernTheme.BG_COLOR);

        // View Events Card
        JPanel eventsCard = createActionCard("Upcoming Events", "Browse standard shows and events", "View Calendar",
                new Color(33, 150, 243));
        JButton viewEventsBtn = (JButton) eventsCard.getClientProperty("actionBtn");

        // My Bookings Card
        JPanel bookingsCard = createActionCard("My Bookings", "Check status of your reservations", "Manage Bookings",
                new Color(76, 175, 80));
        JButton viewBookingsBtn = (JButton) bookingsCard.getClientProperty("actionBtn");

        content.add(eventsCard);
        content.add(bookingsCard);

        main.add(header, BorderLayout.NORTH);
        main.add(content, BorderLayout.CENTER);

        // Actions
        viewEventsBtn.addActionListener(e -> new ViewEventsGUI(userId));
        viewBookingsBtn.addActionListener(e -> new ViewMyBookingsGUI(userId));
        logout.addActionListener(e -> {
            new LoginGUI();
            dispose();
        });

        add(main);
        setVisible(true);
    }

    private JPanel createActionCard(String title, String desc, String btnText, Color accent) {
        JPanel card = new JPanel(new BorderLayout());
        ModernTheme.applyCardStyle(card);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);

        JLabel titleLbl = new JLabel(title);
        titleLbl.setFont(ModernTheme.FONT_TITLE);

        JLabel descLbl = new JLabel(desc);
        descLbl.setFont(ModernTheme.FONT_BODY);
        descLbl.setForeground(Color.GRAY);

        textPanel.add(titleLbl);
        textPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        textPanel.add(descLbl);

        JButton actionBtn = new JButton(btnText);
        ModernTheme.applymodernButton(actionBtn, accent);
        // Store button in client property to access listener later, keeping this helper
        // pure UI
        card.putClientProperty("actionBtn", actionBtn);

        card.add(textPanel, BorderLayout.CENTER);
        card.add(actionBtn, BorderLayout.SOUTH);

        return card;
    }
}
