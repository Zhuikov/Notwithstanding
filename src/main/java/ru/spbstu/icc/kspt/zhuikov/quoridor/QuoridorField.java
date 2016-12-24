package ru.spbstu.icc.kspt.zhuikov.quoridor;


import ru.spbstu.icc.kspt.zhuikov.quoridor.items.*;

import java.util.*;

public class QuoridorField {
    //TODO я бы засунул этот класс в пакет связаный с полем (да и вообще всё, что свзяано с полем засунул в один пакет)

    private final int realSize;
    private final int size;
    private Cell[][] field;    //TODO почему бы не использовать, какую-нибудь коллекцию? (конечно банально, но например, List<List<~>>)

    public QuoridorField(int size) {

        this.size = size;
        realSize = size * 2 - 1;
        field = new Cell[realSize][realSize];
        for (int i = 0; i <= realSize - 1; i++) {
            for (int j = 0; j <= realSize - 1; j++) {
                if ((i % 2 == 0) && (j % 2 == 0)) {
                    field[i][j] = new Cell(CellColor.BLACK, new Empty(i, j));
                } else {
                    field[i][j] = new Cell(CellColor.WHITE, new Empty(i, j));
                }
            }
        }
    }

    public QuoridorField() {
        this(9);
    }

    private class Cell {
        private CellColor color;
        private Item item;

        Cell(CellColor color, Item item) {
            this.color = color;
            this.item = item;
        }
    }

    public void setItem(OneCellItem item) {
        field[item.getCoordinates().getVertical()][item.getCoordinates().getHorizontal()].item = item;
    }

    public void setItem(ManyCellsItem item) {
        for (Coordinates coordinates : item.getCoordinates()) {
            field[coordinates.getVertical()][coordinates.getHorizontal()].item = item;
        }
    }

    public void clearCell(int vertical, int horizontal) {
        field[vertical][horizontal].item = new Empty(vertical, horizontal);
    }

    public void clearCells(List<Coordinates> coordinatesList) {
        for (Coordinates coordinates : coordinatesList) {
            field[coordinates.getVertical()][coordinates.getHorizontal()].item
                    = new Empty(coordinates.getVertical(), coordinates.getHorizontal());
        }
    }

    public Item getItem(int vertical, int horizontal) {

        //TODO к примеру, здесь был бы get(), он, по идее, безопаснее

        return field[vertical][horizontal].item;  // todo мб return new ...
    }

    public CellColor getColor(int vertical, int horizontal) {
        return field[vertical][horizontal].color;
    }

    public int getRealSize() {  //TODO не вижу смысла, в слове Real, юезр поля не должен знать, что тут творится (у тебя же нет другого метода получения размера поля), мне бы больше понравился простой getSize()
        return realSize;
    }

    public int getSize() {
        return size;
    }

    /**
     * Возвращает кратчайший путь (в координатах) из заданной позиции до заданного ряда.
     * Если нельзя пройти, вернется пустой стек.
     * @param marker - координаты, из которых ищется путь
     * @param rowNumber - номер ряда (строки)
     */
    public Stack<Coordinates> getPathToRow(Coordinates marker, int rowNumber) {

        if (marker.getVertical() == rowNumber) {
            Stack<Coordinates> coordinates = new Stack<>();
            coordinates.add(getNeighbours(marker).get(0));
            return coordinates;
        }

        class Vertex {
            private Coordinates coordinates;
            private Vertex from;

            private Vertex(Coordinates coordinates, Vertex from) {
                this.coordinates = coordinates;
                this.from = from;
            }
        }

        boolean used[][] = new boolean[realSize][realSize];
        Queue<Vertex> queue = new LinkedList<>();
        List<Vertex> usedVertexes = new ArrayList<>();
        Stack<Coordinates> path = new Stack<>();

        queue.add(new Vertex(marker, null));

        while (!queue.isEmpty()) {

//          for (Coordinates coordinates : queue) {
//              System.out.print(coordinates + " ");
//          }
//          System.out.println();

            if (queue.element().coordinates.getVertical() == rowNumber) {
                Vertex vertex = queue.element();
                while (vertex.from != null) {
                    path.add(vertex.coordinates);
                    vertex = vertex.from;
                }
                return path;
            }

            for (final Coordinates neighbour : getNeighbours(queue.element().coordinates)) {
                try {
                    if (!used[neighbour.getVertical()][neighbour.getHorizontal()] &&   // todo: шлифануть бы тут
                            getItem((queue.element().coordinates.getVertical() + neighbour.getVertical()) / 2,
                                    (queue.element().coordinates.getHorizontal() + neighbour.getHorizontal()) / 2).getType() != ItemType.BARRIER &&
                            !queue.contains(new Vertex(neighbour, queue.element()))) {
                                    //Coordinates(neighbour.getVertical(), neighbour.getHorizontal()))) {
                        queue.add(new Vertex(neighbour, queue.element())); //neighbour
                    }
                } catch (ArrayIndexOutOfBoundsException e) { }
            }

            used[queue.element().coordinates.getVertical()][queue.element().coordinates.getHorizontal()] = true;
            usedVertexes.add(queue.element());
            queue.remove();
        }

        return path;
    }

    /**
     * Возвращает кратчайший путь.
     * @param init начальные координаты
     * @param dest конечные координаты
     * @return стек координат, по которым нужно пройти.
     * Если пройти нельзя, вернется пустой стек.
     */

    //это метод для the Fox, надо будет сделать, чтобы все его использовали
    //для этого выбросить тот енам наверно
    public Stack<Coordinates> getPath(Coordinates init, Coordinates dest) {

        class Vertex {
            private Coordinates coordinates;
            private Vertex from;

            private Vertex(Coordinates coordinates, Vertex from) {
                this.coordinates = coordinates;
                this.from = from;
            }
        }

        boolean used[][] = new boolean[realSize][realSize];
        Queue<Vertex> queue = new LinkedList<>();
        List<Vertex> usedVertexes = new ArrayList<>();
        Stack<Coordinates> path = new Stack<>();

        queue.add(new Vertex(init, null));

        while (!queue.isEmpty()) {

            if (queue.element().coordinates.equals(dest)) {
                Vertex vertex = queue.element();
                while (vertex.from != null) {
                    path.add(vertex.coordinates);
                    vertex = vertex.from;
                }
                return path;
            }

            for (final Coordinates neighbour : getNeighbours(queue.element().coordinates)) {
                try {
                    if (!used[neighbour.getVertical()][neighbour.getHorizontal()] &&
                            getItem((queue.element().coordinates.getVertical() + neighbour.getVertical()) / 2,
                                    (queue.element().coordinates.getHorizontal() + neighbour.getHorizontal()) / 2).getType() != ItemType.BARRIER &&
                            !queue.contains(new Vertex(neighbour, queue.element()))) {
                        //Coordinates(neighbour.getVertical(), neighbour.getHorizontal()))) {
                        queue.add(new Vertex(neighbour, queue.element())); //neighbour
                    }
                } catch (ArrayIndexOutOfBoundsException e) { }
            }

            used[queue.element().coordinates.getVertical()][queue.element().coordinates.getHorizontal()] = true;
            usedVertexes.add(queue.element());
            queue.remove();
        }

        return path;
    }

    private List<Coordinates> getNeighbours(Coordinates coordinates) {

        List<Coordinates> neighbours = new ArrayList<Coordinates>();

        neighbours.add(new Coordinates(coordinates.getVertical() - 2, coordinates.getHorizontal()));
        neighbours.add(new Coordinates(coordinates.getVertical(), coordinates.getHorizontal() - 2));
        neighbours.add(new Coordinates(coordinates.getVertical() + 2, coordinates.getHorizontal()));
        neighbours.add(new Coordinates(coordinates.getVertical(), coordinates.getHorizontal() + 2));

        return neighbours;
    }

}
