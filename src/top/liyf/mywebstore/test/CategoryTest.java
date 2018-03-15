package top.liyf.mywebstore.test;

import org.junit.Assert;
import org.junit.Test;
import top.liyf.mywebstore.dao.CategoryDao;
import top.liyf.mywebstore.dao.impl.CategoryDaoImpl;
import top.liyf.mywebstore.domain.Category;

import java.sql.SQLException;
import java.util.List;

public class CategoryTest {

    @Test
    public void addCategoryTest() {

        Category category = new Category();
        category.setCname("Apple");
        CategoryDao categoryDao = new CategoryDaoImpl();
        try {
            Boolean addCategory = categoryDao.addCategory(category);
            Assert.assertEquals(true, addCategory);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findAllCategoryTest() {
        CategoryDao categoryDao = new CategoryDaoImpl();
        try {
            List<Category> allCategory = categoryDao.findAllCategory();
            System.out.println(allCategory);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteCategoryTest() {
        CategoryDao categoryDao = new CategoryDaoImpl();
        try {
            Boolean deleteCategory = categoryDao.deleteCategory(1007);
            Assert.assertEquals(true, deleteCategory);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
