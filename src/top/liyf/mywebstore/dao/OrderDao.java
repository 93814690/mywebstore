package top.liyf.mywebstore.dao;

import top.liyf.mywebstore.domain.Order;

import java.sql.SQLException;
import java.util.List;

public interface OrderDao {

    Boolean addOrder(Order order) throws SQLException;

    List<Order> getOrderList(int uid) throws SQLException;

    Boolean updateOrderState(String oid, int state) throws SQLException;

    Order getOrder(String oid) throws SQLException;
}
