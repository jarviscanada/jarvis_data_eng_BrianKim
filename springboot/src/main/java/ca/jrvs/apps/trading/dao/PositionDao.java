package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Position;
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
import org.springframework.stereotype.Repository;

@Repository
public class PositionDao {

  private static final Logger logger = LoggerFactory.getLogger(PositionDao.class);

  private final String TABLE_NAME = "position";
  private final String ID_COLUMN = "account_id";

  private JdbcTemplate jdbcTemplate;

  @Autowired public PositionDao(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  public Optional<Position> findById(Integer id) {
    Optional<Position> entity = Optional.empty();
    String select_sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COLUMN + "=?";

    try {
      entity = Optional.ofNullable(jdbcTemplate.queryForObject(select_sql,
          BeanPropertyRowMapper.newInstance(Position.class), id));
    } catch (IncorrectResultSizeDataAccessException e) {
      logger.debug("Can't find position id: " + id, e);
    }
    return entity;
  }

  public boolean existsById(Integer id) {
    String exists_sql = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE " + ID_COLUMN + "=?";
    Integer count = jdbcTemplate.queryForObject(exists_sql, Integer.class, id);
    if (count == null)
      throw new NullPointerException("SQL NULL occurred.");
    return count == 1;
  }

  public List<Position> findAll() {
    List<Position> entities;
    String find_all_sql = "SELECT * FROM " + TABLE_NAME;
    entities = jdbcTemplate.query(find_all_sql, BeanPropertyRowMapper.newInstance(Position.class));
    return entities;
  }

  public List<Position> findAllById(Iterable<Integer> ids) {
    List<Position> entities = new ArrayList<>();
    ids.forEach(id -> {
      entities.add(findById(id).get());
    });
    return entities;
  }

  public long count() {
    String count_sql = "SELECT COUNT(*) FROM " + TABLE_NAME;
    Long count = jdbcTemplate.queryForObject(count_sql, Long.class);
    if (count == null) {
      throw new NullPointerException("SQL NULL occurred.");
    }
    return count;
  }
}
