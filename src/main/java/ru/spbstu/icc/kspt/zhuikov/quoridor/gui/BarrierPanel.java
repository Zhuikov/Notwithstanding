//package ru.spbstu.icc.kspt.zhuikov.quoridor.gui;
//
//import ru.spbstu.icc.kspt.zhuikov.quoridor.Quoridor;
//
//import javax.swing.*;
//import javax.swing.border.LineBorder;
//import java.awt.*;
//
//package ru.spbstu.icc.kspt.zhuikov.quoridor.gui;
//
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
//
//class MenuPanel extends JPanel {
//
//    private JButton startButton;
//    private JButton exitButton = new JButton("Exit");
//    private MainFrame frame;
//    private JLabel label = new JLabel("dddddd");
//    private Font font = new Font("Arial", Font.BOLD, 20);
//
//    MenuPanel(MainFrame frame) {
//
//        this.frame = frame;
//        setLayout(null);
//
//        startButton = new JButton("Start game");
//        startButton.setLocation(240, 180);
//        startButton.setSize(180, 30);
//        startButton.addActionListener(new StartListener());
////
////        exitButton.setLocation(240, 230);
////        exitButton.setSize(180, 30);
////        exitButton.addActionListener(new ExitListener());
//
//        label.setSize(100, 20);
//        label.setFont(font);
//        label.setLocation(300, 100);
//
//        add(startButton);
//        //add(exitButton);
//        add(label);
//
//    }
//
//    private class StartListener implements ActionListener {
//
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            frame.setContentPane(new GamePanel(frame));
//        }
//    }
//
//    private class ExitListener implements ActionListener {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            System.exit(0);
//        }
//    }
//}


