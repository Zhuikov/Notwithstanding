package ru.spbstu.icc.kspt.zhuikov.quoridor;


import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.*;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.*;

import java.util.ArrayList;
import java.util.List;


// todo разбить бы весь этот огромный класс. Мб сделать отдельный класс с логикой
public enum QuoridorPlayer {

    TOP(0, 8, Owner.TOP),
    BOTTOM(16, 8, Owner.BOTTOM),
    RIGHT(8, 16, Owner.RIGHT),
    LEFT(8, 0, Owner.LEFT);

    private final int initialVertical;
    private final int initialHorizontal;
    private final Owner owner;

    private Marker marker;
    private int barriersNumber = 10;
    private List<Barrier> barriers = new ArrayList<Barrier>();
    private QuoridorField field;
    private boolean isActive = false;

    QuoridorPlayer(int initialVertical, int initialHorizontal, Owner owner) {
        this.initialVertical = initialVertical;
        this.initialHorizontal = initialHorizontal;
        this.owner = owner;
    }

    public void createPlayer(QuoridorField field) {          //TODO смущает, что чтобы создать игрока ему нужно передать поле
        this.field = field;
        this.isActive = true;
       // barriersNumber = 10;
        marker = new Marker(initialVertical, initialHorizontal, owner);
        field.setItem(marker);
    }

    public Marker getMarker() { return marker; }

    public int getBarriersNumber() { return barriersNumber; }

    public List<Barrier> getBarriers() { return barriers; }

    public boolean isActive() { return isActive; }

    public void makeMove(int vertical, int horizontal) throws FieldItemException {

        checkPlace(vertical, horizontal);
        setItem(vertical, horizontal);
    }

    public void makeMove(int vertical, int horizontal, BarrierPosition position) throws FieldItemException, NoBarriersException {

        //TODO зачем называть метод makeMove, когда совершается placeBarrier (это имя метода из Quoridor)

        if (barriersNumber == 0) {
            throw new NoBarriersException("you have no barriers");       //TODO можно ещё передать у кого из игроков
        }

        checkPlace(vertical, horizontal, position);
        setItem(vertical, horizontal, position);
    }

    private void checkPlace(int vertical, int horizontal) throws FieldItemException {

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
            throw new CellIsNotEmptyException("cell " + vertical + " " + horizontal + " is not empty");
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

                throw new ImpossibleToSetItemException("impossible to set marker on " + vertical + " " + horizontal +
                        " because of barrier");
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

            throw new ImpossibleToSetItemException("impossible to set marker on " + vertical + " " + horizontal +
                    " because of barrier");
        }

        return true;
    }

    private void checkPlace(int vertical, int horizontal, BarrierPosition position) throws FieldItemException {

        //TODO я бы переименовал в checkPlaceForBarrier

        if (position == BarrierPosition.VERTICAL) {                      //todo что-то сделать
            for (int i = vertical - Barrier.length + 1; i <= vertical + Barrier.length - 1; i++) {
                try {
                    if (field.getItem(i, horizontal).getType() != ItemType.EMPTY) {
                        throw new CellIsNotEmptyException("impossible to place barrier on cell " +
                                vertical + " " + horizontal);
                    }
                    if (field.getColor(i, horizontal) == CellColor.BLACK) {
                        throw new ImpossibleToSetItemException("impossible to set barrier on black cell");
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new FieldBoundsException("impossible to place barrier on " + vertical + " " + horizontal);
                }
            }

        } else if (position == BarrierPosition.HORIZONTAL) {
            for (int i = horizontal - Barrier.length + 1; i <= horizontal + Barrier.length - 1; i++) {
                try {
                    if (field.getItem(vertical, i).getType() != ItemType.EMPTY) {
                        throw new CellIsNotEmptyException("impossible to place barrier on field " +
                                vertical + " " + horizontal);
                    }
                    if (field.getColor(vertical, i) == CellColor.BLACK) {
                        throw new ImpossibleToSetItemException("impossible to set barrier on black cell");
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new FieldBoundsException("impossible to place barrier on " + vertical + " " + horizontal);
                }
            }
        }

        Barrier probableBarrier = new Barrier(vertical, horizontal, position);
        field.setItem(probableBarrier);

        if (TOP.isActive && !field.isRowAvailable(TOP.getMarker().getCoordinates(), 16)) {
            field.clearCells(probableBarrier.getCoordinates());
            throw new ImpossibleToSetItemException("you can't place barrier here. TOP player is locked");
        }
        if (BOTTOM.isActive && !field.isRowAvailable(BOTTOM.getMarker().getCoordinates(), 0)) {
            field.clearCells(probableBarrier.getCoordinates());
            throw new ImpossibleToSetItemException("you can't place barrier here. BOTTOM player is locked");
        }
//        if (RIGHT.isActive && !field.isRowAvailable(RIGHT.getMarker().getCoordinates(), ?????????)) //todo изменить логику

        field.clearCells(probableBarrier.getCoordinates());
    }

    private void setItem(int vertical, int horizontal) {

        //TODO это можно было бы назвать setMarker(~)

        field.setItem(new Empty(marker.getCoordinates().getVertical(), marker.getCoordinates().getHorizontal()));
        marker.moveTo(vertical, horizontal);
        field.setItem(marker);
    }

    private void setItem(int vertical, int horizontal, BarrierPosition position) {

        //TODO два setItem с разными параметрами, по моему должны делать примерно одно и тоже, а у тебя при добавление BarrierPosition изменяется тип добавляемого элемента, предлагаю переименовать метод
        // это фича же
        Barrier barrier = new Barrier(vertical, horizontal, position);
        field.setItem(barrier);
        barriers.add(barrier);
        barriersNumber--;
    }

}
