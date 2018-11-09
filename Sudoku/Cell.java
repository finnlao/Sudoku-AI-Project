package sudoku;

import java.util.HashSet;

public class Cell {
    private int value;
    private HashSet<Integer> possibleValues;
    private boolean fixed;

    public Cell(){
        this.value = 0;
        this.fixed = false;
        this.possibleValues = new HashSet<>();

        for(int i = 1; i < 10; i++){
            this.possibleValues.add(i);
        }
    }

    public Cell(int value){
        this.value = value;
        this.fixed = true;
    }

    public void removeValue(int value){
        if(this.possibleValues.contains(value))
            this.possibleValues.remove(value);
    }

    public HashSet<Integer> getPossibleValues(){
        return this.possibleValues;
    }

    public void setValue(){
        if(possibleValues.size() == 1){
            for(int i : possibleValues){
                this.value = i;
            }
        }
    }

    public void setValue(int value){
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }

    public boolean isFixed(){
        return this.fixed;
    }
}