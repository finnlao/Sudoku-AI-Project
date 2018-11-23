package geneticalgorithm;

import sudoku.Sudoku;
import sudoku.Cell;
import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Random;
import java.util.TreeSet;

public class GeneticAlgorithm {
    private Sudoku initialSudoku;
    private AraryList<Sudoku> solutions;
    private int population;
    private int restartHeuristic;
    private int noOfParents;
    private float totalFitnessLevel;

    public GeneticAlgorithm(String sudokuString, int initPop, int restartHeuristic) {
        this.initialSudoku = new Sudoku(sudokuString);
        this.population = initPop;
        this.solutions = new ArrayList<Sudoku>();

        this.restartHeuristic = restartHeuristic;
        this.noOfParents = 20;

        while (solutions.size() < population) {
            Sudoku solution = new Sudoku(sudokuString);
            solution.generateBoard();
            solution.calculateFitness();
            this.solutions.add(solution);
        }
    }

    public void crossOver() {
        for (int i = 0; i < noOfParents; i += 2) {
            Random rand = new Random();

            Sudoku solution1 = solutions.get(i);
            Sudoku solution2 = solutions.get(i + 1);

            ArrayList offspringBoard1 = new ArrayList<Cell[]>();
            ArrayList offspringBoard2 = new ArrayList<Cell[]>();

            int crossoverPoint = rand.nextInt(9 - 1) + 1;
            for (int j = 0; j < 9; j++) {
                if (j >= crossoverPoint) {
                    offspringBoard1.add(solution2.getGrid(j));
                    offspringBoard2.add(solution1.getGrid(j));
                } else {
                    offspringBoard1.add(solution1.getGrid(j));
                    offspringBoard2.add(solution2.getGrid(j));
                }
            }

            Sudoku offspring1 = new Sudoku(offspringBoard1);
            Sudoku offspring2 = new Sudoku(offspringBoard2);

            offspring1.calculateFitness();
            offspring2.calculateFitness();

            solutions.add(offspring1);
            solutions.add(offspring2);
        }
    }

    public Sudoku getSolution(int index) {

    }

}