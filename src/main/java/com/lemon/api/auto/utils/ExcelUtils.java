package com.lemon.api.auto.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.StyledEditorKit.ForegroundAction;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.lemon.api.auto.pojo.ApiInfo;
import com.lemon.api.auto.pojo.Apidetail;
import com.lemon.api.auto.pojo.ExcelObject;

public class ExcelUtils {
	/**
	 * 将所有的测试用例写在一个excel中,读取该测试用例
	 * @param excelPath
	 * @param sheetNum，sheet编号，从1开始，传1表示第一个sheet，即索引为0的sheet
	 * @param clazz  pojo类的字节码对象
	 */

	public static List<? extends ExcelObject> readAllCaseExcel(String excelPath, int sheetNum, Class<? extends ExcelObject> clazz) {
		List<ExcelObject> objoList = null;
		try {
			InputStream inp = ExcelUtils.class.getResourceAsStream(excelPath);
			// 获得工作薄对象
			Workbook workbook = WorkbookFactory.create(inp);
			// 获得第一个sheet
			Sheet sheet = workbook.getSheetAt(sheetNum - 1);
			// 通过遍历拿到所有的行->通过遍历拿到每一行的列
			// 获得最后的行号(行的索引，从0开始)
			int lastRowNum = sheet.getLastRowNum();

			// 获得第一行
			Row fistrow = sheet.getRow(0);
			// 获得最大的列数(从1开始)
			int lastCellNum = fistrow.getLastCellNum();
			// 创建一个数组，保存表头信息
			String[] columnArray = new String[lastCellNum];
			// 循环遍历第一行，获得表头
			for (int k = 0; k < lastCellNum; k++) {
				// 若存在值为空，则设置该值为空
				Cell cell = fistrow.getCell(k, MissingCellPolicy.CREATE_NULL_AS_BLANK);
				// 设置列的类型
				cell.setCellType(CellType.STRING);
				// 获得该列的值
				String columnName = cell.getStringCellValue();
				columnArray[k] = columnName;
			}

			// 创建一个Object类型的二维数组，保存从excel解析出来的行列数据
			objoList = new ArrayList<>();
			//遍历每一行（i就相当于行的索引，第一行不要）
			for (int i = 1; i <= lastRowNum; i++) {
				// 获得索引对应的行
				Row row = sheet.getRow(i);

				// 通过字节码对象实例化一个对象
				ExcelObject obj = clazz.newInstance();
				obj.setRowNum(i+1);
				for (int j = 0; j < lastCellNum; j++) {
					// 若存在值为空，则设置该值为空
					Cell cell = row.getCell(j, MissingCellPolicy.CREATE_NULL_AS_BLANK);
					// 设置列的类型
					cell.setCellType(CellType.STRING);
					// 获得该列的值
					String cellValue = cell.getStringCellValue();
					// 得到setter方法的名称
					String setMethodName = "set" + columnArray[j].substring(0, columnArray[j].indexOf("("));
					// 得到setter方法
					Method setMethod = clazz.getMethod(setMethodName, String.class);
					// 反射调用该方法
					setMethod.invoke(obj, cellValue);

				}
				// 每遍历一行，要把改行对应的以为数组添加进去
				objoList.add(obj);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objoList;
	}

	
	
	
	
	@Deprecated // 过期了，下面那个方法也可以，但是这种方法的话Excel就不能动了，比如增一行或者别的
//	表结构一旦发生变化，下面的方法就不能使用了
	
	public static Object[][] readAllCaseExcel(String excelPath, int sheetNum) {
		Object[][] datas = null;
		try {
			InputStream inp = ExcelUtils.class.getResourceAsStream(excelPath);
			// 获得工作薄对象
			Workbook workbook = WorkbookFactory.create(inp);
			// 获得第一个sheet
			Sheet sheet = workbook.getSheetAt(sheetNum - 1);
			// 通过遍历拿到所有的行->通过遍历拿到每一行的列
			// 获得最后的行号
			// 获得最后的行号(行的索引，从0开始)
			int lastRowNum = sheet.getLastRowNum();

			// 获得第一行
			Row fistrow = sheet.getRow(0);
			// 获得最大的列数(从1开始)
			int lastCellNum = fistrow.getLastCellNum();

			// 创建一个Object类型的二维数组，保存从excel解析出来的行列数据
			datas = new Object[lastRowNum][];
			for (int i = 1; i <= lastRowNum; i++) {
				// 获得索引对应的行
				Row row = sheet.getRow(i);
				// 创建一个一维数组，保存该行的所有列信息
				Object[] cellArry = new Object[lastCellNum];
				for (int j = 0; j < lastCellNum; j++) {
					// 若存在值为空，则设置该值为空
					Cell cell = row.getCell(j, MissingCellPolicy.CREATE_NULL_AS_BLANK);
					// 设置列的类型
					cell.setCellType(CellType.STRING);
					// 获得该列的值
					String cellValue = cell.getStringCellValue();
					cellArry[j] = cellValue;

				}
				// 每遍历一行，要把改行对应的以为数组添加进去
				datas[i - 1] = cellArry;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return datas;
	}

	/**
	 * 1个excel中保存一个类型的测试用例
	 * 
	 * @param excelPath
	 *            excel的路径
	 * @return
	 */
	@Deprecated
	public static Object[][] readExcel(String excelPath) {
		Object[][] datas = null;
		try {
			InputStream inp = ExcelUtils.class.getResourceAsStream(excelPath);
			// 获得工作薄对象
			Workbook workbook = WorkbookFactory.create(inp);
			// 获得第一个sheet
			Sheet sheet = workbook.getSheetAt(0);
			// 通过遍历拿到所有的行->通过遍历拿到每一行的列
			// 获得最后的行号
			// 获得最后的行号(行的索引，从0开始)
			int lastRowNum = sheet.getLastRowNum();

			// 获得第一行
			Row fistrow = sheet.getRow(0);
			// 获得最大的列数(从1开始)
			int lastCellNum = fistrow.getLastCellNum();

			// 创建一个Object类型的二维数组，保存从excel解析出来的行列数据
			datas = new Object[lastRowNum][];
			for (int i = 1; i <= lastRowNum; i++) {
				// 获得索引对应的行
				Row row = sheet.getRow(i);

				// 创建一个一维数组，保存改行的所有列信息
				Object[] cellDatas = new Object[lastCellNum];
				for (int j = 0; j < lastCellNum; j++) {
					// 若存在值为空，则设置该值为空
					Cell cell = row.getCell(j, MissingCellPolicy.CREATE_NULL_AS_BLANK);
					// 设置列的类型
					cell.setCellType(CellType.STRING);
					// 获得该列的值
					String cellValue = cell.getStringCellValue();
					cellDatas[j] = cellValue;
				}
				// 每遍历一行，要把改行对应的以为数组添加进去
				datas[i - 1] = cellDatas;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return datas;
	}

	

	public static void main(String[] args) {
		
		
		
		List<Apidetail> objList = (List<Apidetail>)readAllCaseExcel("/api_test_case_01.xlsx",2,Apidetail.class);
		for (Object obj : objList) {
			System.out.println(obj);
	}
		
		
		
		
}
			
			//路径写回
//			writexcel("/api_test_case_01.xlsx", 1, "1", 5,"");
		



	

/**
 * 写回数据到Excel
 *性能问题：
 *1、如果写1000次数据，就要IO操作1000次
 *----》只读写一次就OK了（把所有要写的相关信息先收集起来，所有测试用例执行完毕后在写）
 *2、如果caseID对应的行比较靠后，前面遍历次数会非常多
 *---》告诉我一个caseID，就能拿到对应的行（rownum）
 * @param excelPath 路径
 * @param sheetNum 第几个sheet
 * @param caseId 在哪个行写
 * @param cellNum 在哪列写
 * @param result 结果
 * 
 */

	public static void writexcel(String excelPath, int sheetNum, String caseId, int cellNum, String result) {
		InputStream inp = null;
		Workbook workbook =null;
		OutputStream outputstream = null;
		
		try {
			//创建一个工作簿对象
//			inp = ExcelUtils.class.getResourceAsStream(excelPath);
			inp = new FileInputStream(new File(excelPath));
			 workbook = WorkbookFactory.create(inp);
			//获得对应编号的sheet
			Sheet sheet = workbook.getSheetAt(sheetNum-1);
			//获得最大的行号
			int lastRowNum =sheet.getLastRowNum();
			for (int i = 1;i<=lastRowNum;i++) {
				//拿到当前行
				Row row=sheet.getRow(i);
				//拿到当前行的第一列
				Cell cell =row.getCell(0, MissingCellPolicy.CREATE_NULL_AS_BLANK);
				cell.setCellType(CellType.STRING);
				//获得当前列的数据
				String firstCellValue= cell.getStringCellValue();
				//判断第一列的数据是不是caseId
				//如果caseID等于第一列数据
			if(caseId.equals(firstCellValue)) {
				Cell cellToWrite=row.getCell(cellNum-1, MissingCellPolicy.CREATE_NULL_AS_BLANK);
				cellToWrite.setCellType(CellType.STRING);
				cellToWrite.setCellValue(result);
				//已经匹配上了，就跳出循环
				break;
				
				
			}
				
			}
			
			outputstream = new FileOutputStream(new File(excelPath));
			workbook.write(outputstream);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (outputstream!=null) {
				try {
					outputstream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} 
			if (workbook!=null) {
				try {
					workbook.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (inp!=null) {
					try {
						inp.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
			} 
		}
		
	}

}
