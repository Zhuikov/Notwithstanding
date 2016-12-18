package ru.spbstu.icc.kspt.zhuikov.quoridor.gui;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuPanel extends JPanel {

    private JButton startButton = new JButton("Start game");
    private JButton exitButton = new JButton("Exit");
    private MainFrame frame;
    private Image bg = new ImageIcon("pictures/gamePics/background_menu1.jpg").getImage();

    MenuPanel(MainFrame frame) {

        this.frame = frame;
        setLayout(null);

        startButton.setLocation(225, 220);
        startButton.setSize(200, 30);
        startButton.addMouseListener(new MyListener());

        exitButton.setLocation(225, 270);
        exitButton.setSize(200, 30);
        exitButton.addActionListener(new ExitListener());

        add(startButton);
        add(exitButton);

    }

    public void paint(Graphics g) {
        super.paint(g);
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.drawImage(bg, 0, 0, null);
    }

    private class MyListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            frame.setContentPane(new GamePanel(frame));
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            startButton.setText("d"); // ?????????????????????? (без этого не работает!)
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

