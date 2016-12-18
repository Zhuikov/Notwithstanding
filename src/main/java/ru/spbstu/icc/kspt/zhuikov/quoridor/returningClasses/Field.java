package ru.spbstu.icc.kspt.zhuikov.quoridor.returningClasses;

import ru.spbstu.icc.kspt.zhuikov.quoridor.QuoridorField;

public class Field {

    private final Cell[][] cells;
    private final int realSize;
    private final int size;

    public Field(QuoridorField field) {

        cells = new Cell[field.getRealSize()][field.getRealSize()];
        realSize = field.getRealSize();
        size = field.getSize();

        for (int i = 0; i < field.getRealSize(); i++) {
            for (int j = 0; j < field.getRealSize(); j++) {
                cells[i][j] = new Cell(i, j, field.getItem(i, j).getType(), field.getColor(i, j), field.getItem(i, j).getOwner());
            }
        }
    }

    public int getRealSize() {  //TODO можно мне нереальный размер ( предлагаю переименовать в getSize() )
        return realSize;
    }

    public int getSize() {
        return size;
    }

    public Cell getCell(int vertical, int horizontal) {  //TODO возможно некорректное поведение этой структуры, при изменение получаемого объекта
        return cells[vertical][horizontal];
    }
}
