package ru.spbstu.icc.kspt.zhuikov.quoridor;


import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Coordinates;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.ItemType;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Owner;
import ru.spbstu.icc.kspt.zhuikov.quoridor.player.Fox;

import java.util.List;
import java.util.Stack;

public class FoxBrain extends Brain {

    private Fox fox;
    private Owner target;

    public FoxBrain(QuoridorField field, Fox fox) {

        this.quoridorField = field;
        GL = new GameLogic(field);
        this.fox = fox;

        List<Coordinates> playersCoordinates = field.getUsualPlayerMarkers();

        if (playersCoordinates.size() == 0) {
            throw new IllegalArgumentException("there is no target for fox");
        }
        int rand = (int) (Math.random() * 10) % playersCoordinates.size();
        System.out.println("fox rand: " + rand + " " + field.getItem(playersCoordinates.get(rand)).getOwner());
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

        Stack<Coordinates> path = GL.getPath(quoridorField.getCoordinates(Owner.FOX), targetCoordinates);

        if (path.empty()) {
            if (fox.getCore().getPossibleMoves().size() == 0) {
                return new Command(CommandType.MARKER, quoridorField.getCoordinates(Owner.FOX));
            }
            int rand = (int)(Math.random() * 10) % fox.getCore().getPossibleMoves().size();
            return new Command(CommandType.MARKER, fox.getCore().getPossibleMoves().get(rand));
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
