package ru.spbstu.icc.kspt.zhuikov.quoridor;


import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.*;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.*;

public class HumanPlayer extends UsualPlayer {

    public HumanPlayer(QuoridorField field, PlayerPosition position) {
        bot = false;
        this.marker = new Marker(position.initialVertical, position.initialHorizontal, position.owner);
        field.setItem(marker);
        this.field = field;
        this.position = position;
    }

    @Override
    public void makeMove() {}

    @Override
    public void moveMarker(int vertical, int horizontal) throws FieldItemException {

        checkMarkerPlace(vertical, horizontal);
        setMarker(vertical, horizontal);
    }

    @Override
    public void placeBarrier(int vertical, int horizontal, BarrierPosition position) throws FieldItemException, NoBarriersException {

        if (barriersNumber == 0) {
            throw new NoBarriersException("you have no barriers");       //TODO можно ещё передать у кого из игроков
        }

        checkBarrierPlace(vertical, horizontal, position);
        setBarrier(vertical, horizontal, position);
    }

    private void checkBarrierPlace(int vertical, int horizontal, BarrierPosition position) throws FieldItemException {

        if (vertical % 2 == 0 || horizontal % 2 == 0) {
            throw new ImpossibleToSetItemException("impossible to set barrier here");
        }

        if (position == BarrierPosition.VERTICAL) {                      //todo что-то сделать
            for (int i = vertical - Barrier.length + 1; i <= vertical + Barrier.length - 1; i++) {
                try {
                    if (field.getItem(i, horizontal).getType() != ItemType.EMPTY) {
                        throw new CellIsNotEmptyException("impossible to place barrier here");
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
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new FieldBoundsException("impossible to place barrier here");
                }
            }
        }

        // todo проверка на закрытие
//        Barrier probableBarrier = new Barrier(vertical, horizontal, position);
//        field.setItem(probableBarrier);
//
//        if (TOP.isActive && field.getPathToRow(TOP.getMarker().getCoordinates(), 16).empty()) {
//            field.clearCells(probableBarrier.getCoordinates());
//            throw new ImpossibleToSetItemException("you can't place barrier here. Player is locked");
//        }
//        if (BOTTOM.isActive && field.getPathToRow(BOTTOM.getMarker().getCoordinates(), 0).empty()) {
//            field.clearCells(probableBarrier.getCoordinates());
//            throw new ImpossibleToSetItemException("you can't place barrier here. Player is locked");
//        }
//
//        field.clearCells(probableBarrier.getCoordinates());
    }

}
