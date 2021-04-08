package ca.jrvs.apps.twitter.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import ca.jrvs.apps.twitter.controller.TwitterController;
import ca.jrvs.apps.twitter.model.Tweet;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitterControllerUnitTest {
  @Mock Service service;
  @InjectMocks
  TwitterController twitterController;

  @Test
  public void postTweet() {
    when(service.postTweet(any())).thenReturn(new Tweet());
    Tweet tweet = twitterController.postTweet(new String[]{"this is a tweet", "43:-79"});
    assertNotNull(tweet);
  }

  @Test
  public void showTweet() {
    when(service.showTweet(anyString(), any())).thenReturn(new Tweet());
    Tweet tweet = twitterController.showTweet(new String[]{"1234567890", "text, id, id_str"});
    assertNotNull(tweet);
  }

  @Test
  public void deleteTweet() {
    when(service.deleteTweets(any())).thenReturn(new ArrayList<>());
    List<Tweet> tweet = twitterController.deleteTweet(new String[]{"1234567890"});
    assertNotNull(tweet);
  }
}