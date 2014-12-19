package com.xinghui.arsc.resourcetype;

/**
 * Reference to a string in a string pool.
 */

import com.xinghui.arsc.Utils;

import java.io.IOException;

import okio.BufferedSource;

/**
 * Created by hugo on 2014/11/5.
 */
public class ResStringPool_ref {

    // Index into the string pool table (uint32_t-offset from the indices
    // immediately after ResStringPool_header) at which to find the location
    // of the string data in the pool.
    private int index;

    public ResStringPool_ref(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public static ResStringPool_ref build(BufferedSource source) throws IOException {
        int index = Utils.readInt("index: ", source);
        return new ResStringPool_ref(index);
    }

    public static int sizeof() {
        return 4;
    }
}
