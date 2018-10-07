package elcbayramov.onlinemarket.util;

import java.util.Random;

public class TokenUtil {

    /**
     * @return User token random generated
     */
    public static int generateToken(){
        Random random = new Random();
        int token = random.nextInt(999999999)+1;
        return token;
    }

}
