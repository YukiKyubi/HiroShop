package HiroShop.controller.user;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import HiroShop.dto.CartDto;
import HiroShop.entity.Account;
import HiroShop.entity.Cart;
import HiroShop.service.IAccountService;
import HiroShop.service.ICartService;
import HiroShop.service.IRoleService;

@Controller
public class UserAccountController extends UserBaseController {
	
	@Autowired
	private IAccountService accountService;
	
	@Autowired
	private IRoleService roleService;
	
	@Autowired
	private ICartService cartService;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView Login() {
		mv.setViewName("all/login");
		mv.addObject("loginaccount", new Account());
		return mv;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView Login(@ModelAttribute("loginaccount") Account account, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("all/login");
		try {
			String password = account.getPassword();
			Account check = accountService.getAccountByUsername(account.getUsername().trim());
			if(check != null) {
				if(BCrypt.checkpw(password, check.getPassword())) {
					Account loginedAccount = (Account) session.getAttribute("loginsession");
					if(loginedAccount != null) {
						session.removeAttribute("loginsession");
					}
					session.setAttribute("loginsession", check);
					session.setAttribute("rolesession", roleService.getRoleStringByAccountId(check.getId()));
					List<Cart> list = cartService.getCartsDataByUsername(check.getUsername());
					HashMap<Long, CartDto> cart = new HashMap<Long, CartDto>();
					if(!list.isEmpty()) {
						cart = cartService.createCartSession(list, check.getUsername());
						session.setAttribute("cart", cart);
						session.setAttribute("totalQuantity", cartService.totalQuantity(cart));
						session.setAttribute("totalPrice", cartService.totalPrice(cart));
					}
					mv.setViewName("redirect:/");
					return mv;
				}
				mv.addObject("iscorrect", false);
				return mv;
			}
			return mv;
		}
		catch (Exception e) {
			e.printStackTrace();
			mv.addObject("isexist", false);
			return mv;
		}
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public ModelAndView Signup() {
		mv.setViewName("all/signup");
		mv.addObject("newaccount", new Account());
		return mv;
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ModelAndView Signup(@ModelAttribute("newaccount") Account account) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("all/signup");
		try {
			int check = 0;
			List<Account> list = accountService.getAccountsData();
			if(list.isEmpty()) {
				check = 1;
			}
			else {
				for(Account item : list) {
					check = 1;
					if(item.getUsername().equals(account.getUsername())) {
						check = 0;
						break;
					}
				}
			}
			if(check == 1) {
				int addAccount = accountService.addAccount(account);
				Account created = accountService.getAccountByUsername(account.getUsername().trim());
				roleService.setRole(created.getId(), "user");
				if(addAccount > 0) {	
					mv.addObject("issuccess", true);
				}
			}
			else {
				mv.addObject("isexist", true);
			}
			return mv;
		}
		catch (Exception e) {
			e.printStackTrace();
			mv.addObject("error", e.getMessage());
			return mv;
		}
	}
	
	@RequestMapping("/logout")
	public String Logout(HttpSession session, HttpServletRequest request) {
		mv.setViewName("/logout");
		session.removeAttribute("loginsession");
		session.removeAttribute("rolesession");
		session.removeAttribute("cart");
		return "redirect:" + request.getHeader("Referer");
	}
	
}
