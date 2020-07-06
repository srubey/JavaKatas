package edu.pdx.cs410J.scrubey;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;

public class TextParser implements PhoneBillParser<PhoneBill> {
    public PhoneBill parse() throws ParserException{
        PhoneBill bill = new PhoneBill("Scott Rubey");
        return bill;
    }
}
