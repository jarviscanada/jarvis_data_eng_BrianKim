package ca.jrvs.apps.twitter.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import ca.jrvs.apps.twitter.controller.TwitterController;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TwitterControllerIntTest {
  TwitterController twitterController;
  TwitterService twitterService;
  TwitterDao twitterDao;
  String mention = "TorontoStar";
  String hashTag = "covid";
  String text = "@" + mention + " This is a test tweet 8 " + "#" + hashTag;
  String latLon = "43:-73";
  String badLatLon = "4f3:2m5";

  @Before
  public void setUp() {
    String consumerKey = System.getenv("consumerKey");
    String consumerSecret = System.getenv("consumerSecret");
    String accessToken = System.getenv("accessToken");
    String tokenSecret = System.getenv("tokenSecret");
    HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken,
        tokenSecret);
    twitterDao = new TwitterDao(httpHelper);
    twitterService = new TwitterService(twitterDao);
    twitterController = new TwitterController(twitterService);
  }

  @Test
  public void postTweet() {
    // expected bad case
    try {
      twitterController.postTweet(new String[]{"some text", badLatLon});
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }

    // expected good case
    Tweet responseTweet = new Tweet();
    try {
      responseTweet = twitterController.postTweet(new String[]{text, latLon});
    } catch (IllegalArgumentException e) {
      fail();
    }

    assertNotNull(responseTweet);
    assertEquals(text, responseTweet.getText());
    assertNotNull(responseTweet.getCoordinates());
    assertNotNull(responseTweet.getEntities());
  }

  @Test
  public void showTweet() {
    // expected bad case
    try {
      twitterController.showTweet(new String[]{"1379879093030809600", "this,is,wrong,args"});
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }

    // expected good case
    Tweet responseTweet = new Tweet();
    try {
      responseTweet = twitterController.showTweet(new String[]{"1379879093030809600", "id,text,entities"});
    } catch (IllegalArgumentException e) {
      fail();
    }

    assertNotNull(responseTweet);
    assertEquals(text, responseTweet.getText());
    assertEquals(mention, responseTweet.getEntities().getUserMentions()[0].getScreenName());
    assertEquals(hashTag, responseTweet.getEntities().getHashtags()[0].getText());
  }

  @Test
  public void deleteTweet() {
    // expected bad case
    try {
      twitterController.deleteTweet(new String[]{"123456789", "12345678987"});
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }

    // expected good case
    List<Tweet> responseTweets = new ArrayList<>();
    try {
      responseTweets = twitterController.deleteTweet(new String[]{"1379879093030809600"});
    } catch (IllegalArgumentException e) {
      fail();
    }

    assertNotNull(responseTweets);
    assertEquals(text, responseTweets.get(0).getText());
    assertEquals(mention, responseTweets.get(0).getEntities().getUserMentions()[0].getScreenName());
    assertEquals(hashTag, responseTweets.get(0).getEntities().getHashtags()[0].getText());
  }
}