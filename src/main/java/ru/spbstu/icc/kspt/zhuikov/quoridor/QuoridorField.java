package ru.spbstu.icc.kspt.zhuikov.quoridor;


import ru.spbstu.icc.kspt.zhuikov.quoridor.items.*;

import java.util.*;

public class QuoridorField {

    private final int realSize;
    private final int size;
    private Item[][] field;

    public int getRealSize() {  //TODO не вижу смысла, в слове Real, юезр поля не должен знать, что тут творится (у тебя же нет другого метода получения размера поля), мне бы больше понравился простой getSize()
        return realSize;
    }

    public int getSize() {
        return size;
    }

    public QuoridorField(int size) {

        this.size = size;
        realSize = size * 2 - 1;
        field = new Item[realSize][realSize];

        for (int i = 0; i <= realSize - 1; i++) {
            for (int j = 0; j <= realSize - 1; j++) {
                field[i][j] = new Empty();
            }
        }
    }

    public void setItem(Item item, Coordinates destination) {
        field[destination.getVertical()][destination.getHorizontal()] = item;
    }

    public void setBarrier(Barrier barrier) {
        for (Coordinates coordinates : barrier.getCoordinates()) {
            field[coordinates.getVertical()][coordinates.getHorizontal()] = barrier;
        }
    }

    public Item getItem(int vertical, int horizontal) {

        return field[vertical][horizontal];
    }

}
