package ru.spbstu.icc.kspt.zhuikov.quoridor.player;


import ru.spbstu.icc.kspt.zhuikov.quoridor.Command;
import ru.spbstu.icc.kspt.zhuikov.quoridor.CommandType;
import ru.spbstu.icc.kspt.zhuikov.quoridor.FoxBrain;
import ru.spbstu.icc.kspt.zhuikov.quoridor.Quoridor;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.*;

import java.util.List;

public class Fox extends QuoridorPlayer {

    private UsualPlayer target;
    private FoxBrain brain;

    public Coordinates getTargetCoordinates() {
        return target.getCoordinates();
    }

    public void setBrain(FoxBrain brain) {
        this.brain = brain;
    }

    public Fox(Quoridor game, Coordinates initial, List<UsualPlayer> playerList) {

        this.game = game;
        owner = Owner.FOX;
        markerCoordinates = initial;
//        int x, y;
//        do {
//            x = (int) (Math.random() * 10) % field.getSize() * 2;
//            y = (int) (Math.random() * 10) % field.getSize() * 2;
//        } while (field.getItem(x, y).getType() != ItemType.EMPTY);
//        markerCoordinates = new Coordinates(x, y);
//        field.setItem(new Marker(owner), markerCoordinates);

        int rand = (int) (Math.random() * 10) % playerList.size();
        System.out.println("fox rand: " + rand);
        target = playerList.get(rand);
    }

    @Override
    public void makeMove(Command command) {

        if (command.getCommandType() == CommandType.MARKER) {
            game.moveMarker(command.getDestination());
        }

        throw new IllegalArgumentException("the fox can only move marker");
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
