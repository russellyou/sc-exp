/**
 * policy of how we leave the toilet seat
 */
public interface Policy {
    /**
     *
     * @param prefer the toilet seat status current person prefer
     * @return the seat status when current person leave toilet
     */
    Status leaveAs(Status prefer);
}
