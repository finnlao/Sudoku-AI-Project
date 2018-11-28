package sudoku;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

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
        this.possibleValues = new HashSet<>();
    }

    public Cell(Cell c){
        this.value = c.getValue();
        this.fixed = c.isFixed();
        
        if(c.getPossibleValues() != null){
            Iterator<Integer> it = c.getPossibleValues().iterator();
            while(it.hasNext()){
                this.possibleValues.add(it.next().intValue());
            }
        }
    }

    public void removeValue(int value){
        if(this.possibleValues != null && this.possibleValues.contains(value))
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
        if(!this.fixed)
            this.value = value;
    }

    public void setRandomValue(){
        int item = new Random().nextInt(possibleValues.size());
        int counter = 0;
        for(int i : possibleValues){
            if (counter == item)
                this.value = i;
            counter++;
        }
    }

    public int getValue(){
        return this.value;
    }

    public boolean isFixed(){
        return this.fixed;
    }
}