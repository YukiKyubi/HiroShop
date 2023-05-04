package HiroShop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import HiroShop.entity.Bill;

@Service
public interface IBillService {
	
	public int addBill(Bill bill);
	
	public long getTheLastBillId();
	
	public List<Bill> getBillsData();
	
	public List<Bill> getBillsDataPagination(int start, int limit);
	
	public Bill getBillById(long id);
	
	public int edit(Bill bill);
	
	public int delete(long id);
	
	public List<Bill> latestBillsInAPeriodTime(int number);
}
