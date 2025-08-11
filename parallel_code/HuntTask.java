import java.util.concurrent.RecursiveTask;
/**
 * PCP Assignment 1
 * 2025
 * ISCAD001
 */

public class HuntTask extends RecursiveTask <Integer>{
    private static final int THRESHOLD = 1000;  
    private Hunt[] searches;
    private int start, end;

    private int maxMana = Integer.MIN_VALUE;
    private int maxIndex = -1;

    public HuntTask(Hunt[] searches, int start, int end) {
        this.searches = searches;
        this.start = start;
        this.end = end;
    }


    public int getMaxMana() {
        return maxMana;
    }
    
    public int getMaxIndex() {
        return maxIndex;
    }

    @Override
    protected Integer compute() {
        if (end - start <= THRESHOLD) {
            
            for (int i = start; i < end; i++) {
                int mana = searches[i].findManaPeak();
                if (mana > maxMana) {
                    maxMana = mana;
                    maxIndex = i;
                }
            }
            return maxMana;
        } else {
            // Split work
            int mid = (start + end) / 2;
            HuntTask left = new HuntTask(searches, start, mid);
            HuntTask right = new HuntTask(searches, mid, end);

            left.fork();
            int rightResult = right.compute();  
            int leftResult = left.join(); 

           if (leftResult > rightResult) {
                maxMana = leftResult;
                maxIndex = left.maxIndex;
                 return leftResult;
            } else {
                maxMana = rightResult;
                maxIndex = right.maxIndex;
                return rightResult;
            }
        }
    }
}
