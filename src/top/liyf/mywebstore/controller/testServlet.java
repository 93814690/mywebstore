package top.liyf.mywebstore.controller;

import org.apache.log4j.Logger;
import top.liyf.mywebstore.test.Test;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "testServlet", value = "/testServlet")
public class testServlet extends HttpServlet {

    Logger logger = Logger.getLogger(testServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.getWriter().println("---");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().println("---");

        try {
            int i = 1 / 0;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getMessage());
        }

    }
}
