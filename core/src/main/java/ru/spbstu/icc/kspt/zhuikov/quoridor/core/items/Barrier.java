package ru.spbstu.icc.kspt.zhuikov.quoridor.core.items;


import java.util.ArrayList;
import java.util.List;

/**
 * Класс, представляющий объект перегородки в игре.
 */
public class Barrier extends Item {

    /**
     * Длина перегородки (в клетках на поле).
     */
    public final static int length = 2;

    /**
     * Совокупность координат, занимаемых перегородкой на поле.
     */
    private List<Coordinates> coordinates = new ArrayList<>();

    /**
     * Конструктор для перегородки.
     * @param coordinates - координаты центра перегородки.
     * Как вертикальная, так и горизонтальная координаты должны быть нечетными.
     * При указании других координат, перегородка будет создана, но поместить на поле ее будет невозможно.
     * @param position - позиция, показывающая каким образом перегородка должна размещаться на поле.
     */
    public Barrier(Coordinates coordinates, BarrierPosition position) {
        this.type = ItemType.BARRIER;

        if (position == BarrierPosition.VERTICAL) {
            for (int i = coordinates.getVertical() - length + 1; i <= coordinates.getVertical() + length - 1; i++) {
                this.coordinates.add(new Coordinates(i, coordinates.getHorizontal()));
            }
        } else if (position == BarrierPosition.HORIZONTAL) {
            for (int i = coordinates.getHorizontal() - length + 1; i <= coordinates.getHorizontal() + length - 1; i++) {
                this.coordinates.add(new Coordinates(coordinates.getVertical(), i));
            }
        }
    }

    /**
     * Возвращает список координат, занимаемых перегородкой на поле.
     */
    public List<Coordinates> getCoordinates() {
        return coordinates;
    }

}
