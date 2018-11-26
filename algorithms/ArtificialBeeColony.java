package algorithms;

import sudoku.Sudoku;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Youssef El-Hasbani
 */
public class ArtificialBeeColony{
    private int eBee;
    private int oBee;
    private int sBee;
    private String sudokuString;

    public ArrayList<Sudoku> foodSources;

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
        this.sudokuString = sudokuString;
        
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
        }
        
        Collections.sort(foodSources);
    }

    public void run(){

    }

    /**
     * Performs a Neighborhood Search on food souces
     * Number of Search Steps is dependent on the number onlooker bees
     * Food sources with higher nectar are allocated more bees
     */
    public void NeighborhoodSearch(){
        Random random = new Random();
        float totalNector = this.getTotalNector();


        for(int i = 0; i < foodSources.size(); i++){
            int searchSteps = Math.round(this.oBee * (foodSources.get(i).getFitnessLevel() / totalNector) + 1);

            for(int s = 0; s < searchSteps; s++){
                int k, jGrid, jCell;

                // Pick a random food sources index
                k = random.nextInt(foodSources.size());
                
                // Pick a random grid in the puzzle
                jGrid = random.nextInt(foodSources.get(i).getBoard().size());

                // Pick a random cell within the picked grid that isn't a fixed
                do {
                    jCell = random.nextInt(9);
                } while (foodSources.get(i).getGrid(jGrid)[jCell].isFixed());
                
                int Xi = foodSources.get(i).getGrid(jGrid)[jCell].getValue();
                int Xk = foodSources.get(k).getGrid(jGrid)[jCell].getValue();
                int phi = random.nextInt(3) - 1;

                int v = (int) Math.ceil(Xi + phi * Math.abs(Xi - Xk));

                if (v > 9){
                    v = (v % 9) + 1;
                }

                // If third constraint is violated do a swap
                boolean swap = false;
                for(int j = 0; j < 9; j++){
                    if(j != jCell){
                        if(foodSources.get(i).getGrid(jGrid)[j].getValue() == v){
                            swap = true;

                            Sudoku candidateSudoku = new Sudoku(foodSources.get(i).getBoard());

                            candidateSudoku.getGrid(jGrid)[jCell].setValue(v);
                            candidateSudoku.getGrid(jGrid)[j].setValue(Xi);
                            candidateSudoku.calculateFitness();

                            if(candidateSudoku.getFitnessLevel() > foodSources.get(i).getFitnessLevel()){
                                foodSources.set(i, candidateSudoku);
                                break;
                            }
                        }
                    }
                }

                if(!swap){
                    Sudoku candidateSudoku = new Sudoku(foodSources.get(i).getBoard());

                    candidateSudoku.getGrid(jGrid)[jCell].setValue(v);
                    candidateSudoku.calculateFitness();
    
                    if(candidateSudoku.getFitnessLevel() > foodSources.get(i).getFitnessLevel()){
                        foodSources.set(i, candidateSudoku);
                    }
                }

                Collections.sort(foodSources);
            }
        }
    }

    public void AbandonFoodSources(){
        for(int i = 1; i <= eBee; i++){
            Sudoku candidateSudoku = new Sudoku(sudokuString);
            candidateSudoku.generateBoard();   
            candidateSudoku.calculateFitness();
    
            if(candidateSudoku.getFitnessLevel() > foodSources.get(foodSources.size() - i).getFitnessLevel()){
                foodSources.set(foodSources.size() - i, candidateSudoku);
            }
        }
        
        Collections.sort(foodSources);
    }

    /**
     * @return Fitness summation of all candidate solutions in population
     */
    public float getTotalNector(){
        float totalNector = 0;

        for(Sudoku s : foodSources){
            totalNector += s.getFitnessLevel();
        }

        return totalNector;
    }

    public static void main(String[] args) {
        String sudokuString = "060000009005040001900005003409000580008650000220480006080000925000528000540000370";
        
        Sudoku puzzleBoard = new Sudoku(sudokuString);
        ArtificialBeeColony abc = new ArtificialBeeColony(sudokuString, 100, 200, 100000);

        System.out.println(abc.foodSources.get(0).getFitnessLevel());
        System.out.println(abc.foodSources.get(0));

        System.out.println(abc.getTotalNector());

        abc.NeighborhoodSearch();
        
        System.out.println(abc.getTotalNector());

        abc.AbandonFoodSources();

        System.out.println(abc.getTotalNector());
    }
}