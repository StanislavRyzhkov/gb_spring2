package company.ryzhkov.shop.service;

import company.ryzhkov.shop.entity.OrderItem;
import company.ryzhkov.shop.entity.Product;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

@Service
@Getter
@Setter
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CartService {
    private ProductService productService;
    private List<OrderItem> orderItems;
    private int totalCost;

    public CartService() {
        totalCost = 0;
        orderItems = new ArrayList<>();
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public void createAndAddOrderItem(Integer itemPrice, Integer quantity, Long productId) {
        Product product = productService.getProductById(productId);
        OrderItem orderItem = findOrderFromProduct(product);
        if (orderItem == null) {
            orderItem = new OrderItem();
            orderItem.setItemPrice(itemPrice);
            orderItem.setQuantity(quantity);
            orderItem.setTotalPrice(itemPrice * quantity);
            orderItem.setProduct(product);
            orderItems.add(orderItem);
        } else {
            Integer newQuantity = orderItem.getQuantity() + quantity;
            orderItem.setQuantity(newQuantity);
            orderItem.setTotalPrice(orderItem.getItemPrice() * newQuantity);
        }
        recalculate();
    }

    private OrderItem findOrderFromProduct(Product product) {
        for (OrderItem orderItem : orderItems) {
            if (product.equals(orderItem.getProduct())) {
                return orderItem;
            }
        }
        return null;
    }

    private void recalculate() {
        totalCost = 0;
        for (OrderItem orderItem : orderItems) {
            totalCost += orderItem.getTotalPrice();
        }
    }
}
