package ru.spbstu.icc.kspt.zhuikov.quoridor.items;


import ru.spbstu.icc.kspt.zhuikov.quoridor.Coordinates;

abstract public class OneCellItem extends Item {

    protected Coordinates coordinates = new Coordinates(0, 0);

    public OneCellItem(int vertical, int horizontal) {
        this.coordinates.setVertical(vertical);          //TODO зачем? проще создавать объект в конструкторе
        this.coordinates.setHorizontal(horizontal);
    }

    public Coordinates getCoordinates() {    //TODO возвращять копию, хотя если её изменить класс Coordinates, то и так нормально
        return coordinates;
    }
}
