package company.ryzhkov.shop.controller;

import company.ryzhkov.shop.service.CartService;
import company.ryzhkov.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping
public class MainController {
    private ProductService productService;

    @Resource(name = "cartService")
    private CartService cartService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String mainPage(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("cartSize", cartService.getOrderItems().size());
        return "index";
    }
}
