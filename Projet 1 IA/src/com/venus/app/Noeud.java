package com.venus.app;

/**
 * Created by arnold on 08/11/17.
 */
public class Noeud {
    private static int id = 0;
    int idNoeud;
    protected Couple coord;
    protected TypeNoeud type;

    protected Noeud() {}
    
    public Noeud(Couple c) {
        idNoeud = id;
        coord = c;
        type = TypeNoeud.NONE;
        id++;
    }

    public Noeud(Couple c, TypeNoeud t) {
        this(c);
        type = t;
    }

    public int getIdNoeud() {
        return idNoeud;
    }

    public Couple getCoord() {
        return coord;
    }

    public TypeNoeud getType() {
        return type;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Noeud && this.coord.equals(((Noeud) obj).getCoord());
    }

    public enum TypeNoeud {
        NONE,
        INITIAL,
        FINAL
    }
}
