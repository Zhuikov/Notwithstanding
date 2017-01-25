package ru.spbstu.icc.kspt.zhuikov.quoridor;


import ru.spbstu.icc.kspt.zhuikov.quoridor.items.*;

import java.util.Stack;

public class BotPlayer extends Player {

    private PlayerPosition position;
    private int barriersNumber = 10; // todo ...
    private int destinationRow;
    private HumanPlayer opponent;

    public BotPlayer(QuoridorField field, PlayerPosition position, Owner owner, HumanPlayer opponent) {
        this.field = field;
        this.position = position;
        this.destinationRow = position.destinationRow;
        this.opponent = opponent;
        marker = new Marker(position.initialVertical, position.initialHorizontal, owner);
    }

    public void makeMove() {

        double rand = Math.random();
        if (rand > 0.47) {
            placeBarrier();
        } else moveMarker();

    }

    private void placeBarrier() {

        Stack<Coordinates> topPath = field.getPathToRow(opponent.getCoordinates(), opponent.getPosition().destinationRow);
        Coordinates between = new Coordinates((topPath.peek().getVertical() + opponent.getCoordinates().getVertical()) / 2,
                (topPath.peek().getHorizontal() + opponent.getCoordinates().getHorizontal()) / 2);
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

    private void setBarrier(int vertical, int horizontal, BarrierPosition position) {

        field.setItem(new Barrier(vertical, horizontal, position));
        barriersNumber--;
    }

    private void moveMarker() {

        Stack<Coordinates> path = field.getPath(marker.getCoordinates(), new Coordinates(destinationRow, 0));
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

        if (field.getItem(path.peek().getVertical(), path.peek().getHorizontal()).getType() != ItemType.EMPTY) {
            path.pop();
        }
        setMarker(path.peek().getVertical(), path.peek().getHorizontal());
    }

}
