package ru.spbstu.icc.kspt.zhuikov.quoridor.core.player.AI;


import ru.spbstu.icc.kspt.zhuikov.quoridor.core.game.GameLogic;
import ru.spbstu.icc.kspt.zhuikov.quoridor.core.game.QuoridorField;
import ru.spbstu.icc.kspt.zhuikov.quoridor.core.items.Coordinates;
import ru.spbstu.icc.kspt.zhuikov.quoridor.core.items.ItemType;
import ru.spbstu.icc.kspt.zhuikov.quoridor.core.player.Owner;

import java.util.List;
import java.util.Stack;

/**
 * Класс, реализующий "мозг" для принятия решений, используемый лисой.
 */
public class FoxBrain extends Brain {

    /**
     * Ссылка на лису, использующую мозг.
     */
    private Fox fox;

    /**
     * Цель для преследования.
     */
    private Owner target;

    /**
     * Конструктор класса.
     * @param field - игровое поле.
     * @param fox - лиса, использующая мозг.
     */
    public FoxBrain(QuoridorField field, Fox fox) {

        this.quoridorField = field;
        GL = new GameLogic(field);
        this.fox = fox;

        List<Coordinates> playersCoordinates = field.getUsualPlayerMarkers();

        if (playersCoordinates.size() == 0) {
            throw new IllegalArgumentException("there is no target for fox");
        }
        int rand = (int) (Math.random() * 10) % playersCoordinates.size();
        System.out.println("fox rand: " + rand + " " + field.getItem(playersCoordinates.get(rand)).getOwner());
        target = field.getItem(playersCoordinates.get(rand)).getOwner();

    }

    /**
     * Возвращает команду, которую необходимо выполнить для следующего хода.
     * @see Command ;
     */
    public Command whatToDo() {
        return getNextStep();
    }

    /**
     * Возвращает команду, содержащую информацию о координатах передвижения фишки.
     */
    private Command getNextStep() {

        Coordinates targetCoordinates = new Coordinates(-1, -1);
        for (Coordinates c : quoridorField.getUsualPlayerMarkers()) {
            if (quoridorField.getItem(c).getOwner() == target) {
                targetCoordinates = c;
            }
        }

        Stack<Coordinates> path = GL.getPath(quoridorField.getCoordinates(Owner.FOX), targetCoordinates);

        if (path.empty()) {
            if (fox.getCore().getPossibleMoves().size() == 0) {
                return new Command(quoridorField.getCoordinates(Owner.FOX));
            }
            int rand = (int)(Math.random() * 10) % fox.getCore().getPossibleMoves().size();
            return new Command(fox.getCore().getPossibleMoves().get(rand));
        }

        if (quoridorField.getItem(path.peek()).getType() == ItemType.EMPTY) {
            return new Command(path.peek());
        }

        if (path.peek().equals(targetCoordinates)) {
            return new Command(path.peek());
        }

        if (quoridorField.getItem(path.peek()).getType() != ItemType.EMPTY) {
            path.pop();
            return new Command(path.peek());
        }

        throw new IllegalArgumentException("Fox, getNextStep необработанный сценарий"); //todo: убрать это
    }
}
