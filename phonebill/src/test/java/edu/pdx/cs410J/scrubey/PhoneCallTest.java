package edu.pdx.cs410J.scrubey;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link PhoneCall} class.
 *
 * You'll need to update these unit tests as you build out you program.
 */
public class PhoneCallTest {
  @Test(expected = UnsupportedOperationException.class)
  public void getStartTimeStringNeedsToBeImplemented() {
  PhoneCall call = new PhoneCall("123-456-7890", "098-765-4321", "01/02/2020",
                                "19:39", "1/2/20", "1:03");
    call.getStartTimeString();
  }

  @Test
  public void initiallyAllPhoneCallsHaveTheSameCallee() {
    PhoneCall call = new PhoneCall("123-456-7890", "098-765-4321", "01/02/2020",
                                  "19:39", "1/2/20", "1:03");
    assertThat(call.getCallee(), containsString("not implemented"));
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
