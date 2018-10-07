package elcbayramov.onlinemarket.controller;

import elcbayramov.onlinemarket.mail.SmtpMailSender;
import elcbayramov.onlinemarket.model.User;
import elcbayramov.onlinemarket.repository.UserRepository;
import elcbayramov.onlinemarket.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    SmtpMailSender smtpMailSender;

    /**
     * @param user - user object
     * @return - HTTP CREATED OR CONFLICT - if http conflict user already exits, else user created and mail send to user's mail for verify address
     * @throws EntityNotFoundException
     */
    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public ResponseEntity<?> register(@RequestBody User user) throws EntityNotFoundException {
        boolean userFlag = false;
        List<User> userList = (List<User>) userRepository.findAll();

        if (userList.size() == 0){
            userFlag = true;
        }else{
            for (int i=0; i<userList.size(); i++){
                if (user.getEmail().equals(userList.get(i).getEmail())){
                    userFlag = false; // User already
                    break;
                }else{
                    userFlag= true; // User does not exist
                }
            }
        }

        if (userFlag){
            user.setToken(TokenUtil.generateToken());
            userRepository.save(user);
            try {
                smtpMailSender.send(user);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

    }

    /**
     * @param token - user token for mail address verify view in the url
     * @return - User mail address verified.
     * @throws EntityNotFoundException
     */
    @RequestMapping(method = RequestMethod.GET, value = "/verify/{token}")
    public ResponseEntity<String> verify(@PathVariable int token) throws EntityNotFoundException {

        List<User> userList = (List<User>) userRepository.findAll();

        for (User user : userList) {
            if (user.getToken() == token){
                user.setVerify(true);
                userRepository.save(user);
                break;
            }
        }

        return new ResponseEntity<>("Register Success !", HttpStatus.OK);
    }

    /**
     * @param user - login operation for user object
     * @return - HTTP and token value as enum type.
     * @throws EntityNotFoundException
     */
    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public ResponseEntity<Integer> login(@RequestBody User user) throws EntityNotFoundException {
        List<User> userList = (List<User>) userRepository.findAll();

        int token = 0;

        for (User userResult : userList) {
            if (userResult.getEmail().equals(user.getEmail()) && userResult.getPassword().equals(user.getPassword())){
                if (userResult.isVerify()){
                    token = userResult.getToken();
                    break;
                }else{
                    token = -1; // User not verify
                }
            }
        }

        return new ResponseEntity<>(token, HttpStatus.OK);
    }

}
