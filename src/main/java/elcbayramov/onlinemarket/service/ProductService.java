package elcbayramov.onlinemarket.service;

import elcbayramov.onlinemarket.model.Product;

import java.util.List;

public interface ProductService {

    Product findBy(Long productId);

    Product findBy(String description);

    List<Product> findByCategory(String category);

    List<Product> findAll();

}