package top.liyf.mywebstore.controller;

import org.apache.commons.beanutils.BeanUtils;
import top.liyf.mywebstore.domain.Product;
import top.liyf.mywebstore.domain.ShoppingCart;
import top.liyf.mywebstore.domain.ShoppingCartItem;
import top.liyf.mywebstore.domain.User;
import top.liyf.mywebstore.service.ProductService;
import top.liyf.mywebstore.service.ShoppingService;
import top.liyf.mywebstore.service.impl.ProductServiceImpl;
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


@WebServlet(name = "CartServlet", value = "/user/CartServlet")
public class CartServlet extends HttpServlet {

    private ShoppingService service = new ShoppingServiceImpl();
    private ProductService productService = new ProductServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String op = request.getParameter("op");
        if (op != null) {
            switch (op) {
                case "addCart":
                    addCart(request, response);
                    break;

                case "findCart":
                    findCart(request, response);
                    break;

                case "delItem":
                    delItem(request, response);
                    break;

                case "test":
                    test(request, response);
                    break;
                default:
                    break;
            }
        }

    }

    private void test(HttpServletRequest request, HttpServletResponse response) {
        String aaa = request.getParameter("aaa");
        System.out.println("aaa = " + aaa);
    }

    private void delItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String itemid = request.getParameter("itemid");
        HttpSession session = request.getSession();
        ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
        User user = (User) session.getAttribute("user");
        try {
            //只用itemid删除的话就会导致可以随意删除他人的item
            Boolean deleteItem = service.deleteItem(shoppingCart.getSid(), itemid);
            if (deleteItem) {
                // 重新获取购物车
                shoppingCart = service.getCartByUid(user.getUid());
                session.setAttribute("shoppingCart", shoppingCart);
            }
            request.getRequestDispatcher("/user/shoppingcart.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void findCart(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
        if (!Utils.notNUll(shoppingCart)) {
            try {
                User user = (User) session.getAttribute("user");
                shoppingCart = service.getCartByUid(user.getUid());
                session.setAttribute("shoppingCart", shoppingCart);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        request.getRequestDispatcher("/user/shoppingcart.jsp").forward(request, response);

    }

    private void addCart(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String referer = request.getHeader("Referer");
        String uid = request.getParameter("uid");
        try {
            ShoppingCart shoppingCart = service.getCartByUid(Integer.parseInt(uid));
            ShoppingCartItem item = new ShoppingCartItem();
            BeanUtils.populate(item, request.getParameterMap());

            //添加购物车数量检测
            Product productByPid = productService.findProductByPid(item.getPid());
            if (item.getSnum() > productByPid.getPnum()) {
                response.getWriter().println("库存不足");
                response.setHeader("refresh", "2;url=" + referer);
                return;
            }
            item.setSid(shoppingCart.getSid());
            boolean addCartItem = service.addCartItem(item);
            if (addCartItem) {
                shoppingCart.setShoppingItems(service.getCartItemList(shoppingCart.getSid()));
                HttpSession session = request.getSession();
                session.setAttribute("shoppingCart", shoppingCart);
                response.setHeader("refresh", "0;url=" + request.getContextPath() + "/user/shoppingcart.jsp");
            } else {
                response.getWriter().println("添加失败");
                response.setHeader("refresh", "2;url=" + referer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
