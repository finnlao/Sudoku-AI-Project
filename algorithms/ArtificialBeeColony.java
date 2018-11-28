package algorithms;

import sudoku.Sudoku;
import sudoku.Cell;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;

/**
 * @author Youssef El-Hasbani
 */
public class ArtificialBeeColony{
    private int eBee;
    private int oBee;
    private int sBee;
    private int maxCycles;
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
        this.maxCycles = maxCycles;

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
        for (int i = 0; i < maxCycles; i++) {
            System.out.println("Cycle: " + i);

            NeighborhoodSearch();
            AbandonFoodSources();

            System.out.println(foodSources.get(foodSources.size() - 1));
            System.out.println("Max Fitness: " + foodSources.get(foodSources.size() - 1).getFitnessLevel());

            if(foodSources.get(0).getFitnessLevel() == 1){
                System.out.println("SOOOOOLVED!");
            }
        }
        System.out.println("MAX CYCLES HIT");
        System.out.println("Best Solution Found: " + foodSources.get(0));
        System.out.println(foodSources.get(0).getFitnessLevel());
    }

    /**
     * Performs a Neighborhood Search on food souces
     * Number of Search Steps is dependent on the number onlooker bees
     * Food sources with higher nectar are allocated more bees
     */
    public void NeighborhoodSearch(){
        Random random = new Random();
        float totalNector = this.getTotalNector();

        System.out.println("Total Nector: " + totalNector);

        for(int i = 0; i < foodSources.size(); i++){
            int searchSteps = Math.round(this.oBee * (foodSources.get(i).getFitnessLevel() / totalNector) + 1);

            for(int s = 0; s < searchSteps; s++){
                int j, k;

                // Pick a random food sources index
                do {
                    k = random.nextInt(foodSources.size());
                } while (k == i);
                
                // Pick a random cell that isn't a fixed
                do {
                    j = random.nextInt(81);
                } while (foodSources.get(i).getCell(j).isFixed());
                
                int Xi = foodSources.get(i).getCell(j).getValue();
                int Xk = foodSources.get(k).getCell(j).getValue();
                
                int phi = (random.nextInt(2) == 0) ? -1 : 1;

                int v = Math.abs(Xi + phi * Math.abs(Xi - Xk));

                if (v < 1 || v > 9){
                    v = ((v % 9) + 1);
                }

                Sudoku candidateSudoku = new Sudoku(foodSources.get(i).getBoard());

                candidateSudoku.getCell(j).setValue(v);
                candidateSudoku.calculateFitness();
                
                if(checkGridConstraint(candidateSudoku)){
                    if(candidateSudoku.getFitnessLevel() > foodSources.get(i).getFitnessLevel()){
                        foodSources.set(i, candidateSudoku);
                        Collections.sort(foodSources);
                    }
                }
                else{
                    for(int l = 0; l < 9; l++){
                        int jGrid = (int) j / 9;
                        int jCell = j % 9;

                        if(l != jCell && foodSources.get(i).getGrid(jGrid)[l].getValue() == v){
                            candidateSudoku = new Sudoku(foodSources.get(i).getBoard());

                            candidateSudoku.getCell(j).setValue(v);
                            candidateSudoku.getGrid(jGrid)[l].setValue(Xi);
                            candidateSudoku.calculateFitness();

                            if(candidateSudoku.getFitnessLevel() > foodSources.get(i).getFitnessLevel()){
                                foodSources.set(i, candidateSudoku);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    public void AbandonFoodSources(){
        for(int i = 1; i <= sBee; i++){
            Sudoku candidateSudoku = new Sudoku(sudokuString);
            candidateSudoku.generateBoard();   
            candidateSudoku.calculateFitness();
    
            if(candidateSudoku.getFitnessLevel() > foodSources.get(foodSources.size() - i).getFitnessLevel()){
                foodSources.set((foodSources.size() - i), candidateSudoku);
            }
        }
        
        Collections.sort(foodSources);
    }

    /**
     * @return Fitness summation of all candidate solutions in population
     */
    private float getTotalNector(){
        float totalNector = 0;

        for(Sudoku s : foodSources){
            totalNector += s.getFitnessLevel();
        }

        return totalNector;
    }

    public boolean checkGridConstraint(Sudoku s){
        Set<Integer> hashSet = new HashSet<Integer>();

        for (int i = 0; i < 9; i++) {
            for (Cell c : s.getGrid(i)) {
                if(hashSet.contains(c.getValue())) return false;
                hashSet.add(c.getValue());
            }

            hashSet.clear();
        }

        return true;
    }

    public static void main(String[] args) {
        String sudokuString = "004300209005009001070060043006002087190007400050083000600000105003508690042910300";
        
        Sudoku puzzleBoard = new Sudoku(sudokuString);
        ArtificialBeeColony abc = new ArtificialBeeColony(sudokuString, 100, 200, 100000);

        abc.run();
    }
}