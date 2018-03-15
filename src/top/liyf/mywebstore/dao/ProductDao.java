package top.liyf.mywebstore.dao;

import top.liyf.mywebstore.domain.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao {

    Boolean addProduct(Product product) throws SQLException;

    Boolean deleteProduct(Product product) throws SQLException;

    Boolean updateProduct(Product product) throws SQLException;

    List<Product> findAllProduct() throws SQLException;

    Product findProductByPid(String pid) throws SQLException;

    int getTotalProductNumber() throws SQLException;

    List<Product> getPageProduct(int limit, int offset) throws SQLException;

    Boolean deleteMulti(String[] pidList) throws SQLException;

    int getTotalSearchProductNumber(String pid, String cid, String pname, int minPriceInt, int maxPriceInt) throws SQLException;

    List<Product> getSearchPageProduct(String pid, String cid, String pname, int minPriceInt, int maxPriceInt, int limit, int offset) throws SQLException;

    List<Product> findProductPageByCid(String cid, int limit, int offset) throws SQLException;

    List<Product> searchProductPageByKeyword(String keyword, int limit, int offset) throws SQLException;

    int getTotalProductNumberByCid(String cid) throws SQLException;

    int getTotalProductNumberByKeyword(String keyword) throws SQLException;

    void consumeProductNum(String pid, int buynum) throws SQLException;

    void resumeProductNum(String pid, int buynum) throws SQLException;
}
