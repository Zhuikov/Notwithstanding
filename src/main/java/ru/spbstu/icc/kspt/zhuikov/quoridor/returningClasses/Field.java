package ru.spbstu.icc.kspt.zhuikov.quoridor.returningClasses;

import ru.spbstu.icc.kspt.zhuikov.quoridor.QuoridorField;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Barrier;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Coordinates;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.ItemType;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Owner;

import java.util.ArrayList;
import java.util.List;

public class Field {

    private final Cell[][] cells;
    private final int realSize;
    private final int size;
    private List<Coordinates> markers = new ArrayList<>();

    public Field(QuoridorField field) {

        cells = new Cell[field.getRealSize()][field.getRealSize()];
        realSize = field.getRealSize();
        size = field.getSize();

        for (int i = 0; i < field.getRealSize(); i++) {
            for (int j = 0; j < field.getRealSize(); j++) {
                cells[i][j] = new Cell(field.getItem(i, j).getType(), field.getItem(i, j).getOwner());
                if (field.getItem(i, j).getType() == ItemType.MARKER) {
                    markers.add(new Coordinates(i, j));
                }
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

    public ItemType getItemType(int vertical, int horizontal) {
        return cells[vertical][horizontal].getType();
    }

    public Owner getItemOwner(int vertical, int horizontal) {
        return cells[vertical][horizontal].getOwner();
    }

    public List<Coordinates> getMarkers() {
        return markers;
    }

    public void setBarrier(Barrier barrier) {
        for (Coordinates coordinates : barrier.getCoordinates()) {
            cells[coordinates.getVertical()][coordinates.getHorizontal()] = new Cell(ItemType.BARRIER, Owner.NOBODY);
        }
    }
}
