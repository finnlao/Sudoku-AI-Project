
import java.util.ArrayList;

import algorithms.GeneticAlgorithm;
import algorithms.ArtificialBeeColony;
import sudoku.Sudoku;
import sudoku.Cell;

public class main {
    public static void main(String[] args) {
        // String sudokuString =
        // "914753286235186479786492153349628571612754398578931624435192867867543921219867345";
        String sudokuString = "060000009005040001900005003409000580008650000220480006080000925000528000540000370";
        // String sudokuString =
        // "000000000000000000000000000000000000000000000000000000000000000000000000000000000";
        Sudoku puzzleBoard = new Sudoku(sudokuString);
        GeneticAlgorithm ga = new GeneticAlgorithm(sudokuString, 100, 1);

        // for (int i = 0; i < 100; i++) {
        // System.out.println(ga.getSolution(i).getFitnessLevel());
        // }
        while (ga.getSolution(0).getFitnessLevel() != 1) {
            ga.crossOver();
        }

        System.out.println(ga.getSolution(0).getFitnessLevel());

        // System.out.println("\n POST CROSSOVER \n");

        // for (int i = 0; i < ga.getSize(); i++) {
        // System.out.println(ga.getSolution(i).getFitnessLevel());
        // }

        // for (int i = 0; i < 9; i++) {
        // Cell[] cells = puzzleBoard.getGrid(i);
        // for (Cell cell : cells) {
        // System.out.print(cell.getValue());
        // System.out.println(cell.isFixed());
        // }
        // }
    }
}