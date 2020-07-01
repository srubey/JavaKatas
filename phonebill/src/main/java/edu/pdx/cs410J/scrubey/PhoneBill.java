package edu.pdx.cs410J.scrubey;
import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.TreeSet;

import java.util.Collection;

public class PhoneBill extends AbstractPhoneBill<PhoneCall> {
    protected String customer;
    protected TreeSet<PhoneCall> calls;

    protected PhoneBill(String customer){
        this.customer = customer;
        this.calls = new TreeSet<>();
    }

    @Override
    public String getCustomer(){
       return null;
    }

    @Override
    public void addPhoneCall(PhoneCall call){
        ;
    }

    @Override
    public Collection<PhoneCall> getPhoneCalls(){

        return calls;
    }
}
