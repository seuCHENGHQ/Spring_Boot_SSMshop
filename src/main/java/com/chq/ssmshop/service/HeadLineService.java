package com.chq.ssmshop.service;

import java.io.IOException;
import java.util.List;

import com.chq.ssmshop.entity.HeadLine;

public interface HeadLineService {
	public static final String KEYSTRING = "headlinelist";

	/**
	 * 使用headLineCondition作为查询条件，查询符合条件的头条列表
	 * 
	 * @param headLineCondition
	 * @return
	 * @throws IOException
	 */
	List<HeadLine> getHeadLineList(HeadLine headLineCondition);
}
