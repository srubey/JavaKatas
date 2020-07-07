package edu.pdx.cs410J.scrubey;

import org.junit.*;

import java.io.*;

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
    public void testParserErrorChecks(){
        TextParser parser = new TextParser();
        String[] good = {"123-456-7890", "098-765-4321", "10/20/2020", "7:40", "10/21/2020", "00:06"};

        assertThat(parser.chkArgFormatting(good), equalTo(true));
    }
}