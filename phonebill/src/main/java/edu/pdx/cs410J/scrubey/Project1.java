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

    //add CL args and options to appropriate list
    for (String arg : args) {
      if(arg.startsWith("-"))
        CLopts.add(arg);
      else
        CLargs.add(arg);
    }

    //sanity check
    System.out.print(CLargs);
    System.out.print(CLopts);

    //print error message if incorrect number of CL arguments
    if(CLargs.size() < 5) {
      System.err.println("\nMissing command line arguments");
      passErrChk = false;
    }
    if(CLargs.size() > 5) {
      System.err.println("\nToo many command line arguments");
      passErrChk = false;
    }

    //check for proper phone number format in 2nd and 3rd arguments
    //if format incorrect, print error and exit program
    for(int i = 1; i < 3; ++i) {
      if (!checkPhNumFormat(CLargs.get(i))){
        System.err.print("\nPhone number entered improperly");
        passErrChk = false;
      }
    }

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

  public static void CLargsToLists(ArrayList CLargs, ArrayList CLopts){
    
  }
}