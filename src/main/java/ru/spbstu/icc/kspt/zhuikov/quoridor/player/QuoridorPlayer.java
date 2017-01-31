package ru.spbstu.icc.kspt.zhuikov.quoridor.player;

import ru.spbstu.icc.kspt.zhuikov.quoridor.QuoridorField;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Empty;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Marker;

abstract public class QuoridorPlayer {

    protected Marker marker;
    protected QuoridorField field;

    protected void setMarker(int vertical, int horizontal) {

        field.setItem(new Empty(marker.getCoordinates().getVertical(), marker.getCoordinates().getHorizontal()));
        marker.moveTo(vertical, horizontal);
        field.setItem(marker);
    }

    public Marker getMarker() {
        return marker;
    }
}
