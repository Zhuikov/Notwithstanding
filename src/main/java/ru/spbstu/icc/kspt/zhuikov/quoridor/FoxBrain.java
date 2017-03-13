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

    public Coordinates getTargetCoordinates(Field field) {

        for (Coordinates c : field.getMarkers()) {
            if (field.getItemOwner(c.getVertical(), c.getHorizontal()) == target) {
                return c;
            }
        }

        return
    }

    public FoxBrain(QuoridorField field, Fox fox) {

        this.quoridorField = field;
        GL = new GameLogic(field);
        this.fox = fox;

        List<Coordinates> playersCoordinates = field.getUsualPlayerMarkers();

        int rand = (int) (Math.random() * 10) % playersCoordinates.size(); //todo: size == 0
        System.out.println("fox rand: " + rand + " " + playersCoordinates.get(rand));
        target = field.getItem(playersCoordinates.get(rand)).getOwner();

    }

    public Command whatToDo() {
        return getNextStep();
    }

    private Command getNextStep() {

        Coordinates targetCoordinates = new Coordinates(-1, -1);
        for (Coordinates c : quoridorField.getUsualPlayerMarkers()) {
            if (quoridorField.getItem(c).getOwner() == target) {
                targetCoordinates = c;
            }
        }

        Stack<Coordinates> path = GL.getPath(fox.getCoordinates(), targetCoordinates);

        if (path.empty()) {
            int rand = (int)(Math.random() * 10) % fox.getPossibleMoves().size();
            return new Command(CommandType.MARKER, fox.getPossibleMoves().get(rand));
        }

        if (quoridorField.getItem(path.peek()).getType() == ItemType.EMPTY) {
            return new Command(CommandType.MARKER, path.peek());
        }

        if (path.peek().equals(targetCoordinates)) {
            return new Command(CommandType.MARKER, path.peek());
        }

        if (quoridorField.getItem(path.peek()).getType() != ItemType.EMPTY) {
            path.pop();
            return new Command(CommandType.MARKER, path.peek());
        }

        throw new IllegalArgumentException("Fox, getNextStep необработанный сценарий"); //todo: убрать это
    }
}
