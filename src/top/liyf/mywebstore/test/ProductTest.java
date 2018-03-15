package top.liyf.mywebstore.test;

import org.junit.Test;
import top.liyf.mywebstore.dao.ProductDao;
import top.liyf.mywebstore.dao.impl.ProductDaoImpl;
import top.liyf.mywebstore.entity.Product;

import java.sql.SQLException;
import java.util.List;

public class ProductTest {

    ProductDao dao = new ProductDaoImpl();
    @Test
    public void addProductTest() {
        Product product = new Product();
        product.setPid("123");
        product.setCid(1001);
        try {
            dao.addProduct(product);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateProductTest() {
        Product product = new Product();
        product.setPid("test");
        product.setPname("aaa");
        product.setCid(1002);
        try {
            dao.updateProduct(product);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void searchProductNum() {
        try {
            int totalSearchProductNumber = dao.getTotalSearchProductNumber("", "", "小米", -1, -1);
            System.out.println(totalSearchProductNumber);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void searchProductList() {
        try {
            List<Product> pageProduct = dao.getSearchPageProduct("", "", "", 100, -1, 2, 0);
            System.out.println(pageProduct);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
