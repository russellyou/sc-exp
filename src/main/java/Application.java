/**
 * Main application
 */

import java.util.Scanner;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;


public class Application {


    public static void main(String[] args) {
        System.out.println("Application started");
        Application application = new Application();
        System.out.println("Enter seat preference sequence: ");
        Scanner scanner = new Scanner(System.in);
        String preferenceSequence = scanner.nextLine();
        System.out.println(application.calculate(preferenceSequence, new Policy1()));
        System.out.println(application.calculate(preferenceSequence, new Policy2()));
        System.out.println(application.calculate(preferenceSequence, new Policy3()));

    }

    /**
     * <p>Calculate how many seat adjustments given sequence of seat preference and policy
     *
     * <p> Prepare:
     * char U/D are mapped to Enum UP/DOWN for readability
     * a new class InterimResult is created to hold the latest seat status and adjustments count so far
     *
     * <p> Process:
     * <ul>
     * <li>Each char will be enriched to a InterimResult of same status with adjustments count 0</li>
     * <li>The first interimResult will be used as initial value for {@link Stream#reduce(Object, BinaryOperator)} function</li>
     * <li>for each following char(preferred status)</li>
     *     <ul>
     *      <li>Adjust seat if previous status != preferred current status</li>
     *      <li>Decide the seat leaving status from policy</li>
     *      <li>Adjust seat if leaving status != preferred current status</li>
     *      <li>Create next interim result of decided leaving Status with sum of (adjustments in previous interim result) and (adjustments done for current person)</li>
     *     </ul>
     * </ul>
     *
     *
     * @param preferenceSequence the passed in String as seat preference sequence
     * @param policy the way to adjust seat status
     * @return total adjustments needed
     */
    public int calculate(final String preferenceSequence, final Policy policy) {
        validate(preferenceSequence);


        Status initialStatus = toStatus(preferenceSequence.charAt(0)); // first char as initial status
        InterimResult initialResult = new InterimResult(initialStatus, 0); // first result for reduce function

        Stream<Status> preferenceStream = toStream(preferenceSequence.substring(1)); // following chars as preference sequence
        InterimResult finalResult = preferenceStream
                .map(status -> new InterimResult(status, 0)) // attach adjustment value 0 to be InterimResult

                // Attention parallel stream can't be used in reduce because first InterimResult is not identity (aka 0) value for accumulator
                // must be used sequentially from left to right to get correct result
                .reduce(initialResult, (previous, current) -> {// reduce from left, equivalent of scala's foldLeft method
                    int adjustments = 0; // how many seat adjustments for current person
                    if (previous.getStatus() != current.getStatus()) {
                        adjustments++;// adjust before using
                    }
                    Status leaveStatus = policy.leaveAs(current.getStatus());
                    if (current.getStatus() != leaveStatus) {
                        adjustments++;// adjust after using
                    }
                    return new InterimResult(leaveStatus, previous.getAdjustments()  + adjustments);
                });

        return finalResult.getAdjustments();
    }

    /**
     * make sure input string is valid
     *
     * @param preferenceSequence
     */
    private void validate(final String preferenceSequence) {
        if (preferenceSequence == null || preferenceSequence.length() < 2) {
            throw new RuntimeException("need at least one seat sequence to calculate");
        }
    }

    /**
     * covert from string to Enum Stream
     *
     * @param preferenceSequence
     * @return
     */
    private Stream<Status> toStream(final String preferenceSequence) {
        return preferenceSequence.chars()
                .mapToObj(i -> toStatus(i));
    }

    /**
     * convert char(represented by int) to enum
     *
     * @param i
     * @return
     */
    private Status toStatus(final int i) {
        if (i == 85)// char U
            return Status.UP;
        else if (i == 68) // char D
            return Status.DOWN;
        else
            throw new RuntimeException("not a valid input string");
    }
}
