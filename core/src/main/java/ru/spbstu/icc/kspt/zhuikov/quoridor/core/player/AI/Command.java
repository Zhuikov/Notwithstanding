package ru.spbstu.icc.kspt.zhuikov.quoridor.core.player.AI;


import ru.spbstu.icc.kspt.zhuikov.quoridor.core.items.BarrierPosition;
import ru.spbstu.icc.kspt.zhuikov.quoridor.core.items.Coordinates;

/**
 * Класс, представляющий команду для выполнения.
 * Используется игроками с искуственным интеллектом для выполнения хода.
 */
public class Command {

    /**
     * Тип команды.
     */
    private CommandType commandType;

    /**
     * Координаты, куда необходимо сделать ход фишкой или разместить перегородку.
     */
    private Coordinates destination;

    /**
     * Позиция, с которой необходимо размещать перегородку.
     * Если тип команды не равен CommandType.BARRIER, поле принимает значение null.
     */
    private BarrierPosition barrierPosition = null;

    /**
     * Возвращает тип команды.
     */
    public CommandType getCommandType() {
        return commandType;
    }

    /**
     * Возвращает координаты для совершения хода.
     */
    public Coordinates getDestination() {
        return destination;
    }

    /**
     * Возвращает позицию размещения перегородки.
     */
    public BarrierPosition getBarrierPosition() {
        return barrierPosition;
    }

    /**
     * Конструктор, использующийся для создания команды перемещения фишки по полю.
     * @param destination - координаты для осуществления хода фишкой.
     */
    public Command(Coordinates destination) {
        this.commandType = CommandType.MARKER;
        this.destination = destination;
    }

    /**
     * Конструктор, использующийся для создания команды размещения перегородки на поле.
     * @param destination - координаты центра перегородки.
     * @param barrierPosition - позиция размещения перегородки на поле.
     */
    public Command(Coordinates destination, BarrierPosition barrierPosition) {
        this.commandType = CommandType.BARRIER;
        this.destination = destination;
        this.barrierPosition = barrierPosition;
    }
}