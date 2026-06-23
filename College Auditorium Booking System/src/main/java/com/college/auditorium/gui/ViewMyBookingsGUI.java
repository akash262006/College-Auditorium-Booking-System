package com.college.auditorium.gui;

import com.college.auditorium.util.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewMyBookingsGUI extends JFrame {

    public ViewMyBookingsGUI(int userId) {

        setTitle("My Bookings");
        setSize(650, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Font titleFont = new Font("Segoe UI", Font.BOLD, 18);

        JLabel title = new JLabel("My Bookings", JLabel.CENTER);
        title.setFont(titleFont);
        title.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        DefaultTableModel model = new DefaultTableModel(
            new String[]{"Event", "Slot", "Seats"}, 0
        );

        JTable table = new JTable(model);
        table.setRowHeight(25);

        add(title, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "SELECT m.title, s.show_time, b.seats " +
                "FROM bookings b JOIN showtimes s ON b.showtime_id=s.showtime_id " +
                "JOIN movies m ON s.movie_id=m.movie_id WHERE b.user_id=?"
            );
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3)
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Unable to load bookings");
        }

        setVisible(true);
    }
}
