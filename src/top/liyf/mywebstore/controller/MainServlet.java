package top.liyf.mywebstore.controller;

import org.apache.log4j.Logger;
import top.liyf.mywebstore.entity.Product;
import top.liyf.mywebstore.service.ProductService;
import top.liyf.mywebstore.service.impl.ProductServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "MainServlet", value = "/MainServlet")
public class MainServlet extends HttpServlet {

    Logger logger = Logger.getLogger(MainServlet.class);

    private ProductService productService = new ProductServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String op = request.getParameter("op");
        if (op != null) {
            switch (op) {
                case "initIndex":
                    initIndex(request, response);
                    break;

                default:
                    break;
            }
        } else {
            initIndex(request, response);
        }
    }

    private void initIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Product> topProductList = productService.findTopProduct();
            request.setAttribute("topProductList", topProductList);
            List<Product> hotProductList = productService.findHotProduct();
            request.setAttribute("hotProductList", hotProductList);
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.info(e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
