package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.model.domain.Account;
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
public class AccountDaoIntTest {

  @Autowired
  AccountDao accountDao;

  @Autowired
  TraderDao traderDao;

  private Trader savedTrader1;
  private Trader savedTrader2;

  private Account savedAccount1;
  private Account savedAccount2;

  @Before
  public void init() {
    savedTrader1 = new Trader();
    savedTrader1.setFirst_name("Brian");
    savedTrader1.setLast_name("Kim");
    savedTrader1.setDob(Date.valueOf("1994-12-06"));
    savedTrader1.setCountry("Canada");
    savedTrader1.setEmail("kim.brian94@gmail.com");

    savedTrader2 = new Trader();
    savedTrader2.setFirst_name("Bob");
    savedTrader2.setLast_name("Ross");
    savedTrader2.setDob(Date.valueOf("1942-10-29"));
    savedTrader2.setCountry("United States");
    savedTrader2.setEmail("bob_ross@gmail.com");

    traderDao.saveAll(Arrays.asList(savedTrader1, savedTrader2));

    savedAccount1 = new Account();
    savedAccount1.setTrader_id(1);
    savedAccount1.setAmount(1000d);

    savedAccount2 = new Account();
    savedAccount2.setTrader_id(2);
    savedAccount2.setAmount(2000d);

    accountDao.saveAll(Arrays.asList(savedAccount1, savedAccount2));
  }

  @After
  public void clean() {
    accountDao.deleteAll();
    traderDao.deleteAll();
  }

  @Test
  public void findAllById() {
    List<Account> accounts = accountDao.findAllById(Arrays.asList(1, 2));
    assertEquals(2, accounts.size());
    assertEquals(1000d, accounts.get(0).getAmount(), 0);
    assertEquals(1, accounts.get(0).getTrader_id().intValue());
    assertEquals(2000d, accounts.get(1).getAmount(), 0);
    assertEquals(2, accounts.get(1).getTrader_id().intValue());
  }

  @Test
  public void findAll() {
    List<Account> accounts = accountDao.findAll();
    assertEquals(2, accounts.size());
    assertEquals(1000d, accounts.get(0).getAmount(), 0);
    assertEquals(1, accounts.get(0).getTrader_id().intValue());
    assertEquals(2000d, accounts.get(1).getAmount(), 0);
    assertEquals(2, accounts.get(1).getTrader_id().intValue());
  }

  @Test
  public void findById() {
    Optional<Account> optional = accountDao.findById(1);
    if (optional.isPresent()) {
      Account account = optional.get();
      assertEquals(1, account.getTrader_id().intValue());
      assertEquals(1000d, account.getAmount(), 0);
    } else {
      fail();
    }

    optional = accountDao.findById(5);
    if (optional.isPresent()) {
      fail();
    } else {
      assertTrue(true);
    }
  }

  @Test
  public void existsById() {
    assertTrue(accountDao.existsById(1));
    assertTrue(accountDao.existsById(2));
    assertFalse(accountDao.existsById(3));
  }

  @Test
  public void deleteById() {
    accountDao.deleteById(1);
    List<Account> accounts = accountDao.findAll();
    assertEquals(1, accounts.size());
    assertFalse(accountDao.existsById(1));
    assertEquals(2, accounts.get(0).getId().intValue());
  }
}