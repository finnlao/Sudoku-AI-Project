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
    }

    public void run(){
         // Initialize Population
        for (int i = 0; i < (eBee * eBee); i++) {
            Sudoku candidateSudoku = new Sudoku(sudokuString);
            candidateSudoku.generateBoard();
            candidateSudoku.calculateFitness();
            foodSources.add(candidateSudoku);
        }
        
        Collections.sort(foodSources);
        foodSources.subList(eBee + 1, foodSources.size()).clear();

        for (int j = 0; j < maxCycles; j++) {
            System.out.println("Cycle: " + j);

            // Employee Bee Neighborhood Search
            for (int k = 0; k < eBee; k++) {
                NeighborhoodSearch(k);
            }

            float totalNectar = getTotalNectar();

            for(int m = 0; m < eBee; m++){
                int allocatedOnlookers = Math.round(this.oBee * (foodSources.get(m).getFitnessLevel() / totalNectar));

                for (int n = 0; n < allocatedOnlookers; n++) {
                    NeighborhoodSearch(m);
                }
            }

            AbandonFoodSources();

            System.out.println(foodSources.get(0));
            System.out.println("Max Fitness: " + foodSources.get(0).getFitnessLevel());

            if(foodSources.get(0).getFitnessLevel() == 1){
                break;
            }
        }
        System.out.println("Best Solution Found: " + foodSources.get(0));
        System.out.println(foodSources.get(0).getFitnessLevel());
        System.out.println();

        foodSources.clear();
    }

    /**
     * Performs a Neighborhood Search on food souces
     * Number of Search Steps is dependent on the number onlooker bees
     * Food sources with higher nectar are allocated more bees
     */
    public void NeighborhoodSearch(int index){
        Random random = new Random();
        Sudoku foodSource = foodSources.get(index);

        int j, k;

        // Pick a random food sources index    
        k = random.nextInt(foodSources.size());
                
        // Pick a random cell that isn't a fixed
        do {
            j = random.nextInt(81);
        } while (foodSource.getCell(j).isFixed());
                
        int Xi = foodSource.getCell(j).getValue();
        int Xk = foodSources.get(k).getCell(j).getValue();
        
        float phi = random.nextFloat() * (random.nextInt(3) - 1);

        int v = Math.round(Xi + phi * Math.abs(Xi - Xk));

        if (v > 9){
            v = ((v % 9) + 1);
        }

        Sudoku candidateSudoku = new Sudoku(foodSource.getBoard());

        candidateSudoku.getCell(j).setValue(v);
        candidateSudoku.calculateFitness();
        
        int gridIndex = (int) j / 9;

        if(checkGridConstraint(candidateSudoku, gridIndex)){
            if(candidateSudoku.getFitnessLevel() > foodSource.getFitnessLevel()){
                foodSources.set(index, candidateSudoku);
                Collections.sort(foodSources);
            }
        }
        else{
            for(int l = 0; l < 9; l++){
                int cellValue = foodSource.getGrid(gridIndex)[l].getValue();

                if(l != (j % 9) && cellValue == v){
                    candidateSudoku = new Sudoku(foodSource.getBoard());
                        
                    candidateSudoku.getCell(j).setValue(v);
                    candidateSudoku.getGrid(gridIndex)[l].setValue(Xi);
                    candidateSudoku.calculateFitness();

                    if(candidateSudoku.getFitnessLevel() > foodSource.getFitnessLevel()){
                        foodSources.set(index, candidateSudoku);
                        Collections.sort(foodSources);
                        break;
                    }
                    else{
                        candidateSudoku = new Sudoku(foodSource.getBoard());
                        candidateSudoku.setGrid((j % 9), foodSources.get(k).getGrid((j % 9)));
                        candidateSudoku.calculateFitness();

                        if(candidateSudoku.getFitnessLevel() > foodSource.getFitnessLevel()){
                            foodSources.set(index, candidateSudoku);
                            Collections.sort(foodSources);
                            break;
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
                    Collections.sort(foodSources);
                }
        }
    }

    /**
     * @return Fitness summation of all candidate solutions in population
     */
    private float getTotalNectar(){
        float totalNectar = 0;

        for(Sudoku s : foodSources){
            totalNectar += s.getFitnessLevel();
        }

        return totalNectar;
    }

    public boolean checkGridConstraint(Sudoku s, int gridIndex){
        Set<Integer> hashSet = new HashSet<Integer>();

        for (Cell c : s.getGrid(gridIndex)) {
            if(hashSet.contains(c.getValue())) return false;
            hashSet.add(c.getValue());
        }

        return true;
    }

    public static void main(String[] args) {
        // 020567900103008600690004270005100000080506090000002700041800059009700804006549010
        // AI Escargot 100030009007020600090008500005010600300080004900002000300041007000000000010007300
        String sudokuString = "020567900103008600690004270005100000080506090000002700041800059009700804006549010";
        
        ArtificialBeeColony abc = new ArtificialBeeColony(sudokuString, 100, 200, 100000);
        
        abc.run();
    }
}