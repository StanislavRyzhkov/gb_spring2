package company.ryzhkov.shop.controller;

import company.ryzhkov.shop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("login")
public class LoginController {
    private CartService cartService;

    @Autowired
    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public String loginPage(Model model) {
        model.addAttribute("cartSize", cartService.getOrderItems().size());
        return "login";
    }
}
