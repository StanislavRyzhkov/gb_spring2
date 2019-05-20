package company.ryzhkov.shop.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ItemAdder {
    private Integer itemPrice;
    private Integer quantity;
    private Long productId;
}
