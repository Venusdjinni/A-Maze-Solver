package com.venus.app;

/**
 * Created by arnold on 13/11/17.
 */
public class NoeudAS extends Noeud implements Comparable<NoeudAS> {
    private int g;
    private int h;

    public NoeudAS(Noeud n, Noeud[] finaux) {
        this.idNoeud = n.idNoeud;
        this.coord = n.coord;
        this.type = n.type;
        calculeH(finaux);
    }

    public int getF() {
        return g + h;
    }

    public int getG() {
        return g;
    }

    public boolean setG(int gPrime) {
        // On actualise g, puis on le retourne
        boolean b = gPrime < g;
        if (b) g = gPrime;
        return b;
    }

    private void calculeH(Noeud[] noeuds) {
        for (Noeud n : noeuds)
            h = Math.min(h, disManhattan(n.coord));
    }

    private int disManhattan(Couple c) {
        return Math.abs(this.coord.x - c.x) + Math.abs(this.coord.y - c.y);
    }

    @Override
    public int compareTo(NoeudAS noeudAS) {
        return this.getF() - noeudAS.getF();
    }
}
