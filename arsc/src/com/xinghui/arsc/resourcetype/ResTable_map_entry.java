package com.xinghui.arsc.resourcetype;

import com.xinghui.arsc.Utils;

import java.io.IOException;

import okio.BufferedSource;

/**
 * Extended form of a ResTable_entry for map entries, defining a parent map
 * resource from which to inherit values.
 * <p/>
 * Created by hugo on 2014/11/14.
 */
public class ResTable_map_entry extends ResTable_entry {

    // Resource identifier of the parent mapping, or 0 if there is none.
    private ResTable_ref parent;
    // Number of name/value pairs that follow for FLAG_COMPLEX.
    private int count;


    public ResTable_ref getParent() {
        return parent;
    }

    public int getCount() {
        return count;
    }

    public ResTable_map_entry(ResTable_entry entry, ResTable_ref parent, int count) {
        super((short) (8), (short) 0,new ResStringPool_ref(0));
//        super((short) (16), (short) 0,new ResStringPool_ref(0));
        this.parent = parent;
        this.count = count;
    }

    public static ResTable_map_entry build(ResTable_entry entry, BufferedSource source) throws IOException {

//        ResTable_entry.build(source);

        ResTable_ref ref = ResTable_ref.build(source);
        int count = Utils.readInt("count:", source);

        return new ResTable_map_entry(entry, ref, count);
    }

    public static int sizeofRead() {
        return ResTable_ref.sizeof() + 4;
    }

    public static int sizeofHave() {
        return ResTable_ref.sizeof() + 4 + ResTable_entry.sizeof();
    }

}
