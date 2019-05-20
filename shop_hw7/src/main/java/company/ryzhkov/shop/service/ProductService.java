package company.ryzhkov.shop.service;

import company.ryzhkov.shop.entity.Product;
import company.ryzhkov.shop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product getProductByVendorCode(String vendorCode) {
        return productRepository.findProductByVendorCode(vendorCode).orElse(null);
    }

    public List<Product> getAllProducts() {
        return  (List<Product>) productRepository.findAll();
    }

    public List<Product> getByParam(String param) {
        return productRepository.findByParam(param.toLowerCase());
    }
}
