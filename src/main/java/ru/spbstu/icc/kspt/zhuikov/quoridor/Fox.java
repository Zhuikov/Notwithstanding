package ru.spbstu.icc.kspt.zhuikov.quoridor;


import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Empty;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.ItemType;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Marker;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Owner;

import java.util.EmptyStackException;
import java.util.Stack;

public class Fox {

    private Marker marker;
    private QuoridorField field;
    private Marker target;

    public Fox(QuoridorField field) {

        this.field = field;
        int x, y;
        do {
            x = (int) (Math.random() * 10) % 9 * 2;
            y = (int) (Math.random() * 10) % 9 * 2;
        } while (field.getItem(x, y).getType() != ItemType.EMPTY);
        marker = new Marker(x, y, Owner.FOX);
        field.setItem(marker);

        double rand = Math.random();
        System.out.println("fox rand: " + rand);
        if (rand > 0.5) {
            target = QuoridorPlayer.TOP.getMarker();
        } else {
            target = QuoridorPlayer.BOTTOM.getMarker();
        }
    }

    public boolean makeMove() {

        Coordinates c = getNextCoordinates();
        // не проверяем потому, что проверяется в getNextCoordinates();
        field.setItem(new Empty(marker.getCoordinates().getVertical(), marker.getCoordinates().getHorizontal()));
        marker.moveTo(c.getVertical(), c.getHorizontal());
        field.setItem(marker);

        return c.equals(target.getCoordinates());
    }

    public Marker getMarker() {
        return marker;
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
