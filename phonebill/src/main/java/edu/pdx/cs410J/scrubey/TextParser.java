package edu.pdx.cs410J.scrubey;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TextParser implements PhoneBillParser<PhoneBill> {
    public PhoneBill parse() throws ParserException{
        PhoneBill bill;
        String line = null;
        BufferedReader reader;
        InputStream readme;

        try {
            readme = Project2.class.getResourceAsStream("PhoneBill.txt");
            reader = new BufferedReader(new InputStreamReader(readme));
            line = reader.readLine();

            String customer = parseCustomer(line);
            bill = new PhoneBill(customer);

            while (line != null) {
                System.out.println(line);
                line = reader.readLine();
            }
            reader.close();
        } catch(IOException e){
            System.out.print("\nFile not found");
        }

        return bill;
    }

    public String parseCustomer(String line){
        return "Scott Rubey";
    }
}
