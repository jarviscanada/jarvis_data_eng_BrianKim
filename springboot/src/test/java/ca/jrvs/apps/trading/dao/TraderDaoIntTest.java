package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.model.domain.Trader;
import java.sql.Date;
import java.util.Arrays;
import java.util.Collections;
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
public class TraderDaoIntTest {

  @Autowired
  private TraderDao traderDao;

  private Trader savedTrader1;
  private Trader savedTrader2;
  private Trader savedTrader3;

  @Before
  public void insertOne() {
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

    savedTrader3 = new Trader();
    savedTrader3.setFirst_name("Norah");
    savedTrader3.setLast_name("Jones");
    savedTrader3.setDob(Date.valueOf("1979-03-30"));
    savedTrader3.setCountry("United States");
    savedTrader3.setEmail("norah_jones@gmail.com");

    traderDao.saveAll(Arrays.asList(savedTrader1, savedTrader2, savedTrader3));
  }

  @After
  public void deleteOne() {
    traderDao.deleteAll();
  }

  @Test
  public void findAllById() {
    List<Trader> traders = traderDao.findAllById(Collections.singletonList(savedTrader1.getId()));
    assertEquals(1, traders.size());
    assertEquals(1, traders.get(0).getId().intValue());
    assertEquals("Brian", traders.get(0).getFirst_name());
    assertEquals("Kim", traders.get(0).getLast_name());
  }

  @Test
  public void findAll() {
    List<Trader> traders = traderDao.findAll();
    assertEquals(3, traders.size());
    assertEquals(1, traders.get(0).getId().intValue());
    assertEquals("Bob", traders.get(1).getFirst_name());
    assertEquals(Date.valueOf("1979-03-30"), traders.get(2).getDob());
  }

  @Test
  public void findById() {
    Optional<Trader> optional = traderDao.findById(3);
    if (optional.isPresent()) {
      Trader trader = optional.get();
      assertEquals("norah_jones@gmail.com", trader.getEmail());
    } else {
      fail();
    }
  }

  @Test
  public void existsById() {
    assertTrue(traderDao.existsById(1));
    assertTrue(traderDao.existsById(2));
    assertTrue(traderDao.existsById(3));
    assertFalse(traderDao.existsById(4));
  }

  @Test
  public void deleteById() {
    traderDao.deleteById(1);
    List<Trader> traders = traderDao.findAll();
    assertEquals(2, traders.size());
    assertFalse(traderDao.existsById(1));
  }
}