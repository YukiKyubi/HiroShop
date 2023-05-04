package HiroShop.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import HiroShop.entity.Company;
import HiroShop.mapper.CompanyMapper;

@Repository
public class CompanyDao extends BaseDao {
	
	public List<Company> getCompaniesData() {
		String sql = "SELECT * FROM `company`";
		List<Company> list = jdbcTemplate.query(sql, new CompanyMapper());
		return list;
	}
	
	public Company getCompanyByCompanyId(int company_id) {
		String sql = "SELECT * FROM `company` WHERE id='" + company_id + "'";
		Company company = jdbcTemplate.queryForObject(sql, new CompanyMapper());
		return company;
	}
	
	public int create(Company company) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO `company` ");
		sql.append("(`id`, `name`, `image`) ");
		sql.append("VALUES (NULL, '" + company.getName() + "', '" + company.getImage() + "');");
		int insert = jdbcTemplate.update(sql.toString());
		return insert;
	}
	
	public int edit(Company company) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE `company` ");
		sql.append("SET `name` = '" + company.getName() + "', `image` = '" + company.getImage() + "' ");
		sql.append("WHERE `company`.`id` = " + company.getId());
		int edit = jdbcTemplate.update(sql.toString());
		return edit;
	}
	
	public int delete(int id) {
		String sql = "DELETE FROM `company` WHERE id=" + id;
		int delete = jdbcTemplate.update(sql);
		return delete;
	}
	
	public List<Company> getCompaniesDataPagination(int start, int limit) {
		String sql = "SELECT * FROM `company` LIMIT " + start + ", " + limit;
		List<Company> list = jdbcTemplate.query(sql, new CompanyMapper());
		return list;
	}
}
