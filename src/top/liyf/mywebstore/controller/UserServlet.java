package top.liyf.mywebstore.controller;

import org.apache.commons.beanutils.BeanUtils;
import top.liyf.mywebstore.entity.User;
import top.liyf.mywebstore.service.UserService;
import top.liyf.mywebstore.service.impl.UserServiceImpl;
import top.liyf.mywebstore.util.Page;
import top.liyf.mywebstore.util.SendJMail;
import top.liyf.mywebstore.util.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "UserServlet", value = "/UserServlet")
public class UserServlet extends HttpServlet {

    private UserService userService = new UserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String op = request.getParameter("op");
        if (op != null) {
            switch (op) {
                case "register":
                    register(request, response);
                    break;

                case "checkUsername":
                    checkUsername(request, response);
                    break;

                case "login":
                    login(request, response);
                    break;

                case "logout":
                    logout(request, response);
                    break;

                case "findAllUser":
                    findAllUser(request, response);
                    break;

                case "active":
                    active(request, response);
                    break;

                case "update":
                    update(request, response);
                    break;


                default:
                    break;
            }
        }
    }

    private void update(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User updateUser = new User();
        User msg = new User();
        msg.setError(new User());
        try {
            BeanUtils.populate(updateUser, request.getParameterMap());
            boolean hasError = updateCheck(updateUser, msg);
            if (hasError) {
                request.setAttribute("msg", msg);
                request.getRequestDispatcher("/user/personal.jsp").forward(request, response);
                return;
            }
            String uid = request.getParameter("uid");
            User user = userService.getUserByUid(uid);
            if (!user.getPassword().equals(updateUser.getPassword())) {
                String newPassword = Utils.newPassword(updateUser.getPassword(), user.getUpdatetime());
                updateUser.setPassword(newPassword);

            }
            boolean update = userService.updateUser(updateUser);
            if (!update) {
                response.getWriter().println("修改失败，请按要求重试");
                response.setHeader("refresh", "2;url=" + request.getContextPath() + "/user/personal.jsp");
                return;
            }
            User userByUid = userService.getUserByUid(uid);
            HttpSession session = request.getSession();
            session.setAttribute("user", userByUid);
            response.getWriter().println("修改成功！");
            response.setHeader("refresh", "2;url=" + request.getContextPath() + "/index.jsp");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void active(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String code = request.getParameter("code");
        try {
            Boolean activeUser = userService.activeUser(code);
            if (activeUser) {
                response.setHeader("refresh", "0;url=" + request.getContextPath() + "/user/login.jsp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void findAllUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException  {
        String pageNum = request.getParameter("num");
        try {
            Page<User> page = userService.findAllUser(pageNum);
            request.setAttribute("page", page);
            request.getRequestDispatcher("/admin/user/userList.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.invalidate();
        response.setHeader("refresh", "0;url=" + request.getContextPath() + "/index.jsp");
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("remember_me");
//        System.out.println("rememberMe = " + rememberMe);
        if (!Utils.notNUll(username, password)) {
            response.getWriter().print("用户名或密码不能为空！");
            response.setHeader("refresh", "2;url=" + request.getContextPath() + "/user/login.jsp");
            return;
        }
        try {
            String time = userService.getUserUpdateTime(username);
            password = Utils.newPassword(password, time);
            System.out.println("password = " + password);
            User user = userService.login(username, password);
            if (!Utils.notNUll(user)) {
                response.getWriter().print("用户名或密码错误！");
                //todo 转到新页面，需要输入验证码
                response.setHeader("refresh", "2;url=" + request.getContextPath() + "/user/login.jsp");
                return;
            }
            //0表示还未激活
            if ("0".equals(user.getState())) {
                response.getWriter().print("该用户还未激活！");
                response.setHeader("refresh", "2;url=" + request.getContextPath() + "/user/login.jsp");
                return;
            }
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            String sessionId = session.getId();
//            System.out.println("sessionId = " + sessionId);
            Cookie jsessionid = new Cookie("JSESSIONID", sessionId);
            jsessionid.setMaxAge(1800);
            response.addCookie(jsessionid);
            if (Utils.notNUll(rememberMe)) {
                Cookie nameCookie = new Cookie("username", username);
                nameCookie.setMaxAge(3600 * 24 * 7);
                response.addCookie(nameCookie);
                Cookie pswCookie = new Cookie("password", password);
                pswCookie.setMaxAge(3600 * 24 * 7);
                response.addCookie(pswCookie);
            }
            String referer = request.getParameter("referer");
            System.out.println("referer = " + referer);
            //直接输入登录页面或从注册页面而来
            if ("null".equals(referer) || "http://localhost/mywebstore/UserServlet".equals(referer)) {
                response.setHeader("refresh", "0;url=" + request.getContextPath() + "/index.jsp");
                return;
            }
            response.setHeader("refresh", "0;url=" + referer);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void checkUsername(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        try {
            if (userService.usernameExist(username)) {
                response.getWriter().print("1");
            } else {
                response.getWriter().print("0");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void register(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            User user = new User();
            User msg = new User();
            msg.setError(new User());
            HttpSession session = request.getSession();
            String kaptchaExpected = (String)request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);

            BeanUtils.populate(user, request.getParameterMap());

            //获取是前台还是后端发来的请求
            //后台为: http://localhost/mywebstore/admin/left.jsp
            String referer = request.getParameter("referer");
            String adminRequest = "http://localhost/mywebstore/admin/left.jsp";
            System.out.println("referer = " + referer);
            boolean hasError = userCheck(user, msg);
            String kaptcha = request.getParameter("kaptcha");
            if (!kaptchaExpected.equals(kaptcha)) {
                msg.getError().setCheckcode("验证码不正确");
                System.out.println("kaptcha = " + kaptcha);
                hasError = true;
            }
            if (hasError) {
                request.setAttribute("msg", msg);
                request.getRequestDispatcher("/user/register.jsp").forward(request, response);
                return;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = sdf.format(new Date());
            user.setUpdatetime(date);
            user.setPassword(Utils.newPassword(user.getPassword(), user.getUpdatetime()));
            Boolean addUser = userService.addUser(user);
            if (addUser) {
                if (adminRequest.equals(referer)) {
                    response.setHeader("refresh", "0;url=" + request.getContextPath() + "/UserServlet?op=findAllUser&num=1");
                    return;
                }
                String activeMessage = "<a href=\"http://localhost"+request.getContextPath()+"/UserServlet?op=active&code="+user.getActivecode()+"\">点我激活</a>";
                SendJMail.sendMail(user.getEmail(), activeMessage);
                response.getWriter().println("注册成功！请激活后登录");
                response.setHeader("refresh", "2;url=" + request.getContextPath() + "/user/login.jsp");
            } else {
                response.getWriter().println("注册失败！");
                response.setHeader("refresh", "2;url=" + request.getContextPath() + "/user/register.jsp");
            }


        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean userCheck(User user, User msg) throws SQLException {
        boolean hasError = false;
        String[] params = {user.getUsername(), user.getNickname(), user.getPassword(), user.getEmail()};
        if ("".equals(user.getUsername().trim())) {
            msg.getError().setUsername("用户名不能为空");
            hasError = true;
        } else if (userService.usernameExist(user.getUsername())) {
            msg.getError().setUsername("用户名已存在");
            hasError = true;
        } else {
            msg.setUsername(user.getUsername());
        }

        if ("".equals(user.getNickname().trim())) {
            msg.getError().setNickname("昵称不能为空");
            hasError = true;
        } else {
            msg.setNickname(user.getNickname());
        }

        if ("".equals(user.getPassword().trim())) {
            msg.getError().setPassword("密码不能为空");
            hasError = true;
        } else {
            msg.setPassword(user.getPassword());
        }

        if ("".equals(user.getEmail().trim())) {
            msg.getError().setEmail("email不能为空");
            hasError = true;
        } else if (!user.getEmail().matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$")) {
            msg.getError().setEmail("email格式不正确");
            hasError = true;
        } else {
            msg.setEmail(user.getEmail());
        }

        //加入闰年的判断
        String regex = "^((((19|20)\\d{2})-(0?(1|[3-9])|1[012])-(0?[1-9]|[12]\\d|30))|" +
                "(((19|20)\\d{2})-(0?[13578]|1[02])-31)|(((19|20)\\d{2})-0?2-(0?[1-9]|" +
                "1\\d|2[0-8]))|((((19|20)([13579][26]|[2468][048]|0[48]))|(2000))-0?2-29))$";
        if (Utils.notNUll(user.getBirthday()) && !user.getBirthday().matches(regex)) {
            msg.getError().setBirthday("日期格式不正确");
            hasError = true;
        }
        return hasError;
    }

    private boolean updateCheck(User user, User msg) throws SQLException {
        boolean hasError = false;

        if ("".equals(user.getNickname().trim())) {
            msg.getError().setNickname("昵称不能为空");
            hasError = true;
        } else {
            msg.setNickname(user.getNickname());
        }

        if ("".equals(user.getPassword().trim())) {
            msg.getError().setPassword("密码不能为空");
            hasError = true;
        } else {
            msg.setPassword(user.getPassword());
        }

        if ("".equals(user.getEmail().trim())) {
            msg.getError().setEmail("email不能为空");
            hasError = true;
        } else if (!user.getEmail().matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$")) {
            msg.getError().setEmail("email格式不正确");
            hasError = true;
        } else {
            msg.setEmail(user.getEmail());
        }

        //加入闰年的判断
        String regex = "^((((19|20)\\d{2})-(0?(1|[3-9])|1[012])-(0?[1-9]|[12]\\d|30))|" +
                "(((19|20)\\d{2})-(0?[13578]|1[02])-31)|(((19|20)\\d{2})-0?2-(0?[1-9]|" +
                "1\\d|2[0-8]))|((((19|20)([13579][26]|[2468][048]|0[48]))|(2000))-0?2-29))$";
        if (Utils.notNUll(user.getBirthday()) && !user.getBirthday().matches(regex)) {
            msg.getError().setBirthday("日期格式不正确");
            hasError = true;
        }
        return hasError;
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }


}
