package top.liyf.mywebstore.dao;

import top.liyf.mywebstore.domain.OrderItem;

import java.sql.SQLException;
import java.util.List;

public interface OrderItemDao {
    Boolean addItem(OrderItem item) throws SQLException;

    List<OrderItem> getOrderItemList(String oid) throws SQLException;
}
