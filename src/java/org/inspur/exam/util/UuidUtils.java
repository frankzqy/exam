package org.inspur.exam.util;

import java.util.UUID;

public class UuidUtils {

	public static String getDefaultUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static String uuid() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	protected static void long2bytes(long value, byte[] bytes, int offset) {
		for (int i = 7; i > -1; i--) {
			bytes[offset++] = (byte) ((value >> 8 * i) & 0xFF);
		}
	}

	protected static long bytes2long(byte[] bytes, int offset) {
		long value = 0;
		for (int i = 7; i > -1; i--) {
			value |= (((long) bytes[offset++]) & 0xFF) << 8 * i;
		}
		return value;
	}

}
