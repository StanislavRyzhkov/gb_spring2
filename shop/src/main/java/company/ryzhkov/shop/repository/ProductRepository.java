package company.ryzhkov.shop.repository;

import company.ryzhkov.shop.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    @Override
    Optional<Product> findById(Long aLong);

    @Override
    Iterable<Product> findAll();

    Optional<Product> findProductByVendorCode(String vendorCode);
}
