/**
 * always leave as DOWN
 */
public class Policy2 implements Policy{
    @Override
    public Status leaveAs(Status prefer) {
        return Status.DOWN;
    }
}
