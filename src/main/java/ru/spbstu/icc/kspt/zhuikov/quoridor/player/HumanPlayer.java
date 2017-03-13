package ru.spbstu.icc.kspt.zhuikov.quoridor.player;


import ru.spbstu.icc.kspt.zhuikov.quoridor.QuoridorCore;
import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.FieldItemException;
import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.NoBarriersException;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.BarrierPosition;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Coordinates;

public class HumanPlayer extends UsualPlayer {

    public HumanPlayer(QuoridorCore core, PlayerPosition position) {

        this.core = core;
        this.position = position;
        owner = position.getOwner();
        markerCoordinates = new Coordinates(position.getInitialVertical(), position.getInitialHorizontal());
    }

    @Override
    public void makeMove() {

        core.setCurrentPlayer(this);
    }

    public void moveMarker(int vertical, int horizontal) throws FieldItemException {

        core.moveMarker(new Coordinates(vertical, horizontal));
        markerCoordinates = new Coordinates(vertical, horizontal);

        if (markerCoordinates.getVertical() == position.getDestinationRow()) {
            for (WinnerListener listener : winnerListeners) {
                listener.setWinner(owner);
            }
        }
    }

    public void placeBarrier(int vertical, int horizontal, BarrierPosition position)
            throws FieldItemException, NoBarriersException {

        core.placeBarrier(new Coordinates(vertical, horizontal), position);
    }

}
