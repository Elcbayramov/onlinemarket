package elcbayramov.onlinemarket.repository;

import elcbayramov.onlinemarket.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findByCategoryId(Long id);
    Product findBy(Long idProduct);
    Product findBy(String description);
    List<Product> findByCategory(String category);
    List<Product> findAll();

}
