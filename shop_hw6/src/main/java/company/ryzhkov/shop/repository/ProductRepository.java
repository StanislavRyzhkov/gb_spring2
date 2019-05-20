package company.ryzhkov.shop.repository;

import company.ryzhkov.shop.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    @Override
    Optional<Product> findById(Long aLong);

    @Override
    Iterable<Product> findAll();

    Optional<Product> findProductByVendorCode(String vendorCode);

    @Query("select p from Product p where " +
            "lower(p.name) like %?1% or " +
            "lower(p.vendorCode) like %?1% or " +
            "lower(p.category.name) like %?1% or " +
            "lower(p.description) like %?1%")
    List<Product> findByParam(String param);
}
