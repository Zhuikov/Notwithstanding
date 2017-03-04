package ru.spbstu.icc.kspt.zhuikov.quoridor.player;


import ru.spbstu.icc.kspt.zhuikov.quoridor.Command;
import ru.spbstu.icc.kspt.zhuikov.quoridor.CommandType;
import ru.spbstu.icc.kspt.zhuikov.quoridor.FoxBrain;
import ru.spbstu.icc.kspt.zhuikov.quoridor.QuoridorCore;
import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.FieldItemException;
import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.NoBarriersException;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.*;
import ru.spbstu.icc.kspt.zhuikov.quoridor.returningClasses.Field;

public class Fox extends QuoridorPlayer {

    private FoxBrain brain;

    public Fox(QuoridorCore game) {

        this.game = game;
        owner = Owner.FOX;

        Field gameField = game.getField();
        brain = new FoxBrain(gameField, this);

        int x, y;
        do {
            x = (int) (Math.random() * 10) % gameField.getSize() * 2;
            y = (int) (Math.random() * 10) % gameField.getSize() * 2;
        } while (gameField.getItemType(x, y) != ItemType.EMPTY);
        markerCoordinates = new Coordinates(x, y);
        game.spawnFox(markerCoordinates);

    }

    @Override
    public void makeMove() {

        Field field = game.getField();
        Command command = brain.whatToDo(field);

        if (command.getCommandType() == CommandType.MARKER) {
            try {
                game.moveMarker(command.getDestination());
                markerCoordinates = command.getDestination();
            } catch (FieldItemException e) {
                throw new AssertionError("fox.makeMove()");
            }
        } else {
            throw new IllegalArgumentException("the fox can only move marker");
        }

        if (field.getItemOwner(markerCoordinates.getVertical(), markerCoordinates.getHorizontal()) == brain.getTarget()) {
            for (VictoryListener listener : victoryListeners) {
                listener.setWinner(owner);
            }
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
