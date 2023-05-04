package HiroShop.controller.admin;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import HiroShop.dto.PaginationDto;
import HiroShop.entity.Bill;
import HiroShop.service.IPaginationService;
import HiroShop.service.IBillService;

@Controller
public class AdminBillController extends AdminBaseController {
	
	@Autowired
	private IBillService billService;
	
	@Autowired
	private IPaginationService paginationService;
	
	@RequestMapping("/admin/bill")
	public ModelAndView Index() {
		mv.setViewName("admin/bill/index");
		try {
			int totalData = billService.getBillsData().size();
			PaginationDto paginatioInfo = paginationService.getInfoPagination(totalData, limitb, 1);
			List<Bill> pagination = billService.getBillsDataPagination(paginatioInfo.getStart(), limitb);
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
	
	@RequestMapping("/admin/billP{current}")
	public ModelAndView IndexPagination(@PathVariable int current) {
		mv.setViewName("admin/bill/index");
		try {
			int totalData = billService.getBillsData().size();
			PaginationDto paginatioInfo = paginationService.getInfoPagination(totalData, limitb, current);
			List<Bill> pagination = billService.getBillsDataPagination(paginatioInfo.getStart(), limitb);
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
	
	@RequestMapping(value = "/admin/createbill", method = RequestMethod.GET)
	public ModelAndView Create() {
		mv.setViewName("admin/bill/create");
		try {
			mv.addObject("newbill", new Bill());
			return mv;
		}
		catch (Exception e) {
			e.printStackTrace();
			return mv;
		}
	}
	
	@RequestMapping(value = "/admin/createbill", method = RequestMethod.POST)
	public ModelAndView Create(@ModelAttribute("newbill") Bill bill) {
		mv.setViewName("admin/bill/create");
		try {
			bill.setCreateat(formattedCurrentDate());
			billService.addBill(bill);
			mv.setViewName("redirect:/admin/bill");
			return mv;
		}
		catch (Exception e) {
			e.printStackTrace();
			return mv;
		}
	}
	
	@RequestMapping(value = "/admin/editbill{id}", method = RequestMethod.GET)
	public ModelAndView Edit(@PathVariable long id) {
		mv.setViewName("admin/bill/edit");
		try {
			mv.addObject("editbill", billService.getBillById(id));
			return mv;
		}
		catch (Exception e) {
			e.printStackTrace();
			return mv;
		}
	}
	
	@RequestMapping(value = "/admin/editbill{id}", method = RequestMethod.POST)
	public ModelAndView Edit(@PathVariable long id, @ModelAttribute("editbill") Bill bill) {
		mv.setViewName("admin/bill/edit");
		try {
			billService.edit(bill);
			mv.setViewName("redirect:/admin/bill");
			return mv;
		}
		catch (Exception e) {
			e.printStackTrace();
			return mv;
		}
	}
	
	@RequestMapping(value = "/admin/deletebill{id}", method = RequestMethod.GET)
	public ModelAndView Delete(@PathVariable long id) {
		mv.setViewName("admin/bill/delete");
		try {
			mv.addObject("deletebill", billService.getBillById(id));
			return mv;
		}
		catch (Exception e) {
			e.printStackTrace();
			return mv;
		}
	}
	
	@RequestMapping(value = "/admin/deletebill{id}", method = RequestMethod.POST)
	public ModelAndView Delete(@PathVariable long id, @ModelAttribute("deletebill") Bill bill) {
		mv.setViewName("admin/bill/delete");
		try {
			billService.delete(id);
			mv.setViewName("redirect:/admin/bill");
			return mv;
		}
		catch (Exception e) {
			e.printStackTrace();
			return mv;
		}
	}
	
	public String formattedCurrentDate() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String nowString = now.format(df);
		return nowString;
	}
}
