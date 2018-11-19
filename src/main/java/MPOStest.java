import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class MPOStest {
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
	
	public static void main(String[] args) {
		
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
}
