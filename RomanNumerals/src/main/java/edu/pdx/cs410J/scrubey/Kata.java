package edu.pdx.cs410J.scrubey;

/**
 * A class for getting started with a code kata
 *
 * Use IntelliJ's "Refactor | Rename..." command to change the name of this
 * class (and its tests).
 */
public class Kata {
                                                                                    

  public static void main(String[] args) {
    System.err.println("Missing command line arguments");
    System.exit(1);
  }

  public String intToRomanNum(int input){
    StringBuilder sb = new StringBuilder();

    while (input - 1000 > 0) {
      sb.append("M");
      input -= 1000;
    }


    return sb.toString();
  }
}