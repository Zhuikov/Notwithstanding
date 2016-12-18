package ru.spbstu.icc.kspt.zhuikov.quoridor.gui;

import ru.spbstu.icc.kspt.zhuikov.quoridor.Quoridor;
import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.FieldItemException;
import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.NoBarriersException;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.BarrierPosition;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel {

    static final Color bottomColor = Color.red;
    static final Color topColor = Color.blue;

    private MainFrame frame;
    private Quoridor game;
    private FieldPanel field;
    private BarrierPanel barrierPanel;
    private JLabel statusLabel;
    private JButton menuButton;

    GamePanel(MainFrame frame) {

        this.frame = frame;

        game = new Quoridor(2);
        try {
            game.placeBarrier(1, 3, BarrierPosition.HORIZONTAL);
            game.placeBarrier(5, 5, BarrierPosition.VERTICAL);
            game.placeBarrier(7, 15, BarrierPosition.HORIZONTAL);
        } catch (FieldItemException e) {
            e.printStackTrace();
        } catch (NoBarriersException e) {
            e.printStackTrace();
        }

        field = new FieldPanel(game);
        barrierPanel = new BarrierPanel();
        add(field);
        add(barrierPanel);

        statusLabel = new JLabel();
        statusLabel.setSize(500, 20);
        statusLabel.setLocation(25, 400);
        statusLabel.setFont(new Font("Arial", Font.ITALIC, 15));
        if (game.getCurrentPlayer().name().equals("TOP")) {
            statusLabel.setText("Blue player's turn");
        } else if (game.getCurrentPlayer().name().equals("BOTTOM")) {
            statusLabel.setText("Red player's turn");
        }
        add(statusLabel);

        menuButton = new JButton("Menu");
        menuButton.setLocation(440, 100);
        menuButton.setSize(140, 20);
        menuButton.addActionListener(new MenuListener());
        add(menuButton);

        setBackground(Color.green);
        setLayout(null);
        setVisible(true);
    }

    private class MenuListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            field.setVisible(false);
            barrierPanel.setVisible(false);
            frame.setContentPane(new MenuPanel(frame));
        }
    }

}
