package top.liyf.mywebstore.dao;

import top.liyf.mywebstore.entity.ShoppingCart;
import top.liyf.mywebstore.entity.ShoppingCartItem;

import java.sql.SQLException;
import java.util.List;

public interface ShoppingDao {


    ShoppingCart getCartByUid(int uid) throws SQLException;

    Boolean addCart(int uid) throws SQLException;

    Boolean addCartItem(ShoppingCartItem item) throws SQLException;

    List<ShoppingCartItem> getCartItemList(int sid) throws SQLException;

    ShoppingCartItem itemExist(ShoppingCartItem item) throws SQLException;

    Boolean updateItemSnum(ShoppingCartItem itemExist, int snum) throws SQLException;

    Boolean deleteItem(int sid, String itemid) throws SQLException;

    ShoppingCartItem getCartItem(int sid, String pid) throws SQLException;

    void deleteItemByPid(int sid, String pid) throws SQLException;
}
