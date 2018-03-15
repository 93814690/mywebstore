package top.liyf.mywebstore.controller;

import top.liyf.mywebstore.entity.Product;
import top.liyf.mywebstore.service.ProductService;
import top.liyf.mywebstore.service.impl.ProductServiceImpl;
import top.liyf.mywebstore.util.Page;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ProductServlet2", value = "/ProductServlet")
public class ProductServlet2 extends HttpServlet {

    private ProductService productService = new ProductServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String op = request.getParameter("op");
        if (op != null) {
            switch (op) {
                case "byCid":
                    request.setAttribute("op",op);
                    findProductByCid(request, response);
                    break;

                case "findProductByPid":
                    findProductByPid(request, response);
                    break;

                case "findProByKeyword":
                    request.setAttribute("op",op);
                    findProByKeyword(request, response);
                    break;

                default:
                    break;
            }
        }
    }

    private void findProByKeyword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        String pageNum = request.getParameter("num");
        request.setAttribute("keyword",keyword);
//        System.out.println("keyword = " + keyword);
        try {
            Page<Product> page =  productService.searchProductPageByKeyword(keyword, pageNum);
            request.setAttribute("page",page);
            request.getRequestDispatcher("/products.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void findProductByPid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pid = request.getParameter("pid");
        try {
            Product product = productService.findProductByPid(pid);
            request.setAttribute("product",product);
            request.getRequestDispatcher("/productdetail.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void findProductByCid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cid = request.getParameter("cid");
        String pageNum = request.getParameter("num");
        request.setAttribute("cid",cid);
        try {
            Page<Product> page = productService.findProductPageByCid(cid, pageNum);
            request.setAttribute("page",page);
            request.getRequestDispatcher("/products.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
