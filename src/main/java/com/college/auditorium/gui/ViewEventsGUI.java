package com.college.auditorium.gui;

import com.college.auditorium.util.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ViewEventsGUI extends JFrame {

    JComboBox<String> eventBox;
    JComboBox<String> slotBox;

    int[] eventIds = new int[100];
    int[] slotIds = new int[100];

    int eventCount = 0;
    int slotCount = 0;
    int userId;

    public ViewEventsGUI(int userId) {

        this.userId = userId;

        setTitle("View Events");
        setSize(450, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("Select Event:"));
        eventBox = new JComboBox<>();
        add(eventBox);

        add(new JLabel("Select Slot:"));
        slotBox = new JComboBox<>();
        add(slotBox);

        JButton bookBtn = new JButton("Book Seats");
        JButton backBtn = new JButton("Back");

        add(bookBtn);
        add(backBtn);

        loadEvents();
        eventBox.addActionListener(e -> loadSlots());

        bookBtn.addActionListener(e -> {
            int idx = slotBox.getSelectedIndex();
            if (idx < 0) {
                JOptionPane.showMessageDialog(this, "Select a slot");
                return;
            }
            new SeatSelectionGUI(userId, slotIds[idx]);
        });

        backBtn.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void loadEvents() {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps =
                con.prepareStatement("SELECT movie_id, title FROM movies");
            ResultSet rs = ps.executeQuery();

            eventBox.removeAllItems();
            eventCount = 0;

            while (rs.next()) {
                eventBox.addItem(rs.getString("title"));
                eventIds[eventCount++] = rs.getInt("movie_id");
            }

            if (eventCount == 0) {
                JOptionPane.showMessageDialog(this, "No events available");
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load events");
        }
    }

    private void loadSlots() {
        try {
            slotBox.removeAllItems();
            slotCount = 0;

            int index = eventBox.getSelectedIndex();
            if (index < 0) return;

            Connection con = DBConnection.getConnection();
            PreparedStatement ps =
                con.prepareStatement(
                    "SELECT showtime_id, show_time FROM showtimes WHERE movie_id=?"
                );
            ps.setInt(1, eventIds[index]);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                slotBox.addItem(rs.getString("show_time"));
                slotIds[slotCount++] = rs.getInt("showtime_id");
            }

            if (slotCount == 0) {
                JOptionPane.showMessageDialog(this, "No slots for this event");
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load slots");
        }
    }
}
