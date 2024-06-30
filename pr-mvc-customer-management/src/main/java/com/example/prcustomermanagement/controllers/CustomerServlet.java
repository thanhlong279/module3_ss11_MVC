package com.example.prcustomermanagement.controllers;

import com.example.prcustomermanagement.models.Customer;
import com.example.prcustomermanagement.services.ICustomerService;
import com.example.prcustomermanagement.services.impl.CustomerService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
@WebServlet(name = "CustomerServlet", urlPatterns = "/customers")
public class CustomerServlet extends HttpServlet {
    private final ICustomerService customerService = new CustomerService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Lấy Parameters(dữ liệu request gửi đi) có name là "action"
        String action = req.getParameter("action");
        // Nếu dữ liệu request gửi đi ko có "action" thì trả về null
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create":
                createCustomer(req, resp);
                break;
            case "edit":
                updateCustomer(req, resp);
                break;
            case "delete":
                deleteCustomer(req, resp);
                break;
            default:
                break;
        }
    }

    // phương thức xóa Customer dựa theo id
    private void deleteCustomer(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        Customer customer = customerService.findById(id);
        RequestDispatcher requestDispatcher;
        if (customer == null) {
            requestDispatcher = req.getRequestDispatcher("error-404.jsp");
        } else {
            customerService.remove(id);
            try {
                resp.sendRedirect("/customers");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // phương thức sửa customer dựa theo id
    private void updateCustomer(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String address = req.getParameter("address");
        Customer customer = customerService.findById(id);
        RequestDispatcher requestDispatcher;
        if (customer == null) {
            requestDispatcher = req.getRequestDispatcher("error-404.jsp");
        } else {
            customer.setName(name);
            customer.setEmail(email);
            customer.setAddress(address);
            customerService.update(id, customer);
            req.setAttribute("customer", customer);
            req.setAttribute("message", "Thông tin khách hàng đã được cập nhật");
            requestDispatcher = req.getRequestDispatcher("customer/edit.jsp");
            ;
        }
        try {
            requestDispatcher.forward(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // phương thức thêm customer
    private void createCustomer(HttpServletRequest req, HttpServletResponse resp) {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String address = req.getParameter("address");
        int id = (int) (Math.random() * 100000);
        Customer customer = new Customer(id, name, email, address);
        customerService.save(customer);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("customer/create.jsp");
        req.setAttribute("message", "New customer was created");
        try {
            requestDispatcher.forward(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
            case "delete":
                showDeleteForm(req, resp);
                break;
            case "view":
                viewCustomer(req, resp);
                break;
            default:
                listCustomers(req, resp);
                break;
        }
    }
    // phương thức trả về trang view.jsp để hiển thị thông tin của customer
    private void viewCustomer(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        Customer customer = customerService.findById(id);
        RequestDispatcher requestDispatcher;
        if (customer == null) {
            requestDispatcher = req.getRequestDispatcher("error-404.jsp");
        }else{
            req.setAttribute("customer", customer);
            requestDispatcher = req.getRequestDispatcher("customer/view.jsp");
        }
        try {
            requestDispatcher.forward(req, resp);
        }catch (ServletException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    // phương thức trả về trang delete.jsp để hiển thị customer để xóa
    private void showDeleteForm(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        Customer customer = customerService.findById(id);
        RequestDispatcher requestDispatcher;
        if (customer == null) {
            requestDispatcher = req.getRequestDispatcher("error-404.jsp");
        }else {
            req.setAttribute("customer", customer);
            requestDispatcher = req.getRequestDispatcher("customer/delete.jsp");
        }
        try {
            requestDispatcher.forward(req, resp);
        }catch (ServletException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    // phương thức trả về trang edit.jsp để hiển thị customer để sửa
    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        Customer customer = customerService.findById(id);
        RequestDispatcher requestDispatcher;
        if (customer == null) {
            requestDispatcher = req.getRequestDispatcher("error-404.jsp");
        }else {
            req.setAttribute("customer", customer);
            requestDispatcher = req.getRequestDispatcher("customer/edit.jsp");
        }
        try {
            requestDispatcher.forward(req, resp);
        }catch (ServletException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //phương thức trả về trang create.jsp để tạo customer
    private void showCreateForm(HttpServletRequest req, HttpServletResponse resp) {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("customer/create.jsp");
        try {
            requestDispatcher.forward(req, resp);
        }catch (ServletException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //phương thức trả về trang list.jsp để hiển thị toàn bộ customer
    private void listCustomers(HttpServletRequest req, HttpServletResponse resp) {
        List<Customer> customers = customerService.findAll();
        req.setAttribute("customers", customers);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("customer/list.jsp");
        try {
            requestDispatcher.forward(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
