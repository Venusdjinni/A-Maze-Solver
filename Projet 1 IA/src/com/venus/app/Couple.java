package com.venus.app;

/**
 * Created by arnold on 08/11/17.
 */
public class Couple implements Cloneable {
    int x;
    int y;

    Couple() {}

    Couple(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Couple) && (this.x == ((Couple)o).x) && (this.y == ((Couple)o).y);
    }

    public void copy(Couple d) {
        this.x = d.x;
        this.y = d.y;
    }

    public Couple cloner() {
        try {
            return (Couple) this.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
