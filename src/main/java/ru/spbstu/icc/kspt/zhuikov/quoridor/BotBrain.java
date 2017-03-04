package ru.spbstu.icc.kspt.zhuikov.quoridor;


import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.FieldItemException;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.BarrierPosition;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Coordinates;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.ItemType;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Owner;
import ru.spbstu.icc.kspt.zhuikov.quoridor.player.BotPlayer;
import ru.spbstu.icc.kspt.zhuikov.quoridor.returningClasses.Field;

import java.util.Stack;

public class BotBrain extends Brain {

    private BotPlayer player;

    public BotBrain(Field field, BotPlayer player) {
        this.field = field;
        GL = new GameLogic(field);
        this.player = player;
    }

    public Command whatToDo(Field field) {

        this.field = field;
        GL.setField(field);

        double rand = Math.random();

        if (rand > 0.47 && player.getBarriersNumber() > 0) {
            try {
                return placeBarrier();
            } catch (FieldItemException e) {
                return moveMarker();
            }
        }

        return moveMarker();
    }

    private Command placeBarrier() throws FieldItemException {

        Coordinates opponentCoordinates = new Coordinates(-1, -1);
        Owner opponentsOwner = Owner.BOTTOM;

        for (Coordinates c : field.getMarkers()) {
            if (field.getItemOwner(c.getVertical(), c.getHorizontal()) != player.getOwner() &&
                    field.getItemOwner(c.getVertical(), c.getHorizontal()) != Owner.FOX) {
                opponentCoordinates = c;
                opponentsOwner = field.getItemOwner(c.getVertical(), c.getHorizontal());
            }
        }

        Stack<Coordinates> opponentsPath = GL.getPathToRow(opponentCoordinates,
                GL.getPlayerPosition(opponentsOwner).getDestinationRow());

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

        Stack<Coordinates> path = GL.getPathToRow(player.getCoordinates(), player.getPosition().getDestinationRow());

        if (field.getItemType(path.peek().getVertical(), path.peek().getHorizontal()) != ItemType.EMPTY) {
            path.pop();
        }
        return new Command(CommandType.MARKER, path.peek());
    }

}
