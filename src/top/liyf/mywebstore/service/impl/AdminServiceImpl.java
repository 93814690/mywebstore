package top.liyf.mywebstore.service.impl;

import top.liyf.mywebstore.dao.AdminDao;
import top.liyf.mywebstore.dao.impl.AdminDaoImpl;
import top.liyf.mywebstore.domain.Admin;
import top.liyf.mywebstore.service.AdminService;
import top.liyf.mywebstore.util.Page;

import java.sql.SQLException;
import java.util.List;

public class AdminServiceImpl implements AdminService {

    private AdminDao dao = new AdminDaoImpl();

    @Override
    public Boolean addAdmin(String username, String password) throws SQLException {
        return dao.addAdmin(username, password);
    }

    @Override
    public Page<Admin> getPageData(String pageNum) throws SQLException {
        int limit = 3;
        Page<Admin> page = new Page<>();
        int totalRecordNum = dao.getAdminNum();
        page.setTotalRecordsNum(totalRecordNum);
        int totalPageNum = totalRecordNum / limit + (totalRecordNum % limit == 0 ? 0 : 1);
        page.setTotalPageNum(totalPageNum);
        int currentPageNum = Integer.parseInt(pageNum);
        page.setCurrentPageNum(currentPageNum);

        int offset = (currentPageNum - 1) * limit;
        List<Admin> adminList = dao.getPageList(limit, offset);
        page.setPageList(adminList);
        return page;
    }

    @Override
    public Boolean updateAdmin(String username, String password) throws SQLException {

        return dao.updateAdmin(username, password);
    }

    @Override
    public Admin adminLogin(String username, String password) throws SQLException {

        return dao.adminLogin(username, password);
    }

    @Override
    public Boolean adminUsernameExist(String username) throws SQLException {
        Admin admin = dao.getAdminByName(username);
        if (admin != null) {
            return true;
        }
        return false;
    }
}
