package ca.jrvs.apps.twitter.util;

import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;

public class TweetUtil {
  public static Tweet builder(String text, Float lat, Float lon) {
    Tweet newTweet = new Tweet();
    Coordinates newCoord = new Coordinates();
    newCoord.setCoordinates(new Float[]{lon, lat});

    newTweet.setText(text);
    newTweet.setCoordinates(newCoord);
    return newTweet;
  }
}
