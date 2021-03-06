package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.domain.TraderAccountView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TraderAccountService {

  private TraderDao traderDao;
  private AccountDao accountDao;
  private SecurityOrderDao securityOrderDao;
  private PositionDao positionDao;

  @Autowired
  public TraderAccountService(TraderDao traderDao, AccountDao accountDao,
      SecurityOrderDao securityOrderDao, PositionDao positionDao) {
    this.traderDao = traderDao;
    this.accountDao = accountDao;
    this.securityOrderDao = securityOrderDao;
    this.positionDao = positionDao;
  }

  /**
   * Create a new trader and initialize a new account with 0 amount. - validate user input (all
   * fields must be non-empty) - create a trader - create an account - create, setup, and return a
   * new TraderAccountView
   * <p>
   * Assumption: to simplify the logic, each trader only has one account where traderId ==
   * accountId
   *
   * @param trader cannot be null. All fields cannot be null except for id (auto-generated by db
   * @return traderAccountView
   * @throws IllegalArgumentException if a trader has a null fields or id is not null.
   */
  public TraderAccountView createTraderAndAccount(Trader trader) {
    if (!isValid(trader)) {
      throw new IllegalArgumentException("Trader has a null fields or id is not null");
    }
    trader = traderDao.save(trader);

    Account account = new Account();
    account.setTrader_id(trader.getId());
    account.setAmount(0d);
    account = accountDao.save(account);

    return new TraderAccountView(
        trader.getId(), trader.getFirst_name(),
        trader.getLast_name(), trader.getDob(),
        trader.getCountry(), trader.getEmail(),
        account.getId(), account.getAmount());
  }

  /**
   * A trader can be deleted iff it has no open position and 0 cash balance - validate traderId -
   * get trader account by traderId and check account balance - get positions by accountId and check
   * positions - delete all security orders, account, trader (in this order)
   *
   * @param traderId must not be null
   * @throws IllegalArgumentException if traderId is null or not found or unable to delete
   */
  public void deleteTraderById(Integer traderId) {
    if (!isValid(traderId)) {
      throw new IllegalArgumentException("traderId is null or not found");
    }

    Account account = accountDao.findById(traderId).get();
    if (!positionDao.existsById(account.getId()) && account.getAmount() == 0d) {
      securityOrderDao.deleteByAccId(account.getId());
      accountDao.deleteById(account.getId());
      traderDao.deleteById(traderId);
    } else {
      throw new IllegalArgumentException("Failed to delete trader and its account.");
    }
  }

  /**
   * Deposit a fund to an account by traderId - validate user input - account ==
   * accountDao.findbyTraderId - accountDao.updateAmountById
   *
   * @param traderId must not be null
   * @param fund     must be greater 0
   * @return updated Account
   * @throws IllegalArgumentException if traderId is null or not found, and fund is less or equal to
   *                                  0
   */
  public Account deposit(Integer traderId, Double fund) {
    if (!isValid(traderId, fund)) {
      throw new IllegalArgumentException(
          "Wrong input: traderId is null or not found, or fund is less than equal to 0");
    }

    Account account = accountDao.findByTraderId(traderId).get();
    Double amount = account.getAmount();
    account.setAmount(amount + fund);
    account = accountDao.save(account);
    return account;
  }

  /**
   * Withdraw a fund from an account by traderId - validate user input - account ==
   * accountDao.findByTraderId - accountDao.updateAmountById
   *
   * @param traderId traderID
   * @param fund     amount can't be 0
   * @return update Amount
   * @throws IllegalArgumentException if traderId is null or not found, fund is less or equal to 0,
   *                                  or insufficient fund.
   */
  public Account withdraw(Integer traderId, Double fund) {
    if (!isValid(traderId, fund)) {
      throw new IllegalArgumentException(
          "Wrong input: traderId is null or not found, or fund is less than equal to 0");
    }

    Account account = accountDao.findByTraderId(traderId).get();
    Double amount = account.getAmount();

    if (amount - fund < 0) {
      throw new IllegalArgumentException("Insufficient fund");
    }

    account.setAmount(amount - fund);
    account = accountDao.save(account);
    return account;
  }

  public boolean isValid(Trader trader) {
    return trader != null &&
        !trader.getFirst_name().equals("") &&
        !trader.getLast_name().equals("") &&
        !trader.getCountry().equals("") &&
        !trader.getEmail().equals("") &&
        trader.getDob() != null &&
        trader.getId() != null;
  }

  public boolean isValid(Integer traderId) {
    return traderId != null && traderDao.existsById(traderId);
  }

  public boolean isValid(Integer traderId, Double fund) {
    return isValid(traderId) && fund > 0d;
  }
}
