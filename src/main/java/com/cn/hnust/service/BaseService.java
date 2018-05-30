package com.cn.hnust.service;

import java.util.List;

import com.cn.hnust.mapper.BaseMapper;

public abstract class BaseService<T> {
	/**
	 * 需要被子类覆盖
	 * 
	 * @return
	 */
	protected abstract BaseMapper<T> getEntityMapper();

	/**
	 * 查询（根据主键ID查询）
	 **/
	public T selectByPrimaryKey(String id) {
		return getEntityMapper().selectByPrimaryKey(id);
	};
	
	/**
	 * 查询所有数据
	 **/
	public List<T> selectAll() {
		return getEntityMapper().selectAll();
	};
	
	/**
	 * 查询所有数据（匹配有值的字段）
	 **/
	public List<T> selectBySelected(T record) {
		return getEntityMapper().selectBySelected(record);
	};
	
	/**
	 * 删除（根据主键ID删除）
	 **/
	public int deleteByPrimaryKey(String id) {
		return getEntityMapper().deleteByPrimaryKey(id);
	};

	/**
	 * 删除（根据主键ID删除）
	 * @throws InterruptedException 
	 **/
	public int deleteByPrimaryKey(String[] ids) throws InterruptedException {
//		ExecutorService service = Executors.newFixedThreadPool(25);
//		for (String id:ids) {
//			Runnable run = new Runnable() {
//				@Override
//				public void run() {
//					deleteByPrimaryKey(id);
//				}
//
//			};
//			service.execute(run);
//		}
//		// 关闭启动线程
//		service.shutdown();
//		// 等待子线程结束，再继续执行下面的代码
//		service.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
		int num=0;
		for(String id:ids){
			num=num+this.deleteByPrimaryKey(id);
		}
		return num;
	};

	/**
	 * 添加
	 **/
	public int insert(T record) {
		return getEntityMapper().insert(record);
	};

	/**
	 * 添加 （匹配有值的字段）
	 **/
	public int insertSelective(T record) {
		return getEntityMapper().insertSelective(record);
	};

	/**
	 * 修改 （匹配有值的字段）
	 **/
	public int updateByPrimaryKeySelective(T record) {
		return getEntityMapper().updateByPrimaryKeySelective(record);
	};

	/**
	 * 修改（根据主键ID修改）
	 **/
	public int updateByPrimaryKey(T record) {
		return getEntityMapper().updateByPrimaryKey(record);
	};

	/**
	 * 新增或修改（根据主键ID修改）
	 **/
	public int replaceInsert(T record) {
		return getEntityMapper().replaceInsert(record);
	};
	
	/**
	 * 批量insert
	 **/
	public int insertBatch(List<T> record){
		return getEntityMapper().insertBatch(record);
	}
}
