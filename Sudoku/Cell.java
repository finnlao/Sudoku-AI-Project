import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

public class Cell {
    private int value;
    private HashSet<Integer> possibleValues;

    public Cell(){
        this.value = 0;
        int[] range = new int[9];
        for(int i = 1; i < 10; i++){
            range[i-1] = i;
        }
        this.possibleValues = new HashSet<Integer>(Arrays.asList(range));
    }

    public void removeValue(int value){
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

    public int getValue(){
        return this.value;
    }
}