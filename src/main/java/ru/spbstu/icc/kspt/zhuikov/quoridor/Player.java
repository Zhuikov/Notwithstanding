package ru.spbstu.icc.kspt.zhuikov.quoridor;

import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Empty;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Marker;

abstract public class Player {

    protected Marker marker;
    protected QuoridorField field;

    protected void setMarker(int vertical, int horizontal) {

        field.setItem(new Empty(marker.getCoordinates().getVertical(), marker.getCoordinates().getHorizontal()));
        marker.moveTo(vertical, horizontal);
        field.setItem(marker);
    }

    public Coordinates getCoordinates() { return marker.getCoordinates(); }
}
