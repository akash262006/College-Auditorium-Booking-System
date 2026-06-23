package com.college.auditorium.gui;

import com.college.auditorium.util.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class StudentRegisterGUI extends JFrame {

    JTextField usernameField;
    JTextField emailField;
    JPasswordField passwordField;

    public StudentRegisterGUI() {

        setTitle("Student Registration");
        setSize(350, 260);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new GridLayout(5, 2, 10, 10));

        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        JButton registerBtn = new JButton("Register");
        JButton backBtn = new JButton("Back");

        add(registerBtn);
        add(backBtn);

        registerBtn.addActionListener(e -> registerStudent());
        backBtn.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void registerStudent() {
        try {
            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO users (username, email, password, role) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, usernameField.getText());
            ps.setString(2, emailField.getText());
            ps.setString(3, new String(passwordField.getPassword()));
            ps.setString(4, "STUDENT");

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Registration successful!");
            dispose();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Registration failed");
        }
    }
}
