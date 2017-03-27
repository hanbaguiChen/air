package com.cnmts.common.util;

import java.io.File;
import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;

public class ImageUtil {

	/** 压缩后图片大小 单位字节 */
	private final static int defaultQuality = 10000;

	public static void genThumbByDefault(File srcFile, File desFile) {
		long length = srcFile.length();
		float quality = (float) defaultQuality / (float) length;
		genThumb(srcFile, desFile, 200, 200, quality);
	}

	/**
	 * 压缩图片
	 * 
	 * @author 王璞
	 * @date 2016年11月29日 上午9:32:15
	 * @param srcFile
	 *            源文件
	 * @param desFile
	 *            目标文件
	 * @param width
	 *            宽度
	 * @param height
	 *            高度
	 * @param quality
	 *            质量
	 */
	public static void genThumb(File srcFile, File desFile, Integer width, Integer height, Float quality) {
		Builder<File> builder = Thumbnails.of(srcFile);
		if (width != null && height != null) {
			builder.size(width, height);
		}
		if(quality!=null && quality>0f && quality<1f){
			builder.outputQuality(quality);
		}else{
			builder.outputQuality(0.2f);
		}
		try {
			builder.toFile(desFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
