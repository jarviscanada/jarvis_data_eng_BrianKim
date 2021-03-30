package ca.jrvs.apps.jdbc;

import ca.jrvs.apps.jdbc.util.DataAccessObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderDAO extends DataAccessObject<Order> {
  final static Logger logger = LoggerFactory.getLogger(OrderDAO.class);
  private static final String GET_ORDER =
      "SELECT c.first_name, c.last_name, c.email, c.customer_id, o.order_id, "
          + "o.creation_date, o.total_due, o.status, "
          + "s.first_name, s.last_name, s.email, s.salesperson_id, "
          + "ol.order_item_id, ol.quantity, p.product_id, p.code, p.name, p.size, p.variety, p.price "
          + "FROM orders o "
          + "join customer c on o.customer_id=c.customer_id "
          + "join salesperson s on o.salesperson_id=s.salesperson_id "
          + "join order_item ol on ol.order_id=o.order_id "
          + "join product p on ol.product_id=p.product_id "
          + "WHERE o.order_id=?";

  public OrderDAO(Connection connection) {
    super(connection);
  }

  @Override
  public Order findById(long id) {
    Order order = new Order();
    List<OrderItem> orderList = new ArrayList<>();
    try (PreparedStatement statement = this.connection.prepareStatement(GET_ORDER)) {
      statement.setLong(1, id);
      ResultSet rs = statement.executeQuery();
      while (rs.next()) {
        order.setId(rs.getLong("order_id"));
        order.setCreationDate(rs.getTimestamp("creation_date"));
        order.setTotalDue(rs.getFloat("total_due"));
        order.setStatus(rs.getString("status"));
        order.setCustomerId(rs.getLong("customer_id"));
        order.setSalespersonId(rs.getLong("salesperson_id"));

        OrderItem orderItem = new OrderItem();
        orderItem.setId(rs.getLong("order_item_id"));
        orderItem.setOrderId(rs.getLong("order_id"));
        orderItem.setProductId(rs.getLong("product_id"));
        orderItem.setQuantity(rs.getInt("quantity"));
        orderList.add(orderItem);
      }
      order.setOrderList(orderList);
    } catch (SQLException e) {
      logger.error("SQLException occurred "+e.getLocalizedMessage());
      throw new RuntimeException(e);
    }
    return order;
  }

  @Override
  public List<Order> findAll() {
    return null;
  }

  @Override
  public Order update(Order dto) {
    return null;
  }

  @Override
  public Order create(Order dto) {
    return null;
  }

  @Override
  public void delete(long id) {

  }
}
