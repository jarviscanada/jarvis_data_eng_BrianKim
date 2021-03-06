package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
    "indices",
    "text"
})
public class Hashtag {

  private int[] indices = null;
  private String text;

  @JsonProperty("indices")
  public int[] getIndices() {
    return indices;
  }

  @JsonProperty("indices")
  public void setIndices(int[] indices) {
    this.indices = indices;
  }

  @JsonProperty("text")
  public String getText() {
    return text;
  }

  @JsonProperty("text")
  public void setText(String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return "Hashtag{" +
        "indices=" + indices +
        ", text='" + text + '\'' +
        '}';
  }
}
