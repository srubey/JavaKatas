package edu.pdx.cs410J.scrubey;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Scanner;

/**
 * The main class for the CS410J Phone Bill Project.
 * Includes utility methods for parsing command line arguments
 * and error checking.
 */
public class Project1 {
  /**
   * Main program that parses the command line, creates a
   * <code>PhoneBill</code> and <code>PhoneCall</code>, and
   * prints a description of the phone call to standard out
   * by invoking its <code>toString</code> method.
   * @param args command line arguments entered by the user
   */
  public static void main(String[] args) {
    ArrayList<String> CLargs = new ArrayList<>();  //command line arguments list
    ArrayList<String> CLopts = new ArrayList<>();  //command line options list
    boolean passErrChk = true;

    //split CL args into arguments and options
    CLargsToLists(args, CLargs, CLopts);

    //print error message if incorrect number of CL arguments
    passErrChk = chkNumberOfCLArgs(CLargs);
    if(!passErrChk) {
      System.err.println("\nIncorrect number of command line arguments");
      System.exit(1);
    }

    //print error message if CL options contain errors
    boolean optsOK = false;
    if(CLopts.size() > 0){
      optsOK = chkOptsForErrors(CLopts);
    }
    if(!optsOK)
      System.err.println("\nError detected in command line option");

    //if README flag is present, print file and exit program;
    //if cannot connect to file, print error message
    if(readMeFlag(CLopts)) {
      boolean found = openReadMe();
      if (!found)
        System.err.print("Could not connect to README file");
        System.exit(0);
    }

    //check phone number formatting
    passErrChk = chkPhoneArgs(CLargs);
    if(!passErrChk) {
      System.err.println("\nPhone number entered improperly");
      System.exit(1);
    }

    //check date formatting
    passErrChk = chkDateFormat(CLargs);
    if(!passErrChk) {
      System.err.println("\nDate entered improperly");
      System.exit(1);
    }

    //check timestamp formatting
    passErrChk = chkTimeFormat(CLargs);
    if(!passErrChk) {
      System.err.println("\nTime entered improperly");
      System.exit(1);
    }

    //sanity check
    //System.out.print(CLargs);
    //System.out.print(CLopts);

    //if all arguments are present w/out errors, assign args to variables
    String custName = CLargs.get(0);
    String caller = CLargs.get(1);
    String callee = CLargs.get(2);
    String sDate = CLargs.get(3);
    String sTime = CLargs.get(4);
    String eDate = CLargs.get(5);
    String eTime = CLargs.get(6);

    //create class objects
    PhoneCall call = new PhoneCall(caller, callee, sDate, sTime, eDate, eTime);
    PhoneBill bill = new PhoneBill(custName);

    //add call to phone bill
    bill.addPhoneCall(call);

    //print call info if -print flag is present
    if(printFlag(CLopts))
      System.out.print(call.toString());

    System.exit(0);
  }

  /**
   * Adds command line arguments and options to the appropriate lists
   * @param args all arguments as input via the command line
   * @param arguments the <code>ArrayList</code> that will contain arguments used to create PhoneBill and PhoneCall objects
   * @param options the <code>ArrayList</code> that will contain options/flags such as -README, -print
   */
  public static void CLargsToLists(String[] args, ArrayList arguments, ArrayList options){
    for (String arg : args) {
      if(arg.startsWith("-"))
        options.add(arg);
      else
        arguments.add(arg);
    }
  }

  /**
   * Verifies that the proper number of command line arguments have been input by the user
   * @param argList the <code>ArrayList</code> that contains all arguments entered by the user
   * @return true/false value indicating whether the user entered the proper number of arguments
   */
  public static boolean chkNumberOfCLArgs(ArrayList argList){
    boolean pass = true;

    if(argList.size() != 7)
      pass = false;

    return pass;
  }

  /**
   * Checks if any of the option flags (i.e. -README, -print) contains errors
   * @param options the <code>ArrayList</code> that contains all options/flags entered by the user
   * @return true/false value indicating whether the options/flags were entered correctly
   */
  public static boolean chkOptsForErrors(ArrayList options){
    boolean flagsOK = false;

    //check for misspelled/improper arguments
    if((options.size() == 2) && (options.contains("-README") && (options.contains("-print"))))
      flagsOK = true;
    else if((options.size() == 1) && (options.contains("-README") || (options.contains("-print"))))
      flagsOK = true;

    return flagsOK;
  }

  /**
   * Creates a set of acceptable digits 0 - 9 of type char
   * @return a <code>HashSet</code> containing all char digits 0 - 9
   */
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

  /**
   * Wrapper method for checkPhNumFormat()
   * @param argList the <code>ArrayList</code> that contains all arguments entered by the user
   * @return true/false value, as returned by checkPhNumFormat()
   */
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

  /**
   * Checks to see if phone number was entered in correct format
   * @param ph the <code>String</code> object containing the phone number
   * @param digits the <code>ArrayList</code> containing all proper char digits 0 - 9
   * @return true/false value indicating whether the phone number is formatted properly
   */
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

  /**
   * Wrapper method for chkDateArgs()
   * @param arguments the <code>ArrayList</code> that contains all arguments entered by the user
   * @return true/false value, as returned by chkDateArgs()
   */
  public static boolean chkDateFormat(ArrayList<String> arguments){
    boolean pass = true;

    //check the args at idx 3 and 5 for correct formatting
    for(int i = 3; i < 6; i += 2){
      pass = chkDateArgs(arguments.get(i));
    }

    return pass;
  }

  /**
   * Checks for proper formatting of date arguments
   * @param date <code>String</code> object containing the date entered by the user
   * @return returns true if date is formatted properly, otherwise false
   */
  public static boolean chkDateArgs(String date){
    boolean pass = true;

    //verify date has correct number of characters
    pass = chkDateLength(date);

    //verify date has only 2 slashes
    if(pass)
      pass = chkDateSlashes(date);

    //verify any char that's not '/' is a digit
    if(pass)
      pass = chkDateDigits(date);

    //verify month is formatted properly
    if(pass)
      pass = chkMonthFormat(date);

    //verify day is formatted properly
    if(pass)
      pass = chkDayFormat(date);

    return pass;
  }

  /**
   * Verifies that date <code>String</code>is proper number of characters
   * @param date <code>String</code> object containing the date entered by the user
   * @return true if date is in correct date range, false if not
   */
  public static boolean chkDateLength(String date){
    boolean pass = true;

    if(date.length() > 10 || date.length() < 6)
      pass = false;

    return pass;
  }

  /**
   * Verifies that there are only two '/'s in the date <code>String</code>
   * @param date <code>String</code> object containing the date entered by the user
   * @return true if there are two '/'s, false otherwise
   */
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

  /**
   * Verifies any <code>char</code> that's not a '/' is a digit 0 - 9
   * @param date <code>String</code> object containing the date entered by the user
   * @return true if all characters are correct combination of digits and '/'
   */
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

  /**
   * Verify the month in mm/dd/yyyy is in proper format
   * @param date <code>String</code> object containing the date entered by the user
   * @return true if month in proper format; can be digits 1 - 12 and include leading zeros
   *         false otherwise
   */
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

  /**
   * Verify the dd in mm/dd/yyyy is in proper format
   * @param date <code>String</code> object containing the date entered by the user
   * @return true if day in proper format; can be digits 1 - 31 and include leading zeros
   *         false otherwise
   */
  //verify day is in proper format
  public static boolean chkDayFormat(String date){
    boolean pass = false;
    Set<Character> digits = createDigitSet();
    boolean oneDigitDay = false;
    boolean twoDigitDay = false;
    int firstPos = 0;
    int secPos = 0;

    //find the index of the first '/'
    boolean found = false;
    for(int i = 0; i < date.length(); ++i){
      if(date.charAt(i) == '/' && found == false) {
        firstPos = i;
        found = true;
      }
    }

    //find the index of the second '/'
    for(int i = firstPos + 1; i < date.length(); ++i){
      if(date.charAt(i) == '/')
        secPos = i;
    }

    //find out whether it's a 1 or 2 digit day
    int daySize = secPos - firstPos;
    if(daySize == 2)
      oneDigitDay = true;
    else if(daySize == 3)
      twoDigitDay = true;

    //simple case: if 1 digit day and char is 1-9, ok
    if(oneDigitDay){
      if(digits.contains(date.charAt(firstPos + 1)) && date.charAt(firstPos + 1) != '0')
        pass = true;
    }

    //harder case: if 2 digit day...cases explore first digit of the 2-digit day
    else if(twoDigitDay) {
      switch (date.charAt(firstPos + 1)) {
        case '0':
          if (digits.contains(date.charAt(firstPos + 2)) && date.charAt(firstPos + 2) != '0')
            pass = true;
          break;
        case '1':
          if(digits.contains(date.charAt(firstPos + 2)))
            pass = true;
          break;
        case '2':
          if(digits.contains(date.charAt(firstPos + 2)))
            pass = true;
          break;
        case '3':
          if(date.charAt(firstPos + 2) == '0' || date.charAt(firstPos + 2) == '1')
            pass = true;
          break;
        default:
          pass = false;
      }
    }
    else
      pass = false;

    //final redundant check to verify the day is only 1 or 2 digits long
    if(!oneDigitDay && !twoDigitDay)
      pass = false;

    return pass;
  }

  /**
   * Wrapper method for chkTimeArgs()
   * @param arguments the <code>ArrayList</code> that contains all arguments entered by the user
   * @return true/false value, as returned by ckTimeArgs()
   */
  public static boolean chkTimeFormat(ArrayList<String> arguments){
    boolean pass = true;

    //check the args at idx 4 and 6 for correct formatting
    for(int i = 4; i < 7; i += 2){
      pass = chkTimeArgs(arguments.get(i));
    }

    return pass;
  }

  /**
   * Verifies proper formatting for the time arguments
   * @param time <code>String</code> object containing the date arguments, as entered by the user
   * @return true if time is formatted correctly, false otherwise.  Time can be 0:00 through 23:59.
   *         Leading zeros are accepted when applicable.
   */
  public static boolean chkTimeArgs(String time){
    boolean pass = true;
    int colonPos = 0;

    //make sure time is only 4 or 5 characters
    if(!(time.length() == 4 || time.length() == 5))
      pass = false;

    //make sure there's only one ':'
    if(pass) {
      int count = 0;
      for (int i = 0; i < time.length(); ++i) {
        if (time.charAt(i) == ':') {
          ++count;
          colonPos = i;
        }
      }
      if (count != 1)
        pass = false;
    }

    if(pass)
      pass = chkHoursFormat(time, colonPos);
    if(pass)
      pass = chkMinutesFormat(time, colonPos);

    return pass;
  }

  /**
   * Verifies hh in hh:mm is formatted correctly
   * @param time <code>String</code> object containing the date argument, as entered by the user
   * @param colonPos index of the ':' character
   * @return true if hh is formatted correct (0 - 23, leading zeros okay when applicable), false otherwise
   */
  public static boolean chkHoursFormat(String time, int colonPos){
    boolean pass = true;
    Set<Character> digits = createDigitSet();
    Set<Character> after20 = new HashSet<>(){{
      add('0');
      add('1');
      add('2');
      add('3');
    }};

    //verify hours is formatted properly
    //start with single digit
    if(colonPos == 1){
      if(!digits.contains(time.charAt(0)))
        pass = false;
    }
      //verify two-digit hours are formatted properly
    else if(colonPos == 2) {
      switch (time.charAt(0)) {
        case '0':
          if(!digits.contains(time.charAt(1)))
            pass = false;
          break;
        case '1':
          if(!digits.contains(time.charAt(1)))
            pass = false;
          break;
        case '2':
          if(!(after20.contains(time.charAt(1))))
            pass = false;
          break;
        default:
          pass = false;
      }
    }
    //if colon isn't in one of those two positions, timestamp is not valid
    else
      pass = false;

    return pass;
  }

  /**
   * Verifies mm in hh:mm is formatted correctly
   * @param time <code>String</code> object containing the date argument, as entered by the user
   * @param colonPos index of the ':' character
   * @return true if mm is formatted correctly (00 - 59), false otherwise
   */
  public static boolean chkMinutesFormat(String time, int colonPos){
    boolean pass = true;
    Set<Character> digits = createDigitSet();

    //verify there are two digits in minutes
    int count = 0;
    for(int i = colonPos+1; i < time.length(); ++i)
      ++count;
    if(count != 2)
      pass = false;

    //if passing to this point, check to make sure they're all numerical digits 0 - 59
    if(pass) {
      switch (time.charAt(colonPos + 1)) {
        case '0':
          if (!digits.contains(time.charAt(colonPos + 2)))
            pass = false;
          break;
        case '1':
          if (!digits.contains(time.charAt(colonPos + 2)))
            pass = false;
          break;
        case '2':
          if (!digits.contains(time.charAt(colonPos + 2)))
            pass = false;
          break;
        case '3':
          if (!digits.contains(time.charAt(colonPos + 2)))
            pass = false;
          break;
        case '4':
          if (!digits.contains(time.charAt(colonPos + 2)))
            pass = false;
          break;
        case '5':
          if (!digits.contains(time.charAt(colonPos + 2)))
            pass = false;
          break;
        default:
          pass = false;
      }
    }

    return pass;
  }

  /**
   * Determines whether -README flag is present in command line options entered by user
   * @param options the <code>ArrayList</code> that contains all options/flags entered by the user
   * @return true if -README flag is present, false otherwise
   */
  public static boolean readMeFlag(ArrayList options){
    boolean flagPresent = false;

    if(options.contains("-README"))
      flagPresent = true;

    return flagPresent;
  }

  /**
   * Opens and prints README file
   * @return true if README file is found and opened, false if file not found
   */
  public static boolean openReadMe() {
    Scanner readFile = null;
    String line = null;
    BufferedReader reader = null;
    InputStream readme = null;
    boolean found = true;

/*    try {
      readFile = new Scanner(new File("/Users/srubey/PortlandStateJavaSummer2020/phonebill/src/main/resources/edu/pdx/cs410J/scrubey/README.txt"));
    }
    catch(FileNotFoundException e) {
      found = false;
    }  */

    try {
      readme = Project1.class.getResourceAsStream("README.txt");
      reader = new BufferedReader(new InputStreamReader(readme));
      line = reader.readLine();
      while (line != null) {
        System.out.println(line);
        line = reader.readLine();
      }
      reader.close();
    } catch(IOException e){
      found = false;
    }

    System.out.print("\n");

    return found;
  }

  /**
   * Determines whether -print flag is present in command line options entered by user
   * @param options the <code>ArrayList</code> that contains all options/flags entered by the user
   * @return true if -print flag is present, false otherwise
   */
  //see if -print flag is present in CL options
  public static boolean printFlag(ArrayList options){
    boolean flagPresent = false;

    if(options.contains("-print"))
      flagPresent = true;

    return flagPresent;
  }
}
