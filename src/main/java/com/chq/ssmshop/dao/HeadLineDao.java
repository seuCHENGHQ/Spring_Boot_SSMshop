package com.chq.ssmshop.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chq.ssmshop.entity.HeadLine;

public interface HeadLineDao {
	List<HeadLine> queryHeadLine(@Param("headLineCondition") HeadLine headLineCondition);
}
