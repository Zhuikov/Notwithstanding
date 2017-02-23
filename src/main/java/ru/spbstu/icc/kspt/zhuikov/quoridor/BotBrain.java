package ru.spbstu.icc.kspt.zhuikov.quoridor;


import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.FieldItemException;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.BarrierPosition;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Coordinates;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.ItemType;
import ru.spbstu.icc.kspt.zhuikov.quoridor.player.BotPlayer;

import java.util.Stack;

public class BotBrain extends Brain {

    private final GameLogic GL;
    private BotPlayer player;
    private int barrierNumber;

    public BotBrain(QuoridorField field, BotPlayer player) {
        this.field = field;
        GL = new GameLogic(field);
        this.player = player;
        barrierNumber = Quoridor.getStartBarrierNumber();
    }

    public Command whatToDo() {

        double rand = Math.random();

        if (rand > 0.47 && barrierNumber > 0) {
            barrierNumber--; // todo: тут считается параллельно ядру
            try {
                return placeBarrier();
            } catch (FieldItemException e) {
                return moveMarker();
            }
        }

        return moveMarker();
    }

    private Command placeBarrier() throws FieldItemException {

        Stack<Coordinates> opponentsPath = GL.getPathToRow(player.getOpponent().getCoordinates(),
                player.getOpponent().getPosition().getDestinationRow());

        Coordinates between = new Coordinates((opponentsPath.peek().getVertical() + player.getOpponent().getCoordinates().getVertical()) / 2,
                (opponentsPath.peek().getHorizontal() + player.getOpponent().getCoordinates().getHorizontal()) / 2);

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

        if (field.getItem(path.peek().getVertical(), path.peek().getHorizontal()).getType() != ItemType.EMPTY) {
            path.pop();
        }
        return new Command(CommandType.MARKER, path.peek());
    }

}
