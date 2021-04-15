package ca.jrvs.apps.twitter.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.example.JsonParser;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.TweetUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitterDaoUnitTest {

  @Mock HttpHelper httpHelper;
  @InjectMocks TwitterDao twitterDao;
  String jsonStr = "{\n"
      + "   \"created_at\":\"Mon Feb 18 21:24:39 +0000 2019\",\n"
      + "   \"id\":1097607853932564480,\n"
      + "   \"id_str\":\"1097607853932564480\",\n"
      + "   \"text\":\"test with loc223\",\n"
      + "   \"entities\":{\n"
      + "      \"hashtags\":[],\n"
      + "      \"user_mentions\":[]\n"
      + "   },\n"
      + "   \"coordinates\":{\n"
      + "      \"coordinates\":[\n"
      + "         -75.14310264,\n"
      + "         40.05701649\n"
      + "      ],\n"
      + "      \"type\":\"Point\"\n"
      + "   },\n"
      + "   \"retweet_count\":0,\n"
      + "   \"favorite_count\":0,\n"
      + "   \"favorited\":false,\n"
      + "   \"retweeted\":false\n"
      + "}";

  @Test
  public void create() throws Exception {
    String mention = "TorontoStar";
    String hashTag = "covid";
    String text = "@" + mention + " This is a test tweet 5 " + "#" + hashTag;
    Float latitude = 43.65f;
    Float longitude = -79.38f;

    // expected bad case
    when(httpHelper.httpPost(isNotNull())).thenThrow(new RuntimeException());

    try {
      twitterDao.create(TweetUtil.builder(text, latitude, longitude));
      fail();
    } catch (RuntimeException e) {
      assertTrue(true);
    }

    // expected good case
    TwitterDao spyDao = spy(twitterDao);
    Tweet expectedTweet = JsonParser.toObjectFromJson(jsonStr, Tweet.class);
    doReturn(expectedTweet).when(spyDao).sendRequest(any(), any());
    Tweet returnedTweet = spyDao.create(TweetUtil.builder(text, latitude, longitude));

    assertNotNull(returnedTweet);
    assertNotNull(returnedTweet.getText());
  }

  @Test
  public void findById() throws Exception {
    String id = "1379471678179270659";

    // expected bad case
    when(httpHelper.httpGet(isNotNull())).thenThrow(new RuntimeException());
    try {
      twitterDao.findById(id);
      fail();
    } catch (RuntimeException e) {
      assertTrue(true);
    }

    // expected good case
    TwitterDao spyDao = spy(twitterDao);
    Tweet expectedTweet = JsonParser.toObjectFromJson(jsonStr, Tweet.class);
    doReturn(expectedTweet).when(spyDao).sendRequest(any(), any());
    Tweet returnedTweet = spyDao.findById(id);

    assertNotNull(returnedTweet);
    assertNotNull(returnedTweet.getText());
    assertNotNull(returnedTweet.getCreatedAt());
  }

  @Test
  public void deleteById() throws Exception {
    String id = "1379471678179270659";

    // expected bad case
    when(httpHelper.httpPost(isNotNull())).thenThrow(new RuntimeException());
    try {
      twitterDao.deleteById(id);
      fail();
    } catch (RuntimeException e) {
      assertTrue(true);
    }

    // expected good case
    TwitterDao spyDao = spy(twitterDao);
    Tweet expectedTweet = JsonParser.toObjectFromJson(jsonStr, Tweet.class);
    doReturn(expectedTweet).when(spyDao).sendRequest(any(), any());
    Tweet deletedTweet = spyDao.deleteById(id);

    assertNotNull(deletedTweet);
    assertNotNull(deletedTweet.getText());
    assertNotNull(deletedTweet.getCreatedAt());
  }
}