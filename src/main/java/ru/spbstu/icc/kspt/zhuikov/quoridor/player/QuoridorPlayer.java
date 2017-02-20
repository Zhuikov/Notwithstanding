package ru.spbstu.icc.kspt.zhuikov.quoridor.player;

import ru.spbstu.icc.kspt.zhuikov.quoridor.QuoridorField;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Coordinates;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Empty;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Marker;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Owner;

abstract public class QuoridorPlayer {

    protected Coordinates markerCoordinates;
    protected QuoridorField field;
    protected Owner owner;

    protected void setMarker(Coordinates coordinates, Owner owner) {

        field.setItem(new Empty(), markerCoordinates);
        markerCoordinates = coordinates;
        field.setItem(new Marker(owner), markerCoordinates);
    }

    public Coordinates getCoordinates() {
        return markerCoordinates;
    }
}
