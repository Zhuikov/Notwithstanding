package ru.spbstu.icc.kspt.zhuikov.quoridor.player;

import ru.spbstu.icc.kspt.zhuikov.quoridor.QuoridorCore;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Coordinates;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Owner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

abstract public class QuoridorPlayer {

    protected Coordinates markerCoordinates;
    protected QuoridorCore core;
    protected Owner owner;
    protected Set<WinnerListener> winnerListeners = new HashSet<>();

    public Coordinates getCoordinates() {
        return markerCoordinates;
    }

    public Owner getOwner() { return owner; }

    public List<Coordinates> getPossibleMoves() {
        return core.getPossibleMoves(markerCoordinates);
    }

    public void addWinnerListener(Set<WinnerListener> listeners) {
            winnerListeners.addAll(listeners);
    }

    public void addWinnerListener(WinnerListener listener) {
        winnerListeners.add(listener);
    }

    abstract public void makeMove();
}