package ru.spbstu.icc.kspt.zhuikov.quoridor;


import ru.spbstu.icc.kspt.zhuikov.quoridor.items.BarrierPosition;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Coordinates;

public class RulesController {

    private QuoridorField field;

    public RulesController(QuoridorField field) {
        this.field = field;
    }

    public boolean checkMarker(Coordinates from, Coordinates dest) {

        //todo: here;
        return true;
    }

    public boolean checkBarrier(Coordinates dest, BarrierPosition position) {

        //todo: here
        return true;
    }

}
