package ru.spbstu.icc.kspt.zhuikov.quoridor;

import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.FieldItemException;
import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.NoBarriersException;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.*;
import ru.spbstu.icc.kspt.zhuikov.quoridor.player.PlayerPosition;
import ru.spbstu.icc.kspt.zhuikov.quoridor.returningClasses.Field;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//TODO мне не хватает документации к коду
//TODO также хотелось бы, чтобы ядро с логикой было выделенно, если не в отдельный модуль, то хотябы в отдельный пакет(в отдельный относительно UI)

public class QuoridorCore {

    private final QuoridorField field = new QuoridorField(9);
    private final GameLogic GL = new GameLogic(new Field(field));
    private QuoridorQueue queue;
    private Map<PlayerPosition, Integer> barrierNumbers;

    private static final int startBarrierNumber = 10;

    public int getBarriersNumber(Owner owner) {

        PlayerPosition playerPosition;
        try {
            playerPosition = GL.getPlayerPosition(owner);
        } catch (IllegalArgumentException e) {
            return -1;
        }

        return barrierNumbers.get(playerPosition);
    }

    public Field getField() {
        return new Field(field);
    }

    public QuoridorCore(QuoridorQueue queue) {

        this.queue = queue;
        barrierNumbers = new HashMap<>();
    }


    public void moveMarker(Coordinates destination) throws FieldItemException {

        if (GL.checkMarker(queue.getCurrentPlayer().getCoordinates(), destination)) {
            field.setItem(new Empty(), queue.getCurrentPlayer().getCoordinates());
            field.setItem(new Marker(queue.getCurrentPlayer().getOwner()), destination);
        }

        queue.moveNextPlayer();
    }

    public void placeBarrier(Coordinates destination, BarrierPosition position)
            throws FieldItemException, NoBarriersException {

        if (barrierNumbers.get(GL.getPlayerPosition(queue.getCurrentPlayer().getOwner())) == 0) {
            throw new NoBarriersException("you have no barriers", GL.getPlayerPosition(queue.getCurrentPlayer().getOwner()));
        }

        if (GL.checkBarrier(destination, position)) {
            field.setBarrier(new Barrier(destination, position));
            int barrierNumber = barrierNumbers.get(GL.getPlayerPosition(queue.getCurrentPlayer().getOwner()));
            barrierNumbers.put(GL.getPlayerPosition(queue.getCurrentPlayer().getOwner()), --barrierNumber);
        }

        queue.moveNextPlayer();
    }

    public List<Coordinates> getPossibleMoves(Coordinates initCoordinates) {

        List<Coordinates> possibleMoves = new ArrayList<>();
        for (int i = initCoordinates.getVertical() - 4; i <= initCoordinates.getVertical() + 4; i+=2) {
            for (int j = initCoordinates.getHorizontal() - 4; j <= initCoordinates.getHorizontal() + 4; j+=2) {
                try {
                    GL.checkMarker(initCoordinates, new Coordinates(i, j));
                } catch (Exception e) {}
                possibleMoves.add(new Coordinates(i, j));
            }
        }

        return possibleMoves;
    }

    public void spawnFox(Coordinates spawnCoordinates) {
        if (field.getItem(spawnCoordinates.getVertical(), spawnCoordinates.getHorizontal()).getType() == ItemType.EMPTY) {
            field.setItem(new Marker(Owner.FOX), spawnCoordinates);
        }

        throw new IllegalArgumentException("cannot spawn Fox on " + spawnCoordinates);
    }

    public void addBarriers(PlayerPosition playerPosition) {

        barrierNumbers.put(playerPosition, startBarrierNumber);
    }

//    private void changePlayerTurn()  {
//
//        if (++step == foxTime) {
//            queue.addPlayer(new Fox(this));
//        }
//
//        if (isEnd()) return;
//
//        if (step % foxFrequency == 0 && fox != null) {
//            fox.makeMove();
//            if (isEnd()) return;
//        }
//
//        if (step == foxTime) {
//            fox = new Fox(field, queue);
//        }
//
//        step++;
//
//        if (queue.get(currentPlayer).isBot()) {
//            queue.get(currentPlayer).makeMove();
////            if (isEnd()) return;
//            changePlayerTurn();
//        }
//    }

}
