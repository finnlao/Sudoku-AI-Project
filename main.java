
import java.util.ArrayList;

import geneticalgorithm.GeneticAlgorithm;
import sudoku.Sudoku;
import sudoku.Cell;

public class main {
    public static void main(String[] args) {
        String sudokuString = "914753286235186479786492153349628571612754398578931624435192867867543921219867345";
        //String sudokuString = "060000009325040001900005003439000580008650000220483916680000925713528694540000378";
        //String sudokuString = "000000000000000000000000000000000000000000000000000000000000000000000000000000000";
        Sudoku puzzleBoard = new Sudoku(sudokuString);


        System.out.println(puzzleBoard.toString());
        GeneticAlgorithm ga = new GeneticAlgorithm(sudokuString, 5, 1);
       
        for(int i = 0 ; i < 5; i++){
            puzzleBoard = ga.getSolution(i);
            System.out.println(puzzleBoard.getFitnessLevel());
        }


        // for (int i = 0; i < 9; i++) {
        //     Cell[] cells = puzzleBoard.getGrid(i);
        //     for (Cell cell : cells) {
        //         System.out.print(cell.getValue());
        //         System.out.println(cell.isFixed());
        //     }
        // }
    }
}