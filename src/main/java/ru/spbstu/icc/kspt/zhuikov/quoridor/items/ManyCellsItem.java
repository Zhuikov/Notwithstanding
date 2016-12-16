package ru.spbstu.icc.kspt.zhuikov.quoridor.items;


import ru.spbstu.icc.kspt.zhuikov.quoridor.Coordinates;

import java.util.ArrayList;
import java.util.List;

abstract public class ManyCellsItem extends Item {

    protected List<Coordinates> coordinates = new ArrayList<Coordinates>();

    public ManyCellsItem() { }

    public List<Coordinates> getCoordinates() {       //TODO по моему, не правильно отдавать поле (возвращять нужно скорее копию)
        return coordinates;
    } //todo может return new ArrayList<Coordinates>.addAll(coordinates);
}
