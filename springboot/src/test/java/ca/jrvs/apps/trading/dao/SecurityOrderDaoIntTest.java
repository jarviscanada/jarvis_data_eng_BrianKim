package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.domain.Trader;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
public class SecurityOrderDaoIntTest {

  @Autowired
  SecurityOrderDao securityOrderDao;

  @Autowired
  AccountDao accountDao;

  @Autowired
  TraderDao traderDao;

  @Autowired
  QuoteDao quoteDao;

  private SecurityOrder so1;
  private SecurityOrder so2;
  private SecurityOrder so3;

  @Before
  public void init() {
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

    Trader savedTrader1 = new Trader();
    savedTrader1.setFirst_name("Brian");
    savedTrader1.setLast_name("Kim");
    savedTrader1.setDob(Date.valueOf("1994-12-06"));
    savedTrader1.setCountry("Canada");
    savedTrader1.setEmail("kim.brian94@gmail.com");

    Trader savedTrader2 = new Trader();
    savedTrader2.setFirst_name("Bob");
    savedTrader2.setLast_name("Ross");
    savedTrader2.setDob(Date.valueOf("1942-10-29"));
    savedTrader2.setCountry("United States");
    savedTrader2.setEmail("bob_ross@gmail.com");
    traderDao.saveAll(Arrays.asList(savedTrader1, savedTrader2));

    Account savedAccount1 = new Account();
    savedAccount1.setTrader_id(1);
    savedAccount1.setAmount(1000d);

    Account savedAccount2 = new Account();
    savedAccount2.setTrader_id(2);
    savedAccount2.setAmount(2000d);
    accountDao.saveAll(Arrays.asList(savedAccount1, savedAccount2));

    so1 = new SecurityOrder();
    so2 = new SecurityOrder();

    so1.setAccount_id(1);
    so1.setStatus("UNFILLED");
    so1.setTicker("AAPL");
    so1.setSize(4);
    so1.setPrice(130d);
    so1.setNotes("some note");

    so2.setAccount_id(2);
    so2.setStatus("UNFILLED");
    so2.setTicker("AMZN");
    so2.setSize(6);
    so2.setPrice(250d);
    so2.setNotes("some note");
    securityOrderDao.saveAll(Arrays.asList(so1, so2));
  }

  @After
  public void clean() {
    securityOrderDao.deleteAll();
    accountDao.deleteAll();
    traderDao.deleteAll();
    quoteDao.deleteAll();
  }

  @Test
  public void findAllById() {
    List<SecurityOrder> orders = securityOrderDao.findAllById(Arrays.asList(1, 2));
    assertEquals(2, orders.size());

    try {
      orders = securityOrderDao.findAllById(Arrays.asList(1, 2, 3, 4));
      fail();
    } catch (Exception e) {
      assertTrue(true);
    }
  }

  @Test
  public void findAll() {
    List<SecurityOrder> orders = securityOrderDao.findAll();
    assertEquals(2, orders.size());
  }

  @Test
  public void findById() {
    Optional<SecurityOrder> optional = securityOrderDao.findById(2);
    if (optional.isPresent()) {
      SecurityOrder order = optional.get();
      assertEquals(2, order.getId().intValue());
      assertEquals("AMZN", order.getTicker());
      assertEquals(250d, order.getPrice(), 0);
    } else {
      fail();
    }

    optional = securityOrderDao.findById(3);
    if (optional.isPresent()) {
      fail();
    } else {
      assertTrue(true);
    }
  }

  @Test
  public void existsById() {
    assertTrue(securityOrderDao.existsById(1));
    assertTrue(securityOrderDao.existsById(2));
    assertFalse(securityOrderDao.existsById(3));
    assertFalse(securityOrderDao.existsById(100));
  }

  @Test
  public void deleteById() {
    securityOrderDao.deleteById(1);
    List<SecurityOrder> orders = securityOrderDao.findAll();
    assertEquals(1, orders.size());
    assertFalse(securityOrderDao.existsById(1));

    try {
      securityOrderDao.deleteById(100);
      fail();
    } catch (Exception e) {
      assertTrue(true);
    }
  }
}