package top.liyf.mywebstore.controller;

import org.apache.commons.beanutils.BeanUtils;
import top.liyf.mywebstore.domain.Message;
import top.liyf.mywebstore.domain.Order;
import top.liyf.mywebstore.domain.ShoppingCart;
import top.liyf.mywebstore.domain.User;
import top.liyf.mywebstore.service.OrderService;
import top.liyf.mywebstore.service.ShoppingService;
import top.liyf.mywebstore.service.impl.OrderServiceImpl;
import top.liyf.mywebstore.service.impl.ShoppingServiceImpl;
import top.liyf.mywebstore.util.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "OrderServlet", value = "/user/OrderServlet")
public class OrderServlet extends HttpServlet {

    private OrderService orderService = new OrderServiceImpl();
    private ShoppingService shoppingService = new ShoppingServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String op = request.getParameter("op");
        if (Utils.notNUll(op)) {
            switch (op) {
                case "placeOrder":
                    placeOrder(request, response);
                    break;

                case "myoid":
                    myoid(request, response);
                    break;

                case "cancelOrder":
                    cancelOrder(request, response);
                    break;

                case "deleteOrder":
                    deleteOrder(request, response);
                    break;

                default:
                    break;
            }
        }
    }

    private void deleteOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String oid = request.getParameter("oid");
        try {
            Boolean updateOrderState = orderService.deleteOrder(oid);
            if (updateOrderState) {
                response.setHeader("refresh", "0;url="+request.getContextPath()+"/user/OrderServlet?op=myoid");

            } else {
                response.getWriter().println("操作失败，请稍后再试！");
                response.setHeader("refresh", "2;url="+request.getContextPath()+"/user/OrderServlet?op=myoid");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cancelOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String oid = request.getParameter("oid");
        try {
            Boolean updateOrderState = orderService.cancelOrder(oid);
            if (updateOrderState) {
                response.setHeader("refresh", "0;url="+request.getContextPath()+"/user/OrderServlet?op=myoid");

            } else {
                response.getWriter().println("操作失败，请稍后再试！");
                response.setHeader("refresh", "2;url="+request.getContextPath()+"/user/OrderServlet?op=myoid");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void myoid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        try {
            List<Order> orders = orderService.getOrderList(user.getUid());
            request.setAttribute("orders", orders);
            request.getRequestDispatcher("/user/myOrders.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void placeOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Order order = new Order();
        try {
            BeanUtils.populate(order, request.getParameterMap());
            String[] pids = request.getParameterValues("pids");
//            System.out.println("pids[0] = " + pids[0]);

            order.setUid(user.getUid());
            Message createOrder = orderService.createOrder(order, pids);
            if (!Utils.notNUll(createOrder.getMesage())) {
                ShoppingCart shoppingCart = shoppingService.getCartByUid(user.getUid());
                session.setAttribute("shoppingCart", shoppingCart);
                response.getWriter().println("订单创建成功，请尽快支付！");
                //todo 跳转支付页面
                response.setHeader("refresh", "2;url="+request.getContextPath() + "/user/OrderServlet?op=myoid");
            } else {
                response.getWriter().println(createOrder.getMesage());
                response.setHeader("refresh", "2;url="+request.getContextPath() + "/user/placeOrder.jsp");
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
