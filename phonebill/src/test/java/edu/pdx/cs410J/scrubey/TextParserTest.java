package edu.pdx.cs410J.scrubey;

import edu.pdx.cs410J.ParserException;
import org.junit.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link TextParser} class.  In addition to the JUnit annotations,
 * they also make use of the <a href="http://hamcrest.org/JavaHamcrest/">hamcrest</a>
 * matchers for more readable assertion statements.
 */

public class TextParserTest {
    @Test
    public void testParseCustomer() throws IOException {
        TextParser parser = new TextParser();

        BufferedReader reader = null;
        InputStream file = null;

        try {
            file = Project2.class.getResourceAsStream("PhoneBill.txt");
            reader = new BufferedReader(new InputStreamReader(file));
            assertThat(parser.parseCustomer(reader), equalTo("Scott Rubey"));
            reader.close();
        } catch(IOException e){
            System.out.print("\nFile not found");
        }
    }

    @Test
    public void testChkArgFormatting(){
        TextParser parser = new TextParser();
        String[] good = {"123-456-7890", "098-765-4321", "01/02/2020", "19:39", "1/2/2020", "1:03"};
        String[] badFirst = {"123-4567890", "098-765-4321", "01/02/2020", "19:39", "1/2/20", "1:03"};
        String[] badSecond = {"123-456-7890", "0980-765-4321", "01/02/2020", "19:39", "1/2/20", "1:03"};

        assertThat(parser.chkArgFormatting(good), equalTo(true));
        assertThat(parser.chkArgFormatting(badFirst), equalTo(false));
        assertThat(parser.chkArgFormatting(badSecond), equalTo(false));
    }

    @Test
    public void testParserErrorChecks(){
        TextParser parser = new TextParser();
        String[] good = {"123-456-7890", "098-765-4321", "10/20/2020", "7:40", "10/21/2020", "00:06"};
        String[] bad1 = {"123-4567890", "098-765-4321", "10/20/2020", "7:40", "10/21/2020", "00:06"};
        String[] bad2 = {"123-456-7890", "098-765-4321", "10/20/2020", "7:40", "10/21/2020"};

        assertThat(parser.chkArgFormatting(good), equalTo(true));
        assertThat(parser.chkArgFormatting(bad1), equalTo(false));
        assertThat(parser.chkArgFormatting(bad2), equalTo(false));
    }

    @Test
    public void testFileExists(){
        TextParser parser = new TextParser();
        String goodFileName = "PhoneBill.txt";
        String badFileName = ("MyFile.txt");

        assertThat(parser.fileExists(goodFileName), equalTo(true));
        assertThat(parser.fileExists(badFileName), equalTo(false));
    }
}