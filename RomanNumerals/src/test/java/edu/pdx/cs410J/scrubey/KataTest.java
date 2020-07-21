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
    int input = 3001;

    assertThat(kata.intToRomanNum(input), equalTo("MMMI"));
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

  @Test
  public void checkFor1(){
    Kata kata = new Kata();
    int input = 1;
    assertThat(kata.intToRomanNum(input), equalTo("I"));
  }

  @Test
  public void checkFor5(){
    Kata kata = new Kata();
    int input = 5;
    assertThat(kata.intToRomanNum(input), equalTo("V"));
  }

  @Test
  public void checkFor10(){
    Kata kata = new Kata();
    int input = 10;
    assertThat(kata.intToRomanNum(input), equalTo("X"));
  }

  @Test
  public void checkFor50(){
    Kata kata = new Kata();
    int input = 50;
    assertThat(kata.intToRomanNum(input), equalTo("L"));
  }

  @Test
  public void checkFor100(){
    Kata kata = new Kata();
    int input = 100;
    assertThat(kata.intToRomanNum(input), equalTo("C"));
  }

  @Test
  public void checkFor500(){
    Kata kata = new Kata();
    int input = 500;
    assertThat(kata.intToRomanNum(input), equalTo("D"));
  }

  @Test
  public void checkFor1000(){
    Kata kata = new Kata();
    int input = 1000;
    assertThat(kata.intToRomanNum(input), equalTo("M"));
  }

  @Test
  public void checkForFiveHundred(){
    Kata kata = new Kata();
    int input = 591;
    assertThat(kata.intToRomanNum(input), equalTo("DXCI"));
  }
}
