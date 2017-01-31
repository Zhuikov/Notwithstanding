package ru.spbstu.icc.kspt.zhuikov.quoridor.player;


import ru.spbstu.icc.kspt.zhuikov.quoridor.QuoridorField;
import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.FieldItemException;
import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.NoBarriersException;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.*;

import java.util.Stack;

public class BotPlayer extends UsualPlayer {

    private final UsualPlayer opponent;

    public BotPlayer(QuoridorField field, PlayerPosition position, UsualPlayer opponent) {
        bot = true;
        this.opponent = opponent;
        this.field = field;
        this.position = position;
        marker = new Marker(position.getInitialVertical(), position.getInitialHorizontal(), position.getOwner());
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

    @Override
    public void makeMove() throws NoBarriersException {

        double rand = Math.random();
        if (rand > 0.47) {
            placeBarrier();
        } else moveMarker();

    }

    private void placeBarrier() throws NoBarriersException {

        if (barriersNumber == 0) {
            throw new NoBarriersException("you have no barriers", this.position);
        }

        Stack<Coordinates> opponentsPath = getPathToRow(opponent.getMarker().getCoordinates(), opponent.getPosition().getDestinationRow());
        Coordinates between = new Coordinates((opponentsPath.peek().getVertical() + opponent.getMarker().getCoordinates().getVertical()) / 2,
                (opponentsPath.peek().getHorizontal() + opponent.getMarker().getCoordinates().getHorizontal()) / 2);

        double rand = Math.random();

        try {
            if (rand < 0.5) {
                int vertical = between.getVertical() % 2 == 0 ? between.getVertical() - 1 : between.getVertical();
                int horizontal = between.getHorizontal() % 2 == 0 ? between.getHorizontal() - 1 : between.getHorizontal();
                BarrierPosition barrierPosition = rand > 0.15 ? BarrierPosition.HORIZONTAL : BarrierPosition.VERTICAL;
                checkBarrierPlace(vertical, horizontal, barrierPosition);
                setBarrier(vertical, horizontal, barrierPosition);

            } else {
                int vertical = between.getVertical() % 2 == 0 ? between.getVertical() + 1 : between.getVertical();
                int horizontal = between.getHorizontal() % 2 == 0 ? between.getHorizontal() + 1 : between.getHorizontal();
                BarrierPosition barrierPosition = rand > 0.65 ? BarrierPosition.HORIZONTAL : BarrierPosition.VERTICAL;
                checkBarrierPlace(vertical, horizontal, barrierPosition);
                setBarrier(vertical, horizontal, barrierPosition);
            }

        } catch (Exception e) {
            moveMarker();
        }

    }

    private void moveMarker() {

        Stack<Coordinates> path = getPathToRow(marker.getCoordinates(), position.getDestinationRow());

        if (field.getItem(path.peek().getVertical(), path.peek().getHorizontal()).getType() != ItemType.EMPTY) {
            path.pop();
        }
        setMarker(path.peek().getVertical(), path.peek().getHorizontal());
    }

}