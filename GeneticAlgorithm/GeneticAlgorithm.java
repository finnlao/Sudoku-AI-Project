package geneticalgorithm;

import sudoku.Sudoku;
import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;

public class GeneticAlgorithm {
    private Sudoku initialSudoku;
    private ArrayList<Sudoku> solutions;
    private int population; 
    private int restartHeuristic;

    public GeneticAlgorithm(String sudokuSet, int initPop, int restartHeuristic){
        this.initialSudoku = new Sudoku(sudokuSet);
        this.population = initPop;
        this.solutions = new ArrayList<Sudoku>();
        this.restartHeuristic = restartHeuristic;
        for(int i = 0 ; i < population; i++){
            Sudoku solution = new Sudoku(sudokuSet);
            solution.generateBoard();
            solution.calculateFitness();
            this.solutions.add(solution);
        }

        Collections.sort(solutions, new Comparator<Sudoku>(){
            public int compare(Sudoku s1, Sudoku s2){
                int s1Fitness = s1.getFitnessLevel();
                int s2Fitness = s2.getFitnessLevel();

                if(s1Fitness < s2Fitness)
                    return 1;
                else if (s1Fitness == s2Fitness)
                    return 0;
                else
                    return -1;

            }
        });
    }

    public Sudoku getSolution(int index){
        return this.solutions.get(index);
    }


    
}