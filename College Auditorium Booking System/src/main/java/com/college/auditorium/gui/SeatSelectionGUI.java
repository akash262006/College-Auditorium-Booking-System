package com.college.auditorium.gui;

import com.college.auditorium.util.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

public class SeatSelectionGUI extends JFrame {

    int userId;
    int showtimeId;

    Set<String> bookedSeats = new HashSet<>();
    Set<JToggleButton> selectedSeats = new HashSet<>();

    public SeatSelectionGUI(int userId, int showtimeId) {

        this.userId = userId;
        this.showtimeId = showtimeId;

        setTitle("Auditorium Seat Selection");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout(10, 10));

        JLabel screen = new JLabel("ALL EYES THIS WAY PLEASE!", JLabel.CENTER);
        screen.setOpaque(true);
        screen.setBackground(Color.LIGHT_GRAY);
        screen.setFont(new Font("Arial", Font.BOLD, 14));
        screen.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(screen, BorderLayout.NORTH);

        loadBookedSeats();   // 🔴 VERY IMPORTANT

        JPanel seatPanel = new JPanel(new GridLayout(5, 7, 10, 10));
        seatPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        char row = 'A';
        for (int r = 0; r < 5; r++) {

            seatPanel.add(new JLabel(String.valueOf(row)));

            for (int c = 1; c <= 6; c++) {
                String seatNo = row + String.valueOf(c);
                JToggleButton seat = new JToggleButton(seatNo);
                seat.setFocusPainted(false);

                // 🔒 BLOCK SEAT IF ALREADY BOOKED
                if (bookedSeats.contains(seatNo)) {
                    seat.setEnabled(false);
                    seat.setBackground(Color.GRAY);
                } else {
                    seat.setBackground(Color.WHITE);
                    seat.addActionListener(e -> {
                        if (seat.isSelected()) {
                            seat.setBackground(Color.GREEN);
                            selectedSeats.add(seat);
                        } else {
                            seat.setBackground(Color.WHITE);
                            selectedSeats.remove(seat);
                        }
                    });
                }

                seatPanel.add(seat);
            }
            row++;
        }

        add(seatPanel, BorderLayout.CENTER);

        JButton confirmBtn = new JButton("Confirm Booking");
        confirmBtn.addActionListener(e -> bookSeats());

        add(confirmBtn, BorderLayout.SOUTH);

        setVisible(true);
    }

    // 🔴 LOAD ALREADY BOOKED SEATS
    private void loadBookedSeats() {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "SELECT seats FROM bookings WHERE showtime_id=?"
            );
            ps.setInt(1, showtimeId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String[] seats = rs.getString("seats").split(",");
                for (String s : seats) {
                    bookedSeats.add(s.trim());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ✅ STORE EXACT SEAT NUMBERS
    private void bookSeats() {
        try {
            if (selectedSeats.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Select at least one seat");
                return;
            }

            StringBuilder seatStr = new StringBuilder();
            for (JToggleButton seat : selectedSeats) {
                seatStr.append(seat.getText()).append(",");
            }

            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO bookings (user_id, showtime_id, seats) VALUES (?, ?, ?)"
            );

            ps.setInt(1, userId);
            ps.setInt(2, showtimeId);
            ps.setString(3, seatStr.toString());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this,
                "Booking successful!\nSeats: " + seatStr);

            dispose();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Booking failed");
        }
    }
}
