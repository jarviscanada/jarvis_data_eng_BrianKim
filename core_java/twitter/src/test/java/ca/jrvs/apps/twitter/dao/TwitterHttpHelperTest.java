package ca.jrvs.apps.twitter.dao;

import com.google.gdata.util.common.base.PercentEscaper;
import java.net.URI;
import junit.framework.TestCase;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

public class TwitterHttpHelperTest extends TestCase {
  TwitterHttpHelper helper = new TwitterHttpHelper(
      System.getenv("consumerKey"), System.getenv("consumerSecret"),
      System.getenv("accessToken"), System.getenv("tokenSecret"));
  PercentEscaper percentEscaper = new PercentEscaper("", false);
  String status = "Testing tweet status post again";
  String postUri = "https://api.twitter.com/1.1/statuses/update.json?status="+percentEscaper.escape(status);
  String showUri = "https://api.twitter.com/1.1/statuses/show.json?id=1379095346366844939";
  String deleteUri = "https://api.twitter.com/1.1/statuses/destroy/1377650230611615748.json";

  public void testHttpPost() throws Exception {
    HttpResponse postResponse = helper.httpPost(URI.create(postUri));
    HttpResponse deleteResponse = helper.httpGet(URI.create(deleteUri));

    assertNotNull(postResponse);
    System.out.println(EntityUtils.toString(postResponse.getEntity()));
  }

  public void testHttpGet() throws Exception {
    HttpResponse showResponse = helper.httpGet(URI.create(showUri));

    assertNotNull(showResponse);
    System.out.println(EntityUtils.toString(showResponse.getEntity()));
  }
}