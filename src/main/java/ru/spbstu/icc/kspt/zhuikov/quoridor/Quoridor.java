package ru.spbstu.icc.kspt.zhuikov.quoridor;


import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.FieldItemException;
import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.NoBarriersException;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.BarrierPosition;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Coordinates;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Owner;
import ru.spbstu.icc.kspt.zhuikov.quoridor.player.*;
import ru.spbstu.icc.kspt.zhuikov.quoridor.returningClasses.Field;

import java.util.List;

/**
 * Класс для предоставления функциональности игры "Коридор".
 */
public class Quoridor {

    /**
     * Ядро игры.
     */
    private QuoridorCore core;

    /**
     * Игровое поле.
     */
    private QuoridorQueue queue;

    /**
     * Конструктор игры.
     * @param bot - флаг, несущий информацию о необходимости бота в игре;
     *            если равен true, один из игроков будет ботом,
     *            в обратном случае обоих игроков сможет контролировать пользователь.
     */
    public Quoridor(boolean bot) {

        core  = new QuoridorCore();
        queue = new QuoridorQueue(core);
        core.setQueue(queue);

        if (bot) {
            queue.addPlayer(new HumanPlayer(core, PlayerPosition.BOT));
            queue.addPlayer(new BotPlayer(core, PlayerPosition.TOP));
        } else {
            queue.addPlayer(new HumanPlayer(core, PlayerPosition.BOT));
            queue.addPlayer(new HumanPlayer(core, PlayerPosition.TOP));
        }
    }

    /**
     * Метод, запускающий игру.
     */
    public void launchGame() {
        queue.moveNextPlayer();
    }

    /**
     * Передвигает фишку текущего игрока на клетку поля с указанными параметрами.
     * @param vertical - вертикальная координата клетки.
     * @param horizontal - горизонатальная кордината клетки.
     * @throws FieldItemException при невозможном передвижении фишки; исключение содержит информацию о нарушеных правилах.
     */
    public void moveMarker(int vertical, int horizontal) throws FieldItemException {
        core.moveMarker(new Coordinates(vertical, horizontal));
    }

    /**
     * Устанавливает перегородку текущего игрока на клетку поля с указанными координатами.
     * @param vertical - вертикальная координата клетки.
     * @param horizontal - горизонатальная кордината клетки.
     * @param position - позиция размещения перегородки.
     * @throws FieldItemException при невозможном размещении перегородки на поле.
     * @throws NoBarriersException при отсутствии перегородок у текущего игрока.
     */
    public void placeBarrier(int vertical, int horizontal, BarrierPosition position)
            throws FieldItemException, NoBarriersException {
        core.placeBarrier(new Coordinates(vertical, horizontal), position);
    }

    /**
     * Добавляет "слушателя" конца игры.
     * @param listener - добавляемый "слушатель", реализующий необходимый интерфейс.
     * @see WinnerListener;
     */
    public void addWinnerListener (WinnerListener listener) {
        queue.addWinnerListener(listener);
    }

    /**
     * Возвращает владельца текущего игрока.
     */
    public Owner getCurrentOwner() {
        return queue.getCurrentPlayer().getOwner();
    }

    /**
     * Возвращает текущий шаг игры.
     */
    public int getStep() {
        return  queue.getStep();
    }

    /**
     * Возвращает шаг, на котором появится лиса.
     */
    public static int getFoxTime() {
        return QuoridorQueue.getFoxTime();
    }

    /**
     * Возвращает частоту хода лисы.
     */
    public static int getFoxFrequency() {
        return QuoridorQueue.getFoxFrequency();
    }

    /**
     * Возвращает неизменяемое поле игры.
     */
    public Field getField() { return core.getConstantField(); }

    /**
     * Возвращает список координат, на которые текущему игроку возможно передвинуть фишку.
     */
    public List<Coordinates> getPossibleMoves() {
        return core.getPossibleMoves();
    }

    /**
     * Возвращает число перегородок у заданного владельца.
     * @param owner - владелец, для которого необходимо вернуть количество перегородок.
     */
    public int getBarriersNumber(Owner owner) {
        return core.getBarriersNumber(owner);
    }

    /**
     * Устанавливает шаг, на котором появляется лиса.
     * @param foxTime - целое, неотрицательное число - шаг пояления лисы на поле.
     */
    public static void setFoxTime(int foxTime) {
        QuoridorQueue.setFoxTime(foxTime);
    }

    /**
     * Устанавливает частоту хода лисы.
     * @param foxTurn - натуральное число - частота хода лисы.
     */
    public static void setFoxFrequency(int foxTurn) {
        QuoridorQueue.setFoxFrequency(foxTurn);
    }

}
