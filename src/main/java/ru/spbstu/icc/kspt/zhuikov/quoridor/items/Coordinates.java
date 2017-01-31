package ru.spbstu.icc.kspt.zhuikov.quoridor.items;


public class Coordinates {

    private final int vertical;
    private final int horizontal;

    public static double pathBetween(Coordinates coordinates1, Coordinates coordinates2) {

        //TODO конечно бред, но можно выделить private метод, возведения в квадрат, по крайней мере, понятность кода увеличится
        return Math.sqrt((coordinates1.getVertical() - coordinates2.getVertical()) *
                         (coordinates1.getVertical() - coordinates2.getVertical()) +
                        (coordinates1.getHorizontal() - coordinates2.getHorizontal()) *
                        (coordinates1.getHorizontal() - coordinates2.getHorizontal()));
    }

    public int getVertical() {
        return vertical;
    }

    public int getHorizontal() {
        return horizontal;
    }

    public Coordinates(int vertical, int horizontal) {
        this.horizontal = horizontal;
        this.vertical = vertical;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("[")
                .append(vertical)
                .append(" ")
                .append(horizontal)
                .append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinates that = (Coordinates) o;

        if (vertical != that.vertical) return false;
        return horizontal == that.horizontal;
    }

    @Override
    public int hashCode() {
        int result = vertical;
        result = 31 * result + horizontal;
        return result;
    }
}
