package ru.spbstu.icc.kspt.zhuikov.quoridor.gui;


import ru.spbstu.icc.kspt.zhuikov.quoridor.QuoridorCore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SettingsPanel extends JPanel {

    private final Image bg = new ImageIcon("pictures/gamePics/background_menu1.jpg").getImage();
    private MainFrame frame;
    private JButton backButton = new JButton("Back");
    private JSlider stepSlider = new JSlider(0, 40, QuoridorCore.getFoxTime());
    private JSlider frequencySlider = new JSlider(1, 20, QuoridorCore.getFoxFrequency());
    private JLabel stepLabel = new JLabel("");
    private JLabel frequencyLabel = new JLabel("");

    SettingsPanel(MainFrame frame) {

        this.frame = frame;
        setLayout(null);

        JLabel changeFoxTime = new JLabel("Fox spawn time (in steps from beginning): ");
        changeFoxTime.setLocation(130, 210);
        changeFoxTime.setSize(400, 20);
        changeFoxTime.setFont(new Font("Arial", Font.BOLD + Font.ITALIC, 15));

        stepSlider.setSize(300, 45);
        stepSlider.setFont(new Font("Arial",Font.BOLD + Font.ITALIC, 14));
        stepSlider.setBackground(new Color(210, 230, 250));
        stepSlider.setLocation(110, 240);
        stepSlider.setMajorTickSpacing(5);
        stepSlider.setPaintLabels(true);
        stepSlider.setPaintTicks(true);
        stepSlider.addChangeListener(e -> {
            stepLabel.setText("" + stepSlider.getValue());
            QuoridorCore.setFoxTime(stepSlider.getValue());
        });

        stepLabel = new JLabel("" + stepSlider.getValue());
        stepLabel.setSize(140, 45);
        stepLabel.setLocation(480, 242);
        stepLabel.setFont(new Font("Arial", Font.BOLD + Font.ITALIC, 16));

        JLabel changeFoxTurn = new JLabel("Fox move frequency (once in a set steps): ");
        changeFoxTurn.setLocation(130, 300);
        changeFoxTurn.setSize(400, 20);
        changeFoxTurn.setFont(new Font("Arial", Font.BOLD + Font.ITALIC, 15));

        frequencySlider.setSize(300, 45);
        frequencySlider.setFont(new Font("Arial",Font.BOLD + Font.ITALIC, 14));
        frequencySlider.setBackground(new Color(210, 230, 250));
        frequencySlider.setLocation(110, 330);
        frequencySlider.setMajorTickSpacing(2);
        frequencySlider.setPaintLabels(true);
        frequencySlider.setPaintTicks(true);
        frequencySlider.addChangeListener(e -> {
            frequencyLabel.setText("" + frequencySlider.getValue());
            QuoridorCore.setFoxFrequency(frequencySlider.getValue());
        });

        frequencyLabel = new JLabel("" + frequencySlider.getValue());
        frequencyLabel.setSize(140, 45);
        frequencyLabel.setLocation(480, 332);
        frequencyLabel.setFont(new Font("Arial", Font.BOLD + Font.ITALIC, 16));

        backButton.setLocation(200, 400);
        backButton.setSize(200, 30);
        backButton.addMouseListener(new BackListener());

        add(changeFoxTime);
        add(stepLabel);
        add(stepSlider);
        add(changeFoxTurn);
        add(frequencyLabel);
        add(frequencySlider);
        add(backButton);
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.drawImage(bg, 0, 0, null);
    }

    private class BackListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            frame.setContentPane(new MenuPanel(frame));
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            backButton.setText("a");
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

}
