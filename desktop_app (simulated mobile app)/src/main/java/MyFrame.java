package main.java;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {
    private final CardLayout cardLayout;
    private final JPanel cardPanel;
    private final LoginPanel loginPanel;
    private final DashboardMenu dashboardMenuPanel;
    private final MessagePanel messagePanel;
    private final JLayeredPane layeredMessagePane;

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

        // Create an instance of SessionManager and set the reference
        SessionManager.setMyFrame(this);

        // Show the login panel initially
        cardLayout.show(cardPanel, "login_register");

        this.setLayout(new BorderLayout());
        JLayeredPane layeredPane = new JLayeredPane();
        add(layeredPane, BorderLayout.CENTER);
        layeredPane.setBounds(0, 0, 480, 640);

        cardPanel.setBounds(0, 0, 480, 640);
        cardPanel.setOpaque(true);

        // Create a layered pane
        layeredMessagePane = new JLayeredPane();
        this.add(layeredMessagePane, BorderLayout.CENTER);
        layeredMessagePane.setBounds(0, 0, 480, 640);

        // Create the translucent overlay panel
        JPanel overlayPanel = new JPanel();
        overlayPanel.setBackground(new Color(0, 0, 0, 150));
        overlayPanel.setBounds(0, 0, 480, 640);
        layeredMessagePane.add(overlayPanel, Integer.valueOf(0));

        // Create the message panel and add it to the overlayPanel
        messagePanel = new MessagePanel(this);
        messagePanel.setBounds(84, 120, 300, 400);
        messagePanel.setOpaque(true);
        layeredMessagePane.add(messagePanel, Integer.valueOf(1));

        layeredPane.add(cardPanel, Integer.valueOf(0));
        layeredPane.add(layeredMessagePane, Integer.valueOf(1));

        this.setVisible(true);

        layeredMessagePane.setVisible(false);
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

    public void showMessage(String message) {
        messagePanel.setMessage(message);
        layeredMessagePane.setVisible(true);
    }

    public void closeMessage() {
        layeredMessagePane.setVisible(false);
    }
}
