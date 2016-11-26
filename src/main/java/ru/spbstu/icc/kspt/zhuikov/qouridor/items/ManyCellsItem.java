package ru.spbstu.icc.kspt.zhuikov.qouridor.items;


import ru.spbstu.icc.kspt.zhuikov.qouridor.Coordinates;

import java.util.ArrayList;
import java.util.List;

abstract public class ManyCellsItem extends Item {

    protected List<Coordinates> coordinates = new ArrayList<Coordinates>();

    public ManyCellsItem() { }

    public List<Coordinates> getCoordinates() {
        return coordinates;
    } //todo может return new ArrayList<Coordinates>.addAll(coordinates);

}
