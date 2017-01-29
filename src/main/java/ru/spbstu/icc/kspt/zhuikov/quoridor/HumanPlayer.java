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
    protected void makeMove() {}

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

}
