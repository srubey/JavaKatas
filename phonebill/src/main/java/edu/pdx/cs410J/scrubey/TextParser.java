package edu.pdx.cs410J.scrubey;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TextParser implements PhoneBillParser<PhoneBill> {
    public PhoneBill parse() throws ParserException {
        PhoneBill bill = null;
        BufferedReader reader;
        InputStream file;

        try {
            file = Project2.class.getResourceAsStream("PhoneBill.txt");
            reader = new BufferedReader(new InputStreamReader(file));

            //parse customer's name, create new bill object under that name
            String customer = parseCustomer(reader);
            bill = new PhoneBill(customer);

            //find list of phone calls (if any), create phonecall objects to add to phonebill's list
            Boolean callsFound = findCalls(reader, bill);

            reader.close();
        } catch (IOException e) {
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
        while (line != null && !found) {
            if (line.contains("Customer name: ")) {
                splitStr = line.split(": ", 2);
                found = true;
            }

            if(!found)
                line = br.readLine();
        }

        String name = splitStr[1];

        return name;
    }

    public Boolean findCalls(BufferedReader br, PhoneBill bill) throws IOException {
        //find section of bill that has field headings
        Boolean foundHeadings = false;
        String line = br.readLine();
        while(line != null && !foundHeadings) {
            if (line.contains("Caller#") && line.contains("Callee#") && line.contains("Start Date")
                    && line.contains("Start Time") && line.contains("End Date")
                    && line.contains("End Time")) {
                foundHeadings = true;
            }
            if(!foundHeadings)
                line = br.readLine();
        }

        //parse call info
        Boolean foundCall = false;
        String[] callInfo = null;
        line = br.readLine();
        while(line != null){
            //split string based on 1 or more tabs, 3 spaces, newline
            callInfo = line.split("\\t+|   |\\n");
            for(String arg:callInfo) {
                arg.trim();
            }

            //***error check arguments i.e. formatting

            //add args to new phonecall object
            String caller = callInfo[0];
            String callee = callInfo[1];
            String startDate = callInfo[2];
            String startTime = callInfo[3];
            String endDate = callInfo[4];
            String endTime = callInfo[5];

            PhoneCall call = new PhoneCall(caller, callee, startDate, startTime, endDate, endTime);
            bill.addPhoneCall(call);
            foundCall = true;

            line = br.readLine();
        }

        return foundCall;
    }
}
