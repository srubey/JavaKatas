package edu.pdx.cs410J.scrubey;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class KataTest
{

  @Test
  public void canInstantiateKataClass() {
    new Kata();
  }

  @Test
  public void checkForThousands(){
    Kata kata = new Kata();
    int input = 2668;

    assertThat(kata.intToRomanNum(input), equalTo("MMDCLXVIII"));
  }
  @Test
  public void checkForTwoThousands(){
    Kata kata = new Kata();
    int input = 2449;
    assertThat(kata.intToRomanNum(input), equalTo("MMCDXLIX"));
  }
  @Test
  public void checkForBiggest(){
    Kata kata = new Kata();
    int input = 2999;
    assertThat(kata.intToRomanNum(input), equalTo("MMCMXCIX"));
  }
}
