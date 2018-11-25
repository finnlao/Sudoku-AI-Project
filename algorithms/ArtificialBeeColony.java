package algorithms;

import sudoku.Sudoku;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Youssef El-Hasbani
 */
public class ArtificialBeeColony{
    private int eBee;
    private int oBee;
    private int sBee;

    private Sudoku initialSudoku;
    public ArrayList<Sudoku> foodSources;

    public float totalNector;

    /**
     * Constructs an Instance of the ArtificialBeeColony Sudoku Solver
     * Based on 'SudokuBee: An Artificial Bee Colony-based Approach in Solving Sudoku puzzle'
     * 
     * @param sudokuString String representation of the sudoku puzzle
     * @param employedBees Number of Employed Bees
     * @param onlookerBees Number of Onlooker Bees
     * @param maxCycles Number of cycles before stopping
     */
    public ArtificialBeeColony(String sudokuString, int employedBees, int onlookerBees, int maxCycles){
        this.initialSudoku = new Sudoku(sudokuString);
        
        this.eBee = employedBees;
        this.oBee = onlookerBees;
        this.sBee = (int) (0.1 * this.eBee);

        System.out.println(eBee);
        System.out.println(oBee);
        System.out.println(sBee);

        foodSources = new ArrayList<Sudoku>();

        while(foodSources.size() != this.eBee){
            Sudoku foodSource = new Sudoku(sudokuString);
            foodSource.generateBoard();
            foodSource.calculateFitness();
            foodSources.add(foodSource);

            totalNector += foodSource.getFitnessLevel();
        }
        
        Collections.sort(foodSources);
    }

    public static void main(String[] args) {
        String sudokuString = "060000009005040001900005003409000580008650000220480006080000925000528000540000370";
        
        Sudoku puzzleBoard = new Sudoku(sudokuString);
        ArtificialBeeColony abc = new ArtificialBeeColony(sudokuString, 100, 200, 100000);

        System.out.println(abc.foodSources.get(0).getFitnessLevel());
        System.out.println(abc.foodSources.get(0));

        System.out.println(abc.totalNector);
    }
}