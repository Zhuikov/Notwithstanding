package ru.spbstu.icc.kspt.zhuikov.quoridor.gui;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuPanel extends JPanel {

    private JButton button;
    private MainFrame frame;
    private JLabel label = new JLabel("dddddd");
    private Font font = new Font("Arial", Font.BOLD, 20);

    MenuPanel(MainFrame frame) {
        super();

        this.frame = frame;

        setLayout(null);
        button = new JButton("ffff");
        button.setLocation(100, 50);
        button.setSize(180, 40);

        button.addMouseListener(new MyListener());
        this.add(button);

        label.setSize(100, 20);
        label.setFont(font);
        label.setLocation(300, 100);
        this.add(label);

    }

    public void paint(Graphics g) {
        super.paint(g);
    }

    private class MyListener implements MouseListener {

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseClicked(MouseEvent e) {
         //   button.setVisible(false);
            frame.setContentPane(new GamePanel(frame));
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mousePressed(MouseEvent e) {
            button.setText("Pressed");
        }

        public void mouseReleased(MouseEvent e) {
            button.setText("Realeased");
        }
    }
}

