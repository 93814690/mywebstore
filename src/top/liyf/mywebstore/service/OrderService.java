package top.liyf.mywebstore.service;

import top.liyf.mywebstore.entity.Message;
import top.liyf.mywebstore.entity.Order;

import java.sql.SQLException;
import java.util.List;

public interface OrderService {
    Message createOrder(Order order, String[] pids) throws SQLException;

    List<Order> getOrderList(int uid) throws SQLException;

    Boolean cancelOrder(String oid) throws SQLException;

    Boolean deleteOrder(String oid) throws SQLException;

    Order getOrder(String oid) throws SQLException;

    Boolean successOrder(String oid) throws SQLException;
}
