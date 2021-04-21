package ca.jrvs.apps.trading.service;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.TestConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class QuoteServiceIntTest {

  @Autowired
  private QuoteService quoteService;

  @Autowired
  private QuoteDao quoteDao;

  @Before
  public void setUp() {
    quoteDao.deleteAll();

    Quote q1 = new Quote();
    q1.setAskPrice(10d);
    q1.setAskSize(10);
    q1.setBidPrice(10.2d);
    q1.setBidSize(10);
    q1.setId("AAPL");
    q1.setLastPrice(10.1d);

    Quote q2 = new Quote();
    q2.setAskPrice(10d);
    q2.setAskSize(10);
    q2.setBidPrice(10.2d);
    q2.setBidSize(10);
    q2.setId("AMZN");
    q2.setLastPrice(10.1d);

    Quote q3 = new Quote();
    q3.setAskPrice(10d);
    q3.setAskSize(10);
    q3.setBidPrice(10.2d);
    q3.setBidSize(10);
    q3.setId("FB");
    q3.setLastPrice(10.1d);

    quoteDao.saveAll(Arrays.asList(q1, q2, q3));
  }

  @Test
  public void findIexQuoteByTicker() {
    IexQuote iexQuote = quoteService.findIexQuoteByTicker("amzn");
    assertNotNull(iexQuote);
    assertEquals("AMZN", iexQuote.getSymbol());

    try {
      quoteService.findIexQuoteByTicker("amzn2");
      fail();
    } catch (Exception e) {
      assertTrue(true);
    }
  }

  @Test
  public void updateMarketData() {
    quoteService.updateMarketData();
    List<Quote> quotes = quoteService.findAllQuotes();
    assertEquals(3, quotes.size());
    assertNotEquals(10d, quotes.get(0).getAskPrice());
    assertNotEquals(10, quotes.get(1).getBidSize().intValue());
  }

  @Test
  public void saveQuotes() {
    quoteService.saveQuotes(Arrays.asList("TSLA", "GME", "MSFT"));
    List<Quote> quotes = quoteService.findAllQuotes();
    assertEquals(6, quotes.size());
    assertEquals("TSLA", quotes.get(3).getTicker());
    assertEquals("GME", quotes.get(4).getTicker());
    assertEquals("MSFT", quotes.get(5).getTicker());

    try {
      quoteService.saveQuotes(Arrays.asList("this1", "is2", "wrong3"));
      fail();
    } catch (Exception e) {
      assertTrue(true);
    }
  }

  @Test
  public void saveQuote() {
    quoteService.saveQuote("TSLA");
    List<Quote> quotes = quoteService.findAllQuotes();
    assertEquals(4, quotes.size());
    assertEquals("TSLA", quotes.get(3).getTicker());

    try {
      quoteService.saveQuote("wrong123");
      fail();
    } catch (Exception e) {
      assertTrue(true);
    }
  }

  @Test
  public void findAllQuotes() {
    List<Quote> quotes = quoteService.findAllQuotes();
    assertEquals(3, quotes.size());
    assertEquals("AAPL", quotes.get(0).getTicker());
    assertEquals("AMZN", quotes.get(1).getTicker());
    assertEquals("FB", quotes.get(2).getTicker());
  }
}