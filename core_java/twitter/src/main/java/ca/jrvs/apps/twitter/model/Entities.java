package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;

// @JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
    "hashtags",
    "user_mentions"
})
public class Entities {
  private Hashtag[] hashtags = null;
  @JsonProperty("user_mentions")
  private UserMention[] userMentions = null;

  @JsonProperty("hashtags")
  public Hashtag[] getHashtags() {
    return hashtags;
  }

  @JsonProperty("hashtags")
  public void setHashtags(Hashtag[] hashtags) {
    this.hashtags = hashtags;
  }

  @JsonProperty("user_mentions")
  public UserMention[] getUserMentions() {
    return userMentions;
  }

  @JsonProperty("user_mentions")
  public void setUserMentions(UserMention[] userMentions) {
    this.userMentions = userMentions;
  }

  @Override
  public String toString() {
    return "Entities{" +
        "hashtags=" + hashtags +
        ", userMentions=" + userMentions +
        '}';
  }
}
