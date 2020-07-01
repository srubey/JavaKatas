package edu.pdx.cs410J.scrubey;

import org.junit.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link PhoneCall} class.
 *
 * You'll need to update these unit tests as you build out you program.
 */
public class PhoneCallTest {
  @Test
  public void getStartTimeStringNeedsToBeImplemented() {
  PhoneCall call = new PhoneCall("123-456-7890", "098-765-4321", "01/02/2020",
                                "19:39", "1/2/20", "1:03");
    assertThat(call.getStartTimeString(), equalTo("19:39"));
  }

  @Test
  public void initiallyAllPhoneCallsHaveTheSameCallee() {
    PhoneCall call = new PhoneCall("123-456-7890", "098-765-4321", "01/02/2020",
                                  "19:39", "1/2/20", "1:03");
    assertThat(call.getCallee(), equalTo("098-765-4321"));
  }

  @Test
  public void forProject1ItIsOkayIfGetStartTimeReturnsNull() {
    PhoneCall call = new PhoneCall("123-456-7890", "098-765-4321", "01/02/2020",
                                  "19:39", "1/2/20", "1:03");
    assertThat(call.getStartTime(), is(nullValue()));
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
