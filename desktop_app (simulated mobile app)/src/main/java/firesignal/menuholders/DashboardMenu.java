package firesignal.menuholders;

import firesignal.panels.AlarmsPanel;
import firesignal.panels.DashboardPanel;
import firesignal.panels.FireEnginesPanel;
import firesignal.panels.FirefightersPanel;
import firesignal.utils.GlobalVariables;

import javax.swing.*;
import java.awt.*;

public class DashboardMenu extends JPanel {
    private final CardLayout cardLayout;
    private final JPanel cardPanel;
    private final DashboardPanel dashboardPanel;
    private final AlarmsPanel alarmsPanel;
    private final MyFrame frame;

    public DashboardMenu(MyFrame frame) {
        this.frame = frame;

        // Set background color
        setBackground(GlobalVariables.BACKGROUND_COLOR);

        // Create the card layout and add the panels
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Create the dashboard panel
        dashboardPanel = new DashboardPanel(this);
        cardPanel.add(dashboardPanel, "dashboardPanel");

        // Create the alarms panel
        alarmsPanel = new AlarmsPanel(this);
        cardPanel.add(alarmsPanel, "alarmsPanel");

        // Create the fire engines panel
        FireEnginesPanel fireEnginesPanel = new FireEnginesPanel(this);
        cardPanel.add(fireEnginesPanel, "fireEnginesPanel");

        // Create the firefighters panel
        FirefightersPanel firefightersPanel = new FirefightersPanel(this);
        cardPanel.add(firefightersPanel, "firefightersPanel");

        // Add the cardPanel to the frame
        this.add(cardPanel);

        dashboardPanel.updateUsername();
        cardLayout.show(cardPanel, "dashboardPanel");

        this.setVisible(true);
    }

    public void switchToDashboard() {
        dashboardPanel.updateUsername(); // Set the username in the dashboard panel
        cardLayout.show(cardPanel, "dashboardPanel");
    }

    public void switchToAlarms() {
        alarmsPanel.updateUsername();
        alarmsPanel.displayAlarmButtons();
        cardLayout.show(cardPanel, "alarmsPanel");
    }

    public void switchToFireEngines() {
        cardLayout.show(cardPanel, "fireEnginesPanel");
    }

    public void switchToFirefighters() {
        cardLayout.show(cardPanel, "firefightersPanel");
    }

    public void switchToLogin() {
        frame.switchToLogin();
    }

    public void updateDashboardPanelUsername() { dashboardPanel.updateUsername(); }
}
