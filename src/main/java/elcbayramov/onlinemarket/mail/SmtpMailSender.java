package elcbayramov.onlinemarket.mail;

import elcbayramov.onlinemarket.model.User;
import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class SmtpMailSender {

    @Autowired
    private JavaMailSender javaMailSender;

    public Configuration freemarkerConfig;

    public void send(User user) throws MessagingException {

        freemarkerConfig = new Configuration();

        MimeMessage message = javaMailSender.createMimeMessage();

        String content = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "\t<title></title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "Hello "+ user.getName() +" Sing up  <a href=\"http://localhost:3029/user/verify/" + user.getToken() + "\"> here </a> Confirm.\n" +
                "\n" +
                "</body>\n" +
                "</html>";

        MimeMessageHelper helper = new MimeMessageHelper(message, false); // false indicates
        // multipart message
        helper.setSubject("user verification");
        helper.setTo(user.getEmail());
        helper.setText(content, true); // true indicates html

        javaMailSender.send(message);

    }

}