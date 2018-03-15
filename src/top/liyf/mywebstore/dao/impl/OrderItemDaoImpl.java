package top.liyf.mywebstore.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import top.liyf.mywebstore.dao.OrderItemDao;
import top.liyf.mywebstore.domain.OrderItem;
import top.liyf.mywebstore.util.C3P0Util;
import top.liyf.mywebstore.util.TransactionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderItemDaoImpl implements OrderItemDao {

    @Override
    public Boolean addItem(OrderItem item) throws SQLException {
        QueryRunner qr = new QueryRunner();
        Connection connection = TransactionManager.getConnection();
        String sql = "insert into t_orderitem values(null,?,?,?)";
        int update = qr.update(connection, sql, item.getOid(), item.getPid(), item.getBuynum());
        return update == 1;
    }

    @Override
    public List<OrderItem> getOrderItemList(String oid) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.ds);
        String sql = "select * from t_orderitem where oid = ?";
        return qr.query(sql, new BeanListHandler<OrderItem>(OrderItem.class), oid);
    }


}
