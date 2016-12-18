package ru.spbstu.icc.kspt.zhuikov.quoridor.gui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class BarrierPanel extends JPanel {

    BarrierPanel() {

        Color color = new Color(130, 130, 130, 90);
        setLayout(null);

        setBorder(new LineBorder(Color.BLACK, 2));
        setLocation(410, 160);
        setSize(190, 200);
        setBackground(color);
        setVisible(true);

        JButton barrierButton = new JButton("Place barrier");
        barrierButton.setLocation(20, 40);
        barrierButton.setSize(140, 20);

        ButtonGroup group = new ButtonGroup();
        JRadioButton horizontal = new JRadioButton("Horizontal", true);
        horizontal.setSize(100, 20);
        horizontal.setLocation(40, 100);
        horizontal.setBackground(color);
        group.add(horizontal);

        JRadioButton vertical = new JRadioButton("Vertical", false);
        vertical.setSize(100, 20);
        vertical.setLocation(40, 130);
        vertical.setBackground(color);
        group.add(vertical);

        add(barrierButton);
        add(vertical);
        add(horizontal);
    }
}
