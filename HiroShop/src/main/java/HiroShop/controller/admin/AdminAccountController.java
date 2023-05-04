package HiroShop.controller.admin;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import HiroShop.dto.PaginationDto;
import HiroShop.entity.Account;
import HiroShop.service.IAccountService;
import HiroShop.service.IPaginationService;

@Controller
public class AdminAccountController extends AdminBaseController {
	
	@Autowired
	private IAccountService accountService;
	
	@Autowired
	private IPaginationService paginationService;
	
	@RequestMapping("/admin/account")
	public ModelAndView Index() {
		mv.setViewName("admin/account/index");
		try {
			int totalData = accountService.getAccountsData().size();
			PaginationDto paginatioInfo = paginationService.getInfoPagination(totalData, limita, 1);
			List<Account> pagination = accountService.getAccountsDataPagination(paginatioInfo.getStart(), limita);
			mv.addObject("paginationinfo", paginatioInfo);
			mv.addObject("pagination", pagination);
			mv.addObject("totaldata", totalData);
			return mv;
		}
		catch (Exception e) {
			e.printStackTrace();
			return mv;
		}
	}
	
	@RequestMapping("/admin/accountP{current}")
	public ModelAndView IndexPagination(@PathVariable int current) {
		mv.setViewName("admin/account/index");
		try {
			int totalData = accountService.getAccountsData().size();
			PaginationDto paginatioInfo = paginationService.getInfoPagination(totalData, limita, current);
			List<Account> pagination = accountService.getAccountsDataPagination(paginatioInfo.getStart(), limita);
			mv.addObject("paginationinfo", paginatioInfo);
			mv.addObject("pagination", pagination);
			mv.addObject("totaldata", totalData);
			return mv;
		}
		catch (Exception e) {
			e.printStackTrace();
			return mv;
		}
	}
	
	@RequestMapping(value = "/admin/createaccount", method = RequestMethod.GET)
	public ModelAndView Create() {
		mv.setViewName("admin/account/create");
		try {
			mv.addObject("newaccount", new Account());
			return mv;
		}
		catch (Exception e) {
			e.printStackTrace();
			return mv;
		}
	}
	
	@RequestMapping(value = "/admin/createaccount", method = RequestMethod.POST)
	public ModelAndView Create(@ModelAttribute("newaccount") Account account) {
		mv.setViewName("admin/account/create");
		try {
			account.setIs_verified(false);
			accountService.addAccount(account);
			mv.setViewName("redirect:/admin/account");
			return mv;
		}
		catch (Exception e) {
			e.printStackTrace();
			return mv;
		}
	}
	
	@RequestMapping(value = "/admin/editaccount{id}", method = RequestMethod.GET)
	public ModelAndView Edit(@PathVariable long id) {
		mv.setViewName("admin/account/edit");
		try {
			mv.addObject("editaccount", accountService.getAccountById(id));
			return mv;
		}
		catch (Exception e) {
			e.printStackTrace();
			return mv;
		}
	}
	
	@RequestMapping(value = "/admin/editaccount{id}", method = RequestMethod.POST)
	public ModelAndView Edit(@PathVariable long id, @ModelAttribute("editaccount") Account account) {
		mv.setViewName("admin/account/edit");
		try {
			account.setPassword(BCrypt.hashpw(account.getPassword(), BCrypt.gensalt(12)));
			accountService.edit(account);
			mv.setViewName("redirect:/admin/account");
			return mv;
		}
		catch (Exception e) {
			e.printStackTrace();
			return mv;
		}
	}
	
	@RequestMapping(value = "/admin/deleteaccount{id}", method = RequestMethod.GET)
	public ModelAndView Delete(@PathVariable long id) {
		mv.setViewName("admin/account/delete");
		try {
			mv.addObject("deleteaccount", accountService.getAccountById(id));
			return mv;
		}
		catch (Exception e) {
			e.printStackTrace();
			return mv;
		}
	}
	
	@RequestMapping(value = "/admin/deleteaccount{id}", method = RequestMethod.POST)
	public ModelAndView Delete(@PathVariable long id, @ModelAttribute("deleteaccount") Account account) {
		mv.setViewName("admin/account/delete");
		try {
			accountService.delete(id);
			mv.setViewName("redirect:/admin/account");
			return mv;
		}
		catch (Exception e) {
			e.printStackTrace();
			return mv;
		}
	}
}
