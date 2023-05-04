package HiroShop.controller.user;

import java.math.BigDecimal;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import HiroShop.dto.Contact;
import HiroShop.entity.Account;
import HiroShop.entity.Product;

@Controller
public class UserContactController extends UserBaseController {
	
	@RequestMapping(value = "/contact", method = RequestMethod.GET)
	public ModelAndView Contact(HttpSession sesion) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("user/contact/contact");
		mv.addObject("searchobject", new Product());
		mv.addObject("companies", companyService.getCompaniesData());
		Contact contact = new Contact();
		Account account = (Account) sesion.getAttribute("loginsession");
		if(account != null) {
			mv.addObject("email", account.getUsername());
		}
		mv.addObject("contact", contact);
		return mv;
	}
	
	@RequestMapping(value = "/contact", method = RequestMethod.POST)
	public ModelAndView Contact(@ModelAttribute("contact") Contact contact) {
		mv.setViewName("user/contact/contact");
		mv.addObject("success", true);
		sendMail(contact);
		return mv;
	}
	
	public void sendMail(Contact contact) {
		final String username = "hiroshop76@gmail.com";
        final String password = "oxbbzbppguvnitui";

        Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS
        
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        
        try {

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("hiroshop76@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("hiroshop76@gmail.com")
            );
            message.setSubject("Khách hàng: " + contact.getName(), "utf-8");
            message.setText("Email: " + contact.getEmail() + ",\nTiêu đề: " + contact.getTitle() +
            		",\n" + contact.getMessage(), "utf-8");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
	}
}
