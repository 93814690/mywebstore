package top.liyf.mywebstore.controller;

import top.liyf.mywebstore.entity.Category;
import top.liyf.mywebstore.service.CategoryService;
import top.liyf.mywebstore.service.impl.CategoryServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "AllCategoryServlet", value = "/AllCategoryServlet", loadOnStartup = 0)
public class AllCategoryServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        CategoryService categoryService = new CategoryServiceImpl();
        List<Category> allCategory = null;
        try {
            allCategory = categoryService.findAllCategory();
            System.out.println("allCategory init");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        servletContext.setAttribute("allCategory",allCategory);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
