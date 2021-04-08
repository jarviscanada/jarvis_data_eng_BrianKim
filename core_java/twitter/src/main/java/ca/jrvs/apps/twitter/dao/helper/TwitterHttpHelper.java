package ca.jrvs.apps.twitter.dao.helper;

import com.google.gdata.util.common.base.PercentEscaper;
import java.io.IOException;
import java.net.URI;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class TwitterHttpHelper implements HttpHelper {

  private OAuthConsumer consumer;
  private HttpClient httpClient;
  private Logger logger = LoggerFactory.getLogger(TwitterHttpHelper.class);

  public static void main(String[] args) throws Exception {
    TwitterHttpHelper helper = new TwitterHttpHelper(
        System.getenv("consumerKey"), System.getenv("consumerSecret"),
        System.getenv("accessToken"), System.getenv("tokenSecret"));
    PercentEscaper percentEscaper = new PercentEscaper("", false);

    String status = "This is a tweet using the HttpHelper class.";
    String postUri =
        "https://api.twitter.com/1.1/statuses/update.json?status=" + percentEscaper.escape(status);
    String showUri = "https://api.twitter.com/1.1/statuses/show.json?id=1379095346366844939";
    String deleteUri = "https://api.twitter.com/1.1/statuses/destroy/1377650230611615748.json";

    HttpResponse postResponse = helper.httpPost(URI.create(postUri));
    HttpResponse showResponse = helper.httpGet(URI.create(showUri));
    HttpResponse deleteResponse = helper.httpPost(URI.create(showUri));
    System.out.println(EntityUtils.toString(deleteResponse.getEntity()));
  }

  public TwitterHttpHelper(String consumerKey, String consumerSecret, String accessToken,
      String tokenSecret) {
    // Initialize members
    consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
    consumer.setTokenWithSecret(accessToken, tokenSecret);

    httpClient = HttpClientBuilder.create().build();
  }

  public TwitterHttpHelper() {
    String consumerKey = System.getenv("consumerKey");
    String consumerSecret = System.getenv("consumerSecret");
    String accessToken = System.getenv("accessToken");
    String tokenSecret = System.getenv("tokenSecret");
    consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
    consumer.setTokenWithSecret(accessToken, tokenSecret);
    /**
     * Default = single connection.
     */
    httpClient = HttpClientBuilder.create().build();
  }

  @Override
  public HttpResponse httpPost(URI uri) {
    try {
      return executeRequest(HttpMethod.POST, uri);
    } catch (Exception e) {
      throw new RuntimeException("Runtime Exception has occurred", e);
    }
  }

  @Override
  public HttpResponse httpGet(URI uri) {
    try {
      return executeRequest(HttpMethod.GET, uri);
    } catch (Exception e) {
      throw new RuntimeException("Runtime Exception has occurred", e);
    }
  }

  private HttpResponse executeRequest(HttpMethod method, URI uri) throws
      OAuthException, IOException {
    if (method == HttpMethod.POST) {
      HttpPost request = new HttpPost(uri);
      consumer.sign(request);
      return httpClient.execute(request);
    } else if (method == HttpMethod.GET) {
      HttpGet request = new HttpGet(uri);
      consumer.sign(request);
      return httpClient.execute(request);
    } else {
      throw new IllegalArgumentException("Wrong method input: " + method.name());
    }
  }
}
