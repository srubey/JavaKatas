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

  public int romanToNumber(String str)
  {
    int res = 0;

    for (int i = 0; i < str.length(); i++) {

      int s1 = value(str.charAt(i));

      if (i + 1 < str.length()) {
        int s2 = value(str.charAt(i + 1));

        if (s1 >= s2) {
          res = res + s1;
        }
        else {
          res = res + s2 - s1;
          i++;
        }
      }
      else {
        res = res + s1;
        i++;
      }
    }

    return res;
  }
  int value(char r)
  {
    if (r == 'I')
      return 1;
    if (r == 'V')
      return 5;
    if (r == 'X')
      return 10;
    if (r == 'L')
      return 50;
    if (r == 'C')
      return 100;
    if (r == 'D')
      return 500;
    if (r == 'M')
      return 1000;
    return -1;
  }
}