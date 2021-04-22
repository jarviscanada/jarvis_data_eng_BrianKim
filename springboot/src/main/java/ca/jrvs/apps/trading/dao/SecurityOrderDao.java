package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class SecurityOrderDao extends JdbcCrudDao<SecurityOrder> {

  private static final Logger logger = LoggerFactory.getLogger(AccountDao.class);

  private final String TABLE_NAME = "security_order";
  private final String ID_COLUMN = "id";

  private JdbcTemplate jdbcTemplate;
  private SimpleJdbcInsert simpleJdbcInsert;

  @Autowired
  public SecurityOrderDao(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
    this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(getTableName())
        .usingGeneratedKeyColumns(getIdColumnName());
  }

  @Override
  public JdbcTemplate getJdbcTemplate() { return jdbcTemplate; }

  @Override
  public SimpleJdbcInsert getSimpleJdbcInsert() { return simpleJdbcInsert; }

  @Override
  public String getTableName() { return TABLE_NAME; }

  @Override
  public String getIdColumnName() { return ID_COLUMN; }

  @Override
  Class<SecurityOrder> getEntityClass() { return SecurityOrder.class; }

  /**
   * Saves all given entities.
   *
   * @param entities must not be {@literal null}.
   * @return the saved entities will never be {@literal null}.
   * @throws IllegalArgumentException in case the given entity is {@literal null}.
   */
  @Override
  public <S extends SecurityOrder> Iterable<S> saveAll(Iterable<S> entities) {
    List<S> savedEntities = new ArrayList<>();
    entities.forEach(entity -> {
      if (entity == null)
        throw new IllegalArgumentException("SecurityOrder enttiy is null");
      savedEntities.add(save(entity));
    });
    return savedEntities;
  }

  @Override
  public int updateOne(SecurityOrder entity) {
    throw new UnsupportedOperationException("This is not implemented");
  }

  @Override
  public void delete(SecurityOrder entity) {
    throw new UnsupportedOperationException("This is not implemented");
  }

  @Override
  public void deleteAll(Iterable<? extends SecurityOrder> entities) {
    throw new UnsupportedOperationException("This is not implemented");
  }
}
