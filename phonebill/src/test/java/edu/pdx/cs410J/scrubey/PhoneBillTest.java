package edu.pdx.cs410J.scrubey;

import org.junit.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link PhoneBill} class.
 */

public class PhoneBillTest {
    @Test
    public void testGetCustomer(){
        PhoneBill bill = new PhoneBill("Scott Rubey");

        assertThat(bill.getCustomer(), equalTo("Scott Rubey"));
    }

    @Test
    public void testListNotNull(){
        PhoneBill bill = new PhoneBill("Scott Rubey");

        assertThat(bill.getPhoneCalls(), not(nullValue()));
    }

    @Test
    public void testAddPhoneCall(){
        PhoneCall call = new PhoneCall("123-456-7890", "098-765-4321", "01/02/2020",
                                        "19:39", "1/2/20", "1:03");
        PhoneBill bill = new PhoneBill("Scott Rubey");
        bill.addPhoneCall(call);

        assertThat(bill.getPhoneCalls().isEmpty(), equalTo(false));
        assertThat(bill.getPhoneCalls().size(), equalTo(1));
    }
}