package com.xinghui.arsc.resourcetype;

import com.xinghui.arsc.Utils;

import java.io.IOException;

import okio.BufferedSource;

/**
 *  This is a reference to a unique entry (a ResTable_entry structure)
 *  in a resource table.
 *
 *  The value is structured as:
 *
 *  0xpptteeee,
 *
 *  where pp is the package index,
 *  tt is the type index in that package,
 *  and eeee is the entry index in that type.
 *
 *  The package
 *  and type values start at 1 for the first item, to help catch cases
 *  where they have not been supplied.
 *
 * Created by hugo on 2014/11/14.
 */
public class ResTable_ref {
    private int ident;

    public int getIdent() {
        return ident;
    }

    public ResTable_ref(int ident) {
        this.ident = ident;
    }

    public static ResTable_ref build(BufferedSource source) throws IOException {
        int ident = Utils.readInt("ident:", source);
        return new ResTable_ref(ident);
    }

    public static int sizeof() {
        return 4;
    }
}
