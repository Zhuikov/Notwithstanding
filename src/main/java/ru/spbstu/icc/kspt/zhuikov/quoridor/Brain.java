package ru.spbstu.icc.kspt.zhuikov.quoridor;


import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Coordinates;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.ItemType;

import java.util.*;

abstract public class Brain {

    protected QuoridorField field;

    abstract public Command whatToDo();

//    protected List<Coordinates> getPossibleMoves() {
//
//        List<Coordinates> possibleMoves = new ArrayList<>();
//        for (int i = markerCoordinates.getVertical() - 4; i <= markerCoordinates.getVertical() + 4; i+=2) {
//            for (int j = markerCoordinates.getHorizontal() - 4; j <= markerCoordinates.getHorizontal() + 4; j+=2) {
//                try {
//                    checkMarkerPlace(i, j);
//                    possibleMoves.add(new Coordinates(i, j));
//                } catch (Exception e) {}
//            }
//        }
//
//        return possibleMoves;
//    }

    /**
     * Возвращает кратчайший путь от одной точки поля до другой.
     * Если пройти нельзя, вернется пустой стек.
     * @param from начальные координаты
     * @param dest конечные координаты
     * @return стек координат, по которым нужно пройти.
     */
    protected Stack<Coordinates> getPath(Coordinates from, Coordinates dest) {

        class Vertex {
            private Coordinates coordinates;
            private Vertex from;

            private Vertex(Coordinates coordinates, Vertex from) {
                this.coordinates = coordinates;
                this.from = from;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (!(o instanceof Vertex)) return false;

                Vertex vertex = (Vertex) o;

                return coordinates != null ? coordinates.equals(vertex.coordinates) : vertex.coordinates == null;

            }

            @Override
            public int hashCode() {
                return coordinates != null ? coordinates.hashCode() : 0;
            }
        }

        boolean used[][] = new boolean[field.getRealSize()][field.getRealSize()];
        Queue<Vertex> queue = new LinkedList<>();
        Stack<Coordinates> path = new Stack<>();

        queue.add(new Vertex(from, null));

        while (!queue.isEmpty()) {

//            for (Vertex vertex : queue) {
//                  System.out.print(vertex.coordinates + " ");
//            }
//            System.out.println();
            if (queue.element().coordinates.equals(dest)) {
                Vertex vertex = queue.element();
                while (vertex.from != null) {
                    path.add(vertex.coordinates);
                    vertex = vertex.from;
                }
//                int i = 0;
//                for (Coordinates coord : path) {
//                    System.out.print(i + ") " + coord + " ");
//                    i++;
//                }
//                System.out.println();
//                System.out.println(path.peek());
                return path;
            }

            for (final Coordinates neighbour : getNeighbours(queue.element().coordinates)) {
                try {
                    if (!used[neighbour.getVertical()][neighbour.getHorizontal()] &&
                            field.getItem((queue.element().coordinates.getVertical() + neighbour.getVertical()) / 2,
                                    (queue.element().coordinates.getHorizontal() + neighbour.getHorizontal()) / 2).getType() != ItemType.BARRIER &&
                            !queue.contains(new Vertex(neighbour, queue.element()))) {
                        queue.add(new Vertex(neighbour, queue.element()));
                    }
                } catch (ArrayIndexOutOfBoundsException e) { }
            }

            used[queue.element().coordinates.getVertical()][queue.element().coordinates.getHorizontal()] = true;
            queue.remove();
        }

        return path;
    }

    private List<Coordinates> getNeighbours(Coordinates coordinates) {

        List<Coordinates> neighbours = new ArrayList<>();

        neighbours.add(new Coordinates(coordinates.getVertical() - 2, coordinates.getHorizontal()));
        neighbours.add(new Coordinates(coordinates.getVertical(), coordinates.getHorizontal() - 2));
        neighbours.add(new Coordinates(coordinates.getVertical() + 2, coordinates.getHorizontal()));
        neighbours.add(new Coordinates(coordinates.getVertical(), coordinates.getHorizontal() + 2));

        return neighbours;
    }


}
