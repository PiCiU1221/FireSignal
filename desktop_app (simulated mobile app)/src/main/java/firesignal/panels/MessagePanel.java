package firesignal.panels;

import firesignal.menuholders.MyFrame;
import firesignal.utils.GlobalVariables;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MessagePanel extends JPanel {

    private String id;
    private String firefighterId;
    private final JTextArea cityLabel;
    private final JTextArea streetLabel;
    private final JTextArea descriptionLabel;

    public MessagePanel(MyFrame frame) {
        setLayout(new BorderLayout());
        setBackground(GlobalVariables.BACKGROUND_COLOR);

        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 0, 0, 0),
                BorderFactory.createMatteBorder(4, 4, 4, 4, Color.LIGHT_GRAY)
        ));

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setOpaque(false);

        cityLabel = new JTextArea("");
        cityLabel.setLineWrap(true);
        cityLabel.setWrapStyleWord(true);
        cityLabel.setEditable(false);
        cityLabel.setForeground(Color.WHITE);
        cityLabel.setFont(new Font("Arial", Font.BOLD, 18));
        cityLabel.setOpaque(false);
        cityLabel.setBorder(new EmptyBorder(20, 20, 10, 20));
        headerPanel.add(cityLabel);

        streetLabel = new JTextArea("");
        streetLabel.setLineWrap(true);
        streetLabel.setWrapStyleWord(true);
        streetLabel.setEditable(false);
        streetLabel.setForeground(Color.WHITE);
        streetLabel.setFont(new Font("Arial", Font.BOLD, 18));
        streetLabel.setOpaque(false);
        streetLabel.setBorder(new EmptyBorder(0, 20, 10, 20));
        headerPanel.add(streetLabel);

        add(headerPanel, BorderLayout.NORTH);

        descriptionLabel = new JTextArea("");
        descriptionLabel.setLineWrap(true); // Enable text wrapping
        descriptionLabel.setWrapStyleWord(true); // Wrap at word boundaries
        descriptionLabel.setEditable(false); // Make the JTextArea read-only
        descriptionLabel.setForeground(Color.WHITE);
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 18)); // Set font and size
        descriptionLabel.setOpaque(false); // Make the background transparent
        descriptionLabel.setBorder(new EmptyBorder(70, 20, 0, 20)); // Add margin
        add(descriptionLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setOpaque(false);

        JButton declineButton = new JButton("DECLINE");

        declineButton.addActionListener(e -> {
            // Call API to decline the alarm
            // firesignal.utils.AlarmActionApiService.declineAlarm(Integer.parseInt(id), Integer.parseInt(firefighterId));
            firesignal.utils.AlarmActionApiService.updateAcceptance(Integer.parseInt(id), Integer.parseInt(firefighterId), false);

            // Close the message frame regardless of the result
            frame.closeMessage();
        });

        declineButton.setBackground(Color.RED);
        declineButton.setFont(new Font("Arial", Font.BOLD, 20));
        declineButton.setPreferredSize(new Dimension(130, 50));
        buttonPanel.add(declineButton);

        JButton acceptButton = new JButton("ACCEPT");

        acceptButton.addActionListener(e -> {
            // Call API to accept the alarm
            //firesignal.utils.AlarmActionApiService.acceptAlarm(Integer.parseInt(id), Integer.parseInt(firefighterId));
            firesignal.utils.AlarmActionApiService.updateAcceptance(Integer.parseInt(id), Integer.parseInt(firefighterId), true);

            // Close the message frame
            frame.closeMessage();
        });

        acceptButton.setBackground(Color.GREEN);
        acceptButton.setFont(new Font("Arial", Font.BOLD, 20));
        acceptButton.setPreferredSize(new Dimension(130, 50));
        buttonPanel.add(acceptButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void setMessage(String message) {
        String[] parts = message.split(", ");
        id = parts[0].substring(parts[0].indexOf(":") + 5);
        firefighterId = parts[1].substring(parts[1].indexOf(":") + 2);
        String city = parts[2].substring(parts[2].indexOf(":") + 2);
        String street = parts[3].substring(parts[3].indexOf(":") + 2);
        String description = parts[4].substring(parts[4].indexOf(":") + 2);

        cityLabel.setText(city);
        streetLabel.setText(street);
        descriptionLabel.setText(description);
    }
}