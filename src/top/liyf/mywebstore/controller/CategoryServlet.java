package top.liyf.mywebstore.controller;

import org.apache.commons.beanutils.BeanUtils;
import top.liyf.mywebstore.entity.Category;
import top.liyf.mywebstore.service.CategoryService;
import top.liyf.mywebstore.service.impl.CategoryServiceImpl;
import top.liyf.mywebstore.util.Page;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "CategoryServlet", value = "/admin/CategoryServlet")
public class CategoryServlet extends HttpServlet {

    private CategoryService categoryService = new CategoryServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String op = request.getParameter("op");
        if (op != null) {
            switch (op) {
                case "addCategory":
                    addCategory(request, response);
                    break;

                case "findAllCategory":
                    findAllCategory(request, response);
                    break;

                case "deleteCategory":
                    deleteCategory(request, response);
                    break;

                case "deleteMulti":
                    deleteMulti(request, response);
                    break;


                case "toUpdateCategory":
                    toUpdateCategory(request, response);
                    break;

                case "updateCategory":
                    updateCategory(request, response);
                    break;

                case "checkCname":
                    checkCname(request, response);
                    break;


                default:
                    break;
            }
        }
    }

    private void checkCname(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String cname = request.getParameter("cname");
        System.out.println("CategoryServlet.checkCname --- cname=" + cname);
        try {
            Category category = categoryService.getCategoryByCname(cname);
            if (category != null) {
                response.getWriter().print("1");
            } else {
                response.getWriter().print("0");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteMulti(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] cidList = request.getParameterValues("cid");
//        System.out.println(cidList[0]);
        if (cidList == null) {
            response.getWriter().println("选中为空，请重新选择!");
            response.setHeader("refresh", "2;url=" + request.getContextPath() + "/admin/CategoryServlet?op=findAllCategory&num=1");
        } else {
            try {
                boolean deleteMulti = categoryService.deleteMulti(cidList);
                if (deleteMulti) {
                    updateAllCategory();
                    response.getWriter().println("删除成功！即将返回");
                }else {
                    response.getWriter().println("删除失败，请先删除该品牌下的商品，再重试！即将返回");
                }
                response.setHeader("refresh", "2;url=" + request.getContextPath() + "/admin/CategoryServlet?op=findAllCategory&num=1");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Category category = new Category();

        try {
            BeanUtils.populate(category, request.getParameterMap());
            Boolean updateCategory = categoryService.updateCategory(category);
            if (updateCategory) {
                updateAllCategory();
                response.getWriter().println("更新成功！即将返回");
            } else {
                response.getWriter().println("更新失败！即将返回");
            }
            response.setHeader("refresh", "2;url=" + request.getContextPath() + "/admin/CategoryServlet?op=findAllCategory&num=1");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    private void toUpdateCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cid = request.getParameter("cid");

        try {
            Category category = categoryService.getCategoryByCid(Integer.parseInt(cid));
            request.setAttribute("category", category);
            request.getRequestDispatcher("/admin/category/updateCategory.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String cid = request.getParameter("cid");

        try {
            Boolean deleteCategory = categoryService.deleteCategory(Integer.parseInt(cid));
            if (deleteCategory) {
                updateAllCategory();
                response.getWriter().println("删除成功！即将返回");
            } else {
                response.getWriter().println("删除失败！即将返回");
            }
            response.setHeader("refresh", "2;url=" + request.getContextPath() + "/admin/CategoryServlet?op=findAllCategory&num=1");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void findAllCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String num = request.getParameter("num");
        try {
            Page<Category> page = categoryService.getPageData(num);
            request.setAttribute("page", page);
            request.getRequestDispatcher("/admin/category/categoryList.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void addCategory(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        Category category = new Category();
        try {
            BeanUtils.populate(category, request.getParameterMap());

            Boolean addCategory = categoryService.addCategory(category);
            if (addCategory) {
                updateAllCategory();
                response.getWriter().println("类别添加成功！即将返回");
            } else {
                response.getWriter().println("类别添加失败！即将返回");
            }
            response.setHeader("refresh", "2;url=" + request.getContextPath() + "/admin/CategoryServlet?op=findAllCategory&num=1");

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void updateAllCategory() throws SQLException {
        List<Category> allCategory = categoryService.findAllCategory();
        getServletContext().setAttribute("allCategory", allCategory);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);
    }
}
