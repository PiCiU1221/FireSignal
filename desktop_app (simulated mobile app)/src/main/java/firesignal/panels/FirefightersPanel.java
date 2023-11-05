package firesignal.panels;

import firesignal.menuholders.DashboardMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FirefightersPanel extends JPanel {
    public FirefightersPanel(DashboardMenu frame) {

        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
    }

    public void populateFirefighters() {
        // TODO: Retrieve firefighter data from the database

        // Example firefighter data
        String[] firefighterNames = {"Firefighter 1", "Firefighter 2", "Firefighter 3"};

        for (String firefighterName : firefighterNames) {
            JButton firefighterButton = createFirefighterButton(firefighterName);
            add(firefighterButton);
        }
    }

    private JButton createFirefighterButton(String firefighterName) {
        JButton button = new JButton(firefighterName);
        button.setPreferredSize(new Dimension(120, 40));

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Retrieve additional information for the selected firefighter from the database

                // Example additional information
                String additionalInfo = "This is additional information for " + firefighterName;

                // Show popup window with additional information
                showAdditionalInfoPopup(additionalInfo);
            }
        });

        return button;
    }

    private void showAdditionalInfoPopup(String additionalInfo) {
        JDialog popup = new JDialog();
        popup.setTitle("Firefighter Details");
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
