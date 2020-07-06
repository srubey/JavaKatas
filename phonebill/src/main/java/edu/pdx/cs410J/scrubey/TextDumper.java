package edu.pdx.cs410J.scrubey;

import edu.pdx.cs410J.PhoneBillDumper;

import java.io.IOException;

public class TextDumper implements PhoneBillDumper<PhoneBill> {
    protected PhoneBill bill;

    protected TextDumper(PhoneBill bp){
        this.bill = bp;
    }

    public void dump(PhoneBill bill) throws IOException{
        String customer = this.bill.getCustomer();

    }
}
