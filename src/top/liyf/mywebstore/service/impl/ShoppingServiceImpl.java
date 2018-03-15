package top.liyf.mywebstore.service.impl;

import top.liyf.mywebstore.dao.ShoppingDao;
import top.liyf.mywebstore.dao.impl.ShoppingDaoImpl;
import top.liyf.mywebstore.entity.Product;
import top.liyf.mywebstore.entity.ShoppingCart;
import top.liyf.mywebstore.entity.ShoppingCartItem;
import top.liyf.mywebstore.service.ProductService;
import top.liyf.mywebstore.service.ShoppingService;
import top.liyf.mywebstore.util.Utils;

import java.sql.SQLException;
import java.util.List;

public class ShoppingServiceImpl implements ShoppingService {

    private ShoppingDao dao = new ShoppingDaoImpl();


    @Override
    public ShoppingCart getCartByUid(int uid) throws SQLException {

        ShoppingCart shoppingCart = dao.getCartByUid(uid);
        if (shoppingCart == null) {
            dao.addCart(uid);
            shoppingCart = dao.getCartByUid(uid);
        }
        shoppingCart.setShoppingItems(getCartItemList(shoppingCart.getSid()));
        return shoppingCart;
    }

    @Override
    public Boolean addCartItem(ShoppingCartItem item) throws SQLException {
        ShoppingCartItem itemExist = dao.itemExist(item);
        if (Utils.notNUll(itemExist)) {
            Boolean updateItemSnum = dao.updateItemSnum(itemExist, item.getSnum());
            return updateItemSnum;
        }
        return dao.addCartItem(item);
    }

    @Override
    public List<ShoppingCartItem> getCartItemList(int sid) throws SQLException {
        List<ShoppingCartItem> itemList = dao.getCartItemList(sid);
        ProductService productService = new ProductServiceImpl();
        for (ShoppingCartItem item : itemList) {
            Product product = productService.findProductByPid(item.getPid());
            item.setProduct(product);
        }
        return itemList;
    }

    @Override
    public Boolean deleteItem(int sid, String itemid) throws SQLException {
        return dao.deleteItem(sid, itemid);
    }
}
