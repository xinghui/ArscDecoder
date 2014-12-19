package com.xinghui.arsc.resourcetype;

/**
 * /** ********************************************************************
 *  Base Types
 *
 *  These are standard types that are shared between multiple specific
 *  resource types.
 *
 *********************************************************************** */

import com.xinghui.arsc.Utils;

import java.io.IOException;

import okio.BufferedSource;

/**
 * Header that appears at the front of every data chunk in a resource.
 * <p/>
 * Created by hugo on 2014/11/4.
 */
public class ResChunk_header {

    private ResChunk_header(short type, short headerSize, int size) {
        this.type = type;
        this.headerSize = headerSize;
        this.size = size;
    }

    // Type identifier for this chunk.  The meaning of this value depends
    // on the containing chunk.
    private short type;

    // Size of the chunk header (in bytes).  Adding this value to
    // the address of the chunk allows you to find its associated data
    // (if any).
    private short headerSize;

    // Total size of this chunk (in bytes).  This is the chunkSize plus
    // the size of any data associated with the chunk.  Adding this value
    // to the chunk allows you to completely skip its contents (including
    // any child chunks).  If this value is the same as chunkSize, there is
    // no data associated with the chunk.
    private int size;

    public short getType() {
        return type;
    }

    public short getHeaderSize() {
        return headerSize;
    }

    public int getSize() {
        return size;
    }

    public static ResChunk_header build(BufferedSource source) throws IOException {
        short type =  Utils.readShort("type: ", source);

        short headerSize = Utils.readShort("headerSize: ", source);

        int size = Utils.readInt("size: ", source);
        return new ResChunk_header(type, headerSize, size);
    }

    public static int sizeof() {
        return 2 + 2 + 4;
    }
}
