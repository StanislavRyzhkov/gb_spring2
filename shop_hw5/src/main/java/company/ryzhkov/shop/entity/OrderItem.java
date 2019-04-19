package company.ryzhkov.shop.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "app_order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Order order;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "item_price")
    private Integer itemPrice;

    @Column(name = "total_price")
    private Integer totalPrice;
}
