package com.venus.app;

/**
 * Created by arnold on 13/11/17.
 */
public class NoeudAS extends Noeud {
    private int g;
    private int h;

    public NoeudAS(Couple c, Noeud[] finaux) {
        super(c);
        calculeH(finaux);
    }

    public NoeudAS(Couple c, TypeNoeud t, Noeud[] finaux) {
        super(c, t);
        calculeH(finaux);
    }

    public int getF() {
        return getG() + h;
    }

    private int getG() {
        // On actualise g, puis on le retourne
        return g;
    }

    private void calculeH(Noeud[] noeuds) {
        for (Noeud n : noeuds)
            h = Math.min(h, disManhattan(n.coord));
    }

    private int disManhattan(Couple c) {
        return Math.abs(this.coord.x - c.x) + Math.abs(this.coord.y - c.y);
    }
}
