package elcbayramov.onlinemarket.service;


import elcbayramov.onlinemarket.model.Product;
import elcbayramov.onlinemarket.repository.ProductRepository;
import elcbayramov.onlinemarket.repository.mapper.ProductMapper;
import elcbayramov.onlinemarket.util.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductMapper productMapper;


    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product findBy(Long productId) throws ProductNotFoundException {
        Product product = productMapper.getProductByid(productId);
        if (product != null)
            return product;
        else
            throw new ProductNotFoundException("There is not a such product");
    }

    @Override
    public Product findBy(String description) throws ProductNotFoundException {
        Product product = productRepository.findBy(description);
        if (product != null)
            return product;
        else
            throw new ProductNotFoundException("There is not a such product");
    }

    @Override
    public List<Product> findByCategory(String category) throws ProductNotFoundException {
        List<Product> products = productRepository.findByCategory(category);
        if (products.isEmpty() || products == null)
            throw new ProductNotFoundException("There is not a such product");
        else
            return products;
    }

    @Override
    public List<Product> findAll() throws ProductNotFoundException {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty() || products == null)
            throw new ProductNotFoundException("There is not a such product");
        else
            return products;
    }
}