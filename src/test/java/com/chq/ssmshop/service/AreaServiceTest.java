package com.chq.ssmshop.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.chq.ssmshop.entity.Area;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AreaServiceTest {

	@Autowired
	private AreaService areaService;
	@Autowired
	private CacheService cacheService;

	@Test
	public void queryListTest() {
		List<Area> result = areaService.queryArea();
		assertEquals(result.size(), 3);
		cacheService.removeFromCache(AreaService.AREALISTKEY);
		result = areaService.queryArea();
	}

}
