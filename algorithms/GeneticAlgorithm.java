package algorithms;

import sudoku.Sudoku;
import sudoku.Cell;
import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Random;
import java.util.TreeSet;

public class GeneticAlgorithm {
    private Sudoku initialSudoku;
    private ArrayList<Sudoku> solutions;
    private int restartHeuristic;
    private int noOfParents;
    private float totalFitnessLevel;

    public GeneticAlgorithm(String sudokuString, int initPop, int restartHeuristic) {
        this.initialSudoku = new Sudoku(sudokuString);
        this.solutions = new ArrayList<Sudoku>();

        this.restartHeuristic = restartHeuristic;
        this.noOfParents = 20;

        while (solutions.size() < initPop) {
            Sudoku solution = new Sudoku(sudokuString);
            solution.generateBoard();
            solution.calculateFitness();
            this.solutions.add(solution);
        }
        this.sort();
    }

    public void sort() {
        Collections.sort(solutions);
    }

    public void crossOver() {
        for (int i = 0; i < noOfParents; i += 2) {
            Random rand = new Random();

            Sudoku solution1 = solutions.get(i);
            Sudoku solution2 = solutions.get(i + 1);

            System.out.println("Parent 1 Fitness: " + solution1.getFitnessLevel());
            System.out.println("Parent 2 Fitness: " + solution1.getFitnessLevel());

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

            System.out.println("Offspring 1 Fitness: " + offspring1.getFitnessLevel());
            System.out.println("Offspring 2 Fitness: " + offspring2.getFitnessLevel());

            solutions.add(offspring1);
            solutions.add(offspring2);
        }
    }

    public Sudoku getSolution(int index) {
        return solutions.get(index);
    }

    public int getSize() {
        return solutions.size();
    }
}