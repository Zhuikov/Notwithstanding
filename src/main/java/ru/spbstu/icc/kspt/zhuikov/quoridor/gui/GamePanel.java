package ru.spbstu.icc.kspt.zhuikov.quoridor.gui;

import ru.spbstu.icc.kspt.zhuikov.quoridor.Coordinates;
import ru.spbstu.icc.kspt.zhuikov.quoridor.Quoridor;
import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.FieldItemException;
import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.NoBarriersException;
import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.NoWinnerException;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.BarrierPosition;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.ItemType;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Owner;
import ru.spbstu.icc.kspt.zhuikov.quoridor.returningClasses.Cell;
import ru.spbstu.icc.kspt.zhuikov.quoridor.returningClasses.Field;
import ru.spbstu.icc.kspt.zhuikov.quoridor.returningClasses.Player;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

class GamePanel extends JPanel {

    private final Color bottomColor = Color.red;
    private final Color topColor = Color.blue;

    private MainFrame frame;
    private Quoridor game;
    private FieldPanel fieldPanel;
    private BarrierPanel barrierPanel;
    private JLabel statusLabel;
    private FoxPanel foxPanel;
    private Image bg = new ImageIcon("pictures/gamePics/background_game.jpg").getImage();

    GamePanel(MainFrame frame, boolean bots) {

        this.frame = frame;
        game = new Quoridor(2, bots);

        statusLabel = new JLabel("fff");
        statusLabel.setSize(470, 25);
        statusLabel.setLocation(45, 395);
        statusLabel.setBorder(new LineBorder(Color.BLACK, 2));
        statusLabel.setFont(new Font("Arial", Font.ITALIC + Font.BOLD, 17));

        updateStatusLabel();
        add(statusLabel);

        JButton menuButton = new JButton("Menu");
        menuButton.setLocation(435, 20);
        menuButton.setSize(140, 20);
        menuButton.addActionListener(new MenuListener());
        add(menuButton);

        fieldPanel = new FieldPanel();
        barrierPanel = new BarrierPanel();
        foxPanel = new FoxPanel();

        add(foxPanel);
        add(fieldPanel);
        add(barrierPanel);

        setBackground(new Color(200, 200, 200));
        setLayout(null);
        setVisible(true);

    }

    private void updateStatusLabel() {
        if (game.getCurrentPlayer().name().equals("TOP")) {
            statusLabel.setText("Blue player's turn");
        } else if (game.getCurrentPlayer().name().equals("BOTTOM")) {
            statusLabel.setText("Red player's turn");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.drawImage(bg, 0, 0, null);
    }

    private class MenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            fieldPanel.setVisible(false);
            barrierPanel.setVisible(false);
            frame.setContentPane(new MenuPanel(frame));
        }
    }

    private class FieldPanel extends JPanel {

        private Field field;
        private final int cellSize = 30;
        private final int spaceSize = 10;
        private boolean pickedMarker = false;
        private boolean pickedBarrier = false;
        private BarrierPosition barrierPosition;
        private Coordinates pickedMarkerCoordinates;
        private FieldMouseListener fieldMouseListener;

        FieldPanel() {

            this.field = game.getField();

            setLayout(null);
            setBackground(Color.white);
            setLocation(25, 30);
            setSize(350, 350);
            setVisible(true);

            fieldMouseListener = new FieldMouseListener();
            addMouseListener(fieldMouseListener);
        }

        void pickBarrier(BarrierPosition position) {
            pickedMarker = false;
            this.repaint();
            pickedBarrier = true;
            barrierPosition = position;
            statusLabel.setText("Place " + position + " barrier...");
        }

        private void unpickBarrier(int x, int y) {

            Coordinates coordinates = roundToOdd(x, y);
            try {
                game.placeBarrier(coordinates.getVertical(), coordinates.getHorizontal(), barrierPosition);
                pickedBarrier = false;
                updateStatusLabel();
                barrierPanel.updateText();
            } catch (FieldItemException | NoBarriersException e)  {
                statusLabel.setText(e.getMessage());
                pickedBarrier = false;
                Timer timer = new Timer(1500, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        updateStatusLabel();
                    }
                });
                timer.setRepeats(false);
                timer.start();
              //  barrierPanel.updateText();
            }
        }

        private Coordinates roundToOdd(int x, int y) {

            Coordinates initCoordinates = convertCoordinates(x, y);
            int newX = x;
            int newY = y;
            if (initCoordinates.getVertical() % 2 == 0) {
                int leftBound  = (initCoordinates.getVertical() / 2) * (cellSize + spaceSize) - spaceSize / 2; // в пикселах
                int rightBound = (initCoordinates.getVertical() / 2 + 1) * (cellSize + spaceSize) - spaceSize / 2;
                if (Math.abs(y - leftBound) < Math.abs(y - rightBound)) {
                    newY = leftBound;
                } else {
                    newY = rightBound;
                }
            }

            if (initCoordinates.getHorizontal() % 2 == 0) {
                int leftBound  = (initCoordinates.getHorizontal() / 2) * (cellSize + spaceSize) - spaceSize / 2; // в пикселах
                int rightBound = (initCoordinates.getHorizontal() / 2 + 1) * (cellSize + spaceSize) - spaceSize / 2;
                if (Math.abs(x - leftBound) < Math.abs(x - rightBound)) {
                    newX = leftBound;
                } else {
                    newX = rightBound;
                }
            }

            if (newX > this.getSize().width) newX = this.getSize().width - cellSize - spaceSize / 2;
            if (newX < 0) newX = cellSize + spaceSize / 2;
            if (newY > this.getSize().height) newY = this.getSize().height - cellSize - spaceSize / 2;
            if (newY < 0) newY = cellSize + spaceSize / 2;

            return convertCoordinates(newX, newY);
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);

            g.setColor(new Color(170, 170, 170));

            for (int i = 0; i <= field.getSize(); i++ ) {
//            g.drawLine(i * (cellSize + spaceSize), 0, i * (cellSize + spaceSize), 350);
//            g.drawLine(i * (cellSize + spaceSize) - spaceSize, 0, i * (cellSize + spaceSize) - spaceSize, 350);
//
//            g.drawLine(0, i * (cellSize + spaceSize), 350, i * (cellSize + spaceSize));
//            g.drawLine(0, i * (cellSize + spaceSize) - spaceSize, 350, i * (cellSize + spaceSize) - spaceSize);

                for (int j = 0; j <= field.getSize(); j++) {
                    g.fillRect(i * (cellSize + spaceSize), j * (cellSize + spaceSize), cellSize, cellSize);
                }
            }

            this.field = game.getField();

            for (int i = 0; i < field.getRealSize(); i++) {
                for (int j = 0; j < field.getRealSize(); j++) {
                    Cell cell = field.getCell(i, j);
                    switch (cell.getType()) {
                        case MARKER: {
                            if (cell.getOwner() == Owner.TOP) {
                                g.setColor(topColor);
                                g.fillOval(j * (cellSize + spaceSize) / 2 + cellSize / 8,
                                        i * (cellSize + spaceSize) / 2 + cellSize / 8,
                                        24, 24);
                            } else if (cell.getOwner() == Owner.BOTTOM) {
                                g.setColor(bottomColor);
                                g.fillOval(j * (cellSize + spaceSize) / 2 + cellSize / 8,
                                        i * (cellSize + spaceSize) / 2 + cellSize / 8,
                                        24, 24);
                            } else if (cell.getOwner() == Owner.FOX) {
                                g.setColor(new Color(255, 77, 0));
                                g.fillPolygon(new int [] {j * (cellSize + spaceSize) / 2 + 3, j * (cellSize + spaceSize) / 2 + cellSize / 2, j * (cellSize + spaceSize) / 2 + cellSize - 3 },
                                        new int [] {i * (cellSize + spaceSize) / 2 + 3, i * (cellSize + spaceSize) / 2 + cellSize - 3, i * (cellSize + spaceSize) / 2 + 3}, 3);
                            }
                            break;
                        }
                        case BARRIER: {
                            g.setColor(new Color(140, 90, 90));
                            if (i % 2 == 1 && j % 2 == 1)
                                g.fillRect((j + 1) * (cellSize + spaceSize) / 2 - spaceSize,
                                        (i + 1) * (cellSize + spaceSize) / 2 - spaceSize, spaceSize, spaceSize);
                            if (i % 2 == 1 && j % 2 == 0)
                                g.fillRect(j * (cellSize + spaceSize) / 2,
                                        (i + 1) * (cellSize + spaceSize) / 2 - spaceSize, cellSize, spaceSize);
                            if (i % 2 == 0 && j % 2 == 1)
                                g.fillRect((j + 1) * (cellSize + spaceSize) / 2 - spaceSize,
                                        i * (cellSize + spaceSize) / 2, spaceSize, cellSize);
                            break;
                        }
                    }
                }
            }

            foxPanel.updateLabel();

            if (pickedMarker) {
                g.setColor(Color.BLACK);
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(3));
                g2.drawRect(pickedMarkerCoordinates.getHorizontal() * (cellSize + spaceSize) / 2,
                        pickedMarkerCoordinates.getVertical() * (cellSize + spaceSize) / 2, cellSize, cellSize);
                for (Coordinates c : game.getPossibleMoves()) {
                    g.setColor(Color.green);
                    g.fillRect(c.getHorizontal() * (cellSize + spaceSize) / 2,
                            c.getVertical() * (cellSize + spaceSize) / 2, cellSize, cellSize);
                }
            }

            try {
                if (game.isEnd()) {
                    this.removeMouseListener(fieldMouseListener);
                    barrierPanel.removeListeners();
                    if (game.getWinner().name().equals("TOP")) {
                        statusLabel.setText("Blue player won!");
                    } else if (game.getWinner().name().equals("BOTTOM")) {
                        statusLabel.setText("Red player won!");
                    } else if (game.getWinner().name().equals("FOX")) {
                        statusLabel.setText("Fox won!");
                    }
                }
            } catch (NoWinnerException e) {}

        }

        /**
         * "Переводит" из координат курсора в координаты поля игры
         * @return координаты клетки поля, на которую кликнули
         */
        private Coordinates convertCoordinates(int x, int y) {

            int fieldVertical;      // координаты на игровом поле (0 - realSize)
            int fieldHorizontal;

            if (y % (cellSize + spaceSize) > cellSize) {
                fieldVertical = y / (cellSize + spaceSize) * 2 + 1;
            } else {
                fieldVertical = y / (cellSize + spaceSize) * 2;
            }

            if (x % (cellSize + spaceSize) > cellSize) {
                fieldHorizontal = x / (cellSize + spaceSize) * 2 + 1;
            } else {
                fieldHorizontal = x / (cellSize + spaceSize) * 2;
            }

            return new Coordinates(fieldVertical, fieldHorizontal);
        }

        private boolean onCell(int x, int y) {

            Coordinates fieldCoordinates = convertCoordinates(x, y);

            return fieldCoordinates.getVertical() % 2 == 0 && fieldCoordinates.getHorizontal() % 2 == 0;
        }

        private void pickMarker(Cell marker) {

            pickedMarker = marker.getOwner().name().equals(game.getCurrentPlayer().name());
            pickedMarkerCoordinates = new Coordinates(marker.getVertical(), marker.getHorizontal());
        }

        private void unpickMarker(Coordinates newCoordinates) {

            pickedMarker = false;
            try {
                game.moveMarker(newCoordinates.getVertical(), newCoordinates.getHorizontal());
                updateStatusLabel();
                barrierPanel.updateText();
            } catch (FieldItemException | NoBarriersException e ) {
                statusLabel.setText(e.getMessage());
                Timer timer = new Timer(1500, e1 -> updateStatusLabel());
                timer.setRepeats(false);
                timer.start();
            }
        }

        private class FieldMouseListener implements MouseListener {

            public void mouseClicked(MouseEvent e) {
                if (onCell(e.getX(), e.getY())) {
                    Coordinates fieldCoordinates = convertCoordinates(e.getX(), e.getY());
                    Cell cell = field.getCell(fieldCoordinates.getVertical(), fieldCoordinates.getHorizontal());
                    if (cell.getType() == ItemType.MARKER) {
                        pickMarker(cell);
                    } else if (pickedMarker) {
                        unpickMarker(fieldCoordinates);
                    }
                } else if (pickedBarrier) {
                    unpickBarrier(e.getX(), e.getY());
                }
                repaint();
            }

            public void mousePressed(MouseEvent e) {

            }

            public void mouseReleased(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {

            }
        }
    }

    private class BarrierPanel extends JPanel {

        private JLabel bottomBarriersNumber = new JLabel();
        private JLabel topBarriersNumber = new JLabel();
        private JButton barrierButton = new JButton("Place barrier");
        private JButton cancelButton = new JButton("Cancel");
        private BarrierListener barrierListener;
        private CancelBarrierListener cancelBarrierListener;
        private JRadioButton horizontal = new JRadioButton("Horizontal", true);
        private JRadioButton vertical = new JRadioButton("Vertical", false);

        BarrierPanel() {

            Color color = new Color(190, 140, 140);
            setLayout(null);

            setBorder(new LineBorder(Color.BLACK, 2));
            setLocation(410, 180);
            setSize(190, 200);
            setBackground(color);
            setVisible(true);

            bottomBarriersNumber.setLocation(15, 10);
            bottomBarriersNumber.setSize(185, 20);
            bottomBarriersNumber.setFont(new Font("Arial", Font.ITALIC + Font.BOLD, 14));
            topBarriersNumber.setLocation(10, 30);
            topBarriersNumber.setSize(185, 20);
            topBarriersNumber.setFont(new Font("Arial", Font.BOLD + Font.ITALIC, 14));

            updateText();

            barrierButton.setLocation(25, 70);
            barrierButton.setSize(140, 20);
            barrierListener = new BarrierListener();
            barrierButton.addMouseListener(barrierListener);

            cancelButton.setLocation(25, 160);
            cancelButton.setSize(140, 20);
            cancelBarrierListener = new CancelBarrierListener();
            cancelButton.addMouseListener(cancelBarrierListener);

            ButtonGroup group = new ButtonGroup();
            horizontal.setSize(100, 20);
            horizontal.setLocation(45, 100);
            horizontal.setBackground(color);
            group.add(horizontal);

            vertical.setSize(100, 20);
            vertical.setLocation(45, 130);
            vertical.setBackground(color);
            group.add(vertical);

            add(bottomBarriersNumber);
            add(topBarriersNumber);
            add(barrierButton);
            add(cancelButton);
            add(vertical);
            add(horizontal);

        }

        void removeListeners() {
            barrierButton.removeMouseListener(barrierListener);
            cancelButton.removeMouseListener(cancelBarrierListener);
        }

        void updateText() {
            bottomBarriersNumber.setText("Red has " + game.getPlayerInformation(Player.BOTTOM).getBarrierNumber() + " barriers");
            topBarriersNumber.setText("Blue has " + game.getPlayerInformation(Player.TOP).getBarrierNumber() + " barriers");
        }

        private class BarrierListener implements MouseListener {

            @Override
            public void mouseClicked(MouseEvent e) {
                fieldPanel.pickBarrier(horizontal.isSelected() ? BarrierPosition.HORIZONTAL : BarrierPosition.VERTICAL);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        }

        private class CancelBarrierListener implements MouseListener {
            @Override
            public void mouseClicked(MouseEvent e) {
                fieldPanel.pickedBarrier = false;
                updateStatusLabel();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        }
    }


    class FoxPanel extends JPanel {

        private JLabel foxAppearingLabel;
        private JLabel foxFrequencyLabel;

        FoxPanel() {

            setLayout(null);
            setLocation(410, 60);
            setSize(190, 100);
            setBorder(new LineBorder(Color.BLACK, 2));
            setBackground(new Color(190, 140, 140));

            foxAppearingLabel = new JLabel();
            foxAppearingLabel.setSize(180, 20);
            foxAppearingLabel.setLocation(7, 5);
            foxAppearingLabel.setFont(new Font("Arial", Font.ITALIC + Font.BOLD, 14));

            JLabel foxFrequencyTextLabel = new JLabel("The Fox moves once ");
            foxFrequencyTextLabel.setSize(180, 20);
            foxFrequencyTextLabel.setLocation(7, 40);
            foxFrequencyTextLabel.setFont(new Font("Arial", Font.ITALIC + Font.BOLD, 14));

            foxFrequencyLabel = new JLabel("in " + Quoridor.getFoxFrequency() + " steps");
            foxFrequencyLabel.setSize(100, 20);
            foxFrequencyLabel.setLocation(100, 70);
            foxFrequencyLabel.setFont(new Font("Arial", Font.ITALIC + Font.BOLD, 14));

            add(foxAppearingLabel);
            add(foxFrequencyTextLabel);
            add(foxFrequencyLabel);

        }

        void updateLabel() {
            if (Quoridor.getFoxTime() - game.getStep() > 0) {
                foxAppearingLabel.setText("The Fox appears " + (Quoridor.getFoxTime() - game.getStep()));
            } else {
                foxAppearingLabel.setLocation(26, 5);
                foxAppearingLabel.setText("The Fox is here!");
            }
        }
    }
}
