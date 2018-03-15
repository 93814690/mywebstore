package top.liyf.mywebstore.service;

import top.liyf.mywebstore.entity.Admin;
import top.liyf.mywebstore.util.Page;

import java.sql.SQLException;

public interface AdminService {

    Boolean addAdmin(String username, String password) throws SQLException;

    Page<Admin> getPageData(String pageNum) throws SQLException;

    Boolean updateAdmin(String username, String password) throws SQLException;

    Admin adminLogin(String username, String password) throws SQLException;

    Boolean adminUsernameExist(String username) throws SQLException;
}
