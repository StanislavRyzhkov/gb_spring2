package company.ryzhkov.shop.controller;

import company.ryzhkov.shop.entity.OrderItem;
import company.ryzhkov.shop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
        model.addAttribute("cartSize", orderItems.size());
        model.addAttribute("totalCost", cartService.getTotalCost());
        return "cart";
    }

    @PostMapping
    public void addOrderItem(
            @RequestParam("item_price") Integer itemPrice,
            @RequestParam("quantity") Integer quantity,
            @RequestParam("product_id") Long productId,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        cartService.createAndAddOrderItem(itemPrice, quantity, productId);
        response.sendRedirect(request.getHeader("referer"));
    }
}
