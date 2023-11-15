package firesignal.panels;

import firesignal.menuholders.MyFrame;
import firesignal.utils.ApiResponse;
import firesignal.utils.ApiService;
import firesignal.utils.GlobalVariables;
import firesignal.utils.SessionManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class LoginPanel extends JPanel {
    private final JTextField loginPanelLogin;
    private final JTextField loginPanelPassword;
    private final JTextField registerPanelLogin;
    private final JTextField registerPanelPassword;

    public LoginPanel(MyFrame frame) {
        setLayout(new BorderLayout());

        // Create a JPanel with BoxLayout
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(GlobalVariables.BACKGROUND_COLOR);

        // Create a JLabel for the image
        JLabel imageLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon(Objects.requireNonNull(LoginPanel.class.getResource("/images/siren_logo.png")));

        // Scale the image to the desired size
        Image scaledImage = imageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);

        imageLabel.setIcon(scaledImageIcon);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createVerticalStrut(100));
        panel.add(imageLabel);

        // Create a login JPanel with BoxLayout
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());
        loginPanel.setBackground(GlobalVariables.BACKGROUND_COLOR);

        GridBagConstraints c = new GridBagConstraints();

        JButton switchToLoginButton1 = createStyledButton("Login", 0, 50);
        //switchToLoginButton1.setPreferredSize(new Dimension(0,50));
        //switchToLoginButton1.setBackground(utils.GlobalVariables.BUTTON_BACKGROUND_COLOR);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.5;
        loginPanel.add(switchToLoginButton1, c);

        JButton switchToRegisterButton1 = createStyledButton("Register", 0, 50);

        c.gridx = 1;
        loginPanel.add(switchToRegisterButton1, c);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;

        loginPanel.add(Box.createVerticalStrut(25), c);

        loginPanelLogin = new JTextField(20);
        loginPanelLogin.setFont(new Font("Arial", Font.PLAIN, 16));

        c.gridx = 0;
        c.gridy = 2;
        c.insets = new Insets(0,25, 0, 25);
        loginPanel.add(loginPanelLogin, c);

        loginPanelPassword = new JPasswordField(20);
        loginPanelPassword.setFont(new Font("Arial", Font.PLAIN, 16));

        c.gridy = 3;
        loginPanel.add(loginPanelPassword, c);

        c.gridy = 4;
        c.insets = new Insets(0,0, 0, 0);
        loginPanel.add(Box.createVerticalStrut(25), c);

        JButton loginButton = createStyledButton("Login", 0, 50);

        c.gridy = 5;
        loginPanel.add(loginButton, c);

        // Create the register panel
        JPanel registerPanel = new JPanel();
        registerPanel.setLayout(new GridBagLayout());
        registerPanel.setBackground(GlobalVariables.BACKGROUND_COLOR);

        JButton switchToLoginButton2 = createStyledButton("Login", 0, 50);

        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        registerPanel.add(switchToLoginButton2, c);

        JButton switchToRegisterButton2 = createStyledButton("Register", 0, 50);

        c.gridx = 1;
        registerPanel.add(switchToRegisterButton2, c);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;

        registerPanel.add(Box.createVerticalStrut(25), c);

        registerPanelLogin = new JTextField(20);
        registerPanelLogin.setFont(new Font("Arial", Font.PLAIN, 16));

        c.gridx = 0;
        c.gridy = 2;
        c.insets = new Insets(0,25, 0, 25);
        registerPanel.add(registerPanelLogin, c);

        registerPanelPassword = new JPasswordField(20);
        registerPanelPassword.setFont(new Font("Arial", Font.PLAIN, 16));

        c.gridy = 3;
        registerPanel.add(registerPanelPassword, c);

        c.gridy = 4;
        c.insets = new Insets(0,0, 0, 0);
        registerPanel.add(Box.createVerticalStrut(25), c);

        JButton registerButton = createStyledButton("Register", 0, 50);

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
        loginButton.addActionListener(e -> {
            // Get the entered username and password from the text fields
            String username = loginPanelLogin.getText();
            String password = loginPanelPassword.getText();

            ApiResponse<String> response = ApiService.validateUser(username, password);

            // Validate the credentials against the database
            if (response.isSuccess()) {
                // User login successful
                String token = response.getData();

                // To check the token value
                //JOptionPane.showMessageDialog(null, token);

                // Switch to the dashboard frame
                SessionManager.setLoggedInUser(username, token);
                frame.switchToDashboardMenu();
            } else {
                // Invalid credentials
                JOptionPane.showMessageDialog(null, response.getMessage());
            }
        });

        registerButton.addActionListener(e -> {
            String username = registerPanelLogin.getText();
            String password = registerPanelPassword.getText();

            // Validate if username and password are not empty
            if (username.isEmpty() || password.isEmpty()) {
                // Show a message dialog to the user
                JOptionPane.showMessageDialog(null, "Please enter a username and password.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Register the user
            ApiResponse<String> response = ApiService.registerUser(username, password);

            if (response.isSuccess()) {
                // Registration successful
                JOptionPane.showMessageDialog(null, response.getMessage());

                // Clear the fields
                clearFields();

                // Switch to the login panel
                cardLayout.show(cardPanel, "login");
            } else {
                // Registration failed
                JOptionPane.showMessageDialog(null, response.getMessage());
            }
        });

        panel.add(cardPanel);

        this.add(panel);
        this.setVisible(true);
    }

    public void clearFields() {
        loginPanelLogin.setText("");
        loginPanelPassword.setText("");
        registerPanelLogin.setText("");
        registerPanelPassword.setText("");
    }

    private JButton createStyledButton(String text, int width, int height) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(width, height));
        button.setBackground(new Color(105, 105, 105)); // Darker gray color
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Add subtle hover effect
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button.setBackground(new Color(80, 80, 80)); // Slightly darker gray on press
            }
        });

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(80, 80, 80).brighter()); // Lighter gray on hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(105, 105, 105)); // Restore the original dark gray color on exit
            }
        });

        return button;
    }
}
