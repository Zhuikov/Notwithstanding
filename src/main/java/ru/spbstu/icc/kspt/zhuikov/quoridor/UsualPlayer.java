package ru.spbstu.icc.kspt.zhuikov.quoridor;


import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.*;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Barrier;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.BarrierPosition;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.ItemType;

import java.util.ArrayList;
import java.util.List;

// обычный игрок, который хочет дойти до конца (не лиса)
// м.б. ботом или человеком
abstract class UsualPlayer extends Player {

    protected PlayerPosition position;
    protected boolean bot;
    protected int barriersNumber = 10;

    protected void setBarrier(int vertical, int horizontal, BarrierPosition position) {

        field.setItem(new Barrier(vertical, horizontal, position));
        barriersNumber--;
    }

    public boolean isBot() { return bot; }

    public PlayerPosition getPosition() {
        return position;
    }

    public int getBarriersNumber() { return barriersNumber; }

    public List<Coordinates> getPossibleMoves() {

        List<Coordinates> possibleMoves = new ArrayList<>();
        for (int i = this.marker.getCoordinates().getVertical() - 4; i <= this.marker.getCoordinates().getVertical() + 4; i+=2) {
            for (int j = this.marker.getCoordinates().getHorizontal() - 4; j <= this.marker.getCoordinates().getHorizontal() + 4; j+=2) {
                try {
                    checkMarkerPlace(i, j);
                    possibleMoves.add(new Coordinates(i, j));
                } catch (Exception e) {}
            }
        }

        return possibleMoves;
    }

    abstract public void makeMove();

    abstract public void moveMarker(int vertical, int horizontal) throws FieldItemException;

    abstract public void placeBarrier(int vertical, int horizontal, BarrierPosition position)
            throws FieldItemException, NoBarriersException;

    void checkMarkerPlace(int vertical, int horizontal) throws FieldItemException {

        try {
            field.getItem(vertical, horizontal);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new FieldBoundsException("impossible to place marker on " + vertical + " " + horizontal);
        }

        if (marker.getCoordinates().equals(new Coordinates(vertical, horizontal))) {
            throw new ImpossibleToSetItemException("impossible to move to the same cell");
        }

        if (field.getColor(vertical, horizontal) == CellColor.WHITE) {
            throw new ImpossibleToSetItemException("impossible to set marker on white cell");
        }

        if (field.getItem(vertical, horizontal).getType() != ItemType.EMPTY) {
            throw new CellIsNotEmptyException("cell is not empty");
        }

        if (Coordinates.pathBetween(marker.getCoordinates(), new Coordinates(vertical, horizontal)) > 2.1) {

            if (!jumpOverMarker(vertical, horizontal)) {
                throw new TooLongDistanceException("you can move just nearby cells");
            }
        }

        if (field.getItem((marker.getCoordinates().getVertical() + vertical) / 2,
                (marker.getCoordinates().getHorizontal() + horizontal) / 2).getType() == ItemType.BARRIER) {
            throw new ImpossibleToSetItemException("impossible to jump over the barrier");
        }
    }

    private boolean jumpOverMarker(int vertical, int horizontal) throws FieldItemException {

        // если "прыгают" прямо:
        if (Coordinates.pathBetween(marker.getCoordinates(), new Coordinates(vertical, horizontal)) < 4.01 &&
                Coordinates.pathBetween(marker.getCoordinates(), new Coordinates(vertical,horizontal)) > 3.99) {

            return jumpForward(vertical, horizontal);
        }

        // если "прыгают" по диагонали:
        if (Coordinates.pathBetween(marker.getCoordinates(), new Coordinates(vertical, horizontal)) < 2.83 &&
                Coordinates.pathBetween(marker.getCoordinates(), new Coordinates(vertical,horizontal)) > 2.81) {

            return jumpDiagonal(vertical, horizontal);
        }

        return false;
    }

    private boolean jumpForward(int vertical, int horizontal) throws FieldItemException {

        Coordinates midCoordinates = new Coordinates( (marker.getCoordinates().getVertical() + vertical) / 2,
                (marker.getCoordinates().getHorizontal() + horizontal) / 2);

        if (field.getItem(midCoordinates.getVertical(), midCoordinates.getHorizontal()).getType() == ItemType.MARKER) {

            if ( (field.getItem( (midCoordinates.getVertical() + marker.getCoordinates().getVertical()) / 2,
                    (midCoordinates.getHorizontal() + marker.getCoordinates().getHorizontal()) / 2).getType() == ItemType.BARRIER) ||
                    (field.getItem( (midCoordinates.getVertical() + vertical) / 2,
                            (midCoordinates.getHorizontal() + horizontal) / 2).getType() == ItemType.BARRIER) ) {

                throw new ImpossibleToSetItemException("impossible to set marker because of barrier");
            }

            return true;
        }

        return false;
    }

    private boolean jumpDiagonal(int vertical, int horizontal) throws FieldItemException {

        Coordinates opponentsMarker;

        if (field.getItem(marker.getCoordinates().getVertical(), horizontal).getType() == ItemType.MARKER) {
            opponentsMarker = new Coordinates(marker.getCoordinates().getVertical(), horizontal);
        } else if (field.getItem(vertical, marker.getCoordinates().getHorizontal()).getType() == ItemType.MARKER) {
            opponentsMarker = new Coordinates(vertical, marker.getCoordinates().getHorizontal());
        } else { return false; }

        if ( (field.getItem( (opponentsMarker.getVertical() + marker.getCoordinates().getVertical()) / 2,
                (opponentsMarker.getHorizontal() + marker.getCoordinates().getHorizontal()) / 2).getType() == ItemType.BARRIER)
                ||
                (field.getItem( (opponentsMarker.getVertical() + vertical) / 2,
                        (opponentsMarker.getHorizontal() + horizontal) / 2).getType() == ItemType.BARRIER)) {

            throw new ImpossibleToSetItemException("impossible to set marker because of barrier");
        }

        return true;
    }
}
