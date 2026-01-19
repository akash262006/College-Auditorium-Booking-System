package com.college.auditorium.gui;

import java.awt.*;
import javax.swing.*;

public class LoginGUI extends JFrame {

    public LoginGUI() {

        setTitle("College Auditorium Booking");
        setSize(500, 400); // Increased size for better layout
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main Container with Modern Background
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(ModernTheme.BG_COLOR);
        main.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(ModernTheme.BG_COLOR);

        JLabel title = new JLabel("Welcome", JLabel.CENTER);
        title.setFont(ModernTheme.FONT_HEADER);
        title.setForeground(ModernTheme.PRIMARY_COLOR);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("College Auditorium Management", JLabel.CENTER);
        subtitle.setFont(ModernTheme.FONT_BODY);
        subtitle.setForeground(Color.GRAY);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(title);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        headerPanel.add(subtitle);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Button Panel - Card Style
        JPanel btnPanel = new JPanel(new GridLayout(3, 1, 15, 15));
        ModernTheme.applyCardStyle(btnPanel);

        JButton adminBtn = new JButton("Faculty Login");
        JButton studentBtn = new JButton("Student Login");
        JButton exitBtn = new JButton("Exit System");

        ModernTheme.applymodernButton(adminBtn, ModernTheme.PRIMARY_COLOR);
        ModernTheme.applymodernButton(studentBtn, ModernTheme.ACCENT_COLOR);
        ModernTheme.applymodernButton(exitBtn, ModernTheme.DANGER_COLOR);

        btnPanel.add(adminBtn);
        btnPanel.add(studentBtn);
        btnPanel.add(exitBtn);

        // Actions
        adminBtn.addActionListener(e -> {
            new AdminLoginGUI();
            dispose();
        });

        studentBtn.addActionListener(e -> {
            new StudentLoginGUI();
            dispose();
        });

        exitBtn.addActionListener(e -> System.exit(0));

        main.add(headerPanel, BorderLayout.NORTH);
        main.add(btnPanel, BorderLayout.CENTER);

        add(main);
        setVisible(true);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {
        }
        new LoginGUI();
    }
}
