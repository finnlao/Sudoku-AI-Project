package algorithms;

import sudoku.Sudoku;
import sudoku.Cell;
import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.TreeSet;

public class GeneticAlgorithm {
    private String initialSudokuString;
    private ArrayList<Sudoku> solutions;
    private Sudoku bestSolution = null;
    private int population, selectedNoOfParents, maxGeneration;
    private float crossoverRate, mutationRate, selectionRate;
    private final Random rand = new Random();

    public GeneticAlgorithm(String sudokuString, int initPop, float crossoverRate, float mutationRate, float selectionRate, int maxGeneration) {
        this.initialSudokuString = sudokuString;
        this.solutions = new ArrayList<Sudoku>();
        this.population = initPop;
        this.mutationRate = mutationRate;
        this.selectionRate = selectionRate;
        this.crossoverRate = crossoverRate;
        this.maxGeneration = maxGeneration;
        this.selectedNoOfParents = (int)(population * selectionRate);

        while (solutions.size() < initPop) {
            Sudoku solution = new Sudoku(sudokuString);
            solution.generateBoard();
            solution.calculateFitness();
            if(bestSolution == null || bestSolution.getFitnessLevel() < solution.getFitnessLevel())
                bestSolution = solution;
            this.solutions.add(solution);
        }
    }

    public void sort() {
        Collections.sort(solutions);
    }

    
    public ArrayList<Sudoku> select(){

        ArrayList<Sudoku> selection = new ArrayList<Sudoku>();

        // Roulette Wheel
        // float[] cumulativeFitnesses = new float[population];
        // cumulativeFitnesses[0] = solutions.get(0).getFitnessLevel();

        // for(int i = 1; i < population; i++){
        //     cumulativeFitnesses[i] = cumulativeFitnesses[i-1] + solutions.get(i).getFitnessLevel();
        // }

        // for(int i = 0 ; i < population - selectedNoOfParents; i++){
        //     float randomFitness = rand.nextFloat() * cumulativeFitnesses[cumulativeFitnesses.length - 1];    
        //     int index = Arrays.binarySearch(cumulativeFitnesses, randomFitness);

        //     if(index < 0)
        //         index = Math.abs(index + 1);

        //     selection.add(solutions.get(index));
        // }
        Sudoku [] tournamentBracket = new Sudoku[5];
        // Tournament 
        while (selection.size() < selectedNoOfParents){
            for (int i = 0; i < 5; i++){
                int index = rand.nextInt(population);
                tournamentBracket[i] = solutions.get(index);
            } 
            Arrays.sort(tournamentBracket);
            if(bestSolution.getFitnessLevel() < tournamentBracket[0].getFitnessLevel())
                bestSolution = tournamentBracket[0];

            if(bestSolution.getFitnessLevel() < tournamentBracket[1].getFitnessLevel())
                bestSolution = tournamentBracket[1];

            selection.add(tournamentBracket[0]);
            selection.add(tournamentBracket[1]);
        }  
        return selection;
    }


    public void crossOver() {
        int remainingPopulation = selectedNoOfParents;
        // parents = new ArrayList<Sudoku>(solutions.subList(0, selectedNoOfParents));
        // solutions.clear();
        ArrayList<Sudoku> parents = select();
        solutions.clear();
        while (remainingPopulation != population){
            if(rand.nextFloat() <= crossoverRate){
                int index1 = rand.nextInt(selectedNoOfParents);
                int index2 = rand.nextInt(selectedNoOfParents);
                while(index1 == index2){
                    index2 = rand.nextInt(selectedNoOfParents);
                }

                Sudoku parent1 = parents.get(index1);
                Sudoku parent2 = parents.get(index2);

                ArrayList offspringBoard1 = new ArrayList<Cell[]>();
                ArrayList offspringBoard2 = new ArrayList<Cell[]>();

                int crossoverPoint = rand.nextInt(9-1) + 1;
                for (int j = 0; j < 9; j++) {
                    if (j >= crossoverPoint) {
                        offspringBoard1.add(parent2.getGrid(j));
                        offspringBoard2.add(parent1.getGrid(j));
                    } else {
                        offspringBoard1.add(parent1.getGrid(j));
                        offspringBoard2.add(parent2.getGrid(j));
                    }
                }

                Sudoku offspring1 = new Sudoku(offspringBoard1);
                Sudoku offspring2 = new Sudoku(offspringBoard2);

                mutation(offspring1);
                mutation(offspring2);

                remainingPopulation++;
            }
        }
        solutions.addAll(parents);
    }

    public void mutation(Sudoku offspring){
        Sudoku solution = offspring;
        float mutation = rand.nextFloat();
        if (mutation <= mutationRate){
            int subGridIndex = rand.nextInt(8);
            Cell[] cells = solution.getGrid(subGridIndex);

            int index1 = rand.nextInt(8);
            while(cells[index1].isFixed()){
                index1 = rand.nextInt(8);
            }

            int index2 = rand.nextInt(8);
            while(index1 == index2 || cells[index2].isFixed()){
                index2 = rand.nextInt(8);
            }

            Cell temp = cells[index1];
            cells[index1] = cells[index2];
            cells[index2] = temp;

            solution.setGrid(subGridIndex, cells);
            solution.calculateFitness();
        } 
        if(bestSolution.getFitnessLevel() < solution.getFitnessLevel())
            bestSolution = solution;
        solutions.add(solution);
    }

    public Sudoku getSolution(int index) {
        return solutions.get(index);
    }

    public int getSize() {
        return solutions.size();
    }

    public void restart(){
        // System.out.println("RESTART");
        // parents = new ArrayList<Sudoku>(solutions.subList(0, selectedNoOfParents));
        // solutions.clear();
        // solutions.addAll(parents);
        // while (solutions.size() < population) {
        //     Sudoku solution = new Sudoku(initialSudokuString);
        //     solution.generateBoard();
        //     solution.calculateFitness();
        //     this.solutions.add(solution);
        // }
    }
    

    public void run(){
        int generationCount = 0;
        int sameFitnessCounter = 0;
        float fittestSolution = 0f;
        do {
            float currentBest = solutions.get(0).getFitnessLevel();
            crossOver();
            System.out.println("Generation "+ (generationCount + 1));
            System.out.println("Fittest solution: "+bestSolution.getFitnessLevel());
            System.out.println("Board: \n"+bestSolution.toString());
            generationCount++;
        } while (bestSolution.getFitnessLevel() != 1 && generationCount < maxGeneration);
    }


    public static void main(String[] args) {
        // String sudokuString =
        // "914753286235186479786492153349628571612754398578931624435192867867543921219867345";
        // String sudokuString = "060000009005040001900005003409000580008650000220480006080000925000528000540000370";
        String sudokuString = "020567900103008600690004270005100000080506090000002700041800059009700804006549010";
        // String sudokuString =
        // "000000000000000000000000000000000000000000000000000000000000000000000000000000000";
        Sudoku puzzleBoard = new Sudoku(sudokuString);
        GeneticAlgorithm ga = new GeneticAlgorithm(sudokuString, 100000, 0.78f, 0.01f, 0.5f, 10000);
        ga.run();

    }
}