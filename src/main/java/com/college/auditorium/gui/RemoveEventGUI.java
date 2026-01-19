package com.college.auditorium.gui;

import com.college.auditorium.util.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RemoveEventGUI extends JFrame {

    JComboBox<String> eventBox;
    int[] eventIds = new int[100];
    int count = 0;

    public RemoveEventGUI() {

        setTitle("Remove Event");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel("Select Event:"));
        eventBox = new JComboBox<>();
        add(eventBox);

        JButton removeBtn = new JButton("Remove Event");
        JButton backBtn = new JButton("Back");

        add(removeBtn);
        add(backBtn);

        loadEvents();

        removeBtn.addActionListener(e -> removeEvent());
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
            count = 0;

            while (rs.next()) {
                eventBox.addItem(rs.getString("title"));
                eventIds[count++] = rs.getInt("movie_id");
            }

            if (count == 0) {
                JOptionPane.showMessageDialog(this, "No events available");
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load events");
        }
    }

    private void removeEvent() {
        try {
            int index = eventBox.getSelectedIndex();
            if (index < 0) {
                JOptionPane.showMessageDialog(this, "Select an event");
                return;
            }

            int eventId = eventIds[index];

            int confirm = JOptionPane.showConfirmDialog(
                this,
                "This will delete the event and all its bookings.\nAre you sure?",
                "Confirm",
                JOptionPane.YES_NO_OPTION
            );

            if (confirm != JOptionPane.YES_OPTION) return;

            Connection con = DBConnection.getConnection();

            // delete bookings
            PreparedStatement ps1 = con.prepareStatement(
                "DELETE FROM bookings WHERE showtime_id IN " +
                "(SELECT showtime_id FROM showtimes WHERE movie_id=?)"
            );
            ps1.setInt(1, eventId);
            ps1.executeUpdate();

            // delete showtimes
            PreparedStatement ps2 =
                con.prepareStatement("DELETE FROM showtimes WHERE movie_id=?");
            ps2.setInt(1, eventId);
            ps2.executeUpdate();

            // delete event
            PreparedStatement ps3 =
                con.prepareStatement("DELETE FROM movies WHERE movie_id=?");
            ps3.setInt(1, eventId);
            ps3.executeUpdate();

            JOptionPane.showMessageDialog(this, "Event removed successfully!");
            loadEvents();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
}
