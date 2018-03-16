package top.liyf.mywebstore.controller;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import top.liyf.mywebstore.entity.Product;
import top.liyf.mywebstore.service.ProductService;
import top.liyf.mywebstore.service.impl.ProductServiceImpl;
import top.liyf.mywebstore.util.Utils;
import top.liyf.mywebstore.util.Page;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.*;

@WebServlet(name = "ProductServlet", value = "/admin/ProductServlet")
public class ProductServlet extends HttpServlet {

    private ProductService productService = new ProductServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String op = request.getParameter("op");
        if (op != null) {
            switch (op) {
                case "addProduct":
                    addProduct(request, response);
                    break;

                case "findAllProduct":
                    findAllProduct(request, response);
                    break;

                case "deleteOne":
                    deleteOne(request, response);
                    break;

                case "deleteMulti":
                    deleteMulti(request, response);
                    break;


                case "updateProduct":
                    updateProduct(request, response);
                    break;

                case "toUpdateProduct":
                    toUpdateProduct(request, response);
                    break;

                case "multiConditionSearch":
                    multiConditionSearch(request, response);
                    break;

                case "multiConditionSearch2":
                    multiConditionSearch2(request, response);
                    break;

                case "checkPid":
                    checkPid(request, response);
                    break;

                default:
                    break;
            }
        }
    }

    private void checkPid(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pid = request.getParameter("pid");
        try {
            Product productByPid = productService.findProductByPid(pid);
//            System.out.println("productByPid = " + productByPid);
            if (productByPid != null) {
                response.getWriter().print("1");
            } else {
                response.getWriter().print("0");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void multiConditionSearch2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageNum = request.getParameter("num");
        HttpSession session = request.getSession();
        String pid = (String) session.getAttribute("pid");
        String cid = (String) session.getAttribute("cid");
        String pname = (String) session.getAttribute("pname");
        String minPrice = (String) session.getAttribute("minPrice");
        String maxPrice = (String) session.getAttribute("maxPrice");
        try {
            Page<Product> page = productService.getSearchPageData(pid, cid, pname, minPrice, maxPrice, pageNum);
            request.setAttribute("page", page);
            request.getRequestDispatcher("/admin/product/searchproductList.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void multiConditionSearch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pid = request.getParameter("pid");
        String cid = request.getParameter("cid");
        String pname = request.getParameter("pname");
        String minPrice = request.getParameter("minPrice");
        String maxPrice = request.getParameter("maxPrice");
        String pageNum = request.getParameter("num");

        try {
            Page<Product> page = productService.getSearchPageData(pid, cid, pname, minPrice, maxPrice, pageNum);
            request.setAttribute("page", page);
            HttpSession session = request.getSession();
            session.setAttribute("pid", pid);
            session.setAttribute("cid", cid);
            session.setAttribute("pname", pname);
            session.setAttribute("minPrice", minPrice);
            session.setAttribute("maxPrice", maxPrice);
            request.getRequestDispatcher("/admin/product/searchproductList.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void deleteMulti(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] pidList = request.getParameterValues("pid");
//        System.out.println(pidList[0]);
        if (pidList == null) {
            response.getWriter().println("选中为空，请重新选择!");
            response.setHeader("refresh", "2;url=" + request.getContextPath() + "/admin/ProductServlet?op=findAllProduct&num=1");
        } else {
            try {
                boolean deleteMulti = productService.deleteMulti(pidList);
                if (deleteMulti) {
                    response.getWriter().println("删除成功！即将返回");
                } else {
                    response.getWriter().println("删除失败！即将返回");
                }
                response.setHeader("refresh", "2;url=" + request.getContextPath() + "/admin/ProductServlet?op=findAllProduct&num=1");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void toUpdateProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pid = request.getParameter("pid");
        Product product = null;
        try {
            product = productService.findProductByPid(pid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.setAttribute("product", product);
        request.getRequestDispatcher("/admin/product/updateProduct.jsp").forward(request, response);
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String oldImgurl = request.getParameter("imgurl");
        String pid = request.getParameter("pid");

        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletContext servletContext = this.getServletConfig().getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("utf-8");
        Map<String, String[]> parameterMap = new HashMap<>();
        List<FileItem> itemList = null;
        try {
            itemList = upload.parseRequest(request);
            Iterator<FileItem> iterator = itemList.iterator();
            while (iterator.hasNext()) {
                FileItem item = iterator.next();
                if (item.isFormField()) {
                    String name = item.getFieldName();
                    String value = item.getString("utf-8");
                    parameterMap.put(name, new String[]{value});
                } else {
                    String picName = processUploadedFile(item, oldImgurl);
                    parameterMap.put("imgurl", new String[]{picName});
                }
            }

            Product product = new Product();
            BeanUtils.populate(product, parameterMap);
            product.setPid(pid);
            Boolean updateProduct = productService.updateProduct(product);
            if (updateProduct) {
                response.getWriter().println("更新成功！即将返回");
            } else {
                response.getWriter().println("更新失败！即将返回");
            }
            response.setHeader("refresh", "2;url=" + request.getContextPath() + "/admin/ProductServlet?op=findAllProduct&num=1");
        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String pid = request.getParameter("pid");
        try {
            Product product = productService.findProductByPid(pid);
            Boolean deleteProduct = productService.deleteProduct(product);
            if (deleteProduct) {
                response.getWriter().println("删除成功！即将返回");
            } else {
                response.getWriter().println("删除失败！即将返回");
            }
            response.setHeader("refresh", "2;url=" + request.getContextPath() + "/admin/ProductServlet?op=findAllProduct&num=1");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void findAllProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String num = request.getParameter("num");
        Page<Product> page;
        try {
            page = productService.getPageData(num);
            request.setAttribute("page", page);
            request.getRequestDispatcher("/admin/product/productList.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private void addProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {

        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletContext servletContext = this.getServletConfig().getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("utf-8");
        Map<String, String[]> parameterMap = new HashMap<>();
        List<FileItem> itemList = null;
        try {
            itemList = upload.parseRequest(request);
            Iterator<FileItem> iterator = itemList.iterator();
            while (iterator.hasNext()) {
                FileItem item = iterator.next();
                if (item.isFormField()) {
                    String name = item.getFieldName();
                    String value = item.getString("utf-8");
                    parameterMap.put(name, new String[]{value});
                } else {
                    String picName = processUploadedFile(item, "");
                    parameterMap.put("imgurl", new String[]{picName});
                }
            }
            Product product = new Product();
            BeanUtils.populate(product, parameterMap);
            Boolean addProduct = productService.addProduct(product);
            if (addProduct) {
                response.getWriter().println("添加成功！即将返回");
            } else {
                response.getWriter().println("添加失败！即将返回");
            }
            response.setHeader("refresh", "2;url=" + request.getContextPath() + "/admin/ProductServlet?op=findAllProduct&num=1");
        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String processUploadedFile(FileItem item, String oldImgurl) {
        String name = item.getName();
        if ("".equals(name)) {
            return oldImgurl;
        } else {
            Utils.deleteImg(oldImgurl);
            String id = UUID.randomUUID().toString();
            String fileName = "pic-" + id + "-" + name;
            int code = fileName.hashCode();
            String hexString = Integer.toHexString(code);
            char[] charArray = hexString.toCharArray();
            String path = "productImg/";
            for (char c : charArray) {
                path = path + c + "/";
            }
            String newpath = getServletContext().getRealPath(path);
            File file = new File(newpath);
            if (!file.exists()) {
                file.mkdirs();
            }
            try {
                InputStream inputStream = item.getInputStream();
                FileOutputStream fos = new FileOutputStream(new File(file, fileName));
                byte[] bs = new byte[1024];
                int length = 0;
                while ((length = inputStream.read(bs, 0, 1024)) != -1) {
                    fos.write(bs, 0, length);
                }
                fos.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            item.delete();
            return path + fileName;
        }


    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
