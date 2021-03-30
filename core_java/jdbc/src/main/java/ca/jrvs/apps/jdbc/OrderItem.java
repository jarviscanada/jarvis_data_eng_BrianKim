package ca.jrvs.apps.jdbc;

import ca.jrvs.apps.jdbc.util.DataTransferObject;

public class OrderItem implements DataTransferObject {
  private long id;
  private long orderId;
  private long productId;
  private int quantity;

  @Override
  public long getId() {
    return 0;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getOrderItemId() {
    return id;
  }

  public long getOrderId() {
    return orderId;
  }

  public void setOrderId(long orderId) {
    this.orderId = orderId;
  }

  public long getProductId() {
    return productId;
  }

  public void setProductId(long productId) {
    this.productId = productId;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  @Override
  public String toString() {
    return "OrderItem{" +
        "id=" + id +
        ", orderId=" + orderId +
        ", productId=" + productId +
        ", quantity=" + quantity +
        '}';
  }
}
