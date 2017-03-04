package ru.spbstu.icc.kspt.zhuikov.quoridor;


import ru.spbstu.icc.kspt.zhuikov.quoridor.returningClasses.Field;

abstract public class Brain {

    protected Field field;
    protected GameLogic GL;

    abstract public Command whatToDo(Field field);

}
