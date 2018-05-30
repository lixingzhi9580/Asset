package com.cn.encryption.utilsOut;

public class Util {
	//转换成十六进制字符串
    public static String byte2hex(byte[] b) {
        String hs="";
        String stmp="";
        for (int n=0;n<b.length;n++) {
            stmp=(java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length()==1) 
            	hs=hs+"0"+stmp;
            else 
            	hs=hs+stmp;
        }
        return hs.toUpperCase();
    }
}
