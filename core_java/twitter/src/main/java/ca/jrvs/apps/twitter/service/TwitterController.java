package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.controller.Controller;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.TweetUtil;
import java.util.List;

public class TwitterController implements Controller {

  private static final String COORD_SEP = ":";
  private static final String COMMA = ",";

  private Service service;

  public TwitterController(Service service) { this.service = service; }

  @Override
  public Tweet postTweet(String[] args) throws IllegalArgumentException {
    Float[] latLon = parseLatLon(args[1]);
    Tweet requestTweet = TweetUtil.builder(args[0], latLon[0], latLon[1]);
    return service.postTweet(requestTweet);
  }

  @Override
  public Tweet showTweet(String[] args) throws IllegalArgumentException {
    String[] fields = args[1].split(COMMA);
    return service.showTweet(args[0], fields);
  }

  @Override
  public List<Tweet> deleteTweet(String[] args) throws IllegalArgumentException {
    return service.deleteTweets(args);
  }

  public Float[] parseLatLon(String s) throws IllegalArgumentException {
    String[] latLon = s.split(COORD_SEP);

    if (latLon.length != 2)
      throw new IllegalArgumentException();
    try {
      float lat = Float.parseFloat(latLon[0]);
      float lon = Float.parseFloat(latLon[1]);
      return new Float[]{lat, lon};
    } catch (Exception e) {
      throw new IllegalArgumentException();
    }
  }
}
