package ca.jrvs.apps.twitter.dao;

import com.google.gdata.util.common.base.PercentEscaper;
import java.net.URI;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.Test;

public class TwitterHttpHelperTest {

  TwitterHttpHelper helper = new TwitterHttpHelper(
      System.getenv("consumerKey"), System.getenv("consumerSecret"),
      System.getenv("accessToken"), System.getenv("tokenSecret"));
  PercentEscaper percentEscaper = new PercentEscaper("", false);
  String status = "Testing tweet status post again";
  String postUri =
      "https://api.twitter.com/1.1/statuses/update.json?status=" + percentEscaper.escape(status);
  String showUri = "https://api.twitter.com/1.1/statuses/show.json?id=1379095346366844939";
  String deleteUri = "https://api.twitter.com/1.1/statuses/destroy/1377650230611615748.json";

  @Test
  public void testHttpPost() throws Exception {
    HttpResponse postResponse = helper.httpPost(URI.create(postUri));
    HttpResponse deleteResponse = helper.httpGet(URI.create(deleteUri));

    Assert.assertNotNull(postResponse);
    System.out.println(EntityUtils.toString(postResponse.getEntity()));
  }

  @Test
  public void testHttpGet() throws Exception {
    HttpResponse showResponse = helper.httpGet(URI.create(showUri));

    Assert.assertNotNull(showResponse);
    System.out.println(EntityUtils.toString(showResponse.getEntity()));
  }
}