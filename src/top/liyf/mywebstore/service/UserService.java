package top.liyf.mywebstore.service;

import top.liyf.mywebstore.entity.User;
import top.liyf.mywebstore.util.Page;

import java.sql.SQLException;

public interface UserService {

    Boolean addUser(User user) throws SQLException;

    Boolean usernameExist(String username) throws SQLException;

    User login(String username, String password) throws SQLException;

    Page<User> findAllUser(String pageNum) throws SQLException;

    String getUserUpdateTime(String username) throws SQLException;

    Boolean activeUser(String code) throws SQLException;

    User getUserByUid(String uid) throws SQLException;

    Boolean updateUser(User updateUser) throws SQLException;
}
