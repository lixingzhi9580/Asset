package com.cn.encryption.demoIm;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cn.encryption.utilsIn.ByteUtil;

/**
 * @description 套节字通讯客户端
 * @author lds
 *
 */
public class SocketClient {

	private static final Logger LOG = LoggerFactory.getLogger(SocketClient.class);

	/**
	 * @description 同步短连接
	 * @param url
	 * @param port
	 * @param timeout
	 * @param requestBytes
	 * @return
	 */
	public static byte[] syncShortConnection(String url, int port, int timeout, byte[] requestBytes) {
		Socket socket = null;
		OutputStream out = null;
		InputStream in = null;

		byte[] resbytes = null;
		try {
			socket = new Socket(url, port);
			socket.setSoTimeout(timeout);
			
			LOG.info("同步短连接请求：[{}]", ByteUtil.bytesToHex(requestBytes));
			out = socket.getOutputStream();
			out.write(requestBytes);
			out.flush();

			in = socket.getInputStream();
			resbytes = null;
			while (-1 != in.read()) {
				resbytes = new byte[in.available()];
				in.read(resbytes);
			}
			LOG.info("同步短连接响应：[{}]", ByteUtil.bytesToHex(resbytes));

			return resbytes;
		} catch (Exception e) {
			LOG.info(e.getMessage(), e);
			return null;
		} finally {
			try {
				if (null != out)
					out.close();
				if (null != in)
					in.close();
				if (null != socket)
					socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
