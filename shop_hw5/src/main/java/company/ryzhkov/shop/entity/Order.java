package company.ryzhkov.shop.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "app_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private OrderStatus orderStatus;

    @ManyToOne
    private DeliveryAddress deliveryAddress;

    @Column(name = "price")
    private Integer price;

    @Column(name = "delivery_price")
    private Integer deliveryPrice;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "delivery_date")
    private Date deliveryDate;

    @Column(name = "create_at")
    private Date createAt;

    @Column(name = "update_at")
    private Date updateAt;
}
