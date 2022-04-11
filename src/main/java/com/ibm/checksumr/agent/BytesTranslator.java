package com.ibm.checksumr.agent;

public class BytesTranslator {

	public static String toHex(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < bytes.length; i++) {
			sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		
		return sb.toString();
	}

}
