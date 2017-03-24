package ru.spbstu.icc.kspt.zhuikov.quoridor.player;


import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Coordinates;

/**
 * Перечисление, представляющее позицию игрока на поле.
 * Содержит информацию об игроке:
 * ---- Начальное положение фишки на поле;
 * ---- Ряд, до которого необходимо добраться для победы в игре;
 * ---- Владелец элементов на поле;
 *
 * Позицию на поле может иметь только обычный игрок.
 */
public enum PlayerPosition {

    /**
     * Позиция с начальными координатами [0, 8].
     */
    TOP(0, 8, 16, Owner.TOP),

    /**
     * Позиция с начальными координатами [16, 8].
     */
    BOT(16, 8, 0, Owner.BOTTOM);

    /**
     * Начальная вертикальная координата фишки игрока на поле.
     */
    private final int initialVertical;

    /**
     * Начальная горизонтальная координата фишки игрока на поле.
     */
    private final int initialHorizontal;

    /**
     * Ряд, до которого должен добраться игрок для победы в игре.
     */
    private final int destinationRow;

    /**
     * Владелец элементов на поле, связанный с позицией игрока.
     */
    private final Owner owner;

    PlayerPosition(int initialVertical, int initialHorizontal, int destinationRow, Owner owner) {
        this.initialVertical = initialVertical;
        this.initialHorizontal = initialHorizontal;
        this.destinationRow = destinationRow;
        this.owner = owner;
    }

    /**
     * Возвращает владельца, связанного с позицией игрока.
     */
    public Owner getOwner() { return owner; }

    /**
     * Возвращает начальные координаты фишки игрока.
     */
    public Coordinates getInitialCoordinates() { return new Coordinates(initialVertical, initialHorizontal); }

    /**
     * Возвращает номер ряда на поле, необходимого для победы игроку.
     */
    public int getDestinationRow() {
        return destinationRow;
    }
}
