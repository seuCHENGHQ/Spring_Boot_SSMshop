package com.chq.ssmshop.service;

import java.util.List;

import com.chq.ssmshop.entity.Area;

public interface AreaService {

	/**
	 * 这个是Redis中的key，Redis保存数据是以键值对的形式保存的，这就是其中的键，值是所有的arealist
	 */
	public static final String AREALISTKEY = "arealist";

	public List<Area> queryArea();
}
