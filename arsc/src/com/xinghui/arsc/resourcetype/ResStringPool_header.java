package com.xinghui.arsc.resourcetype;

/** ********************************************************************
 *  String Pool
 *
 *  A set of strings that can be references by others through a
 *  ResStringPool_ref.
 *
 *********************************************************************** */

/**
 * Definition for a pool of strings.  The data of this chunk is an
 * array of uint32_t providing indices into the pool, relative to
 * stringsStart.  At stringsStart are all of the UTF-16 strings
 * concatenated together; each starts with a uint16_t of the string's
 * length and each ends with a 0x0000 terminator.  If a string is >
 * 32767 characters, the high bit of the length is set meaning to take
 * those 15 bits as a high word and it will be followed by another
 * uint16_t containing the low word.
 *
 * If styleCount is not zero, then immediately following the array of
 * uint32_t indices into the string table is another array of indices
 * into a style table starting at stylesStart.  Each entry in the
 * style table is an array of ResStringPool_span structures.
 */

import com.xinghui.arsc.Utils;

import java.io.IOException;

import okio.BufferedSource;

/**
 * Created by hugo on 2014/11/4.
 */
public class ResStringPool_header {

    // If set, the string index is sorted by the string values (based
    // on strcmp16()).
    public static final int SORTED_FLAG = 1 << 0;

    // String pool is encoded in UTF-8
    public static final int UTF8_FLAG = 1 << 8;

    private ResChunk_header header;

    // Number of strings in this pool (number of uint32_t indices that follow
    // in the data).
    private int stringCount;

    // Number of style span arrays in the pool (number of uint32_t indices
    // follow the string indices).
    // like this:
    //      <string name="abc"><u>abc</u></string>
    private int styleCount;

    // 等于0、SORTED_FLAG、UTF8_FLAG或者它们的组合值，用来描述字符串资源串的属性
    // 例如，SORTED_FLAG位等于1表示字符串是经过排序的，而UTF8_FLAG位等于1表示字符串是使用UTF8编码的，否则就是UTF16编码的。
    private int flags;

    // Index from header of the string data.
    private int stringsStart;

    // Index from header of the style data.
    private int stylesStart;

    public ResStringPool_header(ResChunk_header header, int stringCount, int styleCount, int flags, int stringsStart, int stylesStart) {
        this.header = header;
        this.stringCount = stringCount;
        this.styleCount = styleCount;
        this.flags = flags;
        this.stringsStart = stringsStart;
        this.stylesStart = stylesStart;
        System.out.println("SORTED_FLAG = " + SORTED_FLAG);
        System.out.println("UTF8_FLAG = " + UTF8_FLAG);
    }

    public ResChunk_header getHeader() {
        return header;
    }

    public int getStringCount() {
        return stringCount;
    }

    public int getStyleCount() {
        return styleCount;
    }

    public int getFlags() {
        return flags;
    }

    public int getStringsStart() {
        return stringsStart;
    }

    public int getStylesStart() {
        return stylesStart;
    }

    public static ResStringPool_header build(BufferedSource source) throws IOException {
        ResChunk_header header = ResChunk_header.build(source);

        if (header.getType() != ResourceTypes.RES_STRING_POOL_TYPE) {
            throw new IOException("NOT RES_STRING_POOL_TYPE, the type is " + header.getType());
        }

        int stringCount = Utils.readInt("stringCount: ", source);

        int styleCount = Utils.readInt("styleCount: ", source);

        int flags = Utils.readInt("flags: ", source);

        int stringsStart = Utils.readInt("stringsStart: ", source);

        int stylesStart = Utils.readInt("stylesStart: ", source);

        return new ResStringPool_header(header, stringCount, styleCount, flags, stringsStart, stylesStart);
    }

    public boolean isUTF8() {
        return (flags & UTF8_FLAG) != 0;//TODO
//        return true;
    }

    @Override
    public String toString() {
        return "header: [" + header + "], stringCount: " + stringCount +
                ", styleCount: " + styleCount + ", flags: " + flags +
                " [utf: " + isUTF8() + "], stringsStart: " + stringsStart +
                ", stylesStart: " + stylesStart;
    }

    public static int sizeof() {
        return ResChunk_header.sizeof() + 4 * 5;
    }
}
