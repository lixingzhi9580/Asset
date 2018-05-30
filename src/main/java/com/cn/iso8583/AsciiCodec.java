package com.cn.iso8583;

import java.io.UnsupportedEncodingException;
import java.security.InvalidParameterException;

import org.apache.commons.lang3.StringUtils;


public class AsciiCodec {

	final static char[] ascii = "0123456789ABCDEF".toCharArray();

	public static int binary2Int(String bin) {
		return Integer.valueOf(bin, 2).intValue();
	}

	public static String int2Binary(int val) {
		return Integer.toBinaryString(val);
	}

	public static String hex2Binary(String val) throws CodecException {
		StringBuffer ret = new StringBuffer();

		for (int i = 0; i < val.length(); i++) {
			ret.append(hex2binary(val.charAt(i)));
		}
		return ret.toString();
	}

	public static String hex2binary(char hex) throws CodecException {
		switch (hex) {
		case '0':
			return "0000";
		case '1':
			return "0001";
		case '2':
			return "0010";
		case '3':
			return "0011";
		case '4':
			return "0100";
		case '5':
			return "0101";
		case '6':
			return "0110";
		case '7':
			return "0111";
		case '8':
			return "1000";
		case '9':
			return "1001";
		case 'a':
		case 'A':
			return "1010";
		case 'b':
		case 'B':
			return "1011";
		case 'c':
		case 'C':
			return "1100";
		case 'd':
		case 'D':
			return "1101";
		case 'e':
		case 'E':
			return "1110";
		case 'f':
		case 'F':
			return "1111";
		default:
			throw new CodecException("Unrecogonized character: " + hex);
		}
	}

	public static String binary2hex(String binary) {
		String hexString = "";
		int binLen = binary.length();
		if (binLen % 4 != 0) {
			binary = StringUtils.repeat("0", 4 - binLen % 4) + binary;
			binLen = binary.length();
		}
		for (int i = 0; i < binLen; i = i + 4) {
			hexString += Integer.toHexString(Integer.valueOf(
					binary.substring(i, i + 4), 2).intValue());
		}
		return hexString;
	}

	public static String byte2String(byte val) throws CodecException {
		byte[] arrVal = { val };
		return byte2String(arrVal, "ISO-8859-1");
	}

	public static String byte2String(byte[] val, String charset)
			throws CodecException {
		try {
			return new String(val, charset);
		} catch (UnsupportedEncodingException uee) {
			throw new CodecException("Unsupported encoding: " + charset);
		}
	}

	public static String binToAscStr(String binStr) {
		return binToAscStr(binStr.getBytes());
	}

	public static String binToAscStr(byte[] binBuf) {
		long ascVal = 0;
		for (int i = 0; i < binBuf.length; i++) {
			ascVal = (ascVal << 8) + (long) (binBuf[i] & 0xff);
		}

		return String.valueOf(ascVal);
	}

	public static String asc2bin(String strAsc) throws CodecException {
		Integer deliInt = Integer.valueOf(strAsc);
		if (deliInt.intValue() > 255 || deliInt.intValue() < -128) {
			throw new CodecException("Ascii string is out of scope: " + strAsc);
		}

		byte[] asc = { deliInt.byteValue() };
		try {
			return new String(asc, "ISO-8859-1");
		} catch (UnsupportedEncodingException uee) {
			throw new CodecException("Unsupported encoding: ISO-8859-1");
		}
	}

	public static String asc2bin(int intAsc) throws CodecException {
		byte[] aryAsc = new byte[4];
		int2byte(aryAsc, 0, intAsc);
		return byte2String(aryAsc, "ISO-8859-1");
	}

	public static String asc2bin(int intAsc, int binLen) throws CodecException {
		byte[] aryAsc = new byte[4];

		int2byte(aryAsc, 0, intAsc);
		String retStr = "";

		boolean start = true;
		for (int i = 0; i < 4; i++) {
			if (aryAsc[i] == 0x00 && start) {
				continue;
			}
			start = false;
			retStr += (char) aryAsc[i];
		}

		if (retStr.length() < binLen) {
			char fill_asc = (byte) 0x00;
			String fill_str = String.valueOf(fill_asc);

			retStr = StringUtils.repeat(fill_str, binLen - retStr.length())
					+ retStr;
		} else if (retStr.length() > binLen) {
			throw new CodecException(
					"String length is greator than expected length, actual: "
							+ retStr.length() + ", expected: " + binLen);
		}

		return retStr;
	}

	
	public static String bcd2AscStr(byte[] bytes) throws CodecException {
		return ascii2Str(bcd2Ascii(bytes));
	}

	public static byte[] ascStr2Bcd(String s) {
		return ascii2Bcd(str2Ascii(s));
	}

	public static byte[] bcd2Ascii(byte[] bytes) {
		byte[] temp = new byte[bytes.length * 2];

		for (int i = 0; i < bytes.length; i++) {
			temp[i * 2] = (byte) ((bytes[i] >> 4) & 0x0f);
			temp[i * 2 + 1] = (byte) (bytes[i] & 0x0f);

		}
		return temp;
	}

	public static byte[] str2Ascii(String s) {
		byte[] str = s.toUpperCase().getBytes();
		byte[] ascii = new byte[str.length];
		for (int i = 0; i < ascii.length; i++) {
			ascii[i] = (byte) asciiValue(str[i]);
		}
		return ascii;
	}

	public static String ascii2Str(byte[] ascii) throws CodecException {
		StringBuffer res = new StringBuffer();
		for (int i = 0; i < ascii.length; i++) {
			res.append(strValue(ascii[i]));
		}
		return res.toString();
	}

	private static char strValue(byte asc) throws CodecException {
		if (asc < 0 || asc > 15)
			throw new CodecException("Unrecogonized ascii value, actual: "
					+ asc + ", expected: 0 - 15");
		return ascii[asc];
	}

	public static byte[] ascii2Bcd(byte[] asc) {
		int len = asc.length / 2;
		byte[] bcd = new byte[len];
		for (int i = 0; i < len; i++) {
			bcd[i] = (byte) ((asc[2 * i] << 4) | asc[2 * i + 1]);
		}
		return bcd;
	}

	private static int asciiValue(byte b) {
		if ((b >= '0') && (b <= '9')) {
			return (b - '0');
		}
		if ((b >= 'a') && (b <= 'f')) {
			return (b - 'a') + 0x0a;
		}
		if ((b >= 'A') && (b <= 'F')) {
			return (b - 'A') + 0x0a;
		}

		throw new InvalidParameterException();
	}

	public static void printByte(byte[] b) {
		for (int i = 0; i < b.length; i++) {
			System.out.print(b[i] + " ");
		}
		System.out.println();
	}

	// ---------------------------------------------------------------------
	/**
	 * Converts two bytes into a short.
	 * 
	 * The short should be interpreted as an unsigned short and if you want to
	 * add it to an int then do something like the code fragment below.
	 * 
	 * <PRE>
	 * int sum = 0;
	 * sum += byte2short(buf, index) &amp; 0xffff;
	 * </PRE>
	 */
	public static short byte2short(byte[] bp, int index) {
		return (short) (((bp[index] & 0xff) << 8) + (bp[index + 1] & 0xff));
	}

	/**
	 * Converts four bytes into an int.
	 */
	public static int byte2int(byte[] bp, int index) {
		return (int) (((bp[index] & 0xff) << 24)
				+ ((bp[index + 1] & 0xff) << 16)
				+ ((bp[index + 2] & 0xff) << 8) + ((bp[index + 3] & 0xff)));
	}

	/**
	 * Convert short into two bytes in buffer.
	 **/
	public static void short2byte(byte[] bp, int index, short value) {
		bp[index] = (byte) ((value >> 8) & 0xff);
		bp[index + 1] = (byte) (value & 0xff);
	}

	/**
	 * Convert int into four bytes in buffer.
	 **/
	public static void int2byte(byte[] bp, int index, int value) {
		bp[index] = (byte) ((value >> 24) & 0xff);
		bp[index + 1] = (byte) ((value >> 16) & 0xff);
		bp[index + 2] = (byte) ((value >> 8) & 0xff);
		bp[index + 3] = (byte) (value & 0xff);
	}

	/**
	 * Convert an integer to an unsigned integer, represented by a long type.
	 **/
	public static long int2uint(int x) {
		return ((((long) x) << 32) >>> 32);
	}

	/**
	 * Convert an integer to an unsigned integer, represented by a long type.
	 **/
	public static long byte2uint(byte[] x, int offs) {
		long z = 0;
		for (int i = 0; i < 4; i++) {
			z = (z << 8) + (long) (x[offs + i] & 0xff);
		}
		return z;
	}

	/**
	 * Convert an array of two long integers to an byte array
	 **/
	public static byte[] uint2byte(long[] x) {
		byte[] res = new byte[8];
		int2byte(res, 0, (int) (x[0]));
		int2byte(res, 4, (int) (x[1]));
		return res;
	}

	/**
	 * Convert an array of a long integer to an byte array
	 **/
	public static byte[] long2byte(long x) {
		byte[] res = new byte[8];
		int2byte(res, 0, (int) ((x >> 32) & 0xffffffff));
		int2byte(res, 4, (int) (x & 0xffffffff));
		return res;
	}

	public static long byte2long(byte[] msg, int offs) {
		long high = byte2uint(msg, offs);
		offs += 4;
		long low = byte2uint(msg, offs);
		offs += 4;
		long ans = (high << 32) + low;
		return ans;
	}

	/*
	 * Make printable form for boolean array
	 */
	public static String boolean2String(boolean[] ba) {
		StringBuffer strb = new StringBuffer();
		int cnt = 0;

		if (ba == null || ba.length == 0) {
			return "(none)";
		}

		for (int i = 0; i < ba.length; i++) {
			if (ba[i]) {
				if (cnt++ != 0) {
					strb.append("+");
				}
				strb.append(i);
			}
		}
		return strb.toString();
	}

	public static String convFlags(String equiv, byte flags) {
		char chs[] = new char[8];
		StringBuffer strb = new StringBuffer(" ");
		int bit, i;

		if (equiv.length() > 8) {
			return (">8?");
		}
		equiv.getChars(0, equiv.length(), chs, 0);

		for (bit = 0x80, i = 0; bit != 0; bit >>= 1, i++) {
			if ((flags & bit) != 0) {
				strb.setCharAt(0, '*');
				strb.append(chs[i]);
			}
		}
		return strb.toString();
	}

	public static String time2string(long time) {
		String timeString = null;

		long msec = time % 1000;
		String ms = String.valueOf(msec);
		ms = fill(ms, 3, "0");

		long rem = time / 1000; // in seconds
		int xsec = (int) (rem % 60);
		rem = (int) ((rem - xsec) / 60); // in minutes
		int xmin = (int) (rem % 60);
		rem = (int) ((rem - xmin) / 60); // in hours
		int xhour = (int) (rem % 24);
		int xday = (int) ((rem - xhour) / 24);

		String sday = String.valueOf(xday);
		String shour = String.valueOf(xhour);
		shour = fill(shour, 2, "0");
		String smin = String.valueOf(xmin);
		smin = fill(smin, 2, "0");
		String ssec = String.valueOf(xsec);
		ssec = fill(ssec, 2, "0");

		timeString = sday + " days, " + shour + ":" + smin + ":" + ssec + "."
				+ ms;
		return timeString;
	}

	private static String fill(String str, int sz, String cfill) {
		while (str.length() < sz) {
			str = cfill + str;
		}
		return str;
	}

	public static byte[] hexToByteArray(String hexa) throws CodecException {
		if (hexa == null) {
			throw new CodecException("hex string is null.");
		}
		if ((hexa.length() % 2) != 0) {
			throw new CodecException(
					"hex string's length must be even, actual: "
							+ hexa.length());
		}

		byte[] b = new byte[hexa.length() / 2];
		for (int i = 0, j = 0; j < b.length; j++) {
			b[j] = (byte) Integer.parseInt(hexa.substring(i, i + 2), 16);
			i += 2;
		}
		return b;

	}

	public static String byteArrayToHex(byte[] bytes) {
		String retorno = "";
		if (bytes == null || bytes.length == 0) {
			return retorno;
		}
		for (int i = 0; i < bytes.length; i++) {
			byte valor = bytes[i];
			int d1 = valor & 0xF;
			d1 += (d1 < 10) ? 48 : 55;
			int d2 = (valor & 0xF0) >> 4;
			d2 += (d2 < 10) ? 48 : 55;
			retorno = retorno + (char) d2 + (char) d1;
		}
		return retorno;
	}
	public static byte[] decodeHex(byte[] array) throws CodecException {
		char[] data = null;
		try {
			data = new String(array, "UTF-8").toCharArray();
		} catch (UnsupportedEncodingException e) {
			throw new CodecException("byte to string(UTF-8) failed",e);
		}
		int len = data.length;
          if ((len & 0x01) != 0) {
              throw new CodecException("Odd number of characters.");
          }
          byte[] out = new byte[len >> 1];
          for (int i = 0, j = 0; j < len; i++) {
              int f = toDigit(data[j], j) << 4;
              j++;
              f = f | toDigit(data[j], j);
              j++;
              out[i] = (byte) (f & 0xFF);
          }
          return out;
	}
      protected static int toDigit(char ch, int index) throws CodecException {
          int digit = Character.digit(ch, 16);
          if (digit == -1) {
              throw new CodecException("Illegal hexadecimal charcter " + ch + " at index " + index);
          }
          return digit;
      }	
      
	public static String hexString2binaryString(String hexString) {
		if (hexString == null || hexString.length() % 2 != 0)
			return null;
		String bString = "";
		String tmp = "";
		for (int i = 0; i < hexString.length(); i++) {
			tmp = "0000"
					+ Integer.toBinaryString(Integer.parseInt(
							hexString.substring(i, i + 1), 16));
			bString += tmp.substring(tmp.length() - 4);
		}
		return bString;
	}
	public static void main(String[] args) throws CodecException {
		System.out.println(new String(AsciiCodec.hexToByteArray("373030303030303031313931303236")));
	}
}
