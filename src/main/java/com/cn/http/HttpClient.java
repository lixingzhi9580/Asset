/**
 *
 * Licensed Property to China UnionPay Co., Ltd.
 * 
 * (C) Copyright of China UnionPay Co., Ltd. 2010
 *     All Rights Reserved.
 *
 * 
 * Modification History:
 * =============================================================================
 *   Author         Date          Description
 *   ------------ ---------- ---------------------------------------------------
 *   xshu       2014-05-28       HTTP通信工具类
 * =============================================================================
 */
package com.cn.http;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cn.http.BaseHttpSSLSocketFactory.TrustAnyHostnameVerifier;

public class HttpClient {
	static final Logger LOG = LoggerFactory.getLogger(HttpClient.class);
	/**
	 * 目标地址
	 */
	private URL url;

	/**
	 * 通信连接超时时间
	 */
	private int connectionTimeout;

	/**
	 * 通信读超时时间
	 */
	private int readTimeOut;

	/**
	 * 通信结果
	 */
	private String result;

	/**
	 * 获取通信结果
	 * 
	 * @return
	 */
	public String getResult() {
		return result;
	}

	/**
	 * 设置通信结果
	 * 
	 * @param result
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * 构造函数
	 * 
	 * @param url
	 *            目标地址
	 * @param connectionTimeout
	 *            HTTP连接超时时间
	 * @param readTimeOut
	 *            HTTP读写超时时间
	 */
	public HttpClient(String url, int connectionTimeout, int readTimeOut) {
		try {
			this.url = new URL(url);
			this.connectionTimeout = connectionTimeout;
			this.readTimeOut = readTimeOut;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送信息到服务端
	 * 
	 * @param data
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public int send(Map<String, String> data, String encoding) throws Exception {
		try {
			HttpURLConnection httpURLConnection = createConnection(encoding);
			if (null == httpURLConnection) {
				throw new Exception("创建联接失败");
			}
			long startTime = System.currentTimeMillis();
			
			
			
			this.requestServer(httpURLConnection,this.getRequestParamString(data, encoding), encoding);
			
			this.result = this.response(httpURLConnection, encoding);
			
			
			
			
			long endTime =System.currentTimeMillis() - startTime;
			LOG.info("耗时[{}];返回报文:[" + result + "]",endTime);
			if(endTime>8000){
				LOG.error("耗时[{}];返回报文:[" + result + "]",endTime);
			}
			
			return httpURLConnection.getResponseCode();
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * HTTP Post发送消息
	 *
	 * @param connection
	 * @param message
	 * @throws IOException
	 */
	private void requestServer(final URLConnection connection, String message,
			String encoder) throws Exception {
		PrintStream out = null;
		try {
			connection.connect();
			out = new PrintStream(connection.getOutputStream(), false, encoder);
			out.print(message);
			out.flush();
		} catch (Exception e) {
			throw e;
		} finally {
			if (null != out) {
				out.close();
			}
		}
	}

	/**
	 * 显示Response消息
	 *
	 * @param connection
	 * @param CharsetName
	 * @return
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	private String response(final HttpURLConnection connection, String encoding)
			throws URISyntaxException, IOException, Exception {
		InputStream in = null;
		StringBuilder sb = new StringBuilder(1024);
		BufferedReader br = null;
		try {
			if (200 == connection.getResponseCode()) {
				in = connection.getInputStream();
				sb.append(new String(read(in), encoding));
			} else {
				in = connection.getErrorStream();
				sb.append(new String(read(in), encoding));
			}
			LOG.info("HTTP Return Status-Code:[" + connection.getResponseCode()
					+ "]");
			return sb.toString();
		} catch (Exception e) {
			throw e;
		} finally {
			if (null != br) {
				br.close();
			}
			if (null != in) {
				in.close();
			}
			if (null != connection) {
				connection.disconnect();
			}
		}
	}

	public static byte[] read(InputStream in) throws IOException {
		byte[] buf = new byte[1024];
		int length = 0;
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		while ((length = in.read(buf, 0, buf.length)) > 0) {
			bout.write(buf, 0, length);
		}
		bout.flush();
		return bout.toByteArray();
	}

	/**
	 * 创建连接
	 *
	 * @return
	 * @throws ProtocolException
	 */
	private HttpURLConnection createConnection(String encoding)
			throws ProtocolException {
		HttpURLConnection httpURLConnection = null;
		try {
			httpURLConnection = (HttpURLConnection) url.openConnection();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		httpURLConnection.setConnectTimeout(this.connectionTimeout);// 连接超时时间
		httpURLConnection.setReadTimeout(this.readTimeOut);// 读取结果超时时间
		httpURLConnection.setDoInput(true); // 可读
		httpURLConnection.setDoOutput(true); // 可写
		httpURLConnection.setUseCaches(false);// 取消缓存
		httpURLConnection.setRequestProperty("Content-type",
				"application/x-www-form-urlencoded;charset=" + encoding);
		httpURLConnection.setRequestMethod("POST");
		if ("https".equalsIgnoreCase(url.getProtocol())) {
			HttpsURLConnection husn = (HttpsURLConnection) httpURLConnection;
			husn.setSSLSocketFactory(new BaseHttpSSLSocketFactory());
			husn.setHostnameVerifier(new TrustAnyHostnameVerifier());// 解决由于服务器证书问题导致HTTPS无法访问的情况
			return husn;
		}
		return httpURLConnection;
	}

	/**
	 * 将Map存储的对象，转换为key=value&key=value的字符
	 *
	 * @param requestParam
	 * @param coder
	 * @return
	 */
	private String getRequestParamString(Map<String, String> requestParam,
			String coder) {
		if (null == coder || "".equals(coder)) {
			coder = "UTF-8";
		}
		StringBuffer sf = new StringBuffer("");
		String reqstr = "";
		if (null != requestParam && 0 != requestParam.size()) {
			for (Entry<String, String> en : requestParam.entrySet()) {
				try {
					sf.append(en.getKey()
							+ "="
							+ (null == en.getValue()
									|| "".equals(en.getValue()) ? ""
									: URLEncoder.encode(en.getValue(), coder))
							+ "&");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					return "";
				}
			}
			reqstr = sf.substring(0, sf.length() - 1);
		}
		LOG.info("请求报文:[" + reqstr + "]");
		return reqstr;
	}

	public static Map<String, String> convertResultStringToMap(String result) {
		Map<String, String> map = null;
		try {
			if (StringUtils.isNotBlank(result)) {
				if ((result.startsWith("{")) && (result.endsWith("}"))) {
					result = result.substring(1, result.length() - 1);
				}
				map = parseQString(result);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return map;
	}

	public static Map<String, String> parseQString(String str)
			throws UnsupportedEncodingException {
		Map<String, String> map = new HashMap();
		int len = str.length();
		StringBuilder temp = new StringBuilder();

		String key = null;
		boolean isKey = true;
		boolean isOpen = false;
		char openName = '\000';
		if (len > 0) {
			for (int i = 0; i < len; i++) {
				char curChar = str.charAt(i);
				if (isKey) {
					if (curChar == '=') {
						key = temp.toString();
						temp.setLength(0);
						isKey = false;
					} else {
						temp.append(curChar);
					}
				} else {
					if (isOpen) {
						if (curChar == openName) {
							isOpen = false;
						}
					} else {
						if (curChar == '{') {
							isOpen = true;
							openName = '}';
						}
						if (curChar == '[') {
							isOpen = true;
							openName = ']';
						}
					}
					if ((curChar == '&') && (!isOpen)) {
						putKeyValueToMap(temp, isKey, key, map);
						temp.setLength(0);
						isKey = true;
					} else {
						temp.append(curChar);
					}
				}
			}

			putKeyValueToMap(temp, isKey, key, map);
		}
		return map;
	}

	private static void putKeyValueToMap(StringBuilder temp, boolean isKey,
			String key, Map<String, String> map)
			throws UnsupportedEncodingException {
		if (isKey) {
			key = temp.toString();
			if (key.length() == 0) {
				throw new RuntimeException("QString format illegal");
			}
			map.put(key, "");
		} else {
			if (key.length() == 0) {
				throw new RuntimeException("QString format illegal");
			}
			map.put(key, temp.toString());
		}
	}

}
