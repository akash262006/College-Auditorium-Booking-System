package com.college.auditorium.gui;

import com.college.auditorium.util.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AddSlotGUI extends JFrame {

    JComboBox<String> eventBox;
    JTextField dateField;
    JTextField timeField;

    int[] eventIds = new int[100];
    int eventCount = 0;

    public AddSlotGUI() {

        setTitle("Add Event Slot (Date & Time)");
        setSize(450, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new GridLayout(5, 2, 10, 10));

        add(new JLabel("Select Event:"));
        eventBox = new JComboBox<>();
        add(eventBox);

        add(new JLabel("Date (YYYY-MM-DD):"));
        dateField = new JTextField();
        add(dateField);

        add(new JLabel("Time (HH:MM):"));
        timeField = new JTextField();
        add(timeField);

        JButton addBtn = new JButton("Add Slot");
        JButton backBtn = new JButton("Back");

        add(addBtn);
        add(backBtn);

        loadEvents();

        addBtn.addActionListener(e -> addSlot());
        backBtn.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void loadEvents() {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "SELECT event_id, title FROM events"
            );
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                eventBox.addItem(rs.getString("title"));
                eventIds[eventCount++] = rs.getInt("event_id");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading events");
        }
    }

    private void addSlot() {
        try {
            int selectedIndex = eventBox.getSelectedIndex();
            if (selectedIndex < 0) {
                JOptionPane.showMessageDialog(this, "Select an event");
                return;
            }

            int eventId = eventIds[selectedIndex];
            String date = dateField.getText();
            String time = timeField.getText();

            Connection con = DBConnection.getConnection();
            String slotValue = date + " " + time;

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO showtimes (event_id, show_time) VALUES (?, ?)"
            );
            ps.setInt(1, eventId);
            ps.setString(2, slotValue);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Slot added successfully!");
            dateField.setText("");
            timeField.setText("");

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding slot");
        }
    }
}
