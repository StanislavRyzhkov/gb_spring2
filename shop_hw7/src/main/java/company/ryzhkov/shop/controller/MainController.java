package company.ryzhkov.shop.controller;

import company.ryzhkov.shop.entity.Product;
import company.ryzhkov.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequestMapping
public class MainController {
    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String mainPage(@RequestParam(value = "param", required = false) String param, Model model) {
        List<Product> products = param == null ? productService.getAllProducts() : productService.getByParam(param);
        model.addAttribute("products", products);
        return "index";
    }
}
