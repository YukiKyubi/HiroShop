package HiroShop.controller.admin;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import HiroShop.entity.Account;

@Controller
public class AdminBaseController {
	
	public ModelAndView mv = new ModelAndView();
	
	public final int limit = 7, limitp = 10, limiti = 20, limita = 20, limitr = 20, limitb=20, limitbd=30, limitc = 20;
	
	@PostConstruct
	public ModelAndView init() {
		return mv;
	}
}
