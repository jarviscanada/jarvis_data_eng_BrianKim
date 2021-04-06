package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.TweetUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TwitterDaoIntTest {

  TwitterDao twitterDao;
  String mention = "TorontoStar";
  String hashTag = "covid";
  String text = "@" + mention + " This is a test tweet 5 " + "#" + hashTag;
  Float latitude = 43.65f;
  Float longitude = -79.38f;

  @Before
  public void setUp() {
    String consumerKey = System.getenv("consumerKey");
    String consumerSecret = System.getenv("consumerSecret");
    String accessToken = System.getenv("accessToken");
    String tokenSecret = System.getenv("tokenSecret");
    HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken,
        tokenSecret);
    twitterDao = new TwitterDao(httpHelper);
  }

  @Test
  public void testCreate() {
    Tweet sentTweet = TweetUtil.builder(text, latitude, longitude);
    Tweet responseTweet = twitterDao.create(sentTweet);

    Assert.assertEquals(text, responseTweet.getText());

    Assert.assertEquals(2, responseTweet.getCoordinates().getCoordinates().length);
    Assert.assertEquals(longitude, responseTweet.getCoordinates().getCoordinates()[0]);
    Assert.assertEquals(latitude, responseTweet.getCoordinates().getCoordinates()[1]);

    Assert.assertEquals(mention, responseTweet.getEntities().getUserMentions()[0].getScreenName());
    Assert.assertEquals(hashTag, responseTweet.getEntities().getHashtags()[0].getText());
  }

  @Test
  public void testFindById() {
    String id = "1379471678179270659";
    Tweet responseTweet = twitterDao.findById(id);

    Assert.assertEquals(text, responseTweet.getText());

    Assert.assertEquals(2, responseTweet.getCoordinates().getCoordinates().length);
    Assert.assertEquals(longitude, responseTweet.getCoordinates().getCoordinates()[0]);
    Assert.assertEquals(latitude, responseTweet.getCoordinates().getCoordinates()[1]);

    Assert.assertEquals(mention, responseTweet.getEntities().getUserMentions()[0].getScreenName());
    Assert.assertEquals(hashTag, responseTweet.getEntities().getHashtags()[0].getText());
  }

  @Test
  public void testDeleteById() {
    String id = "1379472129847128065";
    Tweet deletedTweet = twitterDao.deleteById(id);

    Assert.assertEquals(text, deletedTweet.getText());

    Assert.assertEquals(2, deletedTweet.getCoordinates().getCoordinates().length);
    Assert.assertEquals(longitude, deletedTweet.getCoordinates().getCoordinates()[0]);
    Assert.assertEquals(latitude, deletedTweet.getCoordinates().getCoordinates()[1]);

    Assert.assertEquals(mention, deletedTweet.getEntities().getUserMentions()[0].getScreenName());
    Assert.assertEquals(hashTag, deletedTweet.getEntities().getHashtags()[0].getText());
  }
}