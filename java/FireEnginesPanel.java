package main.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FireEnginesPanel extends JPanel {
    private JPanel contentPanel;
    private DashboardMenu frame;
    private JPanel slidingPanel;

    public FireEnginesPanel(DashboardMenu frame) {
        this.frame = frame;

        setBackground(GlobalVariables.BACKGROUND_COLOR); // Set the background color of the dashboard panel

        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
    }

    public void populateFireEngines() {
        // TODO: Retrieve fire engine data from the database

        // Example fire engine data
        String[] fireEngineNames = {"Fire Engine 1", "Fire Engine 2", "Fire Engine 3"};

        for (String fireEngineName : fireEngineNames) {
            JButton fireEngineButton = createFireEngineButton(fireEngineName);
            add(fireEngineButton);
        }
    }

    private JButton createFireEngineButton(String fireEngineName) {
        JButton button = new JButton(fireEngineName);
        button.setPreferredSize(new Dimension(120, 40));

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Retrieve additional information for the selected fire engine from the database

                // Example additional information
                String additionalInfo = "This is additional information for " + fireEngineName;

                // Show popup window with additional information
                showAdditionalInfoPopup(additionalInfo);
            }
        });

        return button;
    }

    private void showAdditionalInfoPopup(String additionalInfo) {
        JDialog popup = new JDialog();
        popup.setTitle("Fire Engine Details");
        popup.setSize(300, 200);
        popup.setLocationRelativeTo(null);
        popup.setModal(true);

        JLabel infoLabel = new JLabel(additionalInfo);
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        popup.getContentPane().add(infoLabel);
        popup.setVisible(true);
    }
}
