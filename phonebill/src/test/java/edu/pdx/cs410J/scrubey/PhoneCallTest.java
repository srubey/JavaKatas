package edu.pdx.cs410J.scrubey;

import org.junit.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link PhoneCall} class.  In addition to the JUnit annotations,
 * they also make use of the <a href="http://hamcrest.org/JavaHamcrest/">hamcrest</a>
 * matchers for more readable assertion statements.
 */

public class PhoneCallTest {
  @Test
  public void testGetters() {
    PhoneCall call = new PhoneCall("123-456-7890", "098-765-4321", "01/02/2020",
                                "19:39", "1/2/20", "1:03");
    assertThat(call.getCaller(), equalTo("123-456-7890"));
    assertThat(call.getCallee(), equalTo("098-765-4321"));
    assertThat(call.getStartDateString(), equalTo("01/02/2020"));
    assertThat(call.getStartTimeString(), equalTo("19:39"));
    assertThat(call.getEndDateString(), equalTo("1/2/20"));
    assertThat(call.getEndTimeString(), equalTo("1:03"));
  }

  @Test
  public void initiallyAllPhoneCallsHaveTheSameCallee() {
    PhoneCall call = new PhoneCall("123-456-7890", "098-765-4321", "01/02/2020",
                                  "19:39", "1/2/20", "1:03");
    assertThat(call.getCallee(), equalTo("098-765-4321"));
  }

  @Test
  public void testPhoneCallObjectCreation(){
    PhoneCall call = new PhoneCall("123-456-7890", "098-765-4321", "01/02/2020",
            "19:39", "1/2/20", "1:03");

    assertThat(call.getCaller(), equalTo("123-456-7890"));
    assertThat(call.getCallee(), equalTo("098-765-4321"));
    assertThat(call.getStartTimeString(), equalTo("19:39"));
    assertThat(call.getEndTimeString(), equalTo("1:03"));
  }
}
