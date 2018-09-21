/**
 * always leave as UP
 */
public class Policy1 implements Policy{
    @Override
    public Status leaveAs(Status prefer) {
        return Status.UP;
    }
}
