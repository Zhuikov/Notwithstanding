package ru.spbstu.icc.kspt.zhuikov.qouridor;


import ru.spbstu.icc.kspt.zhuikov.qouridor.items.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Field {

    private final int realSize;
    private Cell[][] field;

    public Field(int size) {

        realSize = size * 2 - 1;
        field = new Cell[realSize][realSize];
        for (int i = 0; i < realSize - 1; i++) {
            for (int j = 0; j < realSize - 1; j++) {
                if ((i % 2 == 0) && (j % 2 == 0)) {
                    field[i][j] = new Cell(CellColor.WHITE, new Empty(i, j));
                } else {
                    field[i][j] = new Cell(CellColor.BLACK, new Empty(i, j));
                }
            }
        }

    }

    private class Cell {
        private CellColor color;
        private Item item;

        Cell(CellColor color, Item item) {
            this.color = color;
            this.item = item;
        }
    }

    public void setItem(OneCellItem item) {
        field[item.getCoordinates().getVertical()][item.getCoordinates().getHorizontal()].item = item;
    }

    public void setItem(ManyCellsItem item) {
        for (Coordinates coordinates : item.getCoordinates()) {
            field[coordinates.getVertical()][coordinates.getHorizontal()].item = item;
        }
    }

    public Item getItem(int vertical, int horizontal) {
        return field[vertical][horizontal].item;
    }

    public CellColor getColor(int vertical, int horizontal) {
        return field[vertical][horizontal].color;
    }


    public boolean foo(Coordinates marker, int rowNumber) { // todo: rename

        boolean used[][] = new boolean[realSize][realSize];
        Queue<Coordinates> queue = new LinkedList<Coordinates>();

        queue.add(marker);

        while (!queue.isEmpty()) {

//          for (Coordinates coordinates : queue) {
//              System.out.print(coordinates + " ");
//          }
//          System.out.println();

            if (queue.element().getVertical() == rowNumber) {
                return true;
            }

            for (Coordinates neighbour : getNeighbours(queue.element())) {
                try {
                    if (!used[neighbour.getVertical()][neighbour.getHorizontal()] &&   // todo: шлифануть бы тут
                            getItem((queue.element().getVertical() + neighbour.getVertical()) / 2,
                                    (queue.element().getHorizontal() + neighbour.getHorizontal()) / 2).getType() != ItemType.BARRIER &&
                            !queue.contains(new Coordinates(neighbour.getVertical(), neighbour.getHorizontal()))) {
                        queue.add(neighbour);
                    }
                } catch (ArrayIndexOutOfBoundsException e) { }
            }

            used[queue.element().getVertical()][queue.element().getHorizontal()] = true;
            queue.remove();
        }

        return false;
    }

    private List<Coordinates> getNeighbours(Coordinates init) {

        List<Coordinates> neighbours = new ArrayList<Coordinates>();

        neighbours.add(new Coordinates(init.getVertical() - 2, init.getHorizontal()));
        neighbours.add(new Coordinates(init.getVertical(), init.getHorizontal() - 2));
        neighbours.add(new Coordinates(init.getVertical() + 2, init.getHorizontal()));
        neighbours.add(new Coordinates(init.getVertical(), init.getHorizontal() + 2));

        return neighbours;
    }


}
