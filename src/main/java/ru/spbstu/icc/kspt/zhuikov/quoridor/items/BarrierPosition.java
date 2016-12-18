package ru.spbstu.icc.kspt.zhuikov.quoridor.items;


public enum BarrierPosition {

    VERTICAL,
    HORIZONTAL;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
