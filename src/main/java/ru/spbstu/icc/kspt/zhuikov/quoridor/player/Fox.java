package ru.spbstu.icc.kspt.zhuikov.quoridor.player;


import ru.spbstu.icc.kspt.zhuikov.quoridor.QuoridorField;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.*;

import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

public class Fox extends QuoridorPlayer {

    private final UsualPlayer target;

    public Fox(QuoridorField field, List<UsualPlayer> playerList) {

        owner = Owner.FOX;
        this.field = field;
        int x, y;
        do {
            x = (int) (Math.random() * 10) % field.getSize() * 2;
            y = (int) (Math.random() * 10) % field.getSize() * 2;
        } while (field.getItem(x, y).getType() != ItemType.EMPTY);
        markerCoordinates = new Coordinates(x, y);
        field.setItem(new Marker(owner), markerCoordinates);

        int rand = (int) (Math.random() * 10) % playerList.size();
        System.out.println("fox rand: " + rand);
        target = playerList.get(rand);
    }

    public boolean makeMove() {

        Coordinates c = getNextCoordinates();
        field.setItem(new Empty(), markerCoordinates);
        markerCoordinates = c;
        field.setItem(new Marker(owner), markerCoordinates);

        return c.equals(target.getCoordinates());
    }

    public Coordinates getTarget() {
        return target.getCoordinates();
    }

    private Coordinates getNextCoordinates() {

        Stack<Coordinates> path = field.getPath(markerCoordinates, target.getCoordinates());
        Coordinates c = markerCoordinates;

        try {
            if (field.getItem(path.peek().getVertical(), path.peek().getHorizontal()).getType() != ItemType.EMPTY
                    && !path.peek().equals(target.getCoordinates())) {
                path.pop();
                path.peek();
            }
            c = path.peek();
        } catch (EmptyStackException e) {}

        return c;
    }
}
