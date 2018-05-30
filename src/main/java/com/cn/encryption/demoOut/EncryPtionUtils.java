package com.cn.encryption.demoOut;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;

import com.cn.encryption.utilsOut.Des3;
import com.cn.encryption.utilsOut.MD5;
import com.cn.encryption.utilsOut.RSA;

/**
 * 交互DEMO
 * @author lixz
 * @date 2016-06-29
 */
public class EncryPtionUtils {
	
	
	private static final int CONNECTION_TIMEOUT = 10 * 1000; // 10秒
	private static final int READ_TIMEOUT = 90 * 1000; // 90秒
	//二维码主扫请求地址
//	private static final String urlPath = "http://172.16.136.103:8080/cooperation/qr/qrCode";
	private static final String urlPath = "https://testprepos.vbill.cn/cooperation/qr/qrCode";
	
	private static final String CHARSET = "utf-8";
	//第三方提供给随行付偏移量8字节
	private static final String iv = "12345678";
	//随行付接入商的rsa公钥，用于加密请求
	private static final String DEFAULT_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC0RVoxGgDwVdL/N2n0txWp45qlEVS1fNWA045wuZJMpcAiGWCqlp4DUE2+XkWbM/0U5zc9pfmh0+BAlfrtk/3RcEYyHWe0ZBSm6xpdaveEZHAd2feC7Mm6DmpbiA3m6F2RqdS02iCN7pWxZkBDooeCJhO9c8oBERtjo38jgi2OZQIDAQAB";
	//随行付接入商的身份标识。
	private static String PARTNER_ID="ODg4ODg4ODg=";
	
	public static void main(String[] args) throws Exception {
		/**
		 * amt 金额
		 * mno 商编
		 * ordNo 订单号 防错乱,交易解密成功后请对比该号码,如果不一致请按交易失败处理
		 * type 支付类型 
		 */
		 //二维码主扫
		String json = "{'ordNo':'2017031601582703488262843972','amt':'0.01','mno':'836567980410000', 'payType':'03'}";
		System.out.println("发送的json：["+json+"]");
		String desStrg = Des3.generateKey();
		System.out.println("3DES随机密钥：["+desStrg+"]");
		//加密请求
		String reqMsg = requestService(json, desStrg);
		//发送请求
		String resMsg = httpClientUtils(reqMsg);
		//解析返回
		responseService(resMsg, desStrg);
	}
	
	/**
	 * 请求数据加密处理
	 * @param json 请求数据
	 * @param urlPath 请求路径
	 * @param desStrg des串
	 * @return
	 * @throws Exception
	 */
	public static String requestService(String json, String desStrg)
			throws Exception {
		System.out.println("使用的公钥：["+DEFAULT_PUBLIC_KEY+"]");
		
		RSAPublicKey rSAPublicKey = RSA.loadPublicKey(DEFAULT_PUBLIC_KEY);
		String desKey = new String(Base64.getEncoder().encode(RSA.encrypt(rSAPublicKey,desStrg)), CHARSET);
		System.out.println("用BASE64,公钥处理3DES密钥后的结果：["+desKey+"]");
		
		String data = Des3.encode(json, desStrg, iv);
		System.out.println("用3DES,偏移量加密JSON后的结果：["+data+"]");
		
		String str =String.format("%s|%s|%s", PARTNER_ID, desKey, data);
		System.out.println("最终数据串为：["+str+"]");
		return str;
	}

	/**
	 * 响应数据解密处理
	 * @param 响应数据
	 * @param desResStrg 密钥
	 */
	public static void responseService(String msg, String desResStrg) {
		String payData = null;
		String[] payStrArray = msg.split("\\|");
		
		if (payStrArray.length == 3) {
			try {
				payData = Des3.decode(payStrArray[1], desResStrg, iv);
				System.out.println("响应数据为：["+payData+"]");
				String md5Strg = new String(Base64.getDecoder().decode(payStrArray[2]
						.getBytes(CHARSET)));
				String resultStrg = MD5.encode(payData);
				System.out.println("md5加密后数据：["+resultStrg+"]");
				System.out.println("服务器返回的MD5：["+md5Strg+"]");

				if (resultStrg.equals(md5Strg)) {
					System.out.println("返回的数据："+payData);
					/**
					 * result 返回码
					 * reqMsg 返回信息
					 * ordNo  订单号
					 * payUrl 支付地址
					 */
				} else {
					System.out.println("数据被篡改");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * HTTP请求
	 * @param XML 请求的数据
	 * @return 响应的数据
	 * @throws Exception
	 */
	public static String httpClientUtils(String XML)
			throws Exception {		String Method = "POST";
			String xmlString = XML;
			byte[] xmlData = xmlString.getBytes();
			try {
				xmlData = xmlString.getBytes(CHARSET);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			BufferedReader rd = null;
			String msg = "";
			System.out.println("请求URL：["+urlPath+"]");
			System.out.println("请求参数：["+XML+"]");
			InputStream is = null;
			try {
				URL url = new URL(urlPath);
				HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
				urlCon.setDoOutput(true);
				urlCon.setDoInput(true);
				urlCon.setUseCaches(false);
				urlCon.setRequestMethod(Method);
				urlCon.setConnectTimeout(CONNECTION_TIMEOUT);
				urlCon.setReadTimeout(READ_TIMEOUT);
				// urlCon.setRequestProperty("Content-Type,text/xml;charset=utf-8");
				urlCon.setRequestProperty("Content-length",
						String.valueOf(xmlData.length));
				urlCon.setRequestMethod("POST");
				DataOutputStream printout = new DataOutputStream(urlCon.getOutputStream());
				printout.write(xmlData);
				printout.flush();
				printout.close();
				System.out.println("数据发送完成,等待响应...");
				is = urlCon.getInputStream();
				if (is != null) {
					rd = new BufferedReader(new InputStreamReader(is, CHARSET));
					String inputLine;
					while ((inputLine = rd.readLine()) != null) {
						msg += inputLine;
					}
					System.out.println("接收到解密前数据：["+msg+"]");
				}
				rd.close();
				is.close();
			} catch (Exception e) {
				msg = "未连接到服务器,请稍后再试!";
				e.printStackTrace();
				throw e;
			}
			return msg;}
	
}
