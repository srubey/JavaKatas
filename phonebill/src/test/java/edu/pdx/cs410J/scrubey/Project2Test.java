package edu.pdx.cs410J.scrubey;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * A unit test for code in the <code>Project2</code> class.
 */

public class Project2Test {
  @Test
  public void readmeCanBeReadAsResource() throws IOException {
    try (
            InputStream readme = Project2.class.getResourceAsStream("README.txt");
    ) {
      assertThat(readme, not(nullValue()));
      BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
      String line = reader.readLine();
      assertThat(line, containsString("This program parses input"));
    }
  }

  @Test
  public void testArgumentLists(){
    String[] args = {"Scott Rubey", "123-456-7890", "098-765-4321", "01/02/2020", "19:39",
                    "1/2/20", "1:03", "-README", "-print"};
    ArrayList<String> arguments = new ArrayList<>();
    ArrayList<String> options = new ArrayList<>();

    Project2.CLargsToLists(args, arguments, options);
    assertThat(arguments, not(nullValue()));
    assertThat(options, not(nullValue()));
    assert(arguments.size() == 7);
    assert(options.size() == 2);
    assert(arguments.get(1) == "123-456-7890");
    assert(!arguments.contains("123-456-7899"));
    assert(options.get(0) == "-README");
    assert(!options.contains("-doesn't exist"));
  }

  @Test
  public void testNumberOfArgs(){
    ArrayList<String> sevenArgs = new ArrayList<>(Arrays.asList("Scott Rubey", "123-456-7890", "098-765-4321",
                                                              "01/02/2020", "19:39", "1/2/20", "1:03"));
    ArrayList<String> sixArgs = new ArrayList<>(Arrays.asList("Scott Rubey", "123-456-7890", "098-765-4321",
                                                               "19:39", "1/2/20", "1:03"));
    ArrayList<String> eightArgs = new ArrayList<>(Arrays.asList("Scott Rubey", "123-456-7890", "098-765-4321",
                                                              "01/02/2020", "19:39", "1/2/20", "1:03", "extra"));

    assertThat(Project2.chkNumberOfCLArgs(sevenArgs), equalTo(true));
    assertThat(Project2.chkNumberOfCLArgs(sixArgs), equalTo(false));
    assertThat(Project2.chkNumberOfCLArgs(eightArgs), equalTo(false));
  }

  @Test
  public void testDigitSet(){
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

    assertThat(Project2.createDigitSet(), not(nullValue()));
    assertThat(Project2.createDigitSet(), equalTo(digits));
  }

  @Test
  public void testChkPhoneArgs(){
    ArrayList<String> good = new ArrayList<>(Arrays.asList("Scott Rubey", "123-456-7890", "098-765-4321",
                                                          "01/02/2020", "19:39", "1/2/20", "1:03"));
    ArrayList<String> badFirst = new ArrayList<>(Arrays.asList("Scott Rubey", "123-4567890", "098-765-4321",
                                                              "01/02/2020", "19:39", "1/2/20", "1:03"));
    ArrayList<String> badSecond = new ArrayList<>(Arrays.asList("Scott Rubey", "123-456-7890", "0980-765-4321",
                                                                "01/02/2020", "19:39", "1/2/20", "1:03"));
    assertThat(Project2.chkPhoneArgs(good), equalTo(true));
    assertThat(Project2.chkPhoneArgs(badFirst), equalTo(false));
    assertThat(Project2.chkPhoneArgs(badSecond), equalTo(false));
  }

  @Test
  public void testPhNumFormat(){
    //create set of acceptable digits 0-9
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

    String good = "123-456-7890";
    String missingDash = "123456-78901";
    String tooLong = "123-456-78901";
    String tooShort = "123-456-789";
    String wrongChar = "123-4x6-7890";

    assertThat(Project2.checkPhNumFormat(good), equalTo(true));
    assertThat(Project2.checkPhNumFormat(missingDash), equalTo(false));
    assertThat(Project2.checkPhNumFormat(tooLong), equalTo(false));
    assertThat(Project2.checkPhNumFormat(tooShort), equalTo(false));
    assertThat(Project2.checkPhNumFormat(wrongChar), equalTo(false));
  }

  @Test
  public void testDateFormatWrapper(){
    ArrayList<String> good = new ArrayList<>(Arrays.asList("Scott Rubey", "123-456-7890", "098-765-4321",
            "01/02/2020", "19:39", "1/2/2020", "1:03"));

    assertThat(Project2.chkDateFormat(good), equalTo(true));
  }

  @Test
  public void testChkDateArgs(){
    String good = "01/02/2020";

    assertThat(Project2.chkDateArgs(good), equalTo(true));
  }

  @Test
  public void testDateLength(){
    String good1 = "1/2/2020";
    String good2 = "01/2/2020";
    String good3 = "01/02/2020";
    String good4 = "1/2/20";
    String bad2 = "01/02/20203";

    assertThat(Project2.chkDateLength(good1), equalTo(true));
    assertThat(Project2.chkDateLength(good2), equalTo(true));
    assertThat(Project2.chkDateLength(good3), equalTo(true));
    assertThat(Project2.chkDateLength(good4), equalTo(true));
    assertThat(Project2.chkDateLength(bad2), equalTo(false));
  }

  @Test
  public void testDateSlashes(){
    String good = "01/02/2020";
    String bad1 = "01/02/20/20";
    String bad2 = "01/022020";

    assertThat(Project2.chkDateSlashes(good), equalTo(true));
    assertThat(Project2.chkDateSlashes(bad1), equalTo(false));
    assertThat(Project2.chkDateSlashes(bad2), equalTo(false));
  }

  @Test
  public void testDateDigits(){
    String good = "01/02/2020";
    String bad1 = "01/02/2i20";
    String bad2 = "1/2/&020";

    assertThat(Project2.chkDateDigits(good), equalTo(true));
    assertThat(Project2.chkDateDigits(bad1), equalTo(false));
    assertThat(Project2.chkDateDigits(bad2), equalTo(false));
  }

  @Test
  public void testMonthFormat(){
    String good1 = "1/02/2020";
    String bad1 = "0/02/2020";
    String good2 = "01/02/2020";
    String bad2 = "21/02/2020";
    String bad3 = "a/02/2020";

    assertThat(Project2.chkMonthFormat(good1), equalTo(true));
    assertThat(Project2.chkMonthFormat(bad1), equalTo(false));
    assertThat(Project2.chkMonthFormat(good2), equalTo(true));
    assertThat(Project2.chkMonthFormat(bad2), equalTo(false));
    assertThat(Project2.chkMonthFormat(bad3), equalTo(false));
  }

  @Test
  public void testDayFormat(){
    String good1 = "1/2/2020";
    String good2 = "01/2/2020";
    String good3 = "1/02/2020";
    String good4 = "1/19/2020";
    String good5 = "1/29/2020";
    String good6 = "1/31/2020";
    String good7 = "01/02/2020";
    String bad1 = "1/32/2020";
    String bad2 = "1/a2/2020";
    String bad3 = "02/a9/2020";
    String bad4 = "02/122/020";

    assertThat(Project2.chkDayFormat(good1), equalTo(true));
    assertThat(Project2.chkDayFormat(good2), equalTo(true));
    assertThat(Project2.chkDayFormat(good3), equalTo(true));
    assertThat(Project2.chkDayFormat(good4), equalTo(true));
    assertThat(Project2.chkDayFormat(good5), equalTo(true));
    assertThat(Project2.chkDayFormat(good6), equalTo(true));
    assertThat(Project2.chkDayFormat(good7), equalTo(true));
    assertThat(Project2.chkDayFormat(bad1), equalTo(false));
    assertThat(Project2.chkDayFormat(bad2), equalTo(false));
    assertThat(Project2.chkDayFormat(bad3), equalTo(false));
    assertThat(Project2.chkDayFormat(bad4), equalTo(false));
  }

  @Test
  public void testYearFormat(){
    String good = "1/2/2020";
    String bad1 = "1/2/202";
    String bad2 = "1/2/20202";

    assertThat(Project2.chkYearFormat(good), equalTo(true));
    assertThat(Project2.chkYearFormat(bad1), equalTo(false));
    assertThat(Project2.chkYearFormat(bad2), equalTo(false));
  }

  @Test
  public void testChkTimeFormat(){
    ArrayList<String> good = new ArrayList<>(Arrays.asList("Scott Rubey", "123-456-7890", "098-765-4321",
            "01/02/2020", "19:39", "1/2/20", "1:03"));

    assertThat(Project2.chkTimeFormat(good), equalTo(true));
  }

  @Test
  public void testChkTimeArgs(){
    String good1 = "1:13";
    String good2 = "01:13";
    String bad1 = ":0113";
    String bad2 = "0113:";
    String bad3 = "a:13";
    String bad4 = "0a:13";

    //assertThat(Project2.chkTimeArgs(good1), equalTo(true));
    assertThat(Project2.chkTimeArgs(bad1), equalTo(false));
    assertThat(Project2.chkTimeArgs(bad2), equalTo(false));
    assertThat(Project2.chkTimeArgs(bad3), equalTo(false));
    assertThat(Project2.chkTimeArgs(bad4), equalTo(false));
  }

  @Test
  public void testChkHoursFormat(){
    String good1 = "0:35";
    String good2 = "01:35";
    String good3 = "23:59";
    String bad1 = "24:00";

    assertThat(Project2.chkHoursFormat(good1, 1), equalTo(true));
    assertThat(Project2.chkHoursFormat(good2, 2), equalTo(true));
    assertThat(Project2.chkHoursFormat(good3, 2), equalTo(true));
    assertThat(Project2.chkHoursFormat(bad1, 2), equalTo(false));
  }

  @Test
  public void testChkMinutesFormat(){
    String good1 = "12:59";
    String good2 = "12:01";
    String good3 = "1:59";
    String good4 = "1:01";
    String bad1 = "01:0";
    String bad2 = "1:000";

    assertThat(Project2.chkMinutesFormat(good1, 2), equalTo(true));
    assertThat(Project2.chkMinutesFormat(good2, 2), equalTo(true));
    assertThat(Project2.chkMinutesFormat(good3, 1), equalTo(true));
    assertThat(Project2.chkMinutesFormat(good4, 1), equalTo(true));
    assertThat(Project2.chkMinutesFormat(bad1, 2), equalTo(false));
    assertThat(Project2.chkMinutesFormat(bad2, 1), equalTo(false));
  }

  @Test
  public void testIsReadMeFlagPresent(){
    ArrayList<String> present = new ArrayList<>(Arrays.asList("-print", "-README"));
    ArrayList<String> notPresent = new ArrayList<>(Arrays.asList("-print"));
    ArrayList<String> empty = new ArrayList<>();

    assertThat(Project2.readMeFlag(present), equalTo(true));
    assertThat(Project2.readMeFlag(notPresent), equalTo(false));
    assertThat(Project2.readMeFlag(empty), equalTo(false));
  }

  @Test
  public void testIsTextFileFlagPresent(){
    ArrayList<String> present = new ArrayList<>(Arrays.asList("-print", "-textFile"));
    ArrayList<String> notPresent = new ArrayList<>(Arrays.asList("-print"));

    assertThat(Project2.textFileFlag(present), equalTo(true));
    assertThat(Project2.textFileFlag(notPresent), equalTo(false));
  }

  @Test
  public void testGetFileName(){
    ArrayList<String> args = new ArrayList<>(Arrays.asList("PhoneBill.txt", "Joe Schmoe", "123-456-7890", "098-765-4321",
                                                          "01/02/2020", "19:39", "1/2/20", "1:03"));
    ArrayList<String> opts = new ArrayList<>(Arrays.asList("-print", "-textFile"));

    assertThat(Project2.getFileName(args), equalTo("PhoneBill.txt"));
    assertThat(args.size(), equalTo(7));
  }

  @Test
  public void testOpenReadMe(){
    assertThat(Project2.openReadMe(), equalTo(true));
  }

  @Test
  public void testForOptionErrors(){
    ArrayList<String> twoGoodOpts = new ArrayList<>(Arrays.asList("-README", "-print"));
    ArrayList<String> twoOptsFirstBad = new ArrayList<>(Arrays.asList("-REDME", "-print"));
    ArrayList<String> twoOptsSecondBad = new ArrayList<>(Arrays.asList("-README", "-prin"));
    ArrayList<String> oneGoodOpt = new ArrayList<>(Arrays.asList("-README"));
    ArrayList<String> oneBadOpt = new ArrayList<>(Arrays.asList("-pint"));

    assertThat(Project2.chkOptsForErrors(twoGoodOpts), equalTo(true));
    assertThat(Project2.chkOptsForErrors(twoOptsFirstBad), equalTo(false));
    assertThat(Project2.chkOptsForErrors(twoOptsSecondBad), equalTo(false));
    assertThat(Project2.chkOptsForErrors(oneGoodOpt), equalTo(true));
    assertThat(Project2.chkOptsForErrors(oneBadOpt), equalTo(false));
  }

  @Test
  public void testIsPrintFlagPresent(){
    ArrayList<String> present = new ArrayList<>(Arrays.asList("-print", "-README"));
    ArrayList<String> notPresent = new ArrayList<>(Arrays.asList("-README"));
    ArrayList<String> empty = new ArrayList<>();

    assertThat(Project2.printFlag(present), equalTo(true));
    assertThat(Project2.printFlag(notPresent), equalTo(false));
    assertThat(Project2.printFlag(empty), equalTo(false));
  }
}
