
import java.util.ArrayList;

import algorithms.GeneticAlgorithm;
import algorithms.ArtificialBeeColony;
import sudoku.Sudoku;
import sudoku.Cell;

public class main {
    private static ArrayList<String> sudokus;

    private static ArrayList<Long> GA;
    private static ArrayList<Long> ABC;

    public static void main(String[] args) {
        sudokus = new ArrayList<String>();
        GA = new ArrayList<Long>();
        ABC = new ArrayList<Long>();

        sudokus.add("070002080032400007000075090367040000108090706000010384050710000200005960030600050");
        sudokus.add("020567900103008600690004270005100000080506090000002700041800059009700804006549010");
        sudokus.add("000004080030068100800275003618040090005000600090080431400723001006450070020100000");

        Long startTime;

        for (String sudokuString : sudokus) {
            startTime = System.currentTimeMillis();
            
            GeneticAlgorithm ga = new GeneticAlgorithm(sudokuString, 100000, 0.78f, 0.01f, 0.5f, 10000);
            ga.run();

            GA.add(System.currentTimeMillis() - startTime);


            startTime = System.currentTimeMillis();

            ArtificialBeeColony abc = new ArtificialBeeColony(sudokuString, 100, 200, 100000);
            abc.run();

            ABC.add(System.currentTimeMillis() - startTime);
        }

        System.out.println("GA Times: " + GA);

        System.out.println("ABC Times: " + ABC);
    }
}