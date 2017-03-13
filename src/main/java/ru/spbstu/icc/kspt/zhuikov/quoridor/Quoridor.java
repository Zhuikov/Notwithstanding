package ru.spbstu.icc.kspt.zhuikov.quoridor;


import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.FieldItemException;
import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.NoBarriersException;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.BarrierPosition;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Coordinates;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Owner;
import ru.spbstu.icc.kspt.zhuikov.quoridor.player.*;
import ru.spbstu.icc.kspt.zhuikov.quoridor.returningClasses.Field;
import ru.spbstu.icc.kspt.zhuikov.quoridor.returningClasses.Player;

import java.util.List;

public class Quoridor {

    private QuoridorCore core;
    private QuoridorQueue queue;

    public Quoridor(boolean bot) {

        core  = new QuoridorCore();
        queue = new QuoridorQueue(core);
        core.setQueue(queue);

        if (bot) {
            queue.addPlayer(new HumanPlayer(core, PlayerPosition.BOT));
            queue.addPlayer(new BotPlayer(core, PlayerPosition.TOP));
        } else {
            queue.addPlayer(new HumanPlayer(core, PlayerPosition.BOT));
            queue.addPlayer(new HumanPlayer(core, PlayerPosition.TOP));
        }

        queue.moveNextPlayer();
    }

    public void moveMarker(int vertical, int horizontal) throws FieldItemException {
        core.moveMarker(new Coordinates(vertical, horizontal));
        queue.moveNextPlayer();
    }

    public void placeBarrier(int vertical, int horizontal, BarrierPosition position)
            throws FieldItemException, NoBarriersException {
        core.placeBarrier(new Coordinates(vertical, horizontal), position);
        queue.moveNextPlayer();
    }

    public void addWinnerListener (WinnerListener listener) {
        queue.addWinnerListener(listener);
    }

    public Player getCurrentPlayer() {
        Owner owner = queue.getCurrentPlayer().getOwner();
        return new Player(core.getBarriersNumber(owner), owner);
    }

    public int getStep() {
        return  queue.getStep();
    }

    public static int getFoxTime() {
        return QuoridorQueue.getFoxTime();
    }

    public static int getFoxFrequency() {
        return QuoridorQueue.getFoxFrequency();
    }

    public Field getField() { return core.getField(); }

    public List<Coordinates> getPossibleMoves() {
        return core.getPossibleMoves(queue.getCurrentPlayer().getCoordinates());
    }

    public int getBarriersNumber(Owner owner) {
        return core.getBarriersNumber(owner);
    }

    public static void setFoxTime(int foxTime) {

        if (foxTime < 0) {
            throw new IllegalArgumentException("Fox time must be >= 0");
        }
        QuoridorQueue.setFoxTime(foxTime);
    }

    public static void setFoxFrequency(int foxTurn) {

        if (foxTurn < 1 ) {
            throw new IllegalArgumentException("Fox frequency must be > 0");
        }
        QuoridorQueue.setFoxFrequency(foxTurn);
    }

}
