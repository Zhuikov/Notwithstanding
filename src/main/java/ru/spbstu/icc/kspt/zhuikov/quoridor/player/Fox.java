package ru.spbstu.icc.kspt.zhuikov.quoridor.player;


import ru.spbstu.icc.kspt.zhuikov.quoridor.*;
import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.FieldItemException;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.*;

public class Fox extends QuoridorPlayer {

    private FoxBrain brain;

    public Fox(QuoridorCore core) {

        this.core = core;
        owner = Owner.FOX;

        QuoridorField gameField = core.getField();
        brain = new FoxBrain(quoridorField, this);

        int x, y;
        do {
            x = (int) (Math.random() * 10) % gameField.getSize() * 2;
            y = (int) (Math.random() * 10) % gameField.getSize() * 2;
        } while (gameField.getItem(x, y).getType() != ItemType.EMPTY);
        markerCoordinates = new Coordinates(x, y);
        core.spawnFox(markerCoordinates);

    }

    @Override
    public void makeMove() {

        core.setCurrentPlayer(this);
        Command command = brain.whatToDo();

        if (command.getCommandType() == CommandType.MARKER) {
            try {
                core.moveMarker(command.getDestination());
                markerCoordinates = command.getDestination();
            } catch (FieldItemException e) {
                throw new AssertionError("fox.makeMove()");
            }
        } else {
            throw new AssertionError("the fox can only move marker");
        }

//        Coordinates c = getNextCoordinates();
//        field.setItem(new Empty(), markerCoordinates);
//        markerCoordinates = c;
//        field.setItem(new Marker(owner), markerCoordinates);
//
//        return c.equals(target.getCoordinates());
    }
//
//
//    private Coordinates getNextCoordinates() {
//
//        Stack<Coordinates> path = field.getPath(markerCoordinates, target.getCoordinates());
//        Coordinates c = markerCoordinates;
//
//        try {
//            if (field.getItem(path.peek().getVertical(), path.peek().getHorizontal()).getType() != ItemType.EMPTY
//                    && !path.peek().equals(target.getCoordinates())) {
//                path.pop();
//                path.peek();
//            }
//            c = path.peek();
//        } catch (EmptyStackException e) {}
//
//        return c;
//    }
}
