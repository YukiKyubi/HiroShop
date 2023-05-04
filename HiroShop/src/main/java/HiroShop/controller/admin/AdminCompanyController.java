package HiroShop.controller.admin;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import HiroShop.dto.PaginationDto;
import HiroShop.entity.Company;
import HiroShop.service.ICompanyService;
import HiroShop.service.IImageService;
import HiroShop.service.IPaginationService;
import HiroShop.service.IProductService;

@Controller
public class AdminCompanyController extends AdminBaseController {
	
	@Autowired
	IProductService productService;
	
	@Autowired
	ICompanyService companyService;
	
	@Autowired
	IImageService imageService;
	
	@Autowired
	IPaginationService paginationService;
	
	@RequestMapping("/admin/company")
	public ModelAndView Index() {
		mv.setViewName("admin/company/index");
		try {
			int totalData = companyService.getCompaniesData().size();
			PaginationDto paginationInfo = paginationService.getInfoPagination(totalData, limit, 1);
			List<Company> pagination = companyService.getCompaniesDataPagination(paginationInfo.getStart(), limit);
			mv.addObject("paginationinfo", paginationInfo);
			mv.addObject("pagination", pagination);
			mv.addObject("totaldata", totalData);
			return mv;
		}
		catch (Exception e) {
			e.printStackTrace();
			return mv;
		}
	}
	
	@RequestMapping("/admin/companyP{current}")
	public ModelAndView IndexPagination(@PathVariable int current) {
		mv.setViewName("admin/company/index");
		try {
			int totalData = companyService.getCompaniesData().size();
			PaginationDto paginationInfo = paginationService.getInfoPagination(totalData, limit, current);
			List<Company> pagination = companyService.getCompaniesDataPagination(paginationInfo.getStart(), limit);
			mv.addObject("paginationinfo", paginationInfo);
			mv.addObject("pagination", pagination);
			return mv;
		}
		catch (Exception e) {
			e.printStackTrace();
			return mv;
		}
	}
	
	@RequestMapping(value = "/admin/createcompany", method = RequestMethod.GET)
	public ModelAndView Create() {
		mv.setViewName("admin/company/create");
		mv.addObject("newcompany", new Company());
		return mv;
	}
	
	@RequestMapping(value = "/admin/createcompany", method = RequestMethod.POST)
	public ModelAndView Create(@ModelAttribute("newcompany") Company company, HttpSession s, @RequestParam("imageupload") CommonsMultipartFile file) {
		mv.setViewName("admin/company/create");
		try {
			byte[] data = file.getBytes();
			String path = s.getServletContext().getRealPath("/") + "assets" + File.separator + "img" + File.separator
					+ "companies" + File.separator + file.getOriginalFilename();
			FileOutputStream os = new FileOutputStream(path);
			os.write(data);
			os.close();
			company.setImage(file.getOriginalFilename());
			companyService.create(company);
			mv.setViewName("redirect:/admin/company");
			return mv;
		}
		catch (Exception e) {
			e.printStackTrace();
			return mv;
		}
	}
	
	@RequestMapping(value = "/admin/editcompany{id}", method = RequestMethod.GET)
	public ModelAndView Edit(@PathVariable int id) {
		mv.setViewName("admin/company/edit");
		mv.addObject("editcompany", companyService.getCompanyByCompanyId(id));
		return mv;
	}
	
	@RequestMapping(value = "/admin/editcompany{id}", method = RequestMethod.POST)
	public ModelAndView Edit(@PathVariable int id, @ModelAttribute("editcompany") Company company, HttpSession s, @RequestParam("imageupload") CommonsMultipartFile file) {
		mv.setViewName("admin/company/edit");
		try {
			byte[] data = file.getBytes();
			String path = s.getServletContext().getRealPath("/") + "assets" + File.separator + "img" + File.separator
					+ "companies" + File.separator + file.getOriginalFilename();
			company.setImage(companyService.getCompanyByCompanyId(id).getImage());
			if(data.length > 0) {
				FileOutputStream os = new FileOutputStream(path);
				os.write(data);
				os.close();
				company.setImage(file.getOriginalFilename());
			}
			String dbPath = s.getServletContext().getRealPath("/") + "assets" + File.separator + "img" + File.separator
					+ "companies" + File.separator + companyService.getCompanyByCompanyId(id).getImage();
			companyService.edit(company);
			if(data.length > 0) {
				File dbImage = new File(dbPath);
				if(dbImage.exists()) {
					dbImage.delete();
				}
			}
			mv.setViewName("redirect:/admin/company");
			return mv;
		}
		catch (Exception e) {
			e.printStackTrace();
			return mv;
		}
	}
	
	@RequestMapping(value = "/admin/deletecompany{id}", method = RequestMethod.GET)
	public ModelAndView Delete(@PathVariable int id) {
		mv.setViewName("admin/company/delete");
		mv.addObject("deletecompany", companyService.getCompanyByCompanyId(id));
		return mv;
	}
	
	@RequestMapping(value = "/admin/deletecompany{id}", method = RequestMethod.POST)
	public ModelAndView Delete(@PathVariable int id, @ModelAttribute("deletecompany") Company company, HttpSession s) {
		mv.setViewName("admin/company/delete");
		try {
			List<String> imagesp = productService.getImagesByCompanyId(id);
			List<Long> ids = productService.getProductIdsByCompanyId(id);
			List<String> imagesi = new ArrayList<String>();
			for(Long item : ids) {
				List<String> imagesbyid = imageService.getImagesStringByProductId(item);
				for(String imagebyid : imagesbyid) {
					imagesi.add(imagebyid);
				}
			}
			String path = s.getServletContext().getRealPath("/") + "assets" + File.separator + "img" + File.separator
					+ "companies" + File.separator + companyService.getCompanyByCompanyId(id).getImage();
			companyService.delete(id);
			File deleteImage = new File(path);
			if(deleteImage.exists()) {
				deleteImage.delete();
			}
			for(String item : imagesp) {
				String dbPath = s.getServletContext().getRealPath("/") + "assets" + File.separator + "img" + File.separator
						+ "products" + File.separator + item;
				File image = new File(dbPath);
				if(image.exists()) {
					image.delete();
				}
			}
			for(String item : imagesi) {
				String dbPath = s.getServletContext().getRealPath("/") + "assets" + File.separator + "img" + File.separator
						+ "products" + File.separator + item;
				File image = new File(dbPath);
				if(image.exists()) {
					image.delete();
				}
			}
			mv.setViewName("redirect:/admin/company");
			return mv;
		}
		catch (Exception e) {
			e.printStackTrace();
			return mv;
		}
	}
}
