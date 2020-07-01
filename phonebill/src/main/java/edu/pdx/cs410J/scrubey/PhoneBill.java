package edu.pdx.cs410J.scrubey;
import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;
import java.util.ArrayList;

import java.util.TreeSet;

import java.util.Collection;

public class PhoneBill extends AbstractPhoneBill<PhoneCall> {
    protected String customer;
    protected ArrayList<PhoneCall> calls;

    protected PhoneBill(String customer){
        this.customer = customer;
        this.calls = new ArrayList<>();
    }

    @Override
    public String getCustomer(){
       return this.customer;
    }

    @Override
    public void addPhoneCall(PhoneCall call){
        this.calls.add(call);
    }

    @Override
    public Collection<PhoneCall> getPhoneCalls(){
        return this.calls;
    }
}
