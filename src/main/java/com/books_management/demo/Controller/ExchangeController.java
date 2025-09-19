package com.books_management.demo.Controller;

import com.books_management.demo.Entity.Exchange;
import com.books_management.demo.Service.ExchangeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/exchange")
public class ExchangeController {

    private final ExchangeService exchangeService;

    public ExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    // Open exchange page at /exchange
    @GetMapping
    public String exchangeRoot(Model model) {
        model.addAttribute("exchange", new Exchange());
        return "exchange";
    }

    // Show form at /exchange/new
    @GetMapping("/new")
    public String showExchangeForm(Model model) {
        model.addAttribute("exchange", new Exchange());
        return "exchange"; // exchange.html
    }

    // Handle form submit
    @PostMapping("/save")
    public String saveExchange(@ModelAttribute("exchange") Exchange exchange) {
        exchangeService.saveExchange(exchange);
        return "redirect:/exchange/success";
    }

    // Success page
    @GetMapping("/success")
    public String successPage() {
        return "exchange_success"; // create a simple success page
    }

    @GetMapping("/exchange-orders")
    public String showExchangeOrders(Model model) {
        List<Exchange> orders = exchangeService.getAllExchanges();
        model.addAttribute("exchangeOrders", orders);
        return "admin/exchange-orders";
    }

}
