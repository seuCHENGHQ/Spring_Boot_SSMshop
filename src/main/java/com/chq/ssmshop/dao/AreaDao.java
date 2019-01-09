package com.chq.ssmshop.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.chq.ssmshop.entity.Area;

@Repository
public interface AreaDao {
	
	public List<Area> listArea();
	
}
