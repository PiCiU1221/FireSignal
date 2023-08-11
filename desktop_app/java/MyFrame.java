package main.java;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {
    private final CardLayout cardLayout;
    private final JPanel cardPanel;
    private final LoginPanel loginPanel;
    DashboardMenu dashboardMenuPanel;

    public MyFrame() {
        this.setTitle("FireSignal");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(480, 640);
        this.setLocationRelativeTo(null); // Center the frame on the screen

        // Set background color
        setBackground(GlobalVariables.BACKGROUND_COLOR);

        // Create the card layout and add the panels
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Create the login panel
        loginPanel = new LoginPanel(this);
        cardPanel.add(loginPanel, "login_register");

        // Create the dashboard panel
        dashboardMenuPanel = new DashboardMenu(this);
        cardPanel.add(dashboardMenuPanel, "dashboardMenu");

        // Add the cardPanel to the frame
        this.add(cardPanel);

        // Show the login panel initially
        cardLayout.show(cardPanel, "login_register");

        this.setVisible(true);
    }

    public void switchToDashboardMenu() {
        cardLayout.show(cardPanel, "dashboardMenu");
        dashboardMenuPanel.updateDashboardPanelUsername();
    }

    public void switchToLogin() {
        loginPanel.clearFields();
        SessionManager.resetLoggedInUser();
        cardLayout.show(cardPanel, "login_register");
    }
}
