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

public class TwitterService implements Service {

  CrdDao dao;
  private static final Logger logger = LoggerFactory.getLogger(TwitterService.class);

  public TwitterService(CrdDao dao) { this.dao = dao; }
  @Override
  public Tweet postTweet(Tweet tweet) {
    try {
      validatePostTweet(tweet);
    } catch (RuntimeException e) {
      logger.error("Failed to validate tweet: ", e);
    }

    return (Tweet) dao.create(tweet);
  }

  @Override
  public Tweet showTweet(String id, String[] fields) {
    try {
      validateShowDeleteTweet(id);
    } catch (RuntimeException e) {
      logger.error("Failed to validate the user input ID: ", e);
    }

    return (Tweet) dao.findById(id);
  }

  @Override
  public List<Tweet> deleteTweets(String[] ids) {
    List<Tweet> tweets = new ArrayList<>();
    Arrays.stream(ids)
        .forEach(id -> {
          try {
            validateShowDeleteTweet(id);
            tweets.add((Tweet) dao.deleteById(id));
          } catch (RuntimeException e) {
            logger.error("Failed to validate the user input ID: ", e);
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

  public void validateShowDeleteTweet(String id_str) throws UserIdInputFormatException {
    try {
      Long.parseLong(id_str);
    } catch (Exception e) {
      throw new UserIdInputFormatException();
    }
  }
}
