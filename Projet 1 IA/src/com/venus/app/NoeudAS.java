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
        if (this.type != TypeNoeud.INITIAL) g = -1;
    }

    public int getF() {
        return g + h;
    }

    public int getG() {
        return g;
    }

    public boolean setG(int gPrime) {
        // On actualise g, puis on le retourne
        boolean b = g < 0 || gPrime < g;
        if (b) g = gPrime;
        return b;
    }

    private void calculeH(Noeud[] noeuds) {
        if (noeuds != null && noeuds.length > 0) {
            h = disManhattan(noeuds[0].coord);
            for (int i = 1; i < noeuds.length; i++)
                h = Math.min(h, disManhattan(noeuds[i].coord));
        }
    }

    private int disManhattan(Couple c) {
        return Math.abs(this.coord.x - c.x) + Math.abs(this.coord.y - c.y);
    }

    @Override
    public int compareTo(NoeudAS noeudAS) {
        int c = this.getF() - noeudAS.getF();
        // Si deux points ont la même valeur de f, on compare sur h
        return c == 0 ? (this.h - noeudAS.h) : c;
    }
}
