package com.books_management.demo.Controller;

import com.books_management.demo.Entity.Order;
import com.books_management.demo.Service.OrderService;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller
public class PaymentController {

    @Autowired
    private RazorpayClient razorpayClient;

    @Value("${razorpay.key_id}")
    private String razorpayKeyId;

    @Autowired
    private OrderService orderService;

    @PostMapping("/order/place")
    public String placeOrder(@RequestParam("amount") double amount, Model model) throws Exception {
        BigDecimal totalAmount = BigDecimal.valueOf(amount);

        // Save order info in DB
        Order savedOrder = orderService.saveOrder(totalAmount);

        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", totalAmount.multiply(BigDecimal.valueOf(100)).intValue()); // in paise
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "order_rcptid_" + savedOrder.getId());
        orderRequest.put("payment_capture", 1);

        // Use full class name here to avoid conflict
        com.razorpay.Order razorpayOrder = razorpayClient.Orders.create(orderRequest);

        model.addAttribute("razorpayOrderId", razorpayOrder.get("id"));
        model.addAttribute("amount", razorpayOrder.get("amount"));
        model.addAttribute("currency", razorpayOrder.get("currency"));
        model.addAttribute("key", razorpayKeyId);
        model.addAttribute("orderId", savedOrder.getId());

        return "payment";
    }

    @GetMapping("/orders/{orderId}/pay")
    public String showPaymentPage(@PathVariable Long orderId, Model model) throws Exception {
        Order order = orderService.findById(orderId);
        if (order == null) {
            model.addAttribute("error", "Order not found");
            return "error";
        }

        // Create Razorpay order
        JSONObject orderRequest = new JSONObject();
        int amountInPaise = order.getTotalAmount().multiply(BigDecimal.valueOf(100)).intValue();

        orderRequest.put("amount", amountInPaise);
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "order_rcptid_" + orderId);
        orderRequest.put("payment_capture", 1);

        com.razorpay.Order razorpayOrder = razorpayClient.Orders.create(orderRequest);

        model.addAttribute("razorpayOrderId", razorpayOrder.get("id"));
        model.addAttribute("amount", amountInPaise);
        model.addAttribute("currency", "INR");
        model.addAttribute("key", razorpayKeyId);
        model.addAttribute("orderId", orderId);

        return "payment";
    }
}
