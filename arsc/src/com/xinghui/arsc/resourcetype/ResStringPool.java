package com.xinghui.arsc.resourcetype;

import com.xinghui.arsc.Utils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;

import okio.BufferedSource;

/**
 *
 * Convenience class for accessing data in a ResStringPool resource.
 *
 * Created by hugo on 2014/11/6.
 */
public class ResStringPool {

    private ArrayList<String> string_pool_list = new ArrayList<String>();



    public ResStringPool(BufferedSource source, ResStringPool_header mStringPoolHeader, boolean isTop) throws IOException {

        int skipSize = mStringPoolHeader.getStringsStart() - mStringPoolHeader.getHeader().getHeaderSize();

        int assume = mStringPoolHeader.getStringCount() * 4 + mStringPoolHeader.getStyleCount() * 4;
        if (assume != skipSize) {
            throw new AssertionError("skipSize = " + skipSize + ", assume = " + assume);
        }

        int[] mStringOffsets = Utils.readIntArray(source, mStringPoolHeader.getStringCount());

        int[] mStyleOffsets;
        if (mStringPoolHeader.getStyleCount() != 0) {
            mStyleOffsets = Utils.readIntArray(source, mStringPoolHeader.getStyleCount());
        }

        if (mStringPoolHeader.isUTF8()) {
            isTop = false;
            readString(source, mStringPoolHeader, getString_pool_list(), mStringOffsets);
        } else {
            readStringToList(source, mStringPoolHeader, getString_pool_list());
        }

        for (int k = 0; k < mStringPoolHeader.getStyleCount(); k++) {
            ResStringPool_span rsps = ResStringPool_span.build(source);
            String styleStringstr = getString_pool_list().get(k);//
            String s = getString_pool_list().get(rsps.getName().getIndex());
            String styleString = styleStringstr.substring(rsps.getFirstChar(), rsps.getLastChar() + 1);
            StringBuilder sb = new StringBuilder();
            if (s.length() > 0 && s.indexOf(';') > -1) {//<a href="http://www.google.com">Google</a>
                String[] pro = s.split(";");
                sb.append("<");
                sb.append(pro[0]);
                for (int j = 1; j < pro.length; j++) {
                    sb.append(" ");
                    if (pro[j].indexOf("href=") > -1) { // if has other Attribute???
                        String temp = pro[j];
                        StringBuilder tt = new StringBuilder();
                        tt.append(temp.substring(0, temp.indexOf("href=") + 5));
                        tt.append("\"");
                        tt.append(temp.substring(temp.indexOf("href=") + 5, temp.length()));
                        tt.append("\"");
                        sb.append(tt.toString());
                    } else {
                        sb.append(pro[j]);
                    }
                }
                sb.append(">" + styleString + "</" + s.substring(0, 1) + ">");
            } else {
                sb.append("<");
                sb.append(s);
                sb.append(">");
                sb.append(styleString);
                sb.append("</");
                sb.append(s.substring(0, 1));
                sb.append(">");
            }
            System.out.println(sb.toString());
        }
        if (isTop) {
            ResStringPool_span.END(source);
        }
    }

    private void readString(BufferedSource source, ResStringPool_header mStringPoolHeader, ArrayList<String> string_pool_list, int[] mStringOffsets) throws IOException {
        int size = ((mStringPoolHeader.getStylesStart() == 0) ? mStringPoolHeader.getHeader().getSize() : mStringPoolHeader.getStylesStart()) - mStringPoolHeader.getStringsStart();
        System.out.println("----++--size = " + size);
        if (size % 4 != 0) {
            throw new AssertionError("String data size is not multiple of 4 (" + size + ").");
        }

//        byte[] mStringData = source.readByteArray(size);
        byte[] mStringData = Utils.readByteArray("readString", source, size);

        for (int i = 0; i < mStringPoolHeader.getStringCount(); i++) {
            int offset = mStringOffsets[i];
            int length = 0;

            if (mStringPoolHeader.isUTF8()) {
                int[] val = getUtf8(mStringData, offset);
                offset = val[0];
                length = val[1];
            } else {

            }
            String entry = decodeString(mStringData, offset, length);
            System.out.println("\n entry=== " + entry);

            if (string_pool_list != null) {
                string_pool_list.add(entry);
            }
        }
    }

    private String decodeString(byte[] mStringData, int offset, int length) throws CharacterCodingException {
        CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();
        return decoder.decode(ByteBuffer.wrap(mStringData, offset, length)).toString();
    }

    private int[] getUtf8(byte[] array, int offset) {
        int val = array[offset];
        int length;

        if ((val & 0x80) != 0) {
            offset += 2;
        } else {
            offset += 1;
        }
        val = array[offset];
        if ((val & 0x80) != 0) {
            offset += 2;
        } else {
            offset += 1;
        }
        length = 0;
        while (array[offset + length] != 0) {
            length++;
        }
        return new int[]{offset, length};
    }

    private void readStringToList(BufferedSource source, ResStringPool_header mStringPoolHeader, ArrayList<String> string_pool_list) throws IOException {

        for (int i = 0; i < mStringPoolHeader.getStringCount(); i++) {
            short wordLength = Utils.readShort("wordLength: ", source);
            if (wordLength < 0) {
                throw new IOException("String Pool Word Length < 0, the value is " + wordLength);
            }
            if (mStringPoolHeader.isUTF8()) {

//                int size = ((mStringPoolHeader.getStylesStart() == 0) ? mStringPoolHeader.getHeader().getSize() : mStringPoolHeader.getStylesStart()) - mStringPoolHeader.getStringsStart();
//                System.out.println("----++--size = " + size);
//                if (size % 4 != 0) {
//                    throw new AssertionError("String data size is not multiple of 4 (" + size + ").");
//                }
//
//                String entry = Utils.readStringUTF8("!!!", source, size);
//
//
////                source.skip(1);//NULL Symbol.
////                Utils.skip(source, 1);
//                Utils.readByte("NULL", source);
//                System.out.println(entry);
            } else {
                String entry = Utils.readStringUTF16("name", source, wordLength);
                /**
                 * UTF-8: 0x00;     1bytes
                 * UTF-16: 0x0000;  2bytes
                 */
//                source.skip(2);//NULL Symbol.
//                Utils.skip(source, 2);
                Utils.readShort("SKIP", source);

                if (string_pool_list != null) {
                    string_pool_list.add(entry);
                }
            }
        }
    }

    public ArrayList<String> getString_pool_list() {
        return string_pool_list;
    }
}
