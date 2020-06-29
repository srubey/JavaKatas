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
      assertThat(line, containsString("This is a README file!"));
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
}
