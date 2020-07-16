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

    while (input - 1000 >= 0) {
      sb.append("M");
      input -= 1000;
    }
    while (input - 900 >= 0) {
      sb.append("CM");
      input -= 900;
    }
    while (input - 500 >= 0) {
      sb.append("D");
      input -= 500;
    }
    while (input - 400 >= 0) {
      sb.append("CD");
      input -= 400;
    }
    while (input - 100 >= 0) {
      sb.append("C");
      input -= 100;
    }
    while (input - 90 >= 0) {
      sb.append("XC");
      input -= 90;
    }
    while (input - 50 >= 0) {
      sb.append("L");
      input -= 50;
    }
    while (input - 40 >= 0) {
      sb.append("XL");
      input -= 40;
    }
    while (input - 10 >= 0) {
      sb.append("X");
      input -= 10;
    }
    while (input - 9 >= 0) {
      sb.append("IX");
      input -= 9;
    }
    while (input - 5 >= 0) {
      sb.append("V");
      input -= 5;
    }
    while (input - 4 >= 0) {
      sb.append("IV");
      input -= 4;
    }
    while (input - 1 >= 0) {
      sb.append("I");
      input -= 1;
    }
    return sb.toString();
  }
}