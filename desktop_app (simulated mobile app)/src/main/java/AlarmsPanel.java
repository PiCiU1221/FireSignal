package main.java;

import main.resources.config.AlarmDisplayApiService;
import main.resources.config.ApiService;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.List;
import java.time.format.DateTimeFormatter;

public class AlarmsPanel extends JPanel {
    private final JLabel usernameLabel;
    private final JPanel contentPanel;
    private final JPanel slidingPanel;
    private int currentPage;

    public AlarmsPanel(DashboardMenu frame) {
        setBackground(GlobalVariables.BACKGROUND_COLOR);

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
        slidingPanel.add(usernameLabel, "gap 0 0 120 0");

        // Create back button
        JToggleButton backButton = new JToggleButton("Back");
        backButton.setPreferredSize(new Dimension(150, 80));
        backButton.setBackground(GlobalVariables.BACKGROUND_COLOR);
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> {
            slidingPanel.setVisible(false);
            frame.switchToDashboard();
        });

        slidingPanel.add(backButton, "center, gap 0 0 100 0, wrap");

        // Create logout button
        JToggleButton logoutButton = new JToggleButton("Logout");
        logoutButton.setPreferredSize(new Dimension(150, 80));
        logoutButton.setBackground(Color.RED);
        logoutButton.setForeground(Color.WHITE);
        logoutButton.addActionListener(e -> {
            slidingPanel.setVisible(false);
            frame.switchToLogin();
        });

        slidingPanel.add(logoutButton, "center");

        menuPanel.add(slidingPanel, "grow");

        // Create a panel for the content
        contentPanel = new JPanel(new MigLayout("center, wrap, gap 10"));
        contentPanel.setBackground(GlobalVariables.BACKGROUND_COLOR);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(70, 0, 50, 10));

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

        // Set initial current page
        currentPage = 1;
    }

    private void enableContentPanel(boolean enabled) {
        for (Component component : contentPanel.getComponents()) {
            component.setEnabled(enabled);
        }
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

    private JToggleButton createAlarmButton(Alarm alarm) {
        JToggleButton button = new JToggleButton();

        // Define the data variables
        String formattedDatetime = alarm.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String cityAndStreet = alarm.getCity() + ", " + alarm.getStreet();
        int acceptedFirefightersCount = AlarmDisplayApiService.getAcceptedFirefightersCount(alarm.getAlarmId());
        int alarmedFirefightersCount = AlarmDisplayApiService.getAlarmedFirefightersCount(alarm.getAlarmId());
        boolean hasAcceptedCommander = AlarmDisplayApiService.hasAcceptedCommander(alarm.getAlarmId());
        int acceptedDriversCount = AlarmDisplayApiService.getAcceptedDriversCount(alarm.getAlarmId());
        boolean hasAcceptedTechnicalRescue = AlarmDisplayApiService.hasAcceptedTechnicalRescue(alarm.getAlarmId());

        // Create a custom panel for the button's content
        JPanel contentPanel = new JPanel(new MigLayout("insets 0 10 10 10, fill", "[]-100[][]"));
        contentPanel.setOpaque(false); // Make the panel transparent

        // Create the text label for datetime and customize its appearance
        JLabel datetimeLabel = new JLabel(formattedDatetime);
        datetimeLabel.setForeground(Color.WHITE);
        datetimeLabel.setFont(datetimeLabel.getFont().deriveFont(Font.BOLD, 14));
        contentPanel.add(datetimeLabel, "wrap"); // Wrap to the next line after this label

        // Create the text label for city and address
        JLabel locationLabel = new JLabel(cityAndStreet);

        // Create the font with a bigger size
        Font biggerFont = locationLabel.getFont().deriveFont(locationLabel.getFont().getSize2D() + 1f);

        locationLabel.setFont(biggerFont);
        locationLabel.setForeground(Color.WHITE);
        contentPanel.add(locationLabel, "wrap"); // Wrap to the next line after this label

        // Create the text label for the number of firefighters that accepted
        String acceptedLabelText = "<html><font color='white'>Accepted: </font>";
        if (acceptedFirefightersCount > 0) {
            acceptedLabelText += "<font color='green'>" + acceptedFirefightersCount + "</font></html>";
        } else {
            acceptedLabelText += "<font color='red'>" + acceptedFirefightersCount + "</font></html>";
        }

        JLabel acceptedLabel = new JLabel(acceptedLabelText);
        acceptedLabel.setForeground(Color.WHITE);
        acceptedLabel.setFont(biggerFont);

        // Spacer label
        JLabel spacerLabel = new JLabel();

        // Create the text label for the number of firefighters that were alarmed
        JLabel alarmedLabel = new JLabel("Alarmed: " + alarmedFirefightersCount);
        alarmedLabel.setForeground(Color.WHITE);
        alarmedLabel.setFont(biggerFont);

        contentPanel.add(acceptedLabel, "left"); // Align the alarmed label to the left
        contentPanel.add(spacerLabel, "center");
        contentPanel.add(alarmedLabel, "right, wrap"); // Align the alarmed label to the right

        // Create the text label for commander and customize its appearance
        String commanderLabelText = "<html><font color='white'>Cmdr: </font>";
        if (hasAcceptedCommander) {
            commanderLabelText += "<font color='green'>✓</font></html>";
        } else {
            commanderLabelText += "<font color='red'>✘</font></html>";
        }

        JLabel commanderLabel = new JLabel(commanderLabelText);
        commanderLabel.setForeground(Color.WHITE);
        commanderLabel.setFont(biggerFont);
        contentPanel.add(commanderLabel, "alignx left"); // Align the commander label to the left and wrap to the next line

        // Create the text label for driver count and customize its appearance
        String driverLabelText = "<html><font color='white'>Drivers: </font>";
        if (acceptedFirefightersCount > 0) {
            driverLabelText += "<font color='green'>" + acceptedDriversCount + "</font></html>";
        } else {
            driverLabelText += "<font color='red'>" + acceptedDriversCount + "</font></html>";
        }

        JLabel driverLabel = new JLabel(driverLabelText);
        driverLabel.setForeground(Color.WHITE);
        driverLabel.setFont(biggerFont);
        contentPanel.add(driverLabel, "alignx center"); // Align the driver label to the center and wrap to the next line

        // Create the text label for technical rescue and customize its appearance
        String technicalRescueLabelText = "<html><font color='white'>Tech. R: </font>";
        if (hasAcceptedTechnicalRescue) {
            technicalRescueLabelText += "<font color='green'>✓</font></html>";
        } else {
            technicalRescueLabelText += "<font color='red'>✘</font></html>";
        }

        JLabel technicalRescueLabel = new JLabel(technicalRescueLabelText);
        technicalRescueLabel.setForeground(Color.WHITE);
        technicalRescueLabel.setFont(biggerFont);
        contentPanel.add(technicalRescueLabel, "alignx right"); // Align the technical rescue label to the right

        // Set the border color
        Color borderColor = new Color(128, 128, 128);
        Border lineBorder = BorderFactory.createLineBorder(borderColor, 1);
        Border emptyBorder = BorderFactory.createEmptyBorder(1, 1, 1, 1);
        button.setBorder(BorderFactory.createCompoundBorder(lineBorder, emptyBorder));


        button.setFocusPainted(false); // Remove the button focus border
        button.setContentAreaFilled(false); // Make the button transparent
        button.setLayout(new BorderLayout()); // Set the button's layout to BorderLayout
        button.add(contentPanel, BorderLayout.CENTER); // Add the custom panel as the button's content

        // Set the preferred size for the button
        button.setPreferredSize(new Dimension(420, 100));
        button.setMinimumSize(new Dimension(420, 100));

        // Disable the button
        button.setEnabled(false);

        return button;
    }

    private JButton createNavigationButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(100, 40));
        button.setMinimumSize(new Dimension(100, 40));
        button.setBackground(GlobalVariables.BUTTON_BACKGROUND_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false); // Remove the button focus border
        button.setContentAreaFilled(false); // Make the button transparent
        button.setFont(button.getFont().deriveFont(Font.BOLD, 16)); // Increase the font size

        // Set the border color and style
        Color borderColor = new Color(128, 128, 128);
        Border lineBorder = BorderFactory.createLineBorder(borderColor, 1);
        Border emptyBorder = BorderFactory.createEmptyBorder(1, 1, 1, 1);
        button.setBorder(BorderFactory.createCompoundBorder(lineBorder, emptyBorder));

        return button;
    }


    private void addAlarmButtons(int page) {
        // Retrieve the newest alarms for the logged-in firefighter from the database
        List<Alarm> newestAlarms = AlarmDisplayApiService.getAlarmsForFirefighter((page - 1), 4);

        // Clear any existing buttons
        contentPanel.removeAll();

        // Create and add the buttons for the newest alarms
        assert newestAlarms != null;
        for (Alarm alarm : newestAlarms) {
            JToggleButton alarmButton = createAlarmButton(alarm);
            contentPanel.add(alarmButton, "gapbottom 1, aligny 50%, alignx 47%");
        }

        // Calculate the number of remaining slots
        int remainingSlots = 4 - newestAlarms.size();

        // Add invisible placeholder components to occupy the remaining slots
        for (int i = 0; i < remainingSlots; i++) {
            JPanel placeholderPanel = new JPanel();
            placeholderPanel.setPreferredSize(new Dimension(420, 100));
            placeholderPanel.setMinimumSize(new Dimension(420, 100));
            placeholderPanel.setOpaque(false); // Make the panel transparent
            contentPanel.add(placeholderPanel, "gapbottom 1, aligny 50%, alignx 47%");
        }

        // Create a panel for the previous and next buttons
        JPanel buttonPanel = new JPanel(new MigLayout("wrap 3, fill", "[120]50[100]50[120]"));
        buttonPanel.setPreferredSize(new Dimension(420, 50));
        buttonPanel.setMinimumSize(new Dimension(420, 50));
        buttonPanel.setOpaque(false);

        // Create previous and next page buttons
        JButton previousButton = createNavigationButton("PREVIOUS");
        JButton nextButton = createNavigationButton("NEXT");

        // Disable the "Previous" button if there are no alarms on the previous page
        boolean hasPreviousAlarms = page > 1;
        previousButton.setEnabled(hasPreviousAlarms);

        previousButton.addActionListener(e -> {
            if (currentPage > 1) {
                currentPage--;
                addAlarmButtons(currentPage);
            }
        });

        // Disable the "Next" button if there are no alarms on the next page
        boolean hasNextAlarms = Objects.requireNonNull(AlarmDisplayApiService.getAlarmsForFirefighter(page, 4)).size() > 0;
        nextButton.setEnabled(hasNextAlarms);

        nextButton.addActionListener(e -> {
            currentPage++;
            addAlarmButtons(currentPage);
        });

        // Create a label for the current page information
        JLabel pageLabel = new JLabel("Page " + page);
        pageLabel.setForeground(Color.WHITE);
        pageLabel.setFont(pageLabel.getFont().deriveFont(Font.BOLD, 16));

        // Add the buttons to the button panel
        buttonPanel.add(previousButton, "grow");
        buttonPanel.add(pageLabel, "grow, center, gapleft 15px push"); // Add the page label to the middle column
        buttonPanel.add(nextButton, "grow");

        // Add the button panel to the content panel
        contentPanel.add(buttonPanel, "center");

        // Repaint the panel to update the UI
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public void displayAlarmButtons() {
        addAlarmButtons(1);
    }

    public void updateUsername() {
        usernameLabel.setText("Welcome, " + ApiService.getFirefighterName(SessionManager.getLoggedInUser()) + "!");
    }
}