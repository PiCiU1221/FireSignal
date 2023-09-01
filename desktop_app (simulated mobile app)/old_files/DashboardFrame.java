package test.java;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {
    DashboardFrame(String username) {
        // Create a panel for the dashboard
        JPanel dashboardPanel = new JPanel();
        // Set background color
        Color backgroundColor = Color.decode("#2b2b2b");
        dashboardPanel.setBackground(backgroundColor);

        // Create a label for the username
        JLabel usernameLabel = new JLabel("Username: " + username);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        usernameLabel.setForeground(Color.WHITE);
        dashboardPanel.add(usernameLabel);

        // Create a logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            // Create the dashboard frame with the username
            old_files.MyFrame frame = new old_files.MyFrame();

            // Replace the content pane of the main frame with the dashboard frame
            setContentPane(frame.getContentPane());

            // Revalidate and repaint the main frame to reflect the changes
            revalidate();
            repaint();
        });
        dashboardPanel.add(logoutButton);

        // Add the panel to the dashboard frame
        this.add(dashboardPanel);
    }
}
