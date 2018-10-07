package elcbayramov.onlinemarket.repository;

import elcbayramov.onlinemarket.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findByName(String name);
}
