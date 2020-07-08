package edu.pdx.cs410J.scrubey;
import edu.pdx.cs410J.AbstractPhoneBill;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This class is represents a <code>PhoneBill</code>.
 */
public class PhoneBill extends AbstractPhoneBill<PhoneCall> {

    protected String customer;
    protected String fileName;
    protected ArrayList<PhoneCall> calls;

    /**
     * Builds a <code>PhoneBill</code> class object
     * @param customer The customer's name.  May be one or more words, and may contain
     *                 letters, numbers and symbols.
     */
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
    public ArrayList<PhoneCall> getPhoneCalls(){
        return this.calls;
    }

    /**
     * Sets the fileName data member
     * @param fileName the name of the textfile, as entered by the user on the command line
     */
    public void setFileName(String fileName){
        this.fileName = fileName;
    }

    /**
     * Getter method for the fileName data member
     * @return the filename associated with this phonebill object
     */
    public String getFileName(){
        return this.fileName;
    }
}
