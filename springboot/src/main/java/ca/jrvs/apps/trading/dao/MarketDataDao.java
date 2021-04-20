package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.util.JsonParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.swing.text.html.Option;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class MarketDataDao implements CrudRepository<IexQuote, String> {

  private static final String IEX_BATCH_PATH = "/stock/market/batch?symbols=%s&types=quote&token=";
  private final String IEX_BATCH_URL;

  private Logger logger = LoggerFactory.getLogger(MarketDataDao.class);
  private HttpClientConnectionManager httpClientConnectionManager;

  @Autowired
  public MarketDataDao(HttpClientConnectionManager httpClientConnectionManager,
      MarketDataConfig marketDataConfig) {
    this.httpClientConnectionManager = httpClientConnectionManager;
    IEX_BATCH_URL = marketDataConfig.getHost() + IEX_BATCH_PATH + marketDataConfig.getToken();
  }

  /**
   * Get an IexQuote (helper method which is used in findAllById)
   * @return an Optional of IexQuote object
   * @param ticker
   * @throws IllegalArgumentException if a given ticker is invalid
   * @throws DataRetrievalFailureException if HTTP request failed
   */
  @Override
  public Optional<IexQuote> findById(String ticker) throws
      IllegalArgumentException, DataRetrievalFailureException {
    Optional<IexQuote> iexQuote;
    List<IexQuote> quotes = findAllById(Collections.singletonList(ticker));

    if (quotes.size() == 0) {
      return Optional.empty();
    } else if (quotes.size() == 1) {
      iexQuote = Optional.of(quotes.get(0));
    } else {
      throw new DataRetrievalFailureException("Unexpected number of quotes");
    }
    return iexQuote;
  }

  /**
   * Get quotes from IEX
   * @param tickers is a list of tickers
   * @return a list of IexQuote object
   * @throws IllegalArgumentException if any ticker is invalid or ticker is empty
   * @throws DataRetrievalFailureException if HTTP request failed
   */
  @Override
  public List<IexQuote> findAllById(Iterable<String> tickers) throws
      IllegalArgumentException, DataRetrievalFailureException {
    List<IexQuote> quotes = new ArrayList<>();
    tickers.forEach(ticker -> {
      if (ticker.equals("") || ticker.matches("(.*)\\d(.*)"))
        throw new IllegalArgumentException("Ticker value is invalid");

      String url = String.format(IEX_BATCH_URL, ticker);
      String response = executeHttpGet(url).get();
      JSONObject jo = new JSONObject(response);
      String filteredJson = jo.getJSONObject(ticker.toUpperCase()).get("quote").toString();

      try {
        IexQuote quote = JsonParser.toObjectFromJson(filteredJson, IexQuote.class);
        quotes.add(quote);
      } catch (IOException e) {
        throw new RuntimeException("Failed to parse json into object");
      }
    });
    return quotes;
  }

  /**
   * Execute a get and return http entity/body as a string
   * @param url resource URL
   * @return http response body or Optional.empty() for 404 response
   * @throws DataRetrievalFailureException if HTTP failed or status code is unexpected
   */
  private Optional<String> executeHttpGet(String url) throws DataRetrievalFailureException {
    HttpClient httpClient = getHttpClient();
    HttpGet request = new HttpGet(url);

    try {
      HttpResponse response = httpClient.execute(request);
      if (response.getStatusLine().getStatusCode() == 404)
        return Optional.empty();
      else if (response.getStatusLine().getStatusCode() == 200)
        return Optional.of(EntityUtils.toString(response.getEntity()));
      else
        throw new DataRetrievalFailureException("Http Request has Failed");
    } catch (IOException e) {
      throw new RuntimeException("Failed to execute HTTP request");
    }
  }

  /**
   * Borrow a HTTP client from the httpClientConnectionManager
   * @return a httpClient
   */
  private CloseableHttpClient getHttpClient() {
    return HttpClients.custom()
        .setConnectionManager(httpClientConnectionManager)
        // prevent connectionManager shutdown when calling httpClient.close()
        .setConnectionManagerShared(true)
        .build();
  }

  @Override
  public <S extends IexQuote> S save(S s) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public <S extends IexQuote> Iterable<S> saveAll(Iterable<S> iterable) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public boolean existsById(String s) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public Iterable<IexQuote> findAll() {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public long count() {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void deleteById(String s) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void delete(IexQuote iexQuote) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void deleteAll(Iterable<? extends IexQuote> iterable) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void deleteAll() {
    throw new UnsupportedOperationException("Not implemented");
  }
}
