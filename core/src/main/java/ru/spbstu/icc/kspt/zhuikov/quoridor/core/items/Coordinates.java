package ru.spbstu.icc.kspt.zhuikov.quoridor.core.items;

/**
 * Класс для представления координат точки на поле.
 */
public class Coordinates {

    /**
     * Вертикальная координата.
     */
    private final int vertical;

    /**
     * Горизонтальная координата.
     */
    private final int horizontal;

    /**
     * Метод, подсчитывающий расстояние от одной точки на поле до другой.
     * @param coordinates1 - координаты первой точки.
     * @param coordinates2 - координаты второй точки.
     * @return расстояние между указанными точками.
     */
    public static double pathBetween(Coordinates coordinates1, Coordinates coordinates2) {

        //TODO конечно бред, но можно выделить private метод, возведения в квадрат, по крайней мере, понятность кода увеличится
        return Math.sqrt((coordinates1.getVertical() - coordinates2.getVertical()) *
                         (coordinates1.getVertical() - coordinates2.getVertical()) +
                        (coordinates1.getHorizontal() - coordinates2.getHorizontal()) *
                        (coordinates1.getHorizontal() - coordinates2.getHorizontal()));
    }

    /**
     * Возвращает вертикальную координату точки.
     */
    public int getVertical() {
        return vertical;
    }

    /**
     * Возвращает горизонтальную координату точки.
     */
    public int getHorizontal() {
        return horizontal;
    }

    /**
     * Конструктор класса. Создает объект с указанными параметрами.
     * @param vertical - вертикальная координата создаваемой точки.
     * @param horizontal - горизонтальная координата создаваемой точки.
     */
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
