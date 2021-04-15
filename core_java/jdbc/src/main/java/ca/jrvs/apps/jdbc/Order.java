package ca.jrvs.apps.jdbc;

import ca.jrvs.apps.jdbc.util.DataTransferObject;
import java.sql.Timestamp;
import java.util.List;

public class Order implements DataTransferObject {

  private long id;
  private Timestamp creationDate;
  private float totalDue;
  private String status;
  private long customerId;
  private long salespersonId;
  private List<OrderItem> orderList;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Timestamp getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(Timestamp creationDate) {
    this.creationDate = creationDate;
  }

  public float getTotalDue() {
    return totalDue;
  }

  public void setTotalDue(float totalDue) {
    this.totalDue = totalDue;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public long getCustomerId() {
    return customerId;
  }

  public void setCustomerId(long customerId) {
    this.customerId = customerId;
  }

  public long getSalespersonId() {
    return salespersonId;
  }

  public void setSalespersonId(long salespersonId) {
    this.salespersonId = salespersonId;
  }

  public List<OrderItem> getOrderList() {
    return orderList;
  }

  public void setOrderList(List<OrderItem> orderList) {
    this.orderList = orderList;
  }

  @Override
  public String toString() {
    return "Order{" +
        "id=" + id +
        ", creationDate=" + creationDate +
        ", totalDue=" + totalDue +
        ", status='" + status + '\'' +
        ", customerId=" + customerId +
        ", salespersonId=" + salespersonId +
        ", orderList=" + orderList +
        '}';
  }
}
