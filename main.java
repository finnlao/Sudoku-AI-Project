
import java.util.ArrayList;

import geneticalgorithm.GeneticAlgorithm;
import sudoku.Sudoku;
import sudoku.Cell;

public class main {
    public static void main(String[] args) {
        //String sudokuString = "864371259325849761971265843436192587198657432257483916689734125713528694542916378";
        String sudokuString = "060000009325040001900005003439000580008650000220483916680000925713528694540000378";
        //String sudokuString = "000000000000000000000000000000000000000000000000000000000000000000000000000000000";
        Sudoku puzzleBoard = new Sudoku(sudokuString);


        System.out.println(puzzleBoard.toString());
        GeneticAlgorithm ga = new GeneticAlgorithm(sudokuString, 1, 1);
        System.out.println(ga.getSolution(0));

        // for (int i = 0; i < 9; i++) {
        //     Cell[] cells = puzzleBoard.getGrid(i);
        //     for (Cell cell : cells) {
        //         System.out.print(cell.getValue());
        //         System.out.println(cell.isFixed());
        //     }
        // }
    }
}