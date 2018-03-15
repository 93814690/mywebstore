package top.liyf.mywebstore.controller;

import top.liyf.mywebstore.domain.Admin;
import top.liyf.mywebstore.service.AdminService;
import top.liyf.mywebstore.service.impl.AdminServiceImpl;
import top.liyf.mywebstore.util.Page;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "AdminServlet", value = "/admin/AdminServlet")
public class AdminServlet extends HttpServlet {

    private AdminService adminService = new AdminServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String op = request.getParameter("op");
        if (op != null) {
            switch (op) {
                case "addAdmin":
                    addAdmin(request, response);
                    break;

                case "findAllAdmin":
                    findAllAdmin(request, response);
                    break;

                case "updateAdmin":
                    updateAdmin(request, response);
                    break;

                case "login":
                    login(request, response);
                    break;
                    
                case "logout":
                    logout(request, response);
                    break;
                    
                
                default:
                    break;
            }
        }
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.invalidate();
        response.setHeader("refresh", "0;url=" + request.getContextPath() + "/admin/index.jsp");
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if ("".equals(username) || "".equals(password)) {
            response.getWriter().println("不能为空，请重填！即将返回");
            response.setHeader("refresh", "2;url=" + request.getContextPath() + "/admin/index.jsp");
            return;
        }
        try {
            Admin admin = adminService.adminLogin(username, password);
            if (admin == null) {
                response.getWriter().println("用户名或密码错误！即将返回");
                response.setHeader("refresh", "2;url=" + request.getContextPath() + "/admin/index.jsp");
                return;
            }
            HttpSession session = request.getSession();
            session.setAttribute("admin", admin);
            response.setHeader("refresh", "0;url=" + request.getContextPath() + "/admin/main.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String repass = request.getParameter("repass");
        String wrongPath = "/admin/AdminServlet?op=findAllAdmin&num=1";
        if (baseCheck(request, response, username, password, repass, wrongPath)) {
            return;
        }
        try {
            Boolean updateAdmin = adminService.updateAdmin(username, password);
            if (updateAdmin) {
                response.getWriter().println("更新成功！");
                response.setHeader("refresh", "2;url=" + request.getContextPath() + "/admin/AdminServlet?op=findAllAdmin&num=1");
            } else {
                response.getWriter().println("更新失败！");
                response.setHeader("refresh", "2;url=" + request.getContextPath() + "/admin/AdminServlet?op=findAllAdmin&num=1");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("您正在访问的页面出现了一些问题，工程师正在抢修中！");
        }
    }

    private void findAllAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageNum = request.getParameter("num");
        try {
            Page<Admin> page = adminService.getPageData(pageNum);
            request.setAttribute("page", page);
            request.getRequestDispatcher("/admin/admin/adminList.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("您正在访问的页面出现了一些问题，工程师正在抢修中！");
        }
    }

    private void addAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String repass = request.getParameter("repass");
        String wrongPath = "/admin/admin/addAdmin.jsp";
        if (baseCheck(request, response, username, password, repass, wrongPath)) {
            return;
        }
        try {
            if (checkAdminUsernameExist(request, response, username)) {
                response.setHeader("refresh", "2;url=" + request.getContextPath() + "/admin/admin/addAdmin.jsp");
                return;
            }
            Boolean addAdmin = adminService.addAdmin(username, password);
            if (addAdmin) {
                response.getWriter().println("添加成功！");
                response.setHeader("refresh", "2;url=" + request.getContextPath() + "/admin/AdminServlet?op=findAllAdmin&num=1");
            } else {
                response.getWriter().println("添加失败！");
                response.setHeader("refresh", "2;url=" + request.getContextPath() + "/admin/admin/addAdmin.jsp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("您正在访问的页面出现了一些问题，工程师正在抢修中！");
        }
    }

    private boolean checkAdminUsernameExist(HttpServletRequest request, HttpServletResponse response, String username) throws SQLException, IOException {
        if (adminService.adminUsernameExist(username)) {
            response.getWriter().println("帐号名已存在！");
            return true;
        }
        return false;
    }

    private boolean baseCheck(HttpServletRequest request, HttpServletResponse response, String username, String password, String repass, String wrongPath) throws IOException {
        if ("".equals(username) || "".equals(password) || "".equals(repass)) {
            response.getWriter().println("不能为空，请重填！即将返回");
            response.setHeader("refresh", "2;url=" + request.getContextPath() + wrongPath);
            return true;
        } else if (!password.equals(repass)) {
            response.getWriter().println("两次密码不一致，请重填！即将返回");
            response.setHeader("refresh", "2;url=" + request.getContextPath() + wrongPath);
            return true;
        }
        return false;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
