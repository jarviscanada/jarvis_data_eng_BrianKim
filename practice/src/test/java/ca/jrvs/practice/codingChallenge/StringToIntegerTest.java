package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class StringToIntegerTest {

  @Test
  public void atoiOne() {
    StringToInteger parser = new StringToInteger();
    String numStr = "1234";
    int num = parser.atoiOne(numStr);

    assertEquals(1234, num);

    String numStrWrong = "abcd";
    try {
      parser.atoiOne(numStrWrong);
      fail();
    } catch (Exception e) {
      assertTrue(true);
    }
  }

  @Test
  public void atoiTwo() {
    StringToInteger parser = new StringToInteger();
    String numStr = "12345";
    int num = parser.atoiTwo(numStr);

    assertEquals(12345, num);

    String numStrWrong = "abcd";
    try {
      parser.atoiTwo(numStrWrong);
      fail();
    } catch (Exception e) {
      assertTrue(true);
    }
  }
}