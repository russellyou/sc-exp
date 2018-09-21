/**
 * To hold the latest status + number of seats adjustments so far
 * make fields final to prevent mutation
 */
public class InterimResult {
    private final Status status;
    private final int adjustments;

    public InterimResult(Status status, int adjustments) {
        this.status = status;
        this.adjustments = adjustments;
    }

    public Status getStatus() {
        return status;
    }


    public int getAdjustments() {
        return adjustments;
    }

}
