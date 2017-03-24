package ru.spbstu.icc.kspt.zhuikov.quoridor.returningClasses;

import ru.spbstu.icc.kspt.zhuikov.quoridor.game.QuoridorField;

/**
 * Класс, представляющий возвращаемое поле.
 * Не содержит методы, позволяющие размещать элементы.
 */
public class Field {

    /**
     * Двумерный массив клеток поля. Имеет размерность realSize x realSize.
     * @see Cell;
     */
    private final Cell[][] cells;

    /**
     * Настоящий размер поля. Равен (size * 2 - 1), где size - размер поля в клетках.
     */
    private final int realSize;

    /**
     * Размер поля в клетках.
     */
    private final int size;

    /**
     * Конструктор возвращаемого поля.
     * @param field - настоящее поле игры.
     */
    public Field(QuoridorField field) {

        cells = new Cell[field.getRealSize()][field.getRealSize()];
        realSize = field.getRealSize();
        size = field.getSize();

        for (int i = 0; i < field.getRealSize(); i++) {
            for (int j = 0; j < field.getRealSize(); j++) {
                cells[i][j] = new Cell(field.getItem(i, j).getType(), field.getItem(i, j).getOwner());
            }
        }
    }

    /**
     * Возвращает настоящий размер поля.
     */
    public int getRealSize() {  //TODO можно мне нереальный размер ( предлагаю переименовать в getSize() )
        return realSize;
    }

    /**
     * Возвращает размер поля в клетках.
     */
    public int getSize() {
        return size;
    }

    /**
     * Возвращает клетку с указанными координатами.
     * @param vertical - вертикальная координата возвращаемой клетки.
     * @param horizontal - горизонтальная координата возвращаемой клетки.
     */
    public Cell getCell(int vertical, int horizontal) {  //TODO возможно некорректное поведение этой структуры, при изменение получаемого объекта
        return cells[vertical][horizontal];
    }

}
