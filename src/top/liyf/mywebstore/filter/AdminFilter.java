package top.liyf.mywebstore.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;


@WebFilter(filterName = "AdminFilter" , value = "/admin/*")
public class AdminFilter implements Filter {


    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession session = request.getSession(false);
        String requestURI = request.getRequestURI();
//        System.out.println("requestURI = " + requestURI);
        ArrayList<String> except = new ArrayList<>();
        except.add("/mywebstore/admin/index.jsp");
        except.add("/mywebstore/admin/");
        if (greenLight(request, session, requestURI, except)) {
            //System.out.println("AdminFilter.doFilter---放行");
            chain.doFilter(req, resp);
        } else {
            request.getRequestDispatcher("/admin/index.jsp").forward(req, resp);
        }
    }

    private boolean greenLight(HttpServletRequest request, HttpSession session, String requestURI, ArrayList<String> except) {
        return except.contains(requestURI) || (session != null && session.getAttribute("admin") != null) || "login".equals(request.getParameter("op")) ||
                requestURI.endsWith(".js") || requestURI.endsWith(".css") || requestURI.endsWith(".gif");
    }

    @Override
    public void init(FilterConfig config) throws ServletException {

    }

}
