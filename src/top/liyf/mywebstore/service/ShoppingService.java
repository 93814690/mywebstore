package top.liyf.mywebstore.service;

import top.liyf.mywebstore.entity.ShoppingCart;
import top.liyf.mywebstore.entity.ShoppingCartItem;

import java.sql.SQLException;
import java.util.List;

public interface ShoppingService {

    ShoppingCart getCartByUid(int uid) throws SQLException;

    Boolean addCartItem(ShoppingCartItem item) throws SQLException;

    List<ShoppingCartItem> getCartItemList(int sid) throws SQLException;

    Boolean deleteItem(int sid, String itemid) throws SQLException;
}
