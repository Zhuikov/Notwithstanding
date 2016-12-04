package ru.spbstu.icc.kspt.zhuikov.quoridor.items;


import ru.spbstu.icc.kspt.zhuikov.quoridor.Coordinates;

abstract public class OneCellItem extends Item {

    protected Coordinates coordinates = new Coordinates(0, 0);

    public OneCellItem(int vertical, int horizontal) {
        this.coordinates.setVertical(vertical);
        this.coordinates.setHorizontal(horizontal);
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }
}
