package ru.spbstu.icc.kspt.zhuikov.quoridor.player;

import ru.spbstu.icc.kspt.zhuikov.quoridor.Command;
import ru.spbstu.icc.kspt.zhuikov.quoridor.Quoridor;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Coordinates;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Owner;

import java.util.List;

abstract public class QuoridorPlayer {

    protected Coordinates markerCoordinates;
    protected Quoridor game;
    protected Owner owner;

    public Coordinates getCoordinates() {
        return markerCoordinates;
    }

    public Owner getOwner() { return owner; }

    public List<Coordinates> getPossibleMoves() {
        return game.getPossibleMoves(markerCoordinates);
    }

    public void makeMove(Command command) {

        switch (command.getCommandType()) {
            case MARKER:
                game.moveMarker(command.getDestination());
                break;
            case BARRIER:
                game.placeBarrier(command.getDestination(), command.getBarrierPosition());
                break;
        }
    }
}