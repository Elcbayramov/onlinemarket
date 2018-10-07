package elcbayramov.onlinemarket.service;

import elcbayramov.onlinemarket.model.User;

import java.util.List;
import java.util.Optional;


public interface UserService {

    Optional<User> findOne(Long id);

    void save(User user);
    List<User> findByName(String name);
}