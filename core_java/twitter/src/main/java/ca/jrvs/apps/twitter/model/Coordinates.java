package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

// @JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
    "coordinates",
    "type"
})
public class Coordinates {
  private Float[] coordinates = null;
  private String type = "Point";

  @JsonProperty("coordinates")
  public Float[] getCoordinates() {
    return coordinates;
  }

  @JsonProperty("coordinates")
  public void setCoordinates(Float[] coordinates) {
    this.coordinates = coordinates;
  }

  @JsonProperty("type")
  public String getType() {
    return type;
  }

  @JsonProperty("type")
  public void setType(String type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return "Coordinates{" +
        "coordinates=" + coordinates +
        ", type='" + type + '\'' +
        '}';
  }
}
