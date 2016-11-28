package ru.spbstu.icc.kspt.zhuikov.qouridor;


import ru.spbstu.icc.kspt.zhuikov.qouridor.exceptions.*;
import ru.spbstu.icc.kspt.zhuikov.qouridor.items.*;

import java.util.ArrayList;
import java.util.List;

public enum Player {

    TOP(0, 8),
    BOTTOM(16, 8),
    RIGHT(8, 16),
    LEFT(8, 0);

    private final int initialVertical;
    private final int initialHorizontal;

    private Marker marker;
    private int barriersNumber = 10;
    private List<Barrier> barriers = new ArrayList<Barrier>();
    private Field field;
    private boolean isActive = false;

    Player(int initialVertical, int initialHorizontal) {
        this.initialVertical = initialVertical;
        this.initialHorizontal = initialHorizontal;
    }

    public void createPlayer(Field field) {
        this.field = field;
        this.isActive = true;
       // barriersNumber = 10;
        marker = new Marker(initialVertical, initialHorizontal);
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

        if (barriersNumber == 0) {
            throw new NoBarriersException("you have no barriers");
        }

        if (TOP.isActive && !field.foo(TOP.getMarker().getCoordinates(), 16)) {
            throw new ImpossibleToSetException("you can't place barrier here. TOP player is locked");
        }
        if (BOTTOM.isActive && !field.foo(BOTTOM.getMarker().getCoordinates(), 0)) {
            throw new ImpossibleToSetException("you can't place barrier here. BOTTOM player is locked");
        }
//        if (RIGHT.isActive && !field.foo(RIGHT.getMarker().getCoordinates(), ?????????)) //todo изменить логику

        checkPlace(vertical, horizontal, position);
        setItem(vertical, horizontal, position);
    }

    private void checkPlace(int vertical, int horizontal) throws FieldItemException {

        try {
            field.getItem(vertical, horizontal);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new FieldCoordinatesException("impossible to place marker on " + vertical + " " + horizontal);
        }

        if (marker.getCoordinates().equals(new Coordinates(vertical, horizontal))) {
            throw new SetToSameCellException("impossible to move to the same cell");
        }

        if (field.getColor(vertical, horizontal) == CellColor.WHITE) {
            throw new ImpossibleToSetException("impossible to set marker on white cell");
        }

        if (field.getItem(vertical, horizontal).getType() != ItemType.EMPTY) {
            throw new CellIsNotEmptyException("cell " + vertical + horizontal + " is not empty");
        }

        if (Math.sqrt((vertical - marker.getCoordinates().getVertical()) * (vertical - marker.getCoordinates().getVertical()) +
                (horizontal - marker.getCoordinates().getHorizontal()) * (horizontal - marker.getCoordinates().getHorizontal())) > 2.1) {
            throw new TooLongDistanceException("you can move just nearby cells");  // todo не только на nearby
        }

        if (field.getItem((marker.getCoordinates().getVertical() + vertical) / 2,
                (marker.getCoordinates().getHorizontal() + horizontal) / 2).getType() == ItemType.BARRIER) {
            throw new ImpossibleToSetException("impossible to jump over the barrier");
        }
    }

    private void checkPlace(int vertical, int horizontal, BarrierPosition position) throws FieldItemException {

        if (position == BarrierPosition.VERTICAL) {                      //todo что-то сделать
            for (int i = vertical - Barrier.length + 1; i <= vertical + Barrier.length - 1; i++) {
                try {
                    if (field.getItem(i, horizontal).getType() != ItemType.EMPTY) {
                        throw new CellIsNotEmptyException("impossible to place barrier on occupied cell " +
                                vertical + " " + horizontal);
                    }
                    if (field.getColor(i, horizontal) == CellColor.BLACK) {
                        throw new ImpossibleToSetException("impossible to set barrier on black cell");
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new FieldCoordinatesException("impossible to place barrier on " + vertical + " " + horizontal);
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
                        throw new ImpossibleToSetException("impossible to set barrier on black cell");
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new FieldCoordinatesException("impossible to place barrier on " + vertical + " " + horizontal);
                }
            }
        }
    }

    private void setItem(int vertical, int horizontal) {

        field.setItem(new Empty(marker.getCoordinates().getVertical(), marker.getCoordinates().getHorizontal()));
        marker.moveTo(vertical, horizontal);
    }

    private void setItem(int vertical, int horizontal, BarrierPosition position) {

        Barrier barrier = new Barrier(vertical, horizontal, position);
        field.setItem(barrier);
        barriers.add(barrier);
        barriersNumber--;
    }

}
