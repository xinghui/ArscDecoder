package com.xinghui.arsc.resourcetype;

import com.xinghui.arsc.Utils;

import java.io.IOException;

import okio.BufferedSource;

/**
 * This is the beginning of information about an entry in the resource
 * table.  It holds the reference to the name of this entry, and is
 * immediately followed by one of:
 * * A Res_value structure, if FLAG_COMPLEX is -not- set.
 * * An array of ResTable_map structures, if FLAG_COMPLEX is set.
 * These supply a set of name/value mappings of data.
 * <p/>
 * Created by hugo on 2014/11/10.
 */
public class ResTable_entry {

    // If set, this is a complex entry, holding a set of name/value
    // mappings.  It is followed by an array of ResTable_map structures.
    public static int FLAG_COMPLEX = 0x0001;
    // If set, this resource has been declared public, so libraries
    // are allowed to reference it.
    public static int FLAG_PUBLIC = 0x0002;

    // Number of bytes in this structure.
    private short size;

    private short flags;

    // Reference into ResTable_package::keyStrings identifying this entry.
    private ResStringPool_ref key;

    public ResTable_entry(short size, short flags, ResStringPool_ref key) {
        this.size = size;
        this.flags = flags;
        this.key = key;
    }

    public short getSize() {
        return size;
    }

    public short getFlags() {
        return flags;
    }

    public ResStringPool_ref getKey() {
        return key;
    }

    public static ResTable_entry build(BufferedSource source) throws IOException {

        short size = Utils.readShort("size:", source);
        short flags = Utils.readShort("flags:", source);
        ResStringPool_ref key = ResStringPool_ref.build(source);

        return new ResTable_entry(size, flags, key);
    }

    public static int sizeof() {
        return 2 * 2 + ResStringPool_ref.sizeof();
    }
}
