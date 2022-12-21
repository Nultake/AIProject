package Game.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame {
    public MainFrame() {
        setBounds(500, 200, 500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("AI Project");

        JPanel panel = new JPanel();
        setContentPane(panel);
        panel.setLayout(null);

        JButton startGameButton = new JButton("Start Game");
        startGameButton.setBounds(150, 200, 200, 100);

        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new SettingsFrame();
            }
        });

        panel.add(startGameButton);

        setVisible(true);
    }
}
