package edu.pdx.cs410J.scrubey;

import java.util.ArrayList;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project1 {

  public static void main(String[] args) {
    ArrayList<String> CLargs = new ArrayList<>();  //command line arguments list
    ArrayList<String> CLopts = new ArrayList<>();  //command line options list
    PhoneCall call = new PhoneCall();  // Refer to one of Dave's classes so that we can be sure it is on the classpath

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
    if(CLargs.size() < 5)
      System.err.println("\nMissing command line arguments");
    if(CLargs.size() > 5)
      System.err.println("\nToo many command line arguments");

    //check for proper phone number format in 2nd and 3rd arguments
    //if format incorrect, print error and exit program
    for(int i = 1; i < 3; ++i) {
      if (!checkPhNumFormat(CLargs.get(i))){
        System.err.print("Phone number entered improperly");
        System.exit(1);
      }
    }
  }

  public static boolean checkPhNumFormat(String ph){
    boolean pass = false;
    for(int i = 0; i < 12; ++i){
      
    }

    return pass;
  }
}