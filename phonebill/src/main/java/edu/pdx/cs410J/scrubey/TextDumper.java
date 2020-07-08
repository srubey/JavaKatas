package edu.pdx.cs410J.scrubey;

import edu.pdx.cs410J.PhoneBillDumper;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;

public class TextDumper implements PhoneBillDumper<PhoneBill> {
    public void dump(PhoneBill bill) throws IOException{
        String customer = bill.getCustomer();
        File file = new File("/Users/srubey/PortlandStateJavaSummer2020/phonebill/src/main/resources/edu/pdx/cs410J/scrubey/"
                                        + Project2.getFileName());
//        File file = new File(Project2.getFileName());
        FileWriter writer = null;

        try{
            //**if file not yet created
            writer = new FileWriter(file);

            writer.write("Customer name: " + bill.getCustomer() + "\n\n");
            writer.write("Caller#        Callee#\t\t  Start Date\tStart Time\tEnd Date\tEnd Time\n");

            //iterate through each phone call, extracting and adding data to file
            for(int i = 0; i < bill.getPhoneCalls().size(); ++i) {
                String caller = bill.getPhoneCalls().get(i).callerNumber;
                String callee = bill.getPhoneCalls().get(i).calleeNumber;
                String sd = bill.getPhoneCalls().get(i).startDate;
                String st = bill.getPhoneCalls().get(i).startTime;
                String ed = bill.getPhoneCalls().get(i).endDate;
                String et = bill.getPhoneCalls().get(i).endTime;

                writer.write(caller + "   " + callee + "   " + sd);

                //formatting
                if(sd.length() < 10)
                    writer.write("\t");

                writer.write("\t" + st + "\t\t" + ed + "\t" + et + "\n");
            }

            file.createNewFile();
        }catch(IOException e){
            System.out.print("\nText File error");
        }finally{
            writer.close();
        }
    }
}
