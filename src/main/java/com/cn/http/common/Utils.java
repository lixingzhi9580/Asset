package com.cn.http.common;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;

/** 
 * 
 * @author arron
 * @date 2015年11月10日 下午12:49:26 
 * @version 1.0 
 */
public class Utils {
	
	//传入参数特定类型
	public static final String ENTITY_STRING="$ENTITY_STRING$";
	public static final String ENTITY_FILE="$ENTITY_FILEE$";
	public static final String ENTITY_BYTES="$ENTITY_BYTES$";
	public static final String ENTITY_INPUTSTREAM="$ENTITY_INPUTSTREAM$";
	public static final String ENTITY_SERIALIZABLE="$ENTITY_SERIALIZABLE$";
	private static final List<String> SPECIAL_ENTITIY = Arrays.asList(ENTITY_STRING, ENTITY_BYTES, ENTITY_FILE, ENTITY_INPUTSTREAM, ENTITY_SERIALIZABLE);

	/**
	 * 检测url是否含有参数，如果有，则把参数加到参数列表中
	 * 
	 * @param url					资源地址
	 * @param nvps				参数列表
	 * @return	返回去掉参数的url
	 * @throws UnsupportedEncodingException 
	 */
	public static String checkHasParas(String url, List<NameValuePair> nvps, String encoding) throws UnsupportedEncodingException {
		// 检测url中是否存在参数
		if (url.contains("?") && url.indexOf("?") < url.indexOf("=")) {
			Map<String, Object> map = buildParas(url.substring(url
					.indexOf("?") + 1));
			map2List(nvps, map, encoding);
			url = url.substring(0, url.indexOf("?"));
		}
		return url;
	}

	/**
	 * 参数转换，将map中的参数，转到参数列表中
	 * 
	 * @param nvps				参数列表
	 * @param map				参数列表（map）
	 * @throws UnsupportedEncodingException 
	 */
	public static HttpEntity map2List(List<NameValuePair> nvps, Map<String, Object> map, String encoding) throws UnsupportedEncodingException {
		HttpEntity entity = null;
		if(map!=null && map.size()>0){
			boolean isSpecial = false;
			// 拼接参数
			for (Entry<String, Object> entry : map.entrySet()) {
				if(SPECIAL_ENTITIY.contains(entry.getKey())){//判断是否在之中
					isSpecial = true;
					if(ENTITY_STRING.equals(entry.getKey())){//string
						entity = new StringEntity(String.valueOf(entry.getValue()), encoding);
						break;
					}else if(ENTITY_BYTES.equals(entry.getKey())){//file
						entity = new ByteArrayEntity((byte[])entry.getValue());
						break;
					}else if(ENTITY_FILE.equals(entry.getKey())){//file
						//entity = new FileEntity(file)
						break;
					}else if(ENTITY_INPUTSTREAM.equals(entry.getKey())){//inputstream
//						entity = new InputStreamEntity();
						break;
					}else if(ENTITY_SERIALIZABLE.equals(entry.getKey())){//serializeable
//						entity = new SerializableEntity()
						break;
					}else {
						nvps.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
					}
				}else{
					nvps.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
				}
			}
			if(!isSpecial) {
				entity = new UrlEncodedFormEntity(nvps, encoding);
			}
		}
		return entity;
	}
	
	
	/**
	 * 生成参数
	 * 参数格式“k1=v1&k2=v2”
	 * 
	 * @param paras				参数列表
	 * @return						返回参数列表（map）
	 */
	public static Map<String,Object> buildParas(String paras){
		String[] p = paras.split("&");
		String[][] ps = new String[p.length][2];
		int pos = 0;
		for (int i = 0; i < p.length; i++) {
			pos = p[i].indexOf("=");
			ps[i][0]=p[i].substring(0,pos);
			ps[i][1]=p[i].substring(pos+1);
			pos = 0;
		}
		return buildParas(ps);
	}
	
	/**
	 * 生成参数
	 * 参数类型：{{"k1","v1"},{"k2","v2"}}
	 * 
	 * @param paras 				参数列表
	 * @return						返回参数列表（map）
	 */
	public static Map<String,Object> buildParas(String[][] paras){
		// 创建参数队列    
		Map<String,Object> map = new HashMap<String, Object>();
		for (String[] para: paras) {
			map.put(para[0], para[1]);
		}
		return map;
	}
	
	
	/**
	 * 将Map存储的对象，转换为key=value&key=value的字符
	 *
	 * @param requestParam
	 * @param coder
	 * @return
	 */
	public static String buildParas(Map<String, Object> requestParam) {
		StringBuffer sf = new StringBuffer("");
		String reqstr = "";
		if (null != requestParam && 0 != requestParam.size()) {
			for (Entry<String, Object> entry : requestParam.entrySet()) {
					sf.append(entry.getKey()).append("=").append(String.valueOf(entry.getValue())).append("&");
			}
			reqstr = sf.substring(0, sf.length() - 1);
		}
		return reqstr;
	}

	public static void main(String[] args) {
		String aa="{'desc': '([\'cDesc\':\'#cFFFFFF\u753b\u4e2d\u4ed9   #cEF6051\u70b9\u5316183\u7ea7#r#cFBFFC1\u6210\u957f\u7387 1.112 \u9f99\u9aa8x5#r#c7DDE8C\u6c14\u8840 256334#cF6FCF5(314) #c7DDE8C\u653b\u51fb  7683#cF6FCF5(   8)#r#c7DDE8C\u6cd5\u529b  37525#cF6FCF5(  2) #c7DDE8C\u901f\u5ea6  1513#cF6FCF5(1028)#r#c7DDE8C\u7985\u5b9a    183#cF6FCF5(  0) #r#cD8AB6C\u5185\u3000\u4e39 \u6d69\u7136\u6b63\u6c14 \u70b9\u5316179\u7ea7#r#cD8AB6C\u3000\u3000\u3000 \u51cc\u6ce2\u5fae\u6b65 \u70b9\u5316179\u7ea7#r#cD8AB6C\u3000\u3000\u3000 \u6697\u6e21\u9648\u4ed3 \u70b9\u5316180\u7ea7#r#cACDBEE\u70bc\u5996\u6b21\u6570 11#r#c8EDEFF\u6297\u3000\u6c34: 53.7(#cFFFFFF30.0#c8EDEFF+23.7)#r#c8EDEFF\u6297\u9b3c\u706b: 30.0(#cFFFFFF30.0#c8EDEFF+0.0)#r#c8EDEFF\u6297\u3000\u98ce: 49.3   #c8EDEFF\u6297\u3000\u96f7: 48.7#r#c8EDEFF\u6297\u3000\u706b: 56.2   #r#r#r#r\u5929\u8d4b:\u4e94\u9636\u5929\u8d4b#r\u6280\u80fd1:\u795e\u51fa\u9b3c\u6ca1\uff1b\u6280\u80fd2:\u5982\u4eba\u996e\u6c34#r\u6280\u80fd3:\u5999\u624b\u56de\u6625\uff1b\u6280\u80fd4:\u4f5c\u9e1f\u517d\u6563#r\u6280\u80fd5:\u6625\u98ce\u62c2\u9762\uff1b\u6280\u80fd6:\u5929\u5730\u540c\u5bff\uff1b\uff1b\u795e\u517d\u6280\u80fd\u683c:\u6d85\u69c3#r#Y\u88c5\u5907\uff1a#o25419 #o25425 #o25431 #r#Y\u89c9\u9192\u6280\uff1a\u80cc\u5411#n#r#cA49BC8\u91d1: 0 \u6728:50 \u6c34:50 \u706b: 0 \u571f: 0#r#cC5CE64#r\u5f69\u6676\u77f3:#r\u6839\u9aa8 +10    \u654f\u6377 +10    #r\u54c1\u8d28\uff1a383#r#r#n\',\'equip\':({\'#W\u3010\u7b49\u7ea7\u30116\u7ea7#r\u3010\u88c5\u5907\u90e8\u4f4d\u3011\u517d\u73af#r#n#Y\u3010\u7b49\u7ea7\u9700\u6c42\u30112\u8f6c100\u7ea7#r\u6839\u9aa8 +17#r \u3010\u54c1\u8d28\u301183/100#r\u3010\u901a\u7075\u30116000/6000#r\u3010\u8010\u4e45\u30112998/3000#r#n#G\u968f\u673a\u6297\u7075\u5b9d\u4f24\u5bb3 +8300~16600#r\u9644\u52a0\u6c14\u8840 +3320#r #n#c00FCF0\u57f9\u517b 0#r#n#r#Y\u80cc\u5411       #o110001#o110001#o110001#o110001#o110002#r#n#W\u3010\u89c9\u9192\u6280\u7b49\u7ea7\u30111#r#n#W\u3010\u7c7b\u578b\u3011\u901a\u7528#n#r#n#G\u53d7\u5230\u4f24\u5bb3\u65f6\uff0c\u6709#R4#G%\u51e0\u7387\u80cc\u5411\u654c\u4eba\uff0c\u4ee5\u51cf\u5c11#R6#G%\u53d7\u5230\u7684\u7269\u7406\u3001\u6cd5\u672f\u3001\u7075\u5b9d\u4f24\u5bb3\u3002#r#n#cC5C582\u94c3\u3001\u73af\u3001\u7532\u89c9\u9192\u4e09\u5408\u4e00\uff0c\u89c9\u9192\u6280\u65b9\u53ef\u751f\u6548#r#n\',\'#W\u3010\u7b49\u7ea7\u30116\u7ea7#r\u3010\u88c5\u5907\u90e8\u4f4d\u3011\u517d\u94c3#r#n#Y\u3010\u7b49\u7ea7\u9700\u6c42\u30112\u8f6c100\u7ea7#r\u9644\u52a0\u901f\u5ea6 +20#r \u3010\u54c1\u8d28\u301185/100#r\u3010\u901a\u7075\u30116000/6000#r\u3010\u8010\u4e45\u30112998/3000#r#n#G\u968f\u673a\u6297\u7075\u5b9d\u4f24\u5bb3 +8500~17000#r\u9752\u9762\u7360\u7259\u7b49\u7ea7 +6#r \u589e\u52a0\u9632\u5fa1 +6120#r #n#c00FCF0\u57f9\u517b 0#r#n#r#Y\u80cc\u5411       #o110001#o110001#o110001#o110001#o110002#r#n#W\u3010\u89c9\u9192\u6280\u7b49\u7ea7\u30111#r#n#W\u3010\u7c7b\u578b\u3011\u901a\u7528#n#r#n#G\u53d7\u5230\u4f24\u5bb3\u65f6\uff0c\u6709#R4#G%\u51e0\u7387\u80cc\u5411\u654c\u4eba\uff0c\u4ee5\u51cf\u5c11#R6#G%\u53d7\u5230\u7684\u7269\u7406\u3001\u6cd5\u672f\u3001\u7075\u5b9d\u4f24\u5bb3\u3002#r#n#cC5C582\u94c3\u3001\u73af\u3001\u7532\u89c9\u9192\u4e09\u5408\u4e00\uff0c\u89c9\u9192\u6280\u65b9\u53ef\u751f\u6548#r#n\',\'#W\u3010\u7b49\u7ea7\u30116\u7ea7#r\u3010\u88c5\u5907\u90e8\u4f4d\u3011\u517d\u7532#r#n#Y\u3010\u7b49\u7ea7\u9700\u6c42\u30112\u8f6c100\u7ea7#r\u9644\u52a0\u901f\u5ea6 +19#r \u3010\u54c1\u8d28\u301181/100#r\u3010\u901a\u7075\u30116000/6000#r\u3010\u8010\u4e45\u30112998/3000#r#n#G\u968f\u673a\u6297\u7075\u5b9d\u4f24\u5bb3 +8100~16200#r\u6d69\u7136\u6b63\u6c14\u7b49\u7ea7 +6#r #n#c00FCF0\u57f9\u517b 0#r#n#r#Y\u80cc\u5411       #o110001#o110001#o110001#o110001#o110002#r#n#W\u3010\u89c9\u9192\u6280\u7b49\u7ea7\u30111#r#n#W\u3010\u7c7b\u578b\u3011\u901a\u7528#n#r#n#G\u53d7\u5230\u4f24\u5bb3\u65f6\uff0c\u6709#R4#G%\u51e0\u7387\u80cc\u5411\u654c\u4eba\uff0c\u4ee5\u51cf\u5c11#R6#G%\u53d7\u5230\u7684\u7269\u7406\u3001\u6cd5\u672f\u3001\u7075\u5b9d\u4f24\u5bb3\u3002#r#n#cC5C582\u94c3\u3001\u73af\u3001\u7532\u89c9\u9192\u4e09\u5408\u4e00\uff0c\u89c9\u9192\u6280\u65b9\u53ef\u751f\u6548#r#n\',}),\'iType\':2455,\'iFlyupFlag\':1,\'iSpecial\':0,])'}";
		String b=Utils.convert(aa);
		System.out.println(aa);
		String c=b.replace("{\"desc\": \"","").replace("#cFEFF72", "").replace("#c4BF24B", "").replace("#cE43D31", "").replace("#Y", "");
		System.out.println(c);
	}
	
	
	/**
	 * 去除转意字符
	 * @param utfString
	 * @return
	 */
	public static String convert(String utfString) {
		StringBuilder sb = new StringBuilder();
		int i = -1;
		int pos = 0;

		while ((i = utfString.indexOf("\\u", pos)) != -1) {
			sb.append(utfString.substring(pos, i));
			if (i + 5 < utfString.length()) {
				pos = i + 6;
				sb.append((char) Integer.parseInt(
						utfString.substring(i + 2, i + 6), 16));
			}
		}
		return sb.toString();
	}
}
