package com.xinghui.arsc.resourcetype;

import com.xinghui.arsc.Utils;

import java.io.IOException;

import okio.BufferedSource;

/**
 * Created by hugo on 2014/11/5.
 */
public class ResStringPool_span {
    /**
     * This structure defines a span of style information associated with
     * a string in the pool.
     */
    public static final int END = 0xFFFFFFFF;

    // This is the name of the span -- that is, the name of the XML
    // tag that defined it.  The special value END (0xFFFFFFFF) indicates
    // the end of an array of spans.
    private ResStringPool_ref name;

    // The range of characters in the string that this span applies to.
    private int firstChar;
    private int lastChar;

    public ResStringPool_span(ResStringPool_ref name, int firstChar, int lastChar) {
        this.name = name;
        this.firstChar = firstChar;
        this.lastChar = lastChar;
    }

    public ResStringPool_ref getName() {
        return name;
    }

    public int getFirstChar() {
        return firstChar;
    }

    public int getLastChar() {
        return lastChar;
    }

    public static ResStringPool_span build(BufferedSource source) throws IOException {
        ResStringPool_ref name = ResStringPool_ref.build(source);
        int firstChar = Utils.readInt("firstChar: ", source);
        int lastChar = Utils.readInt("lastChar: ", source);
        int END1 = Utils.readInt("END1: ", source);
        return new ResStringPool_span(name, firstChar, lastChar);
    }

    public static void END(BufferedSource source) throws IOException {
        int END1 = Utils.readInt("END1: ", source);
        int END2 = Utils.readInt("END2: ", source);

        if (END != END1 || END != END2) {
            throw new IOException("END Symbol is WRONG");
        }
    }

    public static int sizeof() {
        return ResStringPool_ref.sizeof() + 4 * 2;
    }
}
