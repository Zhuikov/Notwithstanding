//package ru.spbstu.icc.kspt.zhuikov.quoridor.gui;
//
//import ru.spbstu.icc.kspt.zhuikov.quoridor.Coordinates;
//import ru.spbstu.icc.kspt.zhuikov.quoridor.Quoridor;
//import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.FieldItemException;
//import ru.spbstu.icc.kspt.zhuikov.quoridor.items.ItemType;
//import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Owner;
//import ru.spbstu.icc.kspt.zhuikov.quoridor.returningClasses.Cell;
//import ru.spbstu.icc.kspt.zhuikov.quoridor.returningClasses.Field;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//
//public class FieldPanel extends JPanel {
//
//    private Field field;
//    private final Quoridor game;
//    private final int cellSize = 30;
//    private final int spaceSize = 10;
//    private JLabel debug;
//    private JLabel statusLabel;
//    private boolean pickedMarker = false;
//    private Coordinates pickedMarkerCoordinates;
//
//    FieldPanel(Quoridor quoridorGame, JLabel statusLabel) {
//
//        this.field = quoridorGame.getField();
//        this.game = quoridorGame;
//        this.statusLabel = statusLabel;
//
//        setLayout(null);
//        setBackground(Color.white);
//        setLocation(25, 30);
//        setSize(350, 350);
//        setVisible(true);
//
//        debug = new JLabel("start");
//        debug.setLocation(200, 100);
//        debug.setSize(90, 30);
//        debug.setFont(new Font("Arial", Font.BOLD, 20));
//
//        addMouseListener(new FieldMouseListener());
//        add(debug);
//
//    }
//
//    @Override
//    public void paint(Graphics g) {
//        super.paint(g);
//
//        g.setColor(new Color(150, 150, 150));
//
//        for (int i = 0; i <= field.getSize(); i++ ) {
////            g.drawLine(i * (cellSize + spaceSize), 0, i * (cellSize + spaceSize), 350);
////            g.drawLine(i * (cellSize + spaceSize) - spaceSize, 0, i * (cellSize + spaceSize) - spaceSize, 350);
////
////            g.drawLine(0, i * (cellSize + spaceSize), 350, i * (cellSize + spaceSize));
////            g.drawLine(0, i * (cellSize + spaceSize) - spaceSize, 350, i * (cellSize + spaceSize) - spaceSize);
//
//            for (int j = 0; j <= field.getSize(); j++) {
//                g.fillRect(i * (cellSize + spaceSize), j * (cellSize + spaceSize), cellSize, cellSize);
//            }
//        }
//
//        this.field = game.getField();
//
//        for (int i = 0; i < field.getRealSize(); i++) {
//            for (int j = 0; j < field.getRealSize(); j++) {
//                Cell cell = field.getCell(i, j);
//                switch (cell.getType()) {
//                    case MARKER: {
//                        if (cell.getOwner() == Owner.TOP) {
//                            g.setColor(GamePanel.topColor);
//                            g.fillOval(j * (cellSize + spaceSize) / 2 + cellSize / 8,
//                                    i * (cellSize + spaceSize) / 2 + cellSize / 8,
//                                    24, 24);
//                        } else if (cell.getOwner() == Owner.BOTTOM) {
//                            g.setColor(GamePanel.bottomColor);
//                            g.fillOval(j * (cellSize + spaceSize) / 2 + cellSize / 8,
//                                    i * (cellSize + spaceSize) / 2 + cellSize / 8,
//                                    24, 24);
//                        }
//                        break;
//                    }
//                    case BARRIER: {
//                        g.setColor(new Color(90, 10, 70));
//                        if (i % 2 == 1 && j % 2 == 1)
//                            g.fillRect((j + 1) * (cellSize + spaceSize) / 2 - spaceSize,
//                                    (i + 1) * (cellSize + spaceSize) / 2 - spaceSize, spaceSize, spaceSize);
//                        if (i % 2 == 1 && j % 2 == 0)
//                            g.fillRect(j * (cellSize + spaceSize) / 2,
//                                    (i + 1) * (cellSize + spaceSize) / 2 - spaceSize, cellSize, spaceSize);
//                        if (i % 2 == 0 && j % 2 == 1)
//                            g.fillRect((j + 1) * (cellSize + spaceSize) / 2 - spaceSize,
//                                    i * (cellSize + spaceSize) / 2, spaceSize, cellSize);
//                        break;
//                    }
//                }
//            }
//        }
//
//        if (pickedMarker) {
//            g.setColor(Color.BLACK);
//            Graphics2D g2 = (Graphics2D) g;
//            g2.setStroke(new BasicStroke(3));
//            g2.drawRect(pickedMarkerCoordinates.getHorizontal() * (cellSize + spaceSize) / 2,
//                    pickedMarkerCoordinates.getVertical() * (cellSize + spaceSize) / 2, cellSize, cellSize);
//        }
//    }
//
//    /**
//     * "Переводит" из координат курсора на панели в координаты поля игры
//     * @return координаты клетки поля, на которую кликнули
//     */
//    private Coordinates convertCoordinates(int x, int y) {
//
//        int fieldVertical;      // координаты на игровом поле (0 - realSize)
//        int fieldHorizontal;
//
//        if (x % (cellSize + spaceSize) > cellSize) {
//            fieldVertical = y / (cellSize + spaceSize) * 2 + 1;
//        } else {
//            fieldVertical = y / (cellSize + spaceSize) * 2;
//        }
//
//        if (y % (cellSize + spaceSize) > cellSize) {
//            fieldHorizontal = x / (cellSize + spaceSize) * 2 + 1;
//        } else {
//            fieldHorizontal = x / (cellSize + spaceSize) * 2;
//        }
//
//        return new Coordinates(fieldVertical, fieldHorizontal);
//    }
//
//    private boolean onCell(int x, int y) {
//
//        Coordinates fieldCoordinates = convertCoordinates(x, y);
//
//        return fieldCoordinates.getVertical() % 2 == 0 && fieldCoordinates.getHorizontal() % 2 == 0;
//    }
//
//    private void pickMarker(Cell marker) {
//
//        pickedMarker = marker.getOwner().name().equals(game.getCurrentPlayer().name());
//        pickedMarkerCoordinates = new Coordinates(marker.getVertical(), marker.getHorizontal());
//    }
//
//    private void unpickMarker(int x, int y) {
//
//        pickedMarker = false;
//        Coordinates newCoordinates = convertCoordinates(x, y);
//        try {
//            game.moveMarker(newCoordinates.getVertical(), newCoordinates.getHorizontal());
//            statusLabel.setText();
//        } catch (FieldItemException e) {
//            final String temp = statusLabel.getText();
//            statusLabel.setText(e.getMessage());
//            Timer timer = new Timer(1500, new ActionListener() {
//                public void actionPerformed(ActionEvent e) {
//                    statusLabel.setText(temp);
//                }
//            });
//            timer.setRepeats(false);
//            timer.start();
//        }
//    }
//
//    private class FieldMouseListener implements MouseListener {
//
//        public void mouseClicked(MouseEvent e) {
//
//            if (onCell(e.getX(), e.getY())) {
//                Coordinates fieldCoordinates = convertCoordinates(e.getX(), e.getY());
//                Cell cell = field.getCell(fieldCoordinates.getVertical(), fieldCoordinates.getHorizontal());
//                if (cell.getType() == ItemType.MARKER) {
//                    pickMarker(cell);
//                } else if (pickedMarker) {
//                    unpickMarker(e.getX(), e.getY());
//                }
//                debug.setText("IN");
//            }
//            repaint();
//        }
//
//        public void mousePressed(MouseEvent e) {
//
//        }
//
//        public void mouseReleased(MouseEvent e) {
//
//        }
//
//        public void mouseEntered(MouseEvent e) {
//
//        }
//
//        public void mouseExited(MouseEvent e) {
//
//        }
//    }
//}
