package ru.spbstu.icc.kspt.zhuikov.quoridor.core.exceptions;

import ru.spbstu.icc.kspt.zhuikov.quoridor.core.player.PlayerPosition;

/**
 * Исключение, генерирующееся при попытке установить перегородку на поле, не имея их.
 */
public class NoBarriersException extends Exception {

    /**
     * Позиция игрока, не имеющего переродки.
     */
    private PlayerPosition position;

    /**
     * Конструктор исключения.
     * @param s - сообщение об ошибке.
     * @param position - позиция игрока, не имеющего перегородки.
     */
    public NoBarriersException(String s, PlayerPosition position) {
        super(s);
        this.position = position;
    }

    /**
     * Возвращает позицию игрока, не имеющего перегородки.
     */
    public PlayerPosition getPosition() {
        return position;
    }

}
