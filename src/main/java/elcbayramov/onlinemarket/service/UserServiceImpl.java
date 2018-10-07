package elcbayramov.onlinemarket.service;

import elcbayramov.onlinemarket.model.User;
import elcbayramov.onlinemarket.repository.UserRepository;
import elcbayramov.onlinemarket.util.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {


    @Autowired
    UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findOne(Long id) {
        User user = userRepository.findOne(id);
        return Optional.ofNullable(user);
    }

    @Override
    public void save(User user) {
        if (user.isVerify()) {
            userRepository.save(user);
        } else throw new UserNotFoundException("There is no verification");
    }

    @Override
    public List<User> findByName(String name) {
        if (name.isEmpty()) {
            throw new UserNotFoundException("Please enter username");
        } else

            return userRepository.findByName(name);
    }
}