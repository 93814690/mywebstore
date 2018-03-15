package top.liyf.mywebstore.service;

import top.liyf.mywebstore.entity.Category;
import top.liyf.mywebstore.util.Page;

import java.sql.SQLException;
import java.util.List;

public interface CategoryService {

    Boolean addCategory(Category category) throws SQLException;

    List<Category> findAllCategory() throws SQLException;

    Boolean deleteCategory(int cid) throws SQLException;

    Boolean updateCategory(Category category) throws SQLException;

    Category getCategoryByCid(int cid) throws SQLException;

    Page<Category> getPageData(String num) throws SQLException;

    Boolean deleteMulti(String[] cidList) throws SQLException;

    Category getCategoryByCname(String cname) throws SQLException;
}
