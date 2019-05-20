package company.ryzhkov.shop.controller;

import company.ryzhkov.shop.entity.OrderItem;
import company.ryzhkov.shop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("cart")
public class CartController {
    private CartService cartService;

    @Autowired
    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public String cartPage(Model model) {
        List<OrderItem> orderItems = cartService.getOrderItems();
        model.addAttribute("items", orderItems);
        model.addAttribute("totalCost", cartService.getTotalCost());
        return "cart";
    }
}
