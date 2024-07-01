package com.example.mvcmanagementproduct.controllers;

import com.example.mvcmanagementproduct.models.Product;
import com.example.mvcmanagementproduct.services.IProductService;
import com.example.mvcmanagementproduct.services.impl.ProductService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProductController", value = "/list")
public class ProductController extends HttpServlet {
    IProductService productService = ProductService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create":
                showCreateForm(req, resp);
                break;
            case "edit":
                showEditForm(req, resp);
                break;
//            case "delete":
//                showDeleteForm(req, resp);
//                break;
            default:
                listProducts(req, resp);
                break;
        }
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Product product = productService.findById(id);
        RequestDispatcher requestDispatcher;
        if (product == null) {
            requestDispatcher = req.getRequestDispatcher("error-404.jsp");
        }else {
            req.setAttribute("product", product);
            requestDispatcher = req.getRequestDispatcher("edit.jsp");
        }
            requestDispatcher.forward(req, resp);
    }

//    private void showDeleteForm(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException  {
//        int id = Integer.parseInt(req.getParameter("id"));
//        Product product = productService.findById(id);
//        RequestDispatcher requestDispatcher;
//        if (product == null) {
//            requestDispatcher = req.getRequestDispatcher("error-404.jsp");
//        } else {
//            req.setAttribute("product", product);
//            requestDispatcher = req.getRequestDispatcher("delete.jsp");
//            requestDispatcher.forward(req, resp);
//        }
//    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Lấy Parameters(dữ liệu request gửi đi) có name là "action"
        String action = req.getParameter("action");
        // Nếu dữ liệu request gửi đi ko có "action" thì trả về null
        if (action == null) {
            action = "";
            deleteProduct(req, resp);
        }
        switch (action) {
            case "create":
                createProduct(req, resp);
                break;
            case "edit":
                editProduct(req, resp);
            case "list":
                deleteProduct(req, resp);
                break;
            case "search":
                searchProductByName(req, resp);
                break;
            default:
                break;
        }
    }

    private void editProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        double price = Double.parseDouble(req.getParameter("price"));
        String description = req.getParameter("description");
        String producer = req.getParameter("producer");

        Product product = productService.findById(id);
        RequestDispatcher requestDispatcher;
        if (product == null) {
            requestDispatcher = req.getRequestDispatcher("error-404.jsp");
        } else {
            product.setName(name);
            product.setPrice(price);
            product.setDescription(description);
            product.setProducer(producer);
            productService.update(id, product);
            req.setAttribute("product", product);
            req.setAttribute("message", "Thông tin khách hàng đã được cập nhật");
            requestDispatcher = req.getRequestDispatcher("edit.jsp");
        }
            requestDispatcher.forward(req, resp);
    }

    private void deleteProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Product product = productService.findById(id);
        if (product == null) {
            req.getRequestDispatcher("error-404.jsp").forward(req, resp);
        } else {
            productService.remove(id);
            resp.sendRedirect(req.getHeader("Referer"));
        }
    }


    private void createProduct(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        double price = Double.parseDouble(req.getParameter("price"));
        String description = req.getParameter("description");
        String producer = req.getParameter("producer");
        Product product = new Product(id, name, price, description, producer);
        productService.addProduct(product);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("create.jsp");
        req.setAttribute("message", "Thêm mới thành công");
        try {
            requestDispatcher.forward(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void listProducts(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> products = productService.findAll();
        req.setAttribute("lists", products);
        req.getRequestDispatcher("list.jsp").forward(req, resp);
    }

    private void showCreateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("create.jsp");
        try {
            requestDispatcher.forward(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void searchProductByName(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("search");
        List<Product> products = productService.findByName(name);
        req.setAttribute("lists", products);
        req.getRequestDispatcher("list.jsp").forward(req, resp);
    }
}
