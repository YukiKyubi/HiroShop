package HiroShop.controller.user;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import HiroShop.dto.CartDto;
import HiroShop.entity.Account;
import HiroShop.entity.Bill;
import HiroShop.entity.BillDetails;
import HiroShop.entity.Product;
import HiroShop.service.IBillDetailsService;
import HiroShop.service.IBillService;
import HiroShop.service.ICartService;
import HiroShop.service.IProductService;

@Controller
public class UserCartController extends UserBaseController {
	
	@Autowired
	ICartService cartService;
	
	@Autowired
	IBillService billService;
	
	@Autowired
	IBillDetailsService billdetailsService;
	
	@Autowired
	IProductService productService;
	
	@RequestMapping("/cart")
	public ModelAndView Index() {
		mv.setViewName("user/cart/cart");
		return mv;
	}
	
	@RequestMapping(value = "/addcart", method = RequestMethod.GET)
	@ResponseBody
	public int addToCart(HttpSession session, HttpServletRequest request, HttpServletResponse respone) {
		HashMap<Long, CartDto> cart = (HashMap<Long, CartDto>) session.getAttribute("cart");
		Account accountSession = (Account) session.getAttribute("loginsession");
		String username = accountSession.getUsername();
		long id = Long.parseLong(request.getParameter("id"));
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		if(cart == null) {
			cart = new HashMap<Long, CartDto>();
		}
		cart = cartService.addToCart(id, quantity, cart, username);
		session.setAttribute("cart", cart);
		session.setAttribute("totalQuantity", cartService.totalQuantity(cart));
		session.setAttribute("totalPrice", cartService.totalPrice(cart));
		int size = cart.size();
		return size;
	}
	
	@RequestMapping(value = "/edit-item", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<Long, CartDto> editCartItem(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		HashMap<Long, CartDto> cart = (HashMap<Long, CartDto>) session.getAttribute("cart");
		if(cart.get((long)-1) != null) {
			cart.remove((long)-1);
		}
		Account accountSession = (Account) session.getAttribute("loginsession");
		String username = accountSession.getUsername();
		long id = Long.parseLong(request.getParameter("id"));
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		cart = cartService.editCartItem(id, quantity, cart, username);
		session.setAttribute("cart", cart);
		session.setAttribute("totalQuantity", cartService.totalQuantity(cart));
		session.setAttribute("totalPrice", cartService.totalPrice(cart));
		return cart;
	}
	
	@RequestMapping("delete-item/{id}")
	public String deleteCartItem(HttpSession session, HttpServletRequest request, @PathVariable long id) {
		HashMap<Long, CartDto> cart = (HashMap<Long, CartDto>) session.getAttribute("cart");
		Account accountSession = (Account) session.getAttribute("loginsession");
		String username = accountSession.getUsername();
		cart = cartService.deleteCartItem(id, cart, username);
		session.setAttribute("cart", cart);
		session.setAttribute("totalQuantity", cartService.totalQuantity(cart));
		session.setAttribute("totalPrice", cartService.totalPrice(cart));
		return "redirect:" + request.getHeader("Referer");
	}
	
	@RequestMapping(value = "/checkout", method = RequestMethod.GET)
	public ModelAndView checkOut(HttpSession session) {
		mv.setViewName("user/cart/checkout");
		Bill bill = new Bill();
		Account loginaccount = (Account) session.getAttribute("loginsession");
		if(loginaccount != null) {
			bill.setUsername(loginaccount.getUsername());
		}
		mv.addObject("bill", bill);
		return mv;
	}
	
	@RequestMapping(value = "/checkout", method = RequestMethod.POST)
	public ModelAndView checkOut(HttpSession session, @ModelAttribute("bill") Bill bill) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("user/cart/cart");
		mv.addObject("searchobject", new Product());
		mv.addObject("companies", companyService.getCompaniesData());
		Account account = (Account) session.getAttribute("loginsession");
		String username = account.getUsername();
		bill.setQuantity(Integer.parseInt(session.getAttribute("totalQuantity").toString()));
		bill.setTotal(BigDecimal.valueOf(Double.parseDouble(session.getAttribute("totalPrice").toString())).setScale(2,	RoundingMode.HALF_UP));
		bill.setCreateat(formattedCurrentDate());
		try {		
			int insert = billService.addBill(bill);
			if(insert > 0) {
				long id = billService.getTheLastBillId();
				HashMap<Long, CartDto> cart = (HashMap<Long, CartDto>)session.getAttribute("cart");
				for(Map.Entry<Long, CartDto> item : cart.entrySet()) {
					BillDetails billdetails = new BillDetails();
					billdetails.setBill_id(id);
					billdetails.setProduct_id(item.getValue().getProduct().getId());
					billdetails.setQuantity(item.getValue().getQuantity());
					productService.updateQuantity(item.getValue().getProduct().getId(), item.getValue().getProduct().getQuantity() - item.getValue().getQuantity());
					billdetails.setTotal(item.getValue().getTotalPrice());
					billdetailsService.addBillDetails(billdetails);
				}
				sendMail(BigDecimal.valueOf(Double.parseDouble(session.getAttribute("totalPrice").toString())).setScale(2, RoundingMode.HALF_UP), username);
				session.removeAttribute("cart");
				cartService.deleteCartByUsername(username);
				mv.addObject("success", true);
				return mv;
			}
			return mv;
		}
		catch (Exception e) {
			e.printStackTrace();
			return mv;
		}
	}
	
	public void sendMail(BigDecimal totalPrice, String email) {
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
                    InternetAddress.parse(email)
            );
            message.setSubject("Đơn hàng của bạn", "utf-8");
            message.setText("Đơn hàng của bạn  " + totalPrice + " vnđ được đặt thành công!" 
                    + "\n\n Please do not spam my email!", "utf-8");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
	}
	
	public String formattedCurrentDate() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String nowString = now.format(df);
		return nowString;
	}
}
