package com.cnmts.common.bean;

/**
 * Http接口
 * 
 * @author 王璞
 * @date 2016年11月14日 上午10:29:19
 * @version 1.0
 */
public class HttpInterface {

	private static final String ASSET = "/asset";
	/** 得到查询条件 查询全部 写入地图里面 */
	public static final String ASSET_GET_ALL_ASSET_BYSEARCH = ASSET + "/getAllAssetBySearch";
	/** 删除asset */
	public static final String ASSET_DELETE_ASSET = ASSET + "/deleteAsset";
	/** 添加一处房屋资产 */
	public static final String ASSET_ADD_ASSET = ASSET + "/addAsset";
	/** 更新Asset */
	public static final String ASSET_UPDATE_ASSET = ASSET + "/updateAsset";
	/** 分页获取房屋资产 */
	public static final String ASSET_GET_ASSETLIST_BYPAGE = ASSET + "/getAssetListByPage";
	/** 查询房屋资产详细信息 */
	public static final String ASSET_GET_ASSET_BYASSETID = ASSET + "/getAssetByAssetId";
	/** 删除房屋资产文件 */
	public static final String ASSET_DELETE_ASSETFILE_BYASSETFILEID = ASSET + "/deleteAssetFileByAssetFileId";
	
	public static final String ASSET_EXPORT_EXCEL = ASSET + "/exportExcel";
}
