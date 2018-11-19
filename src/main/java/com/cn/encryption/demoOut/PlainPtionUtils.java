package com.cn.encryption.demoOut;

import java.security.interfaces.RSAPrivateKey;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cn.encryption.utilsOut.Des3;
import com.cn.encryption.utilsOut.RSA;

public class PlainPtionUtils {
	
	private final static Logger LOG = LoggerFactory.getLogger(PlainPtionUtils.class);
	public static final String RSA_PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMyPHq2LOrKz0I1/\r/d1u1O9OTOJTPoQDNgwsxfocoX1I8HcOlNJHNsEQg+LsIrkG8hQOnLK55UiS+A5p\r93/4hHT+GyHVf7xciN+1OYaOptRfQMiVIRJfTRtZWj0cRN8Zw2U8MjvupI72pv9Q\rZV6t4mv5NGC3xiyaXfwq0u8yOnltAgMBAAECgYEAtfk35FCwhhrak5ZiA2O+P6jb\rUpeVTKECqrAF6usfajHB4VfyYmIBvpxvhyZj+U/JeLhLA9/Frds4mrIAADLXuRdl\rcQYyz9RK2y/q8WfN8XcAWWKydQTs1wGCcku3RS2rg0Sfrdinb0jq37KUWKqex3Mo\rg5KDR+ueZnBJxsoRJmECQQD4yxtFak8VkbfOHpWrmcHgRO6TZwZaNWExsXCQRnze\rWOTEVoPEhG7IwRPGIxfE0Zkf2cG+vfGKF65YCbzcHcI1AkEA0nv/7NpqgGszAjOZ\rdE7x5t8UfGdBe6wieZJToqqWE7NjbgyLmUl1YFz+Hu3qebrVkl1hNXLZ6Jv45fgi\r2ZjBWQJAIEh6qW86A9p8t0pQsYuqFKfdLVNZB6uViRU1PgNngJKYXMG9J2rn1TT4\rk+VJ2Eg6Tl+7PDz5cqnP/ayFzSovYQJAQzofr8LDKWkTzaw1YxSj5p1xqZpBLAL6\rr+GwnM/nRzuQkmGnZLo1pyWMdMyAi4jFFg6FMdEREF5gzPLIDr/fYQJBAIxcvKka\r43RpfMLcwDYmc0gglODFTuVFkjxZK9Xi/3JBrtwWxPHLkJd4leoPOq527MB9pUDm\rT87Eaa2vVKPl8cQ=";
	public static final String CHARSET = "UTF-8";

	public static void main(String[] args) throws Exception {
		
		byte[] key = null;
		String aa="MjE=|l4LTTNpL7F+xIq5V6IDYX/Cx27Z707Eov62whn2PMhGfxX4/YcWQXDviIuGjPg0DsqVtc2UH00tRoWsyWnNjY0qnrsV1slWCmwx9Agz55RU7xXCxoA/dzdo8/btQ3zzBHL0QrBPoAfKHkW2jyINbr4YSstmo9dHrqXVO0K1G2+Y=|Biiv63ICC2LwJY0HydAnJayANG1ehpIC2bvQGW7gUKBoLHAAo2ecs2ofoICs/8D/ZDT+uoXRr/qPEJGiWtXlSoaYYeLORmfYgxZZKOncsHsrdVkkOHAJEGRsl1+T2GWov4R359BjtzK6KCF/cE42yJttPBQgXN9DPhlgJsACfuIFgsGvnIKvfPiZ86+jQ7nWJnq9qPSiAAivYxIzL25amodO8cSN+tIjjZO+53Y8ZolMeGUilNCnEe+6CFePQFvajZdaqdFiJ6gPQs/GLGJPQ7LoVvP/bO/7h24+eEEJbL99Pg079UrNXBsSBaqLcNtoMRu2WO/WHmYvyt5M7tkkFBaGCGfjEavS0OQYZpXLjXNt1Od8ZuYtQi698cmNgjXlMn1q7ZY/bei7NdKNp7etBVToH4GNn+s8B4x5m2pqIBUymI7bW5X/+ZuXHhiEKiD7yfkQiWoS19XTxyJT00NO1RKRsmoveI18eSsrbXfkTpn88ug8UHwwSiqvgFFNH85Tr+o31YRwrFPuQ7bf3MWCvGxfnYX7rmPgc5UkuA2jMzIDpMupgmlupXTOdA4xGfa+l4OoFCfJcYrhB1asQr1pNgMZewGt2KbAbR29cJKOaFUjOLA3RdovO9NIiJoJtpOuP0B46qxzNitKtjCzebS3HL1vaCC3QQCIAlUhLdHLJ/QvyPCzM9yYTZ9xrAoKkOsw29Lav7KGa7684Va9WiW+v35qusnOzznP3kO9Sbs8ttq0qdFQcZPRrF9dSclOSqIU5uuC8m4ZsEsbidFMuQtpK66tZKgjP7jGWscZHIzUNUNDuFux73fcNotrBEoxAzCVlq1QIZjRlsYoKCoPko6Rm8/HGPJYAepQbUmxzNLgdPdLQ+Flv2dzroePDGCsaL5JpM+c2nRTGTAgJFom2dOJxcWqxbn0Ka5Wxj0xPbMXClB6RjdV3nOk3QCShvnaapr34d1lF3OWf7RFz7lH+MGB7m7jVh8zBEBbUHm6SzTWK0WQ2YJLLHXwtytQ+SMcdV7O+D+t/L8bQcKJUVUE3Td+emNcUtYguOSJeAwdOXgk4eGimiqidhQellXiSod0pfl2DsyVhhz+ug3eBuKng6GtbW2nEXTldfI4NPh8mik6HTQ4PPHqUdivMdKciokIVFYs8Qr40i21Rmv069CHsLrJDM34DlKtngB9KG2cFpnJ9dqsXpn0xvRSsA==";
		StringBuilder stringBuilder = new StringBuilder(aa);
		LOG.info("接收到的数据：[{}]", stringBuilder);
		
		String[] msgArr = stringBuilder.toString().split("\\|");
		String trmTyp = new String(Base64.getDecoder().decode(msgArr[0]), CHARSET);
		LOG.info("身份标识：[{}]", trmTyp);

		byte[] rsaBytes = Base64.getDecoder().decode(msgArr[1]);
		// RSA私钥
		RSAPrivateKey priKey = RSA.loadPrivateKey(RSA_PRIVATE_KEY.getBytes(CHARSET));
		byte[] desKeyBytes = RSA.decrypt(priKey, rsaBytes);
		String desKey = new String(desKeyBytes, CHARSET);
		LOG.info("3DES密钥：[{}]", desKey);

		byte[] bytes = Base64.getDecoder().decode(
				msgArr[2].getBytes(CHARSET));
		LOG.info("报文密文长度：[{}]", bytes.length);
		key = desKey.getBytes();
		String jsonBytes = Des3.decode(new String(msgArr[2].getBytes(CHARSET)),new String(key) ,"12345678");
		LOG.info("报文明文字符：[{}]", jsonBytes);
	}
}
