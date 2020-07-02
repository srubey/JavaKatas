package edu.pdx.cs410J.scrubey;
import edu.pdx.cs410J.AbstractPhoneBill;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This class is represents a <code>PhoneBill</code>.
 */
public class PhoneBill extends AbstractPhoneBill<PhoneCall> {
    /**
     * Creates a new <code>PhoneBill</code>
     *
     * @param customer
     *        The customer's name.  May be one or more words, and may contain
     *        letters, numbers and symbols.
     * @param calls
     *        The data structure that contains all phone calls attributed to
     *        the given customer
     */
    
    protected String customer;
    protected ArrayList<PhoneCall> calls;

    protected PhoneBill(String customer){
        this.customer = customer;
        this.calls = new ArrayList<>();
    }

    /**
     * @return the customer's name
     */
    @Override
    public String getCustomer(){
       return this.customer;
    }

    /**
     * Adds the phone call object to the bill
     */
    @Override
    public void addPhoneCall(PhoneCall call){
        this.calls.add(call);
    }

    /**
     * @return the list of calls attributed to the bill
     */
    @Override
    public Collection<PhoneCall> getPhoneCalls(){
        return this.calls;
    }
}
