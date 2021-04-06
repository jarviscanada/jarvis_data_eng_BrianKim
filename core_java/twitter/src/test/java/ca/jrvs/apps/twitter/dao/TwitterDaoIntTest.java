package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.TweetUtil;
import java.net.URI;
import junit.framework.TestCase;
import org.junit.Before;

public class TwitterDaoIntTest extends TestCase {

  TwitterDao twitterDao;
  String mention = "TorontoStar";
  String hashTag = "covid";
  String text = "@"+mention+" This is a test tweet 5 "+"#"+hashTag;
  Float latitude = 43.65f;
  Float longitude = -79.38f;

  @Before
  public void setUp() {
    String consumerKey = System.getenv("consumerKey");
    String consumerSecret = System.getenv("consumerSecret");
    String accessToken = System.getenv("accessToken");
    String tokenSecret = System.getenv("tokenSecret");
    HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
    twitterDao = new TwitterDao(httpHelper);
  }

  public void testCreate() {
    Tweet sentTweet = TweetUtil.builder(text, latitude, longitude);
    Tweet responseTweet = twitterDao.create(sentTweet);

    assertEquals(text, responseTweet.getText());

    assertEquals(2, responseTweet.getCoordinates().getCoordinates().length);
    assertEquals(longitude, responseTweet.getCoordinates().getCoordinates()[0]);
    assertEquals(latitude, responseTweet.getCoordinates().getCoordinates()[1]);

    assertEquals(mention, responseTweet.getEntities().getUserMentions()[0].getScreenName());
    assertEquals(hashTag, responseTweet.getEntities().getHashtags()[0].getText());
  }

  public void testFindById() {
    String id = "1379472129847128065";
    Tweet responseTweet = twitterDao.findById(id);

    assertEquals(text, responseTweet.getText());

    assertEquals(2, responseTweet.getCoordinates().getCoordinates().length);
    assertEquals(longitude, responseTweet.getCoordinates().getCoordinates()[0]);
    assertEquals(latitude, responseTweet.getCoordinates().getCoordinates()[1]);

    assertEquals(mention, responseTweet.getEntities().getUserMentions()[0].getScreenName());
    assertEquals(hashTag, responseTweet.getEntities().getHashtags()[0].getText());
  }

  public void testDeleteById() {
    String id = "1379472129847128065";
    Tweet deletedTweet = twitterDao.deleteById(id);

    assertEquals(text, deletedTweet.getText());

    assertEquals(2, deletedTweet.getCoordinates().getCoordinates().length);
    assertEquals(longitude, deletedTweet.getCoordinates().getCoordinates()[0]);
    assertEquals(latitude, deletedTweet.getCoordinates().getCoordinates()[1]);

    assertEquals(mention, deletedTweet.getEntities().getUserMentions()[0].getScreenName());
    assertEquals(hashTag, deletedTweet.getEntities().getHashtags()[0].getText());
  }
}