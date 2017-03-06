package ru.spbstu.icc.kspt.zhuikov.quoridor;


import ru.spbstu.icc.kspt.zhuikov.quoridor.items.*;
import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.*;
import ru.spbstu.icc.kspt.zhuikov.quoridor.player.PlayerPosition;
import ru.spbstu.icc.kspt.zhuikov.quoridor.returningClasses.Field;

import java.util.*;

public class GameLogic {

    private Field field;

    public GameLogic(Field field) {
        this.field = field;
    }

    public void setField(Field field) { this.field = field; }

    public boolean checkMarker(Coordinates from, Coordinates dest) throws FieldItemException {

        try {
            field.getItemType(dest.getVertical(), dest.getHorizontal());
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new FieldBoundsException("impossible to place marker on " + dest.getVertical() + " " + dest.getHorizontal());
        }

        if (from.equals(dest)) {
            throw new ImpossibleToSetItemException("impossible to move to the same cell");
        }

        if (dest.getVertical() % 2 != 0 || dest.getHorizontal() % 2 != 0) {
            throw new ImpossibleToSetItemException("impossible to set marker on this cell");
        }

        if (field.getItemType(dest.getVertical(), dest.getHorizontal()) != ItemType.EMPTY) {
            throw new CellIsNotEmptyException("cell is not empty");
        }

        if (Coordinates.pathBetween(from, dest) > 2.1) {

            if (!jumpOverMarker(from, dest)) {
                throw new TooLongDistanceException("you can move just nearby cells");
            }
        }

        if (field.getItemType((from.getVertical() + dest.getVertical()) / 2,
                (from.getHorizontal() + dest.getHorizontal()) / 2) == ItemType.BARRIER) {
            throw new ImpossibleToSetItemException("impossible to jump over the barrier");
        }

        return true;
    }

    public boolean checkBarrier(Coordinates dest, BarrierPosition position) throws FieldItemException {

        if (dest.getVertical() % 2 == 0 || dest.getHorizontal() % 2 == 0) {
            throw new ImpossibleToSetItemException("impossible to set barrier here");
        }

        if (position == BarrierPosition.VERTICAL) {                      //todo что-то сделать
            for (int i = dest.getVertical() - Barrier.length + 1; i <= dest.getVertical() + Barrier.length - 1; i++) {
                try {
                    if (field.getItemType(i, dest.getHorizontal()) != ItemType.EMPTY) {
                        throw new CellIsNotEmptyException("impossible to place barrier here");
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new FieldBoundsException("impossible to place barrier here");
                }
            }

        } else if (position == BarrierPosition.HORIZONTAL) {
            for (int i = dest.getHorizontal() - Barrier.length + 1; i <= dest.getHorizontal() + Barrier.length - 1; i++) {
                try {
                    if (field.getItemType(dest.getVertical(), i) != ItemType.EMPTY) {
                        throw new CellIsNotEmptyException("impossible to place barrier here");
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new FieldBoundsException("impossible to place barrier here");
                }
            }
        }


        Barrier probableBarrier = new Barrier(dest, position);
        field.setBarrier(probableBarrier);

        for (int i = 0; i < field.getRealSize(); i+=2) {
            for (int j = 0; j < field.getRealSize(); j+=2) {
                if (field.getItemType(i, j) == ItemType.MARKER && field.getItemOwner(i, j) != Owner.FOX) {
                    if (getPathToRow(new Coordinates(i, j), getPlayerPosition(field.getItemOwner(i, j)).getDestinationRow()).empty()) {
                        throw new ImpossibleToSetItemException("you can't place barrier here. Player is locked");
                    }
                }
            }
        }

        return true;
    }

    /**
     * Возвращает кратчайший путь от одной точки поля до другой.
     * Если пройти нельзя, вернется пустой стек.
     * @param from начальные координаты
     * @param dest конечные координаты
     * @return стек координат, по которым нужно пройти.
     */
    public Stack<Coordinates> getPath(Coordinates from, Coordinates dest) {

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
                            field.getItemType((queue.element().coordinates.getVertical() + neighbour.getVertical()) / 2,
                                    (queue.element().coordinates.getHorizontal() + neighbour.getHorizontal()) / 2) != ItemType.BARRIER &&
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

    /**
     * Возвращает кратчайший путь из точки на поле до заданного ряда.
     * Длина пути всегда больше либо равна двум. // todo: ...
     * @param start - координаты точки
     * @param destinationRow - ряд, до которого ищется путь
     * @return стек координат - кратчайший путь
     */
    public Stack<Coordinates> getPathToRow(Coordinates start, int destinationRow) {

        Stack<Coordinates> path = getPath(start, new Coordinates(destinationRow, 0));
        int min = 10000000;
        for (int i = 0; i <= field.getRealSize(); i+=2) {
            if (field.getItemType(destinationRow, i)== ItemType.EMPTY) {
                Stack<Coordinates> temp = getPath(start, new Coordinates(destinationRow, i));
                if (temp.size() < min && temp.size() != 0) {
                    path = temp;
                    min = path.size();
                }
            }
        }

        return path;
    }

    /**
     * Возвращет позицию игрока по заданному владельцу.
     * Если для владельца нет определенной позиции, например Fox, будет выброшено исключение.
     * @param owner - владелец
     * @return PlayerPosition, соответствующая данному владельцу.
     */
    public PlayerPosition getPlayerPosition(Owner owner){

        for (PlayerPosition position : PlayerPosition.values()) {
            if (position.getOwner() == owner) {
                return position;
            }
        }

        throw new IllegalArgumentException("there is no PlayerPosition for " + owner);
    }

    private List<Coordinates> getNeighbours(Coordinates coordinates) {

        List<Coordinates> neighbours = new ArrayList<>();

        neighbours.add(new Coordinates(coordinates.getVertical() - 2, coordinates.getHorizontal()));
        neighbours.add(new Coordinates(coordinates.getVertical(), coordinates.getHorizontal() - 2));
        neighbours.add(new Coordinates(coordinates.getVertical() + 2, coordinates.getHorizontal()));
        neighbours.add(new Coordinates(coordinates.getVertical(), coordinates.getHorizontal() + 2));

        return neighbours;
    }

    private boolean jumpOverMarker(Coordinates from, Coordinates dest) throws FieldItemException {

        // если "прыгают" прямо:
        if (Coordinates.pathBetween(from, dest) < 4.01 && Coordinates.pathBetween(from, dest) > 3.99) {

            return jumpForward(from, dest);
        }

        // если "прыгают" по диагонали:
        if (Coordinates.pathBetween(from, dest) < 2.83 && Coordinates.pathBetween(from, dest) > 2.81) {

            return jumpDiagonal(from, dest);
        }

        return false;
    }

    private boolean jumpForward(Coordinates from, Coordinates dest) throws FieldItemException {

        Coordinates midCoordinates = new Coordinates( (from.getVertical() + dest.getVertical()) / 2,
                (from.getHorizontal() + dest.getHorizontal()) / 2);

        if (field.getItemType(midCoordinates.getVertical(), midCoordinates.getHorizontal())== ItemType.MARKER) {

            if ( (field.getItemType( (midCoordinates.getVertical() + from.getVertical()) / 2,
                    (midCoordinates.getHorizontal() + from.getHorizontal()) / 2) == ItemType.BARRIER) ||
                    (field.getItemType( (midCoordinates.getVertical() + dest.getVertical()) / 2,
                            (midCoordinates.getHorizontal() + dest.getHorizontal()) / 2) == ItemType.BARRIER) ) {

                throw new ImpossibleToSetItemException("impossible to set marker because of barrier");
            }

            return true;
        }

        return false;
    }

    private boolean jumpDiagonal(Coordinates from, Coordinates dest) throws FieldItemException {

        Coordinates opponentsMarker;

        if (field.getItemType(from.getVertical(), dest.getHorizontal()) == ItemType.MARKER) {
            opponentsMarker = new Coordinates(from.getVertical(), dest.getHorizontal());
        } else if (field.getItemType(dest.getVertical(), from.getHorizontal())== ItemType.MARKER) {
            opponentsMarker = new Coordinates(dest.getVertical(), from.getHorizontal());
        } else { return false; }

        if ( (field.getItemType( (opponentsMarker.getVertical() + from.getVertical()) / 2,
                (opponentsMarker.getHorizontal() + from.getHorizontal()) / 2) == ItemType.BARRIER)
                ||
                (field.getItemType( (opponentsMarker.getVertical() + dest.getVertical()) / 2,
                        (opponentsMarker.getHorizontal() + dest.getHorizontal()) / 2) == ItemType.BARRIER)) {

            throw new ImpossibleToSetItemException("impossible to set marker because of barrier");
        }

        return true;
    }


}
