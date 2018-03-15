package top.liyf.mywebstore.dao;

import top.liyf.mywebstore.entity.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryDao {

    Boolean addCategory(Category category) throws SQLException;

    List<Category> findAllCategory() throws SQLException;

    Boolean deleteCategory(int cid) throws SQLException;

    Boolean updateCategory(Category category) throws SQLException;

    Category getCategoryByCid(int cid) throws SQLException;

    int getTotalCategoryNum() throws SQLException;

    List<Category> getPageCategory(int limit, int offset) throws SQLException;

    Boolean deleteMulti(String[] cidList) throws SQLException;

    Category getCategoryByCname(String cname) throws SQLException;
}
