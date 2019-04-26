package company.ryzhkov.shop.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "app_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    private Category category;

    @Column(name = "vendor_code", unique = true, nullable = false)
    private String vendorCode;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "name")
    private String name;

    @Column(name = "description", length = 1000)
    private String description;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "product", fetch = FetchType.EAGER)
    private List<ProductImage> images;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Product) {
            Product product = (Product) obj;
            return this.vendorCode.equals(product.vendorCode);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.vendorCode);
    }

    public String getMainImage() {
        return this.getImages().isEmpty() ? "" : this.getImages().get(0).getPath();
    }
}
