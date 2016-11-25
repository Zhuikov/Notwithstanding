package ru.spbstu.icc.kspt.zhuikov.qouridor.items;


import ru.spbstu.icc.kspt.zhuikov.qouridor.Coordinates;

import java.util.ArrayList;
import java.util.List;

abstract public class ManyCellsItem extends Item {

    protected List<Coordinates> coordinates = new ArrayList<Coordinates>();

    public ManyCellsItem(List<Coordinates> coordinates) { this.coordinates.addAll(coordinates); }

    public List<Coordinates> getCoordinates() { return coordinates; }

}
