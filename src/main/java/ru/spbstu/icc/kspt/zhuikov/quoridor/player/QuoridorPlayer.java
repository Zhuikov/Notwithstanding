package ru.spbstu.icc.kspt.zhuikov.quoridor.player;

import ru.spbstu.icc.kspt.zhuikov.quoridor.Command;
import ru.spbstu.icc.kspt.zhuikov.quoridor.Quoridor;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Coordinates;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Owner;

abstract public class QuoridorPlayer {

    protected Coordinates markerCoordinates;
    protected Quoridor game;
    protected Owner owner;

    protected void makeMove(Command command) {
        // todo: here
    }

    public Coordinates getCoordinates() {
        return markerCoordinates;
    }
}
