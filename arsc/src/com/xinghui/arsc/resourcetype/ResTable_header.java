package com.xinghui.arsc.resourcetype;

/** ********************************************************************
 *  RESOURCE TABLE
 *
 *********************************************************************** */

/**
 * Header for a resource table.  Its data contains a series of
 * additional chunks:
 * * A ResStringPool_header containing all table values.  This string pool
 * contains all of the string values in the entire resource table (not
 * the names of entries or type identifiers however).
 * * One or more ResTable_package chunks.
 * <p/>
 * Specific entries within a resource table can be uniquely identified
 * with a single integer as defined by the ResTable_ref structure.
 */

import com.xinghui.arsc.Utils;

import java.io.IOException;

import okio.BufferedSource;

/**
 * Created by hugo on 2014/11/4.
 */
public class ResTable_header {

    private ResTable_header(ResChunk_header header, int packageCount) {
        this.header = header;
        this.packageCount = packageCount;
    }

    private ResChunk_header header;

    // The number of ResTable_package structures.
    private int packageCount;

    public ResChunk_header getHeader() {
        return header;
    }

    public int getPackageCount() {
        return packageCount;
    }

    public static ResTable_header build(BufferedSource source) throws IOException {
        ResChunk_header header = ResChunk_header.build(source);

        int packageCount = Utils.readInt("packageCount: ", source);

        return new ResTable_header(header, packageCount);
    }

    public static int sizeof() {
        return ResChunk_header.sizeof() + 4;
    }
}
