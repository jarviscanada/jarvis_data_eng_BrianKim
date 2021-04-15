package ca.jrvs.apps.twitter.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.exception.CharacterExceedException;
import ca.jrvs.apps.twitter.exception.LatOutOfRangeException;
import ca.jrvs.apps.twitter.exception.LongOutOfRangeException;
import ca.jrvs.apps.twitter.exception.UserIdInputFormatException;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.TweetUtil;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitterServiceUnitTest {

  @Mock CrdDao dao;
  @InjectMocks TwitterService twitterService;

  String badText = "Miusov, as a man man of breeding and deilcacy, could not but feel some inwrd qualms, when he reached the Father Superior's with Ivan: he felt ashamed of havin lost his temper. He felt that he ought to have disdaimed that despicable wretch, Fyodor Pavlovitch, too much to have been upset by him in Father Zossima's cell, and so to have forgotten himself. \"Teh monks were not to blame, in any case,\" he reflceted, on the steps.";
  Float badLon = -100f;
  Float badLat = 100f;
  String badIdStr = "this is not an id";

  @Test
  public void postTweet() {
    when(dao.create(any())).thenReturn(new Tweet());
    Tweet tweet = twitterService.postTweet(TweetUtil.builder("something", -49f, 73f));
    assertNotNull(tweet);
  }

  @Test
  public void showTweet() {
    when(dao.findById(any())).thenReturn(new Tweet());
    Tweet tweet = twitterService.showTweet("123456789", new String[]{"text", "entity", "coordinates"});
    assertNotNull(tweet);
  }

  @Test
  public void deleteTweets() {
    when(dao.deleteById(any())).thenReturn(new Tweet());
    List<Tweet> tweets = twitterService.deleteTweets(new String[]{"123456789","987654321"});
    assertNotNull(tweets);
    assertEquals(2, tweets.size());
  }

  @Test
  public void validatePostTweet() {
    // expected wrong input
    try {
      twitterService.validatePostTweet(TweetUtil.builder("this has wrong longitude", badLon, 73f));
      fail();
    } catch (LatOutOfRangeException | CharacterExceedException | LongOutOfRangeException e) {
      assertTrue(true);
    }

    try {
      twitterService.validatePostTweet(TweetUtil.builder(badText, 49f, 73f));
      fail();
    } catch (CharacterExceedException | LongOutOfRangeException | LatOutOfRangeException e) {
      assertTrue(true);
    }

    try {
      twitterService.validateIdTweet(badIdStr);
      fail();
    } catch (UserIdInputFormatException e) {
      assertTrue(true);
    }
  }

  @Test
  public void validateFieldTweet() {
    // expected bad case
    try {
      twitterService.validateFieldTweet(new String[]{"this_is", "wrong_field"});
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }

    // expected good case
    try {
      twitterService.validateFieldTweet(new String[]{"id", "id_str", "coordinates"});
      assertTrue(true);
    } catch (IllegalArgumentException e) {
      fail();
    }
  }

  @Test
  public void filterTweet() {
//    Tweet responseTweet =
  }
}