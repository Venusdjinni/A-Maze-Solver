package com.venus.app;

import java.io.*;
import java.util.*;

/**
 * Created by arnold on 08/11/17.
 *
 * Pour le fichier, la 1ere ligne donne les cooedonnees de depart, separées par une virgule et sans espace
 * Pour le labyrinthe:
 * - On marche sur les espaces,
 * - les murs sont les #
 * - les sorties sont les $
 */
public class MainClass {
    public static void main(String[] args) {
        if (args.length == 0 || args[0].isEmpty()) {
            System.out.println("Passez le chemin du fichier en argument console!");
            return;
        }
        File f = new File(args[0]);
        BufferedInputStream is = null;
        Couple start = null;
        Graphe graphe = null;
        try {
            // On recupere les entrees: le point de depart et le labyrinthe
            is = new BufferedInputStream(new FileInputStream(f));
            String texte = convertStreamToString(is);
            String[] lines = texte.split("\n");
            String[] c = lines[0].split(",");
            start = new Couple(Integer.valueOf(c[0]), Integer.valueOf(c[1]));
            ArrayList<char[]> al = new ArrayList<>();
            for (int i = 1; i < lines.length; i++)
                al.add(convertToArray(lines[i]));

            // On transforme le labyrinthe en graphe
            graphe = new Graphe(al.toArray(new char[][]{}), start);

            /* Application de l'algorithme A* */
            HashSet<NoeudAS> opened = new HashSet<>(), closed = new HashSet<>();
            Stack<Noeud> parcours = new Stack<>();
            /* 1 */
            opened.add((NoeudAS) graphe.getNoeud(0));

            /* 2 */
            while (!opened.isEmpty()) {
                // min des noeuds opened
                /* 3 */
                NoeudAS min = null;
                for (NoeudAS nas : opened) {
                    if (min == null) min = nas;
                    else if (min.compareTo(nas) > 0) min = nas;
                }
                opened.remove(min);
                closed.add(min);

                int i = 0;
                while (i < parcours.size() && !Arrays.asList(graphe.getSuccesseurs(parcours.get(i).getIdNoeud())).contains(min))
                    i++;
                if (i < parcours.size()) {
                    int l = parcours.size();
                    for (i += 1; i < l; i++)
                        parcours.pop();
                }
                parcours.push(min);

                /* 4 */
                if (min.getType().equals(Noeud.TypeNoeud.FINAL)) {
                    graphe.print(parcours);
                    System.out.println("\nChemin suivi:");
                    for (Noeud n : parcours)
                        System.out.print("[" + n.getCoord().x + ", " + n.getCoord().y + "]->");
                    System.out.println("\n");
                    break;
                } else {
                    /* 5 */
                    ArrayList<NoeudAS> succ = new ArrayList<>();
                    for (Noeud n : graphe.getSuccesseurs(min.getIdNoeud()))
                        succ.add((NoeudAS) n);
                    if (!succ.isEmpty()) {
                        for (NoeudAS s : succ) {
                            if (!opened.contains(s) && !closed.contains(s)) {
                                opened.add(s);
                            }
                            /* 6 */
                            if (s.setG(min.getG() + graphe.getCout(min.getIdNoeud(), s.getIdNoeud()))) {
                                // Si f a diminué et s est dans closed
                                if (closed.remove(s)) opened.add(s);
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static char[] convertToArray(String s) {
        char[] tab = new char[s.length()];
        for (int i = 0; i < s.length(); i++)
            tab[i] = s.charAt(i);
        return tab;
    }
}
