package ru.spbstu.icc.kspt.zhuikov.quoridor;


import ru.spbstu.icc.kspt.zhuikov.quoridor.items.*;
import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.*;
import ru.spbstu.icc.kspt.zhuikov.quoridor.player.PlayerPosition;
import ru.spbstu.icc.kspt.zhuikov.quoridor.player.QuoridorPlayer;

import java.util.*;

/**
 * Класс, представлющий логику игры.
 * Осуществляет проверку правил.
 */
public class GameLogic {

    /**
     * Игровое поле.
     */
    private QuoridorField field;

    /**
     * Конструктор класса.
     * @param field - игровое поле.
     */
    public GameLogic(QuoridorField field) {
        this.field = field;
    }

    /**
     * Проверяет возможность передвижения фишки из одних координат на поле в другие.
     * Используются различные правила для обычных игроков и лисы.
     * @param from - начальные координаты фишки.
     * @param destination - координаты перемещения.
     * @param fox - флаг, является ли игрок лисой.
     * @return true, если перемещение фишки возможно.
     * @throws FieldItemException при невозможном перемещении; исключение содержит причину запрета хода.
     */
    public boolean checkMarker(Coordinates from, Coordinates destination, boolean fox) throws FieldItemException {

        try {
            field.getItem(destination).getType();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new FieldBoundsException("impossible to place marker on " +
                    destination.getVertical() + " " + destination.getHorizontal());
        }

        if (from.equals(destination) && !fox) {
            throw new ImpossibleToSetItemException("impossible to move to the same cell");
        }

        if (destination.getVertical() % 2 != 0 || destination.getHorizontal() % 2 != 0) {
            throw new ImpossibleToSetItemException("impossible to set marker on this cell");
        }

        if (field.getItem(destination).getType() != ItemType.EMPTY && !fox) {
            throw new CellIsNotEmptyException("cell is not empty");
        }

        if (Coordinates.pathBetween(from, destination) > 2.1) {

            if (!jumpOverMarker(from, destination)) {
                throw new TooLongDistanceException("you can move just nearby cells");
            }
        }

        if (field.getItem((from.getVertical() + destination.getVertical()) / 2,
                (from.getHorizontal() + destination.getHorizontal()) / 2).getType() == ItemType.BARRIER) {
            throw new ImpossibleToSetItemException("impossible to jump over the barrier");
        }

        return true;
    }

    /**
     * Проверяет возможность установки перегородки на заданных координатах.
     * @param dest - координаты центра перегородки.
     * @param position - позиция перегородки.
     * @return true при возможной установке.
     * @throws FieldItemException при невозможной установке; исключение содержит причину запрета хода.
     */
    public boolean checkBarrier(Coordinates dest, BarrierPosition position) throws FieldItemException {

        if (dest.getVertical() % 2 == 0 || dest.getHorizontal() % 2 == 0) {
            throw new ImpossibleToSetItemException("impossible to set barrier here");
        }

        if (position == BarrierPosition.VERTICAL) {
            for (int i = dest.getVertical() - Barrier.length + 1; i <= dest.getVertical() + Barrier.length - 1; i++) {
                try {
                    if (field.getItem(i, dest.getHorizontal()).getType() != ItemType.EMPTY) {
                        throw new CellIsNotEmptyException("impossible to place barrier here");
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new FieldBoundsException("impossible to place barrier here");
                }
            }

        } else if (position == BarrierPosition.HORIZONTAL) {
            for (int i = dest.getHorizontal() - Barrier.length + 1; i <= dest.getHorizontal() + Barrier.length - 1; i++) {
                try {
                    if (field.getItem(dest.getVertical(), i).getType() != ItemType.EMPTY) {
                        throw new CellIsNotEmptyException("impossible to place barrier here");
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new FieldBoundsException("impossible to place barrier here");
                }
            }
        }


        Barrier probableBarrier = new Barrier(dest, position);
        field.setBarrier(probableBarrier);

        for (Coordinates marker : field.getUsualPlayerMarkers()) {

            if (getPathToRow(marker, getPlayerPosition(field.getItem(marker).getOwner()).getDestinationRow()).empty()) {
                field.clearCells(probableBarrier.getCoordinates());
                throw new ImpossibleToSetItemException("you can't place barrier here. Player is locked");
            }
        }

//        for (int i = 0; i < field.getRealSize(); i+=2) {
//            for (int j = 0; j < field.getRealSize(); j+=2) {
//                if (field.getItem(i, j).getType() == ItemType.MARKER && field.getItem(i, j).getOwner() != Owner.FOX) {
//                    if (getPathToRow(new Coordinates(i, j), getPlayerPosition(field.getItemOwner(i, j)).getDestinationRow()).empty()) {
//                        field.clearCells(probableBarrier.getCoordinates());
//                        throw new ImpossibleToSetItemException("you can't place barrier here. Player is locked");
//                    }
//                }
//            }
//        }

        field.clearCells(probableBarrier.getCoordinates());

        return true;
    }

    /**
     * Проверяет, одержал ли игрок победу.
     * @param player - игрок для проверки.
     * @return true, если игрок одержал победу; false в обратном случае.
     */
    public boolean checkVictory(QuoridorPlayer player) {

        Coordinates playerCoordinates = field.getCoordinates(player.getOwner());
        if (player.getOwner() == Owner.FOX) {
            for (Coordinates c : field.getUsualPlayerMarkers()) {
                if (playerCoordinates.equals(c)) return true;
            }
        } else {
            if (playerCoordinates.getVertical() == getPlayerPosition(player.getOwner()).getDestinationRow()) return true;
        }

        return false;
    }

    /**
     * Возвращает кратчайший путь от одной точки поля до другой.
     * Если пройти невозможно, возвращает пустой стек.
     * @param from - начальные координаты
     * @param dest - конечные координаты
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

    /**
     * Возвращает кратчайший путь из точки на поле до заданного ряда.
     * Длина пути всегда больше нуля.
     * @param start - координаты точки, от которой происходит поиск пути.
     * @param destinationRow - ряд, до которого происходит поиск пути.
     * @return стек координат - кратчайший путь.
     */
    public Stack<Coordinates> getPathToRow(Coordinates start, int destinationRow) {

        Stack<Coordinates> path = getPath(start, new Coordinates(destinationRow, 0));
        int min = 10000000;
        for (int i = 0; i <= field.getRealSize(); i+=2) {
            if (field.getItem(destinationRow, i).getType() == ItemType.EMPTY) {
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
     * Если для владельца нет определенной позиции (например Fox), будет сгенерировано исключение.
     * @param owner - владелец.
     */
    public PlayerPosition getPlayerPosition(Owner owner){

        for (PlayerPosition position : PlayerPosition.values()) {
            if (position.getOwner() == owner) {
                return position;
            }
        }

        throw new IllegalArgumentException("there is no PlayerPosition for " + owner);
    }

    /**
     * Возвращает список координат соседних клеток,
     * в которые допускается совершить ход из клетки с заданными координатами.
     * @param coordinates - координаты клетки, для которой происходит поиск соседей.
     */
    private List<Coordinates> getNeighbours(Coordinates coordinates) {

        List<Coordinates> neighbours = new ArrayList<>();

        neighbours.add(new Coordinates(coordinates.getVertical() - 2, coordinates.getHorizontal()));
        neighbours.add(new Coordinates(coordinates.getVertical(), coordinates.getHorizontal() - 2));
        neighbours.add(new Coordinates(coordinates.getVertical() + 2, coordinates.getHorizontal()));
        neighbours.add(new Coordinates(coordinates.getVertical(), coordinates.getHorizontal() + 2));

        return neighbours;
    }

    /**
     * Проверяет возможность передвижения фишки из одних координат на поле в другие.
     * Используется, если указанные координаты не являются соседними (в них невозможен переход на один ход).
     * @param from - начальные координаты фишки.
     * @param dest - координаты для проверки возможности передвижения.
     * @return true, если возможно совершить ход.
     * @throws FieldItemException при невозможности передвижения; исключение содержит причину запрета хода.
     */
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

        if (field.getItem(midCoordinates).getType()== ItemType.MARKER) {

            if ( (field.getItem( (midCoordinates.getVertical() + from.getVertical()) / 2,
                    (midCoordinates.getHorizontal() + from.getHorizontal()) / 2).getType() == ItemType.BARRIER) ||
                    (field.getItem( (midCoordinates.getVertical() + dest.getVertical()) / 2,
                            (midCoordinates.getHorizontal() + dest.getHorizontal()) / 2).getType() == ItemType.BARRIER) ) {

                throw new ImpossibleToSetItemException("impossible to set marker because of barrier");
            }

            return true;
        }

        return false;
    }

    private boolean jumpDiagonal(Coordinates from, Coordinates dest) throws FieldItemException {

        Coordinates opponentsMarker;

        if (field.getItem(from.getVertical(), dest.getHorizontal()).getType() == ItemType.MARKER) {
            opponentsMarker = new Coordinates(from.getVertical(), dest.getHorizontal());
        } else if (field.getItem(dest.getVertical(), from.getHorizontal()).getType() == ItemType.MARKER) {
            opponentsMarker = new Coordinates(dest.getVertical(), from.getHorizontal());
        } else { return false; }

        if ( (field.getItem( (opponentsMarker.getVertical() + from.getVertical()) / 2,
                (opponentsMarker.getHorizontal() + from.getHorizontal()) / 2).getType() == ItemType.BARRIER)
                ||
                (field.getItem( (opponentsMarker.getVertical() + dest.getVertical()) / 2,
                        (opponentsMarker.getHorizontal() + dest.getHorizontal()) / 2).getType() == ItemType.BARRIER)) {

            throw new ImpossibleToSetItemException("impossible to set marker because of barrier");
        }

        return true;
    }


}
