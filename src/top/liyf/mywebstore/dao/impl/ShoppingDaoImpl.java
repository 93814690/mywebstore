package top.liyf.mywebstore.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import top.liyf.mywebstore.dao.ShoppingDao;
import top.liyf.mywebstore.domain.ShoppingCart;
import top.liyf.mywebstore.domain.ShoppingCartItem;
import top.liyf.mywebstore.util.C3P0Util;
import top.liyf.mywebstore.util.TransactionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ShoppingDaoImpl implements ShoppingDao {


    @Override
    public ShoppingCart getCartByUid(int uid) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.ds);
        String sql = "select * from t_shoppingcart where uid = ?";
        return qr.query(sql, new BeanHandler<>(ShoppingCart.class), uid);
    }

    @Override
    public Boolean addCart(int uid) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.ds);
        String sql = "insert into t_shoppingcart values(null,?)";
        int update = qr.update(sql, uid);
        return update == 1;
    }

    @Override
    public Boolean addCartItem(ShoppingCartItem item) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.ds);
        String sql = "insert into t_shoppingitem values (null, ?, ?, ?)";
        int update = qr.update(sql, item.getSid(), item.getPid(), item.getSnum());
        return update == 1;
    }


    @Override
    public List<ShoppingCartItem> getCartItemList(int sid) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.ds);
        String sql = "select * from t_shoppingitem where sid = ?";
        List<ShoppingCartItem> cartItems = qr.query(sql, new BeanListHandler<>(ShoppingCartItem.class), sid);
        return cartItems;
    }

    @Override
    public ShoppingCartItem itemExist(ShoppingCartItem item) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.ds);
        String sql = "select * from t_shoppingitem where sid = ? and pid = ?";
        ShoppingCartItem cartItem = qr.query(sql, new BeanHandler<>(ShoppingCartItem.class), item.getSid(), item.getPid());
        return cartItem;
    }

    @Override
    public Boolean updateItemSnum(ShoppingCartItem itemExist, int snum) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.ds);
        String sql = "update t_shoppingitem set snum = snum + ? where itemid = ?";
        int update = qr.update(sql, snum, itemExist.getItemid());
        return update == 1;
    }

    @Override
    public Boolean deleteItem(int sid, String itemid) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.ds);
        String sql = "delete from t_shoppingitem where sid = ? and itemid = ?";
        int update = qr.update(sql, sid, itemid);
        return update == 1;
    }

    @Override
    public ShoppingCartItem getCartItem(int sid, String pid) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.ds);
        String sql = "select * from t_shoppingitem where sid = ? and pid = ?";
        ShoppingCartItem shoppingCartItem = qr.query(sql, new BeanHandler<>(ShoppingCartItem.class), sid, pid);
        return shoppingCartItem;
    }

    @Override
    public void deleteItemByPid(int sid, String pid) throws SQLException {
        QueryRunner qr = new QueryRunner();
        Connection conn = TransactionManager.getConnection();
        String sql = "delete from t_shoppingitem where sid = ? and pid = ?";
        qr.update(conn, sql, sid, pid);
    }
}
