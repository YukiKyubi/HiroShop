package HiroShop.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import HiroShop.entity.Bill;

public class BillMapper implements RowMapper<Bill> {

	public Bill mapRow(ResultSet rs, int rowNum) throws SQLException {
		Bill bill = new Bill();
		bill.setId(rs.getLong("id"));
		bill.setUsername(rs.getString("username"));
		bill.setName(rs.getString("name"));
		bill.setPhone(rs.getString("phone"));
		bill.setAddress(rs.getString("address"));
		bill.setQuantity(rs.getInt("quantity"));
		bill.setTotal(rs.getBigDecimal("total"));
		bill.setNote(rs.getString("note"));
		bill.setCreateat(rs.getString("createat"));
		return bill;
	}

}
