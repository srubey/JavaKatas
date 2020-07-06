package edu.pdx.cs410J.scrubey;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TextParser implements PhoneBillParser<PhoneBill> {
    public PhoneBill parse() throws ParserException{
        PhoneBill bill = null;
        BufferedReader reader;
        InputStream file;

        try {
            file = Project2.class.getResourceAsStream("PhoneBill.txt");
            reader = new BufferedReader(new InputStreamReader(file));

            //parse customer's name, create new bill object under that name
            String customer = parseCustomer(reader);
            bill = new PhoneBill(customer);



            reader.close();
        } catch(IOException e){
            System.out.print("\nFile not found");
        }

        return bill;
    }

    /**
     * Parses the customer's name from the text file
     * @param br BufferedReader object for reading text file
     * @return customer's name
     */
    public String parseCustomer(BufferedReader br) throws IOException {
        String[] splitStr = null;
        Boolean found = false;

        String line = br.readLine();
        while(line != null && !found) {
            if (line.contains("Customer name: ")) {
                splitStr = line.split(": ", 2);
                found = true;
            }
            line = br.readLine();
        }

        String name = splitStr[1];

        return name;
    }
}
