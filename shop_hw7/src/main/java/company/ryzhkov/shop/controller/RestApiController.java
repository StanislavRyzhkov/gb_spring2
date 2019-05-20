package company.ryzhkov.shop.controller;

import company.ryzhkov.shop.entity.ItemAdder;
import company.ryzhkov.shop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api")
public class RestApiController {
    private CartService cartService;

    @Autowired
    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/cartSize")
    public ResponseEntity cartSize() {
        return ResponseEntity.ok(cartService.getOrderItems().size());
    }

    @PostMapping("/addToCart")
    public ResponseEntity addOrderItem(@RequestBody ItemAdder itemAdder) throws IOException {
        cartService.createAndAddOrderItem(itemAdder.getItemPrice(), itemAdder.getQuantity(), itemAdder.getProductId());
        return ResponseEntity.ok(cartService.getOrderItems().size());
    }
}
