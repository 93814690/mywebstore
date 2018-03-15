package top.liyf.mywebstore.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(filterName = "GlobalEncoding", value = "/*")
public class GlobalEncoding implements Filter {
    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        //System.out.println("-----------");
        chain.doFilter(req,resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
