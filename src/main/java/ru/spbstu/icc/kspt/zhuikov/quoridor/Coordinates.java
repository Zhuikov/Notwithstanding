package ru.spbstu.icc.kspt.zhuikov.quoridor;


public class Coordinates {

    private int vertical;
    private int horizontal;

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
    public boolean equals(Object o) {   //TODO однобуквенное имя выглядит не очень
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinates that = (Coordinates) o;

        if (vertical != that.vertical) return false;  //TODO как-то криво, предлагаю объединить в одно return условие;
        return horizontal == that.horizontal;
    }

    @Override
    public int hashCode() {
        int result = vertical;
        result = 31 * result + horizontal;
        return result;
    }
}
