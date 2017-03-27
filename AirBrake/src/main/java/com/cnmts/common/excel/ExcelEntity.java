package com.cnmts.common.excel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 将Excel文件读入到一个List集合中，必须给出“属性”(fields)，会按照给定的属性列表遍历该属性<br />
 * 然后调用该属性的set方法赋值 ， 并添加到List集合中 <br />
 * 注意:给出的属性应和Excel文件的列一一对应，否则会出现异常
 * 
 * @author 王璞
 * @date 2016年11月18日
 * @version 5.0 修改为直接访问字段，不再调用SET方法
 * @param <T>
 */
public class ExcelEntity<T> {
	private File excelFile;
	private InputStream inputStream;
	private int ignoreRow = 1;// 忽略的行数
	private Class<?> entityClass = null;
	private String[] header;// 标题头部
	private short[] formater;// 每列的格式
	private String sheetName = "sheet1";
	private String dateFormate = "yyyy-MM-dd";

	private Workbook workbook = null;

	public static final String HSSF = "application/vnd.ms-excel";
	public static final String XSSF = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

	/** 文件MIME类型 **/
	private String fileContentType = null;

	/**
	 * 构造方法 <br />
	 * 适用于导入
	 * 
	 * @param clazz
	 * @param file
	 * @param fields
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 */
	public ExcelEntity(Class<?> clazz, File file, String fileContentType) {
		this.entityClass = clazz;
		this.excelFile = file;
		this.fileContentType = fileContentType;
		try {
			if (file != null) {
				this.inputStream = new FileInputStream(this.excelFile);
				if (HSSF.equals(fileContentType)) {
					this.workbook = new HSSFWorkbook(this.inputStream);
				} else if (XSSF.equals(fileContentType)) {
					this.workbook = new XSSFWorkbook(this.inputStream);
				}
			} else {
				if (HSSF.equals(fileContentType)) {
					this.workbook = new HSSFWorkbook();
				} else if (XSSF.equals(fileContentType)) {
					this.workbook = new XSSFWorkbook();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设置工作薄名称
	 * 
	 * @param sheetName
	 */
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	/**
	 * 设置头部
	 * 
	 * @param header
	 */
	public void setHeader(String[] header) {
		this.header = header;
	}

	/**
	 * 转换为List<T> <br />
	 * 
	 * @author 王璞
	 * @date 2016年11月14日 下午4:58:45
	 * @param sheetNum
	 *            工作簿序号
	 * @param fields
	 * @throws FileNotFoundException
	 *             文件不存在
	 * @throws NotSupportFormatException
	 *             文件格式不支持
	 * @return
	 */
	public List<T> convertToList(int sheetNum, String... fields) throws FileNotFoundException, NotSupportFormatException {
		List<T> list = null;
		try {
			if (HSSF.equals(this.fileContentType)) {
				list = toList2003(sheetNum, fields);
			} else if (XSSF.equals(this.fileContentType)) {
				list = toList2007(sheetNum, fields);
			} else {
				throw new NotSupportFormatException("不支持的文件格式：" + fileContentType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	/**
	 * 设置忽略的行数，默认忽略第一行
	 * 
	 * @param ignoreRow
	 *            要忽略的行数，默认忽略第一行
	 */
	public void setIgnoreRow(int ignoreRow) {
		this.ignoreRow = ignoreRow;
	}

	/**
	 * Excel 转 List<br />
	 * 适用于2003版本
	 * 
	 * @author 王璞
	 * @date 2014-05-03
	 * @param sheetNum
	 * @param fields
	 * @update 王璞@2017-01-05，fields修改为局部变量，增加sheetNum可选择工作簿
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<T> toList2003(int sheetNum, String... fields) {
		HSSFSheet sheet = ((HSSFWorkbook) workbook).getSheetAt(sheetNum);
		// 在Excel文档中，第一张工作表的缺省索引是0，
		// 用于存储目标对象，excel每一行都是一个实体对象
		List<T> list = new ArrayList<T>();
		// 创建泛类的实例对象
		// 读取左上端单元
		HSSFRow row = null;
		try {
			ExcelUtil excelUtil = new ExcelUtil(null, null);

			for (int i = ignoreRow; sheet.getRow(i) != null; i++) {
				// 指针指向第i行
				row = sheet.getRow(i);
				Object instance;

				instance = ConstructorUtils.invokeConstructor(this.entityClass, null);

				HSSFCell cell = null;
				for (int j = 0; j < fields.length; j++) {
					String field = fields[j];

					Field declaredField = FieldUtils.getField(entityClass, field, true);

					cell = row.getCell(j);
					if (cell == null) {
						continue;
					}
					// 单元格类型
					int cellType = cell.getCellType();
					if (cellType == Cell.CELL_TYPE_NUMERIC) {
						double value = row.getCell(j).getNumericCellValue();
						if (DateUtil.isCellDateFormatted(cell)) {
							// 日期
							Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
							FieldUtils.writeField(declaredField, instance, date, true);
							continue;
						}
						excelUtil.setValue(declaredField, instance, value);
					} else if (cellType == Cell.CELL_TYPE_STRING) {
						String value = row.getCell(j).getStringCellValue();
						excelUtil.setValue(declaredField, instance, value);
					} else if (cellType == Cell.CELL_TYPE_FORMULA) {
						// 公式型
						try {
							double value = cell.getNumericCellValue();
							excelUtil.setValue(declaredField, instance, value);
						} catch (IllegalStateException e) {
							String value = String.valueOf(cell.getRichStringCellValue());
							excelUtil.setValue(declaredField, instance, value);
						}
					}
				}
				list.add((T) instance);
			}
			workbook.close();

		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException | IOException e1) {
			e1.printStackTrace();
		}

		return list;

	}

	/**
	 * Excel 转 List<br />
	 * 适用于2007版本
	 * 
	 * @author 王璞
	 * @date 2014-05-03
	 * @param sheetNum
	 *            工作簿数
	 * @param fields
	 *            注入的字段
	 * @update 王璞@2017-01-05，fields修改为局部变量，增加sheetNum可选择工作簿
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<T> toList2007(int sheetNum, String... fields) {
		XSSFSheet sheet = ((XSSFWorkbook) workbook).getSheetAt(sheetNum);
		List<T> list = new ArrayList<T>();
		XSSFRow row = null;
		try {
			ExcelUtil excelUtil = new ExcelUtil(null, null);
			for (int i = ignoreRow; sheet.getRow(i) != null; i++) {
				// 指针指向第i行
				row = sheet.getRow(i);
				Object instance;

				instance = ConstructorUtils.invokeConstructor(this.entityClass, null);

				XSSFCell cell = null;
				for (int j = 0; j < fields.length; j++) {
					String fieldName = fields[j];

					Field field = FieldUtils.getField(entityClass, fieldName, true);

					cell = row.getCell(j);
					if (cell == null) {
						continue;
					}
					int cellType = cell.getCellType();
					if (cellType == Cell.CELL_TYPE_NUMERIC) {
						double value = row.getCell(j).getNumericCellValue();
						if (DateUtil.isCellDateFormatted(cell)) {
							// 日期
							Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
							FieldUtils.writeField(field, instance, date, true);
							continue;
						}
						excelUtil.setValue(field, instance, value);
					} else if (cellType == Cell.CELL_TYPE_STRING) {
						String value = row.getCell(j).getStringCellValue();
						excelUtil.setValue(field, instance, value);
					} else if (cellType == Cell.CELL_TYPE_FORMULA) {
						// 公式型
						try {
							double value = cell.getNumericCellValue();
							excelUtil.setValue(field, instance, value);
						} catch (IllegalStateException e) {
							String value = String.valueOf(cell.getRichStringCellValue());
							excelUtil.setValue(field, instance, value);
						}
					}
				}
				list.add((T) instance);
			}
			workbook.close();

		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException | IOException e1) {
			e1.printStackTrace();
		}

		return list;
	}

	public Workbook toExcelWithWorkBook(List<T> list, ExcelRow<T> excelRow) {
		ExcelUtil excelUtil = new ExcelUtil(formater, dateFormate);

		HSSFSheet sheet = ((HSSFWorkbook) workbook).createSheet(sheetName);
		// 输出表头
		int rowIndex = 0;// 行数偏移量
		if (header != null) {
			HSSFRow row = sheet.createRow(rowIndex);
			for (int i = 0; i < header.length; i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				String value = header[i];
				cell.setCellValue(value);
			}
			rowIndex++;
		}
		try {
			// 输出数据
			for (int i = 0; i < list.size(); i++) {
				HSSFRow row = sheet.createRow(i + rowIndex);
				T instance = list.get(i);

				List<ExcelRowFormat> excelRowFormats = excelRow.getRow(instance);

				for (int j = 0; j < excelRowFormats.size(); j++) {
					ExcelRowFormat excelRowFormat = excelRowFormats.get(j);

					Cell cell = row.createCell(j);

					if (excelRowFormat == null) {
						continue;
					}

					if (excelRowFormat.getObject() == null) {
						if (ExcelUtil.rowNum.equals(excelRowFormat.getPropertyName())) {
							String replace = excelRowFormat.getPropertyName().replace(ExcelUtil.rowNum, String.valueOf(i + 1));
							if (NumberUtils.isNumber(replace)) {
								excelUtil.setValue(workbook, cell, NumberUtils.toInt(replace), null);
							} else {
								excelUtil.setValue(workbook, cell, NumberUtils.toInt(replace), null);
							}
							continue;
						}

						// 根据属性名称，通过反射获取Field
						Object object = ExcelUtil.getFieldValue(instance, excelRowFormat.getPropertyName());

						if (object instanceof List) {
							List<?> childList = (List<?>) object;
							if (childList.size() - 1 >= excelRowFormat.getIndex()) {
								Object indexObject = childList.get(excelRowFormat.getIndex());
								Field childEntityField = indexObject.getClass().getDeclaredField(excelRowFormat.getChildPropertyName());
								if (!childEntityField.isAccessible()) {
									childEntityField.setAccessible(true);
								}
								Object childObject;

								childObject = childEntityField.get(indexObject);

								excelUtil.setValue(workbook, cell, childObject, excelRowFormat.getStrFormat());
								continue;
							}
						} else {
							excelUtil.setValue(workbook, cell, object, excelRowFormat.getStrFormat());
							continue;
						}
					}
					excelUtil.setValue(workbook, cell, excelRowFormat.getObject(), null);
				}
			}
			for (int i = 0; i < header.length; i++) {
				sheet.autoSizeColumn(i);
			}
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		return this.workbook;
	}

	/**
	 * List 转Excel
	 * 
	 * haveIndex 第一列是否有序号
	 * 
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public ByteArrayOutputStream toExcelWithBAOS(List<T> list, ExcelRow<T> excelRow, Workbook workbook) {
		toExcelWithWorkBook(list, excelRow);
		return ExcelUtil.workBook2BAOS(workbook);
	}

	/**
	 * 设置单元格格式化
	 * 
	 * @author 王璞
	 * @date 2016年11月16日 下午4:44:35
	 * @param formater
	 */
	public void setFormater(short[] formater) {
		this.formater = formater;
	}

	public void setDateFormat(String format) {
		this.dateFormate = format;
	}

	/**
	 * 5.0新增构造一行数据
	 * 
	 * @author 王璞
	 * @date 2016年11月18日 上午11:09:21
	 * @version 1.0
	 * @param <T>
	 */
	public interface ExcelRow<T> {
		public List<ExcelRowFormat> getRow(T t);
	}
}
