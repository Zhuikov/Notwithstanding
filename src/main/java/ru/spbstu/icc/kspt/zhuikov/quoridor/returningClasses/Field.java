package ru.spbstu.icc.kspt.zhuikov.quoridor.returningClasses;

import ru.spbstu.icc.kspt.zhuikov.quoridor.QuoridorField;

public class Field {

    private final Cell[][] cells;
    private final int realSize;

    public Field(QuoridorField field) {

        cells = new Cell[field.getRealSize()][field.getRealSize()];
        realSize = field.getRealSize();

        for (int i = 0; i < field.getRealSize(); i++) {
            for (int j = 0; j < field.getRealSize(); j++) {
                cells[i][j] = new Cell(i, j, field.getItem(i, j).getType(), field.getColor(i, j), field.getItem(i, j).getOwner());
            }
        }
    }

    public int getRealSize() {  //TODO можно мне нереальный размер ( предлагаю переименовать в getSize() )
        return realSize;
    }

    public Cell getCell(int vertical, int horizontal) {  //TODO возможно некорректное поведение этой структуры, при изменение получаемого объекта
        return cells[vertical][horizontal];
    }
}
