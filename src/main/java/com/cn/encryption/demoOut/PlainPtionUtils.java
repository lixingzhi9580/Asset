package com.cn.encryption.demoOut;

import java.io.UnsupportedEncodingException;
import java.security.interfaces.RSAPrivateKey;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cn.encryption.utilsOut.Des3;
import com.cn.encryption.utilsOut.RSA;
import com.cn.hnust.controller.UserTtController;

public class PlainPtionUtils {
	
	private final static Logger LOG = LoggerFactory.getLogger(PlainPtionUtils.class);
	protected static final String RSA_PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMyPHq2LOrKz0I1/\r/d1u1O9OTOJTPoQDNgwsxfocoX1I8HcOlNJHNsEQg+LsIrkG8hQOnLK55UiS+A5p\r93/4hHT+GyHVf7xciN+1OYaOptRfQMiVIRJfTRtZWj0cRN8Zw2U8MjvupI72pv9Q\rZV6t4mv5NGC3xiyaXfwq0u8yOnltAgMBAAECgYEAtfk35FCwhhrak5ZiA2O+P6jb\rUpeVTKECqrAF6usfajHB4VfyYmIBvpxvhyZj+U/JeLhLA9/Frds4mrIAADLXuRdl\rcQYyz9RK2y/q8WfN8XcAWWKydQTs1wGCcku3RS2rg0Sfrdinb0jq37KUWKqex3Mo\rg5KDR+ueZnBJxsoRJmECQQD4yxtFak8VkbfOHpWrmcHgRO6TZwZaNWExsXCQRnze\rWOTEVoPEhG7IwRPGIxfE0Zkf2cG+vfGKF65YCbzcHcI1AkEA0nv/7NpqgGszAjOZ\rdE7x5t8UfGdBe6wieZJToqqWE7NjbgyLmUl1YFz+Hu3qebrVkl1hNXLZ6Jv45fgi\r2ZjBWQJAIEh6qW86A9p8t0pQsYuqFKfdLVNZB6uViRU1PgNngJKYXMG9J2rn1TT4\rk+VJ2Eg6Tl+7PDz5cqnP/ayFzSovYQJAQzofr8LDKWkTzaw1YxSj5p1xqZpBLAL6\rr+GwnM/nRzuQkmGnZLo1pyWMdMyAi4jFFg6FMdEREF5gzPLIDr/fYQJBAIxcvKka\r43RpfMLcwDYmc0gglODFTuVFkjxZK9Xi/3JBrtwWxPHLkJd4leoPOq527MB9pUDm\rT87Eaa2vVKPl8cQ=";
	protected static final String CHARSET = "UTF-8";

	public static void main(String[] args) throws Exception {
		
		byte[] key = null;
		String aa="MjE=|u8LFe+UvLT6qL5oWaJ2NpMa2mUwNWoQrZbBeSqyDwmVPws+nbqSVfI+SJ1QjlMty09Un5BYvYS5hrjnuVD0thwl5UAbcwAhYdeyT2wwVQdWJspXw7X573aP2KypinM9do6208QoM0TNtV/6AzwOW14qG5UprFFyXl+RqbX3nsMM=|waZUh5HdGzLY0NRGkBeh0YmAjhaXnnetIvHxj8/Dz/iu4emoew3nwFX6kbi2vl/55jY11y0D82o897JNIdraD4k+maksE1kN9WQQyaQIVj0vaiLDFlfA9v/T0XzOC1fCw/FLndIpAcfVrPZsrlX90ZtCB9AkYWGFFTWA9LXFS/1gSTyE8EMgKoyWqYT9J0jY4OxUbSdvXn9SjEgrE5Nwb9NbrIr9z/GDwoqUkd0QgRopVplh+NTHJBUz50tgnQLHsZw8QhDReZdOmFsIcV4YobJ4PR7qyVd7HNMgZTaCMulfD/c4Jyfzk19UxMKKyD1LO3aJRhpdMnNmCtKNxYk4a8N1k1BFvWcASR0JAFj930gD82pvKGYhYcuD6kb3hU2ZXoOz06Yda8CLeR9Ie31OSZLOTrcwkOw1Ms1JzlPOh7sO7n70OsthvCb+S4iYPCTOAjfE90NATSDqhlybSO9n/1EKN/DTiXo5EeJ3a56moa9DGkVVyhn8RHXrRU8LyiOdYBWeg6Xe30kF5o6JK3m6AnolCrL4lrBp3poqpMmlb68oTwC9mjeE95HYt9m7oZ3tO5QFlE8wX408nKSOfrbO4jl70dbnANj8Z5IlbKfQkJD+i2l/n/Ir6xty7qxsL70HqPn5P76OTyeDYiaPsmNeyvqDwtQGNljGwFApWSb9Q+QVvUY6PA9ykMZ6C1+MYtu961vDRNJqJHR86V0cEc0mPjnqjs3Kj/VVWA6YWVMrAWwelVFlsZXDlCbYeSolq02MjqLQseFxvVqh4EanuLrDQAf6e2XxvOjLLqM5Sdkqlr9ZzmufOpU9Vudcv5D+6Vf3PImV4miB3aUJVAV7ApSRhTwqyNTpQdNg2ZZq6XfXBMr4np4yaFVSgbg2/E9TqYUCgdO44lM6i7zbTmAtUq5b6yTVmpYavPp2EuIs5t8VVst3t3TI7v02MrP3mcIvd6b9EKoTZphIlCzZVVOqIH4oUZobb3qKnRYHk4w+Ug64Ql1NV+ajnaOTXHUxiD3Ar68OjXvKUUws7/vUOAQBf3qOUg3fVQMFMBQjiqkaVXPOOv53RrQtyrLC2vx3aidsb8qWpNlfoRbT3oNlThNmSZzq3NH/x0xx/ae/ab7KllPksAzuaLJZzRIJvvY9hWyzj3NwwsMtGsKwefYtsW/5vV12S9aeN9FvF0zxwN4gEQDjnkucuwdmPsGcKRMKb/V9zPa27eTJWxM9E/25/AEoShzG8rpAt0DoND9JdoWltckwgKUbEzFl1chMbj9e6Ww3IAsTdXPd11xJ/hM=";
		StringBuilder stringBuilder = new StringBuilder(aa);
		LOG.info("接收到的数据：[{}]", stringBuilder);

		String[] msgArr = stringBuilder.toString().split("\\|");
		String trmTyp = new String(Base64.getDecoder().decode(msgArr[0]),
				CHARSET);
		LOG.info("身份标识：[{}]", trmTyp);

		byte[] rsaBytes = Base64.getDecoder().decode(msgArr[1]);
		// RSA私钥
		RSAPrivateKey priKey = RSA.loadPrivateKey(RSA_PRIVATE_KEY.getBytes(CHARSET));
		byte[] desKeyBytes = RSA.decrypt(priKey, rsaBytes);
		String desKey = new String(desKeyBytes, CHARSET);
		LOG.debug("3DES密钥：[{}]", desKey);

		byte[] bytes = Base64.getDecoder().decode(
				msgArr[2].getBytes(CHARSET));
		LOG.info("报文密文长度：[{}]", bytes.length);
		key = desKey.getBytes();
		String jsonBytes = Des3.decode(new String(bytes),new String(key) ,"87654321");
//		String requestJson = new String(jsonBytes, CHARSET);
		LOG.debug("报文明文字符：[{}]", jsonBytes);
	}
}
