package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "indices",
    "text"
})
public class Hashtag {
  private int[] indices = null;
  private String text;

  public int[] getIndices() {
    return indices;
  }

  public void setIndices(int[] indices) {
    this.indices = indices;
  }

  public String getText() {
    return text;
  }

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
