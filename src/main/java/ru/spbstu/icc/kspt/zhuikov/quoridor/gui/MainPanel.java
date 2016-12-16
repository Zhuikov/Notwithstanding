package ru.spbstu.icc.kspt.zhuikov.quoridor.gui;


import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {

    MainPanel() {
        super();
    }

    public void paint(Graphics g) {
        super.paint(g);
        Font font = new Font("Arial", Font.BOLD, 24);
        setFont(font);

        JLabel label = new JLabel("dddddddddddddddddddd");
        label.setFont(font);
        label.setVisible(true);

        g.drawRect(50, 50, 100, 100);

    }
}
