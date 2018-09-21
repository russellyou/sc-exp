/**
 * always leave as your preference
 */
public class Policy3 implements Policy{
    @Override
    public Status leaveAs(Status prefer) {
        return prefer;
    }
}
