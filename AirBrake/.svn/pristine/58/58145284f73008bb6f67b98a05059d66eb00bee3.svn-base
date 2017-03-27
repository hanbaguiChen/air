package com.cnmts.common.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cnmts.common.bean.Dictionary;
import com.cnmts.common.bean.JsonResult;
import com.cnmts.common.service.DictService;

/**
 * 
 * 字典表 接口
 * 
 * @author xiaoming
 * 
 * @time 2016年11月25日 下午1:35:36
 */
@Controller
@RequestMapping(value = "/dict")
public class DictController {

	@Autowired
	private DictService dictService;

	/**
	 * 
	 * 获取查询后的所有字典表
	 * 
	 * @param dictionary
	 * @return
	 */
	@RequestMapping(value = { "/getDictTypes" })
	@ResponseBody
	public JsonResult<List<Dictionary>> getDictTypes(Dictionary dictionary) {
		JsonResult<List<Dictionary>> jsonResult = new JsonResult<List<Dictionary>>();
		jsonResult.setObj(dictService.getDictTypes(dictionary));
		return jsonResult;
	}

	/**
	 * 
	 * 查询类型下的所有字典
	 * 
	 * @param dictType
	 * @return
	 */
	@RequestMapping(value = { "/getDictByDictType" })
	@ResponseBody
	public JsonResult<List<Dictionary>> getDictByDictType(String dictType) {
		JsonResult<List<Dictionary>> jsonResult = new JsonResult<List<Dictionary>>();
		jsonResult.setObj(dictService.getDictByDictType(dictType));
		return jsonResult;
	}

	/**
	 * 
	 * 根据id查询dict
	 * 
	 * @param dictId
	 * @return
	 */
	@RequestMapping(value = { "/getDictById" })
	@ResponseBody
	public JsonResult<Dictionary> getDictById(int dictId) {
		JsonResult<Dictionary> jsonResult = new JsonResult<Dictionary>();
		jsonResult.setObj(dictService.getDictById(dictId));
		return jsonResult;
	}

	/**
	 * 操作dict 新增与修改
	 * 
	 * @param dictionary
	 * @return
	 */
	@RequestMapping(value = { "/operateDict" })
	@ResponseBody
	public JsonResult<Boolean> operateDict(Dictionary dictionary) {
		return dictService.operateDict(dictionary);
	}

	/**
	 * 删除dict
	 * 
	 * @param dictId
	 * @return
	 */
	@RequestMapping(value = { "/deleteDict" })
	@ResponseBody
	public JsonResult<Boolean> deleteDict(int dictId) {
		return dictService.deleteDict(dictId);
	}

	/**
	 * 移动dict
	 * 
	 * @param dictId
	 * @return
	 */
	@RequestMapping(value = { "/moveDict" })
	@ResponseBody
	public JsonResult<Boolean> moveDict(int dictId, int model) {
		return dictService.moveDict(dictId, model);
	}

}
