package edu.pdx.cs410J.scrubey;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project1 {

  public static void main(String[] args) {
    ArrayList<String> CLargs = new ArrayList<>();  //command line arguments list
    ArrayList<String> CLopts = new ArrayList<>();  //command line options list
    PhoneCall call = new PhoneCall();  // Refer to one of Dave's classes so that we can be sure it is on the classpath
    boolean passErrChk = true;

    //split CL args into arguments and options
    CLargsToLists(args, CLargs, CLopts);

    //print error message if incorrect number of CL arguments
    passErrChk = chkNumberOfCLArgs(CLargs);
    if(!passErrChk)
      System.err.println("\nIncorrect number of command line arguments");

    //check phone number formatting
    passErrChk = chkPhNumberFormat(CLargs);
    if(!passErrChk)
      System.err.println("\nPhone number entered improperly");

    //sanity check
    System.out.print(CLargs);
    System.out.print(CLopts);

    if(!passErrChk)
      System.exit(1);
  }

  public static boolean checkPhNumFormat(String ph){
    boolean pass = true;
    Set<Character> digits = new HashSet<Character>(){{
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

    if(argList.size() != 5)
      pass = false;

    return pass;
  }

  //check for proper phone number format in 2nd and 3rd arguments
  public static boolean chkPhNumberFormat(ArrayList<String> argList){
    boolean pass = true;

    for(int i = 1; i < 3; ++i) {
      if (!checkPhNumFormat(argList.get(i)))
        pass = false;
    }

    return pass;
  }
}