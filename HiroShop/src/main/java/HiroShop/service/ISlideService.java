package HiroShop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import HiroShop.entity.Slide;

@Service
public interface ISlideService {
	
	public List<Slide> getSlidesData();
	
	public List<Slide> getSlidesDataPagination(int start, int limit);
	
	public Slide getSlideById(long id);
	
	public int create(Slide slide);
	
	public int edit(Slide slide);
	
	public int delete(long id);
}
