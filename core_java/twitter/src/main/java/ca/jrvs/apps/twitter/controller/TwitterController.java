package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.util.TweetUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Controller
public class TwitterController implements Controller {

  private static final String COORD_SEP = ":";
  private static final String COMMA = ",";

  private Service service;

  @Autowired
  public TwitterController(Service service) { this.service = service; }

  @Override
  public Tweet postTweet(String[] args) throws IllegalArgumentException {
    String[] latLon = args[1].split(COORD_SEP);
    Tweet requestTweet = TweetUtil.builder(args[0], Float.parseFloat(latLon[0]), Float.parseFloat(latLon[1]));
    return service.postTweet(requestTweet);
  }

  @Override
  public Tweet showTweet(String[] args) throws IllegalArgumentException {
    if (args.length == 1)
      return service.showTweet(args[0], null);
    else {
      String[] fields = args[1].split(COMMA);
      return service.showTweet(args[0], fields);
    }
  }

  @Override
  public List<Tweet> deleteTweet(String[] args) throws IllegalArgumentException {
    String[] fields = args[0].split(COMMA);
    return service.deleteTweets(fields);
  }
}
