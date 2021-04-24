package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.ResourceNotFoundException;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class QuoteService {

  private static final Logger logger = LoggerFactory.getLogger(QuoteService.class);

  private QuoteDao quoteDao;
  private MarketDataDao marketDataDao;

  @Autowired
  public QuoteService(QuoteDao quoteDao, MarketDataDao marketDataDao) {
    this.quoteDao = quoteDao;
    this.marketDataDao = marketDataDao;
  }

  /**
   * Update quote table against IEX source - get all quotes from the db - foreach ticker get
   * iexQuote - convert iexQuote to quote entity - persist quote to db
   *
   * @throws ResourceNotFoundException                   if ticker not found in IEX
   * @throws org.springframework.dao.DataAccessException if unable to retrieve data
   * @throws IllegalArgumentException                    for invalid input
   */
  public void updateMarketData() {
    List<Quote> quotes = findAllQuotes();
    for (int i = 0; i < quotes.size() - 1; i++) {
      if (marketDataDao.findById(quotes.get(i).getTicker()).isPresent()) {
        IexQuote iexQuote = marketDataDao.findById(quotes.get(i).getTicker()).get();
        quotes.set(i, buildQuoteFromIexQuote(iexQuote));
      } else {
        throw new ResourceNotFoundException("Ticker is not found in IEX");
      }
    }
    quoteDao.saveAll(quotes);
  }

  /**
   * Helper method. Map a IexQuote to a Quote entity. Note: `iexQuote.getLastestPrice() == null` if
   * the stock market is closed Make sure to set a default value for number field(s).
   */
  protected static Quote buildQuoteFromIexQuote(IexQuote iexQuote) {
    if (iexQuote.getLatestPrice() == null) {
      throw new ResourceNotFoundException("The stock market is close.");
    }

    Quote quote = new Quote();
    quote.setTicker(iexQuote.getSymbol());
    quote.setLastPrice(iexQuote.getLatestPrice());
    quote.setBidPrice(iexQuote.getIexBidPrice());
    quote.setBidSize(iexQuote.getIexBidSize());
    quote.setAskPrice(iexQuote.getIexAskPrice());
    quote.setAskSize(iexQuote.getIexAskSize());
    return quote;
  }

  /**
   * Validate (against IEX) and save given tickers to quote table.
   * <p>
   * - Get iexQuotes(s) - convert each iexQuote to Quote entity - persist the quote to db
   *
   * @param tickers a list of tickers/symbols
   * @throws IllegalArgumentException if ticker is not found from IEX
   */
  public List<Quote> saveQuotes(List<String> tickers) {
    List<Quote> quotes = new ArrayList<>();
    tickers.forEach(ticker -> {
      Quote quote = saveQuote(ticker);
      quotes.add(quote);
    });
    return quotes;
  }

  /**
   * Helper method
   */
  public Quote saveQuote(String ticker) {
    Optional<IexQuote> iexQuote = marketDataDao.findById(ticker);
    if (iexQuote.isPresent()) {
      Quote quote = buildQuoteFromIexQuote(iexQuote.get());
      quoteDao.save(quote);
      return quote;
    } else {
      throw new IllegalArgumentException("Ticker is not found from IEX");
    }
  }

  /**
   * Find an IexQuote
   *
   * @param ticker id
   * @return IexQuote object
   * @throws IllegalArgumentException if ticker is invalid
   */
  public IexQuote findIexQuoteByTicker(String ticker) {
    return marketDataDao.findById(ticker)
        .orElseThrow(() -> new IllegalArgumentException(ticker + " is invalid"));
  }

  /**
   * Update a given quote to quote table without validation
   *
   * @param quote entity
   */
  public Quote saveQuote(Quote quote) {
    return quoteDao.save(quote);
  }

  /**
   * Find all quotes from the quote table
   *
   * @return a list of quotes
   */
  public List<Quote> findAllQuotes() {
    return (List<Quote>) quoteDao.findAll();
  }
}
