package com.chq.ssmshop.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.chq.ssmshop.entity.HeadLine;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HeadLineDaoTest{
	
	@Autowired
	private HeadLineDao headLineDao;
	
	@Test
	public void queryHeadLineTest() {
		List<HeadLine> headLineList = headLineDao.queryHeadLine(new HeadLine());
		assertEquals(0, headLineList.size());
	}
}
