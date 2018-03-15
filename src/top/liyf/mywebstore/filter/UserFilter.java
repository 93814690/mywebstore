package top.liyf.mywebstore.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;


@WebFilter(filterName = "UserFilter" , value = "/user/*")
public class UserFilter implements Filter {


    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession session = request.getSession(false);
        String requestURI = request.getRequestURI();
//        System.out.println("requestURI = " + requestURI);
        ArrayList<String> forbidden = new ArrayList<>();
        forbidden.add("/mywebstore/user/personal.jsp");
        forbidden.add("/mywebstore/user/shoppingcart.jsp");
        forbidden.add("/mywebstore/user/myOrders.jsp");
        forbidden.add("/mywebstore/user/placeOrder.jsp");
        forbidden.add("/mywebstore/user/CartServlet");
        forbidden.add("/mywebstore/user/OrderServlet");

        if (redLight(request, session, requestURI, forbidden)) {
            System.out.println("UserFilter---forbidden---" + requestURI);
            request.getRequestDispatcher("/user/login.jsp").forward(req, resp);
        } else {
            chain.doFilter(req, resp);
        }
    }

    private boolean redLight(HttpServletRequest request, HttpSession session, String requestURI, ArrayList<String> forbidden) {
        return (session == null || session.getAttribute("user") == null) && (forbidden.contains(requestURI) || "update".equals(request.getParameter("op")));
    }

    @Override
    public void init(FilterConfig config) throws ServletException {

    }

}
