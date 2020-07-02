package edu.pdx.cs410J.scrubey;
import java.util.ArrayList;
import java.util.Arrays;

import edu.pdx.cs410J.AbstractPhoneBill;
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
 * A unit test for code in the <code>Project1</code> class.  This is different
 * from {@link Project1IT} which is an integration test (and can handle the calls
 * to {@link System#exit(int)} and the like.
 */

public class Project1Test {
  @Test
  public void readmeCanBeReadAsResource() throws IOException {
    try (
      InputStream readme = Project1.class.getResourceAsStream("README.txt");
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

    Project1.CLargsToLists(args, arguments, options);
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

    assertThat(Project1.chkNumberOfCLArgs(sevenArgs), equalTo(true));
    assertThat(Project1.chkNumberOfCLArgs(sixArgs), equalTo(false));
    assertThat(Project1.chkNumberOfCLArgs(eightArgs), equalTo(false));
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

    assertThat(Project1.createDigitSet(), not(nullValue()));
    assertThat(Project1.createDigitSet(), equalTo(digits));
  }

  @Test
  public void testChkPhoneArgs(){
    ArrayList<String> good = new ArrayList<>(Arrays.asList("Scott Rubey", "123-456-7890", "098-765-4321",
                                                          "01/02/2020", "19:39", "1/2/20", "1:03"));
    ArrayList<String> badFirst = new ArrayList<>(Arrays.asList("Scott Rubey", "123-4567890", "098-765-4321",
                                                              "01/02/2020", "19:39", "1/2/20", "1:03"));
    ArrayList<String> badSecond = new ArrayList<>(Arrays.asList("Scott Rubey", "123-456-7890", "0980-765-4321",
                                                                "01/02/2020", "19:39", "1/2/20", "1:03"));
    assertThat(Project1.chkPhoneArgs(good), equalTo(true));
    assertThat(Project1.chkPhoneArgs(badFirst), equalTo(false));
    assertThat(Project1.chkPhoneArgs(badSecond), equalTo(false));
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

    assertThat(Project1.checkPhNumFormat(good, digits), equalTo(true));
    assertThat(Project1.checkPhNumFormat(missingDash, digits), equalTo(false));
    assertThat(Project1.checkPhNumFormat(tooLong, digits), equalTo(false));
    assertThat(Project1.checkPhNumFormat(tooShort, digits), equalTo(false));
    assertThat(Project1.checkPhNumFormat(wrongChar, digits), equalTo(false));
  }

  @Test
  public void testDateFormatWrapper(){
    ArrayList<String> good = new ArrayList<>(Arrays.asList("Scott Rubey", "123-456-7890", "098-765-4321",
            "01/02/2020", "19:39", "1/2/20", "1:03"));

    assertThat(Project1.chkDateFormat(good), equalTo(true));
  }

  @Test
  public void testChkDateArgs(){
    String good = "01/02/2020";

    assertThat(Project1.chkDateArgs(good), equalTo(true));
  }

  @Test
  public void testDateLength(){
    String good1 = "1/2/2020";
    String good2 = "01/2/2020";
    String good3 = "01/02/2020";
    String good4 = "1/2/20";
    String bad2 = "01/02/20203";

    assertThat(Project1.chkDateLength(good1), equalTo(true));
    assertThat(Project1.chkDateLength(good2), equalTo(true));
    assertThat(Project1.chkDateLength(good3), equalTo(true));
    assertThat(Project1.chkDateLength(good4), equalTo(true));
    assertThat(Project1.chkDateLength(bad2), equalTo(false));
  }

  @Test
  public void testDateSlashes(){
    String good = "01/02/2020";
    String bad1 = "01/02/20/20";
    String bad2 = "01/022020";

    assertThat(Project1.chkDateSlashes(good), equalTo(true));
    assertThat(Project1.chkDateSlashes(bad1), equalTo(false));
    assertThat(Project1.chkDateSlashes(bad2), equalTo(false));
  }

  @Test
  public void testDateDigits(){
    String good = "01/02/2020";
    String bad1 = "01/02/2i20";
    String bad2 = "1/2/&020";

    assertThat(Project1.chkDateDigits(good), equalTo(true));
    assertThat(Project1.chkDateDigits(bad1), equalTo(false));
    assertThat(Project1.chkDateDigits(bad2), equalTo(false));
  }

  @Test
  public void testMonthFormat(){
    String good1 = "1/02/2020";
    String bad1 = "0/02/2020";
    String good2 = "01/02/2020";
    String bad2 = "21/02/2020";
    String bad3 = "a/02/2020";

    assertThat(Project1.chkMonthFormat(good1), equalTo(true));
    assertThat(Project1.chkMonthFormat(bad1), equalTo(false));
    assertThat(Project1.chkMonthFormat(good2), equalTo(true));
    assertThat(Project1.chkMonthFormat(bad2), equalTo(false));
    assertThat(Project1.chkMonthFormat(bad3), equalTo(false));
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

    assertThat(Project1.chkDayFormat(good1), equalTo(true));
    assertThat(Project1.chkDayFormat(good2), equalTo(true));
    assertThat(Project1.chkDayFormat(good3), equalTo(true));
    assertThat(Project1.chkDayFormat(good4), equalTo(true));
    assertThat(Project1.chkDayFormat(good5), equalTo(true));
    assertThat(Project1.chkDayFormat(good6), equalTo(true));
    assertThat(Project1.chkDayFormat(good7), equalTo(true));
    assertThat(Project1.chkDayFormat(bad1), equalTo(false));
    assertThat(Project1.chkDayFormat(bad2), equalTo(false));
    assertThat(Project1.chkDayFormat(bad3), equalTo(false));
    assertThat(Project1.chkDayFormat(bad4), equalTo(false));
  }

  @Test
  public void testChkTimeFormat(){
    ArrayList<String> good = new ArrayList<>(Arrays.asList("Scott Rubey", "123-456-7890", "098-765-4321",
            "01/02/2020", "19:39", "1/2/20", "1:03"));

    assertThat(Project1.chkTimeFormat(good), equalTo(true));
  }

  @Test
  public void testChkTimeArgs(){
    String good1 = "1:13";
    String good2 = "01:13";
    String bad1 = ":0113";
    String bad2 = "0113:";
    String bad3 = "a:13";
    String bad4 = "0a:13";

    //assertThat(Project1.chkTimeArgs(good1), equalTo(true));
    assertThat(Project1.chkTimeArgs(bad1), equalTo(false));
    assertThat(Project1.chkTimeArgs(bad2), equalTo(false));
    assertThat(Project1.chkTimeArgs(bad3), equalTo(false));
    assertThat(Project1.chkTimeArgs(bad4), equalTo(false));
  }

  @Test
  public void testChkHoursFormat(){
    String good1 = "0:35";
    String good2 = "01:35";
    String good3 = "23:59";
    String bad1 = "24:00";

    assertThat(Project1.chkHoursFormat(good1, 1), equalTo(true));
    assertThat(Project1.chkHoursFormat(good2, 2), equalTo(true));
    assertThat(Project1.chkHoursFormat(good3, 2), equalTo(true));
    assertThat(Project1.chkHoursFormat(bad1, 2), equalTo(false));
  }

  @Test
  public void testChkMinutesFormat(){
    String good1 = "12:59";
    String good2 = "12:01";
    String good3 = "1:59";
    String good4 = "1:01";
    String bad1 = "01:0";
    String bad2 = "1:000";

    assertThat(Project1.chkMinutesFormat(good1, 2), equalTo(true));
    assertThat(Project1.chkMinutesFormat(good2, 2), equalTo(true));
    assertThat(Project1.chkMinutesFormat(good3, 1), equalTo(true));
    assertThat(Project1.chkMinutesFormat(good4, 1), equalTo(true));
    assertThat(Project1.chkMinutesFormat(bad1, 2), equalTo(false));
    assertThat(Project1.chkMinutesFormat(bad2, 1), equalTo(false));
  }

  @Test
  public void testIsReadMeFlagPresent(){
    ArrayList<String> present = new ArrayList<>(Arrays.asList("-print", "-README"));
    ArrayList<String> notPresent = new ArrayList<>(Arrays.asList("-print"));
    ArrayList<String> empty = new ArrayList<>();

    assertThat(Project1.readMeFlag(present), equalTo(true));
    assertThat(Project1.readMeFlag(notPresent), equalTo(false));
    assertThat(Project1.readMeFlag(empty), equalTo(false));
  }

  @Test
  public void testOpenReadMe(){
    assertThat(Project1.openReadMe(), equalTo(true));
  }

  @Test
  public void testForOptionErrors(){
    ArrayList<String> twoGoodOpts = new ArrayList<>(Arrays.asList("-README", "-print"));
    ArrayList<String> twoOptsFirstBad = new ArrayList<>(Arrays.asList("-REDME", "-print"));
    ArrayList<String> twoOptsSecondBad = new ArrayList<>(Arrays.asList("-README", "-prin"));
    ArrayList<String> oneGoodOpt = new ArrayList<>(Arrays.asList("-README"));
    ArrayList<String> oneBadOpt = new ArrayList<>(Arrays.asList("-pint"));

    assertThat(Project1.chkOptsForErrors(twoGoodOpts), equalTo(true));
    assertThat(Project1.chkOptsForErrors(twoOptsFirstBad), equalTo(false));
    assertThat(Project1.chkOptsForErrors(twoOptsSecondBad), equalTo(false));
    assertThat(Project1.chkOptsForErrors(oneGoodOpt), equalTo(true));
    assertThat(Project1.chkOptsForErrors(oneBadOpt), equalTo(false));
  }

  @Test
  public void testIsPrintFlagPresent(){
    ArrayList<String> present = new ArrayList<>(Arrays.asList("-print", "-README"));
    ArrayList<String> notPresent = new ArrayList<>(Arrays.asList("-README"));
    ArrayList<String> empty = new ArrayList<>();

    assertThat(Project1.printFlag(present), equalTo(true));
    assertThat(Project1.printFlag(notPresent), equalTo(false));
    assertThat(Project1.printFlag(empty), equalTo(false));
  }
}
