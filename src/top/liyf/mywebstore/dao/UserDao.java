package top.liyf.mywebstore.dao;

import top.liyf.mywebstore.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {

    Boolean addUser(User user) throws SQLException;

    User getUserByUsername(String username) throws SQLException;

    User login(String username, String password) throws SQLException;

    int getTotalUserNum() throws SQLException;

    List<User> findAllUserPage(int limit, int offset) throws SQLException;


    Boolean activeUser(String code) throws SQLException;

    User getUserByUid(String uid) throws SQLException;

    Boolean updateUser(User updateUser) throws SQLException;

}
