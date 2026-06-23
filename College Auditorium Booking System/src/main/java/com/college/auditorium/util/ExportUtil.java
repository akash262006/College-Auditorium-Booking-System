package com.college.auditorium.util;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class ExportUtil {

    public static void exportBookingsToCSV(Connection conn) {
        String csvFile = "bookings_export.csv";

        try (FileWriter fw = new FileWriter(csvFile); PrintWriter pw = new PrintWriter(fw)) {

            // Header
            pw.println("Booking ID, User Name, Event Name, Show Time, Seats");

            // Query matched to ViewBookingsGUI schema
            String query = "SELECT b.booking_id, u.username, m.title, s.show_time, b.seats " +
                    "FROM bookings b " +
                    "JOIN users u ON b.user_id = u.user_id " +
                    "JOIN showtimes s ON b.showtime_id = s.showtime_id " +
                    "JOIN movies m ON s.movie_id = m.movie_id";

            try (PreparedStatement result = conn.prepareStatement(query);
                    ResultSet rs = result.executeQuery()) {

                while (rs.next()) {
                    pw.printf("%d,%s,%s,%s,%s%n",
                            rs.getInt("booking_id"),
                            rs.getString("username"),
                            rs.getString("title"),
                            rs.getString("show_time"),
                            rs.getString("seats"));
                }
            }

            JOptionPane.showMessageDialog(null, "Data exported successfully to " + csvFile);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error exporting data: " + e.getMessage());
        }
    }
}
