package top.liyf.mywebstore.service.impl;

import top.liyf.mywebstore.dao.ProductDao;
import top.liyf.mywebstore.dao.impl.ProductDaoImpl;
import top.liyf.mywebstore.entity.Category;
import top.liyf.mywebstore.entity.Product;
import top.liyf.mywebstore.service.CategoryService;
import top.liyf.mywebstore.service.ProductService;
import top.liyf.mywebstore.util.Utils;
import top.liyf.mywebstore.util.Page;

import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author 123
 */
public class ProductServiceImpl implements ProductService {

    private ProductDao productDao = new ProductDaoImpl();
    private CategoryService categoryService = new CategoryServiceImpl();

    @Override
    public Boolean addProduct(Product product) throws SQLException {
        return productDao.addProduct(product);
    }

    @Override
    public Boolean deleteProduct(Product product) throws SQLException {
        String imgurl = product.getImgurl();
        Utils.deleteImg(imgurl);
        return productDao.deleteProduct(product);
    }

    @Override
    public Boolean updateProduct(Product product) throws SQLException {
        return productDao.updateProduct(product);
    }

    @Override
    public Product findProduct(String name) throws SQLException {
        return null;
    }

    @Override
    public List<Product> findAllProduct() throws SQLException {
        List<Product> allProduct = productDao.findAllProduct();
        for (Product product : allProduct) {
            addCategory(product);
        }
        return allProduct;
    }

    @Override
    public void addCategory(Product product) throws SQLException {
        int cid = product.getCid();
        Category category = categoryService.getCategoryByCid(cid);
        product.setCategory(category);
    }

    @Override
    public Page<Product> getPageData(String num) throws SQLException {

        int limit = 10;
        Page<Product> page = new Page<>();
        int totalRecordNumber=productDao.getTotalProductNumber();
        int currentPageNum = Integer.parseInt(num);
        int offset = (currentPageNum - 1) * limit;
        List<Product> pageList= productDao.getPageProduct(limit,offset);
        for (Product product : pageList) {
            addCategory(product);
        }

        page.setPage(page, totalRecordNumber, currentPageNum, limit, pageList);

        return page;
    }

    @Override
    public Boolean deleteMulti(String[] pidList) throws SQLException {
        ArrayList<String> imgurlList = new ArrayList<>();
        for (String pid : pidList) {
            Product product = productDao.findProductByPid(pid);
            String imgurl = product.getImgurl();
            imgurlList.add(imgurl);
        }
        Boolean deleteMulti = productDao.deleteMulti(pidList);
        if (deleteMulti) {
            for (String imgurl : imgurlList) {
                Utils.deleteImg(imgurl);
            }
        }
        return deleteMulti;
    }

    @Override
    public Product findProductByPid(String pid) throws SQLException {
        Product product = productDao.findProductByPid(pid);
        addCategory(product);
        return product;
    }

    @Override
    public Page<Product> getSearchPageData(String pid, String cid, String pname, String minPrice, String maxPrice, String pageNum) throws SQLException {

        int limit = 10;
        int minPriceInt = -1;
        int maxPriceInt = -1;
        if (minPrice != null &&(!"".equals(minPrice))) {
            minPriceInt = Integer.parseInt(minPrice);
        }
        if (maxPrice != null &&(!"".equals(maxPrice))) {
            maxPriceInt = Integer.parseInt(maxPrice);
        }
        Page<Product> page = new Page<>();
        int totalRecordNumber = productDao.getTotalSearchProductNumber(pid, cid, pname, minPriceInt, maxPriceInt);
        page.setTotalRecordsNum(totalRecordNumber);
        int totalPageNum = totalRecordNumber / limit + (totalRecordNumber % limit == 0 ? 0 : 1);
        page.setTotalPageNum(totalPageNum);
        int currentPageNum = Integer.parseInt(pageNum);
        page.setCurrentPageNum(currentPageNum);

        int offset = (currentPageNum - 1) * limit;
        List<Product> pageList = productDao.getSearchPageProduct(pid, cid, pname, minPriceInt, maxPriceInt, limit, offset);
        for (Product product : pageList) {
            addCategory(product);
        }
        page.setPageList(pageList);
        return page;
    }

    @Override
    public List<Product> findTopProduct() throws SQLException {
        List<Product> productList = new ArrayList<>();
        Product product = productDao.findProductByPid("1001002");
        productList.add(product);
        Product product2 = productDao.findProductByPid("1002002");
        productList.add(product2);
        Product product3 = productDao.findProductByPid("1001004");
        productList.add(product3);
        Product product4 = productDao.findProductByPid("1002001");
        productList.add(product4);
        return productList;
    }

    @Override
    public List<Product> findHotProduct() throws SQLException {
        List<Product> productList = new ArrayList<>();
        List<Product> allProduct = findAllProduct();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int s = random.nextInt(allProduct.size());
            productList.add(allProduct.remove(s));
        }
        return productList;
    }

    @Override
    public Page<Product> findProductPageByCid(String cid, String pageNum) throws SQLException {

        int limit = 6;
        Page<Product> page = new Page<>();
        int totalRecordNumber = productDao.getTotalProductNumberByCid(cid);
        int currentPageNum = Integer.parseInt(pageNum);
        int offset = (currentPageNum - 1) * limit;
        List<Product> productList = productDao.findProductPageByCid(cid, limit, offset);
        for (Product product : productList) {
            addCategory(product);
        }
        page.setPage(page, totalRecordNumber, currentPageNum, limit, productList);
        return page;
    }

    @Override
    public Page<Product> searchProductPageByKeyword(String keyword, String pageNum) throws SQLException {
        int limit = 9;
        Page<Product> page = new Page<>();
        int totalRecordNumber = productDao.getTotalProductNumberByKeyword(keyword);
        int currentPageNum = Integer.parseInt(pageNum);
        int offset = (currentPageNum - 1) * limit;
        List<Product> productList = productDao.searchProductPageByKeyword(keyword, limit, offset);
        for (Product product : productList) {
            addCategory(product);
        }
        page.setPage(page, totalRecordNumber, currentPageNum, limit, productList);
        return page;
    }
}
