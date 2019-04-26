package company.ryzhkov.shop.controller;

import company.ryzhkov.shop.entity.OrderItem;
import company.ryzhkov.shop.entity.Product;
import company.ryzhkov.shop.service.CartService;
import company.ryzhkov.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("products")
public class ProductController {
    private ProductService productService;
    private CartService cartService;

    @Autowired
    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{vendorCode}")
    public String productPage(@PathVariable("vendorCode") String vendorCode, Model model) {
        Product product = productService.getProductByVendorCode(vendorCode);
        if (product == null) return "error";
        model.addAttribute("product", product);
        model.addAttribute("orderItem", new OrderItem());
        model.addAttribute("cartSize", cartService.getOrderItems().size());
        return "product";
    }
}
