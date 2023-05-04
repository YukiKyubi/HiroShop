package HiroShop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import HiroShop.entity.Company;

@Service
public interface ICompanyService {
	
	public List<Company> getCompaniesData();
	
	public Company getCompanyByCompanyId(int company_id);
	
	public int create(Company company);
	
	public int edit(Company company);
	
	public int delete(int id);
	
	public List<Company> getCompaniesDataPagination(int start, int limit);
}
