package ru.spbstu.icc.kspt.zhuikov.qouridor;


public class Coordinates {

    private int vertical;
    private int horizontal;

    public static double pathBetween(Coordinates coordinates1, Coordinates coordinates2) {

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

    public void setVertical(int vertical) {
        this.vertical = vertical;
    }

    public void setHorizontal(int horizontal) {
        this.horizontal = horizontal;
    }

    public Coordinates(int vertical, int horizontal) {
        this.horizontal = horizontal;
        this.vertical = vertical;
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
