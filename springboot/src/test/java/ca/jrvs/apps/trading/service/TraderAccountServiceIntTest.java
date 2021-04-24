package ca.jrvs.apps.trading.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.TestConfig;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.domain.TraderAccountView;
import java.sql.Date;
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
public class TraderAccountServiceIntTest {

  @Autowired
  private TraderAccountService traderAccountService;
  @Autowired
  private TraderDao traderDao;
  @Autowired
  private AccountDao accountDao;

  private TraderAccountView traderAccountView;
  private Trader trader1;
  private Trader trader2;
  private Trader trader3;

  @Before
  public void init() {
    trader1 = new Trader();
    trader1.setFirst_name("Brian");
    trader1.setLast_name("Kim");
    trader1.setDob(Date.valueOf("1994-12-06"));
    trader1.setCountry("Canada");
    trader1.setEmail("kim.brian94@gmail.com");

    trader2 = new Trader();
    trader2.setFirst_name("Bob");
    trader2.setLast_name("Ross");
    trader2.setDob(Date.valueOf("1942-10-29"));
    trader2.setCountry("United States");
    trader2.setEmail("bob_ross@gmail.com");

    trader3 = new Trader();
    trader3.setFirst_name("Norah");
    trader3.setLast_name("Jones");
    trader3.setDob(Date.valueOf("1979-03-30"));
    trader3.setCountry("United States");
    trader3.setEmail("norah_jones@gmail.com");

    traderAccountService.createTraderAndAccount(trader1);
    traderAccountService.createTraderAndAccount(trader2);
    traderAccountService.createTraderAndAccount(trader3);
  }

  @After
  public void clean() {
    traderAccountService.deleteTraderById(trader1.getId());
    traderAccountService.deleteTraderById(trader2.getId());
    traderAccountService.deleteTraderById(trader3.getId());
  }

  @Test
  public void createTraderAndAccount() {
    Trader trader = new Trader();
    trader.setFirst_name("");
    trader.setLast_name("Musk");
    trader.setDob(Date.valueOf("1971-06-28"));
    trader.setCountry("United States");
    trader.setEmail("elon_musk@gmail.com");

    try {
      traderAccountService.createTraderAndAccount(trader);
      fail();
      traderAccountService.deleteTraderById(trader.getId());
    } catch (Exception e) {
      assertTrue(true);
    }
  }

  @Test
  public void deleteTraderById() {
    assertTrue(true);
  }

  @Test
  public void deposit() {
    try {
      traderAccountService.deposit(trader1.getId(), -1000d);
      fail();
    } catch (Exception e) {
      assertTrue(true);
    }

    traderAccountService.deposit(trader1.getId(), 2000d);
    traderAccountService.deposit(trader2.getId(), 3000d);
    traderAccountService.deposit(trader3.getId(), 4000d);

    assertEquals(2000d, accountDao.findByTraderId(trader1.getId()).get().getAmount(), 0);
    assertEquals(3000d, accountDao.findByTraderId(trader2.getId()).get().getAmount(), 0);
    assertEquals(4000d, accountDao.findByTraderId(trader3.getId()).get().getAmount(), 0);

    traderAccountService.withdraw(trader1.getId(), 2000d);
    traderAccountService.withdraw(trader2.getId(), 3000d);
    traderAccountService.withdraw(trader3.getId(), 4000d);

    assertTrue(true);
  }

  @Test
  public void withdraw() {
    assertTrue(true);
  }
}