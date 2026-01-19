package com.college.auditorium.gui;

import com.college.auditorium.util.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminLoginGUI extends JFrame {

    JTextField usernameField;
    JPasswordField passwordField;

    public AdminLoginGUI() {
        setTitle("Faculty Login");
        setSize(450, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Use GridBagLayout for centering and rigid control
        JPanel main = new JPanel(new GridBagLayout());
        main.setBackground(ModernTheme.BG_COLOR);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1.0;

        // Header
        JLabel title = new JLabel("Faculty Login", JLabel.CENTER);
        title.setFont(ModernTheme.FONT_HEADER);
        title.setForeground(ModernTheme.PRIMARY_COLOR);

        gbc.gridy = 0;
        main.add(title, gbc);

        // Username Label
        JLabel userLbl = new JLabel("Username");
        userLbl.setFont(ModernTheme.FONT_BODY);
        gbc.gridy = 1;
        main.add(userLbl, gbc);

        // Username Field
        usernameField = new JTextField();
        usernameField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        usernameField.setPreferredSize(new Dimension(300, 40));
        usernameField.setForeground(Color.BLACK);
        usernameField.setBackground(Color.WHITE);
        usernameField.setCaretColor(Color.BLACK);
        // Explicit Border
        usernameField.setBorder(BorderFactory.createLineBorder(new Color(100, 116, 139), 4));

        gbc.gridy = 2;
        main.add(usernameField, gbc);

        // Password Label
        JLabel passLbl = new JLabel("Password");
        passLbl.setFont(ModernTheme.FONT_BODY);
        gbc.gridy = 3;
        main.add(passLbl, gbc);

        // Password Field
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        passwordField.setPreferredSize(new Dimension(300, 40));
        passwordField.setForeground(Color.BLACK);
        passwordField.setBackground(Color.WHITE);
        passwordField.setCaretColor(Color.BLACK);
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(100, 116, 139), 4));

        gbc.gridy = 4;
        main.add(passwordField, gbc);

        // Buttons Panel
        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        btnPanel.setOpaque(false);

        JButton loginBtn = new JButton("Login");
        ModernTheme.applymodernButton(loginBtn, ModernTheme.PRIMARY_COLOR);

        JButton backBtn = new JButton("Back");
        ModernTheme.applymodernButton(backBtn, Color.GRAY);

        btnPanel.add(loginBtn);
        btnPanel.add(backBtn);

        gbc.gridy = 5;
        gbc.insets = new Insets(30, 10, 10, 10); // More space before buttons
        main.add(btnPanel, gbc);

        add(main);

        loginBtn.addActionListener(e -> adminLogin());
        backBtn.addActionListener(e -> {
            new LoginGUI();
            dispose();
        });

        setVisible(true);
    }

    private void adminLogin() {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM admin WHERE username=? AND password=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, usernameField.getText().trim());
            ps.setString(2, new String(passwordField.getPassword()).trim());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Faculty Login Successful!");
                new AdminDashboardGUI();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
}
