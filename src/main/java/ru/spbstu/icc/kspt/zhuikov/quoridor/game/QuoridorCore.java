package ru.spbstu.icc.kspt.zhuikov.quoridor.game;

import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.FieldItemException;
import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.NoBarriersException;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.*;
import ru.spbstu.icc.kspt.zhuikov.quoridor.player.Owner;
import ru.spbstu.icc.kspt.zhuikov.quoridor.player.PlayerPosition;
import ru.spbstu.icc.kspt.zhuikov.quoridor.player.QuoridorPlayer;
import ru.spbstu.icc.kspt.zhuikov.quoridor.player.UsualPlayer;
import ru.spbstu.icc.kspt.zhuikov.quoridor.returningClasses.Field;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Класс, представляющий ядро игры.
 */
public class QuoridorCore {

    /**
     * Игровое поле.
     */
    private final QuoridorField quoridorField = new QuoridorField(9);

    /**
     * Логика игры.
     */
    private final GameLogic GL = new GameLogic(quoridorField);

    /**
     * Список с игроками.
     */
    private QuoridorQueue queue;

    /**
     * Текущий игрок, ход которого будет осуществлен.
     */
    private QuoridorPlayer currentPlayer;

    /**
     * Список позиций игроков и их текущего количества перегородок.
     */
    private Map<PlayerPosition, Integer> barrierNumbers = new HashMap<>();

    /**
     * Начальное число перегородок у каждого игрока.
     */
    private static final int startBarrierNumber = 10;

    /**
     * Возвращает число перегородок у заданного владельца.
     * @param owner - владелец, для которого необходимо вернуть количество перегородок.
     */
    public int getBarriersNumber(Owner owner) {

        PlayerPosition playerPosition;
        try {
            playerPosition = GL.getPlayerPosition(owner);
        } catch (IllegalArgumentException e) {
            return -1;
        }

        return barrierNumbers.get(playerPosition);
    }

    /**
     * Возвращает игровое поле.
     */
    public QuoridorField getField() {
        return quoridorField;
    }

    /**
     * Возвращает неизменяемое поле игры.
     */
    public Field getConstantField() {
        return new Field(quoridorField);
    }

    public QuoridorCore() {  }

    /**
     * Устанавливает список с игроками, использующими ядро.
     * @param queue - список игроков.
     */
    public void setQueue(QuoridorQueue queue) {
        this.queue = queue;
    }

    /**
     * Передвигает фишку текущего игрока на клетку поля с указанными координатами.
     * @param destination - координаты клетки.
     * @throws FieldItemException при невозможном передвижении фишки; исключение содержит информацию о нарушеных правилах.
     */
    public void moveMarker(Coordinates destination) throws FieldItemException {

        GL.checkMarker(quoridorField.getCoordinates(currentPlayer.getOwner()),
                destination, currentPlayer.getOwner() == Owner.FOX);

        quoridorField.setItem(new Empty(), quoridorField.getCoordinates(currentPlayer.getOwner()));
        quoridorField.setItem(new Marker(currentPlayer.getOwner()), destination);

        if (GL.checkVictory(currentPlayer)) {
            System.out.println("true");
            queue.onWin();
        } else {
            if (currentPlayer.getOwner() != Owner.FOX) {
                queue.updateCurrentPlayer();
            }
            queue.moveNextPlayer();
        }
    }

    /**
     * Устанавливает перегородку текущего игрока на клетку поля с указанными координатами.
     * @param destination - координаты клетки.
     * @param position - позиция размещения перегородки.
     * @throws FieldItemException при невозможном размещении перегородки на поле.
     * @throws NoBarriersException при отсутствии перегородок у текущего игрока.
     */
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

        queue.updateCurrentPlayer();
        queue.moveNextPlayer();
    }

    /**
     * Возвращает список координат, на которые текущему игроку возможно передвинуть фишку.
     */
    public List<Coordinates> getPossibleMoves() {

        List<Coordinates> possibleMoves = new ArrayList<>();
        Coordinates currentCoordinates = quoridorField.getCoordinates(currentPlayer.getOwner());
        for (int i = currentCoordinates.getVertical() - 4; i <= currentCoordinates.getVertical() + 4; i+=2) {
            for (int j = currentCoordinates.getHorizontal() - 4; j <= currentCoordinates.getHorizontal() + 4; j+=2) {
                try {
                    GL.checkMarker(currentCoordinates, new Coordinates(i, j), false);
                    possibleMoves.add(new Coordinates(i, j));
                } catch (Exception e) {}
            }
        }

        return possibleMoves;
    }

    /**
     * Создает лису на случайной клетке игрового поля.
     */
    public void spawnFox() {

        int x, y;
        do {
            x = (int) (Math.random() * 10) % quoridorField.getSize() * 2;
            y = (int) (Math.random() * 10) % quoridorField.getSize() * 2;
        } while (quoridorField.getItem(x, y).getType() != ItemType.EMPTY);

        quoridorField.setItem(new Marker(Owner.FOX), new Coordinates(x, y));
    }

    /**
     * Устанавливает текущего игрока.
     * @param quoridorPlayer - новый текущий игрок.
     */
    public void setCurrentPlayer(QuoridorPlayer quoridorPlayer) {
        this.currentPlayer = quoridorPlayer;
    }

    /**
     * Добавляет фишку обычного игрока на игровое поле, а также в список с позицией игроков и количеством перегородок.
     * @param usualPlayer - игрок для добавления.
     */
    public void addPlayer(UsualPlayer usualPlayer) {

        quoridorField.setItem(new Marker(usualPlayer.getOwner()), usualPlayer.getPosition().getInitialCoordinates());
        barrierNumbers.put(usualPlayer.getPosition(), startBarrierNumber);
    }
}
