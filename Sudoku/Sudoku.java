package sudoku;

import java.util.ArrayList;
import sudoku.Cell;

public class Sudoku {

    private ArrayList board;
    public Sudoku(String sudokuSet){
        board = new ArrayList<Cell []>();
        for (int j = 0; j < sudokuSet.length(); j+=9){
            char [] sudokuSubset = sudokuSet.substring(j, j+9).toCharArray();
            Cell [] cells = new Cell[9];
            for (int i = 0; i < 9; i++){
                int value = Character.getNumericValue(sudokuSubset[i]);
                if(value != 0){
                    cells[i] = new Cell(value);
                } else {
                    cells[i] = new Cell();
                }
            }
            board.add(cells);
        }
    }


    public ArrayList<Integer> getColumn(int index){
        ArrayList<Integer> columnValues = new ArrayList<Integer>();
        int boardThirdIndex = index/3;
        for (int i = 0; i < 3 ; i++){
            Cell [] cells = (Cell [])board.get(i*3 + boardThirdIndex);
            for (int j = 0 ; j < 3 ; j++){
                int cellValue = cells[3*j + index % 3].getValue();
                columnValues.add(cellValue);
            }
        }
        return columnValues;
    }

    public ArrayList<Integer> getRows(int index){
        ArrayList<Integer> rowValues = new ArrayList<Integer>();
        int boardThirdIndex = index / 3;
        for(int i = 0; i < 3; i++){
            Cell [] cells = (Cell []) board.get(i + 3*boardThirdIndex);
            for(int j = 0 ; j < 3; j++){
                int cellValue = cells[3 * (index % 3) + j].getValue();
                rowValues.add(cellValue);
            }
        }
        return rowValues;
    }

    public ArrayList getBoard(){
        return this.board;
    }

    public Cell [] getGrid(int index){
        return (Cell []) this.board.get(index);
    }

    public String printBoard(){
        String output = "";
        for(int i = 0; i < 9; i++){
            if(i % 3 == 0)
                output += "\n";
            ArrayList rows = this.getRows(i);
            for(int j = 0; j < rows.size(); j++){
                if (j % 3 == 0)
                    output += "\t";
                output += rows.get(j);
            }
            output += "\n";

            
        }
        return output;
    }
}