package ru.spbstu.icc.kspt.zhuikov.quoridor;


import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Coordinates;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.ItemType;
import ru.spbstu.icc.kspt.zhuikov.quoridor.player.Fox;

import java.util.Stack;

public class FoxBrain extends Brain {

    private Fox fox;

    public FoxBrain(QuoridorField field, Fox fox) {
        this.field = field;
        this.fox = fox;
    }

    public Command whatToDo() {

        return getNextStep();
    }

    //    public boolean makeMove() {
//
//        Coordinates c = getNextCoordinates();
//        field.setItem(new Empty(), markerCoordinates);
//        markerCoordinates = c;
//        field.setItem(new Marker(owner), markerCoordinates);
//
//        return c.equals(target.getCoordinates());
//    }
//
//    public Coordinates getTarget() {
//        return target.getCoordinates();
//    }
//
    private Command getNextStep() {

        Stack<Coordinates> path = getPath(fox.getCoordinates(), fox.getTargetCoordinates());

        if (!path.empty() && field.getItem(path.peek().getVertical(), path.peek().getHorizontal()).getType() == ItemType.EMPTY) {
            return new Command(CommandType.MARKER, path.peek());
        }

        if (path.empty()) {

        }
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
