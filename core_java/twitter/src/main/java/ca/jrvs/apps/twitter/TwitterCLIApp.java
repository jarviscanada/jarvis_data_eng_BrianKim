package ca.jrvs.apps.twitter;

import ca.jrvs.apps.twitter.controller.Controller;
import ca.jrvs.apps.twitter.controller.TwitterController;
import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.example.JsonParser;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.service.TwitterService;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TwitterCLIApp {
  private Controller controller;
  private static final Logger logger = LoggerFactory.getLogger(TwitterCLIApp.class);

  @Autowired
  public TwitterCLIApp(Controller controller) {
    this.controller = controller;
  }

  public static void main(String[] args) {
    HttpHelper httpHelper = new TwitterHttpHelper(
        System.getenv("consumerKey"), System.getenv("consumerSecret"),
        System.getenv("accessToken"), System.getenv("tokenSecret"));
    CrdDao twitterDao = new TwitterDao(httpHelper);
    Service twitterService = new TwitterService(twitterDao);
    Controller twitterController = new TwitterController(twitterService);

    TwitterCLIApp twitterApp = new TwitterCLIApp(twitterController);
    twitterApp.run(args);
  }

  public void run(String[] args) throws IllegalArgumentException {
    if (args.length <= 1)
      throw new IllegalArgumentException();
    switch (args[0]) {
      case "post":
        Tweet postedTweet = controller.postTweet(new String[]{args[1], args[2]});
        printTweet(postedTweet);
        break;
      case "show":
        Tweet shownTweet = args.length == 3
            ? controller.showTweet(new String[]{args[1], args[2]})
            : controller.showTweet(new String[]{args[1]});
        printTweet(shownTweet);
        break;
      case "delete":
        List<Tweet> deletedTweets = controller.deleteTweet(new String[]{args[1]});
        deletedTweets.forEach(this::printTweet);
        break;
      default:
        throw new IllegalArgumentException("Invalid arguments: post|show|delete [options]");
    }
  }

  public void printTweet(Tweet tweet) {
    try {
      String jsonOutput = JsonParser.toJson(tweet, true, false);
      logger.info(jsonOutput);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Failed to convert Tweet object into Json", e);
    }
  }
}
