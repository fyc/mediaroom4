package com.sunmnet.mediaroom.common.tools;

public class HexUtil {


    /**
     * Hex字符串转byte
     */
    public static byte hexToByte(String hex) {
        return (byte) Integer.parseInt(hex, 16);
    }

    /**
     * 字节转十六进制
     */
    public static String byteToHex(byte b) {
        String hex = Integer.toHexString(b & 0xFF);
        if (hex.length() < 2) {
            hex = "0" + hex;
        }
        return hex.toUpperCase();
    }

    /**
     * hex转byte数组
     */
    public static byte[] hexToBytes(String hex) {
        hex = hex.length() % 2 != 0 ? "0" + hex : hex;
        byte[] b = new byte[hex.length() / 2];
        for (int i = 0; i < b.length; i++) {
            int index = i * 2;
            int v = Integer.parseInt(hex.substring(index, index + 2), 16);
            b[i] = (byte) v;
        }
        return b;
    }

    /**
     * 字节数组转hex字符串
     */
    public static String bytesToHex(byte[] b, int start, int len) {
        StringBuilder ret = new StringBuilder();
        for (int i = start; i < start + len; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            ret.append(hex.toUpperCase());
        }
        return ret.toString();
    }

    /**
     * 字节数组转hex字符串
     */
    public static String bytesToHex(byte[] b) {
        return bytesToHex(b, 0, b.length);
    }

    /**
     * 字节数组转hex字符串对应的int数值
     */
    public static int bytesToHexInteger(byte[] b, int start, int len) {
        return Integer.parseInt(bytesToHex(b, start, len), 16);
    }

}
