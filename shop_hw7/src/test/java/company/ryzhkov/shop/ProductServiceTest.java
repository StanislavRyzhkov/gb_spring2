package company.ryzhkov.shop;

import company.ryzhkov.shop.repository.ProductRepository;
import company.ryzhkov.shop.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @Test
    public void productServiceTest() {
        productService.getProductById(1L);
        Mockito.verify(productRepository, Mockito.times(1)).findById(1L);
    }
}
