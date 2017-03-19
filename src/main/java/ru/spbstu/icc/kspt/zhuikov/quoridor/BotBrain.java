package ru.spbstu.icc.kspt.zhuikov.quoridor;


import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.FieldBoundsException;
import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.FieldItemException;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.BarrierPosition;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Coordinates;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.ItemType;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Owner;
import ru.spbstu.icc.kspt.zhuikov.quoridor.player.BotPlayer;

import java.util.Stack;

public class BotBrain extends Brain {

    private BotPlayer player;
    private Owner opponent = null;

    public BotBrain(QuoridorField quoridorField, BotPlayer player) {
        this.quoridorField = quoridorField;
        GL = new GameLogic(quoridorField);
        this.player = player;
    }

    public Command whatToDo() {

        if (opponent == null) {
            for (Coordinates c : quoridorField.getUsualPlayerMarkers()) {
                if (quoridorField.getItem(c).getOwner() != player.getOwner()) {
                    opponent = quoridorField.getItem(c).getOwner();
                }
            }
        }

        double rand = Math.random();

        Command command = moveMarker();
        if (rand > 0.17 && player.getBarriersNumber() > 0) {
            try {
                command = placeBarrier();
            } catch (FieldItemException | FieldBoundsException e) { }
        }

        return command;
    }

    private Command placeBarrier() throws FieldItemException {

        Coordinates opponentCoordinates = new Coordinates(-1, -1);
        for (Coordinates c : quoridorField.getUsualPlayerMarkers()) {
            if (quoridorField.getItem(c).getOwner() == opponent) {
                opponentCoordinates = c;
            }
        }

        Stack<Coordinates> opponentsPath = GL.getPathToRow(opponentCoordinates,
                GL.getPlayerPosition(opponent).getDestinationRow());

        Coordinates between = new Coordinates((opponentsPath.peek().getVertical() + opponentCoordinates.getVertical()) / 2,
                (opponentsPath.peek().getHorizontal() + opponentCoordinates.getHorizontal()) / 2);

        double rand = Math.random();
        if (rand < 0.5) {
            Coordinates dest = new Coordinates(between.getVertical() % 2 == 0 ? between.getVertical() - 1 : between.getVertical(),
                    between.getHorizontal() % 2 == 0 ? between.getHorizontal() - 1 : between.getHorizontal());
            BarrierPosition barrierPosition = rand > 0.15 ? BarrierPosition.HORIZONTAL : BarrierPosition.VERTICAL;
            GL.checkBarrier(dest, barrierPosition);

            return new Command(CommandType.BARRIER, dest, barrierPosition);
        }

        Coordinates dest = new Coordinates(between.getVertical() % 2 == 0 ? between.getVertical() + 1 : between.getVertical(),
                between.getHorizontal() % 2 == 0 ? between.getHorizontal() + 1 : between.getHorizontal());
        BarrierPosition barrierPosition = rand > 0.65 ? BarrierPosition.HORIZONTAL : BarrierPosition.VERTICAL;
        GL.checkBarrier(dest, barrierPosition);

        return new Command(CommandType.BARRIER, dest, barrierPosition);

    }

    private Command moveMarker() {

        Stack<Coordinates> path = GL.getPathToRow(quoridorField.getCoordinates(player.getOwner()),
                player.getPosition().getDestinationRow());

        if (quoridorField.getItem(path.peek()).getType() != ItemType.EMPTY) {
            path.pop();
        }
        return new Command(CommandType.MARKER, path.peek());
    }

}
