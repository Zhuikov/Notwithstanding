package ru.spbstu.icc.kspt.zhuikov.quoridor;


import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Coordinates;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.ItemType;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Owner;
import ru.spbstu.icc.kspt.zhuikov.quoridor.player.Fox;
import ru.spbstu.icc.kspt.zhuikov.quoridor.returningClasses.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class FoxBrain extends Brain {

    private Fox fox;
    private Owner target;

    public Owner getTarget() {
        return target;
    }

    public FoxBrain(Field field, Fox fox) {

        this.field = field;
        GL = new GameLogic(field);
        this.fox = fox;

        List<Coordinates> playersCoordinates = new ArrayList<>();
        for (Coordinates c : field.getMarkers()) {
            if (field.getItemOwner(c.getVertical(), c.getHorizontal()) != Owner.FOX) {
                playersCoordinates.add(c);
            }
        }
        int rand = (int) (Math.random() * 10) % playersCoordinates.size();
        System.out.println("fox rand: " + rand);
        target = field.getItemOwner(playersCoordinates.get(rand).getVertical(), playersCoordinates.get(rand).getHorizontal());

    }

    public Command whatToDo(Field field) {

        this.field = field;
        GL.setField(field);

        return getNextStep();
    }

    private Command getNextStep() {

        Coordinates targetCoordinates = new Coordinates(-1, -1);
        for (Coordinates c : field.getMarkers()) {
            if (field.getItemOwner(c.getVertical(), c.getHorizontal()) == target) {
                targetCoordinates = c;
            }
        }

        Stack<Coordinates> path = GL.getPath(fox.getCoordinates(), targetCoordinates);

        if (path.empty()) {
            int rand = (int)(Math.random() * 10) % fox.getPossibleMoves().size();
            return new Command(CommandType.MARKER, fox.getPossibleMoves().get(rand));
        }

        if (field.getItemType(path.peek().getVertical(), path.peek().getHorizontal()) == ItemType.EMPTY) {
            return new Command(CommandType.MARKER, path.peek());
        }

        if (path.peek().equals(targetCoordinates)) {
            return new Command(CommandType.MARKER, path.peek());
        }

        if (field.getItemType(path.peek().getVertical(), path.peek().getHorizontal()) != ItemType.EMPTY) {
            path.pop();
            return new Command(CommandType.MARKER, path.peek());
        }

        throw new IllegalArgumentException("Fox, getNextStep необработанный сценарий"); //todo: убрать это
    }
}
