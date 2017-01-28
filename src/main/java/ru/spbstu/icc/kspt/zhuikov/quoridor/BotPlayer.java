package ru.spbstu.icc.kspt.zhuikov.quoridor;


import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.FieldItemException;
import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.NoBarriersException;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.*;

import java.util.Stack;

public class BotPlayer extends UsualPlayer {

    private int destinationRow;
    private UsualPlayer opponent;

    public BotPlayer(QuoridorField field, PlayerPosition position, UsualPlayer opponent) {
        bot = true;
        this.field = field;
        this.position = position;
        this.destinationRow = position.destinationRow;
        this.opponent = opponent;
        marker = new Marker(position.initialVertical, position.initialHorizontal, position.owner);
        field.setItem(marker);
    }

    @Override
    public void moveMarker(int vertical, int horizontal) throws FieldItemException {
//        makeMove();
    }

    @Override
    public void placeBarrier(int vertical, int horizontal, BarrierPosition position) throws FieldItemException, NoBarriersException {
//        makeMove();
    }

    public void makeMove() {

        double rand = Math.random();
        if (rand > 0.47) {
            placeBarrier();
        } else moveMarker();

    }

    private void placeBarrier() {

        Stack<Coordinates> opponentsPath = getPathToRow(opponent.getMarker().getCoordinates(), opponent.getPosition().destinationRow);
        Coordinates between = new Coordinates((opponentsPath.peek().getVertical() + opponent.getMarker().getCoordinates().getVertical()) / 2,
                (opponentsPath.peek().getHorizontal() + opponent.getMarker().getCoordinates().getHorizontal()) / 2);
        double rand = Math.random();
        try {
            if (rand < 0.5) {
                setBarrier(between.getVertical() % 2 == 0 ? between.getVertical() - 1 : between.getVertical(),
                        between.getHorizontal() % 2 == 0 ? between.getHorizontal() - 1 : between.getHorizontal(),
                        rand > 0.15 ? BarrierPosition.HORIZONTAL : BarrierPosition.VERTICAL);
            } else {
                setBarrier(between.getVertical() % 2 == 0 ? between.getVertical() + 1 : between.getVertical(),
                        between.getHorizontal() % 2 == 0 ? between.getHorizontal() + 1 : between.getHorizontal(),
                        rand > 0.65 ? BarrierPosition.HORIZONTAL : BarrierPosition.VERTICAL);
            }
        } catch (Exception e) {
            moveMarker();
        }

    }

    private void moveMarker() {

        Stack<Coordinates> path = getPathToRow(marker.getCoordinates(), position.destinationRow);

        if (field.getItem(path.peek().getVertical(), path.peek().getHorizontal()).getType() != ItemType.EMPTY) {
            path.pop();
        }
        setMarker(path.peek().getVertical(), path.peek().getHorizontal());
    }

    /**
     * Возвращает кратчайший путь из точки на поле до заданного ряда.
     * Длина пути всегда больше либо равна двум.
     * @param start - координаты точки
     * @param destinationRow - ряд, до которого ищется путь
     * @return последовательность координат - кратчайший путь
     */
    private Stack<Coordinates> getPathToRow(Coordinates start, int destinationRow) {

        Stack<Coordinates> path = field.getPath(start, new Coordinates(destinationRow, 0));
        int min = 1000000;
        for (int i = 0; i <= field.getSize(); i+=2) {
            if (field.getItem(destinationRow, i).getType() == ItemType.EMPTY) {
                Stack<Coordinates> temp = field.getPath(marker.getCoordinates(), new Coordinates(destinationRow, i));
                if (temp.size() < min && temp.size() != 0) {
                    path = temp;
                    min = path.size();
                }
            }
        }

        return path;
    }
}
