package elcbayramov.onlinemarket.repository;

import elcbayramov.onlinemarket.model.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository <Category, Long> {

}
