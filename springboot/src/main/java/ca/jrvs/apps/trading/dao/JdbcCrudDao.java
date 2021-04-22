package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public abstract class JdbcCrudDao<T extends Entity<Integer>> implements CrudRepository<T, Integer> {

  private static final Logger logger = LoggerFactory.getLogger(JdbcCrudDao.class);

  abstract public JdbcTemplate getJdbcTemplate();
  abstract public SimpleJdbcInsert getSimpleJdbcInsert();
  abstract public String getTableName();
  abstract public String getIdColumnName();
  abstract Class<T> getEntityClass();

  /**
   * Save an entity and update auto-generated integer ID
   * @param entity to be saved
   * @return saved entity
   */
  @Override
  public <S extends T> S save(S entity) {
    if (existsById(entity.getId())) {
      if (updateOne(entity) != 1) {
        throw new DataRetrievalFailureException("Unable to update quote");
      }
    } else {
      addOne(entity);
    }
    return entity;
  }

  /**
   * helper method that saves one quote
   */
  private <S extends T> void addOne(S entity) {
    SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(entity);

    Number newId = getSimpleJdbcInsert().executeAndReturnKey(parameterSource);
    entity.setId(newId.intValue());
  }

  /**
   * helper method that updates one quote
   */
  abstract public int updateOne(T entity);

  @Override
  public Optional<T> findById(Integer id) {
    Optional<T> entity = Optional.empty();
    String select_sql = "SELECT * FROM " + getTableName() + " WHERE " + getIdColumnName() + "=?";

    try {
      entity = Optional.ofNullable((T) getJdbcTemplate().queryForObject(select_sql,
          BeanPropertyRowMapper.newInstance(getEntityClass()), id));
    } catch (IncorrectResultSizeDataAccessException e) {
      logger.debug("Can't find trader id: " + id, e);
    }
    return entity;
  }

  @Override
  public boolean existsById(Integer id) {
    String exists_sql = "SELECT COUNT(*) FROM " + getTableName() + " WHERE " + getIdColumnName() + "=?";
    Integer count = getJdbcTemplate().queryForObject(exists_sql, Integer.class, id);
    if (count == null)
      throw new NullPointerException("SQL NULL occurred.");
    return count == 1;
  }

  @Override
  public List<T> findAll() {
    List<T> entities;
    String find_all_sql = "SELECT * FROM " + getTableName();
    entities = getJdbcTemplate().query(find_all_sql, BeanPropertyRowMapper.newInstance(getEntityClass()));
    return entities;
  }

  @Override
  public List<T> findAllById(Iterable<Integer> ids) {
    List<T> entities = new ArrayList<>();
    ids.forEach(id -> {
      entities.add(findById(id).get());
    });
    return entities;
  }

  @Override
  public void deleteById(Integer id) {
    String delete_by_id = "DELETE FROM " + getTableName() + " WHERE " + getIdColumnName() + "=?";
    getJdbcTemplate().update(delete_by_id, id);
  }

  @Override
  public long count() {
    String count_sql = "SELECT COUNT(*) FROM " + getTableName();
    Long count = getJdbcTemplate().queryForObject(count_sql, Long.class);
    if (count == null) {
      throw new NullPointerException("SQL NULL occurred.");
    }
    return count;
  }

  @Override
  public void deleteAll() {
    String delete_all = "DELETE FROM " + getTableName();
    getJdbcTemplate().update(delete_all);
  }
}
