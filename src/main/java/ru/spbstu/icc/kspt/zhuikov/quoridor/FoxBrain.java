package ru.spbstu.icc.kspt.zhuikov.quoridor;


import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Coordinates;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.ItemType;
import ru.spbstu.icc.kspt.zhuikov.quoridor.player.Fox;

import java.util.Stack;

public class FoxBrain extends Brain {

    private Fox fox;
    private GameLogic GL;

    public FoxBrain(QuoridorField field, Fox fox) {
        this.field = field;
        this.fox = fox;
        GL = new GameLogic(field);
    }

    public Command whatToDo() {

        return getNextStep();
    }

    private Command getNextStep() {

        Stack<Coordinates> path = GL.getPath(fox.getCoordinates(), fox.getTargetCoordinates());

        if (path.empty()) {
            int rand = (int)(Math.random() * 10) % fox.getPossibleMoves().size();
            return new Command(CommandType.MARKER, fox.getPossibleMoves().get(rand));
        }

        if (field.getItem(path.peek().getVertical(), path.peek().getHorizontal()).getType() == ItemType.EMPTY) {
            return new Command(CommandType.MARKER, path.peek());
        }

        if (path.peek().equals(fox.getTargetCoordinates())) {
            return new Command(CommandType.MARKER, path.peek());
        }

        if (field.getItem(path.peek().getVertical(), path.peek().getHorizontal()).getType() != ItemType.EMPTY) {
            path.pop();
            return new Command(CommandType.MARKER, path.peek());
        }

        throw new IllegalArgumentException("Fox, getNextStep необработанный сценарий"); //todo: убрать это
    }
}
