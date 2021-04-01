package ca.jrvs.apps.twitter.example;

import ca.jrvs.apps.twitter.example.dto.Company;
import com.google.gdata.util.common.base.PercentEscaper;
import java.util.Arrays;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class TwitterApiTest {

  private static String CONSUMER_KEY = System.getenv("consumerKey");
  private static String CONSUMER_SECRET = System.getenv("consumerSecret");
  private static String ACCESS_TOKEN = System.getenv("accessToken");
  private static String TOKEN_SECRET = System.getenv("tokenSecret");
  public static final String companyStr = "{\n"
      + "   \"symbol\":\"AAPL\",\n"
      + "   \"companyName\":\"Apple Inc.\",\n"
      + "   \"exchange\":\"Nasdaq Global Select\",\n"
      + "   \"description\":\"Apple Inc is designs, manufactures and markets mobile communication and media devices and personal computers, and sells a variety of related software, services, accessories, networking solutions and third-party digital content and applications.\",\n"
      + "   \"CEO\":\"Timothy D. Cook\",\n"
      + "   \"sector\":\"Technology\",\n"
      + "   \"financials\":[\n"
      + "      {\n"
      + "         \"reportDate\":\"2018-12-31\",\n"
      + "         \"grossProfit\":32031000000,\n"
      + "         \"costOfRevenue\":52279000000,\n"
      + "         \"operatingRevenue\":84310000000,\n"
      + "         \"totalRevenue\":84310000000,\n"
      + "         \"operatingIncome\":23346000000,\n"
      + "         \"netIncome\":19965000000\n"
      + "      },\n"
      + "      {\n"
      + "         \"reportDate\":\"2018-09-30\",\n"
      + "         \"grossProfit\":24084000000,\n"
      + "         \"costOfRevenue\":38816000000,\n"
      + "         \"operatingRevenue\":62900000000,\n"
      + "         \"totalRevenue\":62900000000,\n"
      + "         \"operatingIncome\":16118000000,\n"
      + "         \"netIncome\":14125000000\n"
      + "      }\n"
      + "   ],\n"
      + "   \"dividends\":[\n"
      + "      {\n"
      + "         \"exDate\":\"2018-02-09\",\n"
      + "         \"paymentDate\":\"2018-02-15\",\n"
      + "         \"recordDate\":\"2018-02-12\",\n"
      + "         \"declaredDate\":\"2018-02-01\",\n"
      + "         \"amount\":0.63\n"
      + "      },\n"
      + "      {\n"
      + "         \"exDate\":\"2017-11-10\",\n"
      + "         \"paymentDate\":\"2017-11-16\",\n"
      + "         \"recordDate\":\"2017-11-13\",\n"
      + "         \"declaredDate\":\"2017-11-02\",\n"
      + "         \"amount\":0.63\n"
      + "      }\n"
      + "   ]\n"
      + "}";

  public static void main(String[] args) throws Exception {

//    // setup oauth
//    OAuthConsumer consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
//    consumer.setTokenWithSecret(ACCESS_TOKEN, TOKEN_SECRET);
//
//    // create an HTTP GET request
//    String status = "today is a good day";
//    PercentEscaper percentEscaper = new PercentEscaper("", false);
//    HttpPost request = new HttpPost(
//        "https://api.twitter.com/1.1/statuses/update.json?status=" + percentEscaper.escape(status)
//    );
//
//    // sign the request (add headers)
//    consumer.sign(request);
//
//    System.out.println("Http Request Headers:");
//    Arrays.stream(request.getAllHeaders()).forEach(System.out::println);
//
//    // send the request
//    HttpClient httpClient = HttpClientBuilder.create().build();
//    HttpResponse response = httpClient.execute(request);
//    System.out.println(EntityUtils.toString(response.getEntity()));
    Company company = JsonParser.toObjectFromJson(companyStr, Company.class);
    System.out.println(JsonParser.toJson(company, true, false));
  }
}
