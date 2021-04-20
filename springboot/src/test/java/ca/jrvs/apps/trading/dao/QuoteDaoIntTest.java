package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.After;
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
public class QuoteDaoIntTest {

  @Autowired
  private QuoteDao quoteDao;

  @Before
  public void insertOne() {
    Quote q1 = new Quote();
    q1.setAskPrice(10d);
    q1.setAskSize(10);
    q1.setBidPrice(10.2d);
    q1.setBidSize(10);
    q1.setId("aapl");
    q1.setLastPrice(10.1d);

    Quote q2 = new Quote();
    q2.setAskPrice(10d);
    q2.setAskSize(10);
    q2.setBidPrice(10.2d);
    q2.setBidSize(10);
    q2.setId("amzn");
    q2.setLastPrice(10.1d);

    Quote q3 = new Quote();
    q3.setAskPrice(10d);
    q3.setAskSize(10);
    q3.setBidPrice(10.2d);
    q3.setBidSize(10);
    q3.setId("fb");
    q3.setLastPrice(10.1d);

    quoteDao.saveAll(Arrays.asList(q1, q2, q3));
  }

  @After
  public void deleteOne() {
    quoteDao.deleteAll();
  }

  @Test
  public void save() {
    Long count = quoteDao.count();
    assertEquals(3, count.longValue());
  }

  @Test
  public void saveAll() {
    Long count = quoteDao.count();
    assertEquals(3, count.longValue());
  }

  @Test
  public void findAll() {
    List<Quote> quotes = (List<Quote>) quoteDao.findAll();
    assertEquals(3, quotes.size());
    assertEquals("aapl", quotes.get(0).getId());
    assertEquals("amzn", quotes.get(1).getId());
    assertEquals("fb", quotes.get(2).getId());
  }

  @Test
  public void findById() {
    Quote quote = quoteDao.findById("amzn").get();
    assertEquals("amzn", quote.getId());
    assertEquals(10, quote.getAskSize().intValue());
    assertEquals(10, quote.getBidSize().intValue());
  }

  @Test
  public void existsById() {
    boolean idExists = quoteDao.existsById("fb");
    assertTrue(idExists);

    idExists = quoteDao.existsById("tsl");
    assertFalse(idExists);
  }

  @Test
  public void deleteById() {
    quoteDao.deleteById("amzn");
    List<Quote> quotes = (List<Quote>) quoteDao.findAll();
    assertEquals(2, quotes.size());
    assertEquals("aapl", quotes.get(0).getId());
    assertEquals("fb", quotes.get(1).getId());
  }

  @Test
  public void deleteAll() {
    quoteDao.deleteAll();
    List<Quote> quotes = (List<Quote>) quoteDao.findAll();
    assertEquals(0, quotes.size());
  }

  @Test
  public void count() {
    List<Quote> quotes = (List<Quote>) quoteDao.findAll();
    assertEquals(3, quotes.size());
  }
}