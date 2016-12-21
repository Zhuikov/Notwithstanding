package ru.spbstu.icc.kspt.zhuikov.quoridor;


import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.*;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.*;

import java.util.*;


// todo разбить бы весь этот огромный класс. Мб сделать отдельный класс с логикой
// todo вообще, надо было без енама
public enum QuoridorPlayer {

    TOP(0, 8, Owner.TOP, 16),
    BOTTOM(16, 8, Owner.BOTTOM, 0);
  //  RIGHT(8, 16, Owner.RIGHT),
  //  LEFT(8, 0, Owner.LEFT);

    private final int initialVertical;
    private final int initialHorizontal;
    private final Owner owner;
    private final int destinationRow;

    private Marker marker;
    private boolean isBot = false;
    private int barriersNumber = 10;
    private List<Barrier> barriers = new ArrayList<Barrier>();
    private QuoridorField field;
    private boolean isActive = false;

    QuoridorPlayer(int initialVertical, int initialHorizontal, Owner owner, int destinationRow) {
        this.initialVertical = initialVertical;
        this.initialHorizontal = initialHorizontal;
        this.owner = owner;
        this.destinationRow = destinationRow;
    }

    public void createPlayer(QuoridorField field, boolean isBot) {          //TODO смущает, что чтобы создать игрока ему нужно передать поле
        this.field = field;
        this.isBot = isBot;
        this.isActive = true;
        barriersNumber = 10;
        marker = new Marker(initialVertical, initialHorizontal, owner);
        field.setItem(marker);
    }

    public Marker getMarker() { return marker; }

    public int getBarriersNumber() { return barriersNumber; }

    public int getDestinationRow() {
        return destinationRow;
    }

    public List<Barrier> getBarriers() { return barriers; }

    public boolean isActive() { return isActive; }

    public boolean isBot() { return  isBot; }

    public List<Coordinates> getPossibleMoves() {

        List<Coordinates> possibleMoves = new LinkedList<>();
        for (int i = this.marker.getCoordinates().getVertical() - 4; i <= this.marker.getCoordinates().getVertical() + 4; i++) {
            for (int j = this.marker.getCoordinates().getHorizontal() - 4; j <= this.marker.getCoordinates().getHorizontal() + 4; j++) {
                try {
                    checkPlace(i, j);
                    possibleMoves.add(new Coordinates(i, j));
                } catch (Exception e) {}
            }
        }

        return possibleMoves;
    }

    public void makeBotMove() throws FieldItemException, NoBarriersException {

        if (!isBot) {
            throw new UnsupportedOperationException(this.name() + " not a bot");
        }
        double rand = Math.random();
        if (rand > 0.45) {
            setBotBarrier();
        } else setBotMarker();
    }

    private void setBotMarker() throws FieldItemException {

        Stack<Coordinates> path = field.getPathToRow(marker.getCoordinates(), destinationRow);
        if (field.getItem(path.peek().getVertical(), path.peek().getHorizontal()).getType() == ItemType.MARKER) {
            path.pop();
            try {
                makeMove(path.peek().getVertical(), path.peek().getHorizontal());
            } catch (Exception x) {
                setBotBarrier();
            }
            return;
        }
        makeMove(path.peek().getVertical(), path.peek().getHorizontal());
    }

    private void setBotBarrier() throws FieldItemException {

        if (!TOP.isActive()) {
            setBotMarker();
            return;
        }

        Stack<Coordinates> topPath = field.getPathToRow(TOP.marker.getCoordinates(), TOP.destinationRow);
        Coordinates between = new Coordinates((topPath.peek().getVertical() + TOP.marker.getCoordinates().getVertical()) / 2,
                (topPath.peek().getHorizontal() + TOP.marker.getCoordinates().getHorizontal()) / 2);
        double rand = Math.random();
        try {
            if (rand < 0.5) {
                makeMove(between.getVertical() % 2 == 0 ? between.getVertical() - 1 : between.getVertical(),
                        between.getHorizontal() % 2 == 0 ? between.getHorizontal() - 1 : between.getHorizontal(),
                        rand > 0.15 ? BarrierPosition.HORIZONTAL : BarrierPosition.VERTICAL);
            } else {
                makeMove(between.getVertical() % 2 == 0 ? between.getVertical() + 1 : between.getVertical(),
                        between.getHorizontal() % 2 == 0 ? between.getHorizontal() + 1 : between.getHorizontal(),
                        rand > 0.65 ? BarrierPosition.HORIZONTAL : BarrierPosition.VERTICAL);
            }
        } catch (Exception e) {
            setBotMarker();
        }

    }

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

    private void checkPlace(int vertical, int horizontal, BarrierPosition position) throws FieldItemException {

        //TODO я бы переименовал в checkPlaceForBarrier

        if (vertical % 2 == 0 || horizontal % 2 == 0) {
            throw new ImpossibleToSetItemException("impossible to set barrier here");
        }

        if (position == BarrierPosition.VERTICAL) {                      //todo что-то сделать
            for (int i = vertical - Barrier.length + 1; i <= vertical + Barrier.length - 1; i++) {
                try {
                    if (field.getItem(i, horizontal).getType() != ItemType.EMPTY) {
                        throw new CellIsNotEmptyException("impossible to place barrier here");
                    }
                    if (field.getColor(i, horizontal) == CellColor.BLACK) {
                        throw new ImpossibleToSetItemException("impossible to set barrier on black cell");
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new FieldBoundsException("impossible to place barrier here");
                }
            }

        } else if (position == BarrierPosition.HORIZONTAL) {
            for (int i = horizontal - Barrier.length + 1; i <= horizontal + Barrier.length - 1; i++) {
                try {
                    if (field.getItem(vertical, i).getType() != ItemType.EMPTY) {
                        throw new CellIsNotEmptyException("impossible to place barrier here");
                    }
                    if (field.getColor(vertical, i) == CellColor.BLACK) {
                        throw new ImpossibleToSetItemException("impossible to set barrier on black cell");
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new FieldBoundsException("impossible to place barrier here");
                }
            }
        }

        Barrier probableBarrier = new Barrier(vertical, horizontal, position);
        field.setItem(probableBarrier);

        if (TOP.isActive && field.getPathToRow(TOP.getMarker().getCoordinates(), 16).empty()) {
            field.clearCells(probableBarrier.getCoordinates());
            throw new ImpossibleToSetItemException("you can't place barrier here. Player is locked");
        }
        if (BOTTOM.isActive && field.getPathToRow(BOTTOM.getMarker().getCoordinates(), 0).empty()) {
            field.clearCells(probableBarrier.getCoordinates());
            throw new ImpossibleToSetItemException("you can't place barrier here. Player is locked");
        }
//        if (RIGHT.isActive && !field.getPathToRow(RIGHT.getMarker().getCoordinates(), ?????????)) //todo изменить логику

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
        Barrier barrier = new Barrier(vertical, horizontal, position);
        field.setItem(barrier);
        barriers.add(barrier);
        barriersNumber--;
    }

}
