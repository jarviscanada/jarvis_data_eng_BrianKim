package ca.jrvs.apps.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JDBCExecutor {
  final static Logger logger = LoggerFactory.getLogger(JDBCExecutor.class);

  public static void main(String[] args) {
    BasicConfigurator.configure();
    DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost", "hplussport", "briankim", "7596kim");
    try {
      Connection connection = dcm.getConnection();
      OrderDAO orderDao = new OrderDAO(connection);
      Order order = orderDao.findById(1000);
      System.out.println(order);
    } catch (SQLException e) {
      logger.error("SQLException: "+e.getLocalizedMessage(), e);
    }
  }
}
