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
            file = Project2.class.getResourceAsStream(Project2.getFileName());
            reader = new BufferedReader(new InputStreamReader(file));

            //parse customer's name, create new bill object under that name
            String customer = parseCustomer(reader);
            bill = new PhoneBill(customer);

            //find list of phone calls (if any), create phonecall objects to add to phonebill's list
            Boolean errorsFound = getCallInfo(reader, bill);

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

        //if "Customer name: " not present, print error message and exit
        if(!found){
            System.out.print("Improperly formatted phone bill");
            System.exit(1);
        }

        String name = splitStr[1];

        return name;
    }

    public Boolean getCallInfo(BufferedReader br, PhoneBill bill) throws IOException {
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

        //if headings not present in text file, print error and exit
        if(!foundHeadings){
           System.out.print("Improperly formatted phone bill");
           System.exit(1);
        }

        //move to next line, parse call info
        Boolean foundCall = false;
        String[] callInfo;
        line = br.readLine();
        while(line != null){
            //split string based on 1 or more tabs, 3 spaces, newline
            callInfo = line.split("\\t+|   |\\n");
            for(String arg:callInfo) {
                arg.trim();
            }

            //check parsed args for errors
            Boolean formatOK = chkArgFormatting(callInfo);
            if(!formatOK){
                System.out.print("PhoneBill information formatted incorrectly");
                System.exit(1);
            }

            addCallToBill(callInfo, bill);
            foundCall = true;

            line = br.readLine();
        }

        return foundCall;
    }

    public Boolean chkArgFormatting(String[] callInfo){
        Boolean pass = false;
        String caller = null;
        String callee = null;
        String startDate = null;
        String startTime = null;
        String endDate = null;
        String endTime = null;

        //check for proper number of arguments
        if(callInfo.length == 6)
            pass = true;

        if(pass) {
            caller = callInfo[0];
            callee = callInfo[1];
            startDate = callInfo[2];
            startTime = callInfo[3];
            endDate = callInfo[4];
            endTime = callInfo[5];
        }

        //verify caller/callee are formatted correctly
        if(pass)
            pass = Project2.checkPhNumFormat(caller);
        if(pass)
            pass = Project2.checkPhNumFormat(callee);

        //verify startDate/EndDate are formatted correctly
        if(pass)
            pass = Project2.chkDateArgs(startDate);
        if(pass)
            pass = Project2.chkDateArgs(endDate);

        //verify startTime/endTime are formatted correctly
        if(pass)
            pass = Project2.chkTimeArgs(startTime);
        if(pass)
            pass = Project2.chkTimeArgs(endTime);

        return pass;
    }

    public void addCallToBill(String[] callInfo, PhoneBill bill){
        //add args to new phonecall object
        String caller = callInfo[0];
        String callee = callInfo[1];
        String startDate = callInfo[2];
        String startTime = callInfo[3];
        String endDate = callInfo[4];
        String endTime = callInfo[5];

        PhoneCall call = new PhoneCall(caller, callee, startDate, startTime, endDate, endTime);
        bill.addPhoneCall(call);
    }

    public boolean fileExists(String fileName){
        InputStream file = Project2.class.getResourceAsStream(fileName);

        if(file == null)
            return false;

        return true;
    }
}
