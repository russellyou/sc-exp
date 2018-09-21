import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
public class ApplicationTest {

    Application app = new Application();
    Policy1 policy1 = new Policy1();
    Policy2 policy2 = new Policy2();
    Policy3 policy3 = new Policy3();

    @Test
    public void testSeq1(){

        String testSequence = "UUUDDUDU";
        assertThat(app.calculate(testSequence, policy1)).isEqualTo(6);
        assertThat(app.calculate(testSequence, policy2)).isEqualTo(7);
        assertThat(app.calculate(testSequence, policy3)).isEqualTo(4);

    }

    @Test
    public void testSeq2(){
        String testSequence = "UUUDDUDUDU";
        assertThat(app.calculate(testSequence,policy1)).isEqualTo(8);
        assertThat(app.calculate(testSequence,policy2)).isEqualTo(9);
        assertThat(app.calculate(testSequence,policy3)).isEqualTo(6);

    }


    @Test(expected = RuntimeException.class)
    public void testInvalidLengthString(){
        String testSequence = "U";
        app.calculate(testSequence,policy1);
    }


    @Test(expected = RuntimeException.class)
    public void testInvalidStatus(){
        String testSequence = "UID";
        app.calculate(testSequence,policy1);
    }


    @Test
    public void testSeq1000(){
        StringBuilder sb ;
        for(int i = 1; i <= 125; i++) {
            sb = new StringBuilder();
            for (int j = 1; j <= i; j++) {
                sb.append("UUUDDUDU");
            }
            String testSequence = sb.toString();
            assertThat(app.calculate(testSequence, policy1)).isEqualTo(6 * i);
            assertThat(app.calculate(testSequence, policy2)).isEqualTo(7 * i + 3* (i-1));
            assertThat(app.calculate(testSequence, policy3)).isEqualTo(4 * i);
        }

    }



}