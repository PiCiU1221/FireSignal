package main.java;

import main.resources.config.ApiService;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class DashboardPanel extends JPanel {
    private final JLabel usernameLabel;
    private final JPanel contentPanel;
    private final JPanel slidingPanel;

    public DashboardPanel(DashboardMenu frame) {

        setBackground(GlobalVariables.BACKGROUND_COLOR); // Set the background color of the dashboard panel

        setLayout(new BorderLayout());

        // Create a panel for the hamburger menu
        JPanel menuPanel = new JPanel(new MigLayout("fill, insets 0"));
        menuPanel.setBackground(GlobalVariables.BACKGROUND_COLOR);
        menuPanel.setPreferredSize(new Dimension(320, getHeight()));

        // Create a button to toggle the visibility of the sliding frame
        JToggleButton toggleButton = createHamburgerMenuButton();
        toggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (slidingPanel.isVisible()) {
                    slidingPanel.setVisible(false);
                    enableContentPanel(true);
                } else {
                    slidingPanel.setVisible(true);
                    enableContentPanel(false);
                }
            }
        });

        menuPanel.add(toggleButton, "pos 10 10");

        // Create a panel for the sliding frame content
        slidingPanel = new JPanel(new MigLayout("alignx 50%, wrap"));
        slidingPanel.setBackground(GlobalVariables.BACKGROUND_COLOR);
        slidingPanel.setPreferredSize(new Dimension(220, getHeight())); // Set the initial width
        slidingPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 0, 0, 4), // Empty border for spacing
                BorderFactory.createMatteBorder(0, 0, 0, 2, Color.LIGHT_GRAY) // Light gray right border
        ));

        // Create a label for the username
        usernameLabel = new JLabel();
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        usernameLabel.setForeground(Color.WHITE);
        updateUsername();
        slidingPanel.add(usernameLabel, "gap 0 0 120 0");

        // Create logout button
        JToggleButton logoutButton = new JToggleButton("Logout");
        logoutButton.setPreferredSize(new Dimension(150, 80));
        logoutButton.setBackground(Color.RED);
        logoutButton.setForeground(Color.WHITE);
        logoutButton.addActionListener(e -> {
            slidingPanel.setVisible(false);
            frame.switchToLogin();
        });

        slidingPanel.add(logoutButton, "center, gap 0 0 150 0");

        menuPanel.add(slidingPanel, "grow");

        // Create a panel for the content
        contentPanel = new JPanel(new MigLayout("center, wrap, fill"));
        contentPanel.setBackground(GlobalVariables.BACKGROUND_COLOR);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 50, 2));

        // Create buttons with images and text
        JToggleButton alarmsButton = createImageButton("Alarms",
                "../resources/images/siren_icon.png", frame, "alarms");
        JToggleButton fireEnginesButton = createImageButton("Fire Engines",
                "../resources/images/fireEngine_icon.png", frame, "fireEngines");
        JToggleButton firefightersButton = createImageButton("Firefighters",
                "../resources/images/fireFighter_icon.png", frame, "firefighters");

        // Add the buttons to the content panel
        contentPanel.add(alarmsButton, "gapbottom 10, aligny 50%, alignx 47%");
        contentPanel.add(fireEnginesButton, "gapbottom 10, aligny 50%, alignx 47%");
        contentPanel.add(firefightersButton, "gapbottom 10, aligny 50%, alignx 47%");

        frame.setLayout(new BorderLayout());
        JLayeredPane layeredPane = new JLayeredPane();
        add(layeredPane, BorderLayout.CENTER);
        layeredPane.setBounds(0, 0, 480, 640);

        contentPanel.setBounds(0, 0, 480, 640);
        contentPanel.setOpaque(true);
        menuPanel.setBounds(0, 0, 320, 640);
        menuPanel.setOpaque(false);

        layeredPane.add(contentPanel, Integer.valueOf(0));
        layeredPane.add(menuPanel, Integer.valueOf(1));

        slidingPanel.setVisible(false);
    }

    private void enableContentPanel(boolean enabled) {
        for (Component component : contentPanel.getComponents()) {
            component.setEnabled(enabled);
        }
    }

    private JToggleButton createImageButton(String text, String imagePath, DashboardMenu dashboardMenu, String functionName) {
        JToggleButton button = new JToggleButton();

        // Create a custom panel for the button's content
        JPanel contentPanel = new JPanel(new MigLayout("insets 0, gapx 25, aligny center")); // 25-pixel horizontal gap
        contentPanel.setOpaque(false); // Make the panel transparent

        // Load the image from the classpath
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource(imagePath)));
        // Scale the image to fit the button
        Image scaledImage = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        JLabel imageLabel= new JLabel(new ImageIcon(scaledImage));
        contentPanel.add(imageLabel);

        // Create the text label and customize its appearance
        JLabel textLabel = new JLabel(text);
        textLabel.setFont(textLabel.getFont().deriveFont(24f));
        textLabel.setForeground(Color.WHITE);
        contentPanel.add(textLabel, "aligny center"); // Add the text

        button.setFocusPainted(false); // Remove the button focus border
        button.setContentAreaFilled(false); // Make the button transparent
        button.setLayout(new BorderLayout()); // Set the button's layout to BorderLayout
        button.add(contentPanel, BorderLayout.CENTER); // Add the custom panel as the button's content

        // Set the preferred size for the button
        button.setPreferredSize(new Dimension(300, 150));
        button.setMinimumSize(new Dimension(300, 150));

        button.addActionListener(e -> {
            // Call the specified function in the DashboardMenu instance
            switch (functionName) {
                case "alarms" -> dashboardMenu.switchToAlarms();
                case "fireEngines" -> dashboardMenu.switchToFireEngines();
                case "firefighters" -> dashboardMenu.switchToFirefighters();
            }
        });

        return button;
    }

    private JToggleButton createHamburgerMenuButton() {
        JToggleButton button = new JToggleButton();

        // Load the image from the classpath
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("../resources/images/hamburger_icon.png")));
        Image scaledImage = icon.getImage().getScaledInstance(50, 40, Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(scaledImage));

        button.setContentAreaFilled(false); // Make the button area transparent
        button.setBorderPainted(false); // Remove the button border
        button.setFocusPainted(false); // Remove the button focus border
        button.setPreferredSize(new Dimension(50, 40));
        button.setBackground(GlobalVariables.BUTTON_BACKGROUND_COLOR);
        button.setForeground(Color.WHITE);
        button.setMargin(new Insets(0,0,0,0));

        return button;
    }

    public void updateUsername() {
        usernameLabel.setText("Welcome, " + ApiService.getFirefighterName(SessionManager.getLoggedInUser()) + "!");
    }
}
