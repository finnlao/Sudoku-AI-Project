package geneticalgorithm;

import sudoku.Sudoku;
import java.util.ArrayList;

public class GeneticAlgorithm {
    private Sudoku initialSudoku;
    private ArrayList<Sudoku> solutions;
    private int population; 
    private int restartHeuristic;
    private int fitnessLevel; 

    public GeneticAlgorithm(String sudokuSet, int initPop, int restartHeuristic){
        this.initialSudoku = new Sudoku(sudokuSet);
        this.population = initPop;
        this.solutions = new ArrayList<Sudoku>();
        this.restartHeuristic = restartHeuristic;
        this.fitnessLevel = 0;
        for(int i = 0 ; i < population; i++){
            Sudoku solution = new Sudoku(sudokuSet);
            solution.generateBoard();
            this.solutions.add(solution);
        }

    }

    public Sudoku getSolution(int index){
        return this.solutions.get(index);
    }

}