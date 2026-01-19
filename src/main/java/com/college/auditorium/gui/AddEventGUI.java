package com.college.auditorium.gui;

import com.college.auditorium.util.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AddEventGUI extends JFrame {

    JTextField eventNameField;
    JTextField departmentField;
    JTextField durationField;
    JTextField dateField;
    JTextField timeField;

    public AddEventGUI() {

        setTitle("Add Event & Slot");
        setSize(420, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new GridLayout(6, 2, 10, 10));

        add(new JLabel("Event Name:"));
        eventNameField = new JTextField();
        add(eventNameField);

        add(new JLabel("Department:"));
        departmentField = new JTextField();
        add(departmentField);

        add(new JLabel("Duration (minutes):"));
        durationField = new JTextField();
        add(durationField);

        add(new JLabel("Slot Date (YYYY-MM-DD):"));
        dateField = new JTextField();
        add(dateField);

        add(new JLabel("Slot Time (HH:MM):"));
        timeField = new JTextField();
        add(timeField);

        JButton doneBtn = new JButton("Done");
        add(new JLabel());
        add(doneBtn);

        doneBtn.addActionListener(e -> saveEventAndSlot());

        setVisible(true);
    }

    private void saveEventAndSlot() {
        try {
            Connection con = DBConnection.getConnection();

            // Insert event
            PreparedStatement psEvent = con.prepareStatement(
                "INSERT INTO movies (title, genre, duration) VALUES (?, ?, ?)",
                PreparedStatement.RETURN_GENERATED_KEYS
            );

            psEvent.setString(1, eventNameField.getText().trim());
            psEvent.setString(2, departmentField.getText().trim());
            psEvent.setInt(3, Integer.parseInt(durationField.getText().trim()));

            psEvent.executeUpdate();

            ResultSet rs = psEvent.getGeneratedKeys();
            if (!rs.next()) {
                throw new Exception("Event creation failed");
            }

            int eventId = rs.getInt(1);

            // Insert slot (DATETIME format)
            String showTime =
                dateField.getText().trim() + " " +
                timeField.getText().trim() + ":00";

            PreparedStatement psSlot = con.prepareStatement(
                "INSERT INTO showtimes (movie_id, show_time) VALUES (?, ?)"
            );

            psSlot.setInt(1, eventId);
            psSlot.setString(2, showTime);

            psSlot.executeUpdate();

            JOptionPane.showMessageDialog(this,
                "Event and slot added successfully!");

            dispose();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
}
