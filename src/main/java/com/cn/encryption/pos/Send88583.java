package com.cn.encryption.pos;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.cn.encryption.demoIm.SocketClient;
import com.cn.encryption.utilsIn.ByteUtil;
import com.cn.iso8583.AsciiCodec;

public class Send88583 {
	public static void main(String[] args) throws IOException {
		   String head = "6000010000603110310031";
        String sendMsg = "000000DEF973C4E9DE3BCDFA7C5CB7DF2334E989F52BF5A4F11DC70BE3270FF4A0EA8A6AD14ADA7C5004953468B402D19D9DAC4ED6A6F2E4639F50D47022F12DB73244D695D52E4D18DD1D13E2499537F92FDED28602C5CFF99ACC33B67B4F136AC9D175F2E8D0540086EF144702A3C18CCE402376727A1606999791596CF181F5AC712DD5EDF66B29060E09054F1876B8BAFB32E41B94BB0702DD43760C1A562B0624EC7ED231648F2689BF66CA48";
		byte[] reqbytes = AsciiCodec.ascStr2Bcd(sendMsg);
		byte[] reqheadbytes = ByteUtil.hexToBytes(head);

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		out.write(ByteUtil.shortToBytes((short) (reqheadbytes.length + reqbytes.length)));
		out.write(reqheadbytes);
		out.write(reqbytes);
		out.flush();
		out.close();

		byte[] resbytes = SocketClient.syncShortConnection("127.0.0.1", 6665, 60000, out.toByteArray());
		byte[] bytes = new byte[resbytes.length - 12];
		System.arraycopy(resbytes, 12, bytes, 0, bytes.length);
		System.out.println("返回解压报文：" + ByteUtil.bytesToHex(bytes));
	}
}
