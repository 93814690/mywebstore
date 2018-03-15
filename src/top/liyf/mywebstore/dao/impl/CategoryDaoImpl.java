package top.liyf.mywebstore.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import top.liyf.mywebstore.dao.CategoryDao;
import top.liyf.mywebstore.entity.Category;
import top.liyf.mywebstore.util.C3P0Util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    @Override
    public Boolean addCategory(Category category) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.ds);
        int update = qr.update("insert into t_category (cname) values (?)", category.getCname());
        if (update == 1) {
            return true;
        }
        return false;
    }

    @Override
    public List<Category> findAllCategory() throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.ds);
        List<Category> categoryList = qr.query("select * from t_category", new BeanListHandler<Category>(Category.class));
        return categoryList;
    }

    @Override
    public Boolean deleteCategory(int cid) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.ds);
        int delete = qr.update("delete from t_category where cid = ?", cid);
        if (delete == 1) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean updateCategory(Category category) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.ds);
        int update = qr.update("update t_category set cname=? where cid=?", category.getCname(), category.getCid());
        if (update == 1) {
            return true;
        }
        return false;
    }

    @Override
    public Category getCategoryByCid(int cid) throws SQLException {

        QueryRunner qr = new QueryRunner(C3P0Util.ds);
        Category category = qr.query("select * from t_category where cid = ?", new BeanHandler<Category>(Category.class), cid);
        return category;
    }

    @Override
    public int getTotalCategoryNum() throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.ds);
        Long query = qr.query("select count(*) from t_category", new ScalarHandler<Long>());
        return query.intValue();
    }

    @Override
    public List<Category> getPageCategory(int limit, int offset) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.ds);
        List<Category> categoryList = qr.query("select * from t_category limit ? offset ?", new BeanListHandler<Category>(Category.class), limit, offset);
        return categoryList;
    }

    @Override
    public Boolean deleteMulti(String[] cidList) throws SQLException {
        QueryRunner qr = new QueryRunner();
        Connection connection = C3P0Util.getConnection();
        boolean flag = false;
        try {
            connection.setAutoCommit(false);
            String sql = "delete from t_category where cid = ?";
            for (String s : cidList) {
                qr.update(connection, sql, Integer.parseInt(s));
            }
            connection.commit();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
        } finally {
            connection.close();
        }

        return flag;
    }

    @Override
    public Category getCategoryByCname(String cname) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.ds);
        Category category = qr.query("select * from t_category where cname = ?", new BeanHandler<Category>(Category.class), cname);
        return category;
    }
}
