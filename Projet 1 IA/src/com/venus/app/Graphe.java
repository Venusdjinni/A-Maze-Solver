package com.venus.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by arnold on 08/11/17.
 */
public class Graphe {
    private HashMap<Integer, Noeud> noeuds;
    private HashMap<Couple, Integer> couts;
    private char[][] laby;

    public HashMap<Integer, Noeud> getNoeuds() {
        return noeuds;
    }

    public HashMap<Couple, Integer> getCouts() {
        return couts;
    }

    /**
     *  retourne la liste des successeurs du noeud d'id @id
     */
    public Noeud[] getSuccesseurs(int id) {
        HashSet<Noeud> succ = new HashSet<>();
        for (Map.Entry<Couple, Integer> entry : couts.entrySet())
            if (entry.getKey().x == id) succ.add(noeuds.get(id));

        return succ.toArray(new Noeud[]{});
    }

    public Graphe(char[][] laby, Couple start) throws Exception {
        // On cree un nouveau noeuds à partir des parametres
        // @laby est le tableau labyrinthe
        // @start est le couple de coordonnées du point de départ
        this.laby = laby;
        noeuds = new HashMap<>();
        couts = new HashMap<>();

        if (laby == null ||
            laby.length == 0 ||
            start.x >= laby[0].length ||
            start.y >= laby.length) throw new Exception("Paramètres incorrects");

        Noeud n = new Noeud(start, Noeud.TypeNoeud.INITIAL);
        noeuds.put(n.getIdNoeud(), n);
        for (SensChemin sens : SensChemin.values())
            suivreChemin(n, getSuivant(start, sens), sens, 1);
    }

    private Couple getSuivant(Couple c, SensChemin sens) {
        Couple d = c.cloner();
        switch (sens) {
            case HAUT: d.y -= 1; break;
            case BAS: d.y += 1; break;
            case GAUCHE: d.x -= 1; break;
            case DROITE: d.x += 1; break;
        }
        return d;
    }

    private boolean isOnBounds(Couple c) {
        return (0 <= c.x && c.x < laby[0].length) && (0 <= c.y && c.y < laby.length) && laby[c.y][c.x] != '0';
    }

    private void suivreChemin(Noeud from, Couple pos, SensChemin sens, int cout) {
        // Génère les noeuds suivant un chemin commençant par start et par
        // rapport au sens de sens
        if (isOnBounds(pos)) {
            boolean isStraight = true;
            for (SensChemin s : sens.notOpposites()) {
                if (isOnBounds(getSuivant(pos, s))) {
                    isStraight = false;
                    break;
                }
            }
            if (isStraight) { // La ligne est droite, on continue d'avancer
                if (isOnBounds(getSuivant(pos, sens)))
                    suivreChemin(from, getSuivant(pos, sens), sens, cout + 1);
                else { // terminaison
                    Noeud.TypeNoeud type = laby[pos.y][pos.x] == '#' ? Noeud.TypeNoeud.FINAL : Noeud.TypeNoeud.NONE;
                    Noeud n = new Noeud(pos, type);
                    noeuds.put(n.getIdNoeud(), n);
                    couts.put(new Couple(from.getIdNoeud(), n.getIdNoeud()), cout);
                }
            } else { // Il y a des bifurcations
                // On ajoute la position comme un noeud et on calcule son coût
                Noeud.TypeNoeud type = laby[pos.y][pos.x] == '#' ? Noeud.TypeNoeud.FINAL : Noeud.TypeNoeud.NONE;
                Noeud n = new Noeud(pos, type);
                if (noeuds.containsValue(n)) {
                    noeuds.put(n.getIdNoeud(), n);
                    couts.put(new Couple(from.getIdNoeud(), n.getIdNoeud()), cout);
                } else {
                    noeuds.put(n.getIdNoeud(), n);
                    couts.put(new Couple(from.getIdNoeud(), n.getIdNoeud()), cout);
                    // On relance sur les nouvelles routes
                    suivreChemin(n, getSuivant(pos, sens), sens, 1);
                    for (SensChemin s : sens.notOpposites())
                        suivreChemin(n, getSuivant(pos, s), s, 1);
                }
            }
        }
    }

    public void print() {
        for (Map.Entry<Couple, Integer> entry : couts.entrySet()) {
            System.out.print(" [" + noeuds.get(entry.getKey().x).coord.x + ", " + noeuds.get(entry.getKey().x).coord.y + "] ");
            System.out.print("->");
            System.out.print(" [" + noeuds.get(entry.getKey().y).coord.x + ", " + noeuds.get(entry.getKey().y).coord.y + "] ");
            System.out.print(": " + entry.getValue());
            System.out.println();
        }
    }

    public enum SensChemin {
        HAUT,
        BAS,
        GAUCHE,
        DROITE;

        SensChemin opposite() {
            if (this.equals(HAUT)) return BAS;
            else if (this.equals(BAS)) return HAUT;
            else if (this.equals(GAUCHE)) return DROITE;
            else return GAUCHE;
        }

        SensChemin[] notOpposites() {
            if (this.equals(HAUT) || this.equals(BAS))
                return new SensChemin[]{GAUCHE, DROITE};
            else return new SensChemin[]{HAUT, BAS};
        }
    }
}
