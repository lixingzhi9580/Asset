package com.tgb.ccl.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class RunnableTest implements Runnable {
	
	@Override
	public void run() {
		long max=0;
		long min=0;
//		long p1=8699899004000027L;
		String maxresult="";
		String minresult="";
		for (int i = 0; i < 10; i++) {
			String p = "150024";
		    String key="EC66578AE61AA2CD7ED56627359D785D";
		    Date now = new Date();
		    String t =now.getTime()/1000 + "";
		    int ri = new Random().nextInt(1000000000);
		    String  r = "harvey"+ri;
		    String n ="http://118.194.198.6:30091/lemon-gateway-out/cupe/trandata_update";
//		    p1++;
//		    p1=8699800000082770L;
		    String p2="o111"+ri;
		    String p3="2";
		    HashMap<String, String> map=new HashMap<String, String>();
		    map.put("n", n);
		    map.put("p", p);
		    map.put("p1", "91005293");
//		    map.put("p1", p1+"");
		    map.put("p2", p2);
		    map.put("p3",p3);
		    map.put("r", r);
		    map.put("t", t);
		    String s= MD5Sign(map, key);
		    map.put("s", s);
		    String vCupeUrl = "http://vc.counect.com/vcupe/getPay.do";
		  	long start=System.currentTimeMillis();
		  	String result1=executeHttpGet(vCupeUrl, map);
			long end=System.currentTimeMillis();
			long ss=end-start;
			System.out.println(end-start);
			if(max==0){
				max=ss;
				maxresult=result1;
			}else {
				if(max<ss){
					max=ss;
					maxresult=result1;
				}
			}
			if(min==0){
				min=ss;
			}else {
				if(min>ss){
					min=ss;
					minresult=result1;
				}
			}
			System.out.println();
		}
		System.out.println("最小时间："+min+"   ...   "+minresult);
		System.out.println("最大时间："+max+"   ...   "+maxresult);
	}

	public static String MD5Sign(HashMap<String, String> parameters, String key) {
		String s = parameters.get("s");
		String sign = parameters.get("sign");
		String ip = parameters.get("ip");
		parameters.remove("s");
		parameters.remove("sign");
		parameters.remove("ip");
		String text = waitForSignatureString(parameters);
		String signString=text+key;
		String md5_sign = null;
		if (s != null && !s.equals("")) {
			parameters.put("s", s);
		}
		if (sign != null && !sign.equals("")) {
			parameters.put("sign", sign);
		}
		if (ip != null && !ip.equals("")) {
			parameters.put("ip", ip);
		}
		try {
			md5_sign = DigestUtils.md5Hex(signString.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return md5_sign;
		}
		
		return md5_sign;
	}
	public static String waitForSignatureString(HashMap<String, String> parameters){
		String text = "";
		List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(parameters.entrySet());
		Collections.sort(infoIds,
			new Comparator<Map.Entry<String, String>>() {
				@Override
				public int compare(Map.Entry<String, String> o1,Map.Entry<String, String> o2) {
					return (o1.getKey()).toString().compareTo(o2.getKey());
			}
		});
		String key_sort = "";
		for (int i = 0; i < infoIds.size(); i++) {
			Map.Entry<String, String> tmpMap = infoIds.get(i);
			text += tmpMap.getValue();
			key_sort += tmpMap.getKey()+", ";
		}
		//System.out.println("key_sort ------ "+key_sort);
		return text;
	}
	
	public String executeHttpGet(String url,HashMap<String, String> parameters) {
    	return executeHttpGet(url,parameters,"counect cupe");
    }
	
	public String executeHttpGet(String url,HashMap<String, String> parameters,String user_agent) {
		String urlString = url+"?";
    	Map map = parameters;
    	
    	Iterator iter = map.entrySet().iterator();
    	while (iter.hasNext()) {
    		Map.Entry entry = (Map.Entry) iter.next();
    		Object key = entry.getKey();
    		Object val = entry.getValue();
    		urlString = urlString + key.toString() + "=" + val.toString() + "&";
    	}
    	if(urlString.length()>0){
    		urlString = urlString.substring(0, urlString.length()-1);
    	}
    	String responseBody = "";
    	CloseableHttpClient httpclient = HttpClients.createDefault();
    	try {
    		HttpGet httpget = new HttpGet(urlString);
        	
        	//请求处理
        	httpget.addHeader(HttpHeaders.USER_AGENT, user_agent);
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

                @Override
				public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
                	
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }

            };
            
			responseBody = httpclient.execute(httpget, responseHandler);
			
    	}catch(ClientProtocolException e){
    		e.printStackTrace();
    	}catch (IOException e){
    		e.printStackTrace();
    	}finally {
    		try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	
        return responseBody;
	}
	
}
