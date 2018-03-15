package top.liyf.mywebstore.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import top.liyf.mywebstore.dao.ProductDao;
import top.liyf.mywebstore.entity.Product;
import top.liyf.mywebstore.util.C3P0Util;
import top.liyf.mywebstore.util.TransactionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao {


    @Override
    public Boolean addProduct(Product product) throws SQLException {

        QueryRunner qr = new QueryRunner(C3P0Util.ds);
        int update = qr.update("insert into t_product values (?,?,?,?,?,?,?,?)", product.getPid(), product.getPname(), product.getEstoreprice(), product.getMarkprice(), product.getPnum(), product.getCid(), product.getImgurl(), product.getDescription());
        return update == 1;
    }

    @Override
    public Boolean deleteProduct(Product product) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.ds);
        int update = qr.update("delete from t_product where pid=?", product.getPid());
        return update == 1;
    }

    @Override
    public Boolean updateProduct(Product product) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.ds);
        int update = qr.update("update t_product set pname=?,estoreprice=?,markprice=?,pnum=?,cid=?,imgurl=?,description=? where pid=?", product.getPname(), product.getEstoreprice(), product.getMarkprice(), product.getPnum(), product.getCid(), product.getImgurl(), product.getDescription(), product.getPid());
        return update == 1;
    }

    @Override
    public List<Product> findAllProduct() throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.ds);
        List<Product> productList = qr.query("select * from t_product", new BeanListHandler<Product>(Product.class));
        return productList;
    }

    @Override
    public Product findProductByPid(String pid) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.ds);
        Product product = qr.query("select * from t_product where pid=?", new BeanHandler<Product>(Product.class), pid);
        return product;
    }

    @Override
    public int getTotalProductNumber() throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.ds);
        Long query = qr.query("select count(*) from t_product", new ScalarHandler<Long>());
        return query.intValue();
    }

    @Override
    public List<Product> getPageProduct(int limit, int offset) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.ds);
        List<Product> productList = qr.query("select * from t_product limit ? offset ?", new BeanListHandler<Product>(Product.class), limit, offset);
        return productList;
    }

    @Override
    public Boolean deleteMulti(String[] pidList) throws SQLException {
        boolean flag = false;
        QueryRunner qr = new QueryRunner();
        Connection con = C3P0Util.getConnection();
        try {
            con.setAutoCommit(false);
            String sql = "delete from t_product where pid=?";
            for (String pid : pidList) {
                qr.update(con, sql, pid);
            }
            con.commit();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            con.rollback();
        }finally {
            con.close();
        }

        return flag;
    }

    @Override
    public int getTotalSearchProductNumber(String pid, String cid, String pname, int minPriceInt, int maxPriceInt) throws SQLException {

        QueryRunner qr = new QueryRunner(C3P0Util.ds);
        ArrayList arrayList = new ArrayList();
        String sql = "select count(*) from t_product where 1=1";
        if (!"".equals(pid)) {
            sql += " and pid = ? ";
            arrayList.add(pid);
        }
        if (!"".equals(cid)) {
            sql += " and cid = ? ";
            arrayList.add(cid);
        }
        if (!"".equals(pname)) {
            sql += " and pname like ? ";
            arrayList.add("%"+pname+"%");
        }
        if (minPriceInt != -1) {
            sql += " and estoreprice > ? ";
            arrayList.add(minPriceInt);
        }
        if (maxPriceInt != -1) {
            sql += " and estoreprice < ? ";
            arrayList.add(maxPriceInt);
        }
        Object[] objects = arrayList.toArray();
        Long query = qr.query(sql, new ScalarHandler<Long>(), objects);

        return query.intValue();
    }

    @Override
    public List<Product> getSearchPageProduct(String pid, String cid, String pname, int minPriceInt, int maxPriceInt, int limit, int offset) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.ds);
        ArrayList arrayList = new ArrayList();
        String sql = "select * from t_product where 1=1";
        if (!"".equals(pid)) {
            sql += " and pid = ? ";
            arrayList.add(pid);
        }
        if (!"".equals(cid)) {
            sql += " and cid = ? ";
            arrayList.add(cid);
        }
        if (!"".equals(pname)) {
            sql += " and pname like ? ";
            arrayList.add("%"+pname+"%");
        }
        if (minPriceInt != -1) {
            sql += " and estoreprice > ? ";
            arrayList.add(minPriceInt);
        }
        if (maxPriceInt != -1) {
            sql += " and estoreprice < ? ";
            arrayList.add(maxPriceInt);
        }
        sql += " limit ? offset ?";
        arrayList.add(limit);
        arrayList.add(offset);
        Object[] objects = arrayList.toArray();

        List<Product> productList = qr.query(sql, new BeanListHandler<>(Product.class), objects);
        return productList;
    }

    @Override
    public List<Product> findProductPageByCid(String cid, int limit, int offset) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.ds);
        String sql = "select * from t_product where cid=? limit ? offset ?";
        List<Product> productList = qr.query(sql, new BeanListHandler<>(Product.class), cid, limit, offset);
        return productList;
    }

    @Override
    public List<Product> searchProductPageByKeyword(String keyword, int limit, int offset) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.ds);
        String sql = "select * from t_product ";
        List<Product> productList;
        if ("".equals(keyword)) {
            sql += "limit ? offset ?";
            productList = qr.query(sql, new BeanListHandler<>(Product.class), limit, offset);
        } else {
            sql += "where pname like ? or description like ? limit ? offset ?";
            String param = "%" + keyword + "%";
//            System.out.println("param = " + param);
//            System.out.println("sql = " + sql);
            productList = qr.query(sql, new BeanListHandler<>(Product.class), param, param, limit, offset);
//            System.out.println("productList = " + productList);
        }
        return productList;
    }

    @Override
    public int getTotalProductNumberByCid(String cid) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.ds);
        String sql = "select count(*) from t_product where cid=?";
        Long query = qr.query(sql, new ScalarHandler<Long>(), cid);
        return query.intValue();
    }

    @Override
    public int getTotalProductNumberByKeyword(String keyword) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.ds);
        String sql = "select count(*) from t_product ";
        Long query;
        if ("".equals(keyword)) {
            query = qr.query(sql, new ScalarHandler<Long>());
        } else {
            sql += "where pname like ? or description like ? ";
            String param = "%" + keyword + "%";
            query = qr.query(sql, new ScalarHandler<Long>(), param, param);
        }
        return query.intValue();
    }

    @Override
    public void consumeProductNum(String pid, int buynum) throws SQLException {
        QueryRunner qr = new QueryRunner();
        Connection conn = TransactionManager.getConnection();
        String sql = "update t_product set pnum = pnum - ? where pid = ?";
        qr.update(conn, sql, buynum, pid);
    }

    @Override
    public void resumeProductNum(String pid, int buynum) throws SQLException {
        QueryRunner qr = new QueryRunner();
        Connection conn = TransactionManager.getConnection();
        String sql = "update t_product set pnum = pnum + ? where pid = ?";
        qr.update(conn, sql, buynum, pid);
    }
}
