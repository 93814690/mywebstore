package top.liyf.mywebstore.service.impl;

import top.liyf.mywebstore.dao.UserDao;
import top.liyf.mywebstore.dao.impl.UserDaoImpl;
import top.liyf.mywebstore.domain.User;
import top.liyf.mywebstore.service.UserService;
import top.liyf.mywebstore.util.Page;
import top.liyf.mywebstore.util.Utils;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDao dao = new UserDaoImpl();

    @Override
    public Boolean addUser(User user) throws SQLException {

        Boolean addUser = dao.addUser(user);
        return addUser;
    }

    @Override
    public Boolean usernameExist(String username) throws SQLException {
        User userByUsername = dao.getUserByUsername(username);
        return Utils.notNUll(userByUsername);
    }

    @Override
    public User login(String username, String password) throws SQLException {
        return dao.login(username, password);
    }

    @Override
    public Page<User> findAllUser(String pageNum) throws SQLException {

        int limit = 2;
        Page<User> page = new Page<>();
        int totalUserNum = dao.getTotalUserNum();
        int currentPageNum = Integer.parseInt(pageNum);
        int offset = (currentPageNum - 1) * limit;
        List<User> pageList = dao.findAllUserPage(limit, offset);
        page.setPage(page, totalUserNum, currentPageNum, limit, pageList);
        return page;
    }

    @Override
    public String getUserUpdateTime(String username) throws SQLException {
        return dao.getUserByUsername(username).getUpdatetime();
    }

    @Override
    public Boolean activeUser(String code) throws SQLException {
        return dao.activeUser(code);
    }

    @Override
    public User getUserByUid(String uid) throws SQLException {
        return dao.getUserByUid(uid);
    }

    @Override
    public Boolean updateUser(User updateUser) throws SQLException {
        return dao.updateUser(updateUser);
    }
}
