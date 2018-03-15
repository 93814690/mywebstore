package top.liyf.mywebstore.dao;

import top.liyf.mywebstore.entity.Admin;

import java.sql.SQLException;
import java.util.List;

public interface AdminDao {

    Boolean addAdmin(String username, String password) throws SQLException;

    Integer getAdminNum() throws SQLException;

    List<Admin> getPageList(int limit, int offset) throws SQLException;

    Boolean updateAdmin(String username, String password) throws SQLException;

    Admin getAdminByName(String username) throws SQLException;

    Admin adminLogin(String username, String password) throws SQLException;

}
