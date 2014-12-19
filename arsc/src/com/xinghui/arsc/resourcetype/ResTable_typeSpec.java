package com.xinghui.arsc.resourcetype;

import com.xinghui.arsc.Utils;

import java.io.IOException;

import okio.BufferedSource;

/**
 * A specification of the resources defined by a particular type.
 * <p/>
 * There should be one of these chunks for each resource type.
 * <p/>
 * This structure is followed by an array of integers providing the set of
 * configuration change flags (ResTable_config::CONFIG_*) that have multiple
 * resources for that configuration.  In addition, the high bit is set if that
 * resource has been made public.
 * <p/>
 * <p/>
 * Created by hugo on 2014/11/6.
 */
public class ResTable_typeSpec {

    // Additional flag indicating an entry is public.
    public static final int SPEC_PUBLIC = 0x40000000;

    private ResChunk_header header;

    // The type identifier this chunk is holding.  Type IDs start
    // at 1 (corresponding to the value of the type bits in a
    // resource identifier).  0 is invalid.
    private byte id;

    // Must be 0.
    private byte res0;
    // Must be 0.
    private short res1;

    // Number of uint32_t entry configuration masks that follow.
    private int entryCount;

    private ResTable_typeSpec(ResChunk_header header, byte id, byte res0, short res1, int entryCount) {
        this.header = header;
        this.id = id;
        this.res0 = res0;
        this.res1 = res1;
        this.entryCount = entryCount;
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

    public static int sizeof() {
        return ResChunk_header.sizeof() + 1 * 2 + 2 + 4;
    }
    public static int sizeofBody() {
        return 1 * 2 + 2 + 4;
    }


    public static ResTable_typeSpec build(BufferedSource source) throws IOException {
        ResChunk_header header = ResChunk_header.build(source);
        return build(header, source);
    }


    public static ResTable_typeSpec build(ResChunk_header header, BufferedSource source) throws IOException {

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

        //header.size = ResTable_typeSpec.sizeof() + sizeof(uint32_t) * entryCount;
        assert header.getSize() == header.getHeaderSize() + 4 * entryCount;

        return new ResTable_typeSpec(header, id, res0, res1, entryCount);
    }
}
