package com.cnmts.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cnmts.common.bean.Dictionary;

public interface DictMapper {

	List<Dictionary> getDictByDictType(@Param("typeName") String typeName);

	public List<Dictionary> getDictTypes(Dictionary dictionary);

	public Dictionary getDictById(int dictId);

	public Integer checkDictKey(Dictionary dictionary);

	public void addDict(Dictionary dictionary);

	public void updateDict(Dictionary dictionary);

	public void deleteDict(Dictionary dictionary);

	public void updateOtherDict(Dictionary dictionary);

	public void moveUpDict(Dictionary dictionary);

	public void moveDownDict(Dictionary dictionary);

}
