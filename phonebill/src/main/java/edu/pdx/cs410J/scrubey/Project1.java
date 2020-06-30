package edu.pdx.cs410J.scrubey;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project1 {

  public static void main(String[] args) {
    ArrayList<String> CLargs = new ArrayList<>();  //command line arguments list
    ArrayList<String> CLopts = new ArrayList<>();  //command line options list
    boolean passErrChk = true;

    //split CL args into arguments and options
    CLargsToLists(args, CLargs, CLopts);

    //print error message if incorrect number of CL arguments
    passErrChk = chkNumberOfCLArgs(CLargs);
    if(!passErrChk)
      System.err.println("\nIncorrect number of command line arguments");

    //print error message if CL options contain errors
    boolean optsOK = false;
    if(CLopts.size() > 0){
      optsOK = chkOptsForErrors(CLopts);
    }
    if(!optsOK)
      System.out.print("\nError detected in command line option");

    //check phone number formatting
    passErrChk = chkPhoneArgs(CLargs);
    if(!passErrChk)
      System.err.println("\nPhone number entered improperly");

    //check date formatting
    passErrChk = chkDateFormat(CLargs);
    if(!passErrChk)
      System.err.println("\nDate entered improperly");

    //sanity check
//    System.out.print(CLargs);
//    System.out.print(CLopts);

    if(!passErrChk)
      System.exit(1);

    //if all arguments are present w/out errors, assign args to variables
    String custName = CLargs.get(0);
    String caller = CLargs.get(1);
    String callee = CLargs.get(2);
    String sDate = CLargs.get(3);
    String sTime = CLargs.get(4);
    String eDate = CLargs.get(5);
    String eTime = CLargs.get(6);

    //create class objects
    PhoneCall call = new PhoneCall(caller, callee, sDate, sTime, eDate, eTime);  // Refer to one of Dave's classes so that we can be sure it is on the classpath

    //print README if flag present; if cannot read file, print error message
    if(readMeFlag(CLopts)) {
      boolean found = openReadMe();
      if (!found)
        System.out.print("Could not connect to README file");
    }

    //print call info if -print flag is present
    if(printFlag(CLopts))
      System.out.print(call.toString());
  }

  //add CL args and options to appropriate list
  public static void CLargsToLists(String[] args, ArrayList arguments, ArrayList options){
    for (String arg : args) {
      if(arg.startsWith("-"))
        options.add(arg);
      else
        arguments.add(arg);
    }
  }

  //verify proper number of command line arguments
  public static boolean chkNumberOfCLArgs(ArrayList argList){
    boolean pass = true;

    if(argList.size() != 7)
      pass = false;

    return pass;
  }

  //check whether option flags contain errors
  public static boolean chkOptsForErrors(ArrayList options){
    boolean flagsOK = false;

    //check for misspelled/improper arguments
    if((options.size() == 2) && (options.contains("-README") && (options.contains("-print"))))
      flagsOK = true;
    else if((options.size() == 1) && (options.contains("-README") || (options.contains("-print"))))
      flagsOK = true;

    return flagsOK;
  }

  //creates a set of acceptable digits 0 - 9 of type char
  public static Set<Character> createDigitSet(){
    Set<Character> digits = new HashSet<>(){{
      add('0');
      add('1');
      add('2');
      add('3');
      add('4');
      add('5');
      add('6');
      add('7');
      add('8');
      add('9');
    }};

    return digits;
  }

  //wrapper for checkPhNumFormat
  public static boolean chkPhoneArgs(ArrayList<String> argList){
    boolean pass = true;

    //create set of acceptable digits 0-9
    Set<Character> digits = createDigitSet();

    for(int i = 1; i < 3; ++i) {
      if (!checkPhNumFormat(argList.get(i), digits))
        pass = false;
    }

    return pass;
  }

  //check for proper phone number format in 2nd and 3rd arguments
  public static boolean checkPhNumFormat(String ph, Set digits){
    boolean pass = true;

    //if phone number is not proper length
    if(ph.length() != 12)
      pass = false;

    //check that the dash '-' is in the appropriate places
    if(pass && ((ph.charAt(3) != '-') || (ph.charAt(7) != '-')))
      pass = false;

    //if passing to this point and characters at non-dash positions aren't in range 0-9, return false
    if(pass) {
      for (int i = 0; i < 12; ++i) {
        if (i != 3 && i != 7 && !digits.contains(ph.charAt(i)))
          pass = false;
      }
    }

    return pass;
  }

  //wrapper for chkDateArgs
  public static boolean chkDateFormat(ArrayList<String> arguments){
    boolean pass = true;

    //check the args at idx 3 and 5 for correct formatting
    for(int i = 3; i < 6; i += 2){
      pass = chkDateArgs(arguments.get(i));
    }

    return pass;
  }

  //checks for proper formatting of date arguments
  public static boolean chkDateArgs(String date){
    Set<Character> digits = createDigitSet();
    boolean pass = true;
    int firstPos = 0;
    int secPos = 0;

    //verify date has correct number of characters
    pass = chkDateLength(date);

    //verify date has only 2 slashes
    pass = chkDateSlashes(date);

    //verify any char that's not '/' is a digit
    pass = chkDateDigits(date);

    //verify month if formatted properly
    pass = chkMonthFormat(date);

    //*****************************************

    switch(firstPos) {
      case 0:
        pass = false;
      case 1:
        //if single-digit month, must be months 1 - 9
        if(!(digits.contains(date.charAt(0)) && (date.charAt(0) != '0')))
          pass = false;
        //if the prior condition passes, the subsequent character must be a digit
        if(!digits.contains(date.charAt(2)))
          pass = false;
    }

    return pass;
  }

  //check to see that date is proper number of characters
  public static boolean chkDateLength(String date){
    boolean pass = true;

    if(date.length() > 10 || date.length() < 8)
      pass = false;

    return pass;
  }

  //check that there are only 2 '/'s in the date
  public static boolean chkDateSlashes(String date){
    boolean pass = true;
    int count = 0;

    for(int i = 0; i < date.length(); ++i){
      if(date.charAt(i) == '/')
        ++count;
    }

    if(count != 2)
      pass = false;

    return pass;
  }

  //check that any char not '/' is a digit
  public static boolean chkDateDigits(String date){
    boolean pass = true;
    Set<Character> digits = createDigitSet();

    for(int i = 0; i < date.length(); ++i){
      if(date.charAt(i) == '/')
        continue;

      //otherwise, if the character is not a numerical digit...
      else if(!(digits.contains(date.charAt(i))))
        pass = false;
    }

    return pass;
  }

  //verify month is in proper format
  public static boolean chkMonthFormat(String date){
    boolean pass = false;
    int slashPos = 0;
    Set<Character> digits = createDigitSet();

    //find first slash in date string
    for(int i = 0; i < 3; ++i){
      if(date.charAt(i) == '/')
       slashPos = i;
    }

    switch(slashPos){
      //if slash at first index, pass = false
      case 0:
        break;
      case 1:
        //if slash at index 1, digit must be 1 - 9
        if(digits.contains(date.charAt(0)) && date.charAt(0) != '0')
          pass = true;
        break;
      case 2:
        //if slash is at index 2, 1st digit must be 0 - 1
        //also verifies proper position of '/' character
        if((date.charAt(0) == '0' || date.charAt(1) == '1') && date.charAt(1) != '/') {
          //if first digit is '0', second digit can only be 1 - 9.  If it's not, flip 'pass' back to false
          if (date.charAt(0) == '0' && digits.contains(date.charAt(1)) && date.charAt(1) != '0')
            pass = true;
          //if first digit is a '1', second digit can only be 0 - 2
          else if(date.charAt(0) == '1' && (date.charAt(1) == '0' || date.charAt(1) == '1' || date.charAt(1) == '2'))
            pass = true;
        }
        break;
    }

    return pass;
  }

  //see if README flag is present in CL options
  public static boolean readMeFlag(ArrayList options){
    boolean flagPresent = false;

    if(options.contains("-README"))
      flagPresent = true;

    return flagPresent;
  }

  //open and print -README file
  public static boolean openReadMe(){
    Scanner readFile = null;
    boolean found = true;

    try {
      readFile = new Scanner(new File("/Users/srubey/PortlandStateJavaSummer2020/phonebill/src/main/resources/edu/pdx/cs410J/scrubey/README.txt"));
    }
    catch(FileNotFoundException e) {
      found = false;
    }

    if(readFile.hasNext()){
      String line = readFile.nextLine();
      System.out.print(line);
    }

    System.out.print("\n");

    return found;
  }

  //see if -print flag is present in CL options
  public static boolean printFlag(ArrayList options){
    boolean flagPresent = false;

    if(options.contains("-print"))
      flagPresent = true;

    return flagPresent;
  }
}