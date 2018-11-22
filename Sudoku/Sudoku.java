package sudoku;

import java.util.ArrayList;
import sudoku.Cell;

public class Sudoku {
    private ArrayList board;

    public Sudoku(String sudokuSet) {
        board = new ArrayList<Cell[]>();
        for (int j = 0; j < sudokuSet.length(); j += 9) {
            char[] sudokuSubset = sudokuSet.substring(j, j + 9).toCharArray();
            Cell[] cells = new Cell[9];
            ArrayList<Integer> fixedValues = new ArrayList<Integer>();
            for (int i = 0; i < 9; i++) {
                int value = Character.getNumericValue(sudokuSubset[i]);
                if (value != 0) {
                    cells[i] = new Cell(value);
                    fixedValues.add(value);
                } else {
                    cells[i] = new Cell();
                }
            }

            for (int i = 0; i < fixedValues.size(); i++){
                for (int x = 0; x < 9; x++){
                    cells[x].removeValue(fixedValues.get(i));
                }
            }
            board.add(cells);
        }
    }

    public void generateBoard() {
        for (int i = 0; i < 9; i++) {
            Cell[] cells = (Cell[])this.board.get(i);
            for (Cell cell : cells) {
                if (!cell.isFixed()) {
                    cell.setRandomValue();
                    this.removePossibleValues(i, cell.getValue());
                }
            }
            this.board.set(i, cells);
        }

    }

    public void removePossibleValues(int index, int value) {
        Cell[] grid = (Cell[]) board.get(index);
        for (int i = 0; i < 9; i++) {
            if (!grid[i].isFixed()) {
                grid[i].removeValue(value);
            }
        }
        board.set(index, grid);
    }

    public ArrayList<Integer> getColumn(int index) {
        ArrayList<Integer> columnValues = new ArrayList<Integer>();
        int boardThirdIndex = index / 3;
        for (int i = 0; i < 3; i++) {
            Cell[] cells = (Cell[]) board.get(i * 3 + boardThirdIndex);
            for (int j = 0; j < 3; j++) {
                int cellValue = cells[3 * j + index % 3].getValue();
                columnValues.add(cellValue);
            }
        }
        return columnValues;
    }

    public ArrayList<Integer> getRow(int index) {
        ArrayList<Integer> rowValues = new ArrayList<Integer>();
        int boardThirdIndex = index / 3;
        for (int i = 0; i < 3; i++) {
            Cell[] cells = (Cell[]) board.get(i + 3 * boardThirdIndex);
            for (int j = 0; j < 3; j++) {
                int cellValue = cells[3 * (index % 3) + j].getValue();
                rowValues.add(cellValue);
            }
        }
        return rowValues;
    }

    public void setRow(int index, ArrayList<Integer> rowValues) {
        int boardThirdIndex = index / 3;
        int counter = 0;
        Cell[] cells;
        for (int i = 0; i < 3; i++) {
            cells = (Cell[]) board.get(i + 3 * boardThirdIndex);
            for (int j = 0; j < 3; j++) {
                cells[3 * (index % 3) + j].setValue(rowValues.get(3 * i + j));
            }
            board.set(i + 3 * boardThirdIndex, cells);
        }
    }

    public void setGrid(int index, Cell[] cells) {
        this.board.set(index, cells);
    }

    public ArrayList getBoard() {
        return this.board;
    }

    public Cell[] getGrid(int index) {
        return (Cell[]) this.board.get(index);
    }

    public String toString() {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0)
                output.append("\n");

            ArrayList rows = this.getRow(i);
            for (int j = 0; j < rows.size(); j++) {
                if (j % 3 == 0 && j > 1)
                    output.append(" ");

                output.append(" ");
                output.append(rows.get(j));
                output.append(" ");
            }
            output.append("\n");
        }
        return output.toString();
    }
}