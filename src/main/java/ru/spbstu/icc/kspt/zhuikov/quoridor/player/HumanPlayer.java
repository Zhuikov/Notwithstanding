package ru.spbstu.icc.kspt.zhuikov.quoridor.player;


import ru.spbstu.icc.kspt.zhuikov.quoridor.QuoridorField;
import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.*;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.*;

public class HumanPlayer extends UsualPlayer {

    public HumanPlayer(QuoridorField field, PlayerPosition position) {
        bot = false;
        owner = position.getOwner();
        markerCoordinates = new Coordinates(position.getInitialVertical(), position.getInitialHorizontal());
        field.setItem(new Marker(owner), markerCoordinates);
        this.field = field;
        this.position = position;
    }

    @Override
    public void makeMove() {}

    @Override
    public void moveMarker(int vertical, int horizontal) throws FieldItemException {

        checkMarkerPlace(vertical, horizontal);
        setMarker(new Coordinates(vertical, horizontal), owner);
    }

    @Override
    public void placeBarrier(int vertical, int horizontal, BarrierPosition position) throws FieldItemException, NoBarriersException {

        if (barriersNumber == 0) {
            throw new NoBarriersException("you have no barriers", this.position);
        }

        checkBarrierPlace(vertical, horizontal, position);
        setBarrier(vertical, horizontal, position);
    }

}
