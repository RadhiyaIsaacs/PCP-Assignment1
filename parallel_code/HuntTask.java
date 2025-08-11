import java.util.concurrent.ForkJoinTask;

/**
 * Assignment1 PCP
 * 11/08/2025
 * ISCSRAD001 in collaboration with chatgpt
 */

class HuntTask extends ForkJoinTask<Integer> {
    private final Hunt hunt;
    private int result;

    HuntTask(Hunt hunt) {
        this.hunt = hunt;
    }

    @Override
    public Integer getRawResult() {
        return result;
    }

    @Override
    protected void setRawResult(Integer value) {
        this.result = value;
    }

    @Override
    protected boolean exec() {
        result = hunt.findManaPeak();
        return true;
    }
}
