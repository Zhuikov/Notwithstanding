package ru.spbstu.icc.kspt.zhuikov.quoridor.gui;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuPanel extends JPanel {

    private JButton startOnePlayer = new JButton("One Player");
    private JButton startTwoPlayer = new JButton("Two Players");
    private JButton settingsButton = new JButton("Settings");
    private JButton exitButton = new JButton("Exit");
    private MainFrame frame;
    private final Image bg = new ImageIcon("pictures/gamePics/background_menu1.jpg").getImage();

    MenuPanel(MainFrame frame) {

        this.frame = frame;
        setLayout(null);

        startOnePlayer.setLocation(225, 220);
        startOnePlayer.setSize(200, 30);
        startOnePlayer.addMouseListener(new StartListener(true));

        startTwoPlayer.setLocation(225, 270);
        startTwoPlayer.setSize(200, 30);
        startTwoPlayer.addMouseListener(new StartListener(false));

        settingsButton.setLocation(225, 320);
        settingsButton.setSize(200, 30);
        settingsButton.addMouseListener(new SettingsListener());


        exitButton.setLocation(225, 370);
        exitButton.setSize(200, 30);
        exitButton.addActionListener(new ExitListener());

        add(startOnePlayer);
        add(startTwoPlayer);
        add(settingsButton);
        add(exitButton);
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.drawImage(bg, 0, 0, null);
    }

    private class StartListener implements MouseListener {

        private boolean bots = false;
        StartListener(boolean bots) {
            this.bots = bots;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            frame.setContentPane(new GamePanel(frame, bots));
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            startOnePlayer.setText("d"); // ??????????????????????
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    private class SettingsListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            frame.setContentPane(new SettingsPanel(frame));
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            settingsButton.setText("d");
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    private class ExitListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
}

