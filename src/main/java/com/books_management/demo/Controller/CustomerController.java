package com.books_management.demo.Controller;

import com.books_management.demo.Service.CustomerService;
import com.books_management.demo.Entity.Register;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CustomerController{

    @Autowired
    private CustomerService customerService;

    @GetMapping("/admin/admin-customers")
    public String viewCustomers(Model model) {
        // Fetch the list of customers from the service
        List<Register> customers = customerService.getAllCustomers();

        // Add the customers list to the model so it can be used in the view
        model.addAttribute("customers", customers);

        return "admin/customers"; // Return the customers.html view
    }
}
