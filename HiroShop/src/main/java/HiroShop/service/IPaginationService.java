package HiroShop.service;

import org.springframework.stereotype.Service;

import HiroShop.dto.PaginationDto;

@Service
public interface IPaginationService {
	public PaginationDto getInfoPagination(int totalData, int limit, int current);
}
