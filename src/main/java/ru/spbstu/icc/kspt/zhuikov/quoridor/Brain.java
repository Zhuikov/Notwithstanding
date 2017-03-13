package ru.spbstu.icc.kspt.zhuikov.quoridor;

abstract public class Brain {

    protected GameLogic GL;
    protected QuoridorField quoridorField;

    abstract public Command whatToDo();

}
