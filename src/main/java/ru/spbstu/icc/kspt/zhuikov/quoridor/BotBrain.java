package ru.spbstu.icc.kspt.zhuikov.quoridor;


import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.FieldBoundsException;
import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.FieldItemException;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.BarrierPosition;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Coordinates;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.ItemType;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Owner;
import ru.spbstu.icc.kspt.zhuikov.quoridor.player.BotPlayer;

import java.util.Stack;

/**
 * Класс, реализующий "мозг" для принятия решений, используемый ботом.
 */
public class BotBrain extends Brain {

    /**
     * Ссылка на игрока, использующего мозг.
     */
    private BotPlayer player;

    /**
     * Владелец игрока, являющегося оппонентом.
     */
    private Owner opponent = null;

    /**
     * Конструктор класса.
     * @param quoridorField - игровое поле.
     * @param player - игрок, использующий мозг.
     */
    public BotBrain(QuoridorField quoridorField, BotPlayer player) {
        this.quoridorField = quoridorField;
        GL = new GameLogic(quoridorField);
        this.player = player;
    }

    /**
     * Возвращает команду, которую необходимо выполнить для следующего хода.
     * @see Command;
     */
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
        if (rand > 0.47 && player.getBarriersNumber() > 0) {
            try {
                command = placeBarrier();
            } catch (FieldItemException | FieldBoundsException e) { }
        }

        return command;
    }

    /**
     * Возвращает команду, необходимую для размещения перегододки на поле.
     * @throws FieldItemException при невозможном размещении перегородки на поле.
     */
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

            return new Command(dest, barrierPosition);
        }

        Coordinates dest = new Coordinates(between.getVertical() % 2 == 0 ? between.getVertical() + 1 : between.getVertical(),
                between.getHorizontal() % 2 == 0 ? between.getHorizontal() + 1 : between.getHorizontal());
        BarrierPosition barrierPosition = rand > 0.65 ? BarrierPosition.HORIZONTAL : BarrierPosition.VERTICAL;
        GL.checkBarrier(dest, barrierPosition);

        return new Command(dest, barrierPosition);

    }

    /**
     * Возвращает команду, необходимую для хода фишкой по полю.
     */
    private Command moveMarker() {

        Stack<Coordinates> path = GL.getPathToRow(quoridorField.getCoordinates(player.getOwner()),
                player.getPosition().getDestinationRow());

        if (quoridorField.getItem(path.peek()).getType() != ItemType.EMPTY) {
            path.pop();
        }
        return new Command(path.peek());
    }

}
