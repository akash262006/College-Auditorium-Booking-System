package com.college.auditorium.gui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ModernTheme {

    // Color Palette
    // Deep Blue Primary
    public static final Color PRIMARY_COLOR = new Color(15, 23, 42);
    // Vibrant Blue Accent
    public static final Color ACCENT_COLOR = new Color(59, 130, 246);
    // Success Green
    public static final Color SUCCESS_COLOR = new Color(16, 185, 129);
    // Danger Red
    public static final Color DANGER_COLOR = new Color(239, 68, 68);
    // Light Gray Background
    public static final Color BG_COLOR = new Color(248, 250, 252);
    // Text Colors
    public static final Color TEXT_DARK = new Color(30, 41, 59);
    public static final Color TEXT_LIGHT = new Color(255, 255, 255);

    // Fonts
    public static final Font FONT_HEADER = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font FONT_BODY = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD, 14);

    public static void applymodernButton(JButton btn, Color bgColor) {
        btn.setFont(FONT_BUTTON);
        btn.setBackground(bgColor);
        btn.setForeground(TEXT_LIGHT);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        // Simple hover effect can be added here if needed via MouseListener
        // but for basic setup we stick to Swing properties
    }

    public static void applyCardStyle(JPanel panel) {
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));
    }
}
