package ru.spbstu.icc.kspt.zhuikov.quoridor.player;


import ru.spbstu.icc.kspt.zhuikov.quoridor.BotBrain;
import ru.spbstu.icc.kspt.zhuikov.quoridor.Quoridor;
import ru.spbstu.icc.kspt.zhuikov.quoridor.QuoridorField;
import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.FieldItemException;
import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.NoBarriersException;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.*;

import java.util.List;
import java.util.Stack;

public class BotPlayer extends UsualPlayer {

    private final UsualPlayer opponent;
    private BotBrain brain;

    public UsualPlayer getOpponent() {
        return opponent;
    }

    public void setBrain(BotBrain brain) {
        this.brain = brain;
    }

    public BotPlayer(Quoridor game, UsualPlayer opponent, PlayerPosition position) {
        this.game = game;
        this.position = position;
        this.opponent = opponent;
        owner = position.getOwner();
        markerCoordinates = new Coordinates(position.getInitialVertical(), position.getInitialHorizontal());
    }

//    public BotPlayer(QuoridorField field, PlayerPosition position, UsualPlayer opponent) {
//        bot = true;
//        owner = position.getOwner();
//        this.opponent = opponent;
//        this.field = field;
//        this.position = position;
//        markerCoordinates = new Coordinates(position.getInitialVertical(), position.getInitialHorizontal());
//        field.setItem(new Marker(owner), markerCoordinates);
//    }
//
//
//    @Override
//    public void makeMove()  {
//
//        double rand = Math.random();
//        if (rand > 0.47) {
//
//            try {
//                placeBarrier();
//            } catch (NoBarriersException e) {
//                moveMarker();
//            }
//
//        } else moveMarker();
//
//    }
//
//    private void placeBarrier() throws NoBarriersException {
//
//        if (barriersNumber == 0) {
//            throw new NoBarriersException("you have no barriers", this.position);
//        }
//
//        Stack<Coordinates> opponentsPath = getPathToRow(opponent.getCoordinates(), opponent.getPosition().getDestinationRow());
//        Coordinates between = new Coordinates((opponentsPath.peek().getVertical() + opponent.getCoordinates().getVertical()) / 2,
//                (opponentsPath.peek().getHorizontal() + opponent.getCoordinates().getHorizontal()) / 2);
//
//        double rand = Math.random();
//
//        try {
//            if (rand < 0.5) {
//                int vertical = between.getVertical() % 2 == 0 ? between.getVertical() - 1 : between.getVertical();
//                int horizontal = between.getHorizontal() % 2 == 0 ? between.getHorizontal() - 1 : between.getHorizontal();
//                BarrierPosition barrierPosition = rand > 0.15 ? BarrierPosition.HORIZONTAL : BarrierPosition.VERTICAL;
//                checkBarrierPlace(vertical, horizontal, barrierPosition);
//                setBarrier(vertical, horizontal, barrierPosition);
//
//            } else {
//                int vertical = between.getVertical() % 2 == 0 ? between.getVertical() + 1 : between.getVertical();
//                int horizontal = between.getHorizontal() % 2 == 0 ? between.getHorizontal() + 1 : between.getHorizontal();
//                BarrierPosition barrierPosition = rand > 0.65 ? BarrierPosition.HORIZONTAL : BarrierPosition.VERTICAL;
//                checkBarrierPlace(vertical, horizontal, barrierPosition);
//                setBarrier(vertical, horizontal, barrierPosition);
//            }
//
//        } catch (Exception e) {
//            moveMarker();
//        }
//
//    }
//
//    private void moveMarker() {
//
//        Stack<Coordinates> path = getPathToRow(markerCoordinates, position.getDestinationRow());
//
//        if (field.getItem(path.peek().getVertical(), path.peek().getHorizontal()).getType() != ItemType.EMPTY) {
//            path.pop();
//        }
//        setMarker(path.peek(), owner);
//
//    }

}
