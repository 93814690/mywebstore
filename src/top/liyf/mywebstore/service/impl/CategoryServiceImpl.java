package top.liyf.mywebstore.service.impl;

import top.liyf.mywebstore.dao.CategoryDao;
import top.liyf.mywebstore.dao.impl.CategoryDaoImpl;
import top.liyf.mywebstore.entity.Category;
import top.liyf.mywebstore.service.CategoryService;
import top.liyf.mywebstore.util.Page;

import java.sql.SQLException;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    private CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public Boolean addCategory(Category category) throws SQLException {

        Boolean addCategory = categoryDao.addCategory(category);
        return addCategory;
    }

    @Override
    public List<Category> findAllCategory() throws SQLException {

        List<Category> allCategory = categoryDao.findAllCategory();
        return allCategory;
    }

    @Override
    public Boolean deleteCategory(int cid) throws SQLException {

        return categoryDao.deleteCategory(cid);
    }

    @Override
    public Boolean updateCategory(Category category) throws SQLException {
        Boolean updateCategory = categoryDao.updateCategory(category);
        return updateCategory;
    }

    @Override
    public Category getCategoryByCid(int cid) throws SQLException {

        Category category = categoryDao.getCategoryByCid(cid);
        return category;
    }

    @Override
    public Page<Category> getPageData(String num) throws SQLException {
        int limit = 10;
        Page<Category> page = new Page<>();
        int totalRecordNum = categoryDao.getTotalCategoryNum();
        page.setTotalRecordsNum(totalRecordNum);
        int totalPageNum = totalRecordNum / limit + (totalRecordNum % limit == 0 ? 0 : 1);
        page.setTotalPageNum(totalPageNum);
        int currentPageNum = Integer.parseInt(num);
        page.setCurrentPageNum(currentPageNum);
        int offset = (currentPageNum - 1) * limit;
        List<Category> categoryList = categoryDao.getPageCategory(limit, offset);
        page.setPageList(categoryList);
        return page;
    }

    @Override
    public Boolean deleteMulti(String[] cidList) throws SQLException {
        return categoryDao.deleteMulti(cidList);
    }

    @Override
    public Category getCategoryByCname(String cname) throws SQLException {
        return categoryDao.getCategoryByCname(cname);
    }
}
