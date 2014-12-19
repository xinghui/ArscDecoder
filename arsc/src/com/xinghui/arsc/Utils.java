package com.xinghui.arsc;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;

/**
 * Created by hugo on 2014/11/3.
 */
public class Utils {

    public static boolean PRINT_HEX = true;
    private static int size = 0;

    public static int getSize() {
        return size;
    }

    public static ByteOrder BO = ByteOrder.nativeOrder();
//    public static ByteOrder BO = ByteOrder.BIG_ENDIAN;

    private static final char[] HEX_DIGITS =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static void printHEX(String s, byte[] data) {
        if (!PRINT_HEX) return;
//        if (s != null) {
//            System.out.printf("%20s : ", s);
//        }
        for (byte b : data) {
            char[] result = new char[2];
            result[0] = HEX_DIGITS[(b >> 4) & 0xf];
            result[1] = HEX_DIGITS[b & 0xf];
            System.out.printf("%s ", new String(result));
        }
    }

    public static void printHEX(String des, byte[] data, Object obj) {
        if (!PRINT_HEX) return;
        if (des != null) {
            System.out.printf("%20s : ", des);
        } else {
            System.out.print("                    ");
        }
        for (byte b : data) {
            char[] result = new char[2];
            result[0] = HEX_DIGITS[(b >> 4) & 0xf];
            result[1] = HEX_DIGITS[b & 0xf];
            System.out.printf("%s ", new String(result));
        }
        if (obj != null) {
            System.out.println("          " + obj);
        } else {
            System.out.println();
        }
    }

    /**
     * Print File Bytes by 0x
     *
     * @param in
     */
    public static void printFileByte(InputStream in) {
        BufferedSource pngSource = Okio.buffer(Okio.source(in));
        int size = 0;
        try {

            byte[] data = pngSource.readByteArray();
            for (byte b : data) {
                char[] result = new char[2];
                result[0] = HEX_DIGITS[(b >> 4) & 0xf];
                result[1] = HEX_DIGITS[b & 0xf];
                if (size >= 16) {
                    System.out.println();
                    size = 0;
                }
                System.out.printf("%s ", new String(result));
                size++;
            }
            System.out.println();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int toInt(byte[] data) {
        ByteBuffer bb = ByteBuffer.wrap(data);
        bb.order(BO);
        return bb.getInt();
    }

    private static short toShort(byte[] data) {
        ByteBuffer bb = ByteBuffer.wrap(data);
        bb.order(BO);//java.nio.HeapByteBuffer.getInt()
        return bb.getShort();
    }



//    public static String printHEXwithStringBase64(String s, ByteString byteString) {
//        byte[] data = byteString.toByteArray();
////        System.out.printf("%20s",s);
////        printHEX(data);
////        System.out.printf("    %20c%n", c);
//        String ss = null;
//        try {
//            ss = new String(data, 0, data.length, "UTF-16");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
////        return byteString.base64();
//        return ss;
//    }

    public static byte readByte(String s, BufferedSource source) throws IOException {
        byte b = source.readByte();
        size += 1;
        printHEX(s, new byte[]{b}, b);
        return b;
    }

    public static byte[] readByteArray(String s, BufferedSource source, int byteCount) throws IOException {
        byte[] b = source.readByteArray(byteCount);
        size += byteCount;
        printHEX(s, b, null);
        return b;
    }

    public static short readShort(String s, BufferedSource source) throws IOException {
        ByteString bs = source.readByteString(2);
        size += 2;
        short result;
        byte[] data = bs.toByteArray();
        result = toShort(data);
        printHEX(s, data, result);
        return result;
    }

    public static int readInt(String s, BufferedSource source) throws IOException {
        ByteString bs = source.readByteString(4);
        size += 4;
        int result;
        byte[] data = bs.toByteArray();
        result = toInt(data);
        printHEX(s, data, result);
        return result;
    }

    public static char readChar(String s, BufferedSource source, boolean debug) throws IOException {
        ByteString bs = source.readByteString(2);
        size += 2;
        byte[] data = bs.toByteArray();
        ByteBuffer bb = ByteBuffer.wrap(data);
        bb.order(BO);
        char c = bb.getChar();
        if (debug) {
            printHEX(s, data, c);
        } else {
            printHEX(s,data);
        }
        return c;
    }

    public static String readString(String s, BufferedSource source, int byteCount) throws IOException {
        ByteString bs = source.readByteString(byteCount);
        size += byteCount;
        byte[] data = bs.toByteArray();
        String str = bs.utf8();
        printHEX(s,data, str);
        return str;
    }

    public static String readStringUTF16(String s, BufferedSource source, int byteCount) throws IOException {
        char[] entryBytes = new char[byteCount];
        for (int j = 0; j < byteCount; j++) {
            char c = Utils.readChar(s, source, false);
            entryBytes[j] = c;
        }

        String entry = new String(entryBytes).intern().trim();// need trim()??

        System.out.println();
        System.out.println(entry);

        return entry;
    }

    public static String readStringUTF8(String s, BufferedSource source, int lenSize) throws IOException {
        System.out.println("Read size = " + Integer.toHexString(size) + ",---lenSize = " + lenSize);
        String read = source.readUtf8(lenSize);

        size += lenSize;

        System.out.println();
        System.out.println("--------------------------------------");

//        printHEX(s, data);
        System.out.println("--------------------------------------");
//        String entry = new String(data, "UTF-8");

//        return data.utf8();
        return read;
    }

    public static void skip(BufferedSource source, int length) throws IOException {
        byte[] data = source.readByteArray(length);
        size += length;
        printHEX("SKIP-" + length, data, null);
    }

    public static int[] readIntArray(BufferedSource source, int count) throws IOException {
        int[] result = new int[count];
        for (int i = 0; i < count; i++) {
            result[i] = readInt("intArray{" + i + "]", source);
        }
        return result;
    }


//    /**
//     *
//     * via:http://www.java2s.com/Code/Java/Language-Basics/Utilityforbyteswappingofalljavadatatypes.htm
//     *
//     * Byte swap a single short value.
//     *
//     * @param value Value to byte swap.
//     * @return Byte swapped representation.
//     */
//    public static short swapBtoL(short value) {
//        int b1 = value & 0xff;
//        int b2 = (value >> 8) & 0xff;
//
//        return (short) (b1 << 8 | b2 << 0);
//    }

}
