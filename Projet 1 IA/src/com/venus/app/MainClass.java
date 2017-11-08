package com.venus.app;

/**
 * Created by arnold on 08/11/17.
 */
public class MainClass {
    public static void main(String[] args) {
        char[][] tab = new char[][]
                {new char[]{'1', '1', '1', '1', '0'},
                 new char[]{'0', '1', '0', '1', '0'},
                 new char[]{'0', '1', '0', '1', '0'},
                 new char[]{'0', '1', '1', '1', '0'}};

        try {
            new Graphe(tab, new Couple(0, 0)).print();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
