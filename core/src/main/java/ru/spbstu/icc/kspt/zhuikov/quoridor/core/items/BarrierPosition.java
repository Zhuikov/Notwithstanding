package ru.spbstu.icc.kspt.zhuikov.quoridor.core.items;

/**
 * Перечисление, представляющее возможные варианты расположения перегородки на поле.
 */
public enum BarrierPosition {

    /**
     * Вертикальное размещение перегородки.
     */
    VERTICAL,

    /**
     * Горизонтальное размещение перегородки.
     */
    HORIZONTAL;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
