package edu.pdx.cs410J.scrubey;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests the functionality in the {@link Project2} main class.
 */
public class Project2IT extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project2} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project2.class, args );
    }

    /**
    * Tests that invoking the main method with no arguments issues an error
    */
/*    @Test
    public void testNoCommandLineArguments() {
        MainMethodResult result = invokeMain("");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Incorrect number of command line arguments"));
    }  */

    @Test
    public void testFewerThan7CommandLineArguments(){
        MainMethodResult result = invokeMain("-print", "Customer Name", "123-456-7890", "098-765-4321",
                                                     "01/25/2020", "19:35", "01/25/2020");

        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Incorrect number of command line arguments"));
    }

    @Test
    public void testCommandLineOptionsContainErrors(){
        MainMethodResult result = invokeMain("-pint", "Customer Name", "123-456-7890", "098-765-4321",
                                                    "01/25/2020", "19:35", "01/25/2020", "20:30");

        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getTextWrittenToStandardError(), containsString("Error detected in command line option"));
    }

    @Test
    public void testIncorrectlyFormattedPhoneNumber(){
        MainMethodResult result = invokeMain("-print", "Customer Name", "123456-7890", "098-765-4321",
                                                    "01/25/2020", "19:35", "01/25/2020", "20:30");

        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Phone number entered improperly"));
    }

    @Test
    public void testIncorrectlyFormattedDate(){
        MainMethodResult result = invokeMain("-print", "Customer Name", "123-456-7890", "098-765-4321",
                                                    "01/25/2020", "19:35", "01/32/2020", "20:30");

        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Date entered improperly"));
    }

    @Test
    public void testIncorrectlyFormattedTime(){
        MainMethodResult result = invokeMain("-print", "Customer Name", "123-456-7890", "098-765-4321",
                                                    "01/25/2020", "19:35", "01/31/2020", "24-61");

        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Time entered improperly"));
    }

    @Test
    public void testPrintFlagPresent(){
        MainMethodResult result = invokeMain("-print", "Customer Name", "123-456-7890", "098-765-4321",
                                                    "01/25/2020", "19:35", "01/31/2020", "23:30");

        assertThat(result.getExitCode(), equalTo(0));
    }
}