package ru.spbstu.icc.kspt.zhuikov.quoridor.player;

import ru.spbstu.icc.kspt.zhuikov.quoridor.QuoridorCore;
import ru.spbstu.icc.kspt.zhuikov.quoridor.QuoridorField;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Coordinates;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Owner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

abstract public class QuoridorPlayer {

    protected QuoridorCore core;
    protected Owner owner;

    public Owner getOwner() { return owner; }

    public QuoridorCore getCore() {
        return core;
    }

    abstract public void makeMove();
}