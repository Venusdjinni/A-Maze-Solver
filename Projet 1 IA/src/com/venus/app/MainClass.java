package com.venus.app;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by arnold on 08/11/17.
 */
public class MainClass {
    public static void main(String[] args) {
        File f = new File("labyrinthe.txt");
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
            graphe.print();
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
