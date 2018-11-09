
import java.util.ArrayList;
import sudoku.Sudoku;
import sudoku.Cell;

public class main {
    public static void main(String[] args) {
        String sudokuString = "864371259325849761971265843436192587198657432257483916689734125713528694542916378";
        Sudoku puzzleBoard = new Sudoku(sudokuString);


        System.out.println(puzzleBoard.printBoard());
        // for (int i = 0; i < 9; i++) {
        //     Cell[] cells = puzzleBoard.getGrid(i);
        //     for (Cell cell : cells) {
        //         System.out.print(cell.getValue());
        //         System.out.println(cell.isFixed());
        //     }
        // }
    }
}