package ru.spbstu.icc.kspt.zhuikov.quoridor.player;


import ru.spbstu.icc.kspt.zhuikov.quoridor.Quoridor;

public class HumanPlayer extends UsualPlayer {


    public HumanPlayer(Quoridor game, PlayerPosition position) {
        this.game = game;
        this.position = position;
        owner = position.getOwner();
    }
//
//    @Override
//    public void moveMarker(int vertical, int horizontal) throws FieldItemException {
//
//        checkMarkerPlace(vertical, horizontal);
//        setMarker(new Coordinates(vertical, horizontal), owner);
//    }
//
//    @Override
//    public void placeBarrier(int vertical, int horizontal, BarrierPosition position) throws FieldItemException, NoBarriersException {
//
//        if (barriersNumber == 0) {
//            throw new NoBarriersException("you have no barriers", this.position);
//        }
//
//        checkBarrierPlace(vertical, horizontal, position);
//        setBarrier(vertical, horizontal, position);
//    }

}
