package com.cn.encryption.demoOut;

import java.io.File;
import java.io.FileOutputStream;

import com.converter.pack.JBigInflateConverter;

public class MyTest {
public static void main(String[] args) {
		
		String data="00000100000000F000000070000000030800031CFF02FF02FF02FF02FF02FF02FF02FF02FF02FF02FF02FF02FF02FF02FF02FF02FF02F5763D3D79BF9AC975661EE11E36E9D791CB2AFF0264DDA5FBF610DAA9B05E02075BCBD0FF021466D49C52128F98B905CDFD3D70FF027BC1545D798DAEB6610D80A188FF02E0FF02FF02FF02FF02FF02FF02FF02FF02FF02FF02FF02FF02FF02FF02FF02FF02FF02null";//签名域
		createImg(data, "d:/aaa/ccc/eee");
	}
	
	public  static  void createImg(String data, String fileName) {
		
		try {
		File file = new File(fileName + ".jbig");
		FileOutputStream fis = new FileOutputStream(file);
		fis.write(hex2byte(data));//16进制转为byte数组,网上方法很多
		fis.flush();
		fis.close();

		JBigInflateConverter jic = new JBigInflateConverter();//调用jar包里面的方法
		 jic.DoConvert(fileName+".jbig", fileName+".bmp");
		} catch (Exception e) {
		e.printStackTrace();
		
		}
	}
	
	
	 public static byte[] hex2byte(String src){  
	        byte[] res = new byte[src.length()/2];        
	        char[] chs = src.toCharArray();  
	        for(int i=0,c=0; i<chs.length; i+=2,c++){  
	            res[c] = (byte) (Integer.parseInt(new String(chs,i,2), 16));  
	        }  
	          
	        return res;  
	    }  
}
