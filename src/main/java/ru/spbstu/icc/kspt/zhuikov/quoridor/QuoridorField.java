package ru.spbstu.icc.kspt.zhuikov.quoridor;


import ru.spbstu.icc.kspt.zhuikov.quoridor.items.*;

import java.util.*;

public class QuoridorField {
    //TODO я бы засунул этот класс в пакет связаный с полем (да и вообще всё, что свзяано с полем засунул в один пакет)

    private final int realSize;
    private final int size;
    private Item[][] field;

    public QuoridorField(int size) {

        this.size = size;
        realSize = size * 2 - 1;
        field = new Item[realSize][realSize];
        for (int i = 0; i <= realSize - 1; i++) {
            for (int j = 0; j <= realSize - 1; j++) {
                if ((i % 2 == 0) && (j % 2 == 0)) {
                    field[i][j] = new Empty();
                } else {
                    field[i][j] = new Empty();
                }
            }
        }
    }

    public void setItem(Item item, Coordinates destination) {
        field[destination.getVertical()][destination.getHorizontal()] = item;
    }

    public void setBarrier(Barrier barrier) {
        for (Coordinates coordinates : barrier.getCoordinates()) {
            field[coordinates.getVertical()][coordinates.getHorizontal()] = barrier;
        }
    }

    public Item getItem(int vertical, int horizontal) {

        return field[vertical][horizontal];
    }

    public void clearCell(int vertical, int horizontal) {
        field[vertical][horizontal] = new Empty();
    }

    public void clearCells(List<Coordinates> coordinatesList) {
        for (Coordinates coordinates : coordinatesList) {
            field[coordinates.getVertical()][coordinates.getHorizontal()] = new Empty();
        }
    }

    public int getRealSize() {  //TODO не вижу смысла, в слове Real, юезр поля не должен знать, что тут творится (у тебя же нет другого метода получения размера поля), мне бы больше понравился простой getSize()
        return realSize;
    }

    public int getSize() {
        return size;
    }

    /**
     * Возвращает кратчайший путь.
     * @param init начальные координаты
     * @param dest конечные координаты
     * @return стек координат, по которым нужно пройти.
     * Если пройти нельзя, вернется пустой стек.
     */
//    public Stack<Coordinates> getPath(Coordinates init, Coordinates dest) {
//
//        class Vertex {
//            private Coordinates coordinates;
//            private Vertex from;
//
//            private Vertex(Coordinates coordinates, Vertex from) {
//                this.coordinates = coordinates;
//                this.from = from;
//            }
//
//            @Override
//            public boolean equals(Object o) {
//                if (this == o) return true;
//                if (!(o instanceof Vertex)) return false;
//
//                Vertex vertex = (Vertex) o;
//
//                return coordinates != null ? coordinates.equals(vertex.coordinates) : vertex.coordinates == null;
//
//            }
//
//            @Override
//            public int hashCode() {
//                return coordinates != null ? coordinates.hashCode() : 0;
//            }
//        }
//
//        boolean used[][] = new boolean[realSize][realSize];
//        Queue<Vertex> queue = new LinkedList<>();
//        Stack<Coordinates> path = new Stack<>();
//
//        queue.add(new Vertex(init, null));
//
//        while (!queue.isEmpty()) {
//
////            for (Vertex vertex : queue) {
////                  System.out.print(vertex.coordinates + " ");
////            }
////            System.out.println();
//
//            if (queue.element().coordinates.equals(dest)) {
//                Vertex vertex = queue.element();
//                while (vertex.from != null) {
//                    path.add(vertex.coordinates);
//                    vertex = vertex.from;
//                }
//
////                int i = 0;
////                for (Coordinates coord : path) {
////                    System.out.print(i + ") " + coord + " ");
////                    i++;
////                }
////                System.out.println();
////                System.out.println(path.peek());
//                return path;
//            }
//
//            for (final Coordinates neighbour : getNeighbours(queue.element().coordinates)) {
//                try {
//                    if (!used[neighbour.getVertical()][neighbour.getHorizontal()] &&
//                            getItem((queue.element().coordinates.getVertical() + neighbour.getVertical()) / 2,
//                                    (queue.element().coordinates.getHorizontal() + neighbour.getHorizontal()) / 2).getType() != ItemType.BARRIER &&
//                            !queue.contains(new Vertex(neighbour, queue.element()))) {
//                        queue.add(new Vertex(neighbour, queue.element()));
//                    }
//                } catch (ArrayIndexOutOfBoundsException e) { }
//            }
//
//            used[queue.element().coordinates.getVertical()][queue.element().coordinates.getHorizontal()] = true;
//            queue.remove();
//        }
//
//        return path;
//    }
//
//    private List<Coordinates> getNeighbours(Coordinates coordinates) {
//
//        List<Coordinates> neighbours = new ArrayList<Coordinates>();
//
//        neighbours.add(new Coordinates(coordinates.getVertical() - 2, coordinates.getHorizontal()));
//        neighbours.add(new Coordinates(coordinates.getVertical(), coordinates.getHorizontal() - 2));
//        neighbours.add(new Coordinates(coordinates.getVertical() + 2, coordinates.getHorizontal()));
//        neighbours.add(new Coordinates(coordinates.getVertical(), coordinates.getHorizontal() + 2));
//
//        return neighbours;
//    }

}
