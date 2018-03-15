package top.liyf.mywebstore.service;

import top.liyf.mywebstore.domain.Product;
import top.liyf.mywebstore.util.Page;

import java.sql.SQLException;
import java.util.List;

public interface ProductService {

    Boolean addProduct(Product product) throws SQLException;

    Boolean deleteProduct(Product product) throws SQLException;

    Boolean updateProduct(Product product) throws SQLException;

    Product findProduct(String name) throws SQLException;

    List<Product> findAllProduct() throws SQLException;

    Product findProductByPid(String pid) throws SQLException;

    void addCategory(Product product) throws SQLException;

    Page<Product> getPageData(String num) throws SQLException;

    Boolean deleteMulti(String[] pidList) throws SQLException;

    Page<Product> getSearchPageData(String pid, String cid, String pname, String minPrice, String maxPrice, String pageNum) throws SQLException;

    List<Product> findTopProduct() throws SQLException;

    List<Product> findHotProduct() throws SQLException;

    Page<Product> findProductPageByCid(String cid, String pageNum) throws SQLException;

    Page<Product> searchProductPageByKeyword(String keyword, String pageNum) throws SQLException;
}
