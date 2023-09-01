package test.java;

import main.resources.config.DatabaseUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Objects;

public class MyFrame extends JFrame {
    MyFrame() {
        this.setTitle("FireSignal");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(480, 640);
        this.setLocationRelativeTo(null); // Center the frame on the screen

        // Set background color
        Color backgroundColor = Color.decode("#2b2b2b");
        Color buttonBackgroundColor = Color.decode("#3c3f41");
        this.getContentPane().setBackground(backgroundColor);

        // Create a JPanel with BoxLayout
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(backgroundColor);

        // Create a JLabel for the image
        JLabel imageLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("main/resources/images/icon.jpg")));

        // Scale the image to the desired size
        Image scaledImage = imageIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);

        imageLabel.setIcon(scaledImageIcon);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createVerticalStrut(100));
        panel.add(imageLabel);

        // Create a login JPanel with BoxLayout
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());
        loginPanel.setBackground(backgroundColor);

        GridBagConstraints c = new GridBagConstraints();

        JButton switchToLoginButton1 = new JButton("Login");
        switchToLoginButton1.setPreferredSize(new Dimension(0,50));
        switchToLoginButton1.setBackground(buttonBackgroundColor);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.5;
        loginPanel.add(switchToLoginButton1, c);

        JButton switchToRegisterButton1 = new JButton("Register");
        switchToRegisterButton1.setPreferredSize(new Dimension(0,50));

        c.gridx = 1;
        loginPanel.add(switchToRegisterButton1, c);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;

        loginPanel.add(Box.createVerticalStrut(25), c);

        JTextField loginPanelLogin = new JTextField(20);

        c.gridx = 0;
        c.gridy = 2;
        c.insets = new Insets(0,25, 0, 25);
        loginPanel.add(loginPanelLogin, c);

        JTextField loginPanelPassword = new JTextField(20);

        c.gridy = 3;
        loginPanel.add(loginPanelPassword, c);

        c.gridy = 4;
        c.insets = new Insets(0,0, 0, 0);
        loginPanel.add(Box.createVerticalStrut(25), c);

        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(0,50));

        c.gridy = 5;
        loginPanel.add(loginButton, c);

        // Create the register panel
        JPanel registerPanel = new JPanel();
        registerPanel.setLayout(new GridBagLayout());
        registerPanel.setBackground(backgroundColor);

        JButton switchToLoginButton2 = new JButton("Login");
        switchToLoginButton2.setPreferredSize(new Dimension(0,50));

        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        registerPanel.add(switchToLoginButton2, c);

        JButton switchToRegisterButton2 = new JButton("Register");
        switchToRegisterButton2.setPreferredSize(new Dimension(0,50));

        c.gridx = 1;
        registerPanel.add(switchToRegisterButton2, c);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;

        registerPanel.add(Box.createVerticalStrut(25), c);

        JTextField registerPanelLogin = new JTextField(20);

        c.gridx = 0;
        c.gridy = 2;
        c.insets = new Insets(0,25, 0, 25);
        registerPanel.add(registerPanelLogin, c);

        JTextField registerPanelPassword = new JTextField(20);

        c.gridy = 3;
        registerPanel.add(registerPanelPassword, c);

        c.gridy = 4;
        c.insets = new Insets(0,0, 0, 0);
        registerPanel.add(Box.createVerticalStrut(25), c);

        JButton registerButton = new JButton("Register");
        registerButton.setPreferredSize(new Dimension(0,50));

        c.gridy = 5;
        registerPanel.add(registerButton, c);

        // Create the card layout and add the panels
        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);
        cardPanel.add(loginPanel, "login");
        cardPanel.add(registerPanel, "register");

        // Create action listeners
        ActionListener loginSwitch = e -> cardLayout.show(cardPanel, "login");
        ActionListener registerSwitch = e -> cardLayout.show(cardPanel, "register");


        // Add action listeners to buttons
        switchToLoginButton1.addActionListener(loginSwitch);
        switchToLoginButton2.addActionListener(loginSwitch);

        switchToRegisterButton1.addActionListener(registerSwitch);
        switchToRegisterButton2.addActionListener(registerSwitch);

        // Add action listener to the login button
        DatabaseUtils DatabaseUtils;
        loginButton.addActionListener(e -> {
            // Get the entered username and password from the text fields
            String username = loginPanelLogin.getText();
            String password = loginPanelPassword.getText();

            // Validate the credentials against the database
            if (DatabaseUtils.validateUserCredentials(username, password)) {
                // User login successful
                // JOptionPane.showMessageDialog(null, "Login successful!");

                // Create the dashboard frame with the username
                DashboardFrame dashboardFrame = new DashboardFrame(username);

                // Replace the content pane of the main frame with the dashboard frame
                setContentPane(dashboardFrame.getContentPane());

                // Revalidate and repaint the main frame to reflect the changes
                revalidate();
                repaint();
            } else {
                // Invalid credentials
                JOptionPane.showMessageDialog(null, "Invalid username or password.");
            }
        });

        // Add action listener to the register button
        registerButton.addActionListener(e -> {
            String username = registerPanelLogin.getText();
            String password = registerPanelPassword.getText();

            // Validate if username and password are not empty
            if (username.isEmpty() || password.isEmpty()) {
                System.out.println("Please enter a username and password.");
                return;
            }

            // Register the user
            DatabaseUtils.registerUser(username, password);
        });

        panel.add(cardPanel);

        this.add(panel);
        this.setVisible(true);
    }
}
