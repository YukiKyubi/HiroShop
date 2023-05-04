package HiroShop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import HiroShop.dao.SlideDao;
import HiroShop.entity.Slide;

@Service
public class SlideServiceImplement implements ISlideService {
	
	@Autowired
	private SlideDao slideDao;

	public List<Slide> getSlidesData() {
		return slideDao.getSlidesData();
	}

	public List<Slide> getSlidesDataPagination(int start, int limit) {
		return slideDao.getSlidesDataPagination(start, limit);
	}

	public Slide getSlideById(long id) {
		return slideDao.getSlideById(id);
	}

	public int create(Slide slide) {
		return slideDao.create(slide);
	}

	public int edit(Slide slide) {
		return slideDao.edit(slide);
	}

	public int delete(long id) {
		return slideDao.delete(id);
	}

}
