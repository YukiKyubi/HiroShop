package HiroShop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import HiroShop.entity.Role;

@Service
public interface IRoleService {
	
	public int setRole(long account_id, String role);
	
	public List<Role> getRolesData();
	
	public int create(Role role);
	
	public int edit(Role role);
	
	public int delete(long id);
	
	public List<Role> getRolesDataPagination(int start, int limit);
	
	public Role getRoleById(long id);
	
	public List<String> getRoleStringByAccountId(long account_id);
}
