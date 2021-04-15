package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.exception.CharacterExceedException;
import ca.jrvs.apps.twitter.exception.LatOutOfRangeException;
import ca.jrvs.apps.twitter.exception.LongOutOfRangeException;
import ca.jrvs.apps.twitter.exception.UserIdInputFormatException;
import ca.jrvs.apps.twitter.model.Tweet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class TwitterService implements Service {

  CrdDao dao;
  private static final Logger logger = LoggerFactory.getLogger(TwitterService.class);
  private static final String[] tweetFields = {
      "created_at",
      "id",
      "id_str",
      "text",
      "entities",
      "coordinates",
      "retweet_count",
      "favorite_count",
      "favorited",
      "retweeted"
  };

  @Autowired
  public TwitterService(CrdDao dao) { this.dao = dao; }

  @Override
  public Tweet postTweet(Tweet tweet) throws IllegalArgumentException {
    try {
      validatePostTweet(tweet);
    } catch (Exception e) {
      throw new IllegalArgumentException();
    }

    return (Tweet) dao.create(tweet);
  }

  @Override
  public Tweet showTweet(String id, String[] fields) throws IllegalArgumentException {
    try {
      validateIdTweet(id);
      if (fields != null)
        validateFieldTweet(fields);
    } catch (Exception e) {
      throw new IllegalArgumentException();
    }

    Tweet responseTweet = (Tweet) dao.findById(id);
    if (fields != null) {
      return filterTweet(responseTweet, fields);
    } else {
      return responseTweet;
    }
  }

  @Override
  public List<Tweet> deleteTweets(String[] ids) throws  IllegalArgumentException {
    List<Tweet> tweets = new ArrayList<>();
    Arrays.stream(ids)
        .forEach(id -> {
          try {
            validateIdTweet(id);
            tweets.add((Tweet) dao.deleteById(id));
          } catch (Exception e) {
            throw new IllegalArgumentException();
          }
        });
    return tweets;
  }

  public void validatePostTweet(Tweet tweet)
      throws CharacterExceedException, LongOutOfRangeException, LatOutOfRangeException {
    String text = tweet.getText();
    Float lon = tweet.getCoordinates().getCoordinates()[0];
    Float lat = tweet.getCoordinates().getCoordinates()[1];

    if (text.length() >= 140) {
      throw new CharacterExceedException();
    } else if (!(-90f <= lon && lon <= 90f)) {
      throw new LongOutOfRangeException();
    } else if (!(-90f <= lat && lat <= 90f)) {
      throw new LatOutOfRangeException();
    }
  }

  public void validateIdTweet(String id_str) throws UserIdInputFormatException {
    try {
      Long.parseLong(id_str);
    } catch (Exception e) {
      throw new UserIdInputFormatException();
    }
  }

  public void validateFieldTweet(String[] fields) throws IllegalArgumentException {
    Arrays.stream(fields).forEach(field -> {
      if (Arrays.stream(tweetFields).noneMatch(element -> element.equals(field)))
        throw new IllegalArgumentException();
    });
  }

  public Tweet filterTweet(Tweet returnedTweet, String[] fields) {
    Tweet filteredTweet = new Tweet();

    Arrays.stream(fields).forEach(field -> {
      switch (field) {
        case "created_at":
          filteredTweet.setCreatedAt(returnedTweet.getCreatedAt());
          break;
        case "id":
          filteredTweet.setId(returnedTweet.getId());
          break;
        case "id_str":
          filteredTweet.setIdStr(returnedTweet.getIdStr());
          break;
        case "text":
          filteredTweet.setText(returnedTweet.getText());
          break;
        case "entities":
          filteredTweet.setEntities(returnedTweet.getEntities());
          break;
        case "coordinates":
          filteredTweet.setCoordinates(returnedTweet.getCoordinates());
          break;
        case "retweet_count":
          filteredTweet.setRetweetCount(returnedTweet.getRetweetCount());
          break;
        case "favorite_count":
          filteredTweet.setFavoriteCount(returnedTweet.getFavoriteCount());
          break;
        case "favorited":
          filteredTweet.setFavorited(returnedTweet.isFavorited());
          break;
        case "retweeted":
          filteredTweet.setRetweeted(returnedTweet.isRetweeted());
          break;
      }
    });
    return filteredTweet;
  }
}
