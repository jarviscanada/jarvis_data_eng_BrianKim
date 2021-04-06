package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.example.JsonParser;
import ca.jrvs.apps.twitter.model.Tweet;
import com.google.gdata.util.common.base.PercentEscaper;
import java.io.IOException;
import java.net.URI;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;

public class TwitterDao implements CrdDao<Tweet, String> {

  //URI constants
  private static final String API_BASE_URI = "https://api.twitter.com";
  private static final String POST_PATH = "/1.1/statuses/update.json";
  private static final String SHOW_PATH = "/1.1/statuses/show.json";
  private static final String DELETE_PATH = "/1.1/statuses/destroy/";
  //URI symbols
  private static final String QUERY_SYM = "?";
  private static final String AMPERSAND = "&";
  private static final String EQUAL = "=";

  //Response code
  private static final int HTTP_OK = 200;

  PercentEscaper percentEscaper = new PercentEscaper("", false);

  private HttpHelper httpHelper;

  @Autowired
  public TwitterDao(HttpHelper httpHelper) {
    this.httpHelper = httpHelper;
  }

  @Override
  public Tweet create(Tweet entity) {
    URI uri = URI.create(API_BASE_URI + POST_PATH + QUERY_SYM + "status" + EQUAL + percentEscaper
        .escape(entity.getText()));
    //[long, lat]
    if (entity.getCoordinates() != null) {
      String longitude = entity.getCoordinates().getCoordinates()[0].toString();
      String latitude = entity.getCoordinates().getCoordinates()[1].toString();
      uri = URI.create(
          uri.toString() + AMPERSAND + "long" + EQUAL + longitude + AMPERSAND + "lat" + EQUAL
              + latitude);
    }
    return sendRequest(uri, HttpMethod.POST);
  }

  @Override
  public Tweet findById(String s) {
    URI uri = URI.create(API_BASE_URI + SHOW_PATH + QUERY_SYM + "id" + EQUAL + s);
    return sendRequest(uri, HttpMethod.GET);
  }

  @Override
  public Tweet deleteById(String s) {
    URI uri = URI.create(API_BASE_URI + DELETE_PATH + s + ".json");
    return sendRequest(uri, HttpMethod.POST);
  }

  public Tweet sendRequest(URI requestURI, HttpMethod method) {
    try {
      HttpResponse response = null;
      if (method == HttpMethod.POST) {
        response = httpHelper.httpPost(requestURI);
      } else if (method == HttpMethod.GET) {
        response = httpHelper.httpGet(requestURI);
      }

      if (response == null) {
        throw new RuntimeException("Failed to retrieve response");
      }

      if (response.getStatusLine().getStatusCode() != HTTP_OK) {
        throw new RuntimeException("Unexpected HTTP status.");
      }

      return JsonParser.toObjectFromJson(EntityUtils.toString(response.getEntity()), Tweet.class);
    } catch (IOException e) {
      throw new RuntimeException("Failed to parse JSON data.", e);
    }
  }
}
