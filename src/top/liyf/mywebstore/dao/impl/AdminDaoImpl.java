package top.liyf.mywebstore.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import top.liyf.mywebstore.dao.AdminDao;
import top.liyf.mywebstore.entity.Admin;
import top.liyf.mywebstore.util.C3P0Util;

import java.sql.SQLException;
import java.util.List;

public class AdminDaoImpl implements AdminDao {
    @Override
    public Boolean addAdmin(String username, String password) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.ds);
        String sql = "insert into t_admin values (null,?,?)";
        int update = qr.update(sql, username, password);
        return update == 1;
    }

    @Override
    public Integer getAdminNum() throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.ds);
        Long query = qr.query("select count(*) from t_admin", new ScalarHandler<Long>());
        return query.intValue();
    }

    @Override
    public List<Admin> getPageList(int limit, int offset) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.ds);
        String sql = "select * from t_admin limit ? offset ?";
        List<Admin> adminList = qr.query(sql, new BeanListHandler<>(Admin.class), limit, offset);
        return adminList;
    }

    @Override
    public Boolean updateAdmin(String username, String password) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.ds);
        String sql = "update t_admin set password = ? where username = ?";
        int update = qr.update(sql, password, username);
        return update == 1;
    }

    @Override
    public Admin getAdminByName(String username) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.ds);
        String sql = "select * from t_admin where username = ?";
        Admin admin = qr.query(sql, new BeanHandler<>(Admin.class), username);
        return admin;
    }

    @Override
    public Admin adminLogin(String username, String password) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.ds);
        String sql = "select * from t_admin where username = ? and password = ?";
        Admin admin = qr.query(sql, new BeanHandler<>(Admin.class), username, password);
        return admin;
    }
}
