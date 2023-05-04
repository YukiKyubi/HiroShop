package HiroShop.controller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import HiroShop.dto.ProductDto;
import HiroShop.service.IBillDetailsService;
import HiroShop.service.IProductService;
import HiroShop.service.ISlideService;

@Controller
public class UserHomeController extends UserBaseController {
	
	@Autowired
	private ISlideService slideService;
	
	@Autowired
	private IProductService productService;
	
	@Autowired
	private IBillDetailsService billdetailsDervice;
	
	
	@RequestMapping(value = { "/", "/hompage" })
	public ModelAndView Index() {
		mv.setViewName("user/index");
		productService.updateNewProducts();
		List<Long> topsellProducts = billdetailsDervice.topSellProducts();
		for(Long id : topsellProducts) {
			productService.updateTopSellProducts(id);
		}
		List<Long> notTopSellProducts = billdetailsDervice.notTopSellProducts();
		for(Long id : notTopSellProducts) {
			productService.updateNotTopSellProducts(id);
		}
		mv.addObject("isHomepage", "Homepage");
		mv.addObject("slides", slideService.getSlidesData());
		mv.addObject("products", productService.get16ProductsDistinct());
		mv.addObject("newproducts", productService.getNewProducts());
		mv.addObject("topsellproducts", productService.getTopSellProducts());
		return mv;
	}
}
