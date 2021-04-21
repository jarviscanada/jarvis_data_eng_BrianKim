package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import javax.swing.text.html.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class QuoteDao implements CrudRepository<Quote, String> {

  private static final String TABLE_NAME = "quote";
  private static final String ID_COLUMN_NAME = "ticker";

  private static final Logger logger = LoggerFactory.getLogger(QuoteDao.class);
  private JdbcTemplate jdbcTemplate;
  private SimpleJdbcInsert simpleJdbcInsert;

  @Autowired
  public QuoteDao(DataSource dataSource) {
    jdbcTemplate = new JdbcTemplate(dataSource);
    simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME);
  }

  /**
   * hint: http://bit.ly/2sDz8hq DataAccessException family
   * @throws DataAccessException for unexpected SQL result or SQL Execution failure
   */
  @Override
  public <S extends Quote> S save(S quote) {
    if (existsById(quote.getId())) {
      int updatedRowNo = updateOne(quote);
      if (updatedRowNo != 1) {
        throw new DataRetrievalFailureException("Unable to update quote");
      }
    } else {
      addOne(quote);
    }
    return quote;
  }

  @Override
  public <S extends Quote> Iterable<S> saveAll(Iterable<S> quotes) {
    quotes.forEach(this::save);
    return quotes;
  }

  /**
   * helper method that saves one quote
   */
  private void addOne(Quote quote) {
    SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(quote);
    int row = simpleJdbcInsert.execute(parameterSource);
    if (row != 1)
      throw new IncorrectResultSizeDataAccessException("Failed to insert", 1, row);
  }

  /**
   * helper method that updates one quote
   */
  private int updateOne(Quote quote) {
    String update_sql = "UPDATE quote SET last_price=?, bid_price=?, "
        + "bid_size=?, ask_price=?, ask_size=? WHERE " + ID_COLUMN_NAME+ "=?";
    return jdbcTemplate.update(update_sql, makeUpdateValues(quote));
  }

  /**
   * helper method that makes sql update values objects
   * @param quote to be updated
   * @return UPDATE_SQL values
   */
  private Object[] makeUpdateValues(Quote quote) {
    return new Object[]{quote.getLastPrice(), quote.getBidPrice(),
        quote.getBidSize(), quote.getAskPrice(), quote.getAskSize(), quote.getId()};
  }

  /**
   * return all quotes
   * @throws DataAccessException if failed to update
   */
  @Override
  public Iterable<Quote> findAll() {
    String find_sql = "SELECT * FROM " + TABLE_NAME;
    List<Quote> quotes = jdbcTemplate
        .query(find_sql, BeanPropertyRowMapper.newInstance(Quote.class));
    return quotes;
  }

  /**
   * Find a quote by ticker
   * @param ticker name
   * @return quote or Optional.empty if not found
   */
  @Override
  public Optional<Quote> findById(String ticker) {
    String find_sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + "=?";
    try {
      Quote quote = jdbcTemplate.queryForObject(find_sql, BeanPropertyRowMapper.newInstance(Quote.class), ticker);
      return Optional.of(quote);
    } catch (IncorrectResultSizeDataAccessException e) {
      return Optional.empty();
    }
  }

  @Override
  public boolean existsById(String ticker) {
    String exists_sql = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + "=?";
    Integer count = jdbcTemplate.queryForObject(exists_sql, Integer.class, ticker);
    if (count == null)
      throw new NullPointerException("SQL NULL occurred");
    return count == 1;
  }

  @Override
  public void deleteById(String ticker) {
    String delete_sql = "DELETE FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + "=?";
    jdbcTemplate.update(delete_sql, ticker);
  }

  @Override
  public void deleteAll() {
    String delete_sql = "DELETE FROM " + TABLE_NAME;
    jdbcTemplate.update(delete_sql);
  }

  @Override
  public long count() {
    String count_sql = "SELECT COUNT(*) FROM " + TABLE_NAME;
    Long count = jdbcTemplate.queryForObject(count_sql, Long.class);
    if (count == null)
      throw new NullPointerException("SQL NULL occurred");
    return count;
  }

  @Override
  public void delete(Quote quote) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void deleteAll(Iterable<? extends Quote> iterable) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public Iterable<Quote> findAllById(Iterable<String> iterable) {
    throw new UnsupportedOperationException("Not implemented");
  }
}
