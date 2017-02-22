package ru.spbstu.icc.kspt.zhuikov.quoridor;


import ru.spbstu.icc.kspt.zhuikov.quoridor.items.BarrierPosition;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Coordinates;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.ItemType;
import ru.spbstu.icc.kspt.zhuikov.quoridor.player.BotPlayer;

import java.util.Stack;

public class BotBrain extends Brain {

    private final RulesController RC;
    private BotPlayer player;
    private int barrierNumber;

    public BotBrain(QuoridorField field, BotPlayer player) {
        this.field = field;
        RC = new RulesController(field);
        this.player = player;
        barrierNumber = Quoridor.getStartBarrierNumber();
    }

    public Command whatToDo() {

        double rand = Math.random();
        if (rand > 0.47 && barrierNumber > 0) {
            barrierNumber--; // todo: тут считается параллельно ядру
            return placeBarrier();

        }

        return moveMarker();

    }

    private Command placeBarrier() {

        Stack<Coordinates> opponentsPath = getPathToRow(player.getOpponent().getCoordinates(),
                player.getOpponent().getPosition().getDestinationRow());

        Coordinates between = new Coordinates((opponentsPath.peek().getVertical() + player.getOpponent().getCoordinates().getVertical()) / 2,
                (opponentsPath.peek().getHorizontal() + player.getOpponent().getCoordinates().getHorizontal()) / 2);

        double rand = Math.random();
        if (rand < 0.5) {
            Coordinates dest = new Coordinates(between.getVertical() % 2 == 0 ? between.getVertical() - 1 : between.getVertical(),
                    between.getHorizontal() % 2 == 0 ? between.getHorizontal() - 1 : between.getHorizontal());
            BarrierPosition barrierPosition = rand > 0.15 ? BarrierPosition.HORIZONTAL : BarrierPosition.VERTICAL;
            RC.checkBarrier(dest, barrierPosition);

            return new Command(CommandType.BARRIER, dest, barrierPosition);
        }

        Coordinates dest = new Coordinates(between.getVertical() % 2 == 0 ? between.getVertical() + 1 : between.getVertical(),
                between.getHorizontal() % 2 == 0 ? between.getHorizontal() + 1 : between.getHorizontal());
        BarrierPosition barrierPosition = rand > 0.65 ? BarrierPosition.HORIZONTAL : BarrierPosition.VERTICAL;
        RC.checkBarrier(dest, barrierPosition);

        return new Command(CommandType.BARRIER, dest, barrierPosition);

    }

    /**
     * Возвращает кратчайший путь из точки на поле до заданного ряда.
     * Длина пути всегда больше либо равна двум. // todo: ...
     * @param start - координаты точки
     * @param destinationRow - ряд, до которого ищется путь
     * @return стек координат - кратчайший путь
     */
    private Stack<Coordinates> getPathToRow(Coordinates start, int destinationRow) {

        Stack<Coordinates> path = getPath(start, new Coordinates(destinationRow, 0));
        int min = 10000000;
        for (int i = 0; i <= field.getRealSize(); i+=2) {
            if (field.getItem(destinationRow, i).getType() == ItemType.EMPTY) {
                Stack<Coordinates> temp = getPath(start, new Coordinates(destinationRow, i));
                if (temp.size() < min && temp.size() != 0) {
                    path = temp;
                    min = path.size();
                }
            }
        }

        return path;
    }

    private Command moveMarker() {

        Stack<Coordinates> path = getPathToRow(player.getCoordinates(), player.getPosition().getDestinationRow());

        if (field.getItem(path.peek().getVertical(), path.peek().getHorizontal()).getType() != ItemType.EMPTY) {
            path.pop();
        }
        return new Command(CommandType.MARKER, path.peek());
    }

}
