package ru.spbstu.icc.kspt.zhuikov.quoridor.items;


import java.util.ArrayList;
import java.util.List;

public class Barrier extends Item {

    public final static int length = 2;
    private BarrierPosition position;
    private List<Coordinates> coordinates = new ArrayList<>();

    public Barrier(Coordinates coordinates, BarrierPosition position) {
        this.type = ItemType.BARRIER;
        this.position = position;

        if (position == BarrierPosition.VERTICAL) {
            for (int i = coordinates.getVertical() - length + 1; i <= coordinates.getVertical() + length - 1; i++) {
                this.coordinates.add(new Coordinates(i, coordinates.getHorizontal()));
            }
        } else if (position == BarrierPosition.HORIZONTAL) {
            for (int i = coordinates.getHorizontal() - length + 1; i <= coordinates.getHorizontal() + length - 1; i++) {
                this.coordinates.add(new Coordinates(coordinates.getVertical(), i));
            }
        }

//        if (coordinates.size() != realLength) {
//            throw new IllegalArgumentException("too long barrier. Its size must be equals to " + length);
//        }
    }

    public List<Coordinates> getCoordinates() {
        return coordinates;
    }

    public BarrierPosition getPosition() { return position; }

}
