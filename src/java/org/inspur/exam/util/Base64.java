package org.inspur.exam.util;

import java.io.ByteArrayOutputStream;

public class Base64{
	private static char[] base64EncodeChars = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
			'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
			'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/' };

	private static byte[] base64DecodeChars = new byte[] { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55,
			56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
			21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46,
			47, 48, 49, 50, 51, -1, -1, -1, -1, -1 };

	private Base64()
	{
	}

	public static String encode(byte[] data)
	{
		StringBuffer sb = new StringBuffer();
		int len = data.length;
		int i = 0;
		int b1, b2, b3;

		while (i < len)
		{
			b1 = data[i++] & 0xff;
			if (i == len)
			{
				sb.append(base64EncodeChars[b1 >>> 2]);
				sb.append(base64EncodeChars[(b1 & 0x3) << 4]);
				sb.append("==");
				break;
			}
			b2 = data[i++] & 0xff;
			if (i == len)
			{
				sb.append(base64EncodeChars[b1 >>> 2]);
				sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
				sb.append(base64EncodeChars[(b2 & 0x0f) << 2]);
				sb.append("=");
				break;
			}
			b3 = data[i++] & 0xff;
			sb.append(base64EncodeChars[b1 >>> 2]);
			sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
			sb.append(base64EncodeChars[((b2 & 0x0f) << 2) | ((b3 & 0xc0) >>> 6)]);
			sb.append(base64EncodeChars[b3 & 0x3f]);
		}
		return sb.toString();
	}

	public static byte[] decode(String str)
	{
		byte[] data = str.getBytes();
		int len = data.length;
		ByteArrayOutputStream buf = new ByteArrayOutputStream(len);
		int i = 0;
		int b1, b2, b3, b4;

		while (i < len)
		{

			/* b1 */
			do
			{
				b1 = base64DecodeChars[data[i++]];
			}
			while (i < len && b1 == -1);
			if (b1 == -1)
			{
				break;
			}

			/* b2 */
			do
			{
				b2 = base64DecodeChars[data[i++]];
			}
			while (i < len && b2 == -1);
			if (b2 == -1)
			{
				break;
			}
			buf.write((int) ((b1 << 2) | ((b2 & 0x30) >>> 4)));

			/* b3 */
			do
			{
				b3 = data[i++];
				if (b3 == 61)
				{
					return buf.toByteArray();
				}
				b3 = base64DecodeChars[b3];
			}
			while (i < len && b3 == -1);
			if (b3 == -1)
			{
				break;
			}
			buf.write((int) (((b2 & 0x0f) << 4) | ((b3 & 0x3c) >>> 2)));

			/* b4 */
			do
			{
				b4 = data[i++];
				if (b4 == 61)
				{
					return buf.toByteArray();
				}
				b4 = base64DecodeChars[b4];
			}
			while (i < len && b4 == -1);
			if (b4 == -1)
			{
				break;
			}
			buf.write((int) (((b3 & 0x03) << 6) | b4));
		}
		return buf.toByteArray();
	}

	public static void main(String[] args)
	{
		Base64 base64 = new Base64();
		
		String testStr = "[{\"AREA_NAME\":\"济南市\",\"NUM\":106,\"SCENIC_LEVEL_ID\":\"0\"},{\"AREA_NAME\":\"济南市\",\"NUM\":1,\"SCENIC_LEVEL_ID\":\"1\"},{\"AREA_NAME\":\"济南市\",\"NUM\":6,\"SCENIC_LEVEL_ID\":\"2\"},{\"AREA_NAME\":\"济南市\",\"NUM\":6,\"SCENIC_LEVEL_ID\":\"3\"},{\"AREA_NAME\":\"济南市\",\"NUM\":12,\"SCENIC_LEVEL_ID\":\"4\"},{\"AREA_NAME\":\"济南市\",\"NUM\":4,\"SCENIC_LEVEL_ID\":\"5\"},{\"AREA_NAME\":\"青岛市\",\"NUM\":123,\"SCENIC_LEVEL_ID\":\"0\"},{\"AREA_NAME\":\"青岛市\",\"NUM\":2,\"SCENIC_LEVEL_ID\":\"1\"},{\"AREA_NAME\":\"青岛市\",\"NUM\":14,\"SCENIC_LEVEL_ID\":\"2\"},{\"AREA_NAME\":\"青岛市\",\"NUM\":30,\"SCENIC_LEVEL_ID\":\"3\"},{\"AREA_NAME\":\"青岛市\",\"NUM\":20,\"SCENIC_LEVEL_ID\":\"4\"},{\"AREA_NAME\":\"青岛市\",\"NUM\":1,\"SCENIC_LEVEL_ID\":\"5\"},{\"AREA_NAME\":\"淄博市\",\"NUM\":1,\"SCENIC_LEVEL_ID\":null},{\"AREA_NAME\":\"淄博市\",\"NUM\":78,\"SCENIC_LEVEL_ID\":\"0\"},{\"AREA_NAME\":\"淄博市\",\"NUM\":12,\"SCENIC_LEVEL_ID\":\"2\"},{\"AREA_NAME\":\"淄博市\",\"NUM\":12,\"SCENIC_LEVEL_ID\":\"3\"},{\"AREA_NAME\":\"淄博市\",\"NUM\":11,\"SCENIC_LEVEL_ID\":\"4\"},{\"AREA_NAME\":\"枣庄市\",\"NUM\":62,\"SCENIC_LEVEL_ID\":\"0\"},{\"AREA_NAME\":\"枣庄市\",\"NUM\":10,\"SCENIC_LEVEL_ID\":\"2\"},{\"AREA_NAME\":\"枣庄市\",\"NUM\":8,\"SCENIC_LEVEL_ID\":\"3\"},{\"AREA_NAME\":\"枣庄市\",\"NUM\":8,\"SCENIC_LEVEL_ID\":\"4\"},{\"AREA_NAME\":\"枣庄市\",\"NUM\":1,\"SCENIC_LEVEL_ID\":\"5\"},{\"AREA_NAME\":\"东营市\",\"NUM\":32,\"SCENIC_LEVEL_ID\":\"0\"},{\"AREA_NAME\":\"东营市\",\"NUM\":16,\"SCENIC_LEVEL_ID\":\"2\"},{\"AREA_NAME\":\"东营市\",\"NUM\":6,\"SCENIC_LEVEL_ID\":\"3\"},{\"AREA_NAME\":\"东营市\",\"NUM\":2,\"SCENIC_LEVEL_ID\":\"4\"},{\"AREA_NAME\":\"烟台市\",\"NUM\":248,\"SCENIC_LEVEL_ID\":\"0\"},{\"AREA_NAME\":\"烟台市\",\"NUM\":1,\"SCENIC_LEVEL_ID\":\"1\"},{\"AREA_NAME\":\"烟台市\",\"NUM\":17,\"SCENIC_LEVEL_ID\":\"2\"},{\"AREA_NAME\":\"烟台市\",\"NUM\":13,\"SCENIC_LEVEL_ID\":\"3\"},{\"AREA_NAME\":\"烟台市\",\"NUM\":17,\"SCENIC_LEVEL_ID\":\"4\"},{\"AREA_NAME\":\"烟台市\",\"NUM\":3,\"SCENIC_LEVEL_ID\":\"5\"},{\"AREA_NAME\":\"潍坊市\",\"NUM\":120,\"SCENIC_LEVEL_ID\":\"0\"},{\"AREA_NAME\":\"潍坊市\",\"NUM\":9,\"SCENIC_LEVEL_ID\":\"2\"},{\"AREA_NAME\":\"潍坊市\",\"NUM\":27,\"SCENIC_LEVEL_ID\":\"3\"},{\"AREA_NAME\":\"潍坊市\",\"NUM\":16,\"SCENIC_LEVEL_ID\":\"4\"},{\"AREA_NAME\":\"潍坊市\",\"NUM\":1,\"SCENIC_LEVEL_ID\":\"5\"},{\"AREA_NAME\":\"济宁市\",\"NUM\":90,\"SCENIC_LEVEL_ID\":\"0\"},{\"AREA_NAME\":\"济宁市\",\"NUM\":6,\"SCENIC_LEVEL_ID\":\"2\"},{\"AREA_NAME\":\"济宁市\",\"NUM\":21,\"SCENIC_LEVEL_ID\":\"3\"},{\"AREA_NAME\":\"济宁市\",\"NUM\":10,\"SCENIC_LEVEL_ID\":\"4\"},{\"AREA_NAME\":\"济宁市\",\"NUM\":1,\"SCENIC_LEVEL_ID\":\"5\"},{\"AREA_NAME\":\"泰安市\",\"NUM\":121,\"SCENIC_LEVEL_ID\":\"0\"},{\"AREA_NAME\":\"泰安市\",\"NUM\":8,\"SCENIC_LEVEL_ID\":\"2\"},{\"AREA_NAME\":\"泰安市\",\"NUM\":14,\"SCENIC_LEVEL_ID\":\"3\"},{\"AREA_NAME\":\"泰安市\",\"NUM\":7,\"SCENIC_LEVEL_ID\":\"4\"},{\"AREA_NAME\":\"泰安市\",\"NUM\":1,\"SCENIC_LEVEL_ID\":\"5\"},{\"AREA_NAME\":\"威海市\",\"NUM\":48,\"SCENIC_LEVEL_ID\":\"0\"},{\"AREA_NAME\":\"威海市\",\"NUM\":1,\"SCENIC_LEVEL_ID\":\"2\"},{\"AREA_NAME\":\"威海市\",\"NUM\":9,\"SCENIC_LEVEL_ID\":\"3\"},{\"AREA_NAME\":\"威海市\",\"NUM\":8,\"SCENIC_LEVEL_ID\":\"4\"},{\"AREA_NAME\":\"威海市\",\"NUM\":1,\"SCENIC_LEVEL_ID\":\"5\"},{\"AREA_NAME\":\"日照市\",\"NUM\":13,\"SCENIC_LEVEL_ID\":null},{\"AREA_NAME\":\"日照市\",\"NUM\":110,\"SCENIC_LEVEL_ID\":\"0\"},{\"AREA_NAME\":\"日照市\",\"NUM\":4,\"SCENIC_LEVEL_ID\":\"2\"},{\"AREA_NAME\":\"日照市\",\"NUM\":3,\"SCENIC_LEVEL_ID\":\"3\"},{\"AREA_NAME\":\"日照市\",\"NUM\":8,\"SCENIC_LEVEL_ID\":\"4\"},{\"AREA_NAME\":\"莱芜市\",\"NUM\":43,\"SCENIC_LEVEL_ID\":\"0\"},{\"AREA_NAME\":\"莱芜市\",\"NUM\":3,\"SCENIC_LEVEL_ID\":\"2\"},{\"AREA_NAME\":\"莱芜市\",\"NUM\":3,\"SCENIC_LEVEL_ID\":\"3\"},{\"AREA_NAME\":\"莱芜市\",\"NUM\":3,\"SCENIC_LEVEL_ID\":\"4\"},{\"AREA_NAME\":\"临沂市\",\"NUM\":57,\"SCENIC_LEVEL_ID\":\"0\"},{\"AREA_NAME\":\"临沂市\",\"NUM\":18,\"SCENIC_LEVEL_ID\":\"2\"},{\"AREA_NAME\":\"临沂市\",\"NUM\":24,\"SCENIC_LEVEL_ID\":\"3\"},{\"AREA_NAME\":\"临沂市\",\"NUM\":16,\"SCENIC_LEVEL_ID\":\"4\"},{\"AREA_NAME\":\"临沂市\",\"NUM\":1,\"SCENIC_LEVEL_ID\":\"5\"},{\"AREA_NAME\":\"德州市\",\"NUM\":37,\"SCENIC_LEVEL_ID\":\"0\"},{\"AREA_NAME\":\"德州市\",\"NUM\":24,\"SCENIC_LEVEL_ID\":\"2\"},{\"AREA_NAME\":\"德州市\",\"NUM\":4,\"SCENIC_LEVEL_ID\":\"3\"},{\"AREA_NAME\":\"德州市\",\"NUM\":5,\"SCENIC_LEVEL_ID\":\"4\"},{\"AREA_NAME\":\"聊城市\",\"NUM\":10,\"SCENIC_LEVEL_ID\":\"0\"},{\"AREA_NAME\":\"聊城市\",\"NUM\":13,\"SCENIC_LEVEL_ID\":\"2\"},{\"AREA_NAME\":\"聊城市\",\"NUM\":5,\"SCENIC_LEVEL_ID\":\"3\"},{\"AREA_NAME\":\"聊城市\",\"NUM\":6,\"SCENIC_LEVEL_ID\":\"4\"},{\"AREA_NAME\":\"滨州市\",\"NUM\":13,\"SCENIC_LEVEL_ID\":\"0\"},{\"AREA_NAME\":\"滨州市\",\"NUM\":11,\"SCENIC_LEVEL_ID\":\"3\"},{\"AREA_NAME\":\"滨州市\",\"NUM\":2,\"SCENIC_LEVEL_ID\":\"4\"},{\"AREA_NAME\":\"菏泽市\",\"NUM\":9,\"SCENIC_LEVEL_ID\":\"0\"},{\"AREA_NAME\":\"菏泽市\",\"NUM\":1,\"SCENIC_LEVEL_ID\":\"1\"},{\"AREA_NAME\":\"菏泽市\",\"NUM\":6,\"SCENIC_LEVEL_ID\":\"2\"},{\"AREA_NAME\":\"菏泽市\",\"NUM\":3,\"SCENIC_LEVEL_ID\":\"3\"},{\"AREA_NAME\":\"菏泽市\",\"NUM\":2,\"SCENIC_LEVEL_ID\":\"4\"}]";
		System.out.println("加密前：" + testStr);
		
		String encodeStr = base64.encode(testStr.getBytes());
		System.out.println("加密数据：" + encodeStr);
		
		byte[] decodeStr = base64.decode(encodeStr);
		System.out.println("解密数据：" + new String(decodeStr));
	}
}
