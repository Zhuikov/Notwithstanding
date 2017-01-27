package ru.spbstu.icc.kspt.zhuikov.quoridor;


import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Empty;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.ItemType;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Marker;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Owner;

import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

public class Fox extends Player {

    private Marker target;

    public Fox(QuoridorField field, List<UsualPlayer> playerList) {

        this.field = field;
        int x, y;
        do {
            x = (int) (Math.random() * 10) % field.getSize() * 2;
            y = (int) (Math.random() * 10) % field.getSize() * 2;
        } while (field.getItem(x, y).getType() != ItemType.EMPTY);
        marker = new Marker(x, y, Owner.FOX);
        field.setItem(marker);

        int rand = (int) (Math.random() * 10) % playerList.size();
        System.out.println("fox rand: " + rand);
        target = playerList.get(rand).getMarker();
    }

    public boolean makeMove() {

        Coordinates c = getNextCoordinates();
        field.setItem(new Empty(marker.getCoordinates().getVertical(), marker.getCoordinates().getHorizontal()));
        marker.moveTo(c.getVertical(), c.getHorizontal());
        field.setItem(marker);

        return c.equals(target.getCoordinates());
    }

    public Coordinates getTarget() {
        return target.getCoordinates();
    }

    private Coordinates getNextCoordinates() {

        Stack<Coordinates> path = field.getPath(marker.getCoordinates(), target.getCoordinates());
        Coordinates c = marker.getCoordinates();

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
