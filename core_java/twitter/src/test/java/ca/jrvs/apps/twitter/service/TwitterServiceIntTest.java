package ca.jrvs.apps.twitter.service;

import static org.junit.Assert.*;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.TwitterHttpHelper;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.example.JsonParser;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.TweetUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TwitterServiceIntTest {

  TwitterService twitterService;
  TwitterDao twitterDao;
  String mention = "TorontoStar";
  String hashTag = "covid";
  String text = "@" + mention + " This is a test tweet 7 " + "#" + hashTag;
  Float latitude = 43.65f;
  Float longitude = -79.38f;
  String badText = "Miusov, as a man man of breeding and deilcacy, could not but feel some inwrd qualms, when he reached the Father Superior's with Ivan: he felt ashamed of havin lost his temper. He felt that he ought to have disdaimed that despicable wretch, Fyodor Pavlovitch, too much to have been upset by him in Father Zossima's cell, and so to have forgotten himself. \"Teh monks were not to blame, in any case,\" he reflceted, on the steps.";
  Float badLon = -100f;
  Float badLat = 100f;
  String badIdStr = "this is not an id";

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
  }

  @Test
  public void postTweet() {
    // expected bad case
    Tweet badTweet = TweetUtil.builder(badText, latitude, longitude);
    try {
      twitterService.postTweet(badTweet);
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }

    // expected good case
    Tweet sentTweet = TweetUtil.builder(text, latitude, longitude);
    Tweet responseTweet = twitterService.postTweet(sentTweet);

    assertNotNull(responseTweet);
    assertEquals(text, responseTweet.getText());

    assertEquals(latitude, responseTweet.getCoordinates().getCoordinates()[1]);
    assertEquals(longitude, responseTweet.getCoordinates().getCoordinates()[0]);

    assertEquals(mention, responseTweet.getEntities().getUserMentions()[0].getScreenName());
    assertEquals(hashTag, responseTweet.getEntities().getHashtags()[0].getText());
  }

  @Test
  public void showTweet() {
    // expected bad case
    try {
      twitterService.showTweet(badIdStr, new String[]{});
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }

    // expected good case
    String id = "1379849145305989124";
    Tweet responseTweet = twitterService.showTweet(id, new String[]{"created_at", "id_str", "text"});

    assertNotNull(responseTweet);
    assertEquals(text, responseTweet.getText());
    assertEquals(id, responseTweet.getIdStr());
    assertNull(responseTweet.getEntities());

    try {
      String tweetJson = JsonParser.toJson(responseTweet, true, false);
      assertTrue(true);
    } catch (JsonProcessingException e) {
      fail();
    }
  }

  @Test
  public void deleteTweets() {
    // expected bad case
    try {
      twitterService.deleteTweets(new String[]{badIdStr});
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }

    // expected good case
    List<Tweet> deletedTweets = twitterService.deleteTweets(new String[]{"1379849145305989124"});

    assertNotNull(deletedTweets.get(0));
    assertEquals(text, deletedTweets.get(0).getText());

    assertEquals(latitude, deletedTweets.get(0).getCoordinates().getCoordinates()[1]);
    assertEquals(longitude, deletedTweets.get(0).getCoordinates().getCoordinates()[0]);

    assertEquals(mention, deletedTweets.get(0).getEntities().getUserMentions()[0].getScreenName());
    assertEquals(hashTag, deletedTweets.get(0).getEntities().getHashtags()[0].getText());
  }
}