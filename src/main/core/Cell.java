package main.core;

public class Cell {
    public static final int EMPTY = 0;
    public static final int HUMAN = 1;
    public static final int AI = 2;

    private int val; // val = player

    public Cell() {
        this.val = EMPTY;
    }

    public int getVal() {
        return this.val;
    }

    public void setVal(int val) {
        this.val = val;
    }
}
