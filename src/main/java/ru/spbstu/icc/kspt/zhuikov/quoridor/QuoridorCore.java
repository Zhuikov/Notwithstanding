package ru.spbstu.icc.kspt.zhuikov.quoridor;

import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.FieldItemException;
import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.NoBarriersException;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.*;
import ru.spbstu.icc.kspt.zhuikov.quoridor.player.Fox;
import ru.spbstu.icc.kspt.zhuikov.quoridor.player.PlayerPosition;
import ru.spbstu.icc.kspt.zhuikov.quoridor.player.QuoridorPlayer;
import ru.spbstu.icc.kspt.zhuikov.quoridor.player.UsualPlayer;
import ru.spbstu.icc.kspt.zhuikov.quoridor.returningClasses.Field;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//TODO мне не хватает документации к коду
//TODO также хотелось бы, чтобы ядро с логикой было выделенно, если не в отдельный модуль, то хотябы в отдельный пакет(в отдельный относительно UI)

public class QuoridorCore {

    private final QuoridorField quoridorField = new QuoridorField(9);
    private final GameLogic GL = new GameLogic(quoridorField);
    private QuoridorQueue queue;
    private QuoridorPlayer currentPlayer;
    private Map<PlayerPosition, Integer> barrierNumbers = new HashMap<>();

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
        return new Field(quoridorField);
    }

    public QuoridorCore() {  }

    public void setQueue(QuoridorQueue queue) {
        this.queue = queue;
    }

    public void moveMarker(Coordinates destination) throws FieldItemException {

        GL.checkMarker(currentPlayer.getCoordinates(), destination, currentPlayer.getOwner() == Owner.FOX);

        quoridorField.clearCell(currentPlayer.getCoordinates());
        quoridorField.setItem(new Marker(currentPlayer.getOwner()), destination);

        // if (win....)

        queue.moveNextPlayer();
    }

    public void placeBarrier(Coordinates destination, BarrierPosition position)
            throws FieldItemException, NoBarriersException {

        if (barrierNumbers.get(GL.getPlayerPosition(currentPlayer.getOwner())) == 0) {
            throw new NoBarriersException("you have no barriers", GL.getPlayerPosition(currentPlayer.getOwner()));
        }

        if (GL.checkBarrier(destination, position)) {
            quoridorField.setBarrier(new Barrier(destination, position));
            int barrierNumber = barrierNumbers.get(GL.getPlayerPosition(currentPlayer.getOwner()));
            barrierNumbers.put(GL.getPlayerPosition(currentPlayer.getOwner()), --barrierNumber);
        }

        queue.moveNextPlayer();
    }

    public List<Coordinates> getPossibleMoves(Coordinates initCoordinates) {

        List<Coordinates> possibleMoves = new ArrayList<>();
        for (int i = initCoordinates.getVertical() - 4; i <= initCoordinates.getVertical() + 4; i+=2) {
            for (int j = initCoordinates.getHorizontal() - 4; j <= initCoordinates.getHorizontal() + 4; j+=2) {
                try {
                    GL.checkMarker(initCoordinates, new Coordinates(i, j), false);
                    possibleMoves.add(new Coordinates(i, j));
                } catch (Exception e) {}
            }
        }

        return possibleMoves;
    }

    public void spawnFox(Coordinates spawnCoordinates) {

        if (quoridorField.getItem(spawnCoordinates).getType() != ItemType.EMPTY) {
            throw new IllegalArgumentException("cannot spawn Fox on " + spawnCoordinates);
        }

        quoridorField.setItem(new Marker(Owner.FOX), spawnCoordinates);
    }

    public void setCurrentPlayer(QuoridorPlayer quoridorPlayer) {

        this.currentPlayer = quoridorPlayer;
    }

    public void addPlayer(UsualPlayer usualPlayer) {

        quoridorField.setItem(new Marker(usualPlayer.getOwner()), usualPlayer.getCoordinates());
        barrierNumbers.put(usualPlayer.getPosition(), startBarrierNumber);
    }
}
