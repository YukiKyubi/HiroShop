package HiroShop.controller.user;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import HiroShop.entity.Product;
import HiroShop.service.ICategoryService;
import HiroShop.service.ICompanyService;

@Controller
public class UserBaseController {
	
	public ModelAndView mv = new ModelAndView();
	
	@Autowired
	public ICategoryService categoryService;
	
	@Autowired
	public ICompanyService companyService;
	
	@PostConstruct
	public ModelAndView init() {
		mv.addObject("categories", categoryService.getCategoriesData());
		mv.addObject("companies", companyService.getCompaniesData());
		mv.addObject("searchobject", new Product());
		return mv;
	}
}
