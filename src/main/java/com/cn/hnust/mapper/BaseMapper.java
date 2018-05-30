package com.cn.hnust.mapper;

import java.util.List;

public interface BaseMapper<T> {

	/**
	 * 查询（根据主键ID查询）
	 **/
	T selectByPrimaryKey(String id);
	
	/**
	 * 查询所有数据
	 **/
	List<T> selectAll();
	
	/**
	 * 查询所有数据（匹配有值的字段）
	 **/
	List<T> selectBySelected(T record);
	
	/**
	 * 删除（根据主键ID删除）
	 **/
	int deleteByPrimaryKey(String id);

	/**
	 * 添加
	 **/
	int insert(T record);

	/**
	 * 添加 （匹配有值的字段）
	 **/
	int insertSelective(T record);

	/**
	 * 修改 （匹配有值的字段）
	 **/
	int updateByPrimaryKeySelective(T record);

	/**
	 * 修改（根据主键ID修改）
	 **/
	int updateByPrimaryKey(T record);
	
	/**
	 * 新增或修改（根据主键ID修改）
	 **/
	int replaceInsert(T record);
	
	/**
	 * 批量insert
	 **/
	int insertBatch(List<T> Crdinfo);

}
