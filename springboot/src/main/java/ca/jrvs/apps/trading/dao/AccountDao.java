package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Account;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDao extends JdbcCrudDao<Account> {

  private static final Logger logger = LoggerFactory.getLogger(AccountDao.class);

  private final String TABLE_NAME = "account";
  private final String ID_COLUMN = "id";
  private final String TRADER_ID_COLUMN = "trader_id";

  private JdbcTemplate jdbcTemplate;
  private SimpleJdbcInsert simpleJdbcInsert;

  @Autowired
  public AccountDao(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
    this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME)
        .usingGeneratedKeyColumns(ID_COLUMN);
  }

  @Override
  public JdbcTemplate getJdbcTemplate() {
    return jdbcTemplate;
  }

  @Override
  public SimpleJdbcInsert getSimpleJdbcInsert() {
    return simpleJdbcInsert;
  }

  @Override
  public String getTableName() {
    return TABLE_NAME;
  }

  @Override
  public String getIdColumnName() {
    return ID_COLUMN;
  }

  public String getTraderIdColumnName() {
    return TRADER_ID_COLUMN;
  }

  @Override
  Class<Account> getEntityClass() {
    return Account.class;
  }

  /**
   * Saves all given entities.
   *
   * @param entities must not be {@literal null}.
   * @return the saved entities will never be {@literal null}.
   * @throws IllegalArgumentException in case the given entity is {@literal null}.
   */
  @Override
  public <S extends Account> Iterable<S> saveAll(Iterable<S> entities) {
    List<S> savedEntities = new ArrayList<>();
    entities.forEach(entity -> {
      if (entity == null) {
        throw new IllegalArgumentException("Account entity is null");
      }
      savedEntities.add(save(entity));
    });
    return savedEntities;
  }

  public Optional<Account> findByTraderId(Integer traderId) {
    Optional<Account> entity = Optional.empty();
    String select_sql =
        "SELECT * FROM " + getTableName() + " WHERE " + getTraderIdColumnName() + "=?";

    try {
      entity = Optional.ofNullable(getJdbcTemplate().queryForObject(select_sql,
          BeanPropertyRowMapper.newInstance(getEntityClass()), traderId));
    } catch (IncorrectResultSizeDataAccessException e) {
      logger.debug("Can't find entity id: " + traderId, e);
    }
    return entity;
  }

  @Override
  public int updateOne(Account account) {
    String update_sql = "UPDATE account SET amount=? WHERE " + ID_COLUMN + "=?";
    return getJdbcTemplate().update(update_sql, account.getAmount(), account.getId());
  }

  @Override
  public void delete(Account entity) {
    throw new UnsupportedOperationException("This is not implemented");
  }

  @Override
  public void deleteAll(Iterable<? extends Account> entities) {
    throw new UnsupportedOperationException("This is not implemented");
  }
}
