package ru.spbstu.icc.kspt.zhuikov.quoridor.gui;

import javax.swing.*;

public class MainFrame extends JFrame {

    MainFrame(String s) {
        super(s);
        setSize(640, 480);
        MenuPanel panel = new MenuPanel(this);
       // panel.create();
        this.add(panel);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainFrame("There is no fox");

            }
        });
    }
}
