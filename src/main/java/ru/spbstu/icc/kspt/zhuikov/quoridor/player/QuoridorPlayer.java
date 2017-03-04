package ru.spbstu.icc.kspt.zhuikov.quoridor.player;

import ru.spbstu.icc.kspt.zhuikov.quoridor.QuoridorCore;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Coordinates;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Owner;

import java.util.List;

abstract public class QuoridorPlayer {

    protected Coordinates markerCoordinates;
    protected QuoridorCore game;
    protected Owner owner;
    protected List<VictoryListener> victoryListeners;

    public Coordinates getCoordinates() {
        return markerCoordinates;
    }

    public Owner getOwner() { return owner; }

    public List<Coordinates> getPossibleMoves() {
        return game.getPossibleMoves(markerCoordinates);
    }

    public void addVictoryListener(VictoryListener listener) {
        victoryListeners.add(listener);
    }

    abstract public void makeMove();
}