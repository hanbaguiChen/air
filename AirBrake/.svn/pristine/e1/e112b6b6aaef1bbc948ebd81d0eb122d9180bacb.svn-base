package com.cnmts.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnmts.common.bean.DictTypeEnum;
import com.cnmts.common.bean.Dictionary;
import com.cnmts.common.bean.JsonResult;
import com.cnmts.common.bean.ResultType;
import com.cnmts.common.dao.DictMapper;

/**
 * 字典
 * 
 * @author 王璞
 * @date 2016年11月24日 下午2:43:58
 * @version 1.0
 */
@Service
public class DictService {

	@Autowired
	private DictMapper dictMapper;

	public List<Dictionary> getDictByDictType(DictTypeEnum dictType) {
		return dictMapper.getDictByDictType(dictType.getTypeName());
	}
	
	public Dictionary getDictById(int dictId){
		return dictMapper.getDictById(dictId);
	}
	
	public List<Dictionary> getDictByDictType(String type) {
		return dictMapper.getDictByDictType(type);
	}
	
	public List<Dictionary> getDictTypes(Dictionary dictionary){
		return dictMapper.getDictTypes(dictionary);
	}
	
	public JsonResult<Boolean> operateDict(Dictionary dictionary){
		JsonResult<Boolean> jsonResult = new JsonResult<Boolean>();
		//校验key是否可用
		if(dictMapper.checkDictKey(dictionary)>0){
			jsonResult.setCode(ResultType.DICT_ALREADY_EXIST);
			jsonResult.setObj(false);
			return jsonResult;
		}
		if(dictionary.getDictId()>0){
			//编辑
			dictMapper.updateDict(dictionary);
		}else{
			//新增
			dictMapper.addDict(dictionary);
		}
		jsonResult.setObj(true);
		return jsonResult;
	}
	
	public JsonResult<Boolean> deleteDict(int dictId){
		JsonResult<Boolean> jsonResult = new JsonResult<Boolean>();
		Dictionary dictionary = dictMapper.getDictById(dictId);
		//校验是否只有一个字典，只有一个字典不允许删除
		List<Dictionary> list = dictMapper.getDictByDictType(dictionary.getDictType());
		if(list == null || list.size()<2){
			jsonResult.setCode(ResultType.DICT_NOT_ALLOW_NULL);
			jsonResult.setObj(false);
			return jsonResult;
		}
		//先删除本条记录
		dictMapper.deleteDict(dictionary);
		
		//再修改其他的顺序
		dictMapper.updateOtherDict(dictionary);
		
		jsonResult.setObj(true);
		return jsonResult;
	}
	public JsonResult<Boolean> moveDict(int dictId,int model){
		JsonResult<Boolean> jsonResult = new JsonResult<Boolean>();
		Dictionary dictionary = dictMapper.getDictById(dictId);
		List<Dictionary> list = dictMapper.getDictByDictType(dictionary.getDictType());
		if(model == 0){
			//上升
			if(list.get(0).getDictId() == dictionary.getDictId()){
				//判断是不是第一个
				jsonResult.setCode(ResultType.DICT_MOVE_WRONG);
				jsonResult.setObj(false);
				return jsonResult;
			}else{
				for(int i=0;i<list.size();i++){
					if(list.get(i).getDictId() == dictionary.getDictId()){
						//找到本条记录  执行上升
						dictMapper.moveUpDict(dictionary);
						//上一条记录 执行下移
						dictMapper.moveDownDict(list.get(i-1));
					}
				}
			}
		}else{
			if(list.get(list.size()-1).getDictId() == dictionary.getDictId()){
			    //判断是不是最后一个
				jsonResult.setCode(ResultType.DICT_MOVE_WRONG);
				jsonResult.setObj(false);
				return jsonResult;
			}else{
				for(int i=0;i<list.size();i++){
					if(list.get(i).getDictId() == dictionary.getDictId()){
						//找到本条记录  执行下移
						dictMapper.moveDownDict(dictionary);
						//上一条记录 执行上升
						dictMapper.moveUpDict(list.get(i+1));
					}
				}
			}
		}
		
		
		jsonResult.setObj(true);
		return jsonResult;
	}
}
