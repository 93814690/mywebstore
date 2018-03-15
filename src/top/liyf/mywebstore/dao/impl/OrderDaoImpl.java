package top.liyf.mywebstore.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import top.liyf.mywebstore.dao.OrderDao;
import top.liyf.mywebstore.entity.Order;
import top.liyf.mywebstore.util.C3P0Util;
import top.liyf.mywebstore.util.TransactionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    @Override
    public Boolean addOrder(Order order) throws SQLException {
        QueryRunner qr = new QueryRunner();
        Connection connection = TransactionManager.getConnection();
        String sql = "insert into t_order values(?, ?, ?, ?, ?, ?, ?, ?)";
        ArrayList arrayList = new ArrayList();
        arrayList.add(order.getOid());
        arrayList.add(order.getMoney());
        arrayList.add(order.getRecipients());
        arrayList.add(order.getTel());
        arrayList.add(order.getAddress());
        arrayList.add(order.getState());
        arrayList.add(order.getOrdertime());
        arrayList.add(order.getUid());
        Object[] params = arrayList.toArray();
        int update = qr.update(connection, sql, params);
        return update == 1;
    }

    @Override
    public List<Order> getOrderList(int uid) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.ds);
        String sql = "select * from t_order where uid = ?";
        List<Order> query = qr.query(sql, new BeanListHandler<>(Order.class), uid);
        return query;
    }

    @Override
    public Boolean updateOrderState(String oid, int state) throws SQLException {
        QueryRunner qr = new QueryRunner();
        Connection conn = TransactionManager.getConnection();
        String sql = "update t_order set state = ? where oid = ?";
        int update = qr.update(conn, sql, state, oid);
        return update == 1;
    }

    @Override
    public Order getOrder(String oid) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.ds);
        String sql = "select * from t_order where oid = ?";

        return qr.query(sql, new BeanHandler<Order>(Order.class), oid);
    }
}
