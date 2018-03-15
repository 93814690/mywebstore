package top.liyf.mywebstore.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import top.liyf.mywebstore.dao.UserDao;
import top.liyf.mywebstore.entity.User;
import top.liyf.mywebstore.util.C3P0Util;
import top.liyf.mywebstore.util.Utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    @Override
    public Boolean addUser(User user) throws SQLException {

        QueryRunner qr = new QueryRunner(C3P0Util.ds);
        String sql = "insert into t_user values(null,?,?,?,?,null,?,?,?) ";
        ArrayList<String> param = new ArrayList<>();
        param.add(user.getUsername());
        param.add(user.getNickname());
        param.add(user.getPassword());
        param.add(user.getEmail());
        if (Utils.notNUll(user.getBirthday())) {
            sql = "insert into t_user values(null,?,?,?,?,?,?,?,?) ";
            param.add(user.getBirthday());
        }
        param.add(user.getState());
        param.add(user.getActivecode());
        param.add(user.getUpdatetime());
        Object[] objects = param.toArray();
        int update = qr.update(sql, objects);
        return update == 1;
    }

    @Override
    public User getUserByUsername(String username) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.ds);
        String sql = "select * from t_user where username = ?";
        User query = qr.query(sql, new BeanHandler<>(User.class), username);
        return query;
    }

    @Override
    public User login(String username, String password) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.ds);
        String sql = "select * from t_user where username = ? and password = ?";
        User user = qr.query(sql, new BeanHandler<>(User.class), username, password);
        return user;
    }

    @Override
    public int getTotalUserNum() throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.ds);
        String sql = "select count(*) from t_user";
        Long query = qr.query(sql, new ScalarHandler<Long>());
        return query.intValue();
    }

    @Override
    public List<User> findAllUserPage(int limit, int offset) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.ds);
        String sql = "select * from t_user limit ? offset ?";
        List<User> userList = qr.query(sql, new BeanListHandler<>(User.class), limit, offset);
        return userList;
    }

    @Override
    public Boolean activeUser(String code) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.ds);
        String sql = "update t_user set state = '1' where activecode = ?";
        int update = qr.update(sql, code);
        return update == 1;
    }

    @Override
    public User getUserByUid(String uid) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.ds);
        String sql = "select * from t_user where uid = ?";
        User user = qr.query(sql, new BeanHandler<>(User.class), uid);
        return user;
    }

    @Override
    public Boolean updateUser(User updateUser) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.ds);
        String sql = "update t_user set nickname = ? , password = ?, email = ? where uid = ?";
        ArrayList param = new ArrayList<>();
        param.add(updateUser.getNickname());
        param.add(updateUser.getPassword());
        param.add(updateUser.getEmail());
        if (Utils.notNUll(updateUser.getBirthday())) {
            sql ="update t_user set nickname = ? , password = ?, email = ?, birthday= ? where uid = ?";
            param.add(updateUser.getBirthday());
        }
        param.add(updateUser.getUid());
        Object[] objects = param.toArray();
        int update = qr.update(sql, objects);
        return update == 1;
    }
}
