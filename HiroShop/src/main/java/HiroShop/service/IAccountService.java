package HiroShop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import HiroShop.entity.Account;

@Service
public interface IAccountService {
	
	public Account getAccountById(long id);
	
	public int addAccount(Account account);
	
	public Account getAccountByUsername(String username);
	
	public List<Account> getAccountsData();
	
	public int edit(Account account);
	
	public int delete(long id);
	
	public List<Account> getAccountsDataPagination(int start, int limit);
	
	public int setNewPassword(String password, String username);
}
