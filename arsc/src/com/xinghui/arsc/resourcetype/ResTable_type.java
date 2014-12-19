package com.xinghui.arsc.resourcetype;

import com.xinghui.arsc.Utils;

import java.io.IOException;

import okio.BufferedSource;

/**
 * A collection of resource entries for a particular resource data
 * type. Followed by an array of uint32_t defining the resource
 * values, corresponding to the array of type strings in the
 * ResTable_package::typeStrings string block. Each of these hold an
 * index from entriesStart; a value of NO_ENTRY means that entry is
 * not defined.
 * <p/>
 * There may be multiple of these chunks for a particular resource type,
 * supply different configuration variations for the resource values of
 * that type.
 * <p/>
 * It would be nice to have an additional ordered index of entries, so
 * we can do a binary search if trying to find a resource by string name.
 * <p/>
 * Created by hugo on 2014/11/7.
 */
public class ResTable_type {

    public static final int NO_ENTRY = 0xFFFFFFFF;

    private ResChunk_header header;

    // The type identifier this chunk is holding.  Type IDs start
    // at 1 (corresponding to the value of the type bits in a
    // resource identifier).  0 is invalid.
    private byte id;

    // Must be 0.
    private byte res0;
    // Must be 0.
    private short res1;

    // Number of uint32_t entry indices that follow.
    private int entryCount;

    // Offset from header where ResTable_entry data starts.
    private int entriesStart;

    // Configuration this collection of entries is designed for.
    private ResTable_config config;

    private ResTable_type(ResChunk_header header, byte id, byte res0, short res1, int entryCount, int entriesStart, ResTable_config config) {
        this.header = header;
        this.id = id;
        this.res0 = res0;
        this.res1 = res1;
        this.entryCount = entryCount;
        this.entriesStart = entriesStart;
        this.config = config;
    }

    public ResChunk_header getHeader() {
        return header;
    }

    public byte getId() {
        return id;
    }

    public byte getRes0() {
        return res0;
    }

    public short getRes1() {
        return res1;
    }

    public int getEntryCount() {
        return entryCount;
    }

    public int getEntriesStart() {
        return entriesStart;
    }

    public ResTable_config getConfig() {
        return config;
    }

    public int sizeof() {
        return ResChunk_header.sizeof() + 1 * 2 + 2 + 4 * 2 + config.getSize();
    }

    public int sizeofBody() {
        return 1 * 2 + 2 + 4 * 2 + config.getSize();
    }

    public static ResTable_type build(BufferedSource source) throws IOException {
        ResChunk_header header = ResChunk_header.build(source);

        return build(header, source);
    }

    public static ResTable_type build(ResChunk_header header, BufferedSource source) throws IOException {
        byte id = Utils.readByte("id:", source);

        byte res0 = Utils.readByte("res0:", source);

        if (res0 != 0) {
            throw new IOException("ResTable_typeSpec res0 must be 0, the value is " + res0);
        }

        short res1 = Utils.readShort("res1:", source);

        if (res1 != 0) {
            throw new IOException("ResTable_typeSpec res1 must be 0, the value is " + res1);
        }


        int entryCount = Utils.readInt("entryCount:", source);

        int entriesStart = Utils.readInt("entriesStart:", source);
        if (entriesStart == NO_ENTRY) {//a value of NO_ENTRY means that entry is not defined.
            throw new IOException("entry is not defined.");
        }

        assert header.getSize() == header.getHeaderSize() + 4 * entryCount;

        ResTable_config config = ResTable_config.build(source);

        return new ResTable_type(header, id, res0, res1, entryCount, entriesStart, config);
    }
}
