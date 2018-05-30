import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Date;

public class Parse8583Test {
	private static final String IP = "10.1.2.5";
	private static final int PORT = 8;
	private static final Socket socket = new Socket();

	private static OutputStream out;
	private static InputStream in;
	static {
		try {
			socket.connect(new InetSocketAddress(IP, PORT), 3000);
			out = socket.getOutputStream();
			in = socket.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//62257583E6AE8D39D1903101F9EB4B2F 62257583E6AE8D39D1903101F9EB4B2F 372C14D14A62BB4C 15A16D1AC41BC7A1 2241B60BB2BADDA8A8E64D7581FD334F
	//6251530011F5321DD25112060000C5AE5E79 6251530011F5321DD25112060000C5AE5E79 425A72612ECB43A2 357939C7A883D582 2C5BEB88F052DD8D2572BE230F0077B5
	//	sh Parse8583Test.sh -file 1.txt > 2.txt
	public static void main(String[] args) throws Exception {
		System.out.println("start >> " + new Date());
		if ("-file".equals(args[0])) {
			String fileName = args[1];
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			String cardNo = null;
			while (null != (cardNo = br.readLine())) {
				String[] aa = cardNo.split("\\s");
				String track2M = aa[0];
				String track3M = aa[1];
				String pinData = aa[2];
				String dakSek = aa[3];
				String pikSek = aa[4];
				
				String track2 = track(track2M, dakSek);
				String track3 = track(track3M, dakSek);
				String pin = pin(track2, pikSek, pinData);
				System.out.println(track2 + "!!!" + track3 + "!!!" + pin);
			}
			br.close();
		}
		System.out.println("end >> " + new Date());
	}

	public static String pin(String track2, String pikSek,String pinData) throws IOException {
		String track2Arr[] = track2.split("D");
		String crdNo = track2Arr[0];
		int panLen = crdNo.length();
		int begLen = panLen - 13;
		String panNo = crdNo.substring(begLen, panLen - 1);
		String keyBlk = JECommand(pikSek, pinData, panNo);
		String pin = NGCommand(panNo, keyBlk);
		int i = pin.indexOf("F");
		pin = pin.substring(0, i);
		return pin;
	}
	
	public static String track(String track, String key) throws IOException {
		String decryData = track.substring(8, 16) + track.substring(track.length() - 8);
		if (track.length() < 24) {
			decryData = track.substring(track.length() - 16, track.length());
		}
		String outData = E0Command(key, decryData);
		outData = outData.replaceAll("F", "");
		System.out.println(outData);
		if (track.length() < 24) {
			if (track.length() > 16) {
				track = track.substring(0, track.length() - 16) + outData;
			} else {
				track = outData;
			}
		} else {
			track = track.substring(0, 8) + outData.substring(0, 8) + track.substring(16, track.length() - 8)
					+ outData.substring(8, 16);
		}
		return track;
	}

	public static String body(StringBuffer req) throws IOException {
		byte[] reqBytes = req.toString().toUpperCase().getBytes();
		byte[] reqHeadBytes = ByteUtil.decimalToBcd(String.format("%04x", reqBytes.length));
		out.write(reqHeadBytes);
		out.write(reqBytes);
		out.flush();
		byte[] lenBytes = new byte[2];
		in.read(lenBytes);
		short len = ByteUtil.bytesToShort(lenBytes);
		byte[] resultBytes = new byte[len];
		in.read(resultBytes);
		String body = new String(resultBytes, 4, len - 4);
		return body;
	}

	public static String E0Command(String key, String crdNo) throws IOException {
		StringBuffer req = new StringBuffer("E00110");
		req.append(key);
		req.append("11000000008");
		req.append(crdNo);
		String body = body(req);
		String respOutPattern = body.substring(0, 1);
		String respMsgDataLen = body.substring(1, 3);
		String respMsgDataValue = body.substring(4);
		return respMsgDataValue;
	}

	public static String NGCommand(String panNo, String keyBlk) throws IOException {
		StringBuffer req = new StringBuffer("NG");
		req.append(panNo);
		req.append(keyBlk);
		String body = body(req);
		return body;
	}

	public static String JECommand(String pikSek, String pinData, String panNo) throws IOException {
		StringBuffer req = new StringBuffer("JEX");
		req.append(pikSek);
		req.append(pinData);
		req.append("01");
		req.append(panNo);
		String body = body(req);
		return body;
	}

}
 
