package ru.spbstu.icc.kspt.zhuikov.quoridor;


import ru.spbstu.icc.kspt.zhuikov.quoridor.items.*;

import java.util.*;

/**
 * Класс, представляющий игровое поле.
 */
public class QuoridorField {

    /**
     * Настоящий размер поля. Необходим для возможности размещения перегородок между клетками.
     */
    private final int realSize;

    /**
     * Размер поля в клетках, по которым возможно перемещение фишек игроков.
     */
    private final int size;

    /**
     * Двумерный массив, преставляющий поле с установленными элементами.
     */
    private Item[][] field;

    /**
     * Список фишек на поле и их игроков-владельцев.
     */
    private Map<Owner, Coordinates> markers = new HashMap<>();

    /**
     * Возвращает настоящий размер поля.
     */
    public int getRealSize() {  //TODO не вижу смысла, в слове Real, юезр поля не должен знать, что тут творится (у тебя же нет другого метода получения размера поля), мне бы больше понравился простой getSize()
        return realSize;
    }

    /**
     * Возвращает размер поля в клетках.
     */
    public int getSize() {
        return size;
    }

    /**
     * Возвращает список координат фишек простых игроков.
     */
    public List<Coordinates> getUsualPlayerMarkers() {

        List<Coordinates> usualPlayerMarkers = new ArrayList<>();

        for (Owner owner : markers.keySet()) {
            if (owner != Owner.FOX) usualPlayerMarkers.add(markers.get(owner));
        }

        return usualPlayerMarkers;
    }

    /**
     * Конструктор игрового поля.
     * @param size - размерность поля в клетках.
     */
    public QuoridorField(int size) {

        this.size = size;
        realSize = size * 2 - 1;
        field = new Item[realSize][realSize];

        for (int i = 0; i <= realSize - 1; i++) {
            for (int j = 0; j <= realSize - 1; j++) {
                field[i][j] = new Empty();
            }
        }
    }

    /**
     * Размещает элемент на клетку с заданными координатами.
     * @param item - элемент для размещения на поле.
     * @param destination - координаты клетки для размещения элемента.
     */
    // todo может сделать класс OneCellItem, отнаследоваться и передавать сюда, чтобы не пытались перегородку ставить
    public void setItem(Item item, Coordinates destination) {

        field[destination.getVertical()][destination.getHorizontal()] = item;

        if (item.getType() == ItemType.MARKER) {
            markers.put(item.getOwner(), destination);
        }
    }

    /**
     * Размещает перегородку на поле. Координаты для размещения содержатся в передаваемом объекте.
     * @param barrier - перегородка для размещения на поле.
     */
    public void setBarrier(Barrier barrier) {
        for (Coordinates coordinates : barrier.getCoordinates()) {
            field[coordinates.getVertical()][coordinates.getHorizontal()] = barrier;
        }
    }

    /**
     * Возвращает элемент с клетки с заданными параметрами.
     * @param vertical - вертикальная координата клетки.
     * @param horizontal - горизонтальная координата клетки.
     */
    public Item getItem(int vertical, int horizontal) {
        return field[vertical][horizontal];
    }

    /**
     * Возвращает элемент с клетки с заданными координатами.
     * @param coordinates - координаты клетки.
     */
    public Item getItem(Coordinates coordinates) {
        return field[coordinates.getVertical()][coordinates.getHorizontal()];
    }

    /**
     * Возвращает координаты фишки заданного владельца.
     * @param owner - владелец-игрок
     */
    public Coordinates getCoordinates(Owner owner) {

        for (Owner o : markers.keySet()) {
            if (o == owner) {
                return markers.get(o);
            }
        }

        throw new IllegalArgumentException("there is no " + owner );
    }

    /**
     * Делает пустыми клетки на поле с заданными координатами.
     * @param cells - список координат клеток.
     */
    public void clearCells(List<Coordinates> cells) {
        for (Coordinates c : cells) {
            setItem(new Empty(), c);
        }
    }

}
