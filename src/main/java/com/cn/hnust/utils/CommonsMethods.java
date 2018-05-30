package com.cn.hnust.utils;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

public class CommonsMethods {
	/**
	 * @Title: getFileCharset
	 * @Description: 判断文件的编码格式
	 * @param filePath
	 *            文件绝对路径
	 * @return String
	 * @author 
	 * @date 2015年12月26日
	 */
	public static String getFileCharset(String filePath) {

		File file = new File(filePath);

		if (!file.exists()) {
			System.out.println("File not found.");
		}
		// 默认编码格式为GBK
		String charset = "GBK";

		FileInputStream is = null;
		BufferedInputStream bis = null;

		try {
			byte[] first3Bytes = new byte[3];

			boolean checked = false;

			is = new FileInputStream(file);

			bis = new BufferedInputStream(is);

			bis.mark(0);

			int read = bis.read(first3Bytes, 0, 3);

			if (-1 == read) {
				charset = "GBK";
			} else if (first3Bytes[0] == (byte) 0xFF
					&& first3Bytes[1] == (byte) 0xFE) {
				charset = "UTF-16LE";
				checked = true;
			} else if (first3Bytes[0] == (byte) 0xFE
					&& first3Bytes[1] == (byte) 0xFF) {
				charset = "UTF-16BE";
				checked = true;
			} else if (first3Bytes[0] == (byte) 0xEF
					&& first3Bytes[1] == (byte) 0xBB
					&& first3Bytes[2] == (byte) 0xBF) {
				charset = "UTF-8";
				checked = true;
			}
			bis.reset();

			if (!checked) {

				int loc = 0;

				while ((read = bis.read()) != -1) {

					loc++;

					if (read >= 0xF0) {
						break;
					}

					if (0x80 <= read && read <= 0xBF) {
						// 单独出现BF以下的,也算GBK
						break;
					}

					if (0x80 <= read && read <= 0xDF) {
						read = bis.read();
						if (0x80 <= read && read <= 0xBF) {
							// GBK
							continue;
						} else {
							break;
						}
					} else if (0xE0 <= read && read <= 0xEF) {
						read = bis.read();
						if (0x80 <= read && read <= 0xBF) {
							read = bis.read();
							if (0x80 <= read && read <= 0xBF) {
								charset = "UTF-8";
								break;
							} else {
								break;
							}
						} else {
							break;
						}
					}
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(is);
			IOUtils.closeQuietly(is);
		}

		return charset;
	}
	
	/**
	 * 逐行读取文件
	 * @param fileName
	 * @return
	 */
	public static List<String> readMsg(String fileName){
		List<String> list = new ArrayList<>();
		String charset = getFileCharset(fileName);  
		try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName),Charset.forName(charset))) {
			list = br.lines().collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 合并List中io1开始到io2结束之间的值为新的List
	 * @param list
	 * @param io1
	 * @param io2
	 * @return
	 */
	public static List<String> pListString(List<String> list,String io1,String io2){
		List<String> myList=new ArrayList<>();
		boolean flg=false;
		StringBuilder myStr=new StringBuilder();
		for(String str:list){
			if(str.indexOf(io1)>0){
				flg=true;
			}
			if(flg){
				myStr.append(str);
			}else{
				if(StringUtils.isNotBlank(myStr)){
					myList.add(myStr.toString());
					myStr=new StringBuilder();
				}
			}
			if(str.indexOf(io2)>0){
				flg=false;
			}
		}
		return myList;
	}
	
	/**
	 * 合并List中String中io1开始到io2结束之间的值为新的List
	 * @param list
	 * @param io1
	 * @param io2
	 * @return
	 */
	public static List<String> pString(List<String> list,String io1,String io2,String io3){
		List<String> myList=new ArrayList<>();
		for(String str:list){
			int i1=str.indexOf(io1)+io1.length();
			int i2=str.indexOf(io2);
			if(i2==-1){
				i2=str.indexOf(io3);
				if(i2==-1){
					i2=str.length();
				}
			}
			String myStr=str.substring(i1,i2);
			myList.add(myStr);
		}
		return myList;
	}
	
	public static void showList(List<String> list){
		for(String str:list){
			System.out.println(str);
		}
	}
}